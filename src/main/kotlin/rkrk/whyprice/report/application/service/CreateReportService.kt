package rkrk.whyprice.report.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.domain.Asset
import rkrk.whyprice.report.application.port.input.CreateReportUseCase
import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto
import rkrk.whyprice.report.application.port.out.CreateReportPort
import rkrk.whyprice.report.application.port.out.RankFetcher
import rkrk.whyprice.report.application.port.out.ReportCachesRepository
import rkrk.whyprice.report.domain.Report

@Service
@Transactional(readOnly = true)
class CreateReportService(
    private val createReportPort: CreateReportPort,
    private val rankFetcher: RankFetcher,
    private val reportCachesRepository: ReportCachesRepository,
) : CreateReportUseCase {
    @Transactional
    override fun fetchHighReports(): List<ResponseReportDto> {
        val reports =
            rankFetch()
                .map { assetName ->
                    createReport(assetName)
                }.map { ResponseReportDto(it.getReportBody(), it.getCreateTime()) }
        return reports
    }

    @Transactional
    override fun fetchHighReport(asset: Asset): ResponseReportDto {
        createReport(asset.getAssetName()).let {
            return ResponseReportDto(it.getReportBody(), it.getCreateTime())
        }
    }

    private fun createReport(assetName: String): Report {
        if (reportCachesRepository.isCacheValid(assetName)) {
            return reportCachesRepository.findOne(assetName).getMainReport()
        } else {
            val report = createReportPort.createReport(assetName)
            reportCachesRepository.saveOrUpdate(report)
            return report
        }
    }

    private fun rankFetch(): List<String> = rankFetcher.fetch()
}

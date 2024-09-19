package rkrk.whyprice.report.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.domain.Asset
import rkrk.whyprice.report.application.port.input.ReportUseCase
import rkrk.whyprice.report.application.port.out.CreateReportPort
import rkrk.whyprice.report.application.port.out.RankFetcher
import rkrk.whyprice.report.application.port.out.ReportCachesRepository
import rkrk.whyprice.report.domain.Report

@Service
@Transactional(readOnly = true)
class ReportService(
    private val createReportPort: CreateReportPort,
    private val rankFetcher: RankFetcher,
    private val reportCachesRepository: ReportCachesRepository,
) : ReportUseCase {
    @Transactional
    override fun fetchHighReports(): List<Report> {
        val reports =
            rankFetch().map { assetName ->
                createReport(assetName)
            }
        return reports
    }

    @Transactional
    override fun fetchHighReport(asset: Asset): Report = createReport(asset.getAssetName())

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

package rkrk.whyprice.domain.report.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.domain.report.Report
import rkrk.whyprice.domain.report.service.infra.ReportCachesRepository
import rkrk.whyprice.inputapi.usecase.ReportUseCase
import rkrk.whyprice.share.Asset
import rkrk.whyprice.share.RankFetcher
import rkrk.whyprice.share.Responser

@Service
@Transactional(readOnly = true)
class ReportService(
    private val responser: Responser,
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
            val report = responser.createReport(assetName)
            reportCachesRepository.saveOrUpdate(report)
            return report
        }
    }

    private fun rankFetch(): List<String> = rankFetcher.fetch()
}

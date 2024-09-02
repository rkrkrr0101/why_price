package rkrk.whyprice.domain.report.service

import org.springframework.stereotype.Service
import rkrk.whyprice.domain.report.Report
import rkrk.whyprice.inputapi.usecase.ReportUseCase
import rkrk.whyprice.share.Asset
import rkrk.whyprice.share.RankFetcher
import rkrk.whyprice.share.Responser

@Service
class ReportService(
    private val responser: Responser,
    private val rankFetcher: RankFetcher,
) : ReportUseCase {
    override fun fetchHighReports(): List<Report> = responser.createReport(rankFetcher)

    override fun fetchHighReport(asset: Asset): Report = responser.createReport(asset)
}

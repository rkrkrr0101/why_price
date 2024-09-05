package rkrk.whyprice.domain.report.service

import org.springframework.stereotype.Service
import rkrk.whyprice.domain.report.Report
import rkrk.whyprice.domain.report.service.infra.ReportCachesRepository
import rkrk.whyprice.inputapi.usecase.ReportUseCase
import rkrk.whyprice.share.Asset
import rkrk.whyprice.share.RankFetcher
import rkrk.whyprice.share.Responser

@Service
class ReportService(
    private val responser: Responser,
    private val rankFetcher: RankFetcher,
    private val reportCachesRepository: ReportCachesRepository,
) : ReportUseCase {
    // todo 캐시추가,밑에랑 합쳐서 하나로 해도될듯
    // rankFetcher로 값가져오는 책임을 밑으로 내려야하나 여기서해야하나
    override fun fetchHighReports(): List<Report> = responser.createReport(rankFetcher)

    override fun fetchHighReport(asset: Asset): Report = responser.createReport(asset)
}

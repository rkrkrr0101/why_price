package rkrk.whyprice.mock

import rkrk.whyprice.report.Report
import rkrk.whyprice.share.Asset
import rkrk.whyprice.share.RankFetcher
import rkrk.whyprice.share.Responser

class ResponserMock : Responser {
    override fun hasVolatility(asset: Asset): Boolean = asset.getIdentityCode().contains("volatility")

    override fun createReport(rankFetcher: RankFetcher): List<Report> {
        TODO("Not yet implemented")
    }

    override fun createReport(asset: Asset): Report {
        TODO("Not yet implemented")
    }
}

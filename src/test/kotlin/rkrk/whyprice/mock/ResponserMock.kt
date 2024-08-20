package rkrk.whyprice.mock

import rkrk.whyprice.common.Responser
import rkrk.whyprice.report.Report
import rkrk.whyprice.trackedAssets.Asset

class ResponserMock : Responser {
    override fun hasVolatility(asset: Asset): Boolean = asset.getIdentityCode().contains("volatility")

    override fun createReport(): List<Report> {
        TODO("Not yet implemented")
    }

    override fun createReport(asset: Asset): Report {
        TODO("Not yet implemented")
    }
}

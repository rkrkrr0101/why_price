package rkrk.whyprice.responser

import rkrk.whyprice.common.Responser
import rkrk.whyprice.report.Report
import rkrk.whyprice.trackedAssets.Asset

class GptResponser : Responser {
    override fun hasVolatility(asset: Asset): Boolean {
        TODO("Not yet implemented")
    }

    override fun createReport(): List<Report> {
        TODO("Not yet implemented")
    }

    override fun createReport(asset: Asset): Report {
        TODO("Not yet implemented")
    }
}

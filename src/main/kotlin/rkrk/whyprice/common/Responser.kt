package rkrk.whyprice.common

import rkrk.whyprice.report.Report
import rkrk.whyprice.trackedAssets.Asset

interface Responser  {
    fun hasVolatility(asset: Asset): Boolean

    fun createReport(): List<Report>

    fun createReport(asset: Asset): Report
}

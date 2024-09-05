package rkrk.whyprice.share

import rkrk.whyprice.domain.report.Report

interface Responser {
    fun hasVolatility(asset: Asset): Boolean

    fun createReport(assetName: String): Report
}

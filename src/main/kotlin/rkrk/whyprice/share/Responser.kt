package rkrk.whyprice.share

import rkrk.whyprice.domain.report.Report

interface Responser {
    fun hasVolatility(
        asset: Asset,
        volatilityTime: Int = 1,
    ): Boolean

    fun createReport(
        assetName: String,
        volatilityTime: Int = 1,
    ): Report
}

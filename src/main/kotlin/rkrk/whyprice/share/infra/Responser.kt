package rkrk.whyprice.share.infra

import rkrk.whyprice.asset.domain.Asset
import rkrk.whyprice.report.domain.Report

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

package rkrk.whyprice.report

import rkrk.whyprice.trackedAssets.Asset
import java.time.LocalDateTime

data class Report(
    val asset: Asset,
    val report: String,
    val createTime: LocalDateTime,
)

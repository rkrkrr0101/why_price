package rkrk.whyprice.domain.report

import java.time.LocalDateTime

data class Report(
    val report: String,
    val createTime: LocalDateTime,
)

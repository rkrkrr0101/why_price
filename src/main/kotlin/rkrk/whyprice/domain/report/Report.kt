package rkrk.whyprice.domain.report

import java.time.LocalDateTime

class Report(
    private val report: String,
    private val createDateTime: LocalDateTime,
) {
    fun getReportBody(): String = report

    fun getCreateTime(): LocalDateTime = createDateTime
}

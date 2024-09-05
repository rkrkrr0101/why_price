package rkrk.whyprice.domain.report

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
class Report(
    private val report: String,
    private val createDateTime: LocalDateTime,
) {
    fun getReportBody(): String = report

    fun getCreateTime(): LocalDateTime = createDateTime
}

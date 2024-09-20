package rkrk.whyprice.report.application.port.input.dto.res

import java.time.LocalDateTime

data class ResponseReportDto(
    val report: String,
    val reportDate: LocalDateTime,
)

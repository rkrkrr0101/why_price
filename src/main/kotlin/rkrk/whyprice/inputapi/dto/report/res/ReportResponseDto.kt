package rkrk.whyprice.inputapi.dto.report.res

import java.time.LocalDateTime

data class ReportResponseDto(
    val report: String,
    val reportDate: LocalDateTime,
)

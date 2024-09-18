package rkrk.whyprice.report.adapter.input.web.dto.res

import java.time.LocalDateTime

data class ReportResponseDto(
    val report: String,
    val reportDate: LocalDateTime,
)

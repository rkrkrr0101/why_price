package rkrk.whyprice.report.adapter.input.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.report.application.port.input.CreateReportUseCase
import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto
import rkrk.whyprice.share.Result

@RestController
@RequestMapping("/api/report")
class ReportController(
    private val reportUseCase: CreateReportUseCase,
) {
    @GetMapping("/stock/high")
    fun getHighReports(): Result<List<ResponseReportDto>> {
        val reports =
            reportUseCase
                .getHighReports()

        return Result(reports)
    }
}

package rkrk.whyprice.report.adapter.input.web

import org.springframework.ai.tool.annotation.Tool
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
    @Tool(description = "A tool that creates a report explaining the reasons for the high trading volume of the top 10 Korean stocks currently traded in real time")
    fun getHighReports(): Result<List<ResponseReportDto>> {
        val reports =
            reportUseCase
                .getHighReports()

        return Result(reports)
    }
}

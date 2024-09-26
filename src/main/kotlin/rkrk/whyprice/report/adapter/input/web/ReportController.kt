package rkrk.whyprice.report.adapter.input.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.report.application.port.input.CreateReportUseCase
import rkrk.whyprice.report.application.port.input.dto.req.KoreanStockReportDto
import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto
import rkrk.whyprice.share.Result

@RestController
@RequestMapping("/api/report")
class ReportController(
    private val reportUseCase: CreateReportUseCase,
) {
    @GetMapping("/stock/high")
    fun fetchHighReports(): Result<List<ResponseReportDto>> {
        val reports =
            reportUseCase
                .fetchHighReports()

        return Result(reports)
    }

    @GetMapping("/stock")
    fun fetchKoreanStockReport(stockDto: KoreanStockReportDto): Result<ResponseReportDto> = Result(reportUseCase.fetchHighReport(stockDto))
}

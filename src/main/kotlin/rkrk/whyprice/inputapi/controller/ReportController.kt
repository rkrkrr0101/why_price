package rkrk.whyprice.inputapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.inputapi.dto.report.res.ReportResponseDto
import rkrk.whyprice.inputapi.result.Result
import rkrk.whyprice.inputapi.usecase.ReportUseCase

@RestController
@RequestMapping("/api/report")
class ReportController(
    private val reportUseCase: ReportUseCase,
) {
    @GetMapping("/stock/high")
    fun fetchHighReports(): Result<List<ReportResponseDto>> {
        val reports =
            reportUseCase
                .fetchHighReports()
                .map { ReportResponseDto(it.report, it.createTime) }
        return Result(reports)
    }

    @GetMapping("/stock")
    fun fetchKoreanStockReport(
        @RequestParam name: String,
        @RequestParam crno: String,
    ): Result<ReportResponseDto> {
        val report = reportUseCase.fetchHighReport(KoreanStock(name, crno))
        return Result(ReportResponseDto(report.report, report.createTime))
    }
}

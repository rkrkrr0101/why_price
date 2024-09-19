package rkrk.whyprice.report.adapter.input.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.member.domain.KoreanStock
import rkrk.whyprice.report.adapter.input.web.dto.req.KoreanStockReportDto
import rkrk.whyprice.report.adapter.input.web.dto.res.ReportResponseDto
import rkrk.whyprice.report.application.port.input.ReportUseCase
import rkrk.whyprice.share.Result

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
                .map { ReportResponseDto(it.getReportBody(), it.getCreateTime()) }
        return Result(reports)
    }

    @GetMapping("/stock")
    fun fetchKoreanStockReport(stockDto: KoreanStockReportDto): Result<ReportResponseDto> {
        val report = reportUseCase.fetchHighReport(KoreanStock(stockDto.crno, stockDto.name))
        return Result(ReportResponseDto(report.getReportBody(), report.getCreateTime()))
    }
}

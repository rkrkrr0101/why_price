package rkrk.whyprice.report.application.port.input

import rkrk.whyprice.report.application.port.input.dto.req.KoreanStockReportDto
import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto

interface CreateReportUseCase {
    fun fetchHighReports(): List<ResponseReportDto>

    fun fetchHighReport(dto: KoreanStockReportDto): ResponseReportDto
}

package rkrk.whyprice.report.application.port.input

import rkrk.whyprice.member.domain.Asset
import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto

interface CreateReportUseCase {
    fun fetchHighReports(): List<ResponseReportDto>

    fun fetchHighReport(asset: Asset): ResponseReportDto
}

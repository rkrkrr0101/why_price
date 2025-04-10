package rkrk.whyprice.report.application.port.input

import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto

interface CreateReportUseCase {
     fun getHighReports(): List<ResponseReportDto>


}

package rkrk.whyprice.report.application.service


import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.report.application.port.input.CreateReportUseCase
import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto
import rkrk.whyprice.report.application.port.out.HotStockReportRepository

@Service
@Transactional(readOnly = true)
class CreateReportService(
    private val hotStockReportRepository: HotStockReportRepository,
) : CreateReportUseCase {
    @Transactional(readOnly = true)
    override fun getHighReports():List<ResponseReportDto>{
        return hotStockReportRepository.findAll()
            .map { ResponseReportDto(
                it.getMainReport().getReportBody(),
                it.getMainReport().getCreateTime())
            }
    }
}

package rkrk.whyprice.report.adapter.out.persistence.hotstockreport

import org.springframework.stereotype.Repository
import rkrk.whyprice.report.application.port.out.HotStockReportRepository

@Repository
class HotStockReportRepositoryImpl(
    private val jpaRepository: HotStockReportJpaRepository): HotStockReportRepository {
    override fun findAll(): List<HotStockReport> {
        return jpaRepository.findAll()
    }
}
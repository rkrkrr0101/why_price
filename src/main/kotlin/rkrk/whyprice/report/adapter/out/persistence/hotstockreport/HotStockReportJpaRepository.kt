package rkrk.whyprice.report.adapter.out.persistence.hotstockreport

import org.springframework.data.jpa.repository.JpaRepository

interface HotStockReportJpaRepository: JpaRepository<HotStockReport, Long> {
}
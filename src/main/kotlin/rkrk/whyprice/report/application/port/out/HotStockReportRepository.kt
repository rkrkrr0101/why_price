package rkrk.whyprice.report.application.port.out

import rkrk.whyprice.report.adapter.out.persistence.hotstockreport.HotStockReport

interface HotStockReportRepository {
    fun findAll(): List<HotStockReport>
}
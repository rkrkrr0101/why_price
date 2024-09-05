package rkrk.whyprice.domain.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import rkrk.whyprice.domain.report.ReportCache

interface ReportCachesJpaRepository : JpaRepository<ReportCache, Long> {
    fun findByReportAssetName(assetName: String): ReportCache?
}

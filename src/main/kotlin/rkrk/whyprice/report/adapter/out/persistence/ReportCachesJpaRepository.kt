package rkrk.whyprice.report.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ReportCachesJpaRepository : JpaRepository<ReportCache, Long> {
    fun findByReportAssetName(assetName: String): ReportCache?
}

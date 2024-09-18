package rkrk.whyprice.report.adapter.out.persistence

import org.springframework.stereotype.Repository
import rkrk.whyprice.report.application.port.out.ReportCachesRepository
import rkrk.whyprice.report.domain.Report
import rkrk.whyprice.share.infra.CustomDateTime

@Repository
class ReportCachesRepositoryImpl(
    private val jpaRepository: ReportCachesJpaRepository,
    private val customDateTime: CustomDateTime,
) : ReportCachesRepository {
    override fun saveOrUpdate(report: Report) {
        val reportCache = jpaRepository.findByReportAssetName(report.getAssetName())
        if (reportCache == null) {
            val temp = ReportCache(report)
            jpaRepository.save(temp)
            return
        }
        if (!reportCache.isValid(customDateTime.getNow())) {
            reportCache.updateReport(report)
        }
    }

    override fun isCacheValid(assetName: String): Boolean {
        val reportCaches = jpaRepository.findByReportAssetName(assetName) ?: return false
        return reportCaches.isValid(customDateTime.getNow())
    }

    override fun findOne(assetName: String): ReportCache =
        jpaRepository.findByReportAssetName(assetName)
            ?: throw IllegalArgumentException("존재하지 않는 캐시입니다.")
}

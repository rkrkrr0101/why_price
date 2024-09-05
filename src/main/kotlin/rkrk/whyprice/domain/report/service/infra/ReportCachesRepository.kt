package rkrk.whyprice.domain.report.service.infra

import rkrk.whyprice.domain.report.Report
import rkrk.whyprice.domain.report.ReportCache

interface ReportCachesRepository {
    fun saveOrUpdate(report: Report)

    fun isCacheValid(assetName: String): Boolean

    fun findOne(assetName: String): ReportCache
}

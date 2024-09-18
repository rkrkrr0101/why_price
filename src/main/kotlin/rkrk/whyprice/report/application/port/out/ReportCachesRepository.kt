package rkrk.whyprice.report.application.port.out

import rkrk.whyprice.report.adapter.out.persistence.ReportCache
import rkrk.whyprice.report.domain.Report

interface ReportCachesRepository {
    fun saveOrUpdate(report: Report)

    fun isCacheValid(assetName: String): Boolean

    fun findOne(assetName: String): ReportCache
}

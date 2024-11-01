package rkrk.whyprice.report.application.port.out

import rkrk.whyprice.report.domain.Report

interface CreateReportPort {
    suspend fun createReport(
        assetName: String,
        volatilityTime: Int = 24,
    ): Report
}

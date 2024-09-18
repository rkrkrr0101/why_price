package rkrk.whyprice.report.application.port.out

import rkrk.whyprice.report.domain.Report

interface CreateReportPort {
    fun createReport(
        assetName: String,
        volatilityTime: Int = 1,
    ): Report
}

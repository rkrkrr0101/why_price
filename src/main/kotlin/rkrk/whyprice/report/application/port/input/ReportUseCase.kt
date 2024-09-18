package rkrk.whyprice.report.application.port.input

import rkrk.whyprice.asset.domain.Asset
import rkrk.whyprice.report.domain.Report

interface ReportUseCase {
    fun fetchHighReports(): List<Report>

    fun fetchHighReport(asset: Asset): Report
}

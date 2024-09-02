package rkrk.whyprice.inputapi.usecase

import rkrk.whyprice.domain.report.Report
import rkrk.whyprice.share.Asset

interface ReportUseCase {
    fun fetchHighReports(): List<Report>

    fun fetchHighReport(asset: Asset): Report
}

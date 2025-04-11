package rkrk.whyprice.tool.mock


import rkrk.whyprice.report.application.port.out.CreateReportPort
import rkrk.whyprice.report.domain.Report


class ResponserMock(
    private val dateTime: CustomDateTimeMock,
) : CreateReportPort
     {

    override suspend fun createReport(
        assetName: String,
        volatilityTime: Int,
    ): Report {
        val report =
            Report(
                assetName,
                """$assetName report""",
                dateTime.getNow(),
            )

        return report
    }
}

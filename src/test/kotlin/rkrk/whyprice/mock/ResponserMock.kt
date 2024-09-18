package rkrk.whyprice.mock

import rkrk.whyprice.asset.domain.Asset
import rkrk.whyprice.member.application.port.out.CheckVolatilityPort
import rkrk.whyprice.report.application.port.out.CreateReportPort
import rkrk.whyprice.report.domain.Report

class ResponserMock(
    private val dateTime: CustomDateTimeMock,
) : CreateReportPort,
    CheckVolatilityPort {
    override fun hasVolatility(
        asset: Asset,
        volatilityTime: Int,
    ): Boolean = asset.getAssetName().contains("삼성")

    override fun createReport(
        assetName: String,
        volatilityTime: Int,
    ): Report =
        Report(
            assetName,
            """$assetName report""",
            dateTime.getNow(),
        )
}

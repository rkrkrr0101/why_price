package rkrk.whyprice.tool.mock

import rkrk.whyprice.member.application.port.out.CheckVolatilityPort
import rkrk.whyprice.member.domain.Asset
import rkrk.whyprice.report.application.port.out.CreateReportPort
import rkrk.whyprice.report.domain.Report
import rkrk.whyprice.tool.TestConstant

class ResponserMock(
    private val dateTime: CustomDateTimeMock,
) : CreateReportPort,
    CheckVolatilityPort {
    override fun hasVolatility(
        asset: Asset,
        volatilityTime: Int,
    ): Boolean = asset.getAssetName().contains(TestConstant.HAS_VOLATILITY_WORD)

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

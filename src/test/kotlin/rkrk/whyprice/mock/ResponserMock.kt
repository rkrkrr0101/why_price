package rkrk.whyprice.mock

import rkrk.whyprice.domain.report.Report
import rkrk.whyprice.share.Asset
import rkrk.whyprice.share.Responser

class ResponserMock(
    private val dateTime: CustomDateTimeMock,
) : Responser {
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

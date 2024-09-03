package rkrk.whyprice.mock

import rkrk.whyprice.domain.report.Report
import rkrk.whyprice.share.Asset
import rkrk.whyprice.share.RankFetcher
import rkrk.whyprice.share.Responser

class ResponserMock(
    private val dateTime: CustomDateTimeMock,
) : Responser {
    override fun hasVolatility(asset: Asset): Boolean = asset.getIdentityCode().contains("volatility")

    override fun createReport(rankFetcher: RankFetcher): List<Report> {
        val fetch = rankFetcher.fetch()
        val resList = mutableListOf<Report>()

        for (assetName in fetch) {
            resList.add(
                Report(
                    """$assetName report""",
                    dateTime.getNow(),
                ),
            )
        }
        return resList
    }

    override fun createReport(asset: Asset): Report =
        Report(
            """${asset.getAssetName()} report""",
            dateTime.getNow(),
        )
}

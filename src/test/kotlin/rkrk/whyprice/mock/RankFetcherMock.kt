package rkrk.whyprice.mock

import rkrk.whyprice.share.RankFetcher

class RankFetcherMock(
    private val rankList: List<String>,
) : RankFetcher {
    override fun fetch(): List<String> = rankList
}

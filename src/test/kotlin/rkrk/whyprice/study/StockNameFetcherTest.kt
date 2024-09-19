package rkrk.whyprice.study

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import rkrk.whyprice.member.adapter.out.api.assetfetcher.apifetcher.impl.StockNameFetcher
import rkrk.whyprice.share.impl.ApiHelperImpl

class StockNameFetcherTest {
    @Test
    fun successFetch() {
        val stockNameFetcher = StockNameFetcher(ApiHelperImpl())
        val fetch = stockNameFetcher.fetch("1301110006246")

        Assertions.assertThat(fetch["assetName"]).isEqualTo("삼성전자")
    }
}

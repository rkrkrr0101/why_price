package rkrk.whyprice.study

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import rkrk.whyprice.member.adapter.out.api.assetfetcher.apifetcher.impl.FindCrnoByStockNameFetcher
import rkrk.whyprice.share.impl.ApiHelperImpl

class FindCrnoByStockNameFetcherTest {
    @Test
    fun successFetch() {
        val findCrnoByStockNameFetcher = FindCrnoByStockNameFetcher(ApiHelperImpl())
        val fetch = findCrnoByStockNameFetcher.fetch("1301110006246")

        Assertions.assertThat(fetch["assetName"]).isEqualTo("삼성전자")
    }
}

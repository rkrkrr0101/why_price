package rkrk.whyprice.outerapi

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import rkrk.whyprice.assetfetcher.apifetcher.impl.BasicFetcher

class BasicFetcherTest {
    @Test
    fun successFetch() {
        val basicFetcher = BasicFetcher()
        val fetch = basicFetcher.fetch("KR7005930003")

        Assertions.assertThat(fetch["assetName"]).isEqualTo("삼성전자")
    }
}
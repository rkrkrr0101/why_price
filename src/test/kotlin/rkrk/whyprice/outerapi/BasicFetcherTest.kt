package rkrk.whyprice.outerapi

import org.junit.jupiter.api.Test
import rkrk.whyprice.assetfetcher.apifetcher.impl.BasicFetcher

class BasicFetcherTest {
    @Test
    fun successFetch() {
        val basicFetcher = BasicFetcher()
        val fetch = basicFetcher.fetch("KR7005930003")
        println(fetch)
    }
}

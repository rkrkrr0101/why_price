package rkrk.whyprice.study

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import rkrk.whyprice.inputapi.util.ApiUtilImpl
import rkrk.whyprice.outputapi.assetfetcher.apifetcher.impl.BasicFetcher

class BasicFetcherTest {
    @Test
    fun successFetch() {
        val basicFetcher = BasicFetcher(ApiUtilImpl())
        val fetch = basicFetcher.fetch("1301110006246")

        Assertions.assertThat(fetch["assetName"]).isEqualTo("삼성전자")
    }
}

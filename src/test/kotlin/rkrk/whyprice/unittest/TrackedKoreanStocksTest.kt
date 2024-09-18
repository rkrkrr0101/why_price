package rkrk.whyprice.unittest

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rkrk.whyprice.asset.domain.KoreanStock
import rkrk.whyprice.member.domain.TrackedKoreanStocks
import rkrk.whyprice.mock.CustomDateTimeMock
import rkrk.whyprice.mock.ResponserMock

class TrackedKoreanStocksTest {
    @Test
    @DisplayName("가지고있는 자산의 변동성을 체크할수있다")
    fun checkVolatility() {
        val trackedAssets = basicSetUpTrackedAssets()

        val volatilityAssetList =
            trackedAssets.hasVolatility(ResponserMock(CustomDateTimeMock("2021-01-01T00:00:00Z")))
        val tickerList = volatilityAssetList.map { it.getIdentityCode() }

        Assertions.assertThat(volatilityAssetList.size).isEqualTo(2)
        Assertions.assertThat(tickerList.contains("notVol1")).isFalse()
    }

    private fun basicSetUpTrackedAssets(): TrackedKoreanStocks {
        val stock1 = KoreanStock("volatility1", "삼성전자")
        val stock2 = KoreanStock("volatility2", "롯데제과")
        val stock3 = KoreanStock("notVol1", "LG전자")
        val trackedKoreanStocks = TrackedKoreanStocks()
        trackedKoreanStocks.addKoreanStock(stock1)
        trackedKoreanStocks.addKoreanStock(stock2)
        trackedKoreanStocks.addKoreanStock(stock3)

        return trackedKoreanStocks
    }
}

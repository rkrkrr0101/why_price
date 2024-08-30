package rkrk.whyprice.unittest

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rkrk.whyprice.asset.KoreanStock
import rkrk.whyprice.mock.ResponserMock
import rkrk.whyprice.trackedAssets.TrackedKoreanStocks

class TrackedKoreanStocksTest {
    @Test
    @DisplayName("가지고있는 자산의 변동성을 체크할수있다")
    fun checkVolatility() {
        val trackedAssets = basicSetUpTrackedAssets()

        val volatilityAssetList = trackedAssets.hasVolatility(ResponserMock())
        val tickerList = volatilityAssetList.map { it.getIdentityCode() }

        Assertions.assertThat(volatilityAssetList.size).isEqualTo(2)
        Assertions.assertThat(tickerList.contains("notVol1")).isFalse()
    }

    private fun basicSetUpTrackedAssets(): TrackedKoreanStocks {
        val stock1 = KoreanStock("volatility1", "삼성전자")
        val stock2 = KoreanStock("volatility2", "삼성전자")
        val stock3 = KoreanStock("notVol1", "삼성전자")
        val trackedKoreanStocks = TrackedKoreanStocks()
        trackedKoreanStocks.addAsset(stock1)
        trackedKoreanStocks.addAsset(stock2)
        trackedKoreanStocks.addAsset(stock3)

        return trackedKoreanStocks
    }
}

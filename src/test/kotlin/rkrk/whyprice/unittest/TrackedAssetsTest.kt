package rkrk.whyprice.unittest

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rkrk.whyprice.asset.KoreanStock
import rkrk.whyprice.mock.ResponserMock
import rkrk.whyprice.trackedAssets.TrackedAssets

class TrackedAssetsTest {
    @Test
    @DisplayName("가지고있는 자산의 변동성을 체크할수있다")
    fun checkVolatility() {
        val trackedAssets = basicSetUpTrackedAssets()

        val volatilityAssetList = trackedAssets.hasVolatility(ResponserMock())
        val tickerList = volatilityAssetList.map { it.getIdentityCode() }

        Assertions.assertThat(volatilityAssetList.size).isEqualTo(2)
        Assertions.assertThat(tickerList.contains("notVol1")).isFalse()
    }

    private fun basicSetUpTrackedAssets(): TrackedAssets {
        val stock1 = KoreanStock("volatility1")
        val stock2 = KoreanStock("volatility2")
        val stock3 = KoreanStock("notVol1")
        val trackedAssets = TrackedAssets()
        trackedAssets.addAsset(stock1)
        trackedAssets.addAsset(stock2)
        trackedAssets.addAsset(stock3)

        return trackedAssets
    }
}

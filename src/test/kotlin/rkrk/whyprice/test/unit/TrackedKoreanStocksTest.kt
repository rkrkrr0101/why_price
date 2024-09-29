package rkrk.whyprice.test.unit

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rkrk.whyprice.member.domain.KoreanStock
import rkrk.whyprice.member.domain.Member
import rkrk.whyprice.member.domain.TrackedKoreanStocks
import rkrk.whyprice.tool.mock.CustomDateTimeMock
import rkrk.whyprice.tool.mock.ResponserMock

class TrackedKoreanStocksTest {
    @Test
    @DisplayName("가지고있는 자산의 변동성을 체크할수있다")
    fun checkVolatility() {
        val trackedAssets = basicSetUpTrackedAssets()

        val volatilityAssetList =
            trackedAssets.hasVolatility(ResponserMock(CustomDateTimeMock("2021-01-01T00:00:00Z")))
        val tickerList = volatilityAssetList.map { it.getIdentityCode() }

        Assertions.assertThat(volatilityAssetList.size).isEqualTo(1)
        Assertions.assertThat(tickerList.contains("notVol1")).isFalse()
    }

    private fun basicSetUpTrackedAssets(): TrackedKoreanStocks {
        val stock1 = KoreanStock("volatility1", "삼성전자")
        val stock2 = KoreanStock("volatility2", "롯데제과")
        val stock3 = KoreanStock("notVol1", "LG전자")
        val member = Member("abc")
        val trackedKoreanStocks = TrackedKoreanStocks()
        trackedKoreanStocks.addKoreanStock(stock1, member)
        trackedKoreanStocks.addKoreanStock(stock2, member)
        trackedKoreanStocks.addKoreanStock(stock3, member)

        return trackedKoreanStocks
    }
}

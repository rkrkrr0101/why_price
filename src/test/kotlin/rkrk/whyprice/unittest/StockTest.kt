package rkrk.whyprice.unittest

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rkrk.whyprice.asset.Stock
import rkrk.whyprice.mock.AssetFetcherMockOne
import rkrk.whyprice.mock.AssetFetcherMockTwo

class StockTest {
    @Test
    @DisplayName("stock의 데이터추가를 정상적으로 할수있다")
    fun fetchDataSuccess() {
        val stock = Stock("TEST")
        val assetFetcherList = listOf(AssetFetcherMockOne(), AssetFetcherMockTwo())

        stock.fetchData(assetFetcherList)

        Assertions.assertThat(stock.isDataEmpty()).isFalse()
        Assertions.assertThat(stock.getData().size).isEqualTo(2)
    }
}

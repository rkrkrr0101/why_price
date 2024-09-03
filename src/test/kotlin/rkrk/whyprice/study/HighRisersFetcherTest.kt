package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.helper.ApiHelperImpl
import rkrk.whyprice.outputapi.marketfetcher.HighRisersFetcher

class HighRisersFetcherTest {
    @Test
    fun fetchTest() {
        // Given
        val highRisersFetcher = HighRisersFetcher(ApiHelperImpl())

        // When
        val highRisers = highRisersFetcher.fetch()

        // Then
        print(highRisers)
    }
}

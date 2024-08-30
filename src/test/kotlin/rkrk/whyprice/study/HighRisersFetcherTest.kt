package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.inputapi.util.ApiUtilImpl
import rkrk.whyprice.outputapi.marketfetcher.HighRisersFetcher

class HighRisersFetcherTest {
    @Test
    fun fetchTest() {
        // Given
        val highRisersFetcher = HighRisersFetcher(ApiUtilImpl())

        // When
        val highRisers = highRisersFetcher.fetch()

        // Then
        print(highRisers)
    }
}

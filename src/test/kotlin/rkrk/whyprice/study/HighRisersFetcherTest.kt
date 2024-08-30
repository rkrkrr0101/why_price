package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.outputapi.marketfetcher.HighRisersFetcher
import rkrk.whyprice.util.ApiUtilImpl

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

package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.marketfetcher.HighRisersFetcher

class HighRisersFetcherTest {
    @Test
    fun fetchTest() {
        // Given
        val highRisersFetcher = HighRisersFetcher()

        // When
        val highRisers = highRisersFetcher.fetch()

        // Then
        print(highRisers)
    }
}

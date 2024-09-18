package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.report.adapter.out.api.HighRisersFetcher
import rkrk.whyprice.share.impl.ApiHelperImpl

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

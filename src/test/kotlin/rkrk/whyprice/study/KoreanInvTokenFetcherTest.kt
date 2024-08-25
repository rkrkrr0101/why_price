package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.marketfetcher.KoreanInvTokenFetcher
import rkrk.whyprice.util.ApiUtilImpl

class KoreanInvTokenFetcherTest {
    @Test
    fun fetchTest() {
        // Given
        val koreanTokenFetcher = KoreanInvTokenFetcher(ApiUtilImpl())

        // When
        val koreanToken = koreanTokenFetcher.fetch()

        // Then
        print(koreanToken)
    }
}

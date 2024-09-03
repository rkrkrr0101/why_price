package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.helper.ApiHelperImpl
import rkrk.whyprice.outputapi.marketfetcher.koreaninvtoken.KoreanInvTokenFetcher

class KoreanInvTokenFetcherTest {
    @Test
    fun fetchTest() {
        // Given
        val koreanTokenFetcher = KoreanInvTokenFetcher(ApiHelperImpl())

        // When
        val koreanToken = koreanTokenFetcher.fetch()

        // Then
        print(koreanToken)
    }
}

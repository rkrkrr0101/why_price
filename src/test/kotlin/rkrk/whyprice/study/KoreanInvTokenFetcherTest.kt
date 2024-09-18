package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.report.adapter.out.api.KoreanInvTokenFetcher
import rkrk.whyprice.share.impl.ApiHelperImpl

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

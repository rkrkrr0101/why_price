package rkrk.whyprice.mock

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import rkrk.whyprice.member.application.port.out.CheckVolatilityPort
import rkrk.whyprice.member.application.port.out.FindKoreanStockByNamePort
import rkrk.whyprice.report.application.port.out.CreateReportPort
import rkrk.whyprice.report.application.port.out.RankFetcher
import rkrk.whyprice.util.TestConstant

@TestConfiguration
class TestConfig {
    @Bean
    fun checkVolatilityPort(): CheckVolatilityPort {
        val customDate = CustomDateTimeMock(TestConstant.TEST_CURRENT_TIME)
        return ResponserMock(customDate)
    }

    @Bean
    fun createReportPort(): CreateReportPort {
        val customDate = CustomDateTimeMock(TestConstant.TEST_CURRENT_TIME)
        return ResponserMock(customDate)
    }

    @Bean
    fun findKoreanStockByNamePort(): FindKoreanStockByNamePort = FindKoreanStockByNameAdapterMock()

    @Bean
    fun rankFetcher(): RankFetcher {
        val ranks: List<String> = TestConstant.TEST_RANK_LIST
        return RankFetcherMock(ranks)
    }
}

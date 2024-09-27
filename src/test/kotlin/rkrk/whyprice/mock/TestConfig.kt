package rkrk.whyprice.mock

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import rkrk.whyprice.member.application.port.out.CheckVolatilityPort
import rkrk.whyprice.member.application.port.out.FindKoreanStockByNamePort
import rkrk.whyprice.report.application.port.out.CreateReportPort

@TestConfiguration
class TestConfig {
    @Bean
    fun checkVolatilityPort(): CheckVolatilityPort {
        val customDate = CustomDateTimeMock("2021-10-10T10:10:10")
        return ResponserMock(customDate)
    }

    @Bean
    fun createReportPort(): CreateReportPort {
        val customDate = CustomDateTimeMock("2021-10-10T10:10:10")
        return ResponserMock(customDate)
    }

    @Bean
    fun findKoreanStockByNamePort(): FindKoreanStockByNamePort = FindKoreanStockByNameAdapterMock()
}

package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.asset.KoreanStock
import rkrk.whyprice.responser.GptResponser

class GptResponserTest {
    @Test
    fun reportCreateTest() {
        // Given
        val stock = KoreanStock("005930", "삼성전자")
        val gptResponser = GptResponser()

        // When
        val report = gptResponser.createReport(stock)
        print("")

        // Then
        print(report.report)
    }
}

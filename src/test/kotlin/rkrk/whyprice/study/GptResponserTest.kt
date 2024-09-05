package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.helper.CustomDateTimeImpl
import rkrk.whyprice.outputapi.responser.GptResponser

class GptResponserTest {
    @Test
    fun reportCreateTest() {
        // Given
        val stock = KoreanStock("005930", "삼성전자")
        val gptResponser = GptResponser(CustomDateTimeImpl())

        // When
        val report = gptResponser.createReport(stock.getAssetName())
        print("")

        // Then
        print(report.getReportBody())
    }
}

package rkrk.whyprice.study

import org.junit.jupiter.api.Test
import rkrk.whyprice.member.domain.KoreanStock
import rkrk.whyprice.share.adapter.GptResponser
import rkrk.whyprice.share.impl.CustomDateTimeImpl

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

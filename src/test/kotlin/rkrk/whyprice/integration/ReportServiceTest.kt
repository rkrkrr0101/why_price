package rkrk.whyprice.integration

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.domain.report.service.ReportService
import rkrk.whyprice.mock.CustomDateTimeMock
import rkrk.whyprice.mock.RankFetcherMock
import rkrk.whyprice.mock.ResponserMock

@SpringBootTest
@Transactional
class ReportServiceTest {
    private val ranks =
        listOf(
            "삼성전자",
            "LG전자",
            "SK하이닉스",
            "네이버",
            "카카오",
            "삼성바이오로직스",
            "셀트리온",
            "현대차",
            "기아",
            "POSCO",
        )
    private val reportService =
        ReportService(
            ResponserMock(CustomDateTimeMock("2021-10-10T10:10:10")),
            RankFetcherMock(ranks),
        )

    @Test
    @DisplayName("높은 거래량의 변동성보고서를 가져올수있다")
    fun fetchHighReports() {
        val reports = reportService.fetchHighReports()

        Assertions.assertThat(reports.size).isEqualTo(10)
        Assertions.assertThat(reports.first { it.getReportBody() == "삼성바이오로직스 report" }).isNotNull
    }

    @Test
    @DisplayName("특정한 한국주식의 변동성보고서를 가져올수있다")
    fun fetchHighReport() {
        val report = reportService.fetchHighReport(KoreanStock("111111-1111111", "비싼주식"))

        Assertions.assertThat(report.getReportBody()).isEqualTo("비싼주식 report")
    }
}

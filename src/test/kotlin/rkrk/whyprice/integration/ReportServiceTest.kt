package rkrk.whyprice.integration

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.domain.report.Report
import rkrk.whyprice.domain.report.repository.ReportCachesJpaRepository
import rkrk.whyprice.domain.report.repository.ReportCachesRepositoryImpl
import rkrk.whyprice.domain.report.service.ReportService
import rkrk.whyprice.mock.CustomDateTimeMock
import rkrk.whyprice.mock.RankFetcherMock
import rkrk.whyprice.mock.ResponserMock

@SpringBootTest
@Transactional
class ReportServiceTest
    @Autowired
    constructor(
        private val reportCachesJpaRepository: ReportCachesJpaRepository,
    ) {
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
        private val timeNow = "2021-10-10T10:10:10"
        private val reportCachesRepository: ReportCachesRepositoryImpl =
            ReportCachesRepositoryImpl(
                reportCachesJpaRepository,
                CustomDateTimeMock(timeNow),
            )
        private val reportService =
            ReportService(
                ResponserMock(CustomDateTimeMock(timeNow)),
                RankFetcherMock(ranks),
                reportCachesRepository,
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

        @Test
        @DisplayName("캐시 보고서가 없으면 새로운 캐시 보고서를 생성한다")
        fun createCacheReport() {
            val stock = KoreanStock("130111-0006246", "삼성전자")

            reportService.fetchHighReport(stock)
            val report = reportCachesRepository.findOne("삼성전자")

            Assertions.assertThat(report.getMainReport().getReportBody()).isEqualTo("삼성전자 report")
        }

        @Test
        @DisplayName("유효한 캐시 보고서가 있으면 캐시보고서를 가져온다")
        fun cacheReportSuccess() {
            reportCachesRepository.saveOrUpdate(
                Report(
                    "삼성전자",
                    "삼성전자 유효캐시 report",
                    CustomDateTimeMock("2021-10-10T10:00:00").getNow(),
                ),
            )
            val stock = KoreanStock("130111-0006246", "삼성전자")

            val report = reportService.fetchHighReport(stock)

            Assertions.assertThat(report.getReportBody()).isEqualTo("삼성전자 유효캐시 report")
            Assertions.assertThat(report.getCreateTime()).isEqualTo(CustomDateTimeMock("2021-10-10T10:00:00").getNow())
        }

        @Test
        @DisplayName("캐시보고서가 만료되면 새로운 보고서를 리턴한다")
        fun expiredCacheReport() {
            reportCachesRepository.saveOrUpdate(
                Report(
                    "삼성전자",
                    "삼성전자 유효캐시 report",
                    CustomDateTimeMock("2021-10-10T09:00:00").getNow(),
                ),
            )
            val stock = KoreanStock("130111-0006246", "삼성전자")

            val report = reportService.fetchHighReport(stock)

            Assertions.assertThat(report.getReportBody()).isEqualTo("삼성전자 report")
            Assertions.assertThat(report.getCreateTime()).isEqualTo(CustomDateTimeMock(timeNow).getNow())
        }

        @Test
        @DisplayName("캐시보고서가 만료되면 기존보고서를 새로운 보고서로 업데이트한다")
        fun expiredUpdateCacheReport() {
            reportCachesRepository.saveOrUpdate(
                Report(
                    "삼성전자",
                    "삼성전자 유효캐시 report",
                    CustomDateTimeMock("2021-10-10T09:00:00").getNow(),
                ),
            )
            val stock = KoreanStock("130111-0006246", "삼성전자")
            reportService.fetchHighReport(stock)

            val reportCache = reportCachesRepository.findOne("삼성전자")

            Assertions.assertThat(reportCache.getMainReport().getReportBody()).isEqualTo("삼성전자 report")
            Assertions.assertThat(reportCache.getCreateTime()).isEqualTo(CustomDateTimeMock(timeNow).getNow())
        }
    }

package rkrk.whyprice.integration.report

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.adapter.out.persistence.FindOrCreateKoreanStockAdapter
import rkrk.whyprice.member.application.port.out.FindOrCreateKoreanStockPort
import rkrk.whyprice.member.application.port.out.KoreanStockRepository
import rkrk.whyprice.mock.CustomDateTimeMock
import rkrk.whyprice.mock.FindKoreanStockByNameAdapterMock
import rkrk.whyprice.mock.RankFetcherMock
import rkrk.whyprice.mock.ResponserMock
import rkrk.whyprice.report.adapter.out.persistence.ReportCachesJpaRepository
import rkrk.whyprice.report.adapter.out.persistence.ReportCachesRepositoryImpl
import rkrk.whyprice.report.application.port.input.dto.req.KoreanStockReportDto
import rkrk.whyprice.report.application.service.CreateReportService
import rkrk.whyprice.report.domain.Report

@SpringBootTest
@Transactional
class CreateReportServiceTest
    @Autowired
    constructor(
        private val reportCachesJpaRepository: ReportCachesJpaRepository,
        private val koreanStockRepository: KoreanStockRepository,
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
        private val findOrCreateKoreanStockAdapter: FindOrCreateKoreanStockPort =
            FindOrCreateKoreanStockAdapter(koreanStockRepository, FindKoreanStockByNameAdapterMock())
        private val createReportService =
            CreateReportService(
                ResponserMock(CustomDateTimeMock(timeNow)),
                RankFetcherMock(ranks),
                reportCachesRepository,
                findOrCreateKoreanStockAdapter,
            )

        @Test
        @DisplayName("높은 거래량의 변동성보고서를 가져올수있다")
        fun fetchHighReports() {
            val reports = createReportService.fetchHighReports()

            Assertions.assertThat(reports.size).isEqualTo(10)
            Assertions.assertThat(reports.first { it.report == "삼성바이오로직스 report" }).isNotNull
        }

        @Test
        @DisplayName("특정한 한국주식의 변동성보고서를 가져올수있다")
        fun fetchHighReport() {
            val report = createReportService.fetchHighReport(KoreanStockReportDto("비싼주식"))

            Assertions.assertThat(report.report).isEqualTo("비싼주식 report")
        }

        @Test
        @DisplayName("캐시 보고서가 없으면 새로운 캐시 보고서를 생성한다")
        fun createCacheReport() {
            val stockDto = KoreanStockReportDto("삼성전자")

            createReportService.fetchHighReport(stockDto)
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
            val stockDto = KoreanStockReportDto("삼성전자")

            val report = createReportService.fetchHighReport(stockDto)

            Assertions.assertThat(report.report).isEqualTo("삼성전자 유효캐시 report")
            Assertions.assertThat(report.reportDate).isEqualTo(CustomDateTimeMock("2021-10-10T10:00:00").getNow())
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
            val stockDto = KoreanStockReportDto("삼성전자")

            val report = createReportService.fetchHighReport(stockDto)

            Assertions.assertThat(report.report).isEqualTo("삼성전자 report")
            Assertions.assertThat(report.reportDate).isEqualTo(CustomDateTimeMock(timeNow).getNow())
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
            val stockDto = KoreanStockReportDto("삼성전자")
            createReportService.fetchHighReport(stockDto)

            val reportCache = reportCachesRepository.findOne("삼성전자")

            Assertions.assertThat(reportCache.getMainReport().getReportBody()).isEqualTo("삼성전자 report")
            Assertions.assertThat(reportCache.getCreateTime()).isEqualTo(CustomDateTimeMock(timeNow).getNow())
        }
    }

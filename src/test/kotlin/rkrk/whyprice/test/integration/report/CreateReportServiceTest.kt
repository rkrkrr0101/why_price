package rkrk.whyprice.test.integration.report

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.report.adapter.out.persistence.hotstockreport.HotStockReport
import rkrk.whyprice.report.adapter.out.persistence.hotstockreport.HotStockReportJpaRepository
import rkrk.whyprice.report.adapter.out.persistence.hotstockreport.HotStockReportRepositoryImpl
import rkrk.whyprice.report.application.service.CreateReportService
import rkrk.whyprice.report.domain.Report
import rkrk.whyprice.tool.TestConstant
import rkrk.whyprice.tool.mock.CustomDateTimeMock


@SpringBootTest
@Transactional
class CreateReportServiceTest
    @Autowired
    constructor(
        private val hotStockReportJpaRepository: HotStockReportJpaRepository,
    ) {
    private val hotStockReportRepository: HotStockReportRepositoryImpl=
        HotStockReportRepositoryImpl(hotStockReportJpaRepository)
        private val createReportService =
            CreateReportService(
                hotStockReportRepository
            )
    @BeforeEach
    fun init(){
        createTestHotStocks()
    }



        @Test
        @DisplayName("높은 거래량의 변동성보고서를 가져올수있다")
        fun getHighReports() {
            runTest {
                val reports = createReportService.getHighReports()
                Assertions.assertThat(reports.size).isEqualTo(10)
                Assertions.assertThat(reports.first { it.report == "1등주식 report" }).isNotNull
            }
        }







    private fun createTestHotStocks() {
        hotStockSave("1등주식")
        hotStockSave("2등주식")
        hotStockSave("3등주식")
        hotStockSave("4등주식")
        hotStockSave("5등주식")
        hotStockSave("6등주식")
        hotStockSave("7등주식")
        hotStockSave("8등주식")
        hotStockSave("9등주식")
        hotStockSave("10등주식")
    }

    private fun hotStockSave(assetName:String){
        hotStockReportJpaRepository.save<HotStockReport>(
            HotStockReport(Report(
                assetName,
                "$assetName report",
                CustomDateTimeMock(TestConstant.TEST_CURRENT_TIME).getNow())
            )
        )
    }


    }

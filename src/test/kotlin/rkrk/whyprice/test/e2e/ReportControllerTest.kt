package rkrk.whyprice.test.e2e

import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.report.adapter.out.persistence.hotstockreport.HotStockReport
import rkrk.whyprice.report.adapter.out.persistence.hotstockreport.HotStockReportJpaRepository
import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto
import rkrk.whyprice.report.domain.Report
import rkrk.whyprice.share.Result
import rkrk.whyprice.tool.TestConstant
import rkrk.whyprice.tool.config.TestConfig
import rkrk.whyprice.tool.mock.CustomDateTimeMock

@SpringBootTest
@Import(TestConfig::class)
@AutoConfigureMockMvc
@Transactional
class ReportControllerTest
    @Autowired
    constructor(
        val mvc: MockMvc,
        private val hotStockReportJpaRepository: HotStockReportJpaRepository
    ) {
    @BeforeEach
    fun init(){
        createTestHotStocks()
    }

        @Test
        @DisplayName("거래량상위주식의 레포트를 출력할수있다")
        fun getHighStockReport() {
            runTest {
                    mvc
                        .perform(
                            MockMvcRequestBuilders
                                .get("/api/report/stock/high")
                                .contentType(MediaType.APPLICATION_JSON),
                        ).andExpect(status().isOk)
                        .andExpect(jsonPath("$.data", hasSize<Any>(10)))
                        .andExpect(jsonPath("$.data[*].report", hasItem("1등주식 report")))

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

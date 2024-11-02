package rkrk.whyprice.test.e2e

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.hasSize
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
import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto
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
    ) {
        val om = ObjectMapper()

        @Test
        @DisplayName("특정주식의 레포트를 출력할수있다")
        fun getStockReport() {
            runTest {
                val mvcResult =
                    mvc
                        .perform(
                            MockMvcRequestBuilders
                                .get("/api/report/stock?stockName=삼성전자")
                                .contentType(MediaType.APPLICATION_JSON),
                        ).andExpect(status().isOk)
                        .andExpect(request().asyncStarted())
                        .andExpect(
                            request()
                                .asyncResult(
                                    Result(
                                        ResponseReportDto(
                                            "삼성전자 report",
                                            CustomDateTimeMock(TestConstant.TEST_CURRENT_TIME).getNow(),
                                        ),
                                    ),
                                ),
                        ).andReturn()
            }
        }

        @Test
        @DisplayName("거래량상위주식의 레포트를 출력할수있다")
        fun getHighStockReport() {
            runTest {
                val mvcResult =
                    mvc
                        .perform(
                            MockMvcRequestBuilders
                                .get("/api/report/stock/high")
                                .contentType(MediaType.APPLICATION_JSON),
                        ).andExpect(request().asyncStarted())
                        .andReturn()

                mvc
                    .perform(asyncDispatch(mvcResult))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.data", hasSize<Any>(10)))
                    .andExpect(jsonPath("$.data[*].report", hasItem("LG전자 report")))
            }
        }
    }

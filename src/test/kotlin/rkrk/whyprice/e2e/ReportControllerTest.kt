package rkrk.whyprice.e2e

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.mock.TestConfig
import rkrk.whyprice.util.ParsingUtil
import rkrk.whyprice.util.TestConstant

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
            val mvcResult =
                mvc
                    .perform(
                        MockMvcRequestBuilders
                            .get("/api/report/stock?stockName=삼성전자")
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andExpect(status().isOk)
                    .andReturn()

            val body = ParsingUtil.toMap(mvcResult, om)

            Assertions.assertThat(body.size).isEqualTo(2)
            Assertions.assertThat(body["report"]).isEqualTo("삼성전자 report")
            Assertions.assertThat(body["reportDate"]).isEqualTo(TestConstant.TEST_CURRENT_TIME)
        }

        @Test
        @DisplayName("거래량상위주식의 레포트를 출력할수있다")
        fun getHighStockReport() {
            val mvcResult =
                mvc
                    .perform(
                        MockMvcRequestBuilders
                            .get("/api/report/stock/high")
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andExpect(status().isOk)
                    .andReturn()

            val body = ParsingUtil.toListMap(mvcResult, om)

            Assertions.assertThat(body.size).isEqualTo(10)
            Assertions
                .assertThat(
                    body.contains(
                        hashMapOf(
                            Pair("report", "삼성전자 report"),
                            Pair("reportDate", TestConstant.TEST_CURRENT_TIME),
                        ),
                    ),
                ).isTrue()
        }
    }

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
import rkrk.whyprice.member.application.port.out.KoreanStockRepository
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.mock.TestConfig
import rkrk.whyprice.util.InitUtil
import rkrk.whyprice.util.ParsingUtil
import java.nio.charset.StandardCharsets

@SpringBootTest
@Import(TestConfig::class)
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest
    @Autowired
    constructor(
        val mvc: MockMvc,
        val memberRepository: MemberRepository,
        val koreanStockRepository: KoreanStockRepository,
    ) {
        val om = ObjectMapper()

        @Test
        @DisplayName("멤버를 생성할수 있다")
        fun createMember() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)
            val jsonMap = hashMapOf<String, String>()
            jsonMap["name"] = "testMember"
            val reqBodyJson = om.writeValueAsString(jsonMap)

            mvc
                .perform(
                    MockMvcRequestBuilders
                        .post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBodyJson),
                ).andExpect(status().isOk)
        }

        @Test
        @DisplayName("멤버에 한국주식을 추가할수있다")
        fun addKoreanStockToMember() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)
            val jsonMap = hashMapOf<String, String>()
            jsonMap["memberName"] = "member1"
            jsonMap["stockName"] = "삼양"
            val reqBodyJson = om.writeValueAsString(jsonMap)

            mvc
                .perform(
                    MockMvcRequestBuilders
                        .post("/api/member/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBodyJson),
                ).andExpect(status().isOk)
        }

        @Test
        @DisplayName("멤버에 한국주식을 추가시 동일한 한국주식이 있다면 예외가 발생한다")
        fun duplicateExceptionAddKoreanStockToMember() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)
            val jsonMap = hashMapOf<String, String>()
            jsonMap["memberName"] = "member1"
            jsonMap["stockName"] = "삼성전자"
            val reqBodyJson = om.writeValueAsString(jsonMap)

            mvc
                .perform(
                    MockMvcRequestBuilders
                        .post("/api/member/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBodyJson),
                ).andExpect(status().is4xxClientError)
        }

        @Test
        @DisplayName("멤버에 한국주식을 삭제할수있다")
        fun deleteKoreanStockToMember() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)
            val jsonMap = hashMapOf<String, String>()
            jsonMap["memberName"] = "member1"
            jsonMap["stockName"] = "삼성전자"
            val reqBodyJson = om.writeValueAsString(jsonMap)

            mvc
                .perform(
                    MockMvcRequestBuilders
                        .delete("/api/member/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBodyJson),
                ).andExpect(status().isOk)
        }

        @Test
        @DisplayName("멤버에 한국주식을 삭제시 해당주식을 멤버가 가지고있지않다면 예외가 발생한다")
        fun deleteNonExistsExceptionKoreanStockToMember() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)
            val jsonMap = hashMapOf<String, String>()
            jsonMap["memberName"] = "member1"
            jsonMap["stockName"] = "없는주식"
            val reqBodyJson = om.writeValueAsString(jsonMap)

            mvc
                .perform(
                    MockMvcRequestBuilders
                        .delete("/api/member/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBodyJson),
                ).andExpect(status().is4xxClientError)
        }

        @Test
        @DisplayName("멤버에 기록된 한국주식을 조회할수있다")
        fun getKoreanStockToMember() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)

            val mvcResult =
                mvc
                    .perform(
                        MockMvcRequestBuilders
                            .get("/api/member/stock?memberName=member1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8),
                    ).andExpect(status().isOk)
                    .andReturn()
            val body = ParsingUtil.toListMap(mvcResult, om)

            Assertions.assertThat(body.size).isEqualTo(2)
            Assertions.assertThat(body.filter { it["assetName"] == "삼성전자" }.size).isEqualTo(1)
        }

        @Test
        @DisplayName("멤버에 기록된 한국주식의 변동성을 조회할수있다")
        fun volatilityKoreanStockToMember() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)

            val mvcResult =
                mvc
                    .perform(
                        MockMvcRequestBuilders
                            .get("/api/member/stock/volatility?memberName=member1")
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andExpect(status().isOk)
                    .andReturn()

            val body = ParsingUtil.toListMap(mvcResult, om)

            Assertions.assertThat(body.size).isEqualTo(1)
            Assertions.assertThat(body.filter { it["assetName"] == "삼성전자" }.size).isEqualTo(1)
        }
    }

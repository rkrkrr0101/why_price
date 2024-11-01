package rkrk.whyprice.share.adapter.responser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.stereotype.Component
import rkrk.whyprice.config.ApiConfig
import rkrk.whyprice.member.application.port.out.CheckVolatilityPort
import rkrk.whyprice.member.domain.Asset
import rkrk.whyprice.report.application.port.out.CreateReportPort
import rkrk.whyprice.report.domain.Report
import rkrk.whyprice.share.adapter.responser.infrastructure.PerplexityApi
import rkrk.whyprice.share.adapter.responser.infrastructure.PerplexityChatOptions
import rkrk.whyprice.share.port.CustomDateTime

@Component
class GptResponser(
    private val customDateTime: CustomDateTime,
) : CheckVolatilityPort,
    CreateReportPort {
    private val perplexityOptions =
        PerplexityChatOptions
            .builder()
            .searchRecencyFilter("week")
            .withModel("llama-3.1-sonar-small-128k-online")
            // .withModel("llama-3.1-sonar-large-128k-online")
            // .withModel("llama-3.1-sonar-huge-128k-online")
            .withTemperature(0.8F)
            .build()

    private val chatClient: ChatClient =
        ChatClient
            .builder(
                OpenAiChatModel(PerplexityApi(ApiConfig.getPerplexityKey()), perplexityOptions),
            ).build()

    override fun hasVolatility(
        asset: Asset,
        volatilityTime: Int,
    ): Boolean {
        val response = fetch(createVolatilityPrompt(asset.getAssetName(), volatilityTime))
        return response.result.output.content
            .contains("o")
    }

    override suspend fun createReport(
        assetName: String,
        volatilityTime: Int,
    ): Report =
        withContext(Dispatchers.IO) {
            val response = fetchAsync(createReportPrompt(assetName, volatilityTime))

            responseToReport(assetName, response)
        }

    private fun fetch(prompt: Prompt): ChatResponse {
        val response =
            chatClient
                .prompt(prompt)
                .call()
                .chatResponse()
        return response
    }

    private suspend fun fetchAsync(prompt: Prompt): ChatResponse =
        withContext(Dispatchers.IO) {
            val response =
                chatClient
                    .prompt(prompt)
                    .call()
                    .chatResponse()
            response
        }

    // todo 프롬프트에 검색사이트 명시 및 최신 사이트만 검색하게 강제
    // TKG애강 주가 뉴스 inanchor:2024-09-06 inanchor:2024-09-05 inanchor:2024-09-04
    private fun createReportPrompt(
        assetName: String,
        volatilityTime: Int,
    ): Prompt {
        val systemText = "너는 20년 경력의 금융시장 전문가야"
        val systemMessage = SystemMessage(systemText)
        // val userTest = """${assetName}의 현재가격과 최근 ${volatilityTime}시간동안의 변동성과 변동성의 이유를 검색하고 평가해서 레포트로 써줘"""
        val userTest = createReportCotPrompt(assetName, volatilityTime)
        val userMessage = UserMessage(userTest)
        return Prompt(listOf(systemMessage, userMessage))
    }

    private fun createVolatilityPrompt(
        assetName: String,
        volatilityTime: Int,
    ): Prompt {
        val systemText = "당신은 20년 경력의 금융시장 전문가입니다"
        val systemMessage = SystemMessage(systemText)
        val userTest = """${assetName}의 현재가격과 최근 ${volatilityTime}시간동안의 변동성과 변동성의 이유를 검색하고 평가해서 o나 x로만 말해줘"""
        userTest.plus(" 변동성이 크면 o,작으면 x로 말하면돼")

        val userMessage = UserMessage(userTest)
        return Prompt(listOf(systemMessage, userMessage))
    }

    private fun responseToReport(
        assetName: String,
        response: ChatResponse,
    ) = Report(assetName, response.result.output.content, customDateTime.getNow())

    private fun createReportCotPrompt(
        assetName: String,
        volatilityTime: Int,
    ): String {
        val builder = StringBuilder()
        builder.append("""${assetName}의 최근 ${volatilityTime}시간동안의 변동성과 변동성의 이유에 대한 상세 레포트를 작성해주세요""")
        builder.append("다음 단계에 따라 체계적으로 분석해주길 바랍니다")
        builder.append("1.주가 동향: 오늘의 시가, 고가, 저가, 현재가를 제시하고 전일 대비 변동폭과 변동률을 분석해주세요.")
        builder.append("2.거래량 분석: 오늘의 거래량을 전일 및 최근 5일 평균과 비교 분석해주세요.")
        builder.append(
            "3.시장 환경:\n" +
                "3-1.오늘의 코스피 지수 동향과 $assetName 주가와의 연관성을 설명해주세요.\n" +
                "3-2.동종 업계 다른 기업들의 주가 동향과 비교 분석해주세요.",
        )
        builder.append(
            "4.뉴스 및 이벤트:\n" +
                "4-1.오늘 ${assetName}와 관련된 주요 뉴스나 이벤트가 있었는지 확인하고 영향을 분석해주세요.\n" +
                "4-2.산업 전반에 영향을 줄 수 있는 거시경제 뉴스가 있었는지 확인해주세요.",
        )
        builder.append("5.투자자 동향: 오늘의 기관, 외국인, 개인 투자자별 순매수 동향을 분석해주세요.")
        builder.append(
            "6.기술적 분석:\n" +
                "6-1.당일 주가 차트의 주요 기술적 지표(이동평균선, RSI 등)를 해석해주세요.\n" +
                "6-2.최근의 추세선과 오늘의 변동이 어떤 의미를 갖는지 설명해주세요.",
        )
        builder.append("7.변동성 요인 종합: 위의 분석을 종합하여 오늘의 높은 변동성의 주요 원인을 제시해주세요.")
        builder.append("8.단기 전망: 오늘의 변동성을 고려할 때, 향후 수일간의 주가 흐름에 대한 전망을 제시해주세요.")
        builder.append("각 단계에서 관련 데이터와 근거를 제시하고, 논리적인 추론 과정을 보여주세요. 또한, 각 요소가 오늘의 주가 변동성에 어떻게 영향을 미쳤는지 설명해주시기 바랍니다.")
        return builder.toString()
    }
}

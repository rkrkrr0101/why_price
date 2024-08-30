package rkrk.whyprice.responser

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.ai.openai.api.OpenAiApi
import rkrk.whyprice.config.ApiConfig
import rkrk.whyprice.report.Report
import rkrk.whyprice.share.Asset
import rkrk.whyprice.share.RankFetcher
import rkrk.whyprice.share.Responser
import java.time.LocalDateTime

class GptResponser : Responser {
    private val option: OpenAiChatOptions =
        OpenAiChatOptions
            .builder()
            .withModel("gpt-4o-mini")
            .withTemperature(0.8F)
            .build()
    private val chatClient: ChatClient =
        ChatClient.builder(OpenAiChatModel(OpenAiApi(ApiConfig.getGptKey()), option)).build()

    override fun hasVolatility(asset: Asset): Boolean {
        // GPT API를 이용하여 해당 자산의 변동성을 예측하는 기능을 구현
        TODO("Not yet implemented")
    }

    override fun createReport(rankFetcher: RankFetcher): List<Report> {
        val rankList = rankFetcher.fetch()
        val resList = mutableListOf<Report>()

        for (assetName in rankList) {
            val response = fetch(assetName)
            val report = responseToReport(response)
            resList.add(report)
        }
        return resList
    }

    override fun createReport(asset: Asset): Report {
        val response = fetch(asset.getAssetName())

        return responseToReport(response)
    }

    private fun fetch(
        assetName: String,
        volatilityTime: Int = 1,
    ): ChatResponse {
        val response =
            chatClient
                .prompt(createPrompt(assetName, volatilityTime))
                .call()
                .chatResponse()
        return response
    }

    private fun createPrompt(
        assetName: String,
        volatilityTime: Int,
    ): Prompt {
        val systemText = "너는 20년 경력의 금융시장 전문가야"
        val systemMessage = SystemMessage(systemText)
        val userTest = """${assetName}의 현재가격과 최근 ${volatilityTime}시간동안의 변동성과 변동성의 이유를 검색하고 평가해서 레포트로 써줘"""
        val userMessage = UserMessage(userTest)
        return Prompt(listOf(systemMessage, userMessage))
    }

    private fun responseToReport(response: ChatResponse) = Report(response.result.output.content, LocalDateTime.now())
}

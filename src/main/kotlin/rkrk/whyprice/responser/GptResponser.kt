package rkrk.whyprice.responser

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.ai.openai.api.OpenAiApi
import rkrk.whyprice.config.ApiConfig
import rkrk.whyprice.report.Report
import rkrk.whyprice.share.Responser
import rkrk.whyprice.trackedAssets.Asset
import java.time.LocalDateTime

class GptResponser : Responser {
    override fun hasVolatility(asset: Asset): Boolean {
        // GPT API를 이용하여 해당 자산의 변동성을 예측하는 기능을 구현
        TODO("Not yet implemented")
    }

    override fun createReport(): List<Report> {
        // GPT-3 API를 이용하여 모든 자산의 변동성으로 레포트를 쓰는 기능을 구현
        // 이때, 변동성이 높은 순서대로 레포트를 쓰도록 한다.
        // 레포트는 List<Report>로 반환한다.

        TODO("Not yet implemented")
    }

    override fun createReport(asset: Asset): Report {
        val option =
            OpenAiChatOptions
                .builder()
                .withModel("gpt-4o-mini")
                .withTemperature(0.8F)
                .build()

        val response =
            ChatClient
                .builder(OpenAiChatModel(OpenAiApi(ApiConfig.getGptKey()), option))
                .defaultSystem("너는 20년 경력의 금융시장 전문가야")
                // .defaultUser("""${asset.getIdentityCode()}의 최근 6시간동안의 변동성의 이유를 평가해서 레포트로 써줘""")
                .defaultUser("""라이프시맨틱스의 현재가격과 최근 2일동안의 변동성과 변동성의 이유를 검색하고 평가해서 레포트로 써줘""")
                .build()
                .prompt()
                .call()
                .chatResponse()
        print(response.result.output.content)
        return Report(asset, response.result.output.content, LocalDateTime.now())
    }
}

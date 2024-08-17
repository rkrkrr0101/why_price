package rkrk.whyprice.assetfetcher.apifetcher

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import rkrk.whyprice.asset.AssetFetcher
import java.net.URI
import java.time.Duration

abstract class ApiFetcher : AssetFetcher {
    override fun fetch(isinCode: String): Map<String, String> {
        val restTemplate = createRestTemplate()

        val url = buildUrl(isinCode)

        return apiCall(restTemplate, url, isinCode)
    }

    protected abstract fun buildUrl(isinCode: String): URI

    private fun createRestTemplate(): RestTemplate {
        val restTemplate =
            RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(3))
                .build()
        return restTemplate
    }

    protected open fun apiCall(
        restTemplate: RestTemplate,
        url: URI,
        isinCode: String,
    ): Map<String, String> {
        val log = LoggerFactory.getLogger(this.javaClass)
        try {
            val response =
                restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    String::class.java,
                )
            if (response.statusCode.value() != 200) {
                log.warn("BasicFetcher 상태코드이상 상태코드={}", response.statusCode.value())
                throw RuntimeException("${javaClass.name}BasicFetcher http상태코드가 200이 아님")
            }
            log.info("성공 메시지={}", isinCode)
            val om = ObjectMapper()
            val responseBody: Map<String, String> = om.readValue(response.body, Map::class.java) as Map<String, String>

            return responseBody
        } catch (e: Exception) {
            log.warn("BasicFetcher통신에 문제발생 스택트레이스={}", e.stackTrace)
            return emptyMap()
        }
    }
}

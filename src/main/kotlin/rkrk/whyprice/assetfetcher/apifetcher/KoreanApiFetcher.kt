package rkrk.whyprice.assetfetcher.apifetcher

import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import rkrk.whyprice.asset.AssetFetcher
import rkrk.whyprice.config.ApiConfig
import java.net.URI
import java.time.Duration

abstract class KoreanApiFetcher : AssetFetcher {
    override fun fetch(crNo: String): Map<String, String> {
        val restTemplate = createRestTemplate()

        val url = buildUrl(crNo)

        return apiCall(restTemplate, url, crNo)
    }

    private fun buildUrl(crNo: String): URI {
        val baseUrl = getBaseUrl() // url
        val serviceKey = ApiConfig.getOpenApiKey()
        val url =
            UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParams(createQueryParams(crNo, serviceKey))
                .build(true)
                .toUri()
        return url
    }

    private fun apiCall(
        restTemplate: RestTemplate,
        url: URI,
        crNo: String,
    ): Map<String, String> {
        val log = LoggerFactory.getLogger(this.javaClass)
        try {
            val response = fetchApiResponse(restTemplate, url)

            responseErrorCheck(response)
            log.info("성공 법인코드={}", crNo)

            return extractResponseAsMap(response)
        } catch (e: Exception) {
            log.warn("${javaClass.name}통신에 문제발생 메시지={} 스택트레이스={}", e.message, e.stackTrace)
            return emptyMap()
        }
    }

    protected abstract fun getBaseUrl(): String

    protected abstract fun createQueryParams(
        crNo: String,
        serviceKey: String,
    ): MultiValueMap<String, String>

    protected abstract fun responseErrorCheck(response: ResponseEntity<String>)

    protected open fun fetchApiResponse(
        restTemplate: RestTemplate,
        url: URI,
    ): ResponseEntity<String> {
        val response =
            restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String::class.java,
            )
        return response
    }

    protected abstract fun extractResponseAsMap(response: ResponseEntity<String>): Map<String, String>

    private fun createRestTemplate(): RestTemplate {
        val restTemplate =
            RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(3))
                .build()
        return restTemplate
    }
}

package rkrk.whyprice.assetfetcher.apifetcher

import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import rkrk.whyprice.asset.AssetFetcher
import rkrk.whyprice.config.ApiConfig
import rkrk.whyprice.share.ApiUtil
import java.net.URI

abstract class KoreanApiFetcher(
    private val apiUtil: ApiUtil,
) : AssetFetcher {
    override fun fetch(crNo: String): Map<String, String> {
        val restTemplate = apiUtil.createRestTemplate()

        val url = apiUtil.buildUrl(getBaseUrl(), createQueryParams(crNo, ApiConfig.getOpenApiKey()))

        return apiCall(restTemplate, url, crNo)
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
            apiUtil.fetchApiResponse(
                restTemplate,
                url,
                HttpMethod.GET,
                null,
            )
        return response
    }

    protected abstract fun extractResponseAsMap(response: ResponseEntity<String>): Map<String, String>
}

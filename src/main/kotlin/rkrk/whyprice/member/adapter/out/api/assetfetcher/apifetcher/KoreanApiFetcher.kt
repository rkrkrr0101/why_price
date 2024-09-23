package rkrk.whyprice.member.adapter.out.api.assetfetcher.apifetcher

import org.apache.catalina.util.URLEncoder
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import rkrk.whyprice.config.ApiConfig
import rkrk.whyprice.member.adapter.out.api.assetfetcher.AssetFetcher
import rkrk.whyprice.share.port.ApiHelper
import java.net.URI
import java.nio.charset.Charset

abstract class KoreanApiFetcher(
    private val apiHelper: ApiHelper,
) : AssetFetcher {
    override fun fetch(queryKey: String): Map<String, String> {
        val restTemplate = apiHelper.createRestTemplate()

        val queryParams = createQueryParams(queryKey, ApiConfig.getOpenApiKey())
        val specialEncode = specialEncode(queryParams)

        val url = apiHelper.buildUrl(getBaseUrl(), specialEncode, true)

        return apiCall(restTemplate, url, queryKey)
    }

    private fun apiCall(
        restTemplate: RestTemplate,
        url: URI,
        queryKey: String,
    ): Map<String, String> {
        val log = LoggerFactory.getLogger(this.javaClass)
        try {
            val response = fetchApiResponse(restTemplate, url)

            responseErrorCheck(response)
            log.info("성공 검색키={}", queryKey)

            return extractResponseAsMap(response)
        } catch (e: Exception) {
            log.warn("${javaClass.name}통신에 문제발생 메시지={} 스택트레이스={}", e.message, e.stackTrace)
            return emptyMap()
        }
    }

    // 오픈api용 인코딩,/나 +같은걸 한글과 같이 인코딩하기위해 필요
    private fun specialEncode(queryParams: MultiValueMap<String, String>?): MultiValueMap<String, String> {
        val encodeQueryParams = LinkedMultiValueMap<String, String>()
        val urlEncoder = URLEncoder()
        if (queryParams != null) {
            for ((key, valueList) in queryParams) {
                val resValueList = mutableListOf<String>()
                for (value in valueList) {
                    resValueList.add(urlEncoder.encode(value, Charset.forName("UTF-8")))
                }
                encodeQueryParams.addAll(key, resValueList)
            }
        }
        return encodeQueryParams
    }

    protected abstract fun getBaseUrl(): String

    protected abstract fun createQueryParams(
        queryKey: String,
        serviceKey: String,
    ): MultiValueMap<String, String>

    protected abstract fun responseErrorCheck(response: ResponseEntity<String>)

    protected open fun fetchApiResponse(
        restTemplate: RestTemplate,
        url: URI,
    ): ResponseEntity<String> {
        val response =
            apiHelper.fetchApiResponse(
                restTemplate,
                url,
                HttpMethod.GET,
                null,
            )
        return response
    }

    protected abstract fun extractResponseAsMap(response: ResponseEntity<String>): Map<String, String>
}

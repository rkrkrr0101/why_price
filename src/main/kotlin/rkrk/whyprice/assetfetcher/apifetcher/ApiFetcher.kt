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

abstract class ApiFetcher : AssetFetcher {
    override fun fetch(isinCode: String): Map<String, String> {
        val restTemplate = createRestTemplate()

        val url = buildUrl(isinCode)

        return apiCall(restTemplate, url, isinCode)
    }

    private fun buildUrl(isinCode: String): URI {
        val baseUrl = getBaseUrl() // url
        val serviceKey = ApiConfig.getApiKey()
        val url =
            UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParams(createQueryParams(isinCode, serviceKey))
                .build(true)
                .toUri()
        return url
    }

    protected abstract fun getBaseUrl(): String

    protected abstract fun createQueryParams(
        isinCode: String,
        serviceKey: String,
    ): MultiValueMap<String, String>

    private fun apiCall(
        restTemplate: RestTemplate,
        url: URI,
        isinCode: String,
    ): Map<String, String> {
        val log = LoggerFactory.getLogger(this.javaClass)
        try {
            val response = fetchApiResponse(restTemplate, url)

            responseErrorCheck(response)
            log.info("성공 메시지={}", isinCode)

            return extractResponseAsMap(response)
        } catch (e: Exception) {
            log.warn("BasicFetcher통신에 문제발생 스택트레이스={}", e.stackTrace)
            return emptyMap()
        }
    }

    //           todo 에러코드가 0이 아닐때 처리
    // 컨텐츠타입이 xml이면 에러
    // response.headers.contentType== MediaType.TEXT_XML
    // 이후 에러메시지와 에러코드 담아서 로깅
//           todo 결과값의 갯수가 0일때 처리
//           todo http코드 예외발생시 처리
    private fun responseErrorCheck(response: ResponseEntity<String>) {
        val log = LoggerFactory.getLogger(this.javaClass)
        if (response.statusCode.value() != 200) {
            log.warn("BasicFetcher 상태코드이상 상태코드={}", response.statusCode.value())
            throw RuntimeException("${javaClass.name}BasicFetcher http상태코드가 200이 아님")
        }
    }

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

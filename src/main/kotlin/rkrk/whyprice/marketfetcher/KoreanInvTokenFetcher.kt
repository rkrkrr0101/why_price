package rkrk.whyprice.marketfetcher

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import rkrk.whyprice.config.ApiConfig
import rkrk.whyprice.util.ApiUtil

class KoreanInvTokenFetcher(
    private val apiUtil: ApiUtil,
) {
    fun fetch(): String {
        val restTemplate = apiUtil.createRestTemplate()
        val url = apiUtil.buildUrl(getBaseUrl(), null, false)
        val response = apiUtil.fetchApiResponse(restTemplate, url, HttpMethod.POST, createHttpEntity())
        return response.body!!
    }

    private fun getBaseUrl(): String = "https://openapi.koreainvestment.com:9443/oauth2/tokenP"

    private fun createHttpEntity(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.set("content-type", "application/x-www-form-urlencoded")
        return HttpEntity(createRequestBody(), headers)
    }

    private fun createRequestBody(): String {
        val requestBody =
            mapOf(
                "grant_type" to "client_credentials",
                "appkey" to ApiConfig.getKoreaInvKey(),
                "appsecret" to ApiConfig.getKoreaSecretKey(),
            )
        return jacksonObjectMapper().writeValueAsString(requestBody)
    }
}

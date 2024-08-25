package rkrk.whyprice.util

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URI

interface ApiUtil {
    fun createRestTemplate(
        connectTimeout: Long = 3,
        readTimeout: Long = 3,
    ): RestTemplate

    fun buildUrl(
        baseUrl: String,
        queryParams: MultiValueMap<String, String>?,
        encodeType: Boolean = true,
    ): URI

    fun fetchApiResponse( // 리팩터링
        restTemplate: RestTemplate,
        url: URI,
        httpMethod: HttpMethod,
        httpEntity: HttpEntity<*>?,
    ): ResponseEntity<String>
}

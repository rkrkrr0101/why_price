package rkrk.whyprice.helper

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import rkrk.whyprice.share.ApiHelper
import java.net.URI
import java.time.Duration

@Component
class ApiHelperImpl : ApiHelper {
    override fun createRestTemplate(
        connectTimeout: Long,
        readTimeout: Long,
    ): RestTemplate {
        val restTemplate =
            RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(connectTimeout))
                .setReadTimeout(Duration.ofSeconds(readTimeout))
                .build()
        return restTemplate
    }

    override fun buildUrl(
        baseUrl: String,
        queryParams: MultiValueMap<String, String>?,
        encodeType: Boolean,
    ): URI {
        val url =
            UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParams(queryParams)
                .build(encodeType)
                .toUri()
        return url
    }

    override fun fetchApiResponse(
        restTemplate: RestTemplate,
        url: URI,
        httpMethod: HttpMethod,
        httpEntity: HttpEntity<*>?,
    ): ResponseEntity<String> {
        val response =
            restTemplate.exchange(
                url,
                httpMethod,
                httpEntity,
                String::class.java,
            )
        return response
    }

    override fun extractNodeValue(
        node: JsonNode,
        key: String,
    ): String {
        try {
            return node.get(key).asText()
        } catch (e: Exception) {
            throw NoSuchElementException("${javaClass.name}에서 해당하는 노드의 키값이 없음 키:$key ")
        }
    }
}

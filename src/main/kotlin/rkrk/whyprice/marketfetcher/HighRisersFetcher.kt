package rkrk.whyprice.marketfetcher

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import rkrk.whyprice.config.ApiConfig
import java.net.URI
import java.time.Duration

class HighRisersFetcher : RankFetcher {
    override fun fetch(): List<String> {
        val restTemplate = createRestTemplate()
        val url = buildUrl()
        val httpEntity = createHttpEntity()
        val response = fetchApiResponse(restTemplate, url, httpEntity)
        print(response)
        return extractResponseAsList(response)
        // return response.body.lines()
    }

    private fun createRestTemplate(): RestTemplate { // 리팩터링 합성+di로 처리?
        val restTemplate =
            RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(3))
                .build()
        return restTemplate
    }

    private fun buildUrl(): URI { // 리팩터링
        val baseUrl = getBaseUrl() // url
        val url =
            UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParams(createQueryParams())
                .build(true)
                .toUri()
        return url
    }

    private fun fetchApiResponse( // 리팩터링
        restTemplate: RestTemplate,
        url: URI,
        httpEntity: HttpEntity<String>,
    ): ResponseEntity<String> {
        val response =
            restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String::class.java,
            )
        return response
    }

    private fun createHttpEntity(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.set("content-type", "application/json; charset=utf-8")
        headers.set("appkey", ApiConfig.getKoreaInvKey())
        headers.set("appsecret", ApiConfig.getKoreaSecretKey())
        headers.set("tr_id", "FHPST01700000")
        headers.set("custtype", "P")

        return HttpEntity<String>("", headers)
    }

    private fun getBaseUrl(): String = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/ranking/fluctuation"

    private fun createQueryParams(): MultiValueMap<String, String> {
        val resMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        resMap["fid_cond_mrkt_div_code"] = "J"
        resMap["fid_cond_scr_div_code"] = "20170"
        resMap["fid_input_iscd"] = "0000"
        resMap["fid_rank_sort_cls_code"] = "0"
        resMap["fid_input_cnt_1"] = "0"
        resMap["fid_prc_cls_code"] = "1"
        resMap["fid_trgt_cls_code"] = "0"
        resMap["fid_trgt_exls_cls_code"] = "0"
        resMap["fid_div_cls_code"] = "0"
        return resMap
    }

    fun extractResponseAsList(response: ResponseEntity<String>): List<String> {
        val itemNode = extractNodeList(response)
        val resList = mutableListOf<String>()
        for (node in itemNode) {
            resList.add(extractNodeValue(node, "hts_kor_isnm"))
        }
        return resList
    }

    private fun extractNodeList(response: ResponseEntity<String>): List<JsonNode> {
        val om = jacksonObjectMapper()
        val readTree = om.readTree(response.body)
        try {
            return readTree
                .get("output")
                .toList()
        } catch (e: IndexOutOfBoundsException) {
            throw NoSuchElementException("${javaClass.name}가 노드 추출에 실패함 ${response.headers.eTag}")
        }
    }

    private fun extractNodeValue(
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

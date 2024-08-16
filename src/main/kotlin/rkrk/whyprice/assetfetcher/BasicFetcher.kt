package rkrk.whyprice.assetfetcher

import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.util.UriComponentsBuilder
import rkrk.whyprice.asset.AssetFetcher
import java.time.Duration

class BasicFetcher : AssetFetcher {
    private val log = LoggerFactory.getLogger(this.javaClass)

    // 이거 추상메서드로 뽑아도되지않나? 근데 포스트랑 겟 구분어케하지
    override fun fetch(isinCode: String): Map<String, String> {
        val restTemplate =
            RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(3))
                .build()

        val baseUrl = "https://apis.data.go.kr/1160100/service/GetKrxListedInfoService/getItemInfo" // url
        val url =
            UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParam("resultType", "json")
                .queryParam("serviceKey", "adadadada")
                .queryParam("isinCd", isinCode)
                .build()
                .toUri()

        try {
            val response =
                restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    pMapTypeRef<String, String>(),
                )
            if (response.statusCode.value() != 200) {
                log.warn("슬랙통신 상태코드이상 상태코드={}", response.statusCode.value())
                throw RuntimeException("슬랙 response http상태코드가 200이 아님")
            }
            log.info("성공 메시지={}", isinCode)

            return response.body!!
        } catch (e: Exception) {
            log.warn("슬랙통신에 문제발생 스택트레이스={}", e.stackTrace)
        }
    }

    private inline fun <reified T, reified K> pMapTypeRef(): ParameterizedTypeReference<Map<T, K>> =
        object : ParameterizedTypeReference<Map<T, K>>() {}
}

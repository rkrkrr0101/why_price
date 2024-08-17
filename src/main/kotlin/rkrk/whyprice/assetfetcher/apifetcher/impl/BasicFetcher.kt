package rkrk.whyprice.assetfetcher.apifetcher.impl

import org.springframework.web.util.UriComponentsBuilder
import rkrk.whyprice.assetfetcher.apifetcher.ApiFetcher
import rkrk.whyprice.config.ApiConfig
import java.net.URI

class BasicFetcher : ApiFetcher() {
    override fun buildUrl(isinCode: String): URI {
        val baseUrl = "https://apis.data.go.kr/1160100/service/GetKrxListedInfoService/getItemInfo" // url
        val serviceKey = ApiConfig.getApiKey()
        val url =
            UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParam("resultType", "json")
                .queryParam("serviceKey", serviceKey)
                .queryParam("isinCd", isinCode)
                .build()
                .toUri()
        return url
    }
}

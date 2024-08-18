package rkrk.whyprice.assetfetcher.apifetcher.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import rkrk.whyprice.assetfetcher.apifetcher.ApiFetcher

class BasicFetcher : ApiFetcher() {
    override fun getBaseUrl(): String = "https://apis.data.go.kr/1160100/service/GetKrxListedInfoService/getItemInfo"

    override fun createQueryParams(
        isinCode: String,
        serviceKey: String,
    ): MultiValueMap<String, String> {
        val resMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        resMap["resultType"] = "json"
        resMap["numOfRows"] = "1"
        resMap["serviceKey"] = serviceKey
        resMap["isinCd"] = isinCode
        return resMap
    }

    override fun extractResponseAsMap(response: ResponseEntity<String>): Map<String, String> {
        val om = jacksonObjectMapper()
        val readTree = om.readTree(response.body)
        val resMap = HashMap<String, String>()

        val itemNodes =
            readTree
                .get("response")
                .get("body")
                .get("items")
                .get("item")
                .toList()[0]

        resMap["assetName"] = itemNodes.get("itmsNm").asText()

        return resMap
    }
}

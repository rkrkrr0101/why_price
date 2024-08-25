package rkrk.whyprice.assetfetcher.apifetcher.impl

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import rkrk.whyprice.assetfetcher.apifetcher.KoreanApiFetcher
import rkrk.whyprice.util.ApiUtil
import javax.xml.parsers.DocumentBuilderFactory

class BasicFetcher(
    apiUtil: ApiUtil,
) : KoreanApiFetcher(apiUtil) {
    override fun getBaseUrl(): String = "https://apis.data.go.kr/1160100/service/GetKrxListedInfoService/getItemInfo"

    override fun createQueryParams(
        crNo: String,
        serviceKey: String,
    ): MultiValueMap<String, String> {
        val resMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        resMap["resultType"] = "json"
        resMap["numOfRows"] = "1"
        resMap["serviceKey"] = serviceKey
        resMap["crno"] = crNo
        return resMap
    }

    override fun extractResponseAsMap(response: ResponseEntity<String>): Map<String, String> {
        val resMap = HashMap<String, String>()
        val itemNode = extractItemNode(response)

        resMap["assetName"] = extractNodeValue(itemNode, "itmsNm")

        return resMap
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

    private fun extractItemNode(response: ResponseEntity<String>): JsonNode {
        val om = jacksonObjectMapper()
        val readTree = om.readTree(response.body)
        try {
            return readTree
                .get("response")
                .get("body")
                .get("items")
                .get("item")
                .toList()[0]
        } catch (e: IndexOutOfBoundsException) {
            throw NoSuchElementException("${javaClass.name}가 item노드 추출에 실패함 ${response.headers.eTag}")
        }
    }

    override fun responseErrorCheck(response: ResponseEntity<String>) {
        checkHttpStatus(response)
        checkBodyType(response)
    }

    // 리턴이 xml이면 예외발생한거
    private fun checkBodyType(response: ResponseEntity<String>) {
        if (response.headers.contentType.toString() == "text/xml;charset=UTF-8") {
            val responseBody = response.body ?: ""
            val responseInputStream = responseBody.byteInputStream(Charsets.UTF_8)
            val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document = documentBuilder.parse(responseInputStream)
            document.documentElement.normalize()

            val errorCode = document.getElementsByTagName("returnReasonCode").item(0).textContent
            val errorMsg = document.getElementsByTagName("returnAuthMsg").item(0).textContent

            throw RuntimeException("""${javaClass.name} api 통신예외발생 코드:$errorCode 메세지:$errorMsg""")
        }
    }

    private fun checkHttpStatus(response: ResponseEntity<String>) {
        if (response.statusCode.value() != 200) {
            throw RuntimeException("${javaClass.name} http상태코드가 200이 아님")
        }
    }
}

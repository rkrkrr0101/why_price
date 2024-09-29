package rkrk.whyprice.tool.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MvcResult

class ParsingUtil {
    companion object {
        fun toListMap(
            mvcResult: MvcResult,
            om: ObjectMapper,
        ): List<Map<String, Any>> {
            val content = mvcResult.response.contentAsString
            val resMap = om.readValue(content, Map::class.java)
            val body = (resMap["data"] as List<*>).filterIsInstance<Map<String, Any>>()
            return body
        }

        fun toMap(
            mvcResult: MvcResult,
            om: ObjectMapper,
        ): Map<String, Any> {
            val content = mvcResult.response.contentAsString
            val resMap = om.readValue(content, Map::class.java)
            val body =
                (resMap["data"] as Map<*, *>)
                    .asSequence()
                    .filter { it.key is String && it.value != null }
                    .associate { it.key as String to it.value as Any }
                    .toMap()

            return body
        }
    }
}

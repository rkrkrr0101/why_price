package rkrk.whyprice.config

import java.util.Properties

class ApiConfig private constructor() {
    companion object {
        private val properties = Properties()

        init {
            val inputStream =
                this::class.java.classLoader.getResourceAsStream("config.properties")
                    ?: throw IllegalArgumentException("config.properties 파일을 찾을 수 없습니다.")
            properties.load(inputStream)
        }

        fun getApiKey(): String = properties.getProperty("OPENAPI_KEY")
    }
}

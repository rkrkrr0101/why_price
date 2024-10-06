package rkrk.whyprice.config

import io.github.cdimascio.dotenv.dotenv

class ApiConfig private constructor() {
    companion object {
        private val dotenv = dotenv()

        fun getOpenApiKey(): String = dotenv.get("OPENAPI_KEY")

        fun getPerplexityKey(): String = dotenv.get("PERPLEXITY_KEY")

        fun getKoreaInvKey(): String = dotenv.get("KOREA_INV_KEY")

        fun getKoreaSecretKey(): String = dotenv.get("KOREA_INV_SECRET")
    }
}

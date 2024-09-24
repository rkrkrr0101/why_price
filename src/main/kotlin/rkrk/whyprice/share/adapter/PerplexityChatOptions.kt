package rkrk.whyprice.share.adapter

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.ai.openai.OpenAiChatOptions

// https://docs.perplexity.ai/api-reference/chat-completions
// todo 테스트생성
class PerplexityChatOptions : OpenAiChatOptions() {
    @JsonProperty("search_domain_filter")
    var searchDomainFilter = ""

    @JsonProperty("search_recency_filter")
    var searchRecencyFilter = ""

    companion object {
        @JvmStatic
        fun builder(): PerplexityBuilder = PerplexityBuilder()
    }

    override fun equals(other: Any?): Boolean {
        val baseEquals = super.equals(other)
        if (!baseEquals) {
            return false
        }
        val castOther = other as PerplexityChatOptions
        if (this.searchDomainFilter != castOther.searchDomainFilter) {
            return false
        }
        if (this.searchRecencyFilter != castOther.searchRecencyFilter) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (searchDomainFilter.hashCode())
        result = 31 * result + (searchRecencyFilter.hashCode())
        return result
    }

    class PerplexityBuilder : Builder() {
        private val perplexityChatOptions = PerplexityChatOptions()

        fun searchDomainFilter(searchDomainFilter: String): PerplexityBuilder {
            perplexityChatOptions.searchDomainFilter = searchDomainFilter
            return this
        }

        fun searchRecencyFilter(searchRecencyFilter: String): PerplexityBuilder {
            perplexityChatOptions.searchRecencyFilter = searchRecencyFilter
            return this
        }

        override fun build(): PerplexityChatOptions {
            val baseOptions = super.build()
            perplexityChatOptions.apply {
                model = baseOptions.model
                frequencyPenalty = baseOptions.frequencyPenalty
                maxTokens = baseOptions.maxTokens
                temperature = baseOptions.temperature
                topP = baseOptions.topP
                presencePenalty = baseOptions.presencePenalty
            }
            return perplexityChatOptions
        }
    }
}

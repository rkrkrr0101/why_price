package rkrk.whyprice.share.adapter.responser.infrastructure

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.ai.openai.OpenAiChatOptions

// https://docs.perplexity.ai/api-reference/chat-completions
// todo 테스트생성
@JsonInclude(JsonInclude.Include.NON_NULL)
class PerplexityChatOptions : OpenAiChatOptions() {
    @JsonProperty("search_domain_filter")
    var searchDomainFilter: String? = null

    @JsonProperty("search_recency_filter")
    var searchRecencyFilter: String? = null

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

    class PerplexityBuilder {
        private val perplexityChatOptions = PerplexityChatOptions()

        fun searchDomainFilter(searchDomainFilter: String): PerplexityBuilder {
            perplexityChatOptions.searchDomainFilter = searchDomainFilter
            return this
        }

        fun searchRecencyFilter(searchRecencyFilter: String): PerplexityBuilder {
            perplexityChatOptions.searchRecencyFilter = searchRecencyFilter
            return this
        }

        fun withTemperature(temperature: Float): PerplexityBuilder {
            perplexityChatOptions.temperature = temperature
            return this
        }

        fun withModel(model: String): PerplexityBuilder {
            perplexityChatOptions.model = model
            return this
        }

        fun withFrequencyPenalty(frequencyPenalty: Float): PerplexityBuilder {
            perplexityChatOptions.frequencyPenalty = frequencyPenalty
            return this
        }

        fun withMaxTokens(maxTokens: Int): PerplexityBuilder {
            perplexityChatOptions.maxTokens = maxTokens
            return this
        }

        fun withTopP(topP: Float): PerplexityBuilder {
            perplexityChatOptions.topP = topP
            return this
        }

        fun withPresencePenalty(presencePenalty: Float): PerplexityBuilder {
            perplexityChatOptions.presencePenalty = presencePenalty
            return this
        }

        fun build(): PerplexityChatOptions = perplexityChatOptions
    }
}

package rkrk.whyprice.unittest

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rkrk.whyprice.share.adapter.responser.infrastructure.PerplexityChatOptions

class PerplexityChatOptionsTest {
    @Test
    @DisplayName("정상적으로 PerplexityChatOptions를 생성할수있다")
    fun successCreatePerplexityChatOptions() {
        val options =
            PerplexityChatOptions
                .builder()
                .withMaxTokens(300)
                .withModel("llama-3.1-sonar-small-128k-online")
                .searchRecencyFilter("day")
                .build()

        Assertions.assertThat(options.searchRecencyFilter).isEqualTo("day")
        Assertions.assertThat(options.maxTokens).isEqualTo(300)
        Assertions.assertThat(options.model).isEqualTo("llama-3.1-sonar-small-128k-online")
    }

    @Test
    @DisplayName("PerplexityChatOptions의 모든 필드가 같으면 같은 객체로 취급된다")
    fun equalsPerplexityChatOptions() {
        val firstOptions =
            PerplexityChatOptions
                .builder()
                .withMaxTokens(300)
                .withModel("llama-3.1-sonar-small-128k-online")
                .searchRecencyFilter("day")
                .build()
        val secondOptions =
            PerplexityChatOptions
                .builder()
                .withMaxTokens(300)
                .withModel("llama-3.1-sonar-small-128k-online")
                .searchRecencyFilter("day")
                .build()

        Assertions.assertThat(firstOptions).isEqualTo(secondOptions)
    }

    @Test
    @DisplayName("PerplexityChatOptions의 필드가 하나라도 다르면 다른 객체로 취급된다")
    fun notEqualsPerplexityChatOptions() {
        val firstOptions =
            PerplexityChatOptions
                .builder()
                .withMaxTokens(300)
                .withModel("llama-3.1-sonar-small-128k-online")
                .searchRecencyFilter("day")
                .build()
        val secondOptions =
            PerplexityChatOptions
                .builder()
                .withMaxTokens(300)
                .withModel("llama-3.1-sonar-small-128k-online")
                .searchRecencyFilter("week")
                .build()

        Assertions.assertThat(firstOptions).isNotEqualTo(secondOptions)
    }
}

package rkrk.whyprice.share.adapter

import org.springframework.ai.openai.api.ApiUtils
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.client.RestClient
import reactor.core.publisher.Flux

class PerplexityApi(
    key: String,
) : OpenAiApi("https://api.perplexity.ai", key) {
    private val restClient =
        RestClient
            .builder()
            .baseUrl(
                "https://api.perplexity.ai",
            ).defaultHeaders(ApiUtils.getJsonContentHeaders(key))
            .build()

    override fun chatCompletionEntity(chatRequest: ChatCompletionRequest?): ResponseEntity<ChatCompletion> {
        Assert.notNull(chatRequest, "The request body can not be null.")
        Assert.isTrue(!chatRequest!!.stream(), "Request must set the steam property to false.")

        return restClient
            .post()
            .uri("/chat/completions", *arrayOfNulls(0))
            .body(
                chatRequest,
            ).retrieve()
            .toEntity(
                ChatCompletion::class.java,
            )
    }

    override fun chatCompletionStream(chatRequest: ChatCompletionRequest?): Flux<ChatCompletionChunk> = throw RuntimeException("지원되지않음")

    override fun <T : Any?> embeddings(embeddingRequest: EmbeddingRequest<T>?): ResponseEntity<EmbeddingList<Embedding>> =
        throw RuntimeException("지원되지않음")
}

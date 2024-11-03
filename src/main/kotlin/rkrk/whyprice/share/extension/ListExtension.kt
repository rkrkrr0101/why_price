package rkrk.whyprice.share.extension

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

suspend fun <T, R> List<T>.mapAsync(transform: suspend (T) -> R): List<R> =
    supervisorScope {
        this@mapAsync.map { async { transform(it) } }.awaitAll()
    }

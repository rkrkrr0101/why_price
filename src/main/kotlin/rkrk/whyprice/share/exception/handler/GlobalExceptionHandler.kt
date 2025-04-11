package rkrk.whyprice.share.exception.handler

import org.slf4j.LoggerFactory
import org.springframework.ai.retry.NonTransientAiException
import org.springframework.ai.retry.TransientAiException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.share.Result

@ControllerAdvice
@RestController
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun noSearchStockName(exp: NoSuchElementException): Result<String> {
        log.warn(exp.message)
        return Result(exp.message ?: "message null")
    }

    @ExceptionHandler(NonTransientAiException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun nonTransientAiError(exp: NonTransientAiException): Result<String> {
        log.warn(exp.message)
        return Result(exp.message ?: "message null")
    }

    @ExceptionHandler(TransientAiException::class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    fun transientAiError(exp: NonTransientAiException): Result<String> {
        log.warn(exp.message)
        return Result(exp.message ?: "message null")
    }
}

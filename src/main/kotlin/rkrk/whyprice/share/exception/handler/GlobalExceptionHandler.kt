package rkrk.whyprice.share.exception.handler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.member.domain.exception.DuplicateMemberException
import rkrk.whyprice.share.Result

@ControllerAdvice
@RestController
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(DuplicateMemberException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun duplicateMemberName(exp: DuplicateMemberException): Result<String> {
        log.warn(exp.message)
        return Result(exp.msg)
    }
}

package rkrk.whyprice.helper

import org.springframework.stereotype.Component
import rkrk.whyprice.share.CustomDateTime
import java.time.LocalDateTime

@Component
class CustomDateTimeImpl : CustomDateTime {
    override fun getNow(): LocalDateTime = LocalDateTime.now()
}

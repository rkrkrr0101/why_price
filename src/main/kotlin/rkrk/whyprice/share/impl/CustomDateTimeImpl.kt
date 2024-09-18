package rkrk.whyprice.share.impl

import org.springframework.stereotype.Component
import rkrk.whyprice.share.infra.CustomDateTime
import java.time.LocalDateTime

@Component
class CustomDateTimeImpl : CustomDateTime {
    override fun getNow(): LocalDateTime = LocalDateTime.now()
}

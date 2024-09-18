package rkrk.whyprice.mock

import rkrk.whyprice.share.infra.CustomDateTime
import java.time.LocalDateTime

class CustomDateTimeMock(
    private val fixedDateTime: String,
) : CustomDateTime {
    override fun getNow(): LocalDateTime = LocalDateTime.parse(fixedDateTime)
}

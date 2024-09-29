package rkrk.whyprice.tool.mock

import rkrk.whyprice.share.port.CustomDateTime
import java.time.LocalDateTime

class CustomDateTimeMock(
    private val fixedDateTime: String,
) : CustomDateTime {
    override fun getNow(): LocalDateTime = LocalDateTime.parse(fixedDateTime)
}

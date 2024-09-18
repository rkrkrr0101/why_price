package rkrk.whyprice.share.port

import java.time.LocalDateTime

interface CustomDateTime {
    fun getNow(): LocalDateTime
}

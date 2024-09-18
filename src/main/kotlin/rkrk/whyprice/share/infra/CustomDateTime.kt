package rkrk.whyprice.share.infra

import java.time.LocalDateTime

interface CustomDateTime {
    fun getNow(): LocalDateTime
}

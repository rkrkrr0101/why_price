package rkrk.whyprice.member.application.port.out

import rkrk.whyprice.member.domain.Asset

interface CheckVolatilityPort {
    fun hasVolatility(
        asset: Asset,
        volatilityTime: Int = 1,
    ): Boolean
}

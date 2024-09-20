package rkrk.whyprice.member.application.port.input

import rkrk.whyprice.member.application.port.input.dto.req.VolatilityMemberStocksDto
import rkrk.whyprice.member.application.port.input.dto.res.KoreanStockResponseDto

interface VolatilityCheckKoreanStockQuery {
    fun fetchVolatility(memberDto: VolatilityMemberStocksDto): List<KoreanStockResponseDto>
}

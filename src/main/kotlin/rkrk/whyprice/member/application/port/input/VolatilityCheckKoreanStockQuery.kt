package rkrk.whyprice.member.application.port.input

import rkrk.whyprice.member.application.port.input.dto.req.MemberVolatilityDto
import rkrk.whyprice.member.application.port.input.dto.res.KoreanStockResponseDto

interface VolatilityCheckKoreanStockQuery {
    fun fetchVolatility(memberDto: MemberVolatilityDto): List<KoreanStockResponseDto>
}

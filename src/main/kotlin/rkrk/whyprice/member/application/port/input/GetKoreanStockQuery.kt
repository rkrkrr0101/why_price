package rkrk.whyprice.member.application.port.input

import rkrk.whyprice.member.application.port.input.dto.req.MemberStockViewDto
import rkrk.whyprice.member.domain.KoreanStock

interface GetKoreanStockQuery {
    fun getKoreanStock(stockDto: MemberStockViewDto): List<KoreanStock>
}

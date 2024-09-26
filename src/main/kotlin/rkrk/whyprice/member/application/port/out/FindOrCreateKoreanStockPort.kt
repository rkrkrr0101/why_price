package rkrk.whyprice.member.application.port.out

import rkrk.whyprice.member.application.port.input.dto.req.FindOrCreateKoreanStockDto
import rkrk.whyprice.member.domain.KoreanStock

interface FindOrCreateKoreanStockPort {
    fun findOrCreate(dto: FindOrCreateKoreanStockDto): KoreanStock
}

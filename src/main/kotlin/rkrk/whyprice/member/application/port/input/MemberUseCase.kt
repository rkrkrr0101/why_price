package rkrk.whyprice.member.application.port.input

import rkrk.whyprice.member.application.port.input.dto.req.MemberCreateDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockAddDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockDeleteDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberStockViewDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberVolatilityDto
import rkrk.whyprice.member.domain.KoreanStock

interface MemberUseCase {
    fun createMember(createDto: MemberCreateDto)

    fun addKoreanStock(stockDto: MemberKoreanStockAddDto)

    fun deleteKoreanStock(stockDto: MemberKoreanStockDeleteDto)

    fun getKoreanStock(stockDto: MemberStockViewDto): List<KoreanStock>

    fun fetchVolatility(memberDto: MemberVolatilityDto): List<KoreanStock>
}

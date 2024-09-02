package rkrk.whyprice.inputapi.usecase

import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.inputapi.dto.member.req.MemberCreateDto
import rkrk.whyprice.inputapi.dto.member.req.MemberKoreanStockAddDto
import rkrk.whyprice.inputapi.dto.member.req.MemberKoreanStockDeleteDto
import rkrk.whyprice.inputapi.dto.member.req.MemberStockViewDto
import rkrk.whyprice.inputapi.dto.member.req.MemberVolatilityDto

interface MemberUseCase {
    fun createMember(createDto: MemberCreateDto)

    fun addKoreanStock(stockDto: MemberKoreanStockAddDto)

    fun deleteKoreanStock(stockDto: MemberKoreanStockDeleteDto)

    fun getKoreanStock(stockDto: MemberStockViewDto): List<KoreanStock>

    fun fetchVolatility(memberDto: MemberVolatilityDto): List<KoreanStock>
}

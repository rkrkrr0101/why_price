package rkrk.whyprice.inputapi.usecase

import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.inputapi.dto.member.MemberCreateDto
import rkrk.whyprice.inputapi.dto.member.MemberKoreanStockAddDto
import rkrk.whyprice.inputapi.dto.member.MemberKoreanStockRemoveDto
import rkrk.whyprice.inputapi.dto.member.MemberStockViewDto
import rkrk.whyprice.inputapi.dto.member.MemberVolatilityDto

interface MemberUseCase {
    fun createMember(createDto: MemberCreateDto)

    fun addKoreanStock(stockDto: MemberKoreanStockAddDto)

    fun deleteKoreanStock(stockDto: MemberKoreanStockRemoveDto)

    fun getKoreanStock(stockDto: MemberStockViewDto): List<KoreanStock>

    fun fetchVolatility(memberDto: MemberVolatilityDto): List<KoreanStock>
}

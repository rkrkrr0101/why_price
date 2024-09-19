package rkrk.whyprice.member.application.port.input

import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockAddDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockDeleteDto

interface ManageKoreanStockUseCase {
    fun addKoreanStock(stockDto: MemberKoreanStockAddDto)

    fun deleteKoreanStock(stockDto: MemberKoreanStockDeleteDto)
}

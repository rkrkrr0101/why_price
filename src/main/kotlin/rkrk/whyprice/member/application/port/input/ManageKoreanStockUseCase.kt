package rkrk.whyprice.member.application.port.input

import rkrk.whyprice.member.application.port.input.dto.req.AddMemberKoreanStockDto
import rkrk.whyprice.member.application.port.input.dto.req.DeleteMemberKoreanStockDto

interface ManageKoreanStockUseCase {
    fun addKoreanStock(stockDto: AddMemberKoreanStockDto)

    fun deleteKoreanStock(stockDto: DeleteMemberKoreanStockDto)
}

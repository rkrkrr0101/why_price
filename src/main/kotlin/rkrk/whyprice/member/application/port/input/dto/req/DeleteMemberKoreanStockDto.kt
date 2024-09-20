package rkrk.whyprice.member.application.port.input.dto.req

data class DeleteMemberKoreanStockDto(
    val memberName: String,
    val stockCrno: String,
    val stockName: String,
)

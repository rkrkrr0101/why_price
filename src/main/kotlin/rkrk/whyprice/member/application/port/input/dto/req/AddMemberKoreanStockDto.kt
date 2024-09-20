package rkrk.whyprice.member.application.port.input.dto.req

data class AddMemberKoreanStockDto(
    val memberName: String,
    val stockCrno: String,
    val stockName: String,
)

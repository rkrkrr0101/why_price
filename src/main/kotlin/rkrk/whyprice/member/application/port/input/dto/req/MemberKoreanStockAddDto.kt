package rkrk.whyprice.member.application.port.input.dto.req

data class MemberKoreanStockAddDto(
    val memberName: String,
    val stockCrno: String,
    val stockName: String,
)

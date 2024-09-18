package rkrk.whyprice.member.application.port.input.dto.req

data class MemberKoreanStockDeleteDto(
    val memberName: String,
    val stockCrno: String,
    val stockName: String,
)

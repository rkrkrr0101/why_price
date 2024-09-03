package rkrk.whyprice.inputapi.dto.member.req

data class MemberKoreanStockDeleteDto(
    val memberName: String,
    val stockCrno: String,
    val stockName: String,
)

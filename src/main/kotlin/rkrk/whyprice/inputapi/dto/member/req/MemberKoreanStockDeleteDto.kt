package rkrk.whyprice.inputapi.dto.member.req

data class MemberKoreanStockDeleteDto(
    val memberId: Long,
    val stockCrno: String,
    val stockName: String,
)

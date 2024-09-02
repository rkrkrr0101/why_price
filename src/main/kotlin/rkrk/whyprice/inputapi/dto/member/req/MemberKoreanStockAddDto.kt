package rkrk.whyprice.inputapi.dto.member.req

data class MemberKoreanStockAddDto(
    val memberId: Long,
    val stockCrno: String,
    val stockName: String,
)

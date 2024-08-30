package rkrk.whyprice.inputapi.dto.member

data class MemberKoreanStockRemoveDto(
    val memberId: Long,
    val stockCrno: String,
    val stockName: String,
)

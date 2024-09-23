package rkrk.whyprice.member.application.service.exception

class DuplicateMemberKoreanStockException(
    val msg: String,
) : IllegalArgumentException(msg)

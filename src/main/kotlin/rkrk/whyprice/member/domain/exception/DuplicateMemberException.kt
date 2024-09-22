package rkrk.whyprice.member.domain.exception

class DuplicateMemberException(
    val msg: String,
) : IllegalArgumentException(msg)

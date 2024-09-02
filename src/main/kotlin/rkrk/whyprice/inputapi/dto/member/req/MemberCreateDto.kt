package rkrk.whyprice.inputapi.dto.member.req

import rkrk.whyprice.domain.member.Member

data class MemberCreateDto(
    val name: String,
) {
    fun dtoToMember(): Member =
        Member(
            name,
        )
}

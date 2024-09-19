package rkrk.whyprice.member.application.port.input.dto.req

import rkrk.whyprice.member.domain.Member

data class RegisterMemberDto(
    val name: String,
) {
    fun dtoToMember(): Member =
        Member(
            name,
        )
}

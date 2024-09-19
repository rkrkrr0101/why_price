package rkrk.whyprice.member.application.port.input

import rkrk.whyprice.member.application.port.input.dto.req.RegisterMemberDto

interface RegisterMemberUseCase {
    fun registerMember(dto: RegisterMemberDto)
}

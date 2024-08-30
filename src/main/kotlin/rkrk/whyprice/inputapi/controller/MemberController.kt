package rkrk.whyprice.inputapi.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.inputapi.dto.member.MemberCreateDto
import rkrk.whyprice.inputapi.usecase.MemberUseCase

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberUseCase: MemberUseCase,
) {
    @PostMapping
    fun createMember(
        @RequestBody memberCreateDto: MemberCreateDto,
    ) {
        memberUseCase.createMember(memberCreateDto)
    }
}

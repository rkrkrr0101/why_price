package rkrk.whyprice.member.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.RegisterMemberUseCase
import rkrk.whyprice.member.application.port.input.dto.req.RegisterMemberDto
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.domain.service.DuplicateCheckMemberService

@Service
@Transactional
class RegisterMemberService(
    private val memberRepository: MemberRepository,
    private val duplicateCheckMemberService: DuplicateCheckMemberService,
) : RegisterMemberUseCase {
    @Transactional
    override fun registerMember(dto: RegisterMemberDto) {
        duplicateCheckMemberService.duplicateCheckMember(dto.name)

        val member = dto.dtoToMember()
        memberRepository.save(member)
    }
}

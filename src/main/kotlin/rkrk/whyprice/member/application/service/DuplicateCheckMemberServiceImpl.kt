package rkrk.whyprice.member.application.service

import org.springframework.stereotype.Service
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.domain.exception.DuplicateMemberException
import rkrk.whyprice.member.domain.service.DuplicateCheckMemberService

@Service
class DuplicateCheckMemberServiceImpl(
    private val memberRepository: MemberRepository,
) : DuplicateCheckMemberService {
    override fun duplicateCheckMember(memberName: String) {
        if (memberRepository.existsByName(memberName)) {
            throw DuplicateMemberException("이미 존재하는 회원입니다.회원명:$memberName")
        }
    }
}

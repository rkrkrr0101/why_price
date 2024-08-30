package rkrk.whyprice.domain.member.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.domain.member.repository.MemberJpaRepository

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberJpaRepository,
) {
    @Transactional
    fun createMember() {
        memberRepository.save()
    }
}

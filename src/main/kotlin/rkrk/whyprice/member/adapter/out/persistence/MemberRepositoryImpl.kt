package rkrk.whyprice.member.adapter.out.persistence

import org.springframework.stereotype.Repository
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.application.service.exception.NotExistsMemberException
import rkrk.whyprice.member.domain.Member

@Repository
class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberRepository {
    // todo findOne으로 변경
    override fun findByUserName(name: String): Member =
        memberJpaRepository.findByUserName(name)
            ?: throw NotExistsMemberException("존재하지 않는 회원입니다.")

    override fun save(member: Member): Member = memberJpaRepository.save(member)

    override fun delete(member: Member) {
        memberJpaRepository.delete(member)
    }

    // todo findOne으로 변경
    override fun findById(id: Long): Member =
        memberJpaRepository
            .findById(id)
            .orElseThrow { NotExistsMemberException("존재하지 않는 회원입니다.") }

    override fun existsByName(name: String): Boolean = memberJpaRepository.existsByUserName(name)
}

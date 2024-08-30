package rkrk.whyprice.domain.member.repository

import org.springframework.stereotype.Repository
import rkrk.whyprice.domain.member.Member
import rkrk.whyprice.domain.member.service.infra.MemberRepository

@Repository
class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberRepository {
    override fun findByUserName(name: String): Member =
        memberJpaRepository.findByUserName(name)
            ?: throw IllegalArgumentException("존재하지 않는 회원입니다.")

    override fun save(member: Member): Member = memberJpaRepository.save(member)

    override fun delete(member: Member) {
        memberJpaRepository.delete(member)
    }

    override fun findById(id: Long): Member =
        memberJpaRepository
            .findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 회원입니다.") }

    override fun existsByName(name: String): Boolean = memberJpaRepository.existsByUserName(name)
}

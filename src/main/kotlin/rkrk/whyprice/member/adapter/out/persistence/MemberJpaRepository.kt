package rkrk.whyprice.member.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import rkrk.whyprice.member.domain.Member

interface MemberJpaRepository : JpaRepository<Member, Long> {
    fun findByUserName(name: String): Member?

    fun existsByUserName(name: String): Boolean
}

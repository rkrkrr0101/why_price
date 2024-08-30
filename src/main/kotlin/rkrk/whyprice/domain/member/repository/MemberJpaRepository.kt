package rkrk.whyprice.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import rkrk.whyprice.domain.member.Member

interface MemberJpaRepository : JpaRepository<Member, Long> {
    fun findByUserName(name: String): Member?

    fun existsByUserName(name: String): Boolean
}

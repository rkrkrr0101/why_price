package rkrk.whyprice.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rkrk.whyprice.domain.member.Member

@Repository
interface MemberJpaRepository : JpaRepository<Member, Long>

package rkrk.whyprice.member.application.port.out

import rkrk.whyprice.member.domain.Member

interface MemberRepository {
    fun findByUserName(name: String): Member

    fun save(member: Member): Member

    fun delete(member: Member)

    fun findById(id: Long): Member

    fun existsByName(name: String): Boolean
}

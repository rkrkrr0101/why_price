package rkrk.whyprice.domain.member.service.infra

import rkrk.whyprice.domain.member.Member

interface MemberRepository {
    fun findByUserName(name: String): Member

    fun save(member: Member): Member

    fun delete(member: Member)

    fun findById(id: Long): Member

    fun existsByName(name: String): Boolean
}

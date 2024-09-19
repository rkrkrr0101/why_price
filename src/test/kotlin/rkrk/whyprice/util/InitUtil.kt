package rkrk.whyprice.util

import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.domain.KoreanStock
import rkrk.whyprice.member.domain.Member

class InitUtil {
    companion object {
        fun basicMemberInit(memberRepository: MemberRepository) {
            val member1 = Member("member1")
            member1.addKoreanStock(KoreanStock("130111-0006246", "삼성전자"))
            member1.addKoreanStock(KoreanStock("110111-2487050", "LG전자"))
            val member2 = Member("member2")
            member2.addKoreanStock(KoreanStock("110111-4594952", "이마트"))
            val member3 = Member("member3")
            memberRepository.save(member1)
            memberRepository.save(member2)
            memberRepository.save(member3)
        }
    }
}

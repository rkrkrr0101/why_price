package rkrk.whyprice.util

import rkrk.whyprice.member.application.port.out.KoreanStockRepository
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.domain.KoreanStock
import rkrk.whyprice.member.domain.Member

class InitUtil {
    companion object {
        fun basicMemberInit(
            memberRepository: MemberRepository,
            koreanStockRepository: KoreanStockRepository,
        ) {
            val member1 = Member("member1")

            val member2 = Member("member2")

            val member3 = Member("member3")
            memberRepository.save(member1)
            memberRepository.save(member2)
            memberRepository.save(member3)
            val koreanStock1 = KoreanStock("130111-0006246", "삼성전자")
            val koreanStock2 = KoreanStock("110111-2487050", "LG전자")
            val koreanStock3 = KoreanStock("110111-4594952", "이마트")
            koreanStockRepository.save(koreanStock1)
            koreanStockRepository.save(koreanStock2)
            koreanStockRepository.save(koreanStock3)
            member1.addKoreanStock(koreanStock1)
            member1.addKoreanStock(koreanStock2)
            member2.addKoreanStock(koreanStock3)
        }
    }
}

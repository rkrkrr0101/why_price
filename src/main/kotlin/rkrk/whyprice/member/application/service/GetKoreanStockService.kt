package rkrk.whyprice.member.application.service

import org.springframework.stereotype.Service
import rkrk.whyprice.member.application.port.input.GetKoreanStockQuery
import rkrk.whyprice.member.application.port.input.dto.req.MemberStockViewDto
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.domain.KoreanStock

@Service
class GetKoreanStockService(
    private val memberRepository: MemberRepository,
) : GetKoreanStockQuery {
    override fun getKoreanStock(stockDto: MemberStockViewDto): List<KoreanStock> {
        val member = memberRepository.findByUserName(stockDto.memberName)
        return member.getKoreanStocks()
    }
}

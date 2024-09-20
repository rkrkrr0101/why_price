package rkrk.whyprice.member.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.VolatilityCheckKoreanStockQuery
import rkrk.whyprice.member.application.port.input.dto.req.VolatilityMemberStocksDto
import rkrk.whyprice.member.application.port.input.dto.res.KoreanStockResponseDto
import rkrk.whyprice.member.application.port.out.CheckVolatilityPort
import rkrk.whyprice.member.application.port.out.MemberRepository

@Service
@Transactional(readOnly = true)
class VolatilityCheckKoreanStockService(
    private val memberRepository: MemberRepository,
    private val checkVolatilityPort: CheckVolatilityPort,
) : VolatilityCheckKoreanStockQuery {
    override fun fetchVolatility(memberDto: VolatilityMemberStocksDto): List<KoreanStockResponseDto> {
        val member = memberRepository.findByUserName(memberDto.memberName)
        val responseDtos =
            member
                .koreanStockHasVolatility(checkVolatilityPort)
                .map { KoreanStockResponseDto(it.getIdentityCode(), it.getAssetName()) }
        return responseDtos
    }
}

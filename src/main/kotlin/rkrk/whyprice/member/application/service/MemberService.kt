package rkrk.whyprice.member.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.MemberUseCase
import rkrk.whyprice.member.application.port.input.dto.req.MemberCreateDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockAddDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockDeleteDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberStockViewDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberVolatilityDto
import rkrk.whyprice.member.application.port.out.CheckVolatilityPort
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.domain.KoreanStock

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val checkVolatilityPort: CheckVolatilityPort,
) : MemberUseCase {
    @Transactional
    override fun createMember(createDto: MemberCreateDto) {
        duplicateCheckMember(createDto.name)

        val member = createDto.dtoToMember()
        memberRepository.save(member)
    }

    @Transactional
    override fun addKoreanStock(stockDto: MemberKoreanStockAddDto) {
        val member = memberRepository.findByUserName(stockDto.memberName)
        val stock = KoreanStock(stockDto.stockCrno, stockDto.stockName)
        member.addKoreanStock(stock)
    }

    @Transactional
    override fun deleteKoreanStock(stockDto: MemberKoreanStockDeleteDto) {
        val member = memberRepository.findByUserName(stockDto.memberName)
        val stock = KoreanStock(stockDto.stockCrno, stockDto.stockName)
        member.deleteKoreanStock(stock)
    }

    override fun getKoreanStock(stockDto: MemberStockViewDto): List<KoreanStock> {
        val member = memberRepository.findByUserName(stockDto.memberName)
        return member.getKoreanStocks()
    }

    override fun fetchVolatility(memberDto: MemberVolatilityDto): List<KoreanStock> {
        val member = memberRepository.findByUserName(memberDto.memberName)
        return member.koreanStockHasVolatility(checkVolatilityPort)
    }

    private fun duplicateCheckMember(memberName: String) {
        if (memberRepository.existsByName(memberName)) {
            throw IllegalArgumentException("이미 존재하는 회원입니다.")
        }
    }
}

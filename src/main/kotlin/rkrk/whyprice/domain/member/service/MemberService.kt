package rkrk.whyprice.domain.member.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.domain.member.service.infra.MemberRepository
import rkrk.whyprice.inputapi.dto.member.req.MemberCreateDto
import rkrk.whyprice.inputapi.dto.member.req.MemberKoreanStockAddDto
import rkrk.whyprice.inputapi.dto.member.req.MemberKoreanStockDeleteDto
import rkrk.whyprice.inputapi.dto.member.req.MemberStockViewDto
import rkrk.whyprice.inputapi.dto.member.req.MemberVolatilityDto
import rkrk.whyprice.inputapi.usecase.MemberUseCase
import rkrk.whyprice.share.Responser

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val responser: Responser,
) : MemberUseCase {
    @Transactional
    override fun createMember(createDto: MemberCreateDto) {
        duplicateCheckMember(createDto.name)

        val member = createDto.dtoToMember()
        memberRepository.save(member)
    }

    @Transactional
    override fun addKoreanStock(stockDto: MemberKoreanStockAddDto) {
        val member = memberRepository.findById(stockDto.memberId)
        val stock = KoreanStock(stockDto.stockCrno, stockDto.stockName)
        member.addKoreanStock(stock)
    }

    @Transactional
    override fun deleteKoreanStock(stockDto: MemberKoreanStockDeleteDto) {
        val member = memberRepository.findById(stockDto.memberId)
        val stock = KoreanStock(stockDto.stockCrno, stockDto.stockName)
        member.deleteKoreanStock(stock)
    }

    override fun getKoreanStock(stockDto: MemberStockViewDto): List<KoreanStock> {
        val member = memberRepository.findByUserName(stockDto.memberName)
        return member.getKoreanStocks()
    }

    override fun fetchVolatility(memberDto: MemberVolatilityDto): List<KoreanStock> {
        val member = memberRepository.findById(memberDto.memberId)
        return member.koreanStockHasVolatility(responser)
    }

    private fun duplicateCheckMember(memberName: String) {
        if (memberRepository.existsByName(memberName)) {
            throw IllegalArgumentException("이미 존재하는 회원입니다.")
        }
    }
}

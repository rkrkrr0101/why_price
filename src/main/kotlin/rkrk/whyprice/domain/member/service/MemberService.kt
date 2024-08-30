package rkrk.whyprice.domain.member.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.domain.member.service.infra.MemberRepository
import rkrk.whyprice.inputapi.dto.member.MemberCreateDto
import rkrk.whyprice.inputapi.dto.member.MemberKoreanStockAddDto
import rkrk.whyprice.inputapi.dto.member.MemberKoreanStockRemoveDto
import rkrk.whyprice.inputapi.dto.member.MemberStockViewDto
import rkrk.whyprice.inputapi.dto.member.MemberVolatilityDto
import rkrk.whyprice.inputapi.usecase.MemberUseCase

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
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
    override fun deleteKoreanStock(stockDto: MemberKoreanStockRemoveDto) {
        val member = memberRepository.findById(stockDto.memberId)
        val stock = KoreanStock(stockDto.stockCrno, stockDto.stockName)
        member.deleteKoreanStock(stock)
    }

    override fun getKoreanStock(stockDto: MemberStockViewDto): List<KoreanStock> {
        val member = memberRepository.findByUserName(stockDto.memberName)
        return member.getKoreanStocks()
    }

    override fun fetchVolatility(memberDto: MemberVolatilityDto): List<KoreanStock> {
        TODO("Not yet implemented")
    }

    private fun duplicateCheckMember(memberName: String) {
        if (memberRepository.existsByName(memberName)) {
            throw IllegalArgumentException("이미 존재하는 회원입니다.")
        }
    }
}

package rkrk.whyprice.member.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.ManageKoreanStockUseCase
import rkrk.whyprice.member.application.port.input.dto.req.AddMemberKoreanStockDto
import rkrk.whyprice.member.application.port.input.dto.req.DeleteMemberKoreanStockDto
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.domain.KoreanStock

@Service
@Transactional
class ManageKoreanStockService(
    private val memberRepository: MemberRepository,
) : ManageKoreanStockUseCase {
    @Transactional
    override fun addKoreanStock(stockDto: AddMemberKoreanStockDto) {
        val member = memberRepository.findByUserName(stockDto.memberName)
        val stock = KoreanStock(stockDto.stockCrno, stockDto.stockName)
        member.addKoreanStock(stock)
    }

    @Transactional
    override fun deleteKoreanStock(stockDto: DeleteMemberKoreanStockDto) {
        val member = memberRepository.findByUserName(stockDto.memberName)
        val stock = KoreanStock(stockDto.stockCrno, stockDto.stockName)
        member.deleteKoreanStock(stock)
    }
}

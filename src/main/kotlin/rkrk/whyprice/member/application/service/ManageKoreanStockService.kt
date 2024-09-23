package rkrk.whyprice.member.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.ManageKoreanStockUseCase
import rkrk.whyprice.member.application.port.input.dto.req.AddMemberKoreanStockDto
import rkrk.whyprice.member.application.port.input.dto.req.DeleteMemberKoreanStockDto
import rkrk.whyprice.member.application.port.out.FindKoreanStockByNamePort
import rkrk.whyprice.member.application.port.out.KoreanStockRepository
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.application.service.exception.DuplicateMemberKoreanStockException
import rkrk.whyprice.member.domain.KoreanStock

@Service
@Transactional
class ManageKoreanStockService(
    private val memberRepository: MemberRepository,
    private val koreanStockRepository: KoreanStockRepository,
    private val findKoreanStockByNamePort: FindKoreanStockByNamePort,
) : ManageKoreanStockUseCase {
    @Transactional
    override fun addKoreanStock(stockDto: AddMemberKoreanStockDto) {
        val member = memberRepository.findByUserName(stockDto.memberName)
        val koreanStock = findOrCreateKoreanStock(stockDto)

        if (member.existsKoreanStock(koreanStock)) {
            throw DuplicateMemberKoreanStockException("이미 존재하는 주식")
        }

        member.addKoreanStock(koreanStock)
    }

    @Transactional
    override fun deleteKoreanStock(stockDto: DeleteMemberKoreanStockDto) {
        val member = memberRepository.findByUserName(stockDto.memberName)
        val stock = (
            koreanStockRepository.findOneOrNull(stockDto.stockName)
                ?: throw IllegalArgumentException("없는주식")
        )

        member.deleteKoreanStock(stock)
    }

    private fun findOrCreateKoreanStock(stockDto: AddMemberKoreanStockDto): KoreanStock {
        var koreanStock =
            koreanStockRepository.findOneOrNull(stockDto.stockName)
        if (koreanStock == null) {
            koreanStock = findKoreanStockByNamePort.find(stockDto.stockName)
            koreanStockRepository.save(koreanStock)
        }
        return koreanStock
    }
}

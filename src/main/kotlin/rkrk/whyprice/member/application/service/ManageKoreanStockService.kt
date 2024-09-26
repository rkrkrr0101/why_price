package rkrk.whyprice.member.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.ManageKoreanStockUseCase
import rkrk.whyprice.member.application.port.input.dto.req.AddMemberKoreanStockDto
import rkrk.whyprice.member.application.port.input.dto.req.DeleteMemberKoreanStockDto
import rkrk.whyprice.member.application.port.input.dto.req.FindOrCreateKoreanStockDto
import rkrk.whyprice.member.application.port.out.FindOrCreateKoreanStockPort
import rkrk.whyprice.member.application.port.out.KoreanStockRepository
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.application.service.exception.DuplicateMemberKoreanStockException
import rkrk.whyprice.member.application.service.exception.NotExistsDeleteMemberKoreanStockException

@Service
@Transactional
class ManageKoreanStockService(
    private val memberRepository: MemberRepository,
    private val koreanStockRepository: KoreanStockRepository,
    private val findOrCreateKoreanStockPort: FindOrCreateKoreanStockPort,
) : ManageKoreanStockUseCase {
    @Transactional
    override fun addKoreanStock(stockDto: AddMemberKoreanStockDto) {
        val member = memberRepository.findByUserName(stockDto.memberName)
        val koreanStock =
            findOrCreateKoreanStockPort
                .findOrCreate(FindOrCreateKoreanStockDto(stockDto.stockName))

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
                ?: throw NotExistsDeleteMemberKoreanStockException("없는주식")
        )

        member.deleteKoreanStock(stock)
    }
}

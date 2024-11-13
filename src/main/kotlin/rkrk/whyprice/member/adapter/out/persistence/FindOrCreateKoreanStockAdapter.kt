package rkrk.whyprice.member.adapter.out.persistence

import org.springframework.stereotype.Repository
import rkrk.whyprice.member.application.port.input.dto.req.FindOrCreateKoreanStockDto
import rkrk.whyprice.member.application.port.out.FindKoreanStockByNamePort
import rkrk.whyprice.member.application.port.out.FindOrCreateKoreanStockPort
import rkrk.whyprice.member.application.port.out.KoreanStockRepository
import rkrk.whyprice.member.domain.KoreanStock

@Repository
class FindOrCreateKoreanStockAdapter(
    private val koreanStockRepository: KoreanStockRepository,
    private val findKoreanStockByNamePort: FindKoreanStockByNamePort,
) : FindOrCreateKoreanStockPort {
    override fun findOrCreate(dto: FindOrCreateKoreanStockDto): KoreanStock {
        var koreanStock =
            koreanStockRepository.findOneOrNull(dto.name)
        if (koreanStock == null) {
            koreanStock = findKoreanStockByNamePort.find(dto.name)
            koreanStockRepository.save(koreanStock)
        }
        return koreanStock
    }
}

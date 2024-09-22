package rkrk.whyprice.member.adapter.out.persistence

import org.springframework.stereotype.Repository
import rkrk.whyprice.member.application.port.out.KoreanStockRepository
import rkrk.whyprice.member.domain.KoreanStock

@Repository
class KoreanStockRepositoryImpl(
    private val jpaRepository: KoreanStockJpaRepository,
) : KoreanStockRepository {
    override fun findOne(name: String): KoreanStock =
        jpaRepository.findByName(name)
            ?: throw IllegalArgumentException("db에 존재하지 않는 주식입니다.")

    override fun findOneOrNull(name: String): KoreanStock? = jpaRepository.findByName(name)

    override fun exists(name: String): Boolean = jpaRepository.findByName(name) != null

    override fun save(koreanStock: KoreanStock) {
        jpaRepository.save(koreanStock)
    }
}

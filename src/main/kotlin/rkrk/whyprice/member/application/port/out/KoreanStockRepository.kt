package rkrk.whyprice.member.application.port.out

import rkrk.whyprice.member.domain.KoreanStock

interface KoreanStockRepository {
    fun findOne(name: String): KoreanStock

    fun findOneOrNull(name: String): KoreanStock?

    fun exists(name: String): Boolean

    fun save(koreanStock: KoreanStock)
}

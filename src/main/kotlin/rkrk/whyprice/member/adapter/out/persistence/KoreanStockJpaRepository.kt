package rkrk.whyprice.member.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import rkrk.whyprice.member.domain.KoreanStock

interface KoreanStockJpaRepository : JpaRepository<KoreanStock, Long> {
    fun findByName(name: String): KoreanStock?
}

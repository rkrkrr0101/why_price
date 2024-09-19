package rkrk.whyprice.member.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import rkrk.whyprice.member.application.port.out.CheckVolatilityPort

@Embeddable
class TrackedKoreanStocks {
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var koreanStocks = mutableListOf<KoreanStock>()
        protected set

    fun addKoreanStock(koreanStock: KoreanStock) {
        koreanStocks.add(koreanStock)
    }

    fun deleteKoreanStock(koreanStock: KoreanStock) {
        koreanStocks.find { it.name == koreanStock.name && it.crNo == koreanStock.crNo }?.let { koreanStocks.remove(it) }
    }

    fun hasVolatility(checkVolatilityPort: CheckVolatilityPort): List<KoreanStock> {
        val resList = mutableListOf<KoreanStock>()
        for (koreanStock in koreanStocks) {
            if (checkVolatilityPort.hasVolatility(koreanStock)) {
                resList.add(koreanStock)
            }
        }
        return resList
    }

    fun getAssets(): List<KoreanStock> = koreanStocks
}

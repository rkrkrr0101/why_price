package rkrk.whyprice.member.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import rkrk.whyprice.member.application.port.out.CheckVolatilityPort

@Embeddable
class TrackedKoreanStocks {
    @OneToMany(
        mappedBy = "member",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    var trackedKoreanStocks = mutableListOf<TrackedKoreanStock>()
        protected set

    fun addKoreanStock(
        koreanStock: KoreanStock,
        member: Member,
    ) {
        trackedKoreanStocks.add(TrackedKoreanStock(member, koreanStock))
    }

    fun deleteKoreanStock(koreanStock: KoreanStock) {
        // koreanStocks.find { it.name == koreanStock.name && it.crNo == koreanStock.crNo }?.let { koreanStocks.remove(it) }
        trackedKoreanStocks
            .find {
                it.koreanStock.name == koreanStock.name && it.koreanStock.crNo == koreanStock.crNo
            }?.let { trackedKoreanStocks.remove(it) }
    }

    fun hasVolatility(checkVolatilityPort: CheckVolatilityPort): List<KoreanStock> {
        val resList = mutableListOf<KoreanStock>()
        for (trackedKoreanStock in trackedKoreanStocks) {
            val koreanStock = trackedKoreanStock.koreanStock
            if (checkVolatilityPort.hasVolatility(koreanStock)) {
                resList.add(koreanStock)
            }
        }
        return resList
    }

    fun getAssets(): List<KoreanStock> = trackedKoreanStocks.map { it.koreanStock }
}

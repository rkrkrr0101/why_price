package rkrk.whyprice.domain.trackedAssets

import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.share.Asset
import rkrk.whyprice.share.Responser

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
        koreanStocks.remove(koreanStock)
    }

    fun hasVolatility(responser: Responser): List<KoreanStock> {
        val resList = mutableListOf<KoreanStock>()
        for (koreanStock in koreanStocks) {
            if (responser.hasVolatility(koreanStock)) {
                resList.add(koreanStock)
            }
        }
        return resList
    }

    fun getAssets(): List<KoreanStock> = koreanStocks
}

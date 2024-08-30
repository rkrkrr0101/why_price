package rkrk.whyprice.trackedAssets

import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import rkrk.whyprice.asset.KoreanStock
import rkrk.whyprice.share.Asset
import rkrk.whyprice.share.Responser

@Embeddable
class TrackedKoreanStocks(
    koreanStocks: MutableList<KoreanStock>,
) {
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var koreanStocks = koreanStocks
        private set

    fun addKoreanStock(koreanStock: KoreanStock) {
        koreanStocks.add(koreanStock)
    }

    fun deleteKoreanStock(koreanStock: KoreanStock) {
        koreanStocks.remove(koreanStock)
    }

    fun hasVolatility(responser: Responser): List<Asset> {
        val resList = mutableListOf<Asset>()
        for (koreanStock in koreanStocks) {
            if (responser.hasVolatility(koreanStock)) {
                resList.add(koreanStock)
            }
        }
        return resList
    }

    fun getAssets(): List<KoreanStock> = koreanStocks
}

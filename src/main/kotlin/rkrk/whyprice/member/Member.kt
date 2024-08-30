package rkrk.whyprice.member

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import rkrk.whyprice.asset.KoreanStock
import rkrk.whyprice.trackedAssets.TrackedKoreanStocks

@Entity
class Member(
    koreanStocks: TrackedKoreanStocks,
    id: Long = 0,
) {
    @Embedded
    var koreanStocks = koreanStocks
        private set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = id
        private set

    fun addKoreanStock(koreanStock: KoreanStock) {
        koreanStocks.addKoreanStock(koreanStock)
    }

    fun deleteKoreanStock(koreanStock: KoreanStock) {
        koreanStocks.deleteKoreanStock(koreanStock)
    }
}

package rkrk.whyprice.domain.member

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.domain.trackedAssets.TrackedKoreanStocks

@Entity
class Member(
    userName: String,
    id: Long = 0,
) {
    var userName = userName
        protected set

    @Embedded
    var koreanStocks = TrackedKoreanStocks()
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = id
        protected set

    fun addKoreanStock(koreanStock: KoreanStock) {
        koreanStocks.addKoreanStock(koreanStock)
    }

    fun deleteKoreanStock(koreanStock: KoreanStock) {
        koreanStocks.deleteKoreanStock(koreanStock)
    }

    fun getKoreanStocks(): List<KoreanStock> = koreanStocks.getAssets()

    fun getName(): String = userName
}

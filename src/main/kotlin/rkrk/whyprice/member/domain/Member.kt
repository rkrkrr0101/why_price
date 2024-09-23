package rkrk.whyprice.member.domain

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import rkrk.whyprice.member.application.port.out.CheckVolatilityPort

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
        koreanStocks.addKoreanStock(koreanStock, this)
    }

    fun deleteKoreanStock(koreanStock: KoreanStock) {
        koreanStocks.deleteKoreanStock(koreanStock, this)
    }

    fun existsKoreanStock(koreanStock: KoreanStock): Boolean = koreanStocks.existsKoreanStock(koreanStock, this)

    fun koreanStockHasVolatility(checkVolatilityPort: CheckVolatilityPort): List<KoreanStock> =
        koreanStocks.hasVolatility(checkVolatilityPort)

    fun getKoreanStocks(): List<KoreanStock> = koreanStocks.getAssets()

    fun getName(): String = userName
}

package rkrk.whyprice.domain.asset

import jakarta.persistence.Entity
import jakarta.persistence.Id
import rkrk.whyprice.share.Asset

@Entity
class KoreanStock(
    crNo: String,
    name: String,
    id: Long = 0,
) : Asset {
    var crNo = crNo
        protected set

    var name = name
        protected set

    @Id
    var id = id
        private set

    @Transient
    private var dataMap: MutableMap<String, String> = hashMapOf()

    override fun fetchData(assetFetchers: List<AssetFetcher>) {
        dataMap.clear()
        assetFetchers.forEach {
            val insertMap = it.fetch(crNo)
            insertData(insertMap)
        }
    }

    private fun insertData(insertMap: Map<String, String>) {
        dataMap += insertMap
    }

    override fun isDataEmpty(): Boolean = dataMap.isEmpty()

    override fun retrieveData(): Map<String, String> = dataMap

    override fun getIdentityCode(): String = crNo

    override fun getAssetName(): String = name

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as KoreanStock
//
//        if (crNo != other.crNo) return false
//        if (name != other.name) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = crNo.hashCode()
//        result = 31 * result + name.hashCode()
//        result = 31 * result + dataMap.hashCode()
//        return result
//    }
}

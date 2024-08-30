package rkrk.whyprice.asset

import jakarta.persistence.Entity
import jakarta.persistence.Id
import rkrk.whyprice.share.Asset

@Entity
class KoreanStock(
    private var crNo: String,
    private var name: String,
    id: Long = 0,
) : Asset {
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
}

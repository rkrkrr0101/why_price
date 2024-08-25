package rkrk.whyprice.asset

import rkrk.whyprice.trackedAssets.Asset

class KoreanStock(
    private val crNo: String,
) : Asset {
    private var dataMap = hashMapOf<String, String>()

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

    override fun getData(): Map<String, String> = dataMap

    override fun getIdentityCode(): String = crNo
}

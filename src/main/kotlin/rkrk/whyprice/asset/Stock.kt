package rkrk.whyprice.asset

import rkrk.whyprice.trackedAssets.Asset

class Stock(
    private val isinCode: String, // 단축코드 법인번호로 변경?
) : Asset {
    private var dataMap = hashMapOf<String, String>()

    override fun fetchData(assetFetchers: List<AssetFetcher>) {
        dataMap.clear()
        assetFetchers.forEach {
            val insertMap = it.fetch(isinCode)
            insertData(insertMap)
        }
    }

    private fun insertData(insertMap: Map<String, String>) {
        dataMap += insertMap
    }

    override fun isDataEmpty(): Boolean = dataMap.isEmpty()

    override fun getData(): Map<String, String> = dataMap

    override fun getIsinCode(): String = isinCode
}

package rkrk.whyprice.asset

import rkrk.whyprice.trackedAssets.Asset

class Stock(
    private val ticker: String,
) : Asset {
    private var dataMap = hashMapOf<String, String>()

    override fun fetchData(assetFetchers: List<AssetFetcher>) {
        dataMap.clear()
        assetFetchers.forEach {
            val (tempKey, tempValue) = it.fetch()
            dataMap[tempKey] = tempValue
        }
    }

    override fun isDataEmpty(): Boolean = dataMap.isEmpty()

    override fun getData(): Map<String, String> = dataMap

    override fun getTicker(): String = ticker
}

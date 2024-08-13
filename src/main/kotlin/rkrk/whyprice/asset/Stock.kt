package rkrk.whyprice.asset

import rkrk.whyprice.trackedAssets.Asset

class Stock(
    val ticker: String,
) : Asset {
    private val dataMap = hashMapOf<String, String>()

    override fun fetchData() {
        dataMap.clear()
        // 외부데이터 입력
    }

    override fun isDataEmpty(): Boolean = dataMap.isEmpty()

    override fun hasVolatility(): Boolean {
        TODO("Not yet implemented")
    }
}

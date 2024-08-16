package rkrk.whyprice.trackedAssets

import rkrk.whyprice.asset.AssetFetcher

interface Asset {
    fun fetchData(assetFetchers: List<AssetFetcher>)

    fun isDataEmpty(): Boolean

    fun getData(): Map<String, String>

    fun getIsinCode(): String
}

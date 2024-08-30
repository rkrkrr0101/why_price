package rkrk.whyprice.share

import rkrk.whyprice.asset.AssetFetcher

interface Asset {
    fun fetchData(assetFetchers: List<AssetFetcher>)

    fun isDataEmpty(): Boolean

    fun getData(): Map<String, String>

    fun getIdentityCode(): String

    fun getName(): String
}

package rkrk.whyprice.asset.domain

import rkrk.whyprice.domain.asset.AssetFetcher

interface Asset {
    fun fetchData(assetFetchers: List<AssetFetcher>)

    fun isDataEmpty(): Boolean

    fun retrieveData(): Map<String, String>

    fun getIdentityCode(): String

    fun getAssetName(): String
}

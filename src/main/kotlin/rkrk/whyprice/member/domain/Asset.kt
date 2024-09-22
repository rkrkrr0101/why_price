package rkrk.whyprice.member.domain

import rkrk.whyprice.member.adapter.out.api.assetfetcher.AssetFetcher

interface Asset {
    fun fetchData(assetFetchers: List<AssetFetcher>)

    fun isDataEmpty(): Boolean

    fun retrieveData(): Map<String, String>

    fun getIdentityCode(): String

    fun getAssetName(): String
}

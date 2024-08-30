package rkrk.whyprice.mock

import rkrk.whyprice.domain.asset.AssetFetcher

class AssetFetcherMockOne : AssetFetcher {
    override fun fetch(crNo: String): Map<String, String> = hashMapOf(Pair("testKey1", "testValue1"))
}

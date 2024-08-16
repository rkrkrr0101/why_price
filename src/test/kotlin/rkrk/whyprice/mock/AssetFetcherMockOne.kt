package rkrk.whyprice.mock

import rkrk.whyprice.asset.AssetFetcher

class AssetFetcherMockOne : AssetFetcher {
    override fun fetch(ticker: String): Map<String, String> = hashMapOf(Pair("testKey1", "testValue1"))
}

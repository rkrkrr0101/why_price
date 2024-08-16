package rkrk.whyprice.mock

import rkrk.whyprice.asset.AssetFetcher

class AssetFetcherMockTwo : AssetFetcher {
    override fun fetch(ticker: String): Map<String, String> = hashMapOf(Pair("testKey2", "testValue2"))
}

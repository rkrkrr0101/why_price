package rkrk.whyprice.mock

import rkrk.whyprice.member.adapter.out.api.assetfetcher.AssetFetcher

class AssetFetcherMockTwo : AssetFetcher {
    override fun fetch(queryKey: String): Map<String, String> = hashMapOf(Pair("testKey2", "testValue2"))
}

package rkrk.whyprice.tool.mock

import rkrk.whyprice.member.adapter.out.api.assetfetcher.AssetFetcher

class AssetFetcherMockOne : AssetFetcher {
    override fun fetch(queryKey: String): Map<String, String> = hashMapOf(Pair("testKey1", "testValue1"))
}

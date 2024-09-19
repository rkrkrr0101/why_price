package rkrk.whyprice.mock

import rkrk.whyprice.member.application.port.out.AssetFetcher

class AssetFetcherMockTwo : AssetFetcher {
    override fun fetch(crNo: String): Map<String, String> = hashMapOf(Pair("testKey2", "testValue2"))
}

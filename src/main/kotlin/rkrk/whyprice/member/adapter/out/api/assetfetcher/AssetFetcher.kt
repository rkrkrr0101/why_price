package rkrk.whyprice.member.adapter.out.api.assetfetcher

interface AssetFetcher {
    fun fetch(queryKey: String): Map<String, String>
}

package rkrk.whyprice.asset

interface AssetFetcher {
    fun fetch(isinCode: String): Map<String, String>
}

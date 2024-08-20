package rkrk.whyprice.asset

interface AssetFetcher {
    fun fetch(crNo: String): Map<String, String>
}

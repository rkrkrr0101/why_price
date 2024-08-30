package rkrk.whyprice.domain.asset

interface AssetFetcher {
    fun fetch(crNo: String): Map<String, String>
}

package rkrk.whyprice.asset

interface AssetFetcher  {
    fun fetch(): Pair<String, String>
}

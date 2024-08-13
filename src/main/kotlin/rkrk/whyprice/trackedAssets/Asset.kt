package rkrk.whyprice.trackedAssets

interface Asset {
    fun fetchData()

    fun isDataEmpty(): Boolean

    fun hasVolatility(): Boolean
}

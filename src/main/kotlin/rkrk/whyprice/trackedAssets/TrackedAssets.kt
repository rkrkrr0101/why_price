package rkrk.whyprice.trackedAssets

import rkrk.whyprice.common.Responser

class TrackedAssets {
    private val assets = mutableListOf<Asset>()

    fun addAsset(asset: Asset) {
        assets.add(asset)
    }

    fun deleteAsset(asset: Asset) {
        assets.remove(asset)
    }

    fun hasVolatility(responser: Responser): List<Asset> {
        val resList = mutableListOf<Asset>()
        for (asset in assets) {
            if (responser.hasVolatility(asset)) {
                resList.add(asset)
            }
        }
        return resList
    }

    fun getAssets(): List<Asset> = assets
}

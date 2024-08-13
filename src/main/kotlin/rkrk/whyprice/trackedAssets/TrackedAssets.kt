package rkrk.whyprice.trackedAssets

class TrackedAssets {
    private val assets = mutableListOf<Asset>()

    fun addAsset(asset: Asset) {
        assets.add(asset)
    }

    fun deleteAsset(asset: Asset) {
        assets.remove(asset)
    }

    fun checkVolatility(): List<Asset> {
        val resList = mutableListOf<Asset>()
        for (asset in assets) {
            if (asset.hasVolatility())
                {
                    resList.add(asset)
                }
        }
        return resList
    }
}

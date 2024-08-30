package rkrk.whyprice.member

import rkrk.whyprice.share.Asset
import rkrk.whyprice.trackedAssets.TrackedAssets

class Member {
    private val assets = TrackedAssets()

    fun addAsset(asset: Asset) {
        assets.addAsset(asset)
    }

    fun deleteAsset(asset: Asset) {
        assets.deleteAsset(asset)
    }
}

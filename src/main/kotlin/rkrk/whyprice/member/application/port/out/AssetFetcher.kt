package rkrk.whyprice.member.application.port.out

interface AssetFetcher {
    fun fetch(crNo: String): Map<String, String>
}

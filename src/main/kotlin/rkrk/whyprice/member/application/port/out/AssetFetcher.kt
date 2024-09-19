package rkrk.whyprice.member.application.port.out

// 멤버서비스의 포트인터페이스
interface AssetFetcher {
    fun fetch(crNo: String): Map<String, String>
}

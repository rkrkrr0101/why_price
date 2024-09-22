package rkrk.whyprice.member.application.port.out

import rkrk.whyprice.member.domain.KoreanStock

interface FindKoreanStockByNamePort {
    fun find(name: String): KoreanStock
}

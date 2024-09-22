package rkrk.whyprice.member.adapter.out.api

import rkrk.whyprice.member.adapter.out.api.assetfetcher.apifetcher.impl.FindCrnoByStockNameFetcher
import rkrk.whyprice.member.application.port.out.FindKoreanStockByNamePort
import rkrk.whyprice.member.domain.KoreanStock

class FindKoreanStockByNameAdapter(
    private val findStockFetcher: FindCrnoByStockNameFetcher,
) : FindKoreanStockByNamePort {
    override fun find(name: String): KoreanStock {
        val crno = findStockFetcher.fetch(name).values.first()
        return KoreanStock(crno, name)
    }
}

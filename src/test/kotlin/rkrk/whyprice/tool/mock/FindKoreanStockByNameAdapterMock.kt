package rkrk.whyprice.tool.mock

import rkrk.whyprice.member.application.port.out.FindKoreanStockByNamePort
import rkrk.whyprice.member.domain.KoreanStock

class FindKoreanStockByNameAdapterMock : FindKoreanStockByNamePort {
    override fun find(name: String): KoreanStock = KoreanStock("111111-1111111", name)
}

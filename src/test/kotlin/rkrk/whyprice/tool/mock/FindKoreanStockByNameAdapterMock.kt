package rkrk.whyprice.tool.mock

import rkrk.whyprice.member.application.port.out.FindKoreanStockByNamePort
import rkrk.whyprice.member.domain.KoreanStock
import java.util.UUID

class FindKoreanStockByNameAdapterMock : FindKoreanStockByNamePort {
    override fun find(name: String): KoreanStock = KoreanStock(UUID.randomUUID().toString(), name)
}

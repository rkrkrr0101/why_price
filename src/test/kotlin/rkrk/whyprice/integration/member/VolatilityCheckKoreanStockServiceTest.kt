package rkrk.whyprice.integration.member

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.dto.req.VolatilityMemberStocksDto
import rkrk.whyprice.member.application.port.out.KoreanStockRepository
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.application.service.VolatilityCheckKoreanStockService
import rkrk.whyprice.mock.CustomDateTimeMock
import rkrk.whyprice.mock.ResponserMock
import rkrk.whyprice.util.InitUtil

@SpringBootTest
@Transactional
class VolatilityCheckKoreanStockServiceTest
    @Autowired
    constructor(
        private val memberRepository: MemberRepository,
        private val koreanStockRepository: KoreanStockRepository,
    ) {
        private val responserMock = ResponserMock(CustomDateTimeMock("2021-10-10T10:10:10"))
        private val volatilityCheckService =
            VolatilityCheckKoreanStockService(memberRepository, responserMock)

        @Test
        @DisplayName("회원이 가지고있는 한국주식들중 변동성이 있는 주식들을 조회할수있다")
        fun fetchVolatility() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)

            val resDtos = volatilityCheckService.fetchVolatility(VolatilityMemberStocksDto("member1"))

            Assertions.assertThat(resDtos.size).isEqualTo(1)
            Assertions.assertThat(resDtos.map { it.assetName }.toList()).contains("삼성전자")
        }
    }

package rkrk.whyprice.integration.member

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.dto.req.ViewMemberStockDto
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.application.service.GetKoreanStockService
import rkrk.whyprice.util.InitUtil

@SpringBootTest
@Transactional
class GetKoreanStockServiceTest
    @Autowired
    constructor(
        private val memberRepository: MemberRepository,
    ) {
        private val getKoreanStockService = GetKoreanStockService(memberRepository)

        @Test
        @DisplayName("회원이 가지고있는 한국주식들을 조회할수있다")
        fun getKoreanStock() {
            InitUtil.basicMemberInit(memberRepository)

            val koreanStocks = getKoreanStockService.getKoreanStock(ViewMemberStockDto("member1"))

            Assertions.assertThat(koreanStocks.size).isEqualTo(2)
            Assertions.assertThat(koreanStocks.map { it.name }.toList()).contains("삼성전자", "LG전자")
        }
    }

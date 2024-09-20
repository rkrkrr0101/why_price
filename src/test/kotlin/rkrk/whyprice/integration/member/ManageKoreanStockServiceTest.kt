package rkrk.whyprice.integration.member

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.dto.req.AddMemberKoreanStockDto
import rkrk.whyprice.member.application.port.input.dto.req.DeleteMemberKoreanStockDto
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.application.service.ManageKoreanStockService
import rkrk.whyprice.util.InitUtil

@SpringBootTest
@Transactional
class ManageKoreanStockServiceTest
    @Autowired
    constructor(
        private val memberRepository: MemberRepository,
    ) {
        private val manageKoreanStockService = ManageKoreanStockService(memberRepository)

        // todo 중복일경우 에러띄우기
        @Test
        @DisplayName("회원이 가지고있는 한국주식을 추가할수있다")
        fun addKoreanStock() {
            InitUtil.basicMemberInit(memberRepository)

            manageKoreanStockService.addKoreanStock(
                AddMemberKoreanStockDto("member1", "111111-1111111", "비싼주식"),
            )
            val member = memberRepository.findByUserName("member1")

            Assertions.assertThat(member.getKoreanStocks().size).isEqualTo(3)
            Assertions.assertThat(member.getKoreanStocks().map { it.name }.toList()).contains("비싼주식")
        }

        // todo 없는주식일경우 에러띄우기
        @Test
        @DisplayName("회원이 가지고있는 한국주식을 삭제할수있다")
        fun deleteKoreanStock() {
            InitUtil.basicMemberInit(memberRepository)

            manageKoreanStockService.deleteKoreanStock(
                DeleteMemberKoreanStockDto("member1", "130111-0006246", "삼성전자"),
            )
            val member = memberRepository.findByUserName("member1")

            Assertions.assertThat(member.getKoreanStocks().size).isEqualTo(1)
            Assertions.assertThat(member.getKoreanStocks().map { it.name }.toList()).doesNotContain("삼성전자")
        }
    }

package rkrk.whyprice.integration

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.domain.asset.KoreanStock
import rkrk.whyprice.member.application.port.input.dto.req.MemberCreateDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockAddDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockDeleteDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberStockViewDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberVolatilityDto
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.application.service.MemberService
import rkrk.whyprice.member.domain.Member
import rkrk.whyprice.mock.CustomDateTimeMock
import rkrk.whyprice.mock.ResponserMock

@SpringBootTest
@Transactional
class MemberServiceTest
    @Autowired
    constructor(
        private val memberRepository: MemberRepository,
    ) {
        private val responser = ResponserMock(CustomDateTimeMock("2021-10-10T10:10:10"))
        private val memberService = MemberService(memberRepository, responser)

        @Test
        @DisplayName("회원을 생성할수있다")
        fun createMember() {
            basicInit()

            memberService.createMember(MemberCreateDto("newMember"))

            Assertions.assertThat(memberRepository.existsByName("newMember")).isTrue()
        }

        @Test
        @DisplayName("같은이름으로 회원을 생성할수없다")
        fun duplicateCreateMember() {
            basicInit()

            memberService.createMember(MemberCreateDto("newMember"))

            Assertions
                .assertThatThrownBy { memberService.createMember(MemberCreateDto("newMember")) }
                .isInstanceOf(IllegalArgumentException::class.java)
        }

        // todo 중복일경우 에러띄우기
        @Test
        @DisplayName("회원이 가지고있는 한국주식을 추가할수있다")
        fun addKoreanStock() {
            basicInit()

            memberService.addKoreanStock(
                MemberKoreanStockAddDto("member1", "111111-1111111", "비싼주식"),
            )
            val member = memberRepository.findByUserName("member1")

            Assertions.assertThat(member.getKoreanStocks().size).isEqualTo(3)
            Assertions.assertThat(member.getKoreanStocks().map { it.name }.toList()).contains("비싼주식")
        }

        // todo 없는주식일경우 에러띄우기
        @Test
        @DisplayName("회원이 가지고있는 한국주식을 삭제할수있다")
        fun deleteKoreanStock() {
            basicInit()

            memberService.deleteKoreanStock(
                MemberKoreanStockDeleteDto("member1", "130111-0006246", "삼성전자"),
            )
            val member = memberRepository.findByUserName("member1")

            Assertions.assertThat(member.getKoreanStocks().size).isEqualTo(1)
            Assertions.assertThat(member.getKoreanStocks().map { it.name }.toList()).doesNotContain("삼성전자")
        }

        @Test
        @DisplayName("회원이 가지고있는 한국주식들을 조회할수있다")
        fun getKoreanStock() {
            basicInit()

            val koreanStocks = memberService.getKoreanStock(MemberStockViewDto("member1"))

            Assertions.assertThat(koreanStocks.size).isEqualTo(2)
            Assertions.assertThat(koreanStocks.map { it.name }.toList()).contains("삼성전자", "LG전자")
        }

        @Test
        @DisplayName("회원이 가지고있는 한국주식들중 변동성이 있는 주식들을 조회할수있다")
        fun fetchVolatility() {
            basicInit()

            val koreanStocks = memberService.fetchVolatility(MemberVolatilityDto("member1"))

            Assertions.assertThat(koreanStocks.size).isEqualTo(1)
            Assertions.assertThat(koreanStocks.map { it.name }.toList()).contains("삼성전자")
        }

        private fun basicInit() {
            val member1 = Member("member1")
            member1.addKoreanStock(KoreanStock("130111-0006246", "삼성전자"))
            member1.addKoreanStock(KoreanStock("110111-2487050", "LG전자"))
            val member2 = Member("member2")
            member2.addKoreanStock(KoreanStock("110111-4594952", "이마트"))
            val member3 = Member("member3")
            memberRepository.save(member1)
            memberRepository.save(member2)
            memberRepository.save(member3)
        }
    }

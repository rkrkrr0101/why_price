package rkrk.whyprice.test.integration.member

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.adapter.out.persistence.FindOrCreateKoreanStockAdapter
import rkrk.whyprice.member.application.port.input.dto.req.AddMemberKoreanStockDto
import rkrk.whyprice.member.application.port.input.dto.req.DeleteMemberKoreanStockDto
import rkrk.whyprice.member.application.port.out.KoreanStockRepository
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.application.service.ManageKoreanStockService
import rkrk.whyprice.member.application.service.exception.DuplicateMemberKoreanStockException
import rkrk.whyprice.member.application.service.exception.NotExistsDeleteMemberKoreanStockException
import rkrk.whyprice.member.application.service.exception.NotExistsMemberException
import rkrk.whyprice.tool.mock.FindKoreanStockByNameAdapterMock
import rkrk.whyprice.tool.util.InitUtil

@SpringBootTest
@Transactional
class ManageKoreanStockServiceTest
    @Autowired
    constructor(
        private val memberRepository: MemberRepository,
        private val koreanStockRepository: KoreanStockRepository,
    ) {
        private val findOrCreateKoreanStockAdapter =
            FindOrCreateKoreanStockAdapter(koreanStockRepository, FindKoreanStockByNameAdapterMock())
        private val manageKoreanStockService =
            ManageKoreanStockService(
                memberRepository,
                koreanStockRepository,
                findOrCreateKoreanStockAdapter,
            )

        @Test
        @DisplayName("회원이 가지고있는 한국주식을 추가할수있다")
        fun addKoreanStock() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)

            manageKoreanStockService.addKoreanStock(
                AddMemberKoreanStockDto("member1", "비싼주식"),
            )
            val member = memberRepository.findByUserName("member1")

            Assertions.assertThat(member.getKoreanStocks().size).isEqualTo(3)
            Assertions.assertThat(member.getKoreanStocks().map { it.name }.toList()).contains("비싼주식")
        }

        @Test
        @DisplayName("회원이 이미 가지고있는 한국주식을 추가하면 예외가 발생한다")
        fun duplicateAddKoreanStock() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)

            manageKoreanStockService.addKoreanStock(
                AddMemberKoreanStockDto("member1", "비싼주식"),
            )
            Assertions
                .assertThatThrownBy {
                    manageKoreanStockService.addKoreanStock(
                        AddMemberKoreanStockDto("member1", "비싼주식"),
                    )
                }.isInstanceOf(DuplicateMemberKoreanStockException::class.java)
        }

        @Test
        @DisplayName("없는회원이 한국주식을 추가하면 예외가 발생한다")
        fun notExistsMemberAddKoreanStock() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)

            Assertions
                .assertThatThrownBy {
                    manageKoreanStockService.addKoreanStock(
                        AddMemberKoreanStockDto(
                            "notExistsMember",
                            "비싼주식",
                        ),
                    )
                }.isInstanceOf(NotExistsMemberException::class.java)
        }

        // todo 없는주식일경우 에러띄우기
        @Test
        @DisplayName("회원이 가지고있는 한국주식을 삭제할수있다")
        fun deleteKoreanStock() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)

            manageKoreanStockService.deleteKoreanStock(
                DeleteMemberKoreanStockDto("member1", "삼성전자"),
            )
            val member = memberRepository.findByUserName("member1")

            Assertions.assertThat(member.getKoreanStocks().size).isEqualTo(1)
            Assertions.assertThat(member.getKoreanStocks().map { it.name }.toList()).doesNotContain("삼성전자")
        }

        @Test
        @DisplayName("회원이 가지고있지않은 한국주식을 삭제하려고하면 예외가 발생한다")
        fun notExistsDeleteKoreanStock() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)

            Assertions
                .assertThatThrownBy {
                    manageKoreanStockService.deleteKoreanStock(
                        DeleteMemberKoreanStockDto(
                            "member1",
                            "없는주식",
                        ),
                    )
                }.isInstanceOf(NotExistsDeleteMemberKoreanStockException::class.java)
        }

        @Test
        @DisplayName("없는회원이 한국주식을 삭제하려고하면 예외가 발생한다")
        fun notExistsMemberDeleteKoreanStock() {
            InitUtil.basicMemberInit(memberRepository, koreanStockRepository)

            Assertions
                .assertThatThrownBy {
                    manageKoreanStockService.deleteKoreanStock(
                        DeleteMemberKoreanStockDto(
                            "notExistsMember",
                            "비싼주식",
                        ),
                    )
                }.isInstanceOf(NotExistsMemberException::class.java)
        }
    }

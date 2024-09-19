package rkrk.whyprice.integration.member

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.dto.req.RegisterMemberDto
import rkrk.whyprice.member.application.port.out.MemberRepository
import rkrk.whyprice.member.application.service.DuplicateCheckMemberServiceImpl
import rkrk.whyprice.member.application.service.RegisterMemberService
import rkrk.whyprice.util.InitUtil

@SpringBootTest
@Transactional
class RegisterMemberServiceTest
    @Autowired
    constructor(
        private val memberRepository: MemberRepository,
    ) {
        private val duplicateCheckService = DuplicateCheckMemberServiceImpl(memberRepository)
        private val registerMemberService = RegisterMemberService(memberRepository, duplicateCheckService)

        @Test
        @DisplayName("회원을 생성할수있다")
        fun createMember() {
            InitUtil.basicMemberInit(memberRepository)

            registerMemberService.registerMember(RegisterMemberDto("newMember"))

            Assertions.assertThat(memberRepository.existsByName("newMember")).isTrue()
        }

        @Test
        @DisplayName("같은이름으로 회원을 생성할수없다")
        fun duplicateCreateMember() {
            InitUtil.basicMemberInit(memberRepository)

            registerMemberService.registerMember(RegisterMemberDto("newMember"))

            Assertions
                .assertThatThrownBy { registerMemberService.registerMember(RegisterMemberDto("newMember")) }
                .isInstanceOf(IllegalArgumentException::class.java)
        }
    }

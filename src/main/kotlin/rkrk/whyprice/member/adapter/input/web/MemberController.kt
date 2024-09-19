package rkrk.whyprice.member.adapter.input.web

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.member.application.port.input.GetKoreanStockQuery
import rkrk.whyprice.member.application.port.input.ManageKoreanStockUseCase
import rkrk.whyprice.member.application.port.input.RegisterMemberUseCase
import rkrk.whyprice.member.application.port.input.VolatilityCheckKoreanStockQuery
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockAddDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockDeleteDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberStockViewDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberVolatilityDto
import rkrk.whyprice.member.application.port.input.dto.req.RegisterMemberDto
import rkrk.whyprice.member.application.port.input.dto.res.KoreanStockResponseDto
import rkrk.whyprice.share.Result

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val registerMemberUseCase: RegisterMemberUseCase,
    private val manageKoreanStockUseCase: ManageKoreanStockUseCase,
    private val getKoreanStockQuery: GetKoreanStockQuery,
    private val volatilityCheckKoreanStockQuery: VolatilityCheckKoreanStockQuery,
) {
    @PostMapping
    fun createMember(
        @RequestBody createDto: RegisterMemberDto,
    ) {
        registerMemberUseCase.registerMember(createDto)
    }

    @PostMapping("/stock")
    fun addKoreanStock(
        @RequestBody stockAddDto: MemberKoreanStockAddDto,
    ) {
        manageKoreanStockUseCase.addKoreanStock(stockAddDto)
    }

    @DeleteMapping("/stock")
    fun deleteKoreanStock(
        @RequestBody deleteDto: MemberKoreanStockDeleteDto,
    ) {
        manageKoreanStockUseCase.deleteKoreanStock(deleteDto)
    }

    @GetMapping("/stock")
    fun getKoreanStock(stockViewDto: MemberStockViewDto): Result<List<KoreanStockResponseDto>> {
        val stockDtos =
            getKoreanStockQuery
                .getKoreanStock(stockViewDto)
                .map { KoreanStockResponseDto(it.getIdentityCode(), it.getAssetName()) }
        return Result(stockDtos)
    }

    @GetMapping("/stock/volatility")
    fun fetchVolatility(memberVolatilityDto: MemberVolatilityDto): Result<List<KoreanStockResponseDto>> {
        val stockDtos =
            volatilityCheckKoreanStockQuery.fetchVolatility(memberVolatilityDto)
        return Result(stockDtos)
    }
}

package rkrk.whyprice.inputapi.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.inputapi.dto.member.req.MemberCreateDto
import rkrk.whyprice.inputapi.dto.member.req.MemberKoreanStockAddDto
import rkrk.whyprice.inputapi.dto.member.req.MemberKoreanStockDeleteDto
import rkrk.whyprice.inputapi.dto.member.req.MemberStockViewDto
import rkrk.whyprice.inputapi.dto.member.req.MemberVolatilityDto
import rkrk.whyprice.inputapi.dto.member.res.KoreanStockResponseDto
import rkrk.whyprice.inputapi.result.Result
import rkrk.whyprice.inputapi.usecase.MemberUseCase

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberUseCase: MemberUseCase,
) {
    @PostMapping
    fun createMember(
        @RequestBody createDto: MemberCreateDto,
    ) {
        memberUseCase.createMember(createDto)
    }

    @PostMapping("/stock")
    fun addKoreanStock(
        @RequestBody stockAddDto: MemberKoreanStockAddDto,
    ) {
        memberUseCase.addKoreanStock(stockAddDto)
    }

    @DeleteMapping("/stock")
    fun deleteKoreanStock(
        @RequestBody deleteDto: MemberKoreanStockDeleteDto,
    ) {
        memberUseCase.deleteKoreanStock(deleteDto)
    }

    // todo get 리퀘스트바디 변경
    @GetMapping("/stock")
    fun getKoreanStock(stockViewDto: MemberStockViewDto): Result<List<KoreanStockResponseDto>> {
        val stockDtos =
            memberUseCase
                .getKoreanStock(stockViewDto)
                .map { KoreanStockResponseDto(it.getIdentityCode(), it.getAssetName()) }
        return Result(stockDtos)
    }

    @GetMapping("/stock/volatility")
    fun fetchVolatility(memberVolatilityDto: MemberVolatilityDto): Result<List<KoreanStockResponseDto>> {
        val stockDtos =
            memberUseCase
                .fetchVolatility(memberVolatilityDto)
                .map { KoreanStockResponseDto(it.getIdentityCode(), it.getAssetName()) }
        return Result(stockDtos)
    }
}

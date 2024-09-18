package rkrk.whyprice.member.adapter.input.web

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rkrk.whyprice.member.adapter.input.web.dto.res.KoreanStockResponseDto
import rkrk.whyprice.member.application.port.input.MemberUseCase
import rkrk.whyprice.member.application.port.input.dto.req.MemberCreateDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockAddDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberKoreanStockDeleteDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberStockViewDto
import rkrk.whyprice.member.application.port.input.dto.req.MemberVolatilityDto
import rkrk.whyprice.share.Result

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

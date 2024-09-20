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
import rkrk.whyprice.member.application.port.input.dto.req.AddMemberKoreanStockDto
import rkrk.whyprice.member.application.port.input.dto.req.DeleteMemberKoreanStockDto
import rkrk.whyprice.member.application.port.input.dto.req.RegisterMemberDto
import rkrk.whyprice.member.application.port.input.dto.req.ViewMemberStockDto
import rkrk.whyprice.member.application.port.input.dto.req.VolatilityMemberStocksDto
import rkrk.whyprice.member.application.port.input.dto.res.ResponseKoreanStockDto
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
        @RequestBody stockAddDto: AddMemberKoreanStockDto,
    ) {
        manageKoreanStockUseCase.addKoreanStock(stockAddDto)
    }

    @DeleteMapping("/stock")
    fun deleteKoreanStock(
        @RequestBody deleteDto: DeleteMemberKoreanStockDto,
    ) {
        manageKoreanStockUseCase.deleteKoreanStock(deleteDto)
    }

    @GetMapping("/stock")
    fun getKoreanStock(stockViewDto: ViewMemberStockDto): Result<List<ResponseKoreanStockDto>> {
        val stockDtos =
            getKoreanStockQuery
                .getKoreanStock(stockViewDto)
                .map { ResponseKoreanStockDto(it.getIdentityCode(), it.getAssetName()) }
        return Result(stockDtos)
    }

    @GetMapping("/stock/volatility")
    fun fetchVolatility(volatilityMemberStocksDto: VolatilityMemberStocksDto): Result<List<ResponseKoreanStockDto>> {
        val stockDtos =
            volatilityCheckKoreanStockQuery.fetchVolatility(volatilityMemberStocksDto)
        return Result(stockDtos)
    }
}

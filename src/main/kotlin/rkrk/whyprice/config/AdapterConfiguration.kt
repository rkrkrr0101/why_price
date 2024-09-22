package rkrk.whyprice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import rkrk.whyprice.member.adapter.out.api.FindKoreanStockByNameAdapter
import rkrk.whyprice.member.adapter.out.api.assetfetcher.apifetcher.impl.FindCrnoByStockNameFetcher
import rkrk.whyprice.share.port.ApiHelper

@Configuration
class AdapterConfiguration {
    @Bean
    fun findKoreanStockByNameAdapter(apiHelper: ApiHelper): FindKoreanStockByNameAdapter =
        FindKoreanStockByNameAdapter(FindCrnoByStockNameFetcher(apiHelper))
}

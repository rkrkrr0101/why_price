package rkrk.whyprice.share

import rkrk.whyprice.report.Report

interface Responser {
    fun hasVolatility(asset: Asset): Boolean

    fun createReport(rankFetcher: RankFetcher): List<Report>

    fun createReport(asset: Asset): Report
}

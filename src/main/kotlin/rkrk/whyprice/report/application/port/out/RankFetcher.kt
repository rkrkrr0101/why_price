package rkrk.whyprice.report.application.port.out

interface RankFetcher {
    fun fetch(): List<String>
}

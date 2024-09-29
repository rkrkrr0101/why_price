package rkrk.whyprice.test.unit

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rkrk.whyprice.report.adapter.out.persistence.ReportCache
import rkrk.whyprice.report.domain.Report
import rkrk.whyprice.tool.mock.CustomDateTimeMock

class ReportCacheTest {
    @Test
    @DisplayName("저장된 시간이 30분 이상 이전이면 False를 반환한다")
    fun compareCacheTime() {
        val firstTime = CustomDateTimeMock("2021-10-10T10:10:10").getNow()
        val secondTime = CustomDateTimeMock("2021-10-10T10:50:10").getNow()
        val report =
            Report(
                "삼성전자",
                "삼성전자 report",
                firstTime,
            )
        val reportCache = ReportCache(report)

        Assertions.assertThat(reportCache.isValid(secondTime)).isFalse()
    }

    @Test
    @DisplayName("저장된 시간이 30분 이내면 true를 반환한다")
    fun validCacheTime() {
        val firstTime = CustomDateTimeMock("2021-10-10T10:10:10").getNow()
        val secondTime = CustomDateTimeMock("2021-10-10T10:20:10").getNow()
        val report =
            Report(
                "삼성전자",
                "삼성전자 report",
                firstTime,
            )
        val reportCache = ReportCache(report)

        Assertions.assertThat(reportCache.isValid(secondTime)).isTrue()
    }
}

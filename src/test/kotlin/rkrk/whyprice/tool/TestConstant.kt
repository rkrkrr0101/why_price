package rkrk.whyprice.tool

class TestConstant {
    companion object {
        val TEST_RANK_LIST =
            listOf(
                "삼성전자",
                "LG전자",
                "SK하이닉스",
                "네이버",
                "카카오",
                "삼성바이오로직스",
                "셀트리온",
                "현대차",
                "기아",
                "POSCO",
            )
        const val TEST_CURRENT_TIME = "2021-10-10T10:10:10"
        const val TEST_ONE_HOUR_EARLY_TIME = "2021-10-10T09:10:10"
        const val TEST_ONE_HOUR_LATE_TIME = "2021-10-10T11:10:10"
        const val TEST_TEN_MINUTE_EARLY_TIME = "2021-10-10T10:00:10"
        const val TEST_TEN_MINUTE_LATE_TIME = "2021-10-10T10:20:10"

        const val HAS_VOLATILITY_WORD = "삼성"
    }
}

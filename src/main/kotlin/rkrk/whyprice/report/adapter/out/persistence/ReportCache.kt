package rkrk.whyprice.report.adapter.out.persistence

import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import rkrk.whyprice.report.domain.Report
import java.time.LocalDateTime

@Entity
class ReportCache(
    report: Report,
    id: Long = 0,
) {
    @Embedded
    @AttributeOverrides(
        AttributeOverride(
            name = "report",
            column = Column(name = "report", columnDefinition = "TEXT"),
        ),
    )
    var report: Report = report
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = id
        protected set

    fun getCreateTime(): LocalDateTime = report.getCreateTime()

    fun getMainReport(): Report = report

    fun updateReport(report: Report) {
        this.report = report
    }

    fun isValid(dateTime: LocalDateTime): Boolean = report.getCreateTime().isAfter(dateTime.minusMinutes(30))
}

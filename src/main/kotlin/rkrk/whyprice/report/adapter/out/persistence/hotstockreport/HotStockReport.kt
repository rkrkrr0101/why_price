package rkrk.whyprice.report.adapter.out.persistence.hotstockreport

import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import rkrk.whyprice.report.domain.Report

@Entity
class HotStockReport(
    report: Report,
    id: Long=0,

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
}
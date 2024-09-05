package rkrk.whyprice.domain.report

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class ReportCaches(
    @Embedded
    private val report: Report,
    @Id
    private val id: Long = 0,
)

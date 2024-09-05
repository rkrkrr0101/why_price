package rkrk.whyprice.domain.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import rkrk.whyprice.domain.report.ReportCaches

interface ReportCachesJpaRepository : JpaRepository<ReportCaches, Long>

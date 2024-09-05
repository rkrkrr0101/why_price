package rkrk.whyprice.domain.report.repository

import org.springframework.stereotype.Repository
import rkrk.whyprice.domain.report.service.infra.ReportCachesRepository

@Repository
class ReportCachesRepositoryImpl(
    private val jpaRepository: ReportCachesJpaRepository,
) : ReportCachesRepository {
}

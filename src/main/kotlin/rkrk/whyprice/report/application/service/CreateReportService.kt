package rkrk.whyprice.report.application.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.yield
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rkrk.whyprice.member.application.port.input.dto.req.FindOrCreateKoreanStockDto
import rkrk.whyprice.member.application.port.out.FindOrCreateKoreanStockPort
import rkrk.whyprice.report.application.port.input.CreateReportUseCase
import rkrk.whyprice.report.application.port.input.dto.req.KoreanStockReportDto
import rkrk.whyprice.report.application.port.input.dto.res.ResponseReportDto
import rkrk.whyprice.report.application.port.out.CreateReportPort
import rkrk.whyprice.report.application.port.out.RankFetcher
import rkrk.whyprice.report.application.port.out.ReportCachesRepository
import rkrk.whyprice.report.domain.Report
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class CreateReportService(
    private val createReportPort: CreateReportPort,
    private val rankFetcher: RankFetcher,
    private val reportCachesRepository: ReportCachesRepository,
    private val findOrCreateKoreanStockPort: FindOrCreateKoreanStockPort,
) : CreateReportUseCase {
    private val semaphore = Semaphore(3)

    @Transactional
    override suspend fun fetchHighReports(): List<ResponseReportDto> =
        rankFetch()
            .mapAsync {
                semaphore.withPermit { createReport(it) }
            }.map { ResponseReportDto(it.getReportBody(), it.getCreateTime()) }

    @Transactional
    override suspend fun fetchHighReport(dto: KoreanStockReportDto): ResponseReportDto {
        supervisorScope {
            createReport(dto.stockName)
        }.let {
            return ResponseReportDto(it.getReportBody(), it.getCreateTime())
        }
    }

    private suspend fun createReport(assetName: String): Report =
        coroutineScope {
            if (reportCachesRepository.isCacheValid(assetName)) {
                reportCachesRepository.findOne(assetName).getMainReport()
            } else {
                val koreanStock =
                    findOrCreateKoreanStockPort.findOrCreate(FindOrCreateKoreanStockDto(assetName))
                println("$assetName 레포트생성진입시간:" + LocalDateTime.now().toString())
                val report = createReportPort.createReport(koreanStock.name)
                yield()
                reportCachesRepository.saveOrUpdate(report)
                report
            }
        }

    private fun rankFetch(): List<String> = rankFetcher.fetch()

    private suspend fun <T, R> List<T>.mapAsync(transform: suspend (T) -> R): List<R> =
        supervisorScope {
            this@mapAsync.map { async { transform(it) } }.awaitAll()
        }
}

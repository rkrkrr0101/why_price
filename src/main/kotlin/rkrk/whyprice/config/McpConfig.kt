package rkrk.whyprice.config

import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import rkrk.whyprice.report.adapter.input.web.ReportController

@Configuration
class McpConfig {
    @Bean
    fun reportTools(reportController: ReportController): ToolCallbackProvider {
        return MethodToolCallbackProvider.builder().toolObjects(reportController).build()
    }

}
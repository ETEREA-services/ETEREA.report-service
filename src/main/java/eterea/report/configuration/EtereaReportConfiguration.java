package eterea.report.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableFeignClients(basePackages = "eterea.report")
@PropertySource("classpath:config/eterea.properties")
public class EtereaReportConfiguration {
}

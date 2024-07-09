package eterea.report.api.rest.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "eterea.report.api.rest.client")
@PropertySource("classpath:config/eterea.properties")
public class EtereaReportConfiguration {
}

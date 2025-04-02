# ETEREA Report Service

[![ETEREA.report-service CI](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/maven.yml)
[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.20-blue.svg)](https://kotlinlang.org/)

## Overview

ETEREA Report Service is a microservice designed to handle report generation and management within the ETEREA ecosystem. Built with Spring Boot and Kotlin, it provides robust PDF generation capabilities, barcode/QR code support, and seamless integration with other microservices through Spring Cloud.

## Features

- PDF Generation using LibrePDF/OpenPDF
- Barcode and QR Code generation using ZXING
- Email service integration
- Service discovery with Netflix Eureka
- OpenAPI documentation
- Caching support with Caffeine
- Input validation
- Actuator endpoints for monitoring

## Prerequisites

- JDK 21
- Maven 3.6+
- Docker (optional, for containerized deployment)

## Tech Stack

- **Framework**: Spring Boot 3.4.4
- **Language**: Java 21, Kotlin 2.1.20
- **Cloud**: Spring Cloud 2024.0.1
- **Documentation**: SpringDoc OpenAPI 2.8.6
- **PDF Generation**: LibrePDF/OpenPDF 2.0.3
- **Barcode/QR**: ZXING 3.5.3
- **Caching**: Caffeine
- **Testing**: JUnit, Kotlin Test

## Getting Started

### Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/ETEREA-services/ETEREA.report-service.git
   cd ETEREA.report-service
   ```

2. Build the project:
   ```bash
   ./mvnw clean install
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### Docker Deployment

1. Build the Docker image:
   ```bash
   docker build -t eterea-report-service .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 eterea-report-service
   ```

For local development with Docker:
```bash
docker build -f Dockerfile.local -t eterea-report-service-local .
```

## Configuration

The service can be configured through `application.yml` or environment variables. Key configuration properties include:

- Server port
- Eureka client settings
- Email configuration
- Cache settings

## API Documentation

Once the service is running, you can access the OpenAPI documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Monitoring

The service exposes actuator endpoints for monitoring:
- Health: `/actuator/health`
- Metrics: `/actuator/metrics`
- Info: `/actuator/info`

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the terms specified in the LICENSE file.

## Support

For support, please contact the ETEREA development team or create an issue in the repository.

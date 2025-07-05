# Eterea Report Service

[![ETEREA.report-service CI](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/maven.yml)
[![Documentation](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/pages.yml/badge.svg)](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/pages.yml)
[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.20-blue.svg)](https://kotlinlang.org/)
[![Version](https://img.shields.io/badge/Version-1.2.0-blue.svg)](https://github.com/ETEREA-services/ETEREA.report-service)

A comprehensive reporting service for the Eterea platform that handles report generation, email notifications, and document management.

## Features

- **Report Generation**: Generate and distribute various types of reports
- **Email Notifications**: Configurable email delivery with support for BCC and reply-to addresses
- **PDF Handling**: Process and manage PDF documents
- **Integration**: Seamless integration with other Eterea services
- **Documentation**: Comprehensive API documentation and guides
- **Automated Documentation**: Daily updates via GitHub Actions
- **Wiki Integration**: Automated wiki generation and updates

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8 or higher
- PostgreSQL 14 or higher
- SMTP server for email notifications

### Installation

1. Clone the repository:
```bash
git clone https://github.com/eterea/report-service.git
cd report-service
```

2. Build the project:
```bash
mvn clean install
```

3. Configure the application:
```bash
cp src/main/resources/application.example.yml src/main/resources/application.yml
```

4. Update the configuration with your settings:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/eterea
    username: your_username
    password: your_password
  mail:
    host: smtp.example.com
    port: 587
    username: your_email@example.com
    password: your_password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

5. Run the application:
```bash
mvn spring-boot:run
```

## Configuration

### Email Service

The email service can be configured through the following properties:

```yaml
eterea:
  report:
    email:
      from: noreply@eterea.com
      reply-to: support@eterea.com
      bcc: monitoring@eterea.com
      validation:
        enabled: true
        pattern: ^[A-Za-z0-9+_.-]+@(.+)$
```

### Security

Security can be configured through the following properties:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://auth.eterea.com
```

## Documentation

The service documentation is available in multiple formats:

- **API Documentation**: Available at `/swagger-ui.html` when running locally
- **Technical Documentation**: Hosted on [GitHub Pages](https://eterea-services.github.io/ETEREA.report-service/)
- **Project Wiki**: Available in the [GitHub Wiki](https://github.com/ETEREA-services/ETEREA.report-service/wiki)
- **Development Guide**: See the [docs](docs/) directory for detailed guides

The documentation is automatically updated daily and on significant changes through our GitHub Actions workflow.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, please contact:
- Email: support@eterea.com
- Documentation: [Eterea Documentation](https://docs.eterea.com)
- Issue Tracker: [GitHub Issues](https://github.com/ETEREA-services/ETEREA.report-service/issues)
- Wiki: [Project Wiki](https://github.com/ETEREA-services/ETEREA.report-service/wiki)

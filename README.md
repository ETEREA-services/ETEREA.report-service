# Eterea Report Service

[![ETEREA.report-service Build JVM Image](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/maven.yml)
[![Documentation](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/pages.yml/badge.svg)](https://github.com/ETEREA-services/ETEREA.report-service/actions/workflows/pages.yml)
[![Java](https://img.shields.io/badge/Java-25-red.svg)](https://www.oracle.com/java/technologies/javase/jdk25-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Version](https://img.shields.io/badge/Version-2.1.2-blue.svg)](https://github.com/ETEREA-services/ETEREA.report-service)

Servicio de reportes para la plataforma Eterea: generación de reportes, notificaciones por email y gestión de documentos.

## Cambios recientes destacados

- **Arquitectura hexagonal completa:** Implementación de puertos y adaptadores con separación clara de dominio, aplicación e infraestructura
- **Motor de reportes reutilizable:** Nuevo PdfReportBuilder con soporte para headers/footers personalizados y page events
- **Casos de uso modernizados:** Implementación de GenerateInvoicePdfUseCase, SendInvoiceUseCase con patrones DDD
- **Modernización a records Java:** Conversión de modelos inmutables (Comprobante, ComprobanteAfip, Moneda) a records
- **Servicio de reportes de ejemplo:** SampleReportService con demostración de capacidades del motor de PDF
- **Correcciones CI/CD:** Pipeline actualizado para despliegue correcto de diagramas Mermaid
- **Actualización de dependencias:** Adición de commons-fileupload 1.6.0 y mantenimiento de Spring Boot 4.0.2, Java 25

## Requisitos

- Java 25 o superior
- Maven 3.8 o superior
- PostgreSQL 14+
- Servidor SMTP

## Instalación y configuración

1. Clona el repositorio:
   ```bash
   git clone https://github.com/ETEREA-services/ETEREA.report-service.git
   cd ETEREA.report-service
   ```
2. Compila el proyecto:
   ```bash
   mvn clean install
   ```
3. Configura la aplicación editando `src/main/resources/bootstrap.yml` y `config/eterea.properties`.

## Documentación y diagramas

- Diagramas actualizados en `docs/diagrams/mermaid/`.
- Documentación automática publicada vía GitHub Pages.
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

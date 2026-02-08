## [2.1.1] - 2026-02-08
- fix: Mejora en pipeline de documentación con Mermaid v10.9.1
- fix: Corrección de manejo de errores en renderizado de diagramas
- fix: Optimización de despliegue de diagramas en GitHub Pages
- docs: Actualización de diagramas con mayor detalle y claridad
- docs: Mejora en diagrama de componentes con descripciones extendidas
- docs: Enriquecimiento de diagrama de secuencia con flujo completo
- docs: Detallamiento de diagrama de clases con patrones y relaciones

## [2.1.0] - 2026-02-08
- feat: Implementación completa de arquitectura hexagonal con puertos y adaptadores
- feat: Nuevos casos de uso (GenerateInvoicePdfUseCase, SendInvoiceUseCase, GetInvoiceDataUseCase)
- feat: Motor de reportes PDF reutilizable (PdfReportBuilder, HeaderFooterPageEvent, PageSection)
- feat: Servicio de reportes de ejemplo (SampleReportService, SampleReportController)
- feat: Modernización de modelos a records Java (Comprobante, ComprobanteAfip, Moneda)
- feat: Repositorio de datos de factura con patrón adaptador (InvoiceDataRepositoryImpl)
- feat: Reorganización completa de paquetes de api.rest a estructura hexagonal
- fix: Corrección en pipeline CI/CD para despliegue correcto de diagramas Mermaid
- chore: Adición de dependencia commons-fileupload 1.6.0
- docs: Actualización de estructura de paquetes en documentación

## [2.0.0] - 2026-01-31
- feat: Refactor arquitectural completo a hexagonal (domain-driven design)
- feat: Simplificación de API con InvoiceDataClient agregando datos de factura
- feat: Eliminación de dependencias Kotlin y conversión de DTOs a Java
- feat: Mejora de FacturaPdfService con soporte directo para InvoiceData
- feat: Actualización de dependencias principales (Spring Boot 4.0.2, Java 25, Spring Cloud 2025.1.0)
- feat: Actualización de SpringDoc OpenAPI a 3.0.1 y Google ZXing a 3.5.4
- docs: Actualización de diagramas Mermaid (clases y secuencia)
- chore: Modificación de pipeline CI/CD para Java 25
- chore: Actualización de Dockerfile a Eclipse Temurin 25

## [1.1.0] - 2025-07-01
- feat(release): implementar version 1.0.0
- feat(release): implementar release 1.0.1
- feat(email): enhance email address handling
- feat(email): enhance email system and documentation
- feat(docs): enhance project documentation and dependencies

## [1.0.0] - 2025-06-30
- feat(release): implementar release 1.0.1
- feat(email): enhance email address handling
- feat(email): enhance email system and documentation
- feat(email): enhance email system and documentation
- feat(docs): enhance project documentation and dependencies
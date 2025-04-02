# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Initial project setup with Spring Boot 3.4.4
- Integration with Spring Cloud 2024.0.1
- PDF generation capabilities using LibrePDF/OpenPDF 2.0.3
- Barcode and QR code generation using ZXING 3.5.3
- Email service integration
- Service discovery with Netflix Eureka
- OpenAPI documentation with SpringDoc 2.8.6
- Caching support with Caffeine
- Input validation
- Actuator endpoints for monitoring
- Docker support with multi-stage builds
- CI/CD pipeline with GitHub Actions
- Configurable reply-to address for emails
- Enhanced email validation and error handling
- Comprehensive email service documentation
- Detailed logging for email operations

### Changed
- Updated to Java 21 for better performance and features
- Optimized Docker build process with multi-stage builds
- Improved Maven build configuration with Kotlin support
- Enhanced email BCC handling with individual address management
- Improved email address validation and trimming
- Updated email configuration examples
- Enhanced error messages for email failures

### Fixed
- Resolved native access warnings in Docker builds
- Fixed Kotlin compilation issues with Java 21
- Fixed BCC visibility issue in email sending
- Improved null handling in email addresses
- Corrected email recipient handling to prevent duplicate addresses

### Security
- Implemented secure configuration management
- Added actuator security endpoints
- Added email address validation and sanitization

## [0.0.1-SNAPSHOT] - 2024-04-02

### Added
- Initial project structure
- Basic Spring Boot configuration
- Docker configuration files
- GitHub Actions workflow for CI/CD

### Changed
- None (initial release)

### Deprecated
- None (initial release)

### Removed
- None (initial release)

### Fixed
- None (initial release)

### Security
- None (initial release) 
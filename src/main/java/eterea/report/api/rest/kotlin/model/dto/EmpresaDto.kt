package eterea.report.api.rest.kotlin.model.dto

data class EmpresaDto(

    var empresaId: Int? = null,
    var negocioId: Int? = null,
    var razonSocial: String? = null,
    var nombreFantasia: String? = null,
    var domicilio: String? = null,
    var telefono: String? = null,
    var cuit: String? = null,
    var ingresosBrutos: String? = null,
    var numeroEstablecimiento: String? = null,
    var sedeTimbrado: String? = null,
    var inicioActividades: String? = null,
    var condicionIva: String? = null,
    var unidadNegocio: Byte? = null,
    var diaInicial: Int? = null,
    var negocioIdComision: Int? = null,
    var conectaUnificado: Byte? = null,
    var certificado: String? = null

)

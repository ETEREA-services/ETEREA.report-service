package eterea.report.api.rest.kotlin.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.OffsetDateTime

data class ClienteSearchDto(

    var clienteId: Long? = null,
    var nombre: String? = null,
    var negocioId: Int? = null,
    var cuit: String = "",
    var razonSocial: String = "",
    var nombreFantasia: String? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaRestaurant: OffsetDateTime? = null,
    var cantidadPaxs: Int = 0,
    var tipoCliente: Int = 0,
    var domicilio: String = "",
    var telefono: String = "",
    var fax: String? = null,
    var email: String = "",
    var numeroMovil: String? = null,
    var posicionIva: Int = 0,
    var constante: Int = 0,
    var documentoId: Int = 0,
    var tipoDocumento: String = "",
    var numeroDocumento: String? = null,
    var limiteCredito: BigDecimal = BigDecimal.ZERO,
    var nacionalidad: String? = null,
    var clienteCategoriaId: Int? = null,
    var impositivoId: String = "",
    var facturarExtranjero: Byte = 0,
    var bloqueado: Byte = 0,
    var discapacitado: Byte = 0,
    var search: String? = null

)

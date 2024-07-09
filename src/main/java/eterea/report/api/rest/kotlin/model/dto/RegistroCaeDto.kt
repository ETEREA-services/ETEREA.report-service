package eterea.report.api.rest.kotlin.model.dto

import java.math.BigDecimal

data class RegistroCaeDto(

    var registroCaeId: Long? = null,
    var comprobanteId: Int? = null,
    var puntoVenta: Int? = null,
    var numeroComprobante: Long? = null,
    var clienteId: Long? = null,
    var cuit: String? = null,
    var total: BigDecimal = BigDecimal.ZERO,
    var exento: BigDecimal = BigDecimal.ZERO,
    var neto: BigDecimal = BigDecimal.ZERO,
    var neto105: BigDecimal = BigDecimal.ZERO,
    var iva: BigDecimal = BigDecimal.ZERO,
    var iva105: BigDecimal = BigDecimal.ZERO,
    var cae: String? = null,
    var fecha: String? = null,
    var caeVencimiento: String? = null,
    var barras: String = "",
    var tipoDocumento: Int? = null,
    var numeroDocumento: BigDecimal = BigDecimal.ZERO,
    var clienteMovimientoIdAsociado: Long? = null

)

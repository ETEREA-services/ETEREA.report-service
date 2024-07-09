package eterea.report.api.rest.kotlin.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.OffsetDateTime

data class ClienteMovimientoDto(

    var clienteMovimientoId: Long? = null,
    var comprobanteId: Int? = null,
    var puntoVenta: Int = 0,
    var numeroComprobante: Long = 0L,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaComprobante: OffsetDateTime? = null,
    var clienteId: Long? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaVencimiento: OffsetDateTime? = null,
    var negocioId: Int? = null,
    var empresaId: Int? = null,
    var importe: BigDecimal = BigDecimal.ZERO,
    var cancelado: BigDecimal = BigDecimal.ZERO,
    var neto: BigDecimal = BigDecimal.ZERO,
    var netoCancelado: BigDecimal = BigDecimal.ZERO,
    var montoIva: BigDecimal = BigDecimal.ZERO,
    var montoIvaRni: BigDecimal = BigDecimal.ZERO,
    var reintegroTurista: BigDecimal = BigDecimal.ZERO,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaContable: OffsetDateTime? = null,
    var ordenContable: Int = 0,
    var recibo: Byte = 0,
    var asignado: Byte = 0,
    var anulada: Byte = 0,
    var decreto104316: Byte = 0,
    var letraComprobante: String? = null,
    var montoExento: BigDecimal = BigDecimal.ZERO,
    var reservaId: Long? = null,
    var montoCuentaCorriente: BigDecimal = BigDecimal.ZERO,
    var cierreCajaId: Long = 0,
    var cierreRestaurantId: Long = 0,
    var nivel: Int = 0,
    var eliminar: Byte = 0,
    var cuentaCorriente: Byte = 0,
    var letras: String = "",
    var cae: String = "",
    var caeVencimiento: String = "",
    var codigoBarras: String = "",
    var participacion: BigDecimal = BigDecimal.ZERO,
    var monedaId: Int? = null,
    var cotizacion: BigDecimal = BigDecimal.ZERO,
    var observaciones: String? = null,
    var clienteMovimientoIdSlave: Long = 0,
    var comprobante: ComprobanteDto? = null,
    var cliente: ClienteDto? = null,
    var moneda: MonedaDto? = null

)

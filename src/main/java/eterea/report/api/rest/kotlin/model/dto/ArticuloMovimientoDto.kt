package eterea.report.api.rest.kotlin.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.OffsetDateTime

data class ArticuloMovimientoDto(

    var articuloMovimientoId: Long? = null,
    var clienteMovimientoId: Long? = null,
    var stockMovimientoId: Long = 0,
    var tenenciaMovimientoId: Long = 0,
    var centroStockId: Int? = null,
    var comprobanteId: Int? = null,
    var item: Int? = 0,
    var articuloId: String? = null,
    var negocioId: Int? = null,
    var cantidad: BigDecimal? = BigDecimal.ZERO,
    var precioUnitario: BigDecimal? = BigDecimal.ZERO,
    var precioUnitarioSinIva: BigDecimal? = BigDecimal.ZERO,
    var precioUnitarioConIva: BigDecimal? = BigDecimal.ZERO,
    var numeroCuenta: Long? = null,
    var iva105: Byte? = 0,
    var exento: Byte? = 0,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaMovimiento: OffsetDateTime? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaFactura: OffsetDateTime? = null,
    var nivel: Int? = 0,
    var cierreCajaId: Long = 0,
    var cierreRestaurantId: Long = 0,
    var precioCompra: BigDecimal? = BigDecimal.ZERO,
    var precioValuacion: BigDecimal? = BigDecimal.ZERO,
    var mozoId: Long = 0,
    var comision: BigDecimal? = BigDecimal.ZERO,
    var total: BigDecimal? = BigDecimal.ZERO

)

package eterea.report.api.rest.kotlin.model.dto

import java.math.BigDecimal

data class ArticuloDto(

    var articuloId: String? = null,
    var negocioId: Int? = null,
    var descripcion: String = "",
    var leyendaVoucher: String = "",
    var precioVentaSinIva: BigDecimal = BigDecimal.ZERO,
    var precioVentaConIva: BigDecimal = BigDecimal.ZERO,
    var cuentaVentas: Long? = null,
    var cuentaCompras: Long? = null,
    var cuentaGastos: Long? = null,
    var centroStockId: Int? = null,
    var rubroId: Long? = null,
    var subRubroId: Long? = null,
    var precioCompra: BigDecimal = BigDecimal.ZERO,
    var iva105: Byte = 0,
    var precioCompraNeto: BigDecimal = BigDecimal.ZERO,
    var exento: Byte = 0,
    var stockMinimo: Long = 0L,
    var stockOptimo: Long = 0L,
    var bloqueoCompras: Byte = 0,
    var bloqueoStock: Byte = 0,
    var bloqueoVentas: Byte = 0,
    var unidadMedidaId: Long? = null,
    var conDecimales: Byte = 0,
    var ventas: Byte = 0,
    var compras: Byte = 0,
    var unidadMedida: String = "",
    var conversionId: Int? = null,
    var ventaSinStock: Byte = 0,
    var controlaStock: Byte = 0,
    var asientoCostos: Byte = 0,
    var mascaraBalanza: String = "",
    var habilitaIngreso: Byte = 0,
    var comision: BigDecimal = BigDecimal.ZERO,
    var prestadorId: Int? = null,
    var autoNumericoId: Long? = null

)

package eterea.report.api.rest.kotlin.model.dto

data class ComprobanteDto(

    var comprobanteId: Int? = null,
    var descripcion: String = "",
    var negocioId: Int? = null,
    var modulo: Int? = null,
    var stock: Byte = 0,
    var rendicion: Byte = 0,
    var ordenPago: Byte = 0,
    var aplicaPendiente: Byte = 0,
    var cuentaCorriente: Byte = 0,
    var debita: Byte = 0,
    var iva: Byte = 0,
    var ganancias: Byte = 0,
    var aplicable: Byte = 0,
    var libroIva: Byte = 0,
    var letraComprobante: String? = null,
    var recibo: Byte = 0,
    var contado: Byte = 0,
    var transferencia: Byte = 0,
    var imprime: Byte = 0,
    var comprobanteIdAnulacion: Int? = null,
    var centroStockIdEntrega: Int? = null,
    var centroStockIdRecibe: Int? = null,
    var diasVigencia: Int? = null,
    var concepto: Byte = 0,
    var personal: Byte = 0,
    var comanda: Byte = 0,
    var puntoVenta: Int = 0,
    var codigoMozo: Byte = 0,
    var transferir: Byte = 0,
    var numeroCuenta: Long? = null,
    var nivel: Int = 0,
    var fiscalizadora: Byte = 0,
    var invisible: Byte = 0,
    var comprobanteAfipId: Int? = null,
    var facturaElectronica: Byte = 0,
    var asociado: Byte = 0,
    var cuenta: CuentaDto? = null

)

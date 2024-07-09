package eterea.report.api.rest.kotlin.model.dto

data class ConceptoFacturadoDto(

    var conceptoFacturadoId: Long? = null,
    var clienteMovimientoId: Long? = null,
    var numeroLinea: Int? = null,
    var concepto: String? = "",
    var articuloMovimientoId: Long? = null,

    )

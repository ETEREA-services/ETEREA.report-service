package eterea.report.factura.domain.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ArticuloMovimiento {

    private BigDecimal cantidad;
    private BigDecimal precioUnitarioSinIva;
    private BigDecimal precioUnitarioConIva;
    private BigDecimal precioTotalSinIva;
    private BigDecimal precioTotalConIva;
    private Articulo articulo;
    private ConceptoFacturado conceptoFacturado;

}

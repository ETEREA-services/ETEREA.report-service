package eterea.report.factura.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ClienteMovimiento {

    private String letraComprobante;
    private Integer puntoVenta;
    private Long numeroComprobante;
    private String observaciones;
    private String letras;
    private Long reservaId;
    private BigDecimal neto;
    private BigDecimal montoExento;
    private BigDecimal montoIva;
    private BigDecimal montoIvaRni;
    private BigDecimal importe;
    private Empresa empresa;
    private Cliente cliente;
    private Moneda moneda;
    private Comprobante comprobante;
    private List<ArticuloMovimiento> articulos;

}

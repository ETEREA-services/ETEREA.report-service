package eterea.report.api.rest.factura.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RegistroCae {

    private Integer tipoDocumento;
    private Integer puntoVenta;
    private Integer comprobanteId;
    private Long numeroComprobante;
    private BigDecimal total;
    private BigDecimal numeroDocumento;
    private String cae;
    private String caeVencimiento;
    private String fecha;
    private Comprobante comprobante;

}

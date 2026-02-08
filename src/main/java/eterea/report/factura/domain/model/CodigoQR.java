package eterea.report.factura.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodigoQR {

    private int ver = 1;
    private String fecha = "";
    private String cuit = "";
    private int ptoVta = 0;
    private int tipoCmp = 0;
    private long nroCmp = 0;
    private BigDecimal importe = BigDecimal.ZERO;
    private String moneda = "";
    private int ctz = 1;
    private int tipoDocRec = 0;
    private BigDecimal nroDocRec = BigDecimal.ZERO;
    private String tipoCodAut = "E";
    private String codAut = "";

}

package eterea.report.api.rest.factura.domain.model;

import lombok.Getter;

@Getter
public class Comprobante {

    private String letraComprobante;
    private Byte contado;
    private ComprobanteAfip comprobanteAfip;

}

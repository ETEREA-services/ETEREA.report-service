package eterea.report.factura.domain.model;

import eterea.report.tool.Jsonifier;
import lombok.Getter;

@Getter
public class InvoiceData {

    ClienteMovimiento clienteMovimiento;
    RegistroCae registroCae;
    ClienteMovimiento clienteMovimientoAsociado;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}

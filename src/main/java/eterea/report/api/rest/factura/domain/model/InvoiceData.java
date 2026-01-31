package eterea.report.api.rest.factura.domain.model;

import lombok.Getter;

@Getter
public class InvoiceData {

    ClienteMovimiento clienteMovimiento;
    RegistroCae registroCae;
    ClienteMovimiento clienteMovimientoAsociado;

}

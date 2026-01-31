package eterea.report.api.rest.factura.domain.model;

import lombok.Getter;

@Getter
public class Cliente {

    private String razonSocial;
    private String domicilio;
    private String cuit;
    private Integer posicionIva;
    private String email;

}

package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface DatosPagoDto extends IsSerializable {
    FormaPagoDto formaPagoAsociada();
    Long getId();
    boolean isEfectivo();
    boolean isDebitoCuentaBancaria();
    boolean isDebitoTarjetaCredito();
}

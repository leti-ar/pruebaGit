package ar.com.nextel.sfa.client.dto;

public interface DatosPagoDto {
    FormaPagoDto formaPagoAsociada();
    boolean isEfectivo();
    boolean isDebitoCuentaBancaria();
    boolean isDebitoTarjetaCredito();
}

package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosDebitoTarjetaCreditoDto  extends AbstractDatosPagoDto implements IsSerializable {
    private String numero;
    private TipoTarjetaDto tipoTarjeta;
    private Short anoVencimientoTarjeta;
    private Short mesVencimientoTarjeta;
    private FormaPagoDto formaPagoAsociada;
    public boolean isDebitoTarjetaCredito() {
        return true;
    }
    public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public TipoTarjetaDto getTipoTarjeta() {
		return tipoTarjeta;
	}
	public void setTipoTarjeta(TipoTarjetaDto tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
	public Short getAnoVencimientoTarjeta() {
		return anoVencimientoTarjeta;
	}
	public void setAnoVencimientoTarjeta(Short anoVencimientoTarjeta) {
		this.anoVencimientoTarjeta = anoVencimientoTarjeta;
	}
	public Short getMesVencimientoTarjeta() {
		return mesVencimientoTarjeta;
	}
	public void setMesVencimientoTarjeta(Short mesVencimientoTarjeta) {
		this.mesVencimientoTarjeta = mesVencimientoTarjeta;
	}
	public FormaPagoDto getFormaPagoAsociada() {
		return formaPagoAsociada;
	}
	public FormaPagoDto formaPagoAsociada() {
		return formaPagoAsociada;
	}
	public void setFormaPagoAsociada(FormaPagoDto formaPagoAsociada) {
		this.formaPagoAsociada = formaPagoAsociada;
	}
}

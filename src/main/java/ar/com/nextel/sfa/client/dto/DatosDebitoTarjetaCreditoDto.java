package ar.com.nextel.sfa.client.dto;

import ar.com.nextel.sfa.client.enums.TipoFormaPagoEnum;

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
	public FormaPagoDto formaPagoAsociada() {
    	if (formaPagoAsociada==null) {
    		formaPagoAsociada = new FormaPagoDto();
    		formaPagoAsociada.setId(Long.parseLong(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo()));
    		formaPagoAsociada.setDescripcion(TipoFormaPagoEnum.TARJETA_CREDITO.getDesc());
    	}
		return formaPagoAsociada;
	}
	public void setFormaPagoAsociada(FormaPagoDto formaPagoAsociada) {
		this.formaPagoAsociada = formaPagoAsociada;
	}
    public Long getId() {
    	return this.id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
}

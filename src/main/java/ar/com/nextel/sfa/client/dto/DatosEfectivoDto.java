package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosEfectivoDto extends AbstractDatosPagoDto implements IsSerializable {

	private FormaPagoDto formaPagoAsociada;
    @Override
    public boolean isEfectivo() {
        return true;
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

package ar.com.nextel.sfa.client.dto;

import ar.com.nextel.sfa.client.enums.TipoFormaPagoEnum;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosEfectivoDto extends AbstractDatosPagoDto implements IsSerializable {

	private FormaPagoDto formaPagoAsociada;
    @Override
    public boolean isEfectivo() {
        return true;
    }
//	public FormaPagoDto getFormaPagoAsociada() {
//		return formaPagoAsociada;
//	}
	public FormaPagoDto formaPagoAsociada() {
    	if (formaPagoAsociada==null) {
    		formaPagoAsociada = new FormaPagoDto();
    		formaPagoAsociada.setId(Long.parseLong(TipoFormaPagoEnum.EFECTIVO.getTipo()));
    		formaPagoAsociada.setDescripcion(TipoFormaPagoEnum.EFECTIVO.getDesc());
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

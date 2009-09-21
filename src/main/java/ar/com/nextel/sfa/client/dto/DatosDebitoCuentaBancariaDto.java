package ar.com.nextel.sfa.client.dto;

import ar.com.nextel.sfa.client.enums.TipoFormaPagoEnum;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosDebitoCuentaBancariaDto extends AbstractDatosPagoDto implements IsSerializable {
    private String cbu;
    private TipoCuentaBancariaDto tipoCuentaBancaria;
    private FormaPagoDto formaPagoAsociada;

    /**
     * @see ar.com.nextel.model.cuentas.beans.AbstractDatosPago#isDebitoCuentaBancaria()
     */
    @Override
    public boolean isDebitoCuentaBancaria() {
        return true;
    }
    public String getCbu() {
		return cbu;
	}
	public void setCbu(String cbu) {
		this.cbu = cbu;
	}
	public TipoCuentaBancariaDto getTipoCuentaBancaria() {
		return tipoCuentaBancaria;
	}
	public void setTipoCuentaBancaria(TipoCuentaBancariaDto tipoCuentaBancaria) {
		this.tipoCuentaBancaria = tipoCuentaBancaria;
	}
	public FormaPagoDto formaPagoAsociada() {
    	if (formaPagoAsociada==null) {
    		formaPagoAsociada = new FormaPagoDto();
    		formaPagoAsociada.setId(Long.parseLong(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo()));
    		formaPagoAsociada.setDescripcion(TipoFormaPagoEnum.CUENTA_BANCARIA.getDesc());
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

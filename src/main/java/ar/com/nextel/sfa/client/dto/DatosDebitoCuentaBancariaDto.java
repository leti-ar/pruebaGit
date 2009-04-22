package ar.com.nextel.sfa.client.dto;

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

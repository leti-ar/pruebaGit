package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TerminoPagoDto extends EnumDto implements IsSerializable {

//  MGR - Facturacion
    private boolean cuentaCorriente;
    
	public boolean isCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(boolean cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}
}

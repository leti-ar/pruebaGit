package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TerminoPagoDto extends EnumDto implements IsSerializable {

//    MGR - Cta. Cte. 3 cuotas
    /**
     * validaPin
     */
    private boolean validaPin;
    
	public boolean isValidaPin() {
		return validaPin;
	}

	public void setValidaPin(boolean validaPin) {
		this.validaPin = validaPin;
	}
}

package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TerminoPagoDto extends EnumDto implements IsSerializable {
	
    // ----------------------------------------------------------
    // Instance variables
    //

    /**
     * codigoFNCL
     */
    private String codigoFNCL;
    
//    MGR - Cta. Cte. 3 cuotas
    /**
     * validaPin
     */
    private boolean validaPin;
    
    // ----------------------------------------------------------
    // Public methods
    // 
    
	/**
     * @return
     */
    public String getCodigoFNCL() {
		return codigoFNCL;
	}

    /**
     * @param codigoFNCL
     */
	public void setCodigoFNCL(String codigoFNCL) {
		this.codigoFNCL = codigoFNCL;
	}

	public boolean isValidaPin() {
		return validaPin;
	}

	public void setValidaPin(boolean validaPin) {
		this.validaPin = validaPin;
	}
}

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
    
	//CRSfaVta3Cuotcc:
    /**
     * validaPin
     */
    private String validaPin;
    
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

	//CRSfaVta3Cuotcc:
	/**
     * @return
     */
	public String getValidaPin() {
		return validaPin;
	}

	//CRSfaVta3Cuotcc: se agrega una columna mas a la tabla para forzar a validar por PIN por SFA_TERMINO_PAGO 
    /**
     * @param validaPin
     */
	public void setValidaPin(String validaPin) {
		this.validaPin = validaPin;
	}

	
}

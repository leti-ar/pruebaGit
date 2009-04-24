package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class TipoDomicilioAsociadoDto implements IsSerializable{

    private long idTipoDomicilio;
    private Boolean principal;
	
    public long getIdTipoDomicilio() {
		
    	return idTipoDomicilio;
	}
	public void setIdTipoDomicilio(long idTipoDomicilio) {
		this.idTipoDomicilio = idTipoDomicilio;
	}
	public Boolean getPrincipal() {
		return principal;
	}
	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}
}

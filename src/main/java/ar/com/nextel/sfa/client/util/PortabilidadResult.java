package ar.com.nextel.sfa.client.util;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PortabilidadResult  implements IsSerializable{
	private boolean conError;
	private boolean permiteGrabar;
	private String descripcionError;
	
	public PortabilidadResult(){
		
	}

	public PortabilidadResult(boolean conError,boolean permiteGrabar,String descripcionError){
		this.conError = conError;
		this.permiteGrabar = permiteGrabar;
		this.descripcionError = descripcionError;
	}

	public boolean isConError() {
		return conError;
	}

	public void setConError(boolean conError) {
		this.conError = conError;
	}

	public boolean getPermiteGrabar() {
		return permiteGrabar;
	}

	public void setPermiteGrabar(boolean permiteGrabar) {
		this.permiteGrabar = permiteGrabar;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
}

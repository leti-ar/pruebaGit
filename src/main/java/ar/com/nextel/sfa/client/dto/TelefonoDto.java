package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TelefonoDto implements IsSerializable {

	private TipoTelefonoDto tipoTelefono;
	private String numeroLocal;
	private String area;
	private String interno;
	private Boolean principal = Boolean.FALSE;
	private PersonaDto persona;

	public TelefonoDto() {
	}

	public TipoTelefonoDto getTipoTelefono() {
		return tipoTelefono;
	}

	public void setTipoTelefono(TipoTelefonoDto tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}

	public String getNumeroLocal() {
		return numeroLocal;
	}

	public void setNumeroLocal(String numeroLocal) {
		this.numeroLocal = numeroLocal;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}

}

package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TelefonoDto implements IsSerializable , IdentifiableDto, Cloneable {

	private Long id; 
	private TipoTelefonoDto tipoTelefono;
	private String numeroLocal;
	private String area;
	private String interno;
	private Boolean principal = Boolean.FALSE;
	//private PersonaDto persona;
    private Long idPersona;
    
    private boolean enCarga = true;
    
	public TelefonoDto() {}
	
	public TelefonoDto(String area, String interno, String numeroLocal,
			Long idPersona, Boolean principal, TipoTelefonoDto tipoTelefono) {
		super();
		this.area = area;
		this.interno = interno;
		this.numeroLocal = numeroLocal;
		//this.persona = persona;
		this.idPersona = idPersona;
		this.principal = principal;
		this.tipoTelefono = tipoTelefono;
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
//	public PersonaDto getPersona() {
//		return persona;
//	}
//	public void setPersona(PersonaDto persona) {
//		this.persona = persona;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	
	public boolean isEnCarga() {
		return enCarga;
	}
	public void setEnCarga(boolean enCarga) {
		this.enCarga = enCarga;
	}
	
}

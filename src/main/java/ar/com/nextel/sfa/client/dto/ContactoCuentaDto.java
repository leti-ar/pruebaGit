package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContactoCuentaDto implements IsSerializable {

	private Long id;
	private PersonaDto persona;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PersonaDto getPersona() {
		return persona;
	}
	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}
	
	public String getApellido() {
		return persona.getApellido();
	}
	
	public String getNombre() {
		return persona.getNombre();
	}
	
	public String getNumeroDocumento() {
		return persona.getDocumento().getNumero();
	}
	
	public TipoDocumentoDto getTipoDocumento() {
		return persona.getDocumento().getTipoDocumento();
	}
	
	public List<EmailDto> getEmails() {
		return persona.getEmails();
	}
	
	
}

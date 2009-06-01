package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContactoDto implements IsSerializable {

	private Long id;
	private PersonaDto personaDto;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PersonaDto getPersonaDto() {
		return personaDto;
	}
	public void setPersonaDto(PersonaDto personaDto) {
		this.personaDto = personaDto;
	}
	
	public String getApellido() {
		return personaDto.getApellido();
	}
	
	public String getNombre() {
		return personaDto.getNombre();
	}
	
	public String getNumeroDocumento() {
		return personaDto.getDocumento().getNumero();
	}
	
	public TipoDocumentoDto getTipoDocumento() {
		return personaDto.getDocumento().getTipoDocumento();
	}
	
	public List<EmailDto> getEmails() {
		return personaDto.getEmails();
	}
	
	
}

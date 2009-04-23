package ar.com.nextel.sfa.client.dto;

import java.util.Date;
import java.util.Set;

import ar.com.nextel.model.personas.beans.Documento;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PersonaDto implements IsSerializable {

	private Long id;
    private Long idTipoDocumento;
    private String apellido;
    private String nombre;
    private Set<TelefonoDto> telefonos; 
    private Set<EmailDto> emails; 
    private SexoDto sexo;
    private DocumentoDto documento;
    private String razonSocial;
    private Date fechaNacimiento;
    
	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public DocumentoDto getDocumento() {
		return documento;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Set<TelefonoDto> getTelefonos() {
		return telefonos;
	}
	public void setTelefonos(Set<TelefonoDto> telefonos) {
		this.telefonos = telefonos;
	}
	public Set<EmailDto> getEmails() {
		return emails;
	}
	public void setEmails(Set<EmailDto> emails) {
		this.emails = emails;
	}
	public SexoDto getSexo() {
		return sexo;
	}
	public void setSexo(SexoDto sexo) {
		this.sexo = sexo;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}

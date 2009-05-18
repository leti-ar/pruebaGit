package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PersonaDto implements IsSerializable {

	private Long id;
    private Long idTipoDocumento;
    private String apellido;
    private String nombre;
    private List<TelefonoDto> telefonos; 
    private List<EmailDto> emails; 
    private SexoDto sexo;
    private DocumentoDto documento;
    private String razonSocial;
    private Date fechaNacimiento;
    private CargoDto cargo;
    
    //TODO: Arreglar esto, no me esta mapeando este domicilios con el de Persona!!!
    private List<DomiciliosCuentaDto> domicilios = new ArrayList<DomiciliosCuentaDto>();

    
	public List<DomiciliosCuentaDto> getDomicilios() {
		return domicilios;
	}
	public void setDomicilios(List<DomiciliosCuentaDto> domicilios) {
		this.domicilios = domicilios;
	}
	public void removeDomicilio(DomiciliosCuentaDto domicilio) {
		this.domicilios.remove(domicilio);
	}
	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public DocumentoDto getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoDto documento) {
		this.documento = documento;
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
	public List<TelefonoDto> getTelefonos() {
		return telefonos;
	}
	public void setTelefonos(List<TelefonoDto> telefonos) {
		this.telefonos = telefonos;
	}
	public List<EmailDto> getEmails() {
		return emails;
	}
	public void setEmails(List<EmailDto> emails) {
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
	public CargoDto getCargo() {
		return cargo;
	}
	public void setCargo(CargoDto cargo) {
		this.cargo = cargo;
	}
}

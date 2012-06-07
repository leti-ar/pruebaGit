package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudPortabilidadDto implements IsSerializable, IdentifiableDto, Cloneable{

	private Long id;
	
	private boolean recibeSMS;
	
	private String email;
	private String nroSS;
	private String nombre;
	private String apellido;
	private String telefono;
	private String razonSocial;
	private String numeroDocumento;
	private String areaTelefono;
	private String telefonoPortar;
	private String nroUltimaFacura;

	private Integer tipoPersona;
	
	private ProveedorDto proveedorAnterior;
	private TipoDocumentoDto tipoDocumento;
	private TipoTelefoniaDto tipoTelefonia;
	private ModalidadCobroDto modalidadCobro;

	private Long lineaSolicitudServicio;
//	private LineaSolicitudServicioDto lineaSolicitudServicio;
	
	private String nombreRep;
	private String apellidoRep;
	private TipoDocumentoDto tipoDocumentoRep;
	private String numeroDocRep;
	private Date fechaUltFactura;
	
	public Integer getTipoPersona() {
		return tipoPersona;
	}
	
	public void setTipoPersona(Integer tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	
	public Long getLineaSolicitudServicio() {
		return lineaSolicitudServicio;
	}
	
	public void setLineaSolicitudServicio(Long lineaSolicitudServicio) {
		this.lineaSolicitudServicio = lineaSolicitudServicio;
	}
	
//	public LineaSolicitudServicioDto getLineaSolicitudServicio() {
//		return lineaSolicitudServicio;
//	}
//	public void setLineaSolicitudServicio(LineaSolicitudServicioDto lineaSolicitudServicio) {
//		this.lineaSolicitudServicio = lineaSolicitudServicio;
//	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isRecibeSMS() {
		return recibeSMS;
	}
	public void setRecibeSMS(boolean recibeSMS) {
		this.recibeSMS = recibeSMS;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNroSS() {
		return nroSS;
	}
	public void setNroSS(String nroSS) {
		this.nroSS = nroSS;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getAreaTelefono() {
		return areaTelefono;
	}
	public void setAreaTelefono(String areaTelefono) {
		this.areaTelefono = areaTelefono;
	}
	public String getTelefonoPortar() {
		return telefonoPortar;
	}
	public void setTelefonoPortar(String telefonoPortar) {
		this.telefonoPortar = telefonoPortar;
	}
	public String getNroUltimaFacura() {
		return nroUltimaFacura;
	}
	public void setNroUltimaFacura(String nroUltimaFacura) {
		this.nroUltimaFacura = nroUltimaFacura;
	}
	public ProveedorDto getProveedorAnterior() {
		return proveedorAnterior;
	}
	public void setProveedorAnterior(ProveedorDto proveedorAnterior) {
		this.proveedorAnterior = proveedorAnterior;
	}
	public TipoDocumentoDto getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumentoDto tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public TipoTelefoniaDto getTipoTelefonia() {
		return tipoTelefonia;
	}
	public void setTipoTelefonia(TipoTelefoniaDto tipoTelefonia) {
		this.tipoTelefonia = tipoTelefonia;
	}
	public ModalidadCobroDto getModalidadCobro() {
		return modalidadCobro;
	}
	public void setModalidadCobro(ModalidadCobroDto modalidadCobro) {
		this.modalidadCobro = modalidadCobro;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombreRep() {
		return nombreRep;
	}

	public void setNombreRep(String nombreRep) {
		this.nombreRep = nombreRep;
	}

	public String getApellidoRep() {
		return apellidoRep;
	}

	public void setApellidoRep(String apellidoRep) {
		this.apellidoRep = apellidoRep;
	}

	public TipoDocumentoDto getTipoDocumentoRep() {
		return tipoDocumentoRep;
	}

	public void setTipoDocumentoRep(TipoDocumentoDto tipoDocumentoRep) {
		this.tipoDocumentoRep = tipoDocumentoRep;
	}

	public String getNumeroDocRep() {
		return numeroDocRep;
	}

	public void setNumeroDocRep(String numeroDocRep) {
		this.numeroDocRep = numeroDocRep;
	}

	public Date getFechaUltFactura() {
		return fechaUltFactura;
	}

	public void setFechaUltFactura(Date fechaUltFactura) {
		this.fechaUltFactura = fechaUltFactura;
	}
	
	
}

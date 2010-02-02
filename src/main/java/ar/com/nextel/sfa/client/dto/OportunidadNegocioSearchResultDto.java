package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OportunidadNegocioSearchResultDto implements IsSerializable {

	private Long idOportunidadNegocio;
	private String razonSocial;
	private String nombre;
	private String apellido;
	private String telefonoPrincipal;
	private String nroDocumento;
	private String nroCuenta;
	private Date fechaAsignacion;
	private String descripcionEstadoOportunidad;
	private Long idCuentaOrigen;
	private Long idEstadoOpp;

	public Long getIdOportunidadNegocio() {
		return idOportunidadNegocio;
	}

	public void setIdOportunidadNegocio(Long idOportunidadNegocio) {
		this.idOportunidadNegocio = idOportunidadNegocio;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
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

	public String getTelefonoPrincipal() {
		return telefonoPrincipal;
	}

	public void setTelefonoPrincipal(String telefonoPrincipal) {
		this.telefonoPrincipal = telefonoPrincipal;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getNroCuenta() {
		return nroCuenta;
	}

	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public String getDescripcionEstadoOportunidad() {
		return descripcionEstadoOportunidad;
	}

	public void setDescripcionEstadoOportunidad(String descripcionEstadoOportunidad) {
		this.descripcionEstadoOportunidad = descripcionEstadoOportunidad;
	}

	public Long getIdCuentaOrigen() {
		return idCuentaOrigen;
	}

	public void setIdCuentaOrigen(Long idCuentaOrigen) {
		this.idCuentaOrigen = idCuentaOrigen;
	}

	public Long getIdEstadoOpp() {
		return idEstadoOpp;
	}

	public void setIdEstadoOpp(Long idEstadoOpp) {
		this.idEstadoOpp = idEstadoOpp;
	}

}

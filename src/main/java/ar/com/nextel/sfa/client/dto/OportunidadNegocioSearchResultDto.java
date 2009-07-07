package ar.com.nextel.sfa.client.dto;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.oportunidades.beans.EstadoOportunidad;


public class OportunidadNegocioSearchResultDto implements IsSerializable {

	private Long idOportunidadNegocio;
    private String razonSocial;
    private String nombre;
    private String apellido;
    private String telefonoPrincipal;
    private String nroDocumento;
    private String nroCuenta;
    private Date fechaAsignacion;
    private EstadoOportunidadDto estadoOportunidad;
    //private List cuentasCreadas = new ArrayList();
    //private CuentaDto cuentaOrigen;
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
	public EstadoOportunidadDto getEstadoOportunidad() {
		return estadoOportunidad;
	}
	public void setEstadoOportunidad(EstadoOportunidadDto estadoOportunidad) {
		this.estadoOportunidad = estadoOportunidad;
	}
//	public List getCuentasCreadas() {
//		return cuentasCreadas;
//	}
//	public void setCuentasCreadas(List cuentasCreadas) {
//		this.cuentasCreadas = cuentasCreadas;
//	}
//	public CuentaDto getCuentaOrigen() {
//		return cuentaOrigen;
//	}
//	public void setCuentaOrigen(CuentaDto cuentaOrigen) {
//		this.cuentaOrigen = cuentaOrigen;
//	}
	public Long getIdEstadoOpp() {
		return idEstadoOpp;
	}
	public void setIdEstadoOpp(Long idEstadoOpp) {
		this.idEstadoOpp = idEstadoOpp;
	}

}

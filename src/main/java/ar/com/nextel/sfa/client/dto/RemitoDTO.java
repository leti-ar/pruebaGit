package ar.com.nextel.sfa.client.dto;

import java.io.Serializable;

public class RemitoDTO implements Serializable {

	private Long id;
	
	private SolicitudServicioDto solicitudServicio;
	
	private Long idSucursal;
	
	private String numeroRemito;
	
	private String nombreEmpresa;

	private String direccion;
	
	private String cp;
	
	private String telefono;
	
	private String iva;
	
	private String cuit;
	
	private String iibb;
	
	private String inicioActividades;
	
	private String ncai;
	
	private String fechaVencimiento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SolicitudServicioDto getSolicitudServicio() {
		return solicitudServicio;
	}

	public void setSolicitudServicio(SolicitudServicioDto solicitudServicio) {
		this.solicitudServicio = solicitudServicio;
	}

	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	public String getNumeroRemito() {
		return numeroRemito;
	}

	public void setNumeroRemito(String numeroRemito) {
		this.numeroRemito = numeroRemito;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getIibb() {
		return iibb;
	}

	public void setIibb(String iibb) {
		this.iibb = iibb;
	}

	public String getInicioActividades() {
		return inicioActividades;
	}

	public void setInicioActividades(String inicioActividades) {
		this.inicioActividades = inicioActividades;
	}

	public String getNcai() {
		return ncai;
	}

	public void setNcai(String ncai) {
		this.ncai = ncai;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

}

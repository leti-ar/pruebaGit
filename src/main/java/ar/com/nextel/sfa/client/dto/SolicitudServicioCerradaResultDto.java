package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioCerradaResultDto implements IsSerializable {

	private Long idSolicitudServicio;
	private String numeroSS;
	private String numeroCuenta;
	private String razonSocialCuenta;
	private Long cantidadEquipos;
	private Double pataconex;
	private Boolean firmar;
	private Long idVantive;
	private Long idCuenta;
	private Long id;
	private Long cantidadEquiposPorCuenta;
	private List<CambiosSolicitudServicioDto> cambios;
//	private boolean cliente;
	private String numeroCuentaAlCierreSS;
	private String razonSocial;
	//LF
	private String nombreUsuarioCreacion;
	private String apellidoUsuarioCreacion;	
	private String nombreVendedor;
	private String apellidoVendedor;
	private Boolean scoring;
	private Boolean pin;
	private String veraz;
	private Date fechaCreacion;
//	private Date fechaCierre;
	private String anticipo;
	private String desvios;
	private Long idGrupoSolicitud;
	private List<LineaSolicitudServicioDto> lineas = new ArrayList<LineaSolicitudServicioDto>();
	private String item;
	private Boolean enCarga;
	private String apellidoNombreVendedor;

	/** 
	 * 
	 * @return true si numeroCuentaAlCierreSS es un ID Vantive (o sea, no comienza ni con S ni con V)
	 */
    public boolean isNumeroCuentaAlCierreSSIdVantive() {
        return !(("S".equals(numeroCuentaAlCierreSS.substring(0,1))) || ("V".equals(numeroCuentaAlCierreSS.substring(0,1))));       
    }

	public Long getIdSolicitudServicio() {
		return idSolicitudServicio;
	}

	public void setIdSolicitudServicio(Long idSolicitudServicio) {
		this.idSolicitudServicio = idSolicitudServicio;
	}

	public String getNumeroSS() {
		return numeroSS;
	}

	public void setNumeroSS(String numero) {
		this.numeroSS = numero;
	}

	public String getRazonSocialCuenta() {
		return razonSocialCuenta;
	}

	public void setRazonSocialCuenta(String razonSocialCuenta) {
		this.razonSocialCuenta = razonSocialCuenta;
	}

	public Double getPataconex() {
		return pataconex;
	}

	public void setPataconex(Double pataconex) {
		this.pataconex = pataconex;
	}

	public Boolean getFirmar() {
		return firmar;
	}

	public void setFirmar(Boolean firmar) {
		this.firmar = firmar;
	}

	public Long getCantidadEquipos() {
		return cantidadEquipos;
	}

	public void setCantidadEquipos(Long cantidadEquipos) {
		this.cantidadEquipos = cantidadEquipos;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setCambios(List<CambiosSolicitudServicioDto> cambios) {
		this.cambios = cambios;
	}

	public List<CambiosSolicitudServicioDto> getCambios() {
		return cambios;
	}

	public Long getIdVantive() {
		return idVantive;
	}

	public void setIdVantive(Long idVantive) {
		this.idVantive = idVantive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCantidadEquiposPorCuenta() {
		return cantidadEquiposPorCuenta;
	}

	public void setCantidadEquiposPorCuenta(Long cantidadEquiposPorCuenta) {
		this.cantidadEquiposPorCuenta = cantidadEquiposPorCuenta;
	}

//	public boolean isCliente() {
//		return cliente;
//	}
//
//	public void setCliente(boolean cliente) {
//		this.cliente = cliente;
//	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getNumeroCuentaAlCierreSS() {
		return numeroCuentaAlCierreSS;
	}

	public void setNumeroCuentaAlCierreSS(String numeroDeCuenta) {
		this.numeroCuentaAlCierreSS = numeroDeCuenta;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Boolean getScoring() {
		return scoring;
	}

	public void setScoring(Boolean scoring) {
		this.scoring = scoring;
	}

	public String getNombreUsuarioCreacion() {
		return nombreUsuarioCreacion;
	}

	public void setNombreUsuarioCreacion(String nombreUsuarioCreacion) {
		this.nombreUsuarioCreacion = nombreUsuarioCreacion;
	}
	
	public String getApellidoUsuarioCreacion() {
		return apellidoUsuarioCreacion;
	}

	public void setApellidoUsuarioCreacion(String apellidoUsuarioCreacion) {
		this.apellidoUsuarioCreacion = apellidoUsuarioCreacion;
	}	

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	public String getApellidoVendedor() {
		return apellidoVendedor;
	}

	public void setApellidoVendedor(String apellidoVendedor) {
		this.apellidoVendedor = apellidoVendedor;
	}

	public Boolean getPin() {
		return pin;
	}

	public void setPin(Boolean pin) {
		this.pin = pin;
	}

	public String getVeraz() {
		return veraz;
	}

	public void setVeraz(String veraz) {
		this.veraz = veraz;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

//	public Date getFechaCierre() {
//		return fechaCierre;
//	}
//
//	public void setFechaCierre(Date fechaCierre) {
//		this.fechaCierre = fechaCierre;
//	}

	public String getAnticipo() {
		return anticipo;
	}

	public void setAnticipo(String anticipo) {
		this.anticipo = anticipo;
	}

	public String getDesvios() {
		return desvios;
	}

	public void setDesvios(String desvios) {
		this.desvios = desvios;
	}

	public Long getIdGrupoSolicitud() {
		return idGrupoSolicitud;
	}

	public void setIdGrupoSolicitud(Long idGrupoSolicitud) {
		this.idGrupoSolicitud = idGrupoSolicitud;
	}

	public List<LineaSolicitudServicioDto> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaSolicitudServicioDto> lineas) {
		this.lineas = lineas;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Boolean getEnCarga() {
		return enCarga;
	}

	public void setEnCarga(Boolean enCarga) {
		this.enCarga = enCarga;
	}

	public String getApellidoNombreVendedor() {
		return apellidoNombreVendedor;
	}

	public void setApellidoNombreVendedor(String apellidoNombreVendedor) {
		this.apellidoNombreVendedor = apellidoNombreVendedor;
	}

	
}

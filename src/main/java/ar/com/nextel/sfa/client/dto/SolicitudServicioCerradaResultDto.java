package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioCerradaResultDto implements IsSerializable {

	private Long idSolicitudServicio;
    //private Long idEstadoAprobacion;
    //private Long idCuenta;
    private String numero;
    private String numeroCuenta;
    private String razonSocialCuenta;
    private Long cantidadEquipos;
    private Double pataconex;
    private Boolean firmar;
    private List<CambiosSolicitudServicioDto> cambios;
	
    public Long getIdSolicitudServicio() {
		return idSolicitudServicio;
	}
	public void setIdSolicitudServicio(Long idSolicitudServicio) {
		this.idSolicitudServicio = idSolicitudServicio;
	}
//	public Long getIdEstadoAprobacion() {
//		return idEstadoAprobacion;
//	}
//	public void setIdEstadoAprobacion(Long idEstadoAprobacion) {
//		this.idEstadoAprobacion = idEstadoAprobacion;
//	}
//	public Long getIdCuenta() {
//		return idCuenta;
//	}
//	public void setIdCuenta(Long idCuenta) {
//		this.idCuenta = idCuenta;
//	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
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
}

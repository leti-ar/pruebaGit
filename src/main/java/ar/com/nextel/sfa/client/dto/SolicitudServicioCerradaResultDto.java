package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioCerradaResultDto implements IsSerializable {

	private Long idSolicitudServicio;
    private Long idEstadoAprobacion;
    private Long idCuenta;
    private String numero;
    //private String numeroCuenta;
    private String razonSocialCuenta;
    //private Long cantidadEquipos;
    private Double pataconex;
    private Boolean firmar;
	
    public Long getIdSolicitudServicio() {
		return idSolicitudServicio;
	}
	public void setIdSolicitudServicio(Long idSolicitudServicio) {
		this.idSolicitudServicio = idSolicitudServicio;
	}
	public Long getIdEstadoAprobacion() {
		return idEstadoAprobacion;
	}
	public void setIdEstadoAprobacion(Long idEstadoAprobacion) {
		this.idEstadoAprobacion = idEstadoAprobacion;
	}
	public Long getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}
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
    
}

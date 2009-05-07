package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioCerradaResultDto implements IsSerializable {

	private Long idSolicitudServicio;
    private String numero;
    private String numeroCuenta;
    private String razonSocialCuenta;
    private Long cantidadEquipos;
    private Double pataconex;
    private Boolean firmar;
    private Long idVantive;
    private Long id;
    private Long cantidadEquiposPorCuenta;
    private List<CambiosSolicitudServicioDto> cambios;
	
    public Long getIdSolicitudServicio() {
		return idSolicitudServicio;
	}
	public void setIdSolicitudServicio(Long idSolicitudServicio) {
		this.idSolicitudServicio = idSolicitudServicio;
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
}

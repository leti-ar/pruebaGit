package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.nextel.model.cuentas.beans.Cuenta;

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
	private boolean cliente;
	private String numeroCuentaAlCierreSS;
	private String razonSocial;
	
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

	public boolean isCliente() {
		return cliente;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}

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

}

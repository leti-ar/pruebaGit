package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DetalleSolicitudServicioDto implements IsSerializable {

    private String numero;
    private String numeroCuenta;
    private String razonSocialCuenta;
    private List<CambiosSolicitudServicioDto> cambiosEstadoSolicitud;
	
    
    public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getRazonSocialCuenta() {
		return razonSocialCuenta;
	}
	public void setRazonSocialCuenta(String razonSocialCuenta) {
		this.razonSocialCuenta = razonSocialCuenta;
	}
	public List<CambiosSolicitudServicioDto> getCambiosEstadoSolicitud() {
		return cambiosEstadoSolicitud;
	}
	public void setCambiosEstadoSolicitud(List<CambiosSolicitudServicioDto> cambiosEstadoSolicitud) {
		this.cambiosEstadoSolicitud = cambiosEstadoSolicitud;
	}

 }

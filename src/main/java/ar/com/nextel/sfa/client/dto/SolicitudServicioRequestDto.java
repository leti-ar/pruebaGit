package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioRequestDto implements IsSerializable {

	private String numeroCuenta;
	private Long idGrupoSolicitud;
	private Long idCuentaPotencial;
	private Long idCuenta;

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public Long getIdGrupoSolicitud() {
		return idGrupoSolicitud;
	}

	public void setIdGrupoSolicitud(Long idGrupoSolicitud) {
		this.idGrupoSolicitud = idGrupoSolicitud;
	}

	public Long getIdCuentaPotencial() {
		return idCuentaPotencial;
	}

	public void setIdCuentaPotencial(Long idCuentaPotencial) {
		this.idCuentaPotencial = idCuentaPotencial;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

}

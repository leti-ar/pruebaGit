package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CaratulaDto implements IsSerializable {

	private Long id;
	private CuentaDto cuenta;
	private String nroSS;
	private VendedorDto usuarioCreacion;
	private Date fechaCreacion;
	private boolean confirmada;

	// TODO Terminar

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CuentaDto getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaDto cuenta) {
		this.cuenta = cuenta;
	}

	public String getNroSS() {
		return nroSS;
	}

	public void setNroSS(String nroSS) {
		this.nroSS = nroSS;
	}

	public VendedorDto getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(VendedorDto usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public boolean isConfirmada() {
		return confirmada;
	}

	public void setConfirmada(boolean confirmada) {
		this.confirmada = confirmada;
	}
}

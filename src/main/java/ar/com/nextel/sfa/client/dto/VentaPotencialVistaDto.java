package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Clase: CuentaPotencialDto
 * 
 * @author eSalvador
 */

public class VentaPotencialVistaDto implements IsSerializable {

	private Long idCuentaPotencial;
	private boolean esReserva;
	private String numeroCliente;
	private String razonSocial;
	private String telefono;
	private String numero;
	// private Vendedor vendedor;
	private Boolean consultada;
	private Date fechaAsignacion;
	private Long idCuenta;
	
	public Long getIdCuentaPotencial() {
		return idCuentaPotencial;
	}

	public void setIdCuentaPotencial(Long idCuentaPotencial) {
		this.idCuentaPotencial = idCuentaPotencial;
	}

	public boolean isEsReserva() {
		return esReserva;
	}

	public void setEsReserva(boolean esReserva) {
		this.esReserva = esReserva;
	}

	public String getNumeroCliente() {
		return numeroCliente;
	}

	public void setNumeroCliente(String numeroCliente) {
		this.numeroCliente = numeroCliente;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	// public Vendedor getVendedor() {
	// return vendedor;
	// }
	// public void setVendedor(Vendedor vendedor) {
	// this.vendedor = vendedor;
	// }
	public Boolean getConsultada() {
		return consultada;
	}

	public void setConsultada(Boolean consultada) {
		this.consultada = consultada;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}
	
	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}
}

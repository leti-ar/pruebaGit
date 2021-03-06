package ar.com.nextel.sfa.client.initializer;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.EquiposServiciosDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InfocomInitializer implements IsSerializable {

	private String terminalesActivas;
	private String terminalesSuspendidas;
	private String terminalesDesactivadas;
	private String ciclo;
	private String flota;
	private double limiteCredito;
	private List<CuentaDto> responsablePago;
	private String numeroCuenta;
	private String idResponsablePago;
	private String razonSocial;
	private EquiposServiciosDto equiposServicios;
	

//	public InfocomInitializer() {
//		
//	}
		
//	public InfocomInitializer(String ciclo, String flota,
//			String limCredito, List responsablePago) {
//		super();
//		this.ciclo = ciclo;
//		this.flota = flota;
//		this.limiteCredito = limCredito;
//		this.responsablePago = responsablePago;
//	}


	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getFlota() {
		return flota;
	}

	public void setFlota(String flota) {
		this.flota = flota;
	}

	public double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getIdResponsablePago() {
		return idResponsablePago;
	}

	public void setIdResponsablePago(String idResponsablePago) {
		this.idResponsablePago = idResponsablePago;
	}

	public String getTerminalesActivas() {
		return terminalesActivas;
	}

	public void setTerminalesActivas(String terminalesActivas) {
		this.terminalesActivas = terminalesActivas;
	}

	public String getTerminalesSuspendidas() {
		return terminalesSuspendidas;
	}

	public void setTerminalesSuspendidas(String terminalesSuspendidas) {
		this.terminalesSuspendidas = terminalesSuspendidas;
	}

	public String getTerminalesDesactivadas() {
		return terminalesDesactivadas;
	}

	public void setTerminalesDesactivadas(String terminalesDesactivadas) {
		this.terminalesDesactivadas = terminalesDesactivadas;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public EquiposServiciosDto getEquiposServicios() {
		return equiposServicios;
	}

	public void setEquiposServicios(EquiposServiciosDto equiposServicios) {
		this.equiposServicios = equiposServicios;
	}

	public List<CuentaDto> getResponsablePago() {
		return responsablePago;
	}

	public void setResponsablePago(List<CuentaDto> responsablePago) {
		this.responsablePago = responsablePago;
	}

}

package ar.com.nextel.sfa.client.initializer;

import java.util.List;

public class InfocomInitializer {

	private int terminalesActivas;
	private int terminalesSuspendidas;
	private int terminalesDesactivadas;
	private String ciclo;
	private String flota;
	private String limCredito;
	private List responsablePago;

	public int getTerminalesActivas() {
		return terminalesActivas;
	}

	public void setTerminalesActivas(int terminalesActivas) {
		this.terminalesActivas = terminalesActivas;
	}

	public int getTerminalesSuspendidas() {
		return terminalesSuspendidas;
	}

	public void setTerminalesSuspendidas(int terminalesSuspendidas) {
		this.terminalesSuspendidas = terminalesSuspendidas;
	}

	public int getTerminalesDesactivadas() {
		return terminalesDesactivadas;
	}

	public void setTerminalesDesactivadas(int terminalesDesactivadas) {
		this.terminalesDesactivadas = terminalesDesactivadas;
	}

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

	public String getLimCredito() {
		return limCredito;
	}

	public void setLimCredito(String limCredito) {
		this.limCredito = limCredito;
	}

	public List getResponsablePago() {
		return responsablePago;
	}

	public void setResponsablePago(List responsablePago) {
		this.responsablePago = responsablePago;
	}

}

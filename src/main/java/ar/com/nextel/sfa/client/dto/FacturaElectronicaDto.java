package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FacturaElectronicaDto implements IsSerializable {

	private Long id;
	private String email;
	private boolean cargadaEnVantive;
	private boolean replicadaAutogestion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isCargadaEnVantive() {
		return cargadaEnVantive;
	}
	
	public void setCargadaEnVantive(boolean cargadaEnVantive) {
		this.cargadaEnVantive = cargadaEnVantive;
	}

	public void setReplicadaAutogestion(boolean replicadaAutogestion) {
		this.replicadaAutogestion = replicadaAutogestion;
	}

	public boolean isReplicadaAutogestion() {
		return replicadaAutogestion;
	}
}

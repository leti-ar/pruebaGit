package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CuentaSSDto implements IsSerializable {

	private Long id;
	private Long idVantive;
	private String codigoVantive;
	private PersonaDto persona;
	private boolean empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdVantive() {
		return idVantive;
	}

	public void setIdVantive(Long idVantive) {
		this.idVantive = idVantive;
	}

	public String getCodigoVantive() {
		return codigoVantive;
	}

	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}

	public boolean isCliente() {
		return getIdVantive() != null;
	}

	public boolean isEmpresa() {
		return empresa;
	}

	public void setEmpresa(boolean empresa) {
		this.empresa = empresa;
	}
}

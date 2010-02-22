package ar.com.nextel.sfa.client.dto;

import java.util.List;

public class GranCuentaDto extends CuentaDto {

	private CondicionCuentaDto condicionCuentaProspect;
	private CondicionCuentaDto condicionCuentaProspectEnCarga;
	private List<ContactoCuentaDto> contactos;

	public CondicionCuentaDto getCondicionCuentaProspect() {
		return condicionCuentaProspect;
	}

	public void setCondicionCuentaProspect(CondicionCuentaDto condicionCuentaProspect) {
		this.condicionCuentaProspect = condicionCuentaProspect;
	}

	public CondicionCuentaDto getCondicionCuentaProspectEnCarga() {
		return condicionCuentaProspectEnCarga;
	}

	public void setCondicionCuentaProspectEnCarga(CondicionCuentaDto condicionCuentaProspectEnCarga) {
		this.condicionCuentaProspectEnCarga = condicionCuentaProspectEnCarga;
	}

	public List<ContactoCuentaDto> getContactos() {
		return contactos;
	}

	public void setContactos(List<ContactoCuentaDto> contactos) {
		this.contactos = contactos;
	}

}
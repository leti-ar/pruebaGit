package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

public class GranCuentaDto extends CuentaDto {

    private CondicionCuentaDto condicionCuentaProspect;
    private CondicionCuentaDto condicionCuentaProspectEnCarga;
    //private LockingStateDto lockingState;
    private List<ContactoCuentaDto> contactos;
    private List<DivisionDto> divisiones;
    private List<SuscriptorDto> suscriptores;

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
	public List<DivisionDto> getDivisiones() {
		return divisiones;
	}
	public void setDivisiones(List<DivisionDto> divisiones) {
		this.divisiones = divisiones;
	}
	public List<SuscriptorDto> getSuscriptores() {
		return suscriptores;
	}
	public void setSuscriptores(List<SuscriptorDto> suscriptores) {
		this.suscriptores = suscriptores;
	}
	public void addSuscriptor(SuscriptorDto suscriptor) {
		if (suscriptores == null){
		   suscriptores = new ArrayList<SuscriptorDto>();
		}
		this.suscriptores.add(suscriptor);
	}
	public void addDivision(DivisionDto division) {
		if (divisiones == null){
			divisiones = new ArrayList<DivisionDto>();
		this.divisiones.add(division);
		}
	}	
}
package ar.com.nextel.sfa.client.dto;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PlanBaseDto implements IsSerializable{
    private String descripcion;
    private String codigoBSCS;
    private TipoPlanDto tipoPlan;
    private Set<PlanDto> planes = new HashSet<PlanDto>();
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigoBSCS() {
		return codigoBSCS;
	}
	public void setCodigoBSCS(String codigoBSCS) {
		this.codigoBSCS = codigoBSCS;
	}
	public TipoPlanDto getTipoPlan() {
		return tipoPlan;
	}
	public void setTipoPlan(TipoPlanDto tipoPlan) {
		this.tipoPlan = tipoPlan;
	}
	public Set<PlanDto> getPlanes() {
		return planes;
	}
	public void setPlanes(Set<PlanDto> planes) {
		this.planes = planes;
	}
}

package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SubsidiosDto implements IsSerializable {

	private Long idPlan;
	private double subsidio;
	
	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}
	
	public Long getIdPlan() {
		return idPlan;
	}

	public void setSubsidio(double subsidio) {
		this.subsidio = subsidio;
	}

	public double getSubsidio() {
		return subsidio;
	}
	
}

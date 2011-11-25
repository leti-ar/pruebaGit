package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ScoreVerazDto implements IsSerializable  {


	private Integer limCredito;
	private String clasificacion;
	private String riskCode;

	
	public Integer getLimCredito() {
		return limCredito;
	}
	public void setLimCredito(Integer limCredito) {
		this.limCredito = limCredito;
	}
	public String getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}	
		
}


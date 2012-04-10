package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ScoreVerazDto implements IsSerializable  {


	private Double limCredito;
	private CalificacionDto clasificacion;
	private RiskCodeDto riskCode;

	
	public Double getLimCredito() {
		return limCredito;
	}
	public void setLimCredito(Double limCredito) {
		this.limCredito = limCredito;
	}
	public CalificacionDto getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(CalificacionDto clasificacion) {
		this.clasificacion = clasificacion;
	}
	public RiskCodeDto getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(RiskCodeDto riskCode) {
		this.riskCode = riskCode;
	}	
		
}


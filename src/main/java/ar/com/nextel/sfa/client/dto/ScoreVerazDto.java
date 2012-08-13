package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ScoreVerazDto implements IsSerializable  {


	private Double limCredito;
	private CalificacionDto calificacion;
	private RiskCodeDto riskCode;

	
	public Double getLimCredito() {
		return limCredito;
	}
	public void setLimCredito(Double limCredito) {
		this.limCredito = limCredito;
	}
	public CalificacionDto getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(CalificacionDto calificacion) {
		this.calificacion = calificacion;
	}
	public RiskCodeDto getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(RiskCodeDto riskCode) {
		this.riskCode = riskCode;
	}	
		
}


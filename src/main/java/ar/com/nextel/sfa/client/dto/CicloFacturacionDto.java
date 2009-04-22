package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CicloFacturacionDto implements IsSerializable{
    private Integer diaInicio;
    private Integer diaCierre;
    private Integer diaProceso;
    private String  codigoFNCL;
	public Integer getDiaInicio() {
		return diaInicio;
	}
	public void setDiaInicio(Integer diaInicio) {
		this.diaInicio = diaInicio;
	}
	public Integer getDiaCierre() {
		return diaCierre;
	}
	public void setDiaCierre(Integer diaCierre) {
		this.diaCierre = diaCierre;
	}
	public Integer getDiaProceso() {
		return diaProceso;
	}
	public void setDiaProceso(Integer diaProceso) {
		this.diaProceso = diaProceso;
	}
	public String getCodigoFNCL() {
		return codigoFNCL;
	}
	public void setCodigoFNCL(String codigoFNCL) {
		this.codigoFNCL = codigoFNCL;
	}
}

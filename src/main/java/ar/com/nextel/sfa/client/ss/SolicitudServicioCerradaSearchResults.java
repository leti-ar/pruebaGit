package ar.com.nextel.sfa.client.ss;

import java.util.List;

public class SolicitudServicioCerradaSearchResults {

	private List solicitudServicioCerradaResults;
    private Integer totalEquipos;
    private Integer totalEquiposFirmados;
    private String totalPataconex;
	
    public List getSolicitudServicioCerradaResults() {
		return solicitudServicioCerradaResults;
	}
	public void setSolicitudServicioCerradaResults(List solicitudServicioCerradaResults) {
		this.solicitudServicioCerradaResults = solicitudServicioCerradaResults;
	}
	public Integer getTotalEquipos() {
		return totalEquipos;
	}
	public void setTotalEquipos(Integer totalEquipos) {
		this.totalEquipos = totalEquipos;
	}
	public Integer getTotalEquiposFirmados() {
		return totalEquiposFirmados;
	}
	public void setTotalEquiposFirmados(Integer totalEquiposFirmados) {
		this.totalEquiposFirmados = totalEquiposFirmados;
	}
	public String getTotalPataconex() {
		return totalPataconex;
	}
	public void setTotalPataconex(String totalPataconex) {
		this.totalPataconex = totalPataconex;
	}    
    
}

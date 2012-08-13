package ar.com.nextel.sfa.client.comparator;

import java.util.Comparator;

import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;

public class UltimoEstadoSSComparator implements Comparator<SolicitudServicioCerradaResultDto>{

	private boolean ordenDesc;	
	
	public UltimoEstadoSSComparator(boolean desc){
		this.ordenDesc = desc;
	}
	
	public int compare(SolicitudServicioCerradaResultDto arg0,
			SolicitudServicioCerradaResultDto arg1) {
		if(isOrdenDesc()) {
			return arg0.getUltimoEstado().compareTo(arg1.getUltimoEstado());
		} else {
			return arg1.getUltimoEstado().compareTo(arg0.getUltimoEstado()); 
		}
	}
	
	public boolean isOrdenDesc() {
		return ordenDesc;
	}

	public void setOrdenDesc(boolean ordenDesc) {
		this.ordenDesc = ordenDesc;
	}

}

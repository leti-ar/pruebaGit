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
			return arg0.getRazonSocial().compareTo(arg1.getRazonSocial()); // cambiar a ultimo estado
		} else {
			return arg1.getRazonSocial().compareTo(arg0.getRazonSocial()); // cambiar a ultimo estado
		}
	}
	
	public boolean isOrdenDesc() {
		return ordenDesc;
	}

	public void setOrdenDesc(boolean ordenDesc) {
		this.ordenDesc = ordenDesc;
	}

}

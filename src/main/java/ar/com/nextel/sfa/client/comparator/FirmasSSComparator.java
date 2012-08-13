package ar.com.nextel.sfa.client.comparator;

import java.util.Comparator;

import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;

public class FirmasSSComparator implements Comparator<SolicitudServicioCerradaResultDto>{

	private boolean ordenDesc;	
	
	public FirmasSSComparator(boolean desc){
		this.ordenDesc = desc;
	}
	
	public int compare(SolicitudServicioCerradaResultDto arg0,
			SolicitudServicioCerradaResultDto arg1) {
		if(isOrdenDesc()) {
			return arg0.getFirmar().compareTo(arg1.getFirmar());
		} else {
			return arg1.getFirmar().compareTo(arg0.getFirmar());
		}
	}
	
	public boolean isOrdenDesc() {
		return ordenDesc;
	}

	public void setOrdenDesc(boolean ordenDesc) {
		this.ordenDesc = ordenDesc;
	}

}

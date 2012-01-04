package ar.com.nextel.sfa.client.comparator;

import java.util.Comparator;

import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;

public class AnticipoSSComparator implements Comparator<SolicitudServicioCerradaResultDto>{

	private boolean ordenDesc;	
	
	public AnticipoSSComparator(boolean desc){
		this.ordenDesc = desc;
	}
	
	public int compare(SolicitudServicioCerradaResultDto arg0,
			SolicitudServicioCerradaResultDto arg1) {
		String anticipo;
		String anticipo2;
		if(arg0.getAnticipo() == null) {
			anticipo = "";
		} else {
			anticipo = arg0.getAnticipo();
		}
		if(arg1.getAnticipo() == null) {
			anticipo2 = "";
		} else {
			anticipo2 = arg1.getAnticipo();
		}
		if(isOrdenDesc()) {
			return anticipo.compareTo(anticipo2);
		} else {
			return anticipo2.compareTo(anticipo);
		}
	}
	
	public boolean isOrdenDesc() {
		return ordenDesc;
	}

	public void setOrdenDesc(boolean ordenDesc) {
		this.ordenDesc = ordenDesc;
	}

}

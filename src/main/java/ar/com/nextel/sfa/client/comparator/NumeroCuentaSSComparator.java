package ar.com.nextel.sfa.client.comparator;

import java.util.Comparator;

import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;

public class NumeroCuentaSSComparator implements Comparator<SolicitudServicioCerradaResultDto>{

	private boolean ordenDesc;	
	
	public NumeroCuentaSSComparator(boolean desc){
		this.ordenDesc = desc;
	}
	
	public int compare(SolicitudServicioCerradaResultDto arg0,
			SolicitudServicioCerradaResultDto arg1) {
		if(isOrdenDesc()) {
			return arg0.getNumeroCuenta().compareTo(arg1.getNumeroCuenta());
		} else {
			return arg1.getNumeroCuenta().compareTo(arg0.getNumeroCuenta());	
		}
	}

	public boolean isOrdenDesc() {
		return ordenDesc;
	}

	public void setOrdenDesc(boolean ordenDesc) {
		this.ordenDesc = ordenDesc;
	}
	
	

}

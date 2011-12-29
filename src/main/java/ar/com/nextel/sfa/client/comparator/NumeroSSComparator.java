package ar.com.nextel.sfa.client.comparator;

import java.util.Comparator;

import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;

public class NumeroSSComparator implements Comparator<SolicitudServicioCerradaResultDto>{

	private boolean ordenDesc;	
	
	public NumeroSSComparator(boolean desc){
		this.ordenDesc = desc;
	}
	
	public int compare(SolicitudServicioCerradaResultDto arg0,
			SolicitudServicioCerradaResultDto arg1) {
		String numeroSS;
		String numeroSS2;
		if(arg0.getNumeroSS() == null) {
			numeroSS = "";
		} else {
			numeroSS = arg0.getNumeroSS();
		}
		if(arg1.getNumeroSS() == null) {
			numeroSS2 = "";
		} else {
			numeroSS2 = arg1.getNumeroSS();
		}
		
		if(isOrdenDesc()) {
			return numeroSS.compareTo(numeroSS2);
		} else {
			return numeroSS2.compareTo(numeroSS);
		}
	}

	public boolean isOrdenDesc() {
		return ordenDesc;
	}

	public void setOrdenDesc(boolean ordenDesc) {
		this.ordenDesc = ordenDesc;
	}
	
	

}

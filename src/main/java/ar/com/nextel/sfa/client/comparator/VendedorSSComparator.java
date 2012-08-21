package ar.com.nextel.sfa.client.comparator;

import java.util.Comparator;

import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;

public class VendedorSSComparator implements Comparator<SolicitudServicioCerradaResultDto>{

	private boolean ordenDesc;	
	
	public VendedorSSComparator(boolean desc){
		this.ordenDesc = desc;
	}
	
	public int compare(SolicitudServicioCerradaResultDto arg0,
			SolicitudServicioCerradaResultDto arg1) {
		if(isOrdenDesc()) {
			return arg0.getApellidoNombreVendedor().compareTo(arg1.getApellidoNombreVendedor());
		} else {
			return arg1.getApellidoNombreVendedor().compareTo(arg0.getApellidoNombreVendedor());
		}
	}
	
	public boolean isOrdenDesc() {
		return ordenDesc;
	}

	public void setOrdenDesc(boolean ordenDesc) {
		this.ordenDesc = ordenDesc;
	}
	
}

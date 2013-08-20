package ar.com.nextel.sfa.client.comparator;

import java.util.Comparator;

import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;

public class UsuarioCreacionSSComparator implements Comparator<SolicitudServicioCerradaResultDto>{

	private boolean ordenDesc;	
	
	public UsuarioCreacionSSComparator(boolean desc){
		this.ordenDesc = desc;
	}
	
	public int compare(SolicitudServicioCerradaResultDto arg0,
			SolicitudServicioCerradaResultDto arg1) {
		String user1 = arg0.getApellidoUsuarioCreacion() + " " + arg0.getNombreUsuarioCreacion();
		String user2 = arg1.getApellidoUsuarioCreacion() + " " + arg1.getNombreUsuarioCreacion();
		if(isOrdenDesc()) {
			return user1.compareTo(user2);
		} else {
			return user2.compareTo(user1);
		}
			
	}
	
	public boolean isOrdenDesc() {
		return ordenDesc;
	}

	public void setOrdenDesc(boolean ordenDesc) {
		this.ordenDesc = ordenDesc;
	}

}

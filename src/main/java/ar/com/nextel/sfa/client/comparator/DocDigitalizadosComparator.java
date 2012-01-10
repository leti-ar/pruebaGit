package ar.com.nextel.sfa.client.comparator;

import java.util.Comparator;

import ar.com.nextel.sfa.client.dto.DocDigitalizadosDto;

public class DocDigitalizadosComparator implements Comparator<DocDigitalizadosDto> {

	public DocDigitalizadosComparator() {
		
	}
	
	public int compare(DocDigitalizadosDto o1, DocDigitalizadosDto o2) {
		String fecha1 = "z";
		String fecha2 = "z";
		if(o1.getFecha() != null) {
			fecha1 = o1.getFecha().toString();
		} 
		if(o2.getFecha() != null) {
			fecha2 = o2.getFecha().toString();
		}
		return fecha1.compareTo(fecha2);
	}

}

package ar.com.nextel.sfa.client.comparator;

import java.util.Comparator;

import ar.com.nextel.sfa.client.dto.DocDigitalizadosDto;

public class DocDigitalizadosComparator implements Comparator<DocDigitalizadosDto> {

	public DocDigitalizadosComparator() {
		
	}
	
	public int compare(DocDigitalizadosDto o1, DocDigitalizadosDto o2) {
		if(o1.getFecha() != null && o2.getFecha() != null) {
			return o1.getFecha().compareTo(o2.getFecha());
		} else  if(o1.getFecha() == null && o2.getFecha() == null) {
			return 0;
		} 
		else  if(o1.getFecha() != null) {
			return -1;
		}
		else  if(o2.getFecha() != null) {
			return 1;
		}
		return o1.getFecha().compareTo(o2.getFecha());
	}

}

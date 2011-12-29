package ar.com.nextel.sfa.client.ss;

import java.util.Collections;
import java.util.List;

import ar.com.nextel.sfa.client.comparator.AnticipoSSComparator;
import ar.com.nextel.sfa.client.comparator.DesviosSSComparator;
import ar.com.nextel.sfa.client.comparator.EquiposSSComparator;
import ar.com.nextel.sfa.client.comparator.FechaCierreSSComparator;
import ar.com.nextel.sfa.client.comparator.FechaCreacionSSComparator;
import ar.com.nextel.sfa.client.comparator.FirmasSSComparator;
import ar.com.nextel.sfa.client.comparator.NumeroCuentaSSComparator;
import ar.com.nextel.sfa.client.comparator.NumeroSSComparator;
import ar.com.nextel.sfa.client.comparator.PataconexSSComparator;
import ar.com.nextel.sfa.client.comparator.PinScoringSSComparator;
import ar.com.nextel.sfa.client.comparator.RazonSocialSSComparator;
import ar.com.nextel.sfa.client.comparator.ScoringSSComparator;
import ar.com.nextel.sfa.client.comparator.UltimoEstadoSSComparator;
import ar.com.nextel.sfa.client.comparator.UsuarioCreacionSSComparator;
import ar.com.nextel.sfa.client.comparator.VendedorSSComparator;
import ar.com.nextel.sfa.client.comparator.VerazSSComparator;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;

public class OrdenTablaSolicitudServicio {

	private int nroColumna;
	private List<SolicitudServicioCerradaResultDto> solicitudesServicioCerradaResultDto; 
	private boolean ordenDesc;
	private int nroColumnaAnterior = 0;
	
	public  List<SolicitudServicioCerradaResultDto> ordenar(int nroColumna, List<SolicitudServicioCerradaResultDto> solicitudesServicioCerradaResultDto) {
		switch (nroColumna) {
		case 2: // NUMERO SS
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 2) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new NumeroSSComparator(isOrdenDesc()));
			break;
		case 3: // NUMERO CUENTA
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 3) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			System.out.println(isOrdenDesc());
			Collections.sort(solicitudesServicioCerradaResultDto, new NumeroCuentaSSComparator(isOrdenDesc()));
			break;
		case 4: // RAZON SOCIAL
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 4) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new RazonSocialSSComparator(isOrdenDesc()));
			break;
		case 5: // EQUIPOS 
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 5) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new EquiposSSComparator(isOrdenDesc()));
			break;
		case 6: // USUARIO CREACION 
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 6) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new UsuarioCreacionSSComparator(isOrdenDesc()));
			break;
		case 7: // VENDEDOR
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 7) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new VendedorSSComparator(isOrdenDesc()));
			break;
		case 8: // PIN/SCORING
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 8) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new PinScoringSSComparator(isOrdenDesc()));
			break;
		case 9: // SCORING
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 9) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new ScoringSSComparator(isOrdenDesc()));
			break;
		case 10: // VERAZ
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 10) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new VerazSSComparator(isOrdenDesc()));
			break;
		case 11: // ULTIMO ESTADO 
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 11) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new UltimoEstadoSSComparator(isOrdenDesc()));
			break;
		case 12: // FECHA CREACION
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 12) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new FechaCreacionSSComparator(isOrdenDesc()));
			break;
		case 13: // FECHA CIERRE
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 13) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new FechaCierreSSComparator(isOrdenDesc()));
			break;
		case 14: // PATACONEX
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 14) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new PataconexSSComparator(isOrdenDesc()));
			break;
		case 15: // FIRMAS
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 15) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new FirmasSSComparator(isOrdenDesc()));
			break;
		case 16: // ANTICIPO
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 16) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new AnticipoSSComparator(isOrdenDesc()));
			break;
		case 17: // DESVIOS
			if(nroColumnaAnterior != 0 && nroColumnaAnterior == 17) {
				ordenar();
			} else {
				setNroColumnaAnterior(nroColumna);
				setOrdenDesc(false);
			}
			Collections.sort(solicitudesServicioCerradaResultDto, new DesviosSSComparator(isOrdenDesc()));
		default:
			break;
		}
		return solicitudesServicioCerradaResultDto;
	}

	public int getNroColumna() {
		return nroColumna;
	}

	public void setNroColumna(int nroColumna) {
		this.nroColumna = nroColumna;
	}

	public List<SolicitudServicioCerradaResultDto> getSolicitudesServicioCerradaResultDto() {
		return solicitudesServicioCerradaResultDto;
	}

	public void setSolicitudesServicioCerradaResultDto(
			List<SolicitudServicioCerradaResultDto> solicitudesServicioCerradaResultDto) {
		this.solicitudesServicioCerradaResultDto = solicitudesServicioCerradaResultDto;
	}

	public boolean isOrdenDesc() {
		return ordenDesc;
	}

	public void setOrdenDesc(boolean ordenDesc) {
		this.ordenDesc = ordenDesc;
	}
	
	public void ordenar(){
		if(ordenDesc) {
			ordenDesc = false;
		} else {
			ordenDesc = true;
		}
	}

	public int getNroColumnaAnterior() {
		return nroColumnaAnterior;
	}

	public void setNroColumnaAnterior(int nroColumnaAnterior) {
		this.nroColumnaAnterior = nroColumnaAnterior;
	}
	
	
	
}

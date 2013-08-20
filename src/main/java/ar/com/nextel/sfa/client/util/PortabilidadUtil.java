package ar.com.nextel.sfa.client.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;

import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;

public class PortabilidadUtil {
	
	public PortabilidadUtil(){
		
	}
	
	public void generarNroSS(SolicitudServicioDto solicitudServicio){
		if(solicitudServicio.getNumero() == null || solicitudServicio.getNumero().length() < 1) return;
			
		boolean carga;
		boolean agrega;
		long idOperador;
		String apoderado;
		SolicitudPortabilidadDto porta;

		List<Long> operadores = new ArrayList<Long>();
		List<String> apoderados = new ArrayList<String>();
		List<Integer> prepagos = new ArrayList<Integer>();
		List<Integer> postpagos = new ArrayList<Integer>();
		List<List<Integer>> postpagos_apod_op = new ArrayList<List<Integer>>();
		
		List<LineaSolicitudServicioDto> lineas = solicitudServicio.getLineas();
		
		//Recorre las lineas y divide la portabilidad en prepago y postpago
		for(int i = 0; i < lineas.size(); i++){
			if(lineas.get(i).getPortabilidad() != null){
				lineas.get(i).getPortabilidad().setNroSS("");
				if(lineas.get(i).getPortabilidad().getTipoTelefonia().getId() == 1) prepagos.add(i);
				else postpagos.add(i);
			}
		}
		
		// Divide las solicitudes postpagas
		
		//Recorre los postpagos y extrae por separado las portabilidades con mismo apoderado y con mismo operador
		for(int i = 0; i < postpagos.size(); i++){
			porta = lineas.get(postpagos.get(i)).getPortabilidad();
			apoderado = porta.getRazonSocial()+ porta.getApellido() + porta.getNombre() + porta.getTipoDocumento().getId() + porta.getNumeroDocumento();
			idOperador = porta.getProveedorAnterior().getId();

			if(apoderados.size() > 0){
				agrega = true;
				for(int j = 0; j < apoderados.size(); j++){
					if(apoderado.equals(apoderados.get(j))) agrega = false;
				}
				if(agrega) apoderados.add(apoderado);
			}else apoderados.add(apoderado);
			
			if(operadores.size() > 0){
				agrega = true;
				for(int j = 0; j < operadores.size(); j++){
					if(idOperador == operadores.get(j)) agrega = false;
				}
				if(agrega) operadores.add(idOperador);
			}else operadores.add(idOperador);
		}
		
		
		//Recorre apoderados
		for(int i = 0; i < apoderados.size(); i++){
			//Recorre operadores
			for(int j = 0; j < operadores.size(); j++){
				carga = true;
				//Recorre los postpagos y compara el operador y el apoderado
				for(int n = 0; n < postpagos.size(); n++){
					porta = lineas.get(postpagos.get(n)).getPortabilidad();
					apoderado = porta.getRazonSocial()+ porta.getApellido() + porta.getNombre() + porta.getTipoDocumento().getId() + porta.getNumeroDocumento();
					idOperador = porta.getProveedorAnterior().getId();
					
					if(apoderado.equals(apoderados.get(i)) && idOperador == operadores.get(j)){
						//La primera vez genera el array
						if(carga){
							postpagos_apod_op.add(new ArrayList<Integer>());
							carga = false;
						}
						postpagos_apod_op.get(postpagos_apod_op.size()-1).add(postpagos.get(n));
					}
				}
			}
		}
		
		// Asigna los Nro de solicitud de servicio	
		String nroSS;
		int cont = 0;
		
		for(int i = 0; i < lineas.size(); i++){
			for(int j = 0; j < prepagos.size()/* && !encontroPre*/; j++){
				if(lineas.get(i).getAlias().equals(lineas.get(prepagos.get(j)).getAlias())){
					//if(cont == 0) nroSS = "N" + solicitudServicio.getNumero();
					//else nroSS = "N" + solicitudServicio.getNumero() + "." + String.valueOf(cont);
					if(cont == 0) nroSS = solicitudServicio.getNumero();
					else nroSS = solicitudServicio.getNumero() + "." + String.valueOf(cont);
					lineas.get(i).getPortabilidad().setNroSS(nroSS);
					cont++;
				}
			}
			
			for(int j = 0; j < postpagos_apod_op.size(); j++){
				for(int n = 0; n < postpagos_apod_op.get(j).size(); n++){
					if(lineas.get(i).getAlias().equals(lineas.get(postpagos_apod_op.get(j).get(n)).getAlias())){
						if(lineas.get(postpagos_apod_op.get(j).get(n)).getPortabilidad().getNroSS().length() == 0){
							//if(cont == 0) nroSS = "N" + solicitudServicio.getNumero();
							//else nroSS = "N" + solicitudServicio.getNumero() + "." + String.valueOf(cont);
							if(cont == 0) nroSS = solicitudServicio.getNumero();
							else nroSS = solicitudServicio.getNumero() + "." + String.valueOf(cont);
							cont++;
							
							for(int k = 0; k < postpagos_apod_op.get(j).size(); k++){
								lineas.get(postpagos_apod_op.get(j).get(k)).getPortabilidad().setNroSS(nroSS);
							}
						}
					}
				}
			}
		}
	}
}

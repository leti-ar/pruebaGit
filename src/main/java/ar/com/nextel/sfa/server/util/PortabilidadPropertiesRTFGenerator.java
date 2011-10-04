package ar.com.nextel.sfa.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.util.AppLogger;

public class PortabilidadPropertiesRTFGenerator {
	
	
	private List<LineaSolicitudServicioDto> lineas;
	private List<Map<String,String>> reportes_parametros = new ArrayList<Map<String,String>>();
	private List<Map<String, String>> reportes_parametros_adjunto = new ArrayList<Map<String, String>>();
	private CuentaDto cuenta;
	
	/**
	 * 
	 * @param lineas
	 */
	public PortabilidadPropertiesRTFGenerator(CuentaDto cuenta,List<LineaSolicitudServicioDto> lineas){
		this.cuenta = cuenta;
		this.lineas = lineas;
		generar();
	}
	
	/**
	 * 
	 */
	private void generar(){
		List<List<Integer>> x_nroSS = new ArrayList<List<Integer>>();
		boolean encontro;
		String nroSS_1;
		String nroSS_2;
		
		for(int i = 0; i < lineas.size(); i++){
			if(lineas.get(i).getPortabilidad() != null){
				encontro = false;
				for(int j = 0; j < x_nroSS.size() && !encontro; j++){
					nroSS_1 = lineas.get(i).getPortabilidad().getNroSS();
					nroSS_2 = lineas.get(x_nroSS.get(j).get(0)).getPortabilidad().getNroSS();
					
					if(nroSS_1.equals(nroSS_2)){
						x_nroSS.get(j).add(i);
						encontro = true;
					}
				}
				
				if(!encontro){
					x_nroSS.add(new ArrayList<Integer>());
					x_nroSS.get(x_nroSS.size() - 1).add(i);
				}
			}
		}
		
		ordernar_recibeSMS(x_nroSS);
		crearParametros(x_nroSS);
	}
	
	/**
	 * 
	 */
	private void ordernar_recibeSMS(List<List<Integer>> postpagos_divididos){
		int aux;
		
		for(int i = 0; i < postpagos_divididos.size(); i++){
			for(int j = 0; j < postpagos_divididos.get(i).size() - 1; j++){
				if(!lineas.get(postpagos_divididos.get(i).get(j)).getPortabilidad().isRecibeSMS()){
					aux = postpagos_divididos.get(i).get(j);
					postpagos_divididos.get(i).remove(j);
					postpagos_divididos.get(i).add(aux);
				}
			}
		}
	}

	/**
	 * 
	 */
	private void crearParametros(List<List<Integer>> postagos_divididos){
		boolean makeArch;
		LineaSolicitudServicioDto linea;
		String indice;
		String tipo_persona;
		
		//Recorre los postpagos divididos por apoderados
		for(int i= 0; i < postagos_divididos.size(); i++){
			reportes_parametros.add(new HashMap<String, String>());
			makeArch = false;
			
			//Recorre los postagos divididos en apoderados, divididos por operadores 
			for(int j = 0; j < postagos_divididos.get(i).size() && !makeArch; j++){
				linea = lineas.get(postagos_divididos.get(i).get(j));
				indice = String.valueOf(j + 1);
				
				// El primero siempre se adhiere al reporte(recibe SMS)
				if(j == 0){
					if(cuenta.getClaseCuenta().getEsGobierno()) tipo_persona = "3";
					else{
						if(cuenta.isEmpresa()) tipo_persona = "2";
						else tipo_persona = "1";
					}

					reportes_parametros.get(i).put("NRO_DOC_SS", cuenta.getPersona().getDocumento().getNumero());
					reportes_parametros.get(i).put("TIPO_DOC_SS", cuenta.getPersona().getDocumento().getTipoDocumento().getDescripcion());
					reportes_parametros.get(i).put("TIPO_PERSONA_SS", tipo_persona);
					reportes_parametros.get(i).put("RAZON_SOCIAL_SS", cuenta.getPersona().getRazonSocial());
					reportes_parametros.get(i).put("APELLIDO_NOMBRE_SS",cuenta.getPersona().getApellido() + ", " + cuenta.getPersona().getNombre());
					
					reportes_parametros.get(i).put("NRO_DOC_PRTB", linea.getPortabilidad().getNumeroDocumento());
					reportes_parametros.get(i).put("TIPO_DOC_PRTB", linea.getPortabilidad().getTipoDocumento().getDescripcion());
					reportes_parametros.get(i).put("TEL_CONTACTO_PRTB", linea.getPortabilidad().getTelefono());
					reportes_parametros.get(i).put("NRO_SOLICITUD_PRTB", linea.getPortabilidad().getId().toString());
					reportes_parametros.get(i).put("EMAIL_CONTACTO_PRTB", linea.getPortabilidad().getEmail());
					reportes_parametros.get(i).put("APELLIDO_NOMBRE_PRTB", linea.getPortabilidad().getApellido() + ", " + linea.getPortabilidad().getNombre());

					reportes_parametros.get(i).put("OPERADOR_DONADOR", linea.getPortabilidad().getProveedorAnterior().getDescripcion());
					reportes_parametros.get(i).put("OPERADOR_RECEPTOR", "NEXTEL");
					reportes_parametros.get(i).put("MODALIDAD_CONTRATACION", linea.getPortabilidad().getTipoTelefonia().getId().toString());
					
					reportes_parametros.get(i).put(
							"LINEA_" + indice,linea.getPortabilidad().getAreaTelefono() + " " + linea.getPortabilidad().getTelefonoPortar());
					reportes_parametros.get(i).put("FACTURA_" + indice,linea.getPortabilidad().getNroUltimaFacura());
				}else{
					// Si no es el primero verifica que la cantidad de portabilidades sean hasta 5 maximo
					if(postagos_divididos.get(i).size() <= 5){
						reportes_parametros.get(i).put(
								"LINEA_" + indice,linea.getPortabilidad().getAreaTelefono() + " " + linea.getPortabilidad().getTelefonoPortar());
						reportes_parametros.get(i).put("FACTURA_" + indice,linea.getPortabilidad().getNroUltimaFacura());
					}else makeArch = true; // Al ser mas de 5 genera un reporte con el primero que recibe SMS y un reporte adicional con todos
				}
			}
	
			// Si hay mas de 5 items genera una archivo aparte con los items parametrizados y los restantes
			if(makeArch){ 
				reportes_parametros_adjunto.add(new HashMap<String, String>());
				
				for (int j = 0; j < postagos_divididos.get(i).size(); j++){
					linea = lineas.get(postagos_divididos.get(i).get(j));
					indice = String.valueOf(j + 1);

					reportes_parametros_adjunto.get(reportes_parametros_adjunto.size() - 1).put(
							"LINEA_" + indice,linea.getPortabilidad().getAreaTelefono() + " " + linea.getPortabilidad().getTelefonoPortar());
					reportes_parametros_adjunto.get(reportes_parametros_adjunto.size() - 1).put(
							"FACTURA_" + indice,linea.getPortabilidad().getNroUltimaFacura());

				}
			}
		}
	}
	
// ===================================================================================	
	
	/**
	 * 
	 */
	public List<Map<String, String>> getReportes_parametros() {
		return reportes_parametros;
	}

	public List<Map<String, String>> getReportes_parametros_adjunto() {
		return reportes_parametros_adjunto;
	}

	/**
	 * 
	 * @return
	 */
	public List<LineaSolicitudServicioDto> getLineas() {
		return lineas;
	}

	/**
	 * 
	 * @param lineas
	 */
	public void setLineas(List<LineaSolicitudServicioDto> lineas) {
		this.lineas = lineas;
		generar();
	}

}


package ar.com.nextel.sfa.client.dto;

import java.util.HashMap;

import ar.com.nextel.sfa.client.context.ClientContext;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GrupoSolicitudDto implements IsSerializable {

	public static final String ID_EQUIPOS_ACCESORIOS = "GRUPO1";
	public static final String ID_CDW = "GRUPO3";
	public static final String ID_MDS = "GRUPO4";
	public static final String ID_FAC_MENSUAL = "GRUPO6";
	//MGR - #1039
	public static final String DESPACHO_TEL_ANEXO = "GRUPO7";
	public static final String ID_TRANSFERENCIA = "GRUPO5";

	private Long id;
	private String descripcion;

	/**
	 * Rangos para número de trípticos con/sin PIN
	 */
	private Long rangoMinimoConPin;
	private Long rangoMaximoConPin;
	private Long rangoMinimoSinPin;
	private Long rangoMaximoSinPin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getRangoMinimoConPin() {
		return rangoMinimoConPin;
	}

	public void setRangoMinimoConPin(Long rangoMinimoConPin) {
		this.rangoMinimoConPin = rangoMinimoConPin;
	}

	public Long getRangoMaximoConPin() {
		return rangoMaximoConPin;
	}

	public void setRangoMaximoConPin(Long rangoMaximoConPin) {
		this.rangoMaximoConPin = rangoMaximoConPin;
	}

	public Long getRangoMinimoSinPin() {
		return rangoMinimoSinPin;
	}

	public void setRangoMinimoSinPin(Long rangoMinimoSinPin) {
		this.rangoMinimoSinPin = rangoMinimoSinPin;
	}

	public Long getRangoMaximoSinPin() {
		return rangoMaximoSinPin;
	}

	public void setRangoMaximoSinPin(Long rangoMaximoSinPin) {
		this.rangoMaximoSinPin = rangoMaximoSinPin;
	}

	public boolean isEquiposAccesorios() {
		//MGR - #1050
		//return ID_EQUIPOS_ACCESORIOS.equals(id);
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null){
			return instancias.get(ID_EQUIPOS_ACCESORIOS).equals(id);
		}
		return false;
	}

	public boolean isCDW() {
		//MGR - #1050
		//return ID_CDW.equals(id);
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null){
			return instancias.get(ID_CDW).equals(id);
		}
		return false;
	}

	public boolean isMDS() {
		//MGR - #1050
		//return ID_MDS.equals(id);
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null){
			return instancias.get(ID_MDS).equals(id);
		}
		return false;
	}
	
	public boolean isTransferencia() {
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null){
			return instancias.get(ID_TRANSFERENCIA).equals(id);
		}
		return false;
	}
}

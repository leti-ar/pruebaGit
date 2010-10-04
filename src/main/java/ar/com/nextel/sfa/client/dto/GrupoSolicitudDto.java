package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GrupoSolicitudDto implements IsSerializable {

	public static final Long ID_EQUIPOS_ACCESORIOS = Long.valueOf(1);
	public static final Long ID_CDW = Long.valueOf(3);
	public static final Long ID_MDS = Long.valueOf(4);
	public static final Long ID_FAC_MENSUAL	 = Long.valueOf(61);
	//MGR - #1039
	public static final Long DESPACHO_TEL_ANEXO	 = Long.valueOf(21);

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
		return ID_EQUIPOS_ACCESORIOS.equals(id);
	}

	public boolean isCDW() {
		return ID_CDW.equals(id);
	}

	public boolean isMDS() {
		return ID_MDS.equals(id);
	}

}

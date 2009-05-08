package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GrupoSolicitudDto implements IsSerializable{

	private Long Id;
    private String descripcion;

    /**
     * Los tipos de solicitud pertenecientes al grupo
     */
    private List<TipoSolicitudDto> tiposSolicitud;
    
    /** 
     * Rangos para número de trípticos con/sin PIN
     */
    private Long rangoMinimoConPin;
    private Long rangoMaximoConPin;
    private Long rangoMinimoSinPin;
    private Long rangoMaximoSinPin;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    
    
}

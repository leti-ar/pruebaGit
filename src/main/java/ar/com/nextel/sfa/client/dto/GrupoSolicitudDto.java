package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GrupoSolicitudDto implements IsSerializable{

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
}

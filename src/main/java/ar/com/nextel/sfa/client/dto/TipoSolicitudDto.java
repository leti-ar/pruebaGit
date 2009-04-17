package ar.com.nextel.sfa.client.dto;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoSolicitudDto implements IsSerializable {

    private String descripcion;
    private String codigoFNCL;
    private Boolean anexaPorPinMaestro;
    private Set<ListaPreciosDto> listasPrecios = new HashSet<ListaPreciosDto>();
    private GrupoSolicitudDto grupoSolicitud;
    private Long ordenAparicion;
    
}

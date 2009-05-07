package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("solicitud.rpc")
public interface SolicitudRpcService extends RemoteService {

	public static class Util {

		private static SolicitudRpcServiceAsync solicitudRpcServiceAsync = null;
		private static SolicitudRpcServiceDelegate solicitudRpcServiceDelegate = null;

		public static SolicitudRpcServiceDelegate getInstance() {
			if (solicitudRpcServiceDelegate == null) {
				solicitudRpcServiceAsync = GWT.create(SolicitudRpcService.class);
				solicitudRpcServiceDelegate = new SolicitudRpcServiceDelegate(solicitudRpcServiceAsync);
			}
			return solicitudRpcServiceDelegate;
		}
	}
	
	public BuscarSSCerradasInitializer getBuscarSSCerradasInitializer();
	
	public List<SolicitudServicioCerradaResultDto> searchSSCerrada(SolicitudServicioCerradaDto solicitudServicioCerradaDto);

	public SolicitudServicioDto createSolicitudServicio(SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages;

	public SolicitudInitializer getSolicitudInitializer();

	public SolicitudServicioDto saveSolicituServicio(SolicitudServicioDto solicitudServicioDto);
	
	public DetalleSolicitudServicioDto getDetalleSolicitudServicio(Long idSolicitudServicio) ;
}

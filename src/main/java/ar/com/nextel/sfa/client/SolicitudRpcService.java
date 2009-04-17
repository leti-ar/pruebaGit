package ar.com.nextel.sfa.client;

import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("solicitud.rpc")
public interface SolicitudRpcService extends RemoteService {

	public static class Util {

		public static SolicitudRpcServiceAsync getInstance() {

			return GWT.create(SolicitudRpcService.class);
		}
	}
	
	public SolicitudServicioDto createSolicitudServicio(SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages;

}

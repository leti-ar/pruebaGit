package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioSearchResultDto;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("oportunidades.rpc")
public interface OportunidadNegocioRpcService extends RemoteService {

	public static class Util {

		private static OportunidadNegocioRpcServiceAsync oportunidadNegocioRpcService = null;
		private static OportunidadNegocioRpcServiceDelegate oportunidadNegocioRpcServiceDelegate = null;

		public static OportunidadNegocioRpcServiceDelegate getInstance() {
			if (oportunidadNegocioRpcServiceDelegate == null) {
				oportunidadNegocioRpcService = GWT.create(OportunidadNegocioRpcService.class);
				oportunidadNegocioRpcServiceDelegate = new OportunidadNegocioRpcServiceDelegate(
						oportunidadNegocioRpcService);
			}
			return oportunidadNegocioRpcServiceDelegate;
		}
	}

	public List<OportunidadNegocioSearchResultDto> searchOportunidad(OportunidadDto oportunidadDto)
			throws RpcExceptionMessages;

	public BuscarOportunidadNegocioInitializer getBuscarOportunidadInitializer() throws RpcExceptionMessages;

	// public void getAgregarOportunidadInitializer(AsyncCallback<AgregarCuentaInitializer> callback);
	//		
	// public void saveOportunidad(OportunidadSearchDto oportunidadSearchDto, AsyncCallback callback);
}

package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.OportunidadSearchDto;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;

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
					oportunidadNegocioRpcServiceDelegate = new OportunidadNegocioRpcServiceDelegate(oportunidadNegocioRpcService);
				}
				return oportunidadNegocioRpcServiceDelegate;
			}
		}
		
		public List<OportunidadSearchDto> searchOportunidad(OportunidadSearchDto oportunidadSearchDto);

		public BuscarOportunidadNegocioInitializer getBuscarOportunidadInitializer();

//		public void getAgregarOportunidadInitializer(AsyncCallback<AgregarCuentaInitializer> callback);
//		
//		public void saveOportunidad(OportunidadSearchDto oportunidadSearchDto, AsyncCallback callback);
}

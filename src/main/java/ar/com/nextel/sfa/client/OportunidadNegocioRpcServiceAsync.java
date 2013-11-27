package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioSearchResultDto;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OportunidadNegocioRpcServiceAsync { 

		public void searchOportunidad(OportunidadDto oportunidadDto, AsyncCallback<List<OportunidadNegocioSearchResultDto>> callback);

		public void getBuscarOportunidadInitializer(AsyncCallback<BuscarOportunidadNegocioInitializer> callback);

//		public void getAgregarOportunidadInitializer(AsyncCallback<AgregarCuentaInitializer> callback);
//		
//		public void saveOportunidad(OportunidadSearchDto oportunidadSearchDto, AsyncCallback callback);
}
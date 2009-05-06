package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CuentaRpcServiceAsync {

	public void getAgregarCuentaInitializer(AsyncCallback<AgregarCuentaInitializer> callback);

	public void getBuscarCuentaInitializer(AsyncCallback<BuscarCuentaInitializer> callback);

	public void saveCuenta(PersonaDto personaDto, AsyncCallback callback);

	public void searchCuenta(CuentaSearchDto cuentaSearchDto, AsyncCallback<List<CuentaSearchResultDto>> callback);
	
	public void getVerazInitializer(AsyncCallback<VerazInitializer> callback);
	
	public void consultarVeraz(PersonaDto personaDto, AsyncCallback callback);
	
	public void selectCuenta(Long cuentaId, String cod_vantive,AsyncCallback<CuentaDto> callback);
	
	public void reservaCreacionCuenta(DocumentoDto docDto, AsyncCallback<CuentaDto> callback);

}

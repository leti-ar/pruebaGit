package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.VerazSearchDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioSearchDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CuentaRpcServiceAsync {

	public void getAgregarCuentaInitializer(AsyncCallback<AgregarCuentaInitializer> callback);

	public void getBuscarCuentaInitializer(AsyncCallback<BuscarCuentaInitializer> callback);

	public void getBuscarSSCerradasInitializer(AsyncCallback<BuscarSSCerradasInitializer> callback);

	public void saveCuenta(PersonaDto personaDto, AsyncCallback callback);

	public void searchCuenta(CuentaSearchDto cuentaSearchDto, AsyncCallback<List<CuentaDto>> callback);

	public void searchSSCerrada(SolicitudServicioDto solicitudServicioDto,
			AsyncCallback<List<SolicitudServicioDto>> callback);
	
	public void getVerazInitializer(AsyncCallback<VerazInitializer> callback);
	
	public void searchVeraz(VerazSearchDto verazSearchDto, AsyncCallback callback);
	public void searchSSCerrada(SolicitudServicioSearchDto solicitudServicioSearchDto,
			AsyncCallback<SolicitudesServicioTotalesDto> callback);

}

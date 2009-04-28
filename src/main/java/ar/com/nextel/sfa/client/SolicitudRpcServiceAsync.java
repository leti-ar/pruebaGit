package ar.com.nextel.sfa.client;

import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SolicitudRpcServiceAsync {

	public void getBuscarSSCerradasInitializer(AsyncCallback<BuscarSSCerradasInitializer> callback);
	
	public void searchSSCerrada(SolicitudServicioCerradaDto solicitudServicioCerradaDto, AsyncCallback<SolicitudesServicioTotalesDto> callback);
	
	public void createSolicitudServicio(SolicitudServicioRequestDto solicitudServicioRequestDto, AsyncCallback<SolicitudServicioDto> callback);

	public void getSolicitudInitializer(AsyncCallback<SolicitudInitializer> callback);

	public void saveSolicituServicio(SolicitudServicioDto solicitudServicioDto,
			AsyncCallback<SolicitudServicioDto> callback);
}

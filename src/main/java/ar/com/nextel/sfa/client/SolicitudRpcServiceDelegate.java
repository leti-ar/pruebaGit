package ar.com.nextel.sfa.client;

import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

public class SolicitudRpcServiceDelegate {

	private SolicitudRpcServiceAsync solicitudRpcServiceAsync;

	public SolicitudRpcServiceDelegate() {
	}

	public SolicitudRpcServiceDelegate(SolicitudRpcServiceAsync solicitudRpcServiceAsync) {
		this.solicitudRpcServiceAsync = solicitudRpcServiceAsync;
	}

	public void createSolicitudServicio(SolicitudServicioRequestDto solicitudServicioRequestDto,
			DefaultWaitCallback<SolicitudServicioDto> callback) {
		solicitudRpcServiceAsync.createSolicitudServicio(solicitudServicioRequestDto, callback);

	}

	public void getSolicitudInitializer(DefaultWaitCallback<SolicitudInitializer> callback) {
		solicitudRpcServiceAsync.getSolicitudInitializer(callback);
	}

	public void saveSolicituServicio(SolicitudServicioDto solicitudServicioDto,
			DefaultWaitCallback<SolicitudServicioDto> callback) {
		solicitudRpcServiceAsync.saveSolicituServicio(solicitudServicioDto, callback);
	}

	public void getBuscarSSCerradasInitializer(DefaultWaitCallback<BuscarSSCerradasInitializer> callback) {
		solicitudRpcServiceAsync.getBuscarSSCerradasInitializer(callback);	
	}

	public void searchSSCerrada(SolicitudServicioCerradaDto solicitudServicioCerradaDto, DefaultWaitCallback<SolicitudesServicioTotalesDto> callback) {
		solicitudRpcServiceAsync.searchSSCerrada(solicitudServicioCerradaDto, callback);		
	}

}

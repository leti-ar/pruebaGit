package ar.com.nextel.sfa.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

public class SolicitudRpcServiceDelegate {

	private SolicitudRpcServiceAsync solicitudRpcServiceAsync;

	public SolicitudRpcServiceDelegate() {
	}

	public SolicitudRpcServiceDelegate(SolicitudRpcServiceAsync solicitudRpcServiceAsync) {
		this.solicitudRpcServiceAsync = solicitudRpcServiceAsync;
	}

	public void createSolicitudServicio(SolicitudServicioRequestDto solicitudServicioRequestDto,
			DefaultWaitCallback<SolicitudServicioDto> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.createSolicitudServicio(solicitudServicioRequestDto, callback);

	}

	public void getSolicitudInitializer(DefaultWaitCallback<SolicitudInitializer> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getSolicitudInitializer(callback);
	}

	public void saveSolicituServicio(SolicitudServicioDto solicitudServicioDto,
			DefaultWaitCallback<SolicitudServicioDto> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.saveSolicituServicio(solicitudServicioDto, callback);
	}

	public void getBuscarSSCerradasInitializer(DefaultWaitCallback<BuscarSSCerradasInitializer> callback) {
		solicitudRpcServiceAsync.getBuscarSSCerradasInitializer(callback);
	}

	public void searchSSCerrada(SolicitudServicioCerradaDto solicitudServicioCerradaDto,
			DefaultWaitCallback<List<SolicitudServicioCerradaResultDto>> callback) {
		solicitudRpcServiceAsync.searchSSCerrada(solicitudServicioCerradaDto, callback);
	}
	
	public void getDetalleSolicitudServicio(Long idSolicitudServicio, DefaultWaitCallback<DetalleSolicitudServicioDto> callback) {
		solicitudRpcServiceAsync.getDetalleSolicitudServicio(idSolicitudServicio, callback);
	}

	public void getLineasSolicitudServicioInitializer(GrupoSolicitudDto grupoSolicitudDto,
			DefaultWaitCallback<LineasSolicitudServicioInitializer> callback) {
		solicitudRpcServiceAsync.getLineasSolicitudServicioInitializer(grupoSolicitudDto, callback);
	}

}

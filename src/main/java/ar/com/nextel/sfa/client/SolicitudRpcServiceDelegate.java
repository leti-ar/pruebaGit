package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
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
		WaitWindow.show();
		solicitudRpcServiceAsync.getBuscarSSCerradasInitializer(callback);
	}

	public void searchSSCerrada(SolicitudServicioCerradaDto solicitudServicioCerradaDto,
			DefaultWaitCallback<List<SolicitudServicioCerradaResultDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.searchSSCerrada(solicitudServicioCerradaDto, callback);
	}

	public void getDetalleSolicitudServicio(Long idSolicitudServicio,
			DefaultWaitCallback<DetalleSolicitudServicioDto> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getDetalleSolicitudServicio(idSolicitudServicio, callback);
	}

	public void getLineasSolicitudServicioInitializer(GrupoSolicitudDto grupoSolicitudDto,
			DefaultWaitCallback<LineasSolicitudServicioInitializer> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getLineasSolicitudServicioInitializer(grupoSolicitudDto, callback);
	}

	public void buildExcel(SolicitudServicioCerradaDto solicitudServicioCerradaDto,
			DefaultWaitCallback<String> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.buildExcel(solicitudServicioCerradaDto, callback);
	}

	public void getListasDePrecios(TipoSolicitudDto tipoSolicitudDto,
			DefaultWaitCallback<List<ListaPreciosDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getListasDePrecios(tipoSolicitudDto, callback);
	}

	public void getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado, TipoPlanDto tipoPlan,
			Long idCuenta, DefaultWaitCallback<List<PlanDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getPlanesPorItemYTipoPlan(itemSolicitudTasado, tipoPlan, idCuenta, callback);
	}

	public void getServiciosAdicionales(LineaSolicitudServicioDto linea,
			DefaultWaitCallback<LineaSolicitudServicioDto> defaultWaitCallback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getServiciosAdicionales(linea, defaultWaitCallback);
	}

}

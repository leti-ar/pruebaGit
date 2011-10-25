package ar.com.nextel.sfa.client;

import java.util.List;
import java.util.Map;

import ar.com.nextel.business.solicitudes.report.SolicitudPortabilidadPropertiesReport;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSSTransfResultDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSolicitudServicioResultDto;
import ar.com.nextel.sfa.client.dto.DescuentoDto;
import ar.com.nextel.sfa.client.dto.DescuentoLineaDto;
import ar.com.nextel.sfa.client.dto.DescuentoTotalDto;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.GeneracionCierreResultDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ResultadoReservaNumeroTelefonoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalIncluidoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.TipoDescuentoDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.ContratoViewInitializer;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.initializer.PortabilidadInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.nextel.sfa.client.util.PortabilidadResult;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SolicitudRpcServiceDelegate {

	private SolicitudRpcServiceAsync solicitudRpcServiceAsync;

	public SolicitudRpcServiceDelegate() {
	}

	public SolicitudRpcServiceDelegate(SolicitudRpcServiceAsync solicitudRpcServiceAsync) {
		this.solicitudRpcServiceAsync = solicitudRpcServiceAsync;
	}

	//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un CreateSaveSolicitudServicioResultDto 
	//que permite realizar el manejo de mensajes
	public void createSolicitudServicio(SolicitudServicioRequestDto solicitudServicioRequestDto,
			DefaultWaitCallback<CreateSaveSolicitudServicioResultDto> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.createSolicitudServicio(solicitudServicioRequestDto, callback);

	}

	public void getSolicitudInitializer(DefaultWaitCallback<SolicitudInitializer> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getSolicitudInitializer(callback);
	}

	//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un SaveSolicitudServicioResultDto
	//que permite realizar el manejo de mensajes
	public void saveSolicituServicio(SolicitudServicioDto solicitudServicioDto,
			DefaultWaitCallback<CreateSaveSolicitudServicioResultDto> callback) {
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
			boolean isEmpresa, DefaultWaitCallback<LineasSolicitudServicioInitializer> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getLineasSolicitudServicioInitializer(grupoSolicitudDto, isEmpresa, callback);
	}

	public void buildExcel(SolicitudServicioCerradaDto solicitudServicioCerradaDto,
			DefaultWaitCallback<String> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.buildExcel(solicitudServicioCerradaDto, callback);
	}

	public void getListasDePrecios(TipoSolicitudDto tipoSolicitudDto, boolean isEmpresa,
			DefaultWaitCallback<List<ListaPreciosDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getListasDePrecios(tipoSolicitudDto, isEmpresa, callback);
	}

	public void getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado, TipoPlanDto tipoPlan,
			Long idCuenta, DefaultWaitCallback<List<PlanDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getPlanesPorItemYTipoPlan(itemSolicitudTasado, tipoPlan, idCuenta, callback);
	}

	public void getServiciosAdicionales(LineaSolicitudServicioDto linea, Long idCuenta,
			boolean isEmpresa, DefaultWaitCallback<List<ServicioAdicionalLineaSolicitudServicioDto>> defaultWaitCallback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getServiciosAdicionales(linea, idCuenta, isEmpresa, defaultWaitCallback);
	}

	public void reservarNumeroTelefonico(long numero, long idTipoTelefonia, long idModalidadCobro,
			long idLocalidad, DefaultWaitCallback<ResultadoReservaNumeroTelefonoDto> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.reservarNumeroTelefonico(numero, idTipoTelefonia, idModalidadCobro,
				idLocalidad, callback);
	}

	public void desreservarNumeroTelefono(long numero, Long idLineaSolicitudServicio, DefaultWaitCallback callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.desreservarNumeroTelefono(numero, idLineaSolicitudServicio, callback);
	}

	public void getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios,
			DefaultWaitCallback<List<ModeloDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getModelos(imei, idTipoSolicitud, idListaPrecios, callback);
	}

	public void verificarNegativeFiles(String numero, DefaultWaitCallback<String> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.verificarNegativeFiles(numero, callback);
	}

	public void generarCerrarSolicitud(SolicitudServicioDto solicitudServicioDto, String pinMaestro,
			boolean cerrar, DefaultWaitCallback<GeneracionCierreResultDto> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.generarCerrarSolicitud(solicitudServicioDto, pinMaestro, cerrar, callback);
	}

	public void existReport(String report, DefaultWaitCallback<Boolean> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.existReport(report, callback);
	}
	
	public void getVendedoresDae(DefaultWaitCallback<List<VendedorDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getVendedoresDae(callback);
	}
	
	public void getDescuentos(Long idLinea, DefaultWaitCallback<List<DescuentoDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getDescuentos(idLinea, callback);
	}

	public void getDescuentosAplicados(Long idLinea,
			DefaultWaitCallback<List<DescuentoLineaDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getDescuentosAplicados(idLinea, callback);
	}

	public void getTiposDescuento(Long idLinea,
			DefaultWaitCallback<List<TipoDescuentoDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getTiposDescuento(idLinea, callback);
	}
	
	public void getTiposDescuentoAplicados(Long idLinea,
			DefaultWaitCallback<List<TipoDescuentoDto>> defaultWaitCallback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getTiposDescuentoAplicados(idLinea, defaultWaitCallback);
	}

	public void puedeAplicarDescuento(List<LineaSolicitudServicioDto> lineas,
			DefaultWaitCallback<Boolean> defaultWaitCallback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.puedeAplicarDescuento(lineas, defaultWaitCallback);
	}

	public void getInterseccionTiposDescuento(List<LineaSolicitudServicioDto> lineas,
			DefaultWaitCallback<List<TipoDescuentoDto>> defaultWaitCallback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getInterseccionTiposDescuento(lineas, defaultWaitCallback);
	}

	public void getDescuentosTotales(Long idLinea,
			DefaultWaitCallback<DescuentoTotalDto> defaultWaitCallback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getDescuentosTotales(idLinea, defaultWaitCallback);
	}
	
	public void loginServer(String linea){
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}
			public void onSuccess(Void result) {
			}
		};
		solicitudRpcServiceAsync.loginServer(linea, callback);
	}

	//MGR - #1415
	public void crearArchivo(Long idSolicitud,
			boolean enviarEmail, DefaultWaitCallback<Boolean> defaultWaitCallback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.crearArchivo(idSolicitud, enviarEmail, defaultWaitCallback);
	}

	public void getPlanesPorTipoPlan(Long idTipoPlan, Long idCuenta,
			DefaultWaitCallback<List<PlanDto>> defaultWaitCallback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getPlanesPorTipoPlan(idTipoPlan, idCuenta, defaultWaitCallback);
	}

	public void getServiciosAdicionalesContrato(Long idPlan,
			DefaultWaitCallback<List<ServicioAdicionalIncluidoDto>> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getServiciosAdicionalesContrato(idPlan, callback);
	}
	
	public void saveSolicituServicioTranferencia(SolicitudServicioDto solicitudServicioDto,
			AsyncCallback<CreateSaveSSTransfResultDto> callback){
		WaitWindow.show();
		solicitudRpcServiceAsync.saveSolicituServicioTranferencia(solicitudServicioDto, callback);
	}
	
	public void createSolicitudServicioTranferencia(SolicitudServicioRequestDto solicitudServicioRequestDto,
			DefaultWaitCallback<CreateSaveSSTransfResultDto> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.createSolicitudServicioTranferencia(solicitudServicioRequestDto, callback);

	}

	public void getContratoViewInitializer(GrupoSolicitudDto grupoSolicitud,
			DefaultWaitCallback<ContratoViewInitializer> callback) {
		WaitWindow.show();
		solicitudRpcServiceAsync.getContratoViewInitializer(grupoSolicitud, callback);
	}

	public void validarPlanesCedentes(List<ContratoViewDto> ctoCedentes, boolean isEmpresa,
			boolean isSaving, DefaultWaitCallback<List<String>> callback){
		WaitWindow.show();
		solicitudRpcServiceAsync.validarPlanesCedentes(ctoCedentes, isEmpresa, isSaving, callback);
	}

	// Portabilidad ------------------------------
	public void getPortabilidadInitializer(long cuentaID, AsyncCallback<PortabilidadInitializer> callback){
		WaitWindow.show();
		solicitudRpcServiceAsync.getPortabilidadInitializer(cuentaID, callback);
	}

	public void getSolicitudPortabilidadDto(String lineaID, AsyncCallback<SolicitudPortabilidadDto> callback){
		WaitWindow.show();
		solicitudRpcServiceAsync.getSolicitudPortabilidadDto(lineaID, callback);
	}

	public void getExisteEnAreaCobertura(int codArea,AsyncCallback<Boolean> callback){
		WaitWindow.show();
		solicitudRpcServiceAsync.getExisteEnAreaCobertura(codArea, callback);
	}

	public void validarPortabilidad(SolicitudServicioDto solicitudServicioDto,AsyncCallback<PortabilidadResult> callback){
		WaitWindow.show();
		solicitudRpcServiceAsync.validarPortabilidad(solicitudServicioDto, callback);
	}

	public void generarParametrosPortabilidadRTF(Long idSolicitudServicio,AsyncCallback<List<String>> callback){
		WaitWindow.show();
		solicitudRpcServiceAsync.generarParametrosPortabilidadRTF(idSolicitudServicio, callback);
	}
	// -------------------------------------------


}

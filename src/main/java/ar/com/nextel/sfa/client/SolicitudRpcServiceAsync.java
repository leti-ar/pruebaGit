package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSSTransfResultDto;
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
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SolicitudRpcServiceAsync {

	public void getBuscarSSCerradasInitializer(AsyncCallback<BuscarSSCerradasInitializer> callback);

	public void searchSSCerrada(SolicitudServicioCerradaDto solicitudServicioCerradaDto,
			AsyncCallback<List<SolicitudServicioCerradaResultDto>> callback);

	public void createSolicitudServicio(SolicitudServicioRequestDto solicitudServicioRequestDto,
			AsyncCallback<SolicitudServicioDto> callback);

	public void getSolicitudInitializer(AsyncCallback<SolicitudInitializer> callback);

	public void saveSolicituServicio(SolicitudServicioDto solicitudServicioDto,
			AsyncCallback<SolicitudServicioDto> callback);

	public void getDetalleSolicitudServicio(Long idSolicitudServicio,
			AsyncCallback<DetalleSolicitudServicioDto> callback);

	public void getLineasSolicitudServicioInitializer(GrupoSolicitudDto grupoSolicitudDto,
			AsyncCallback<LineasSolicitudServicioInitializer> callback);

	public void getListasDePrecios(TipoSolicitudDto tipoSolicitudDto,
			AsyncCallback<List<ListaPreciosDto>> callback);

	public void buildExcel(SolicitudServicioCerradaDto solicitudServicioCerradaDto,
			AsyncCallback<String> callback);

	public void getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado, TipoPlanDto tipoPlan,
			Long idCuenta, AsyncCallback<List<PlanDto>> callback);

	public void getServiciosAdicionales(LineaSolicitudServicioDto linea, Long idCuenta,
			AsyncCallback<List<ServicioAdicionalLineaSolicitudServicioDto>> defaultWaitCallback);

	public void reservarNumeroTelefonico(long numero, long idTipoTelefonia, long idModalidadCobro,
			long idLocalidad, AsyncCallback<ResultadoReservaNumeroTelefonoDto> callback);

	public void desreservarNumeroTelefono(long numero, Long idLineaSolicitudServicio, AsyncCallback callback);

	public void getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios,
			AsyncCallback<List<ModeloDto>> callback);

	public void verificarNegativeFiles(String numero, AsyncCallback<String> callback);

	public void generarCerrarSolicitud(SolicitudServicioDto solicitudServicioDto, String pinMaestro,
			boolean cerrar, AsyncCallback<GeneracionCierreResultDto> callback);

	public void existReport(String report, AsyncCallback<Boolean> callback);
	
	public void getVendedoresDae(AsyncCallback<List<VendedorDto>> callback);
	
	public void getDescuentos(Long idLinea, AsyncCallback<List<DescuentoDto>> callback);

	public void getDescuentosAplicados(Long idLinea, AsyncCallback<List<DescuentoLineaDto>> callback);

	public void getTiposDescuento(Long idLinea, AsyncCallback<List<TipoDescuentoDto>> callback);

	public void getTiposDescuentoAplicados(Long idLinea, AsyncCallback<List<TipoDescuentoDto>> callback);

	public void puedeAplicarDescuento(List<LineaSolicitudServicioDto> lineas,
			AsyncCallback<Boolean> defaultWaitCallback);

	public void getInterseccionTiposDescuento(List<LineaSolicitudServicioDto> lineas,
			AsyncCallback<List<TipoDescuentoDto>> defaultWaitCallback);

	public void getDescuentosTotales(Long idLinea,
			AsyncCallback<DescuentoTotalDto> defaultWaitCallback);

	public void crearArchivo(SolicitudServicioCerradaResultDto solicitud,
			boolean enviarEmail, AsyncCallback<Boolean> defaultWaitCallback);

	public void getPlanesPorTipoPlan(Long idTipoPlan, Long idCuenta,
			AsyncCallback<List<PlanDto>> defaultWaitCallback);

	public void getServiciosAdicionalesContrato(Long idPlan,
			AsyncCallback<List<ServicioAdicionalIncluidoDto>> callback);

	public void saveSolicituServicioTranferencia(SolicitudServicioDto solicitudServicioDto,
			AsyncCallback<CreateSaveSSTransfResultDto> callback);

	public void createSolicitudServicioTranferencia(SolicitudServicioRequestDto solicitudServicioRequestDto,
			AsyncCallback<CreateSaveSSTransfResultDto> callback);

	public void getContratoViewInitializer(GrupoSolicitudDto grupoSolicitud,
			AsyncCallback<ContratoViewInitializer> callback);

	void validarPlanesCedentes(List<ContratoViewDto> ctoCedentes,
			boolean isEmpresa, AsyncCallback<List<String>> callback);
	
	public void loginServer(String linea, AsyncCallback<Void> callback);
}

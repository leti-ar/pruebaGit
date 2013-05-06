package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.ControlDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSSTransfResultDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSolicitudServicioResultDto;
import ar.com.nextel.sfa.client.dto.DescuentoDto;
import ar.com.nextel.sfa.client.dto.DescuentoLineaDto;
import ar.com.nextel.sfa.client.dto.DescuentoTotalDto;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.EstadoPorSolicitudDto;
import ar.com.nextel.sfa.client.dto.GeneracionCierreResultDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudDto;
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
import ar.com.nextel.sfa.client.dto.TipoPersonaDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.ContratoViewInitializer;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.initializer.PortabilidadInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.nextel.sfa.client.util.PortabilidadResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SolicitudRpcServiceAsync {

	//LF
	//public void getBuscarSSCerradasInitializer(AsyncCallback<BuscarSSCerradasInitializer> callback);
	public void getBuscarSSInitializer(boolean analistaCreditos, AsyncCallback<BuscarSSCerradasInitializer> callback);

	//LF
	//void searchSSCerrada(
	public void searchSolicitudesServicio(
	SolicitudServicioCerradaDto solicitudServicioCerradaDto,
	  //LF#3boolean analistaCreditos,
			AsyncCallback<List<SolicitudServicioCerradaResultDto>> callback);

	//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un CreateSaveSolicitudServicioResultDto 
	//que permite realizar el manejo de mensajes
	public void createSolicitudServicio(SolicitudServicioRequestDto solicitudServicioRequestDto,
			AsyncCallback<CreateSaveSolicitudServicioResultDto> callback);

	public void getSolicitudInitializer(AsyncCallback<SolicitudInitializer> callback);

	//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un SaveSolicitudServicioResultDto
	//que permite realizar el manejo de mensajes
	public void saveSolicituServicio(SolicitudServicioDto solicitudServicioDto,
			AsyncCallback<CreateSaveSolicitudServicioResultDto> callback);

	public void getDetalleSolicitudServicio(Long idSolicitudServicio,
			AsyncCallback<DetalleSolicitudServicioDto> callback);

	public void getLineasSolicitudServicioInitializer(GrupoSolicitudDto grupoSolicitudDto,
			boolean isEmpresa, AsyncCallback<LineasSolicitudServicioInitializer> callback);

	public void getListasDePrecios(TipoSolicitudDto tipoSolicitudDto, boolean isEmpresa, 
			AsyncCallback<List<ListaPreciosDto>> callback);

	public void buildExcel(SolicitudServicioCerradaDto solicitudServicioCerradaDto, //LF#3boolean analistaCreditos,
			AsyncCallback<String> callback);

//	MGR - #3462 - Es necesario indicar el modelo y si es activacion online
	public void getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado, TipoPlanDto tipoPlan,
			Long idCuenta, boolean isActivacion, ModeloDto modelo, AsyncCallback<List<PlanDto>> callback);

	public void getServiciosAdicionales(LineaSolicitudServicioDto linea, Long idCuenta,
			boolean isEmpresa, AsyncCallback<List<ServicioAdicionalLineaSolicitudServicioDto>> defaultWaitCallback);

	public void reservarNumeroTelefonico(long numero, long idTipoTelefonia, long idModalidadCobro,
			long idLocalidad, AsyncCallback<ResultadoReservaNumeroTelefonoDto> callback);

	public void desreservarNumeroTelefono(long numero, Long idLineaSolicitudServicio, AsyncCallback callback);

	public void getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios,
			AsyncCallback<List<ModeloDto>> callback);

	public void verificarNegativeFiles(String numero, AsyncCallback<String> callback);

	public void generarCerrarSolicitud(SolicitudServicioDto solicitudServicioDto, String pinMaestro,
			boolean cerrar, boolean pinChequeadoEnNexus, AsyncCallback<GeneracionCierreResultDto> callback);

	public void existReport(String report, AsyncCallback<Boolean> callback);
	
	public void existDocDigitalizado(String server, String pathAndNameFile, AsyncCallback<Boolean> callback);
	
	public void obtenerPathLinux(String server, String pathAndNameFile, AsyncCallback<String> callback);
	
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

	//MGR - #1415
	public void crearArchivo(Long idSolicitud,
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
			boolean isEmpresa, boolean isSaving, AsyncCallback<List<String>> callback);
	
	public void loginServer(String linea, AsyncCallback<Void> callback);

	public void getSSPorIdCuentaYNumeroSS(Long idCuenta, String numeroSS, AsyncCallback<List<SolicitudServicioDto>> defaultWaitCallback);

	public void getItemsPorLineaSS(SolicitudServicioDto ss,	AsyncCallback<List<ItemSolicitudDto>> callback);
	
	// Portabilidad ------------------------------
	public void getPortabilidadInitializer(String idCuenta,String codigoVantive,
			AsyncCallback<PortabilidadInitializer> callback);

	void getExisteEnAreaCobertura(int codArea, 
			AsyncCallback<Boolean> callback);

	void getSolicitudPortabilidadDto(String lineaID,
			AsyncCallback<SolicitudPortabilidadDto> callback);

	void validarPortabilidad(SolicitudServicioDto solicitudServicioDto,
			AsyncCallback<PortabilidadResult> callback);

	void generarParametrosPortabilidadRTF(Long idSolicitudServicio,
			AsyncCallback<List<String>> callback);
	// -------------------------------------------

	void getCantidadLineasPortabilidad(List<Long> listIdSS,
			AsyncCallback<List<Long>> callback);

	void validarPortabilidadTransferencia(List<ContratoViewDto> contratos,
			AsyncCallback<PortabilidadResult> callback);

//	void obtenerTipoPersona(SolicitudServicioDto solicitudServicioDto,
//			AsyncCallback<Integer> callback);	

	void obtenerTipoPersonaCuenta(SolicitudServicioDto ssDto,
			AsyncCallback<TipoPersonaDto> callback);
	
	public void copySolicitudServicio(
			SolicitudServicioRequestDto solicitudServicioRequestDto , SolicitudServicioDto solicitudToCopy,
			AsyncCallback<CreateSaveSolicitudServicioResultDto> callback);
	
	public void createCopySolicitudServicioTranferencia(
			SolicitudServicioRequestDto solicitudServicioRequestDto,
			SolicitudServicioDto solicitudSS,
			AsyncCallback<CreateSaveSSTransfResultDto> callback);

	public void calcularCantEquipos(List<LineaSolicitudServicioDto> lineaSS,
			AsyncCallback<Integer> callback);

	public void buscarHistoricoVentas(String nss, AsyncCallback<List<SolicitudServicioDto>> callback);

	public void saveEstadoPorSolicitudDto(EstadoPorSolicitudDto estadoPorSolicitudDto,
			AsyncCallback<Boolean> callback);
	
	public void getEstadoSolicitud(long numeroSS,AsyncCallback<String> callback);
	
	public void buscarSSPorId(Long id, AsyncCallback<SolicitudServicioDto> callback);

	public void buscarVendedorPorId(Long id, AsyncCallback<VendedorDto> callback);
	
	public void enviarMail(String subject, String to, AsyncCallback<Void> callback);
	
	public void enviarSMS(String to,String mensaje ,AsyncCallback<Void> callback);
	
	public void validarCuentaPorId(SolicitudServicioDto solicitud, AsyncCallback<Integer> callback);
	
	public void changeToPass(long idSS ,AsyncCallback<Void> callback);

	public void getControles(AsyncCallback<List<ControlDto>> callback);
	
	public void validarLineasPorSegmento(SolicitudServicioDto solicitud, AsyncCallback<Boolean> callback);

	public void sonConfigurablesPorAPG(List<LineaSolicitudServicioDto> lineas, AsyncCallback<Integer> callback);

//	MGR - RQN 2328
	public void validarAreaBilling(String numeroAPortar, AsyncCallback<Boolean> callback);
}

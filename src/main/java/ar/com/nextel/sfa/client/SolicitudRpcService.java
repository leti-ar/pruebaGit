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
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("solicitud.rpc")
public interface SolicitudRpcService extends RemoteService {

	public static class Util {

		private static SolicitudRpcServiceAsync solicitudRpcServiceAsync = null;
		private static SolicitudRpcServiceDelegate solicitudRpcServiceDelegate = null;

		public static SolicitudRpcServiceDelegate getInstance() {
			if (solicitudRpcServiceDelegate == null) {
				solicitudRpcServiceAsync = GWT.create(SolicitudRpcService.class);
				solicitudRpcServiceDelegate = new SolicitudRpcServiceDelegate(solicitudRpcServiceAsync);
			}
			return solicitudRpcServiceDelegate;
		}
	}

	public BuscarSSCerradasInitializer getBuscarSSInitializer(boolean analistaCredito) throws RpcExceptionMessages;
	
	//LF
	//public List<SolicitudServicioCerradaResultDto> searchSSCerrada(
	public List<SolicitudServicioCerradaResultDto> searchSolicitudesServicio(
			SolicitudServicioCerradaDto solicitudServicioCerradaDto//LF#3, boolean analistaCreditos
			) throws RpcExceptionMessages;
	
	//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un CreateSaveSolicitudServicioResultDto 
	//que permite realizar el manejo de mensajes
	public CreateSaveSolicitudServicioResultDto createSolicitudServicio(
			SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages;

	public CreateSaveSolicitudServicioResultDto copySolicitudServicio(
			SolicitudServicioRequestDto solicitudServicioRequestDto , SolicitudServicioDto solicitudToCopy) throws RpcExceptionMessages;
	
	public SolicitudInitializer getSolicitudInitializer() throws RpcExceptionMessages;

	//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un SaveSolicitudServicioResultDto 
	//que permite realizar el manejo de mensajes
	public CreateSaveSolicitudServicioResultDto saveSolicituServicio(SolicitudServicioDto solicitudServicioDto)
			throws RpcExceptionMessages;
	
	public boolean saveEstadoPorSolicitudDto(EstadoPorSolicitudDto estadoPorSolicitudDto) throws RpcExceptionMessages;

	public LineasSolicitudServicioInitializer getLineasSolicitudServicioInitializer(
			GrupoSolicitudDto grupoSolicitudDto, boolean isEmpresa) throws RpcExceptionMessages;

	public DetalleSolicitudServicioDto getDetalleSolicitudServicio(Long idSolicitudServicio)
			throws RpcExceptionMessages;

	public List<ListaPreciosDto> getListasDePrecios(TipoSolicitudDto tipoSolicitudDto, boolean isEmpresa)
			throws RpcExceptionMessages;

	public String buildExcel(SolicitudServicioCerradaDto solicitudServicioCerradaDto//LF#3, boolean analistaCreditos
			) throws RpcExceptionMessages;

//	MGR - #3462 - Es necesario indicar el modelo y si es activacion online
	public List<PlanDto> getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado,
			TipoPlanDto tipoPlan, Long idCuenta, boolean isActivacion, ModeloDto modelo);

	public List<ServicioAdicionalLineaSolicitudServicioDto> getServiciosAdicionales(
			LineaSolicitudServicioDto linea, Long idCuenta, boolean isEmpresa) throws RpcExceptionMessages;

	public ResultadoReservaNumeroTelefonoDto reservarNumeroTelefonico(long numero, long idTipoTelefonia,
			long idModalidadCobro, long idLocalidad) throws RpcExceptionMessages;

	public void desreservarNumeroTelefono(long numero, Long idLineaSolicitudServicio) throws RpcExceptionMessages;

	public List<ModeloDto> getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios)
			throws RpcExceptionMessages;

	public String verificarNegativeFiles(String numero) throws RpcExceptionMessages;

	public GeneracionCierreResultDto generarCerrarSolicitud(SolicitudServicioDto solicitudServicioDto,
			String pinMaestro, boolean cerrar) throws RpcExceptionMessages;

	public Boolean existReport(String report) throws RpcExceptionMessages;
	
	public Boolean existDocDigitalizado(String server, String pathAndNameFile) throws RpcExceptionMessages;
	
	public String obtenerPathLinux(String server, String pathAndNameFile) throws RpcExceptionMessages;
	
	public List<VendedorDto> getVendedoresDae() throws RpcExceptionMessages;

	public List<DescuentoDto> getDescuentos(Long idLinea) throws RpcExceptionMessages;

	public List<DescuentoLineaDto> getDescuentosAplicados(Long idLinea) throws RpcExceptionMessages;
	
	public List<TipoDescuentoDto> getTiposDescuento(Long idLinea) throws RpcExceptionMessages;
	
	public List<TipoDescuentoDto> getTiposDescuentoAplicados(Long idLinea) throws RpcExceptionMessages;

	public boolean puedeAplicarDescuento(List<LineaSolicitudServicioDto> lineas) throws RpcExceptionMessages;

	public List<TipoDescuentoDto> getInterseccionTiposDescuento(List<LineaSolicitudServicioDto> lineas) throws RpcExceptionMessages;

	public DescuentoTotalDto getDescuentosTotales(Long idLinea) throws RpcExceptionMessages;
	
	public void loginServer(String linea);
	
	//MGR - #1415
	public Boolean crearArchivo(Long idSolicitud, boolean enviarEmail) throws RpcExceptionMessages;

	public List<PlanDto> getPlanesPorTipoPlan(Long idTipoPlan, Long idCuenta) throws RpcExceptionMessages;

	public List<ServicioAdicionalIncluidoDto> getServiciosAdicionalesContrato(Long idPlan) throws RpcExceptionMessages;

	public CreateSaveSSTransfResultDto saveSolicituServicioTranferencia(SolicitudServicioDto solicitudServicioDto)	throws RpcExceptionMessages;

	public CreateSaveSSTransfResultDto createSolicitudServicioTranferencia(SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages;

	public ContratoViewInitializer getContratoViewInitializer(GrupoSolicitudDto grupoSolicitud) throws RpcExceptionMessages;
	//MGR - #1481
	/**
	 * Si no se cambio el plan cedente, valida que el plan que se quiere transferir sea del mismo
	 * segmento que el cliente cesionario
	 * @param ctoCedentes Todos los contratos de los que quiero validar el plan cedente
	 * @param isEmpresa Si el cliente cesionario es del tipo empresa
	 * @return Lista con los errores posibles.
	 */
	public List<String> validarPlanesCedentes(List<ContratoViewDto> ctoCedentes, boolean isEmpresa, boolean isSaving);
	
	//LF
	/**
	 * Realiza una consulta a la tabla SFA_SS_CABECERA con los datos pasados por parametros y retorna una 
	 * lista de Solicitudes de servicio que posee. 
	 * @param idCuenta El id de la cuenta
	 * @param numeroSS El numero de la solicitud de servicio 
	 * @return Lista de SolicitudServicioDto
	 */
	public List<SolicitudServicioDto> getSSPorIdCuentaYNumeroSS(Long idCuenta, String numeroSS);
	
	//LF
	/**
	 * Carga en una lista de ItemSolicitudDto, cada item que corresponde a cada linea 
	 * de la SolicitudServicioDto pasado por parametro.
	 * Realiza una query para obtener el item de cada linea de SS.
	 * 
	 * @param SolicitudServicioDto 
	 * @return Lista de ItemSolicitudDto
	 */
	public List<ItemSolicitudDto> getItemsPorLineaSS(SolicitudServicioDto ss);
	
	// Portabilidad ------------------------------
	PortabilidadInitializer getPortabilidadInitializer(String idCuenta,String codigoVantive) throws RpcExceptionMessages;
	
	SolicitudPortabilidadDto getSolicitudPortabilidadDto(String lineaID) throws RpcExceptionMessages;
	
	boolean getExisteEnAreaCobertura(int codArea) throws RpcExceptionMessages;
	
	PortabilidadResult validarPortabilidadTransferencia(List<ContratoViewDto> contratos) throws RpcExceptionMessages;

	PortabilidadResult validarPortabilidad(SolicitudServicioDto solicitudServicioDto) throws RpcExceptionMessages;
	
	List<String> generarParametrosPortabilidadRTF(Long idSolicitudServicio) throws RpcExceptionMessages;
	
	List<Long> getCantidadLineasPortabilidad(List<Long> listIdSS) throws RpcExceptionMessages;
	
	TipoPersonaDto obtenerTipoPersonaCuenta(SolicitudServicioDto ssDto); 
//	public Integer obtenerTipoPersona(SolicitudServicioDto solicitudServicioDto) throws RpcExceptionMessages;
	
	public List<SolicitudServicioDto> buscarHistoricoVentas(String nss) throws RpcExceptionMessages;

	public CreateSaveSSTransfResultDto createCopySolicitudServicioTranferencia(
			SolicitudServicioRequestDto solicitudServicioRequestDto,
			SolicitudServicioDto solicitudSS) throws RpcExceptionMessages;
	
	public Integer calcularCantEquipos(List<LineaSolicitudServicioDto> lineaSS);
	
	public SolicitudServicioDto buscarSSPorId(Long id);	
	
	public String getEstadoSolicitud(long numeroSS)throws RpcExceptionMessages;
	
	public VendedorDto buscarVendedorPorId(Long id);
	
	public void enviarMail(String subject, String to);
	
	public void enviarSMS(String to, String mensaje);
	
	public Integer validarCuentaPorId(SolicitudServicioDto solicitud) throws RpcExceptionMessages;
	
	public void changeToPass(long idSS) throws RpcExceptionMessages;
	
	public List<ControlDto> getControles() throws RpcExceptionMessages;
	
	public boolean validarLineasPorSegmento(SolicitudServicioDto solicitud) throws RpcExceptionMessages;

	public int sonConfigurablesPorAPG(List<LineaSolicitudServicioDto> lineas) throws RpcExceptionMessages;
}


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

	public BuscarSSCerradasInitializer getBuscarSSCerradasInitializer() throws RpcExceptionMessages;

	public List<SolicitudServicioCerradaResultDto> searchSSCerrada(
			SolicitudServicioCerradaDto solicitudServicioCerradaDto) throws RpcExceptionMessages;

	public SolicitudServicioDto createSolicitudServicio(
			SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages;

	public SolicitudInitializer getSolicitudInitializer() throws RpcExceptionMessages;

	public SolicitudServicioDto saveSolicituServicio(SolicitudServicioDto solicitudServicioDto)
			throws RpcExceptionMessages;

	public LineasSolicitudServicioInitializer getLineasSolicitudServicioInitializer(
			GrupoSolicitudDto grupoSolicitudDto) throws RpcExceptionMessages;

	public DetalleSolicitudServicioDto getDetalleSolicitudServicio(Long idSolicitudServicio)
			throws RpcExceptionMessages;

	public List<ListaPreciosDto> getListasDePrecios(TipoSolicitudDto tipoSolicitudDto)
			throws RpcExceptionMessages;

	public String buildExcel(SolicitudServicioCerradaDto solicitudServicioCerradaDto)
			throws RpcExceptionMessages;

	public List<PlanDto> getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado,
			TipoPlanDto tipoPlan, Long idCuenta);

	public List<ServicioAdicionalLineaSolicitudServicioDto> getServiciosAdicionales(
			LineaSolicitudServicioDto linea, Long idCuenta) throws RpcExceptionMessages;

	public ResultadoReservaNumeroTelefonoDto reservarNumeroTelefonico(long numero, long idTipoTelefonia,
			long idModalidadCobro, long idLocalidad) throws RpcExceptionMessages;

	public void desreservarNumeroTelefono(long numero, Long idLineaSolicitudServicio) throws RpcExceptionMessages;

	public List<ModeloDto> getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios)
			throws RpcExceptionMessages;

	public String verificarNegativeFiles(String numero) throws RpcExceptionMessages;

	public GeneracionCierreResultDto generarCerrarSolicitud(SolicitudServicioDto solicitudServicioDto,
			String pinMaestro, boolean cerrar) throws RpcExceptionMessages;

	public Boolean existReport(String report) throws RpcExceptionMessages;
	
	public List<VendedorDto> getVendedoresDae() throws RpcExceptionMessages;

	public List<DescuentoDto> getDescuentos(Long idLinea) throws RpcExceptionMessages;

	public List<DescuentoLineaDto> getDescuentosAplicados(Long idLinea) throws RpcExceptionMessages;
	
	public List<TipoDescuentoDto> getTiposDescuento(Long idLinea) throws RpcExceptionMessages;
	
	public List<TipoDescuentoDto> getTiposDescuentoAplicados(Long idLinea) throws RpcExceptionMessages;

	public boolean puedeAplicarDescuento(List<LineaSolicitudServicioDto> lineas) throws RpcExceptionMessages;

	public List<TipoDescuentoDto> getInterseccionTiposDescuento(List<LineaSolicitudServicioDto> lineas) throws RpcExceptionMessages;

	public DescuentoTotalDto getDescuentosTotales(Long idLinea) throws RpcExceptionMessages;
	
	public void loginServer(String linea);
	public Boolean crearArchivo(SolicitudServicioCerradaResultDto solicitud, boolean enviarEmail) throws RpcExceptionMessages;

	public List<PlanDto> getPlanesPorTipoPlan(Long idTipoPlan, Long idCuenta) throws RpcExceptionMessages;

	public List<ServicioAdicionalIncluidoDto> getServiciosAdicionalesContrato(Long idPlan) throws RpcExceptionMessages;

	public CreateSaveSSTransfResultDto saveSolicituServicioTranferencia(SolicitudServicioDto solicitudServicioDto)	throws RpcExceptionMessages;

	public CreateSaveSSTransfResultDto createSolicitudServicioTranferencia(SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages;

	public ContratoViewInitializer getContratoViewInitializer(GrupoSolicitudDto grupoSolicitud) throws RpcExceptionMessages;
	
	/**
	 * Valida que cada uno de los planes cedentes enviados, primero exista en sfa, y si todos ellos existen, 
	 * validad que los mismos sean validos para la cuenta enviada
	 * @param ctoCedentes Todos los contratos de los que quiero validar el plan cedente
	 * @param idCuenta Cuenta en la que quiero ver si el plan es valido
	 * @return Lista con los errores posibles. (Max cinco errores a la vez)
	 */
	public List<String> validarPlanesCedentes(List<ContratoViewDto> ctoCedentes, Long idCuenta);
}

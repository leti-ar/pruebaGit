package ar.com.nextel.sfa.client;

import java.io.File;
import java.util.List;

import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.GeneracionCierreResultDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ResultadoReservaNumeroTelefonoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
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

	public void desreservarNumeroTelefono(long numero) throws RpcExceptionMessages;

	public List<ModeloDto> getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios)
			throws RpcExceptionMessages;

	public String verificarNegativeFiles(String numero) throws RpcExceptionMessages;

	public GeneracionCierreResultDto generarCerrarSolicitud(SolicitudServicioDto solicitudServicioDto,
			String pinMaestro, boolean cerrar) throws RpcExceptionMessages;

	public Boolean existReport(String report) throws RpcExceptionMessages;
}

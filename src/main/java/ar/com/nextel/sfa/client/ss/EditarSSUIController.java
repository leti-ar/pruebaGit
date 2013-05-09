package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ResultadoReservaNumeroTelefonoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalIncluidoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SubsidiosDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.initializer.ContratoViewInitializer;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

public interface EditarSSUIController {

	EditarSSUIData getEditarSSUIData();

	public void getLineasSolicitudServicioInitializer(
			DefaultWaitCallback<LineasSolicitudServicioInitializer> defaultWaitCallback);

	public void getListaPrecios(TipoSolicitudDto tipoSolicitudDto, boolean isEmpresa,
			DefaultWaitCallback<List<ListaPreciosDto>> defaultWaitCallback);

//	MGR - #3462 - Es necesario indicar el modelo y si es activacion online
	public void getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado, TipoPlanDto tipoPlan,
			boolean isActivacion, ModeloDto modelo, DefaultWaitCallback<List<PlanDto>> callback);

	public void getServiciosAdicionales(LineaSolicitudServicioDto linea,
			DefaultWaitCallback<List<ServicioAdicionalLineaSolicitudServicioDto>> defaultWaitCallback);

	public String getNombreProximoMovil();

	public void reservarNumeroTelefonico(long numero, long idTipoTelefonia, long idModalidadCobro,
			long idLocalidad, DefaultWaitCallback<ResultadoReservaNumeroTelefonoDto> callback);

	public void desreservarNumeroTelefonico(long numero, Long idLineaSolicitudServicio,
			DefaultWaitCallback callback);

	public void getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios,
			DefaultWaitCallback<List<ModeloDto>> callback);

	public void verificarNegativeFiles(String numero, DefaultWaitCallback<String> callback);

	public void borrarDescuentoSeleccionados();

	public void getPlanesPorTipoPlan(TipoPlanDto selectedItem,
			DefaultWaitCallback<List<PlanDto>> actualizarPlanCallback);

	void getServiciosAdicionalesContrato(Long idPlan,
			DefaultWaitCallback<List<ServicioAdicionalIncluidoDto>> defaultWaitCallback);

	void getContratoViewInitializer(DefaultWaitCallback<ContratoViewInitializer> callback);
	
	boolean isEditable();

	public void getSubsidiosPorItem(ItemSolicitudTasadoDto itemSolicitudTasado,
			DefaultWaitCallback<List<SubsidiosDto>> callback);

	public void loadTransferencia(Boolean tienePermanencia);

}

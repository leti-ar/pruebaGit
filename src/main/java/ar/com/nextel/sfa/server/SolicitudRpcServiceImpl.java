package ar.com.nextel.sfa.server;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.constants.KnownInstanceIdentifier;
import ar.com.nextel.business.dao.GenericDao;
import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.vantive.VantiveSystem;
import ar.com.nextel.business.legacy.vantive.dto.EstadoSolicitudServicioCerradaDTO;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.repository.SolicitudServicioRepository;
import ar.com.nextel.business.solicitudes.search.dto.SolicitudServicioCerradaSearchCriteria;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.components.knownInstances.retrievers.model.KnownInstanceRetriever;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.personas.beans.Localidad;
import ar.com.nextel.model.solicitudes.beans.EstadoSolicitud;
import ar.com.nextel.model.solicitudes.beans.GrupoSolicitud;
import ar.com.nextel.model.solicitudes.beans.ListaPrecios;
import ar.com.nextel.model.solicitudes.beans.OrigenSolicitud;
import ar.com.nextel.model.solicitudes.beans.ServicioAdicionalLineaSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.TipoAnticipo;
import ar.com.nextel.model.solicitudes.beans.TipoPlan;
import ar.com.nextel.model.solicitudes.beans.TipoSolicitud;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.CambiosSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.LocalidadDto;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.nextel.sfa.server.businessservice.SolicitudBusinessService;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.nextel.util.DateUtils;
import ar.com.nextel.util.ExcelBuilder;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

public class SolicitudRpcServiceImpl extends RemoteService implements SolicitudRpcService {

	private MapperExtended mapper;
	private WebApplicationContext context;
	private SolicitudBusinessService solicitudBusinessService;
	private Repository repository;
	private RegistroVendedores registroVendedores;
	private GenericDao genericDao;
	private SolicitudServicioBusinessOperator solicitudesBusinessOperator;
	private VantiveSystem vantiveSystem;
	private FinancialSystem financialSystem;
	private KnownInstanceRetriever knownInstanceRetriever;
	private SessionContextLoader sessionContextLoader;
	private SolicitudServicioRepository solicitudServicioRepository;

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		solicitudesBusinessOperator = (SolicitudServicioBusinessOperator) context
				.getBean("solicitudServicioBusinessOperatorBean");
		solicitudBusinessService = (SolicitudBusinessService) context.getBean("solicitudBusinessService");

		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
		genericDao = (GenericDao) context.getBean("genericDao");
		repository = (Repository) context.getBean("repository");

		solicitudServicioRepository = (SolicitudServicioRepository) context
				.getBean("solicitudServicioRepositoryBean");
		vantiveSystem = (VantiveSystem) context.getBean("vantiveSystemBean");
		financialSystem = (FinancialSystem) context.getBean("financialSystemBean");
		knownInstanceRetriever = (KnownInstanceRetriever) context.getBean("knownInstancesRetriever");
		sessionContextLoader = (SessionContextLoader) context.getBean("sessionContextLoader");
	}

	public SolicitudServicioDto createSolicitudServicio(
			SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages {
		SolicitudServicioRequest request = mapper.map(solicitudServicioRequestDto,
				SolicitudServicioRequest.class);
		AppLogger.info("Creando Solicitud de Servicio con Request -> " + request.toString());
		SolicitudServicio solicitud = null;
		try {
			solicitud = solicitudBusinessService.createSolicitudServicio(request);
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}
		SolicitudServicioDto solicitudServicioDto = mapper.map(solicitud, SolicitudServicioDto.class);

		AppLogger.info("Creacion de Solicitud de Servicio finalizada");
		return solicitudServicioDto;
	}

	public List<SolicitudServicioCerradaResultDto> searchSSCerrada(
			SolicitudServicioCerradaDto solicitudServicioCerradaDto) {
		AppLogger.info("Iniciando busqueda de SS cerradas...");
		SolicitudServicioCerradaSearchCriteria solicitudServicioCerradaSearchCriteria = mapper.map(
				solicitudServicioCerradaDto, SolicitudServicioCerradaSearchCriteria.class);
		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Vendedor vendedor = registroVendedores.getVendedor(usuario);
		solicitudServicioCerradaSearchCriteria.setVendedor(vendedor);
		List<SolicitudServicio> list = null;
		try {
			list = this.solicitudesBusinessOperator
					.searchSolicitudesServicioHistoricas(solicitudServicioCerradaSearchCriteria);
		} catch (Exception e) {
			AppLogger.info("Error buscando Solicitudes de Servicio cerradas: " + e.getMessage());
		}
		List result = mapper.convertList(list, SolicitudServicioCerradaResultDto.class, "ssCerradaResult");
		AppLogger.info("Busqueda de SS cerradas finalizada...");
		return result;
	}

	public DetalleSolicitudServicioDto getDetalleSolicitudServicio(Long idSolicitudServicio) {
		AppLogger.info("Iniciando consulta de estados de SS cerradas para idSS " + idSolicitudServicio);
		SolicitudServicio solicitudServicio = solicitudServicioRepository
				.getSolicitudServicioPorId(idSolicitudServicio);
		DetalleSolicitudServicioDto detalleSolicitudServicioDto = mapearSolicitud(solicitudServicio);

		try {
			detalleSolicitudServicioDto
					.setCambiosEstadoSolicitud(getEstadoSolicitudServicioCerrada(solicitudServicio
							.getIdVantive()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		AppLogger
				.info("Consulta de estados de SS cerradas para idSS " + idSolicitudServicio + " finalizada.");
		return detalleSolicitudServicioDto;
	}

	private DetalleSolicitudServicioDto mapearSolicitud(SolicitudServicio solicitudServicio) {
		DetalleSolicitudServicioDto detalleSolicitudServicioDto = new DetalleSolicitudServicioDto();
		detalleSolicitudServicioDto.setNumero(solicitudServicio.getNumero());
		detalleSolicitudServicioDto.setNumeroCuenta(solicitudServicio.getCuenta().getCodigoVantive());
		detalleSolicitudServicioDto.setRazonSocialCuenta(solicitudServicio.getCuenta().getPersona()
				.getRazonSocial());
		return detalleSolicitudServicioDto;
	}

	private List<CambiosSolicitudServicioDto> getEstadoSolicitudServicioCerrada(Long idVantiveSS)
			throws RpcExceptionMessages {
		try {
			List<EstadoSolicitudServicioCerradaDTO> resultDTO = null;
			resultDTO = this.vantiveSystem.retrieveEstadosSolicitudServicioCerrada(idVantiveSS);
			resultDTO.addAll(this.financialSystem.retrieveEstadosSolicitudServicioCerrada(idVantiveSS));

			Comparator<? super EstadoSolicitudServicioCerradaDTO> estadoComparator = new Comparator<EstadoSolicitudServicioCerradaDTO>() {

				public int compare(EstadoSolicitudServicioCerradaDTO estado1,
						EstadoSolicitudServicioCerradaDTO estado2) {
					int ret = 0;
					try {
						Date date1 = (estado1.getFechaCambioEstado() != null) ? DateUtils.getInstance()
								.getDate(estado1.getFechaCambioEstado(), "dd/MM/yyyy") : null;
						Date date2 = (estado2.getFechaCambioEstado() != null) ? DateUtils.getInstance()
								.getDate(estado2.getFechaCambioEstado(), "dd/MM/yyyy") : null;
						if (date1 == null || date2 == null) {
							return 1;
						} else {
							return DateUtils.getInstance().compareDatesByDay(date1, date2);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};

			Collections.sort(resultDTO, estadoComparator);
			return this.transformEstadoSolicitudServicioCerradaDTOToCambioEstadoSolicitudWCTO(resultDTO);
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}
	}

	private List<CambiosSolicitudServicioDto> transformEstadoSolicitudServicioCerradaDTOToCambioEstadoSolicitudWCTO(
			List<EstadoSolicitudServicioCerradaDTO> resultDTO) {
		List result = mapper.convertList(resultDTO, CambiosSolicitudServicioDto.class);
		return result;
	}

	public BuscarSSCerradasInitializer getBuscarSSCerradasInitializer() {
		BuscarSSCerradasInitializer buscarSSCerradasInitializer = new BuscarSSCerradasInitializer();

		List<String> listaResult = new ArrayList<String>();
		String cantResult = "10;25;50;75;100;500";
		listaResult = Arrays.asList(cantResult.split(";"));
		buscarSSCerradasInitializer.setCantidadesResultados(listaResult);

		List<String> listaFirmas = new ArrayList<String>();
		String opcionesFirmas = "Si;No";
		listaFirmas = Arrays.asList(opcionesFirmas.split(";"));
		buscarSSCerradasInitializer.setOpcionesFirmas(listaFirmas);

		List<String> listaPataconex = new ArrayList<String>();
		String opcionesPataconex = "Si;No";
		listaPataconex = Arrays.asList(opcionesPataconex.split(";"));
		buscarSSCerradasInitializer.setOpcionesPatacones(listaPataconex);

		buscarSSCerradasInitializer.setOpcionesEstado(mapper.convertList(genericDao
				.getList(EstadoSolicitud.class), EstadoSolicitudDto.class));

		return buscarSSCerradasInitializer;
	}

	public SolicitudInitializer getSolicitudInitializer() {
		SolicitudInitializer initializer = new SolicitudInitializer();
		initializer.setOrigenesSolicitud(mapper.convertList(repository.getAll(OrigenSolicitud.class),
				OrigenSolicitudDto.class));
		initializer.setTiposAnticipo(mapper.convertList(repository.getAll(TipoAnticipo.class),
				TipoAnticipoDto.class));
		return initializer;
	}

	public SolicitudServicioDto saveSolicituServicio(SolicitudServicioDto solicitudServicioDto) {
		SolicitudServicio solicitudServicio = repository.retrieve(SolicitudServicio.class,
				solicitudServicioDto.getId());
		mapper.map(solicitudServicioDto, solicitudServicio);
		solicitudBusinessService.saveSolicitudServicio(solicitudServicio);
		solicitudServicioDto = mapper.map(solicitudServicio, SolicitudServicioDto.class);
		return solicitudServicioDto;
	}

	public String buildExcel(SolicitudServicioCerradaDto solicitudServicioCerradaDto) {
		AppLogger.info("Iniciando busqueda de SS cerradas para crear excel...");
		SolicitudServicioCerradaSearchCriteria solicitudServicioCerradaSearchCriteria = mapper.map(
				solicitudServicioCerradaDto, SolicitudServicioCerradaSearchCriteria.class);
		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Vendedor vendedor = registroVendedores.getVendedor(usuario);
		solicitudServicioCerradaSearchCriteria.setVendedor(vendedor);
		List<SolicitudServicio> list = null;
		try {
			list = this.solicitudesBusinessOperator
					.searchSolicitudesServicioHistoricas(solicitudServicioCerradaSearchCriteria);
		} catch (Exception e) {
			AppLogger.info("Error buscando Solicitudes de Servicio cerradas para crear excel: "
					+ e.getMessage());
		}
		AppLogger.info("Creando archivo Excel...");
		ExcelBuilder excel = new ExcelBuilder(vendedor.getUserName(), "SFA Revolution");
		try {
			excel.crearExcel(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AppLogger.info("Archivo Excel creado.");
		return vendedor.getUserName();
	}

	public LineasSolicitudServicioInitializer getLineasSolicitudServicioInitializer(
			GrupoSolicitudDto grupoSolicitudDto) {
		LineasSolicitudServicioInitializer initializer = new LineasSolicitudServicioInitializer();
		GrupoSolicitud grupoSolicitud = repository.retrieve(GrupoSolicitud.class, Long.valueOf(1));
		List<TipoSolicitud> tiposSolicitudDeGrupo = grupoSolicitud
				.calculateTiposSolicitud(sessionContextLoader.getVendedor().getSucursal());

		// Obtengo los tipos de solicitud
		initializer.setTiposSolicitudes(mapper.convertList(tiposSolicitudDeGrupo, TipoSolicitudDto.class));
		// Si no es vacio (no deberia serlo) carga la lista de precios del primer tipoSolicitud que se muestra
		if (!initializer.getTiposSolicitudes().isEmpty()) {
			TipoSolicitudDto firstTipoSolicitud = initializer.getTiposSolicitudes().get(0);
			List<ListaPrecios> listasPrecios = new ArrayList<ListaPrecios>(tiposSolicitudDeGrupo.get(0)
					.getListasPrecios());
			firstTipoSolicitud.setListasPrecios(new ArrayList<ListaPreciosDto>());
			for (ListaPrecios listaPrecios : listasPrecios) {
				ListaPreciosDto lista = mapper.map(listaPrecios, ListaPreciosDto.class);
				lista.setItemsListaPrecioVisibles(mapper.convertList(listaPrecios
						.getItemsTasados(tiposSolicitudDeGrupo.get(0)), ItemSolicitudTasadoDto.class));
				firstTipoSolicitud.getListasPrecios().add(lista);
			}
		}
		initializer.setTiposPlanes(mapper.convertList(repository.getAll(TipoPlan.class), TipoPlanDto.class));
		initializer
				.setLocalidades(mapper.convertList(repository.getAll(Localidad.class), LocalidadDto.class));

		System.out.println(new Date());
		return initializer;
	}

	public List<ListaPreciosDto> getListasDePrecios(TipoSolicitudDto tipoSolicitudDto) {
		TipoSolicitud tipoSolicitud = repository.retrieve(TipoSolicitud.class, tipoSolicitudDto.getId());
		Set<ListaPrecios> listasPrecios = tipoSolicitud.getListasPrecios();
		List<ListaPreciosDto> listasPreciosDto = new ArrayList<ListaPreciosDto>();

		// boolean tipoSolicitudActivacion = isTipoSolicitudActivacion(tipoSolicitud);
		// Se realiaza el mapeo de la coleccion a mano para poder filtrar los items por warehouse
		for (ListaPrecios listaPrecios : listasPrecios) {
			ListaPreciosDto lista = mapper.map(listaPrecios, ListaPreciosDto.class);
			if (tipoSolicitud.getId().equals(
					knownInstanceRetriever.getObject(KnownInstanceIdentifier.TIPO_SOLICITUD_BASE_ACTIVACION)))
				// if(!tipoSolicitudActivacion){
				lista.setItemsListaPrecioVisibles(mapper.convertList(listaPrecios
						.getItemsTasados(tipoSolicitud), ItemSolicitudTasadoDto.class));
			// } else{
			// lista.setItemsListaPrecioVisibles(mapper.convertList(listaPrecios
			// .getItemsTasados(tipoSolicitud), ItemSolicitudTasadoDto.class));
			// }
			listasPreciosDto.add(lista);
		}

		return listasPreciosDto;
	}

	private boolean isTipoSolicitudActivacion(TipoSolicitud tipoSolicitud) {
		return tipoSolicitud.equals(knownInstanceRetriever
				.getObject(KnownInstanceIdentifier.TIPO_SOLICITUD_BASE_ACTIVACION))
				|| tipoSolicitud.equals(knownInstanceRetriever
						.getObject(KnownInstanceIdentifier.TIPO_SOLICITUD_BASE_ACTIVACION_G4));

	}

	public List<PlanDto> getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado,
			TipoPlanDto tipoPlan, Long idCuenta) {
		List planes = null;
		planes = solicitudServicioRepository.getPlanes(tipoPlan.getId(), itemSolicitudTasado.getItem()
				.getId(), idCuenta, sessionContextLoader.getVendedor());
		return mapper.convertList(planes, PlanDto.class, "planesParaListBox");
	}

	public LineaSolicitudServicioDto getServiciosAdicionales(LineaSolicitudServicioDto linea) {
		Collection<ServicioAdicionalLineaSolicitudServicio> serviciosAdicionales = solicitudServicioRepository
				.getServiciosAdicionales(linea.getTipoSolicitud().getId(), linea.getPlan().getId(), linea
						.getItem().getId(), new Long(45287));
		// XXX: Hardcode Cuenta (Chorch)
		linea.setServiciosAdicionales(mapper.convertList(serviciosAdicionales,
				ServicioAdicionalLineaSolicitudServicioDto.class));
		return linea;
	}

}

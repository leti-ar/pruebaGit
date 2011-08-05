package ar.com.nextel.sfa.server;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.constants.GlobalParameterIdentifier;
import ar.com.nextel.business.constants.KnownInstanceIdentifier;
import ar.com.nextel.business.constants.MessageIdentifier;
import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.vantive.VantiveSystem;
import ar.com.nextel.business.legacy.vantive.dto.EstadoSolicitudServicioCerradaDTO;
import ar.com.nextel.business.personas.reservaNumeroTelefono.result.ReservaNumeroTelefonoBusinessResult;
import ar.com.nextel.business.solicitudes.crearGuardar.request.CreateSaveSSTransfResponse;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreResponse;
import ar.com.nextel.business.solicitudes.negativeFiles.NegativeFilesBusinessOperator;
import ar.com.nextel.business.solicitudes.negativeFiles.result.NegativeFilesBusinessResult;
import ar.com.nextel.business.solicitudes.repository.SolicitudServicioRepository;
import ar.com.nextel.business.solicitudes.search.dto.SolicitudServicioCerradaSearchCriteria;
import ar.com.nextel.components.knownInstances.GlobalParameter;
import ar.com.nextel.components.knownInstances.retrievers.DefaultRetriever;
import ar.com.nextel.components.knownInstances.retrievers.model.KnownInstanceRetriever;
import ar.com.nextel.components.message.Message;
import ar.com.nextel.components.message.MessageList;
import ar.com.nextel.components.sequence.DefaultSequenceImpl;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.personas.beans.Localidad;
import ar.com.nextel.model.solicitudes.beans.EstadoSolicitud;
import ar.com.nextel.model.solicitudes.beans.GrupoSolicitud;
import ar.com.nextel.model.solicitudes.beans.LineaSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.ListaPrecios;
import ar.com.nextel.model.solicitudes.beans.OrigenSolicitud;
import ar.com.nextel.model.solicitudes.beans.Plan;
import ar.com.nextel.model.solicitudes.beans.PlanBase;
import ar.com.nextel.model.solicitudes.beans.ServicioAdicionalLineaSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.Sucursal;
import ar.com.nextel.model.solicitudes.beans.TipoAnticipo;
import ar.com.nextel.model.solicitudes.beans.TipoPlan;
import ar.com.nextel.model.solicitudes.beans.TipoSolicitud;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.CambiosSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSSTransfResultDto;
import ar.com.nextel.sfa.client.dto.DescuentoDto;
import ar.com.nextel.sfa.client.dto.DescuentoLineaDto;
import ar.com.nextel.sfa.client.dto.DescuentoTotalDto;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.EstadoTipoDomicilioDto;
import ar.com.nextel.sfa.client.dto.GeneracionCierreResultDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.LocalidadDto;
import ar.com.nextel.sfa.client.dto.MessageDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ResultadoReservaNumeroTelefonoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalIncluidoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.SucursalDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;
import ar.com.nextel.sfa.client.dto.TipoDescuentoDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.ContratoViewInitializer;
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
	private SolicitudServicioBusinessOperator solicitudesBusinessOperator;
	private VantiveSystem vantiveSystem;
	private FinancialSystem financialSystem;
	private KnownInstanceRetriever knownInstanceRetriever;
	private SessionContextLoader sessionContextLoader;
	private SolicitudServicioRepository solicitudServicioRepository;
	private NegativeFilesBusinessOperator negativeFilesBusinessOperator;
	private DefaultRetriever globalParameterRetriever;
	
	//MELI
	private DefaultSequenceImpl tripticoNextValue;
//	private AvalonSystem avalonSystem;
	
	//MGR - #1481
	private DefaultRetriever messageRetriever;;
	
	
	

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		solicitudesBusinessOperator = (SolicitudServicioBusinessOperator) context
				.getBean("solicitudServicioBusinessOperatorBean");
		solicitudBusinessService = (SolicitudBusinessService) context.getBean("solicitudBusinessService");
		repository = (Repository) context.getBean("repository");

		solicitudServicioRepository = (SolicitudServicioRepository) context
				.getBean("solicitudServicioRepositoryBean");
		vantiveSystem = (VantiveSystem) context.getBean("vantiveSystemBean");
		financialSystem = (FinancialSystem) context.getBean("financialSystemBean");
		knownInstanceRetriever = (KnownInstanceRetriever) context.getBean("knownInstancesRetriever");
		sessionContextLoader = (SessionContextLoader) context.getBean("sessionContextLoader");
		negativeFilesBusinessOperator = (NegativeFilesBusinessOperator) context
				.getBean("negativeFilesBusinessOperator");
		globalParameterRetriever = (DefaultRetriever) context.getBean("globalParameterRetriever");
		
		tripticoNextValue = (DefaultSequenceImpl)context.getBean("tripticoNextValue");
//		avalonSystem = (AvalonSystem) context.getBean("avalonSystemBean");
		messageRetriever = (DefaultRetriever)context.getBean("messageRetriever");
	}

	public SolicitudServicioDto createSolicitudServicio(
			SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages {
		SolicitudServicioRequest request = mapper.map(solicitudServicioRequestDto,
				SolicitudServicioRequest.class);
		AppLogger.info("Creando Solicitud de Servicio con Request -> " + request.toString());
		SolicitudServicio solicitud = null;
		try {
			solicitud = solicitudBusinessService.createSolicitudServicio(request, mapper);
		} catch (BusinessException e) {
			throw new RpcExceptionMessages((String) e.getParameters().get(0));
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		// Se pide la cuenta nuevamente para renovar el proxy de hibernate. Fix para cuentas que vienen de
		// Vantive
		Cuenta cuenta = repository.retrieve(Cuenta.class, solicitud.getCuenta().getId());
		solicitud.setCuenta(cuenta);
		SolicitudServicioDto solicitudServicioDto = mapper.map(solicitud, SolicitudServicioDto.class);
		
		//MR - le agrego el triptico
		if(solicitudServicioDto.getNumero() == null)
			solicitudServicioDto.setTripticoNumber(tripticoNextValue.nextNumber());
		
		//calculo los descuentos aplicados a cada línea y se los seteo 
		List<LineaSolicitudServicioDto> lineas = solicitudServicioDto.getLineas(); 
		for (Iterator<LineaSolicitudServicioDto> iterator = lineas.iterator(); iterator.hasNext();) {
			Double descuentoAplicado = 0.0;
			Double precioConDescuento = 0.0;
			LineaSolicitudServicioDto linea = (LineaSolicitudServicioDto) iterator.next();
			Long idLinea = linea.getId();
			List descuentosAplicados = solicitudServicioRepository.getDescuentosAplicados(idLinea);
			List<DescuentoLineaDto> descuentos = mapper.convertList(descuentosAplicados, DescuentoLineaDto.class);
			for (Iterator<DescuentoLineaDto> it = descuentos.iterator(); it.hasNext();) {
				DescuentoLineaDto descuentoLineaDto = (DescuentoLineaDto) it.next();
				descuentoAplicado += descuentoLineaDto.getMonto();
			}
			precioConDescuento = linea.getPrecioLista() - descuentoAplicado;
			linea.setPrecioConDescuento(precioConDescuento);
		}

		AppLogger.info("Creacion de Solicitud de Servicio finalizada");
		return solicitudServicioDto;
	}

	public List<SolicitudServicioCerradaResultDto> searchSSCerrada(
			SolicitudServicioCerradaDto solicitudServicioCerradaDto) throws RpcExceptionMessages {
		AppLogger.info("Iniciando busqueda de SS cerradas...");
		SolicitudServicioCerradaSearchCriteria solicitudServicioCerradaSearchCriteria = mapper.map(
				solicitudServicioCerradaDto, SolicitudServicioCerradaSearchCriteria.class);
		Vendedor vendedor = sessionContextLoader.getVendedor();

		solicitudServicioCerradaSearchCriteria.setVendedor(vendedor);
		List<SolicitudServicio> list = null;
		try {
			list = this.solicitudesBusinessOperator
					.searchSolicitudesServicioHistoricas(solicitudServicioCerradaSearchCriteria);
		} catch (Exception e) {
			AppLogger.info("Error buscando Solicitudes de Servicio cerradas: " + e.getMessage(), e);
			throw ExceptionUtil.wrap(e);
		}
		List result = mapper.convertList(list, SolicitudServicioCerradaResultDto.class, "ssCerradaResult");
		AppLogger.info("Busqueda de SS cerradas finalizada...");
		return result;
	}

	public DetalleSolicitudServicioDto getDetalleSolicitudServicio(Long idSolicitudServicio)
			throws RpcExceptionMessages {
		AppLogger.info("Iniciando consulta de estados de SS cerradas para idSS " + idSolicitudServicio);
		SolicitudServicio solicitudServicio = solicitudServicioRepository
				.getSolicitudServicioPorId(idSolicitudServicio);
		DetalleSolicitudServicioDto detalleSolicitudServicioDto = mapearSolicitud(solicitudServicio);

		try {
			detalleSolicitudServicioDto
					.setCambiosEstadoSolicitud(getEstadoSolicitudServicioCerrada(solicitudServicio
							.getIdVantive()));
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
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
						AppLogger.error(e);
					}
					return ret;
				}
			};

			Collections.sort(resultDTO, estadoComparator);
			return mapper.convertList(resultDTO, CambiosSolicitudServicioDto.class);
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
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

		buscarSSCerradasInitializer.setOpcionesEstado(mapper.convertList(repository
				.getAll(EstadoSolicitud.class), EstadoSolicitudDto.class));

		return buscarSSCerradasInitializer;
	}

	public SolicitudInitializer getSolicitudInitializer() {
		SolicitudInitializer initializer = new SolicitudInitializer();
		List<OrigenSolicitud> origenes = repository.getAll(OrigenSolicitud.class);
		Collections.sort(origenes, new Comparator<OrigenSolicitud>() {
			public int compare(OrigenSolicitud o1, OrigenSolicitud o2) {
				return o1.getIndice() - o2.getIndice();
			}
		});
		initializer.setOrigenesSolicitud(mapper.convertList(origenes, OrigenSolicitudDto.class));
		List tiposAnticipo = repository.getAll(TipoAnticipo.class);
		initializer.setTiposAnticipo(mapper.convertList(tiposAnticipo, TipoAnticipoDto.class));
		
		//Se cargan todos los vendedores que no sean del tipo Telemarketer, Dae o Administrador de Créditos
		Long idTipoVendTLM = knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_VENDEDOR_TELEMARKETING);
		Long idTipoVendDAE = knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_VENDEDOR_DAE);
		Long idTipoVendADM = knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_VENDEDOR_CREDITOS);
		List<Vendedor> vendedores = repository.find(
						"from Vendedor vend where vend.tipoVendedor.id <> ? and vend.tipoVendedor.id <> ? and vend.tipoVendedor.id <> ?",
						idTipoVendDAE, idTipoVendTLM, idTipoVendADM);
		Collections.sort(vendedores, new Comparator<Vendedor>() {
			public int compare(Vendedor vend1, Vendedor vend2) {
				if(vend1.getApellido() == null && vend2.getApellido() == null)
					return 0;
				if(vend1.getApellido() == null)
					return -1;
				if(vend2.getApellido() == null)
					return -1;
				return vend1.getApellidoYNombre().compareToIgnoreCase(vend2.getApellidoYNombre());
			}
		});
		initializer.setVendedores(mapper.convertList(vendedores, VendedorDto.class));
		
		List<Sucursal> sucursales = repository.getAll(Sucursal.class);
		initializer.setSucursales(mapper.convertList(sucursales, SucursalDto.class));
		return initializer;
	}

	public SolicitudServicioDto saveSolicituServicio(SolicitudServicioDto solicitudServicioDto)
			throws RpcExceptionMessages {
		try {
			SolicitudServicio solicitudSaved = solicitudBusinessService.saveSolicitudServicio(
					solicitudServicioDto, mapper);
			solicitudServicioDto = mapper.map(solicitudSaved, SolicitudServicioDto.class);
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return solicitudServicioDto;
	}

	public String buildExcel(SolicitudServicioCerradaDto solicitudServicioCerradaDto) {
		AppLogger.info("Iniciando busqueda de SS cerradas para crear excel...");
		SolicitudServicioCerradaSearchCriteria solicitudServicioCerradaSearchCriteria = mapper.map(
				solicitudServicioCerradaDto, SolicitudServicioCerradaSearchCriteria.class);
		Vendedor vendedor = sessionContextLoader.getVendedor();
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
			GrupoSolicitudDto grupoSolicitudDto, boolean isEmpresa) {

		LineasSolicitudServicioInitializer initializer = new LineasSolicitudServicioInitializer();
		List<GrupoSolicitud> grupos = 
				solicitudServicioRepository.getGruposSolicitudesServicio();
		
		Sucursal sucursal = sessionContextLoader.getVendedor().getSucursal();

		// Obtengo los tipos de solicitud de cada Grupo para la sucursal en particular
		Map<Long, List<TipoSolicitudDto>> tiposSolicitudPorGrupo = new HashMap();
		for (GrupoSolicitud gp : grupos) {
			tiposSolicitudPorGrupo.put(gp.getId(), 
					mapper.convertList(gp.calculateTiposSolicitud(sucursal, sessionContextLoader.getVendedor()),
							TipoSolicitudDto.class));
		}
		initializer.setTiposSolicitudPorGrupo(tiposSolicitudPorGrupo);

		List<TipoSolicitudDto> tiposSolicitudDeGrupoSelected = tiposSolicitudPorGrupo.get(grupoSolicitudDto
				.getId());
		// Si no es vaci� (no deber�a serlo) carga la lista de precios del primer tipoSolicitud que se muestra
		if (!tiposSolicitudDeGrupoSelected.isEmpty()) {
			TipoSolicitudDto firstTipoSolicitudDto = tiposSolicitudDeGrupoSelected.get(0);
			TipoSolicitud firstTipoSolicitud = repository.retrieve(TipoSolicitud.class, firstTipoSolicitudDto
					.getId());
			
			List<ListaPrecios> listasPrecios = new ArrayList<ListaPrecios>(firstTipoSolicitud
					.getListasPrecios());
			
			// #1757 -  solo tengo que mostrar la lista de precios asociados al segmento del cliente
			List<Long> ids = (List<Long>) CollectionUtils.collect(listasPrecios,  
		                new BeanToPropertyValueTransformer("id"));
			List<ListaPrecios> listasPreciosSegmentada = null;
			if (isEmpresa) {
				listasPreciosSegmentada = solicitudServicioRepository.getListaPreciosPorSegmento(ids, 2);
			} else {
				listasPreciosSegmentada = solicitudServicioRepository.getListaPreciosPorSegmento(ids, 1);
			}
			listasPrecios.retainAll(listasPreciosSegmentada);
			
			firstTipoSolicitudDto.setListasPrecios(new ArrayList<ListaPreciosDto>());
			for (ListaPrecios listaPrecios : listasPrecios) {
				ListaPreciosDto lista = mapper.map(listaPrecios, ListaPreciosDto.class);
				//MGR - #873 - Se agrega el Vendedor
				lista.setItemsListaPrecioVisibles(mapper.convertList(listaPrecios
						.getItemsTasados(firstTipoSolicitud, sessionContextLoader.getVendedor()),
								ItemSolicitudTasadoDto.class));
				firstTipoSolicitudDto.getListasPrecios().add(lista);
			}
		}
		initializer.setTiposPlanes(mapper.convertList(repository.getAll(TipoPlan.class), TipoPlanDto.class));
		initializer
				.setLocalidades(mapper.convertList(repository.getAll(Localidad.class), LocalidadDto.class));

		//System.out.println(new Date());
		return initializer;
	}

	public List<ListaPreciosDto> getListasDePrecios(TipoSolicitudDto tipoSolicitudDto, boolean isEmpresa) {
		TipoSolicitud tipoSolicitud = repository.retrieve(TipoSolicitud.class, tipoSolicitudDto.getId());
		Set<ListaPrecios> listasPrecios = tipoSolicitud.getListasPrecios();
		List<ListaPreciosDto> listasPreciosDto = new ArrayList<ListaPreciosDto>();
		boolean activacion = isTipoSolicitudActivacion(tipoSolicitud);
		boolean accesorios = isTipoSolicitudAccesorios(tipoSolicitud);

		// Se realiza el mapeo de la colecci�n a mano para poder filtrar los items por warehouse
		for (ListaPrecios listaPrecios : listasPrecios) {
			ListaPreciosDto lista = mapper.map(listaPrecios, ListaPreciosDto.class);
			if (!activacion) {
				//MGR - #873 - Se agrega el Vendedor
				lista.setItemsListaPrecioVisibles(mapper.convertList(listaPrecios
						.getItemsTasados(tipoSolicitud, sessionContextLoader.getVendedor()), ItemSolicitudTasadoDto.class));
			}
			if(accesorios){
				Collections.sort(lista.getItemsListaPrecioVisibles(), new Comparator<ItemSolicitudTasadoDto>() {
					public int compare(ItemSolicitudTasadoDto o1, ItemSolicitudTasadoDto o2) {
						return o1.getItemText().compareTo(o2.getItemText());
					}
				});
			}
			listasPreciosDto.add(lista);
		}
		
		// #1757 -  solo tengo que mostrar la lista de precios asociados al segmento del cliente
		List<Long> ids = (List<Long>) CollectionUtils.collect(listasPreciosDto,  
				new BeanToPropertyValueTransformer("id"));
		List<ListaPrecios> listasPreciosSegmentada = null;
		if (isEmpresa) {
			listasPreciosSegmentada = solicitudServicioRepository.getListaPreciosPorSegmento(ids, 2);
		} else {
			listasPreciosSegmentada = solicitudServicioRepository.getListaPreciosPorSegmento(ids, 1);
		}
		List<ListaPreciosDto> listasPreciosSegmentadaDto = mapper.convertList(listasPreciosSegmentada, ListaPreciosDto.class);
		listasPreciosDto.retainAll(listasPreciosSegmentadaDto);
		
		return listasPreciosDto;
	}

	private boolean isTipoSolicitudActivacion(TipoSolicitud tipoSolicitud) {
		return tipoSolicitud.getTipoSolicitudBase().equals(knownInstanceRetriever
				.getObject(KnownInstanceIdentifier.TIPO_SOLICITUD_BASE_ACTIVACION))
				|| tipoSolicitud.getTipoSolicitudBase().equals(knownInstanceRetriever
						.getObject(KnownInstanceIdentifier.TIPO_SOLICITUD_BASE_ACTIVACION_G4));

	}
	
	private boolean isTipoSolicitudAccesorios(TipoSolicitud tipoSolicitud) {
		return tipoSolicitud.getTipoSolicitudBase().equals(knownInstanceRetriever
				.getObject(KnownInstanceIdentifier.TIPO_SOLICITUD_BASE_VENTA_ACCESORIOS))
				|| tipoSolicitud.getTipoSolicitudBase().equals(knownInstanceRetriever
						.getObject(KnownInstanceIdentifier.TIPO_SOLICITUD_BASE_VENTA_ACCESORIOS_G4));

	}

	public List<PlanDto> getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado,
			TipoPlanDto tipoPlan, Long idCuenta) {
		List planes = null;
		planes = solicitudServicioRepository.getPlanes(tipoPlan.getId(), itemSolicitudTasado.getItem()
				.getId(), idCuenta, sessionContextLoader.getVendedor());
		return mapper.convertList(planes, PlanDto.class);
	}

	public List<ServicioAdicionalLineaSolicitudServicioDto> getServiciosAdicionales(
			LineaSolicitudServicioDto linea, Long idCuenta, boolean isEmpresa) {
		//MGR - #873 - Se agrega el Vendedor
		Collection<ServicioAdicionalLineaSolicitudServicio> serviciosAdicionales = solicitudServicioRepository
				.getServiciosAdicionales(linea.getTipoSolicitud().getId(), linea.getPlan().getId(), linea
						.getItem().getId(), idCuenta, sessionContextLoader.getVendedor(), isEmpresa);
		return mapper.convertList(serviciosAdicionales, ServicioAdicionalLineaSolicitudServicioDto.class);
	}

	public ResultadoReservaNumeroTelefonoDto reservarNumeroTelefonico(long numero, long idTipoTelefonia,
			long idModalidadCobro, long idLocalidad) throws RpcExceptionMessages {
		ReservaNumeroTelefonoBusinessResult result = null;
		try {
			result = solicitudBusinessService.reservarNumeroTelefonico(numero, idTipoTelefonia,
					idModalidadCobro, idLocalidad);
		} catch (BusinessException e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return mapper.map(result, ResultadoReservaNumeroTelefonoDto.class);
	}

	public void desreservarNumeroTelefono(long numero, Long idLineaSolicitudServicio)
			throws RpcExceptionMessages {
		try {
			solicitudBusinessService.desreservarNumeroTelefono(numero, idLineaSolicitudServicio);
		} catch (BusinessException e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
	}

	public List<ModeloDto> getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios)
			throws RpcExceptionMessages {
		List<ModeloDto> modelos = mapper.convertList(solicitudServicioRepository.getModelos(imei),
				ModeloDto.class);
		for (ModeloDto modelo : modelos) {
			//MGR - #873 - Se agrega el Vendedor
			modelo.setItems(mapper.convertList(solicitudServicioRepository.getItems(idTipoSolicitud,
					idListaPrecios, modelo.getId(), sessionContextLoader.getVendedor() ), ItemSolicitudTasadoDto.class));
		}
		return modelos;
	}

	/** Retorna null si la SIM es correcta. De lo contrario retorna el mensaje de error */
	public String verificarNegativeFiles(String numero) throws RpcExceptionMessages {
		NegativeFilesBusinessResult negativeFilesResult = null;
		try {
			negativeFilesResult = negativeFilesBusinessOperator.verificarNegativeFiles(numero);
		} catch (BusinessException e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		if (negativeFilesResult != null) {
			return negativeFilesResult.getAvalonResultDto().getMessage();
		}
		return null;
	}

	public GeneracionCierreResultDto generarCerrarSolicitud(SolicitudServicioDto solicitudServicioDto,
			String pinMaestro, boolean cerrar) throws RpcExceptionMessages {
		String accion = cerrar ? "cierre" : "generación";
		AppLogger.info("Iniciando " + accion + " de SS de id=" + solicitudServicioDto.getId() + " ...");
		GeneracionCierreResultDto result = new GeneracionCierreResultDto();
		SolicitudServicio solicitudServicio = null;
		GeneracionCierreResponse response = null;
		try {
			completarDomiciliosSolicitudTransferencia(solicitudServicioDto);
			solicitudServicio = solicitudBusinessService.saveSolicitudServicio(solicitudServicioDto, mapper);
			response = solicitudBusinessService.generarCerrarSolicitud(solicitudServicio, pinMaestro, cerrar);
			// metodo changelog
			result.setError(response.getMessages().hasErrors());
			if (cerrar == true
					&& response.getMessages().hasErrors() == false
					&& sessionContextLoader.getVendedor().getTipoVendedor().getCodigoVantive().equals(
							KnownInstanceIdentifier.TIPO_VENDEDOR_EECC.getKey())) {
				solicitudBusinessService.generarChangeLog(solicitudServicioDto.getId(), solicitudServicio
						.getVendedor().getId());
			}
			result.setMessages(mapper.convertList(response.getMessages().getMessages(), MessageDto.class));
			result.setRtfFileName(getReporteFileName(solicitudServicio));
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		AppLogger.info(accion + " de SS de id=" + solicitudServicioDto.getId() + " finalizado.");
		return result;
	}
	
	private void completarDomiciliosSolicitudTransferencia(SolicitudServicioDto solicitudServicioDto) {
		if (solicitudServicioDto.getGrupoSolicitud().isTransferencia())
			for (DomiciliosCuentaDto dom : solicitudServicioDto.getCuenta().getPersona().getDomicilios()) {

				if (dom.getIdEntrega().equals(EstadoTipoDomicilioDto.PRINCIPAL.getId())) {
					solicitudServicioDto.setIdDomicilioEnvio(dom.getId());
				}
				if (dom.getIdFacturacion().equals(EstadoTipoDomicilioDto.PRINCIPAL.getId())) {
					solicitudServicioDto.setIdDomicilioFacturacion(dom.getId());

				}
			}
	}
	
	

	private String getReporteFileName(SolicitudServicio solicitudServicio) {
		String filename;
		if (solicitudServicio.getCuenta().isCliente()) {
			filename = solicitudServicio.getCuenta().getId().toString() + "-5-"
					+ solicitudServicio.getNumero() + ".rtf";
		} else {
			filename = solicitudServicio.getCuenta().getId().toString() + "-5-"
					+ solicitudServicio.getNumero() + ".rtf";
		}
		return filename;
	}

	public Boolean existReport(String report) {
		String fullFilename = buildSolicitudReportPath() + File.separatorChar + report;
		AppLogger.info("Searching file " + fullFilename);
		return new File(fullFilename).exists();
	}

	private String buildSolicitudReportPath() {
		GlobalParameter pathGlobalParameter = (GlobalParameter) globalParameterRetriever
				.getObject(GlobalParameterIdentifier.SAMBA_PATH_RTF);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return pathGlobalParameter.getValue() + String.valueOf(File.separatorChar)
				+ dateFormat.format(calendar.getTime()) + months[calendar.get(Calendar.MONTH)];
	}

	private static String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
			"NOV", "DEC" };
	
	
    public List<VendedorDto> getVendedoresDae() {
        AppLogger.info("Iniciando busqueda de vendedores Dae activos...");
        List<VendedorDto> vendedoresDae = mapper.convertList(solicitudServicioRepository.getVendedoresDae(), VendedorDto.class);
        AppLogger.info("Retrieve VENDEDORES DAE finalizado...");
        return vendedoresDae;
    }

	public List<DescuentoDto> getDescuentos(Long idLinea) throws RpcExceptionMessages {
		List descuentos = solicitudServicioRepository.getDescuentos(idLinea);
    	return mapper.convertList(descuentos, DescuentoDto.class);
	}

	public List<DescuentoLineaDto> getDescuentosAplicados(Long idLinea) throws RpcExceptionMessages {
		List descuentosAplicados = solicitudServicioRepository.getDescuentosAplicados(idLinea);
		return mapper.convertList(descuentosAplicados, DescuentoLineaDto.class);
	}

	public List<TipoDescuentoDto> getTiposDescuento(Long idLinea)
			throws RpcExceptionMessages {
		List tiposDescuento = solicitudServicioRepository.getTiposDescuento(idLinea);
		return mapper.convertList(tiposDescuento, TipoDescuentoDto.class);
	}

	public List<TipoDescuentoDto> getTiposDescuentoAplicados(Long idLinea)
			throws RpcExceptionMessages {
		List tiposDescuentoAplicados = solicitudServicioRepository.getTiposDescuentoAplicados(idLinea);
		return mapper.convertList(tiposDescuentoAplicados, TipoDescuentoDto.class);
	}

	public boolean puedeAplicarDescuento(List<LineaSolicitudServicioDto> lineas)
		throws RpcExceptionMessages {
		List<LineaSolicitudServicio> convertList = mapper.convertList(lineas, LineaSolicitudServicio.class);
		return solicitudServicioRepository.puedeAplicarDescuento(convertList);
	}
	
	public List<TipoDescuentoDto> getInterseccionTiposDescuento(List<LineaSolicitudServicioDto> lineas) 
			throws RpcExceptionMessages {
		List tiposDescuento = solicitudServicioRepository.getInterseccionTiposDescuento(mapper.convertList(lineas, LineaSolicitudServicio.class));
		return mapper.convertList(tiposDescuento, TipoDescuentoDto.class);
	}

	public DescuentoTotalDto getDescuentosTotales(Long idLinea)
			throws RpcExceptionMessages {
		DescuentoTotalDto descuentoTotal = new DescuentoTotalDto();
		List descuentos = solicitudServicioRepository.getDescuentos(idLinea);
		descuentoTotal.setDescuentos(mapper.convertList(descuentos, DescuentoDto.class));
		List tiposDescuento = solicitudServicioRepository.getTiposDescuento(idLinea);
		descuentoTotal.setTiposDescuento(mapper.convertList(tiposDescuento, TipoDescuentoDto.class));
		descuentoTotal.setIdLinea(idLinea);
		return descuentoTotal;
	}

	//MGR - #1415
	public Boolean crearArchivo(Long idSolicitud, boolean enviarEmail)
			throws RpcExceptionMessages {
		SolicitudServicio solicitudServicio = solicitudServicioRepository
			.getSolicitudServicioPorId(idSolicitud);
		GeneracionCierreResponse response = null;
		try {
			response = solicitudBusinessService.crearArchivo(solicitudServicio, enviarEmail);
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return true;
	}

	public List<PlanDto> getPlanesPorTipoPlan(Long idTipoPlan, Long idCuenta) throws RpcExceptionMessages {
		List planes = null;
		planes = solicitudServicioRepository.getPlanesPorTipoPlan(idTipoPlan, idCuenta, sessionContextLoader.getVendedor());
		return mapper.convertList(planes, PlanDto.class);
	}

	public List<ServicioAdicionalIncluidoDto> getServiciosAdicionalesContrato(
			Long idPlan) throws RpcExceptionMessages {
		List serviciosAdicionales = solicitudServicioRepository.getServiciosAdicionalesContrato(idPlan, sessionContextLoader.getVendedor());
		return mapper.convertList(serviciosAdicionales, ServicioAdicionalIncluidoDto.class);
	}
	
	public CreateSaveSSTransfResultDto saveSolicituServicioTranferencia(
			SolicitudServicioDto solicitudServicioDto) throws RpcExceptionMessages {
		CreateSaveSSTransfResultDto resultDto = new CreateSaveSSTransfResultDto();
		try {
	

			SolicitudServicioDto solicitudDto = this.saveSolicituServicio(solicitudServicioDto);
			resultDto.setSolicitud(solicitudDto);

			Vendedor vendedor = sessionContextLoader.getSessionContext().getVendedor();
			//valida triptico al guardar
			if (vendedor.isADMCreditos()) {
				SolicitudServicio solicitud = repository.retrieve(SolicitudServicio.class,
						solicitudDto.getId());
				// mapper.map(solicitudDto, solicitud);
				CreateSaveSSTransfResponse ssResponse = solicitudBusinessService.validarTriptico(solicitud);
				resultDto.setMessages(mapper.convertList(ssResponse.getMessages().getMessages(),
						MessageDto.class));
				if (!resultDto.getMessages().isEmpty()) {
					resultDto.setError(true);
				}
			}
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return resultDto;
	}
	
	public CreateSaveSSTransfResultDto createSolicitudServicioTranferencia(
			SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages {
		CreateSaveSSTransfResultDto resultDto = new CreateSaveSSTransfResultDto();
		
		SolicitudServicioDto solicitudDto = this.createSolicitudServicio(solicitudServicioRequestDto);
		resultDto.setSolicitud(solicitudDto);
		SolicitudServicio solicitud = repository.retrieve(SolicitudServicio.class,
				solicitudDto.getId());
		//mapper.map(solicitudDto, solicitud);
		MessageList messages = solicitudBusinessService.validarCreateSSTransf(solicitud).getMessages();
		resultDto.setError(messages.hasErrors());
		resultDto.setMessages(mapper.convertList(messages.getMessages(), MessageDto.class));
		
		Vendedor vendLogeo = sessionContextLoader.getVendedor();
		if(vendLogeo.isADMCreditos() || vendLogeo.isAP()){
			
			if(solicitud.getCuenta().getVendedorLockeo() != null &&
				!vendLogeo.getId().equals(solicitud.getCuenta().getVendedorLockeo().getId())){
				String nombSuper = "";
				if(solicitud.getCuenta().getVendedorLockeo().getSupervisor() != null){
					nombSuper = solicitud.getCuenta().getVendedorLockeo().getSupervisor().getNombreYApellido();
				}
				resultDto.addMessage("La cuenta está lockeada por un ejecutivo, el supervisor es " + 
						nombSuper + ". Puede proseguir la carga de la SS");
			}
		}
		return resultDto;
	}
	
	public ContratoViewInitializer getContratoViewInitializer(
			GrupoSolicitudDto grupoSolicitudDto) {

		ContratoViewInitializer initializer = new ContratoViewInitializer();
		initializer.setTiposPlanes(mapper.convertList(repository.getAll(TipoPlan.class), TipoPlanDto.class));

		return initializer;
	}
	
	//MGR - #1481 - Esto validaba que los planes existieran en SFA, ahora solo que pertenescan al 
	//segmento de cliente
	public List<String> validarPlanesCedentes(List<ContratoViewDto> ctoCedentes, boolean isEmpresa, boolean isSaving) {
		//Validacion 15 del caso de uso.
		/*Se comprueba que los planes cedentes, de existir en SFA, sean del mismo segmento
		que el cliente cesionario.
		(Que un cliente tipo 'Empresa' tenga planes cedentes para empresas y que un cliente
		tipo 'Personal' tenga planes cedentes de su tipo)
		*/
		
		List<String> errores = new ArrayList<String>(); //Guardo los errores que pienso devolver
		boolean error = false;
		final Vendedor vendedor = sessionContextLoader.getVendedor();
		
		for (int i = 0; !error && i < ctoCedentes.size(); i++) {
			ContratoViewDto cto = ctoCedentes.get(i);

			// Si no cambio el plan, valido que el plan cedente sea de su segmento
			if (cto.getPlanCesionario() == null) {
				List<PlanBase> planes = repository.find("from PlanBase p where p.codigoBSCS = ?", 
									String.valueOf(cto.getCodigoBSCSPlanCedente()));

				// Si no hay planes, no debo validar nada
				if (!planes.isEmpty()) {
					for (int j=0; !error && j < planes.size(); j++) {
						PlanBase planBase = planes.get(j);
						if ((planBase.getTipoPlan().isEmpresa() && !isEmpresa)
								|| (planBase.getTipoPlan().isDirecto() && isEmpresa)) {
							Message message;
							if (isEmpresa) {
								message = (Message)this.messageRetriever.getObject(MessageIdentifier.PLAN_DIRECTO_SEG_EMPRESA); 
							} else {
								message = (Message)this.messageRetriever.getObject(MessageIdentifier.PLAN_EMPRESA_SEG_DIRECTO);
							}
							errores.add(message.getDescription());
							error = true;
						}
						//#1748						
						if (!error) {
							if (vendedor.isDealer() || (vendedor.isAP() && !isSaving) 
									|| (vendedor.isADMCreditos() && !isSaving)) {
								if (planBase.getTipoPlan().getId().equals(9L)) {
									if (vendedor.isDealer()) {
										errores.add("Debe seleccionar Planes Vigentes");
									} else {
										errores.add("Está seleccionando Planes No Vigentes");
									}
									error = true;
								}
							}
						}
					}
				}
			}
		}
		return errores;
	}

	public void loginServer(String linea) {
		AppLogger.info(linea);
	}

}
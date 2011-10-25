package ar.com.nextel.sfa.server;

import java.io.File;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
import ar.com.nextel.business.cuentas.tarjetacredito.TarjetaCreditoValidatorResult;
import ar.com.nextel.business.cuentas.tarjetacredito.TarjetaCreditoValidatorServiceAxisImpl;
import ar.com.nextel.business.cuentas.tarjetacredito.TarjetaCreditoValidatorServiceException;
import ar.com.nextel.business.legacy.bps.BPSSystem;
import ar.com.nextel.business.legacy.bps.exception.BPSSystemException;
import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.vantive.VantiveSystem;
import ar.com.nextel.business.legacy.vantive.dto.EstadoSolicitudServicioCerradaDTO;
import ar.com.nextel.business.personas.reservaNumeroTelefono.result.ReservaNumeroTelefonoBusinessResult;
import ar.com.nextel.business.solicitudes.crearGuardar.request.CreateSaveSSResponse;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreResponse;
import ar.com.nextel.business.solicitudes.negativeFiles.NegativeFilesBusinessOperator;
import ar.com.nextel.business.solicitudes.negativeFiles.result.NegativeFilesBusinessResult;
import ar.com.nextel.business.solicitudes.report.SolicitudPortabilidadPropertiesReport;
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
import ar.com.nextel.model.solicitudes.beans.PlanBase;
import ar.com.nextel.model.solicitudes.beans.ServicioAdicionalLineaSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.Sucursal;
import ar.com.nextel.model.solicitudes.beans.TipoAnticipo;
import ar.com.nextel.model.solicitudes.beans.TipoPlan;
import ar.com.nextel.model.solicitudes.beans.TipoSolicitud;
import ar.com.nextel.port.in.endPoint.PortInEndPointImplServiceSoapBindingStub;
import ar.com.nextel.port.in.endPoint.Responsible;
import ar.com.nextel.port.in.endPoint.ServiceOrder;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.services.portabilidad.SolicitudPortabilidadWSImpl;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.CambiosSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSSTransfResultDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSolicitudServicioResultDto;
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
import ar.com.nextel.sfa.client.dto.ModalidadCobroDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.ResultadoReservaNumeroTelefonoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalIncluidoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.SucursalDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;
import ar.com.nextel.sfa.client.dto.TipoDescuentoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.TipoTelefoniaDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.ContratoViewInitializer;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.initializer.PortabilidadInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.nextel.sfa.client.util.PortabilidadResult;
import ar.com.nextel.sfa.server.businessservice.CuentaBusinessService;
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
	private BPSSystem bpsSystem;
	private FinancialSystem financialSystem;
	private KnownInstanceRetriever knownInstanceRetriever;
	private SessionContextLoader sessionContextLoader;
	private SolicitudServicioRepository solicitudServicioRepository;
	private NegativeFilesBusinessOperator negativeFilesBusinessOperator;
	private DefaultRetriever globalParameterRetriever;
	private CuentaBusinessService cuentaBusinessService;

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
		bpsSystem = (BPSSystem) context.getBean("bpsSystemBean");
		financialSystem = (FinancialSystem) context.getBean("financialSystemBean");
		knownInstanceRetriever = (KnownInstanceRetriever) context.getBean("knownInstancesRetriever");
		sessionContextLoader = (SessionContextLoader) context.getBean("sessionContextLoader");
		negativeFilesBusinessOperator = (NegativeFilesBusinessOperator) context
				.getBean("negativeFilesBusinessOperator");
		globalParameterRetriever = (DefaultRetriever) context.getBean("globalParameterRetriever");
		
		tripticoNextValue = (DefaultSequenceImpl)context.getBean("tripticoNextValue");
//		avalonSystem = (AvalonSystem) context.getBean("avalonSystemBean");
		messageRetriever = (DefaultRetriever)context.getBean("messageRetriever");
		cuentaBusinessService = (CuentaBusinessService) context.getBean("cuentaBusinessService");
		
	}

	//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un CreateSaveSolicitudServicioResultDto 
	//que permite realizar el manejo de mensajes
	public CreateSaveSolicitudServicioResultDto createSolicitudServicio(
			SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages {
		//MGR - ISDN 1824
		CreateSaveSolicitudServicioResultDto resultDto = new CreateSaveSolicitudServicioResultDto();

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

		//MGR - ISDN 1824 - Predicados al crear una ss
		MessageList messages = solicitudBusinessService.validarCreateSS(solicitud).getMessages();
		resultDto.setError(messages.hasErrors());
		resultDto.setMessages(mapper.convertList(messages.getMessages(), MessageDto.class));
		
		AppLogger.info("Creacion de Solicitud de Servicio finalizada");
		resultDto.setSolicitud(solicitudServicioDto);
		return resultDto;
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
		
		//Se cargan todos los vendedores que no sean del tipo Telemarketer, Dae o Administrador de Créditos,
		Long idTipoVendTLM = knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_VENDEDOR_TELEMARKETING);
		Long idTipoVendDAE = knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_VENDEDOR_DAE);
		Long idTipoVendADM = knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_VENDEDOR_CREDITOS);
		//MGR - ISDN 1824 - Si el vendedor logeado es Adm. de creditos, los TLM si se cargan al combo
		String query = "from Vendedor vend where vend.tipoVendedor.id not in(" + idTipoVendDAE.toString() + 
							", " + idTipoVendADM.toString();
		if(!SessionContextLoader.getInstance().getVendedor().isADMCreditos()){
			query += ", " + idTipoVendTLM.toString();
		}
		query += ")";
		List<Vendedor> vendedores = repository.find(query);
		
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

	//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un SaveSolicitudServicioResultDto 
	//que permite realizar el manejo de mensajes
	public CreateSaveSolicitudServicioResultDto saveSolicituServicio(SolicitudServicioDto solicitudServicioDto)
			throws RpcExceptionMessages {

		CreateSaveSolicitudServicioResultDto resultDto = new CreateSaveSolicitudServicioResultDto();
		try {
			SolicitudServicio solicitudSaved = solicitudBusinessService.saveSolicitudServicio(solicitudServicioDto, mapper);
			solicitudServicioDto = mapper.map(solicitudSaved, SolicitudServicioDto.class);
			
			Vendedor vendedor = sessionContextLoader.getSessionContext().getVendedor();
			if (vendedor.isADMCreditos()) {
				//Valida los predicados y el triptico
				CreateSaveSSResponse response = solicitudBusinessService.validarPredicadosGuardarSS(solicitudSaved);
				resultDto.setError(response.getMessages().hasErrors());
				resultDto.setMessages(mapper.convertList(response.getMessages().getMessages(), MessageDto.class));
			}
			resultDto.setSolicitud(solicitudServicioDto);
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return resultDto;
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
			
			//TODO: Portabilidad - Setea los nombres de los rtf Generados
			if(response.getRtfFileNamePortabilidad().size() > 0){
				for (String rtfFileNamePorta : response.getRtfFileNamePortabilidad().get("PORTABILIDAD")) {
					result.getRtfFileNamePortabilidad().add(rtfFileNamePorta);
				}
				for (String rtfFileNamePorta_adj : response.getRtfFileNamePortabilidad().get("PORTABILIDAD_ADJUNTO")) {
					result.getRtfFileNamePortabilidad_adj().add(rtfFileNamePorta_adj);
				}
			}
			
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
	
			//MGR - ISDN 1824 - Cambio la forma en la que se valida el triptico, para que se ejecuten 
			//el resto de las validaciones
			SolicitudServicio solicitudSaved = solicitudBusinessService.saveSolicitudServicio(
					solicitudServicioDto, mapper);
			SolicitudServicioDto solicitudDto = mapper.map(solicitudSaved, SolicitudServicioDto.class);
			resultDto.setSolicitud(solicitudDto);

			Vendedor vendedor = sessionContextLoader.getSessionContext().getVendedor();
			if (vendedor.isADMCreditos()) {
				//Valida los predicados y el triptico
				CreateSaveSSResponse response = solicitudBusinessService.validarPredicadosGuardarSS(solicitudSaved);
				resultDto.setError(response.getMessages().hasErrors());
				resultDto.setMessages(mapper.convertList(response.getMessages().getMessages(), MessageDto.class));
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
		
		//MGR - ISDN 1824
		CreateSaveSolicitudServicioResultDto resultSSAux = this.createSolicitudServicio(solicitudServicioRequestDto);
		SolicitudServicioDto solicitudDto = resultSSAux.getSolicitud();
		resultDto.addMessages(resultSSAux.getMessages());
		
		resultDto.setSolicitud(solicitudDto);
		SolicitudServicio solicitud = repository.retrieve(SolicitudServicio.class,
				solicitudDto.getId());
		//mapper.map(solicitudDto, solicitud);
		MessageList messages = solicitudBusinessService.validarCreateSSTransf(solicitud).getMessages();
		resultDto.setError(messages.hasErrors());
		resultDto.addMessages(mapper.convertList(messages.getMessages(), MessageDto.class));
		
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

	/**
	 * TODO: Portabilidad
	 * @return
	 */
	public PortabilidadInitializer getPortabilidadInitializer(long cuentaID) throws RpcExceptionMessages {
		PortabilidadInitializer initializer = new PortabilidadInitializer();
		
		initializer.setLstTipoDocumento(mapper.convertList(
						repository.find("FROM TipoDocumento tdoc WHERE tdoc.portabilidad = TRUE"), TipoDocumentoDto.class));
		initializer.setLstProveedorAnterior(mapper.convertList(
						repository.find("FROM Proveedor pro WHERE pro.portabilidad = TRUE"), ProveedorDto.class));
		initializer.setLstTipoTelefonia(mapper.convertList(
						repository.find("FROM TipoTelefonia ttel WHERE ttel.codigoBSCS IN('1','2')"), TipoTelefoniaDto.class));
		initializer.setLstModalidadCobro(mapper.convertList(
						repository.find("FROM ModalidadCobro mcob WHERE mcob.codigoBSCS IN('CPP','MPP')"), ModalidadCobroDto.class));

		// Carga la cuenta para poder extraer los datos de la persona
		Cuenta persona_aux = (Cuenta) repository.retrieve(Cuenta.class, cuentaID);
		initializer.setPersona(mapper.map(persona_aux.getPersona(), PersonaDto.class));

		return initializer;
	}

	/**
	 * TODO: Portabilidad
	 */
	public SolicitudPortabilidadDto getSolicitudPortabilidadDto(String lineaID) throws RpcExceptionMessages {
		String query = "FROM SolicitudPortabilidad port WHERE port.lineaSolicitud.id IN(" + lineaID + ")"; 
		return mapper.map(repository.find(query).get(0), SolicitudPortabilidadDto.class);
	}


	/**
	 * TODO: Portabilidad
	 */
	public boolean getExisteEnAreaCobertura(int codArea) throws RpcExceptionMessages {
//		SolicitudPortabilidadWSImpl ws = new SolicitudPortabilidadWSImpl();
		
		String query = "FROM Localidad local WHERE local.codigoArea = ? AND local.cobertura = ?";
		return repository.find(query, String.valueOf(codArea),true).size() > 0 ? true : false;
	}

	/**
	 * 
	 * @param nroSS_portabilidad
	 * @param nroSS
	 * @return
	 */
	private boolean verificarNroSS(String nroSS_portabilidad,String nroSS){
		String nroSS_portabilidad_aux;
		int nroSS_portabilidad_aux_int;
		final int MAX_PORTABILIDADES = 20;
		
		// Valida que el numero SS sea valido
		if(nroSS_portabilidad.contains("N")){ // Contiene la letra N
			if(nroSS_portabilidad.contains(".")){ // Contiene un punto
				nroSS_portabilidad_aux = nroSS_portabilidad.substring(nroSS_portabilidad.indexOf("N") + 1, nroSS_portabilidad.lastIndexOf("."));
				if(nroSS_portabilidad_aux.equals(nroSS)){ // Numero igual al de solicitud de servicio
					nroSS_portabilidad_aux = nroSS_portabilidad.substring(nroSS_portabilidad.lastIndexOf(".") + 1);
					try{
						nroSS_portabilidad_aux_int = Integer.parseInt(nroSS_portabilidad_aux);
						if(nroSS_portabilidad_aux_int > MAX_PORTABILIDADES) return false; // El conteo de solicitudes supera el maximo
					}catch(NumberFormatException e){
						return false; // El conteo de solicitudes no es un numero
					}
				}else return false; // El numero sin la N ni el punto es diferente al de la solicitud de servicio
			}else{ // No contiene un punto
				nroSS_portabilidad_aux = nroSS_portabilidad.substring(nroSS_portabilidad.indexOf("N") + 1);
				if(!nroSS_portabilidad_aux.equals(nroSS)) return false; // El numero sin la N es diferente al de la solicitud de servicio
			}
		}else return false; // No contiene la letra N

		return true;
	}
	
	/**
	 * TODO: Portabilidad
	 */
	public PortabilidadResult validarPortabilidad(SolicitudServicioDto solicitudServicioDto) throws RpcExceptionMessages {
		// Mensajes
		final String MSG_ERR_01 = "El numero a portar _NUMERO_ se encuentra cargado en mas de una Linea de la Solicitud";
		final String MSG_ERR_02 = "Solamente una Linea de Solicitud de Portabilidad puede recibir SMS para el Nro. de SS: _NUMERO_";
		final String MSG_ERR_02_BIS = "Debe seleccionar al menos una Linea de Solicitud de Servicio para recibir SMS para el Nro. de SS: _NUMERO_";
		final String MSG_ERR_03 = "Todas la Lineas de Solicitud con Portabilidad que correspondan a la telefonia prepaga deben recibir SMS";
		final String MSG_ERR_04 = "Excede la maxima cantidad de equipos Prepagos con Portabilidad por Solicitud de Servicio";
		final String MSG_ERR_05 = "Existen Lineas con tipo de Telefonia Prepago que contienen el mismo numero de Solicitud de Portabilidad";
		final String MSG_ERR_06 = "Existen Lineas con diferentes Operadores que contienen el mismo numero de Solicitud de Portabilidad";
		final String MSG_ERR_07 = "Existen Lineas con igual numero de Solicitud de Portabilidad para distintas Razon Social, " +
				"Nombre y Apellido, tipo y numero Documento";
		final String MSG_ERR_08 = "Existen Lineas que tienen un numero de Solicitud de Portabilidad incorrecto";
		final String MSG_ERR_09 = "La Cuenta posee Solicitudes de Portabilidad pendientes";
		
		int contLineasPrepagas = 0;
		int cantRecibeSMS;
		
		Boolean permiteBPS;
		Boolean permiteVantive;
		
		String nroSS = "";
		String telefono;
		String telefonoAUX;
		String apoderado;
		String apoderadoAUX;
		
		SolicitudPortabilidadDto portabilidad;
		SolicitudPortabilidadDto portabilidadAUX;
		
		List<LineaSolicitudServicioDto> lineas = solicitudServicioDto.getLineas();
		List<List<Integer>> x_nroSS = new ArrayList<List<Integer>>();
		boolean encontro;
		String nroSS_1;
		String nroSS_2;
		
		for(int i = 0; i < lineas.size(); i++){
			if(lineas.get(i).getPortabilidad() != null){
				encontro = false;
				for(int j = 0; j < x_nroSS.size() && !encontro; j++){
					nroSS_1 = lineas.get(i).getPortabilidad().getNroSS();
					nroSS_2 = lineas.get(x_nroSS.get(j).get(0)).getPortabilidad().getNroSS();
					
					if(nroSS_1.equals(nroSS_2)){
						x_nroSS.get(j).add(i);
						encontro = true;
					}
				}
				
				if(!encontro){
					x_nroSS.add(new ArrayList<Integer>());
					x_nroSS.get(x_nroSS.size() - 1).add(i);
				}
			}
		}

		if(x_nroSS.size() > 0){
			for(int i = 0; i < x_nroSS.size(); i++){
				cantRecibeSMS = 0;
				
				for(int j = 0; j < x_nroSS.get(i).size(); j++){
					portabilidad = lineas.get(x_nroSS.get(i).get(j)).getPortabilidad();
					telefono = portabilidad.getAreaTelefono() + portabilidad.getTelefonoPortar();
					nroSS = portabilidad.getNroSS();
					apoderado = portabilidad.getRazonSocial() + portabilidad.getNombre() + portabilidad.getApellido() + 
								portabilidad.getNumeroDocumento() + portabilidad.getTipoDocumento().getId();
					
					if(!verificarNroSS(nroSS, solicitudServicioDto.getNumero())) return new PortabilidadResult(true,false,MSG_ERR_08);
					
					if(portabilidad.getTipoTelefonia().getId() == 1){ // Tipo de la telefonia de portabilidad es Prepaga
						// Debe recibir SMS si el tipo de telefonia es prepaga
						if(!portabilidad.isRecibeSMS()) return new PortabilidadResult(true, false, MSG_ERR_03);
						contLineasPrepagas++; // Cuenta la cantidad de lineas prepagas
					}
					
					if(portabilidad.isRecibeSMS()) cantRecibeSMS++;
					
					
					// Empieza un bucle para lograr comparaciones
					for(int n = 0; n < x_nroSS.get(i).size(); n++){
						if(j != n){ // Solo compara si las lineas son de diferentes indice en la lista (no son la misma)
							if(solicitudServicioDto.getLineas().get(x_nroSS.get(i).get(n)).getPortabilidad() != null){
								portabilidadAUX = solicitudServicioDto.getLineas().get(x_nroSS.get(i).get(n)).getPortabilidad();
								telefonoAUX = portabilidadAUX.getAreaTelefono() + portabilidadAUX.getTelefonoPortar();
								apoderadoAUX = portabilidadAUX.getRazonSocial() + portabilidadAUX.getNombre() + portabilidadAUX.getApellido() + 
												portabilidadAUX.getNumeroDocumento() + portabilidadAUX.getTipoDocumento().getId();
								
								// Los telefonos a portar no pueden ser iguales entre lineas
								if(telefono.equals(telefonoAUX)) return new PortabilidadResult(true,false,MSG_ERR_01.replaceAll("_NUMERO_", telefono));
								
								// Los numeros de solicitud de portabilidad no deben repetirse
								if(portabilidad.getNroSS().equals(portabilidadAUX.getNroSS())){
									if(portabilidad.getProveedorAnterior().getId() != portabilidadAUX.getProveedorAnterior().getId()) 
										return new PortabilidadResult(true, false, MSG_ERR_06);
									
									// El tipo de telefonia entre las portabilidades comparadas es prepaga
									if(portabilidad.getTipoTelefonia().getId() == 1 && portabilidadAUX.getTipoTelefonia().getId() == 1) 
										return new PortabilidadResult(true, false, MSG_ERR_05);
									// No debe repetirse la razon social, el apellido, el nombre, y el numero y tipo de documento 
									if(!apoderado.toUpperCase().equals(apoderadoAUX.toUpperCase())) return new PortabilidadResult(true, false, MSG_ERR_07);
								} // End control de numero de portabilidad repetido
							}
						}
					} // End n
				}

				if(!nroSS.isEmpty()){
					// Solo permite una linea que reciba SMS
					if(cantRecibeSMS > 1) return new PortabilidadResult(true, false, MSG_ERR_02.replaceAll("_NUMERO_", nroSS));
					// Debe haber una linea que reciba SMS
					if(cantRecibeSMS < 1) return new PortabilidadResult(true, false, MSG_ERR_02_BIS.replaceAll("_NUMERO_", nroSS)); 
				}
			}
			
			// El tipo de contribuyente de la cuenta de la solicitud de servicio es consumidor final
			if(repository.retrieve(Cuenta.class, solicitudServicioDto.getCuenta().getId()).getTipoContribuyente().getId() == 10){
				if(contLineasPrepagas > 6){
					// No pueden haber mas de 6 lineas del tipo prepaga con portabilidad si es consumidor final. 
					// Al analista de Creditos le muestra un mensaje y guarda
					if(sessionContextLoader.getVendedor().getTipoVendedor().getId() == 21) return new PortabilidadResult(true, true, MSG_ERR_04);// Tipo vendedor analista creditos = 21
					else return new PortabilidadResult(true, false, MSG_ERR_04);
				}
			}
			
			// Valida si existen solicitudes Pendientes de Portabilidad
//			permiteBPS = false;
//			permiteVantive = false;
//			try{
//				Long idVantive = repository.retrieve(Cuenta.class, solicitudServicioDto.getCuenta().getId()).getIdVantive();
//				String codVantive = repository.retrieve(Cuenta.class, solicitudServicioDto.getCuenta().getId()).getCodigoVantive();
//				
//				permiteVantive = vantiveSystem.getPermitePortabilidad(idVantive);
//				permiteBPS = bpsSystem.resolverValidacionesPendientes(codVantive);
//			}catch(Exception e){
//				throw ExceptionUtil.wrap(e);
//			}
//			if(!permiteVantive || !permiteBPS){
//				// Al analista de Creditos le muestra un mensaje y guarda
//				if(sessionContextLoader.getVendedor().getTipoVendedor().getId() == 21) return new PortabilidadResult(true, true, MSG_ERR_09);// Tipo vendedor analista creditos = 21
//				else return new PortabilidadResult(true, false, MSG_ERR_09);
//			}
		}
		
		// Puede guardar
		//return new PortabilidadResult(false,true,"");
		return new PortabilidadResult(true,true,"PRUEBAS RTF");
	}
	

	/**
	 * TODO: Portabilidad
	 * @param idSolicitudServicio
	 * @throws RpcExceptionMessages
	 */
	public List<String> generarParametrosPortabilidadRTF(Long idSolicitudServicio) throws RpcExceptionMessages {
		SolicitudServicio solicitudServicio = solicitudServicioRepository.getSolicitudServicioPorId(idSolicitudServicio);
		SolicitudPortabilidadPropertiesReport portabilidadPropRtp = new SolicitudPortabilidadPropertiesReport(solicitudServicio); 
		return portabilidadPropRtp.getReportFileNames();
	}
}

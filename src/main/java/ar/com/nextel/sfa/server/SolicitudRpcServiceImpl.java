package ar.com.nextel.sfa.server;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.hibernate.HibernateException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gargoylesoftware.htmlunit.javascript.host.Window;

import ar.com.nextel.business.constants.GlobalParameterIdentifier;
import ar.com.nextel.business.constants.KnownInstanceIdentifier;
import ar.com.nextel.business.constants.MessageIdentifier;
import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.vantive.VantiveSystem;
import ar.com.nextel.business.legacy.vantive.dto.EstadoSolicitudServicioCerradaDTO;
import ar.com.nextel.business.legacy.vantive.exception.VantiveSystemException;
import ar.com.nextel.business.personas.reservaNumeroTelefono.result.ReservaNumeroTelefonoBusinessResult;
import ar.com.nextel.business.solicitudes.crearGuardar.request.CreateSaveSSResponse;
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
import ar.com.nextel.components.mail.MailSender;
import ar.com.nextel.components.message.Message;
import ar.com.nextel.components.message.MessageList;
import ar.com.nextel.components.sequence.DefaultSequenceImpl;
import ar.com.nextel.components.sms.EnvioSMSService;
import ar.com.nextel.components.sms.SMSSender;
import ar.com.nextel.exception.SFAServerException;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.framework.repository.hibernate.HibernateRepository;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.personas.beans.Localidad;
import ar.com.nextel.model.personas.beans.TipoDocumento;
import ar.com.nextel.model.solicitudes.beans.ComentarioAnalista;
import ar.com.nextel.model.solicitudes.beans.Control;
import ar.com.nextel.model.solicitudes.beans.EstadoHistorico;
import ar.com.nextel.model.solicitudes.beans.EstadoPorSolicitud;
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
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.CambiosSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.ComentarioAnalistaDto;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.ControlDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSSTransfResultDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSolicitudServicioResultDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DescuentoDto;
import ar.com.nextel.sfa.client.dto.DescuentoLineaDto;
import ar.com.nextel.sfa.client.dto.DescuentoTotalDto;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoHistoricoDto;
import ar.com.nextel.sfa.client.dto.EstadoPorSolicitudDto;
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
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
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
	private MailSender mailSender;
 
	
	//MELI
	private DefaultSequenceImpl tripticoNextValue;
//	private AvalonSystem avalonSystem;
	
	//MGR - #1481
	private DefaultRetriever messageRetriever;;
	//#LF
	private static final String SS_SUCURSAL = "SS_SUCURSAL";
	private static final String CANTIDAD_EQUIPOS = "CANTIDAD_EQUIPOS";
	
	
	

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
		
		mailSender= (MailSender)context.getBean("mailSender");
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
		
		if (solicitudServicioDto.getNumero() != null) {
			solicitudServicioDto.setHistorialEstados(getEstadosPorSolicitud(new Long(solicitudServicioDto.getId())));//solicitudServicioDto.getNumero())));
		}
		
		AppLogger.info("Creacion de Solicitud de Servicio finalizada");
		resultDto.setSolicitud(solicitudServicioDto);
		//crea registro estado de la ss

       if (solicitud.getNumero()== null){
		    EstadoPorSolicitudDto estadoPorSolicitudDto = new EstadoPorSolicitudDto();
		  
			estadoPorSolicitudDto.setFecha(new Date());
			estadoPorSolicitudDto.setNumeroSolicitud(solicitudServicioDto.getId());
	//			VendedorDto v = (VendedorDto) mapper.map(sessionContextLoader.getVendedor(),
	//				VendedorDto.class);
	//		
	//		estadoPorSolicitudDto.setUsuario(v);
			estadoPorSolicitudDto.setUsuario(sessionContextLoader.getVendedor().getId());
			EstadoPorSolicitud e= mapper.map(estadoPorSolicitudDto,EstadoPorSolicitud.class);
			
		    List<EstadoSolicitud> estados = repository.getAll(EstadoSolicitud.class);
		    EstadoSolicitud encarga = new EstadoSolicitud();
		    for (Iterator iterator = estados.iterator(); iterator.hasNext();) {
				
		    	EstadoSolicitud estadoSolicitud = (EstadoSolicitud) iterator
						.next();
				if (estadoSolicitud.getDescripcion().equals("En carga")) {
					encarga = estadoSolicitud;
				}
			}
			e.setEstado(encarga);
			
			String totalRegistros=  this.getEstadoSolicitud(solicitudServicioDto.getId());
			if (totalRegistros.equals("")){
				EstadoPorSolicitud estadoPersistido= solicitudBusinessService.saveEstadoPorSolicitudDto(e);
			}
		}
		return resultDto;
	}

	public CreateSaveSolicitudServicioResultDto copySolicitudServicio(SolicitudServicioRequestDto solicitudServicioRequestDto , SolicitudServicioDto solicitudToCopy) 
		throws RpcExceptionMessages {

		CreateSaveSolicitudServicioResultDto resultDto = new CreateSaveSolicitudServicioResultDto();

		SolicitudServicioRequest request = mapper.map(solicitudServicioRequestDto,
				SolicitudServicioRequest.class);
		AppLogger.info("Creando Solicitud de Servicio con Request -> " + request.toString());
		SolicitudServicio solicitud = null;
		try {
			solicitud = solicitudBusinessService.copySolicitudServicio(request, mapper);
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
		
		solicitudToCopy.setId(solicitudServicioDto.getId());
		solicitudToCopy.setNumero(null);
		solicitudToCopy.setPataconex(0d);
		solicitudToCopy.setFirmar(false);
		solicitudToCopy.setAclaracionEntrega(null);
		
		resultDto.setSolicitud(solicitudToCopy);
//		resultDto.setSolicitud(solicitudServicioDto);
		return resultDto;
	}
	
//LF
//	public List<SolicitudServicioCerradaResultDto> searchSSCerrada(
	public List<SolicitudServicioCerradaResultDto> searchSolicitudesServicio(
			SolicitudServicioCerradaDto solicitudServicioCerradaDto, boolean analistaCreditos) throws RpcExceptionMessages {
		AppLogger.info("Iniciando busqueda de SS cerradas...");
		SolicitudServicioCerradaSearchCriteria solicitudServicioCerradaSearchCriteria = mapper.map(
				solicitudServicioCerradaDto, SolicitudServicioCerradaSearchCriteria.class);

		if(analistaCreditos) {
			solicitudServicioCerradaSearchCriteria.setBusquedaAnalistaCreditos(true);
			if(solicitudServicioCerradaDto.getNroDoc() != null && !solicitudServicioCerradaDto.getNroDoc().equals("")) {
				solicitudServicioCerradaSearchCriteria.setIdCuentas(obtenerIdCuentasPorTipoyNroDocumento(solicitudServicioCerradaDto.getTipoDoc(), solicitudServicioCerradaDto.getNroDoc()));
			}
		} else {
			solicitudServicioCerradaSearchCriteria.setBusquedaAnalistaCreditos(false);
			Vendedor vendedor = sessionContextLoader.getVendedor();
			solicitudServicioCerradaSearchCriteria.setVendedor(vendedor);
		}

		List<SolicitudServicio> list = null;
		try {
			list = this.solicitudesBusinessOperator
					.searchSolicitudesServicioHistoricas(solicitudServicioCerradaSearchCriteria);
		} catch (Exception e) {
			AppLogger.info("Error buscando Solicitudes de Servicio cerradas: " + e.getMessage(), e);
			throw ExceptionUtil.wrap(e);
		}
		
		if(analistaCreditos) {
			list = calcularUltimoEstado(list);
		}
		
		List result = mapper.convertList(list, SolicitudServicioCerradaResultDto.class, "ssCerradaResult");
		AppLogger.info("Busqueda de SS cerradas finalizada...");
		return result;
	}

	public List<Long> obtenerIdCuentasPorTipoyNroDocumento(Integer tipoDoc, String nroDoc) {
		List<Long> personas = repository.executeCustomQuery("getPersonaPorTipoYNroDoc", tipoDoc, nroDoc);
		List<Long> cuentas = new ArrayList<Long>();
		if(!personas.isEmpty()) {
			for (Iterator iterator = personas.iterator(); iterator
					.hasNext();) {
				Long idPersona = (Long) iterator.next();
				Long idCuenta = Long.valueOf(repository.executeCustomQuery("idCuentaPorPersona", idPersona).get(0).toString());
				cuentas.add(idCuenta);
			}
		} 
		return cuentas;
	}
	
	public DetalleSolicitudServicioDto getDetalleSolicitudServicio(Long idSolicitudServicio)
			throws RpcExceptionMessages {
		AppLogger.info("Iniciando consulta de estados de SS cerradas para idSS " + idSolicitudServicio);
		SolicitudServicio solicitudServicio = solicitudServicioRepository
				.getSolicitudServicioPorId(idSolicitudServicio);
		DetalleSolicitudServicioDto detalleSolicitudServicioDto = mapearSolicitud(solicitudServicio);

		try {
			//LF
			if(solicitudServicio.getIdVantive() != null) {
				detalleSolicitudServicioDto
						.setCambiosEstadoSolicitud(getEstadoSolicitudServicioCerrada(solicitudServicio
								.getIdVantive()));
			}
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

	public BuscarSSCerradasInitializer getBuscarSSInitializer(boolean analistaCredito) {
		BuscarSSCerradasInitializer buscarSSCerradasInitializer = new BuscarSSCerradasInitializer();

		List<String> listaResult = new ArrayList<String>();
		String cantResult;		
		if(analistaCredito) {
			cantResult = "50;100;500;5000";
		} else {
			cantResult = "10;25;50;75;100;500";
		}		
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
		
		if(analistaCredito) {
			buscarSSCerradasInitializer.setDocumentos(mapper.convertList(
					repository.getAll(TipoDocumento.class),
					TipoDocumentoDto.class));
		}

		mapper.convertList(repository.getAll(EstadoPorSolicitud.class),
				EstadoPorSolicitudDto.class);
				
		return buscarSSCerradasInitializer;
	}

	public List<EstadoPorSolicitudDto> getEstadosPorSolicitud(long numeroSS) {
		List<EstadoPorSolicitudDto> lista = mapper.convertList(
				repository.getAll(EstadoPorSolicitud.class),
				EstadoPorSolicitudDto.class);
		List<EstadoPorSolicitudDto> listaFinal = new ArrayList<EstadoPorSolicitudDto>();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getNumeroSolicitud() == numeroSS) {
				listaFinal.add(lista.get(i));
			}
		}
		return listaFinal;
	}
	 
public String getEstadoSolicitud(long solicitud) {
		
		PreparedStatement stmt;
		String resul ="";
		String s=String.valueOf(solicitud);
		String sql = String.format("select e.descripcion from sfa_estado_solicitud e "+
				"where e.id_estado_solicitud=(select * from (select id_estado_solicitud "+
				"from sfa_estado_por_solicitud s where s.numero_solicitud="+ s +"order by s.fecha_creacion desc)where rownum <=1)");
		try {
			stmt = ((HibernateRepository) repository)
					.getHibernateDaoSupport().getSessionFactory()
					.getCurrentSession().connection().prepareStatement(sql);
		
			
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				
				resul=resultSet.getString(1);
				
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resul;
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
		
		
			List<Control> controles = repository.getAll(Control.class);
			initializer.setControl(mapper.convertList(controles, ControlDto.class));
		
//		 EstadoSolicitud estado=(EstadoSolicitud) knownInstanceRetriever
//			.getObject(KnownInstanceIdentifier.ESTADO_ENCARGA_SS);
		List<Sucursal> sucursales = repository.getAll(Sucursal.class);
		initializer.setSucursales(mapper.convertList(sucursales, SucursalDto.class));
		
	//	initializer.setEstado(estado.getDescripcion());
		
		List<EstadoHistorico> estadosHistorico = repository.getAll(EstadoHistorico.class);
		initializer.setEstadosHistorico(mapper.convertList(estadosHistorico, EstadoHistoricoDto.class));
		initializer.setOpcionesEstado(mapper.convertList(repository.getAll(EstadoSolicitud.class),EstadoSolicitudDto.class));
		
		initializer.setComentarioAnalistaMensaje(mapper.convertList(repository.getAll(ComentarioAnalista.class),ComentarioAnalistaDto.class));

		return initializer;
	}

	//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un SaveSolicitudServicioResultDto 
	//que permite realizar el manejo de mensajes
	public CreateSaveSolicitudServicioResultDto saveSolicituServicio(SolicitudServicioDto solicitudServicioDto)
			throws RpcExceptionMessages {

		CreateSaveSolicitudServicioResultDto resultDto = new CreateSaveSolicitudServicioResultDto();
		try {
			SolicitudServicio solicitudSaved = solicitudBusinessService.saveSolicitudServicio(
					solicitudServicioDto, mapper);
			solicitudServicioDto = mapper.map(solicitudSaved, SolicitudServicioDto.class);
			
			Vendedor vendedor = sessionContextLoader.getSessionContext().getVendedor();
			if (vendedor.isADMCreditos()) {
				//Valida los predicados y el triptico
				CreateSaveSSResponse response = solicitudBusinessService.validarPredicadosGuardarSS(solicitudSaved);
				resultDto.setError(response.getMessages().hasErrors());
				resultDto.setMessages(mapper.convertList(response.getMessages().getMessages(), MessageDto.class));
				//larce
				solicitudBusinessService.transferirCuentaEHistorico(solicitudServicioDto);
			}		
			resultDto.setSolicitud(solicitudServicioDto);
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return resultDto;
	}

public boolean saveEstadoPorSolicitudDto(EstadoPorSolicitudDto estadoPorSolicitudDto) throws RpcExceptionMessages {

		try {
			EstadoPorSolicitud estadoPorSolicitud = mapper.map(estadoPorSolicitudDto, EstadoPorSolicitud.class);
			   List<EstadoSolicitud> estados = repository.getAll(EstadoSolicitud.class);
			    EstadoSolicitud estado = new EstadoSolicitud();
			    for (Iterator iterator = estados.iterator(); iterator.hasNext();) {
					
			    	EstadoSolicitud estadoSolicitud = (EstadoSolicitud) iterator
							.next();
					if (estadoSolicitud.getDescripcion().equals(estadoPorSolicitudDto.getEstado().getDescripcion())) {
						estado = estadoSolicitud;
					}
				}
			    estadoPorSolicitud.setEstado(estado);
			EstadoPorSolicitud estadoSaved = solicitudBusinessService.saveEstadoPorSolicitudDto(estadoPorSolicitud);
			
			if(estadoSaved != null){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
	}

	public String buildExcel(SolicitudServicioCerradaDto solicitudServicioCerradaDto, boolean analistaCreditos) throws RpcExceptionMessages {
		AppLogger.info("Creando archivo Excel...");
		SolicitudServicioCerradaSearchCriteria solicitudServicioCerradaSearchCriteria = mapper.map(
				solicitudServicioCerradaDto, SolicitudServicioCerradaSearchCriteria.class);

		
		if(analistaCreditos) {
			solicitudServicioCerradaSearchCriteria.setBusquedaAnalistaCreditos(true);
			if(solicitudServicioCerradaDto.getNroDoc() != null && !solicitudServicioCerradaDto.getNroDoc().equals("")) {
				solicitudServicioCerradaSearchCriteria.setIdCuentas(obtenerIdCuentasPorTipoyNroDocumento(solicitudServicioCerradaDto.getTipoDoc(), solicitudServicioCerradaDto.getNroDoc()));
			}
		}
		
		List<SolicitudServicio> list = null;
		try {
			list = this.solicitudesBusinessOperator
					.searchSolicitudesServicioHistoricas(solicitudServicioCerradaSearchCriteria);
		} catch (Exception e) {
			AppLogger.info("Error buscando Solicitudes de Servicio cerradas: " + e.getMessage(), e);
			throw ExceptionUtil.wrap(e);
		}
		String nameExcel;
		if(sessionContextLoader.getVendedor() != null) {
			nameExcel = sessionContextLoader.getVendedor().getUserName();
		} else {
			nameExcel = "SFA Revolution";
		}
		
		ExcelBuilder excel = new ExcelBuilder(nameExcel, "SFA Revolution");
		try {
			excel.crearExcel(list, analistaCreditos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AppLogger.info("Archivo Excel creado.");
		return nameExcel;
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
	

	public Boolean existDocDigitalizado(String pahtAndNameFile) {
		//Llega algo como '\\arpalfls02\imaging\imagenes_general\orden_servicio\2002_06\5.66559-1-0300110.tif'
		//Tengo que salvar las barras
		pahtAndNameFile = pahtAndNameFile.replace('\\', File.separatorChar);
		pahtAndNameFile = pahtAndNameFile.replace('/', File.separatorChar);
		AppLogger.info("Searching file " + pahtAndNameFile);
		return new File(pahtAndNameFile).exists();
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
				//larce
				solicitudBusinessService.transferirCuentaEHistorico(solicitudServicioDto);
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
	
	public CreateSaveSSTransfResultDto createCopySolicitudServicioTranferencia(
			SolicitudServicioRequestDto solicitudServicioRequestDto , SolicitudServicioDto solicitudToCopy) throws RpcExceptionMessages {
		CreateSaveSSTransfResultDto resultDto = new CreateSaveSSTransfResultDto();
		
		//MGR - ISDN 1824
		CreateSaveSolicitudServicioResultDto resultSSAux = this.copySolicitudServicio(solicitudServicioRequestDto, solicitudToCopy);
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
	
	public Integer calcularCantEquipos(List<LineaSolicitudServicioDto> lineaSS){
		int cantEquipos = 0;
		for (Iterator iterator = lineaSS.iterator(); iterator.hasNext();) {
			LineaSolicitudServicioDto lineaSolicitudServicioDto = (LineaSolicitudServicioDto) iterator
					.next();
			cantEquipos = cantEquipos + Integer.parseInt(repository.executeCustomQuery(CANTIDAD_EQUIPOS, lineaSolicitudServicioDto.getItem().getId()).get(0).toString());
		}
		
		return cantEquipos;
	}
	
	public List<SolicitudServicioDto> buscarHistoricoVentas(String nss) throws RpcExceptionMessages {
		List<SolicitudServicio> servicios = null;
		String codigoVantive;
		try {
			servicios = solicitudBusinessService.buscarHistoricoVentas(nss);
			if (servicios.size() > 0) {
				SolicitudServicio solicitudServicio = servicios.get(0);
				if (solicitudServicio.getClienteHistorico() == null) {
					codigoVantive = solicitudBusinessService.buscarClienteByNss(nss);
					solicitudServicio.setClienteHistorico(codigoVantive);
				}
				List<EstadoHistorico> estadosHistorico = repository.getAll(EstadoHistorico.class);
				for (Iterator<EstadoHistorico> iterator = estadosHistorico.iterator(); iterator
						.hasNext();) {
					EstadoHistorico estadoHistorico = (EstadoHistorico) iterator.next();
					if (solicitudServicio.getEstadoH().getDescripcion().equals(estadoHistorico.getDescripcion())) {
						solicitudServicio.setEstadoH(estadoHistorico);
						break;
					}
				}
			}
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return mapper.convertList(servicios, SolicitudServicioDto.class);
	}
	
/**
	 * Obtengo el ultimo estado en el que se encuentra la solicitud. 
	 * @param resultados
	 * @return
	 * @throws RpcExceptionMessages 
	 */
	public List<SolicitudServicio> calcularUltimoEstado(List<SolicitudServicio> resultados) throws RpcExceptionMessages {
		for (Iterator iterator = resultados.iterator(); iterator.hasNext();) {
			SolicitudServicio solicitudServicio = (SolicitudServicio) iterator
					.next();
			if(solicitudServicio.getNumero() != null) {
				List <String> ultimoEstado = repository.executeCustomQuery("ULTIMO_ESTADO", solicitudServicio.getNumero());
				if(!ultimoEstado.isEmpty()) {
					solicitudServicio.setUltimoEstado(ultimoEstado.get(0));
				} else {
					DetalleSolicitudServicioDto detalleSolicitudServicioDto = getDetalleSolicitudServicio(solicitudServicio.getId());
					int i = 0;
					if (detalleSolicitudServicioDto.getCambiosEstadoSolicitud() != null) {
						if(detalleSolicitudServicioDto.getCambiosEstadoSolicitud().size() > 1) {
							for (int j = 0; j < detalleSolicitudServicioDto.getCambiosEstadoSolicitud().size(); j++) {
								String estado = detalleSolicitudServicioDto.getCambiosEstadoSolicitud().get(j).getEstadoAprobacionSolicitud();
								if(estado.equals("Eligible")) {
									i = j - 1;
								}
							}
						}
						String ultEstado = detalleSolicitudServicioDto.getCambiosEstadoSolicitud().get(i).getEstadoAprobacionSolicitud();
						solicitudServicio.setUltimoEstado(ultEstado);
					} else {
						solicitudServicio.setUltimoEstado("En carga");
					}			
				}
			} else {
				solicitudServicio.setUltimoEstado("En carga");
			}
			
		}
		return resultados;
	}
	
	/**
	 * Obtengo la SolicitudServicio por id.
	 * @param ssDto
	 * @return
	 */
	public SolicitudServicioDto buscarSSPorId(Long id) {
		SolicitudServicioDto ssDto = new SolicitudServicioDto();
		ssDto.setId(id);
		SolicitudServicio ss = solicitudBusinessService.obtenerSSPorId(ssDto);
		return mapper.map(ss, SolicitudServicioDto.class);
	}
	
		/**
	 * Obtengo la Vendedor por id.
	 * @param vendedorDto
	 * @return
	 */
	public VendedorDto buscarVendedorPorId(Long id) {
		
		Vendedor vendedor = repository.retrieve(Vendedor.class, id);
		VendedorDto vendedorDto = mapper.map(vendedor, VendedorDto.class);
		return vendedorDto;
	}
	
	/**
	 * realiza el envio de mail
	 */
	public void enviarMail(String subject, String to){
		
		mailSender.sendMailBasico("esto es el mail", "estefania.iguacel@snoopconsulting.com");
		
		
	}
	
	
	/**
	 * realiza el envio de sms
	 * @throws SFAServerException 
	 */
	public void enviarSMS(String to, String mensaje){
		EnvioSMSService envioSMSService= new EnvioSMSService();
		try {
			//sms de prueba de eva 1149918150
			envioSMSService.enviarMensajeComun(to,mensaje);
		} catch (SFAServerException e) {
			
			e.printStackTrace();
		}
	
	}
	
	//GB
	/**
	 * Metodo generico para obtener la Cuenta utilizando su id.
	 * Fue creado porque el SolicitudServicioDto en lugar de CuentaDto
	 * posee un CuentaSSDto que es una version reducida.
	 */
	public CuentaDto obtenerCuentaPorId(long idCuenta) {
	List<Cuenta> cuentas = repository.executeCustomQuery("getCuentaById", idCuenta);
		if(cuentas != null && cuentas.size() > 0){
			return mapper.map(cuentas.get(0), CuentaDto.class);
		}else{
			return null;
		}
	}
	
	//GB
	/**
	 * Valida que la solicitud de servicio cumpla con los requerimientos.
	 */
	public Integer validarCuentaPorId(SolicitudServicioDto solicitud) {
		
		long idCuenta = solicitud.getCuenta().getId();
		List<Cuenta> cuentas = repository.executeCustomQuery("getCuentaById", idCuenta);
		
		if(cuentas != null && cuentas.size() > 0){
			
			Cuenta cuenta = cuentas.get(0);
			
			//Validacion 1
			if(cuenta.getCodigoBSCS() != null && cuenta.getCodigoFNCL() != null  && cuenta.getIdVantive() != null){
			//	List<Cuenta> suscriptorCuenta = repository.executeCustomQuery("getGCuentaSuscriptorId", cuentas.get(0).getCodigoVantive()+".00.00.1%");
				List<Cuenta> suscriptorCuenta = repository.executeCustomQuery("getGCuentaSuscriptorId", cuenta.getCodigoVantive()+".00.00.100000");
				//Validacion 2
				if(suscriptorCuenta.size() > 0){
					//Sigue...
					if(suscriptorCuenta.get(0).getCodigoBSCS() != null && suscriptorCuenta.get(0).getCodigoFNCL() != null  && suscriptorCuenta.get(0).getIdVantive() != null){
						//Validacion 3
						if(solicitud.getEstadoH() != null){
							if(solicitud.getEstadoH().getItemText().equalsIgnoreCase("Pass")){

								CuentaDto cuentaDto = mapper.map(cuenta, CuentaDto.class);
								
								CaratulaDto caratula = getCaratulaPorNroSS(cuentaDto.getCaratulas() , solicitud.getNumero());
								
								if(caratula != null){
									//Validacion 4
									if(caratula.isConfirmada()){
										
										String riskCodeText = caratula.getRiskCode().getDescripcion();
										List<ControlDto> controles = getControles();
										
										ControlDto aprobadoPorAgente = null;
										ControlDto analizadoPorCreditos = null;
										
										for (int i = 0; i < controles.size(); i++) {
											ControlDto control = controles.get(i);
											if(control.getDescripcion().equalsIgnoreCase("Analizado por Creditos")){
												analizadoPorCreditos = control;
											}
											if(control.getDescripcion().equalsIgnoreCase("Aprobado por Ejecutivo")){
												aprobadoPorAgente = control;
											}
										}
										
										if(riskCodeText.equalsIgnoreCase("EECC/Agente")){
											solicitud.setControl(aprobadoPorAgente);
										}else{
											solicitud.setControl(analizadoPorCreditos);
										}
									}
									
								}else{
									
									return 4;
								}
								return 0;
							}else{
								 return 3;
							}
						}else{
							 return 3;
						}
					}else{
						return 2;
					}
				}else{
					return 2;
				}
			}else{
				return 1;
			}		
		}else{
			return 1;
		}
	}

	/**
	 * Cambia el estado del historico a "Pass".
	 */
	//Se busca la SS usando el id en lugar de directamente pasar la ss
	//debido a que se producen errores en el mapeo debido a cuenta
	public void changeToPass(long idSS){
		SolicitudServicio ss = solicitudBusinessService.getSSById(idSS);
		EstadoHistorico estadoPass = repository.retrieve(EstadoHistorico.class, 2l);
		ss.setEstadoH(estadoPass);
		solicitudBusinessService.updateSolicitudServicio(ss);
	}

	public List<ControlDto> getControles(){
		List<Control> controles = repository.getAll(Control.class);
		return mapper.convertList(controles, ControlDto.class);
	}
	
	public CaratulaDto getCaratulaPorNroSS(List<CaratulaDto> caratulas, String nroSS){
		
		if(caratulas != null){			
			for (int i = 0; i < caratulas.size(); i++) {
				if(caratulas.get(i).getNroSS().equals(nroSS)){
					return caratulas.get(0);
				}
			}
		}
		
		return null;
	}
	
}	
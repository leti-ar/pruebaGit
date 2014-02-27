package ar.com.nextel.sfa.server.businessservice;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.dozer.DozerBeanMapper;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.nextel.business.constants.GlobalParameterIdentifier;
import ar.com.nextel.business.constants.KnownInstanceIdentifier;
import ar.com.nextel.business.cuentas.caratula.CaratulaTransferidaResultDto;
import ar.com.nextel.business.cuentas.caratula.dao.config.CaratulaTransferidaConfig;
import ar.com.nextel.business.cuentas.create.InsertUpdateCuentaConfig;
import ar.com.nextel.business.cuentas.facturaelectronica.FacturaElectronicaService;
import ar.com.nextel.business.cuentas.flota.FlotaService;
import ar.com.nextel.business.cuentas.flota.FlotaServiceImpl;
import ar.com.nextel.business.cuentas.flota.exception.FlotaServiceException;
import ar.com.nextel.business.cuentas.scoring.legacy.dto.ScoringCuentaLegacyDTO;
import ar.com.nextel.business.legacy.avalon.AvalonSystem;
import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.financial.dto.EncabezadoCreditoDTO;
import ar.com.nextel.business.legacy.financial.exception.FinancialSystemException;
import ar.com.nextel.business.oportunidades.OperacionEnCursoBusinessOperator;
import ar.com.nextel.business.personas.reservaNumeroTelefono.ReservaNumeroTelefonoBusinessOperator;
import ar.com.nextel.business.personas.reservaNumeroTelefono.result.ReservaNumeroTelefonoBusinessResult;
import ar.com.nextel.business.solicitudes.crearGuardar.GuardarBusinessOperator;
import ar.com.nextel.business.solicitudes.crearGuardar.request.CreateSaveSSResponse;
import ar.com.nextel.business.solicitudes.crearGuardar.request.CreateSaveSSTransfResponse;
import ar.com.nextel.business.solicitudes.crearGuardar.request.GuardarRequest;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.despacho.DespachoSolicitudBusinessOperator;
import ar.com.nextel.business.solicitudes.generacionCierre.GeneracionCierreBusinessOperator;
import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreRequest;
import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreResponse;
import ar.com.nextel.business.solicitudes.generacionCierre.result.CierreYPassResult;
import ar.com.nextel.business.solicitudes.historico.TransferirCuentaHistoricoConfig;
import ar.com.nextel.business.solicitudes.historico.VantiveHistoricoClienteConfig;
import ar.com.nextel.business.solicitudes.historico.VantiveHistoricoVentaRetrieveConfig;
import ar.com.nextel.business.solicitudes.provider.SolicitudServicioProviderResult;
import ar.com.nextel.business.solicitudes.repository.GenerarChangelogConfig;
import ar.com.nextel.business.solicitudes.repository.SolicitudServicioRepository;
import ar.com.nextel.components.accessMode.AccessAuthorization;
import ar.com.nextel.components.knownInstances.GlobalParameter;
import ar.com.nextel.components.knownInstances.retrievers.DefaultRetriever;
import ar.com.nextel.components.knownInstances.retrievers.model.KnownInstanceRetriever;
import ar.com.nextel.components.message.Message;
import ar.com.nextel.components.message.MessageImpl;
import ar.com.nextel.components.message.MessageList;
import ar.com.nextel.framework.connectionDAO.ConnectionDAOException;
import ar.com.nextel.framework.connectionDAO.TransactionConnectionDAO;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.framework.repository.hibernate.HibernateRepository;
import ar.com.nextel.model.cuentas.beans.Calificacion;
import ar.com.nextel.model.cuentas.beans.Caratula;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.DatosDebitoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.EstadoCreditoFidelizacion;
import ar.com.nextel.model.cuentas.beans.RiskCode;
import ar.com.nextel.model.cuentas.beans.TipoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.model.cuentas.beans.TipoVendedor;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.OperacionEnCurso;
import ar.com.nextel.model.personas.beans.Domicilio;
import ar.com.nextel.model.personas.beans.Persona;
import ar.com.nextel.model.personas.beans.Sexo;
import ar.com.nextel.model.personas.beans.TipoDocumento;
import ar.com.nextel.model.solicitudes.beans.EstadoPorSolicitud;
import ar.com.nextel.model.solicitudes.beans.EstadoSolicitud;
import ar.com.nextel.model.solicitudes.beans.Item;
import ar.com.nextel.model.solicitudes.beans.LineaSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.LineaTransfSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.ParametrosGestion;
import ar.com.nextel.model.solicitudes.beans.Plan;
import ar.com.nextel.model.solicitudes.beans.ServicioAdicionalIncluido;
import ar.com.nextel.model.solicitudes.beans.ServicioAdicionalLineaSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.ServicioAdicionalLineaTransfSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.Sucursal;
import ar.com.nextel.model.solicitudes.beans.TipoSolicitud;
import ar.com.nextel.services.components.sessionContext.SessionContext;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.services.nextelServices.scoring.ScoringHistoryItem;
import ar.com.nextel.services.nextelServices.veraz.ResultadoVeraz;
import ar.com.nextel.services.nextelServices.veraz.dto.VerazRequestDTO;
import ar.com.nextel.services.nextelServices.veraz.dto.VerazResponseDTO;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CuentaSSDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalIncluidoDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SubsidiosDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.nextel.util.StringUtil;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

@Service
public class SolicitudBusinessService {

	private SolicitudServicioBusinessOperator solicitudesBusinessOperator;
	private ReservaNumeroTelefonoBusinessOperator reservaNumeroTelefonoBusinessOperator;
	private GeneracionCierreBusinessOperator generacionCierreBusinessOperator;
	//MGR - ISDN 1824
	private GuardarBusinessOperator guardarBusinessOperator;
	private OperacionEnCursoBusinessOperator operacionEnCursoBusinessOperator;
	private FinancialSystem financialSystem;
	private SessionContextLoader sessionContextLoader;
	private Repository repository;
	private SolicitudServicioRepository solicitudServicioRepository;
	private final String CUENTA_FILTRADA = "Acceso denegado. No puede operar con esta cuenta.";
	private TransactionConnectionDAO sfaConnectionDAO;
	private GenerarChangelogConfig generarChangelogConfig;
	private FacturaElectronicaService facturaElectronicaService;
	private VantiveHistoricoVentaRetrieveConfig vantiveHistoricoVentaRetrieveConfig;
	private VantiveHistoricoClienteConfig vantiveHistoricoClienteConfig;
	private TransferirCuentaHistoricoConfig transferirCuentaHistoricoConfig;
	private AvalonSystem avalonSystem;
	private DefaultRetriever globalParameterRetriever;
	private KnownInstanceRetriever knownInstanceRetriever;
	private InsertUpdateCuentaConfig insertUpdateCuentaConfig;
	private CaratulaTransferidaConfig caratulaTransferidaConfig;
	private DespachoSolicitudBusinessOperator despachoSolicitudBusinessOperator;
	
	@Qualifier("serviceImpl")
	private FlotaService flotaService;
	
	
//	MGR - Se mueve la creacion de la cuenta
	private String MENSAJE_ERROR_CREAR_CUENTA= "La SS % quedó pendiente de aprobación, por favor verificar y dar curso manual.";

//	MGR - Parche de emergencia
	public static final int CIERRE_NORMAL = 1; //Se cierra la solicitud de servicio y se transfiere a Vantive
	public static final int LINEAS_NO_CUMPLEN_CC = 2;
	public static final int CIERRE_PASS_AUTOMATICO = 3;
	
	private static final String CANTIDAD_EQUIPOS = "CANTIDAD_EQUIPOS";
	
	
	
	@Autowired
    public void setFlotaService(@Qualifier("flotaService")FlotaService flotaService) {
        this.flotaService = flotaService;
    }
	
//	MGR - #3464
//	private static String namedQueryItemParaActivacionOnline = "ITEMS_PARA_PLANES_ACT_ONLINE";
	
	private static String GET_SUBSIDIO_ITEM_VENDEDOR = "GET_SUBSIDIO_ITEM_VENDEDOR";
	
	@Autowired
	public void setGlobalParameterRetriever(
			@Qualifier("globalParameterRetriever") DefaultRetriever globalParameterRetriever) {
		this.globalParameterRetriever = globalParameterRetriever;
	}
	
	
	@Autowired
	public void setAvalonSystem(
			@Qualifier("avalonSystemBean")AvalonSystem avalonSystem) {
		this.avalonSystem = avalonSystem;
	}
	
	@Autowired
	public void setFacturaElectronicaService(FacturaElectronicaService facturaElectronicaService) {
		this.facturaElectronicaService = facturaElectronicaService;
	}
	
	@Autowired
	public void setSolicitudesBusinessOperator(
			SolicitudServicioBusinessOperator solicitudServicioBusinessOperatorBean) {
		this.solicitudesBusinessOperator = solicitudServicioBusinessOperatorBean;
	}

	@Autowired
	public void setReservaNumeroTelefonoBusinessOperator(
			ReservaNumeroTelefonoBusinessOperator reservaNumeroTelefonoBusinessOperator) {
		this.reservaNumeroTelefonoBusinessOperator = reservaNumeroTelefonoBusinessOperator;
	}

	@Autowired
	public void setFinancialSystem(FinancialSystem financialSystemBean) {
		this.financialSystem = financialSystemBean;
	}

	@Autowired
	public void setSessionContextLoader(SessionContextLoader sessionContextLoader) {
		this.sessionContextLoader = sessionContextLoader;
	}

	@Autowired
	public void setGeneracionCierreBusinessOperator(
			@Qualifier("generacionCierreBusinessOperatorBean") GeneracionCierreBusinessOperator generacionCierreBusinessOperator) {
		this.generacionCierreBusinessOperator = generacionCierreBusinessOperator;
	}
	
	//MGR - ISDN 1824
	@Autowired
	public void setGuardarBusinessOperator(
			@Qualifier("guardarBusinessOperatorBean") GuardarBusinessOperator guardarBusinessOperator) {
		this.guardarBusinessOperator = guardarBusinessOperator;
	}

	@Autowired
	public void setOperacionEnCursoBusinessOperator(
			OperacionEnCursoBusinessOperator operacionEnCursoBusinessOperator) {
		this.operacionEnCursoBusinessOperator = operacionEnCursoBusinessOperator;
	}

	@Autowired
	public void setRepository(@Qualifier("repository") Repository repository) {
		this.repository = repository;
	}

	@Autowired
	public void setSolicitudServicioRepository(
			@Qualifier("solicitudServicioRepositoryBean") SolicitudServicioRepository solicitudServicioRepository) {
		this.solicitudServicioRepository = solicitudServicioRepository;
	}

	@Autowired
	public void setSfaConnectionDAO(
			@Qualifier("sfaConnectionDAOBean") TransactionConnectionDAO sfaConnectionDAOBean) {
		this.sfaConnectionDAO = sfaConnectionDAOBean;
	}

	@Autowired
	public void setGenerarChangelogConfig(GenerarChangelogConfig generarChangelogConfig) {
		this.generarChangelogConfig = generarChangelogConfig;
	}

	@Autowired
	public void setVantiveHistoricoVentaRetrieveConfig(
			VantiveHistoricoVentaRetrieveConfig vantiveHistoricoVentaRetrieveConfig) {
		this.vantiveHistoricoVentaRetrieveConfig = vantiveHistoricoVentaRetrieveConfig;
	}
	
	@Autowired
	public void setVantiveHistoricoClienteConfig(
			VantiveHistoricoClienteConfig vantiveHistoricoClienteConfig) {
		this.vantiveHistoricoClienteConfig = vantiveHistoricoClienteConfig;
	}
	
	@Autowired
	public void setTransferirCuentaHistoricoConfig(
			TransferirCuentaHistoricoConfig transferirCuentaHistoricoConfig) {
		this.transferirCuentaHistoricoConfig = transferirCuentaHistoricoConfig;
	}
	
	@Autowired
	public void setKnownInstanceRetriever(
			@Qualifier("knownInstancesRetriever") KnownInstanceRetriever knownInstanceRetrieverBean) {
		this.knownInstanceRetriever = knownInstanceRetrieverBean;
	}
	
	@Autowired
	public void setInsertUpdateCuentaConfig(
			InsertUpdateCuentaConfig insertUpdateCuentaConfig) {
		this.insertUpdateCuentaConfig = insertUpdateCuentaConfig;
	}
	
	@Autowired
	public void setCaratulaTransferidaConfig(CaratulaTransferidaConfig caratulaTransferidaConfig) {
		this.caratulaTransferidaConfig = caratulaTransferidaConfig;
	}
	
	@Autowired
	public void setDespachoSolicitudBusinessOperator(
			DespachoSolicitudBusinessOperator despachoSolicitudBusinessOperator) {
		this.despachoSolicitudBusinessOperator = despachoSolicitudBusinessOperator;
	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SolicitudServicio createSolicitudServicio(SolicitudServicioRequest solicitudServicioRequest,
			DozerBeanMapper mapper) throws BusinessException, FinancialSystemException {

		SolicitudServicioProviderResult providerResult = null;
		providerResult = this.solicitudesBusinessOperator.provideSolicitudServicio(solicitudServicioRequest);

		SolicitudServicio solicitud = providerResult.getSolicitudServicio();

		addCreditoFidelizacion(solicitud);

		AccessAuthorization accessAuthorization = this.solicitudesBusinessOperator
				.calculateAccessAuthorization(providerResult.getSolicitudServicio());

		if (!accessAuthorization.hasSamePermissionsAs(AccessAuthorization.fullAccess())) {
			accessAuthorization.setReasonPrefix(CUENTA_FILTRADA);
			throw new BusinessException(null, accessAuthorization.getReason());
		}

		Vendedor vendedor = sessionContextLoader.getVendedor();

		// if(no tiene permiso de edicion){
		// solicitudServicio.getCuenta().terminarOperacion();
		// }
		checkServiciosAdicionales(solicitud);

		solicitud.consultarCuentaPotencial();
		if (providerResult.wasCreationNeeded()) {
			if (!solicitud.getCuenta().isLockedByAnyone(vendedor) || solicitud.getCuenta().isUnlockedFor(vendedor)) {
				solicitud.getCuenta().iniciarOperacion(vendedor);
			}
			//#3427 - Si el vendedor es AP, pero es distinto al que tenía cargado anteriormente, actualizo la solicitud con el nuevo vendedor
			if (vendedor.isAP() && !solicitud.getVendedor().getId().equals(vendedor.getId())) {
				repository.update(solicitud);
			}
			repository.save(solicitud);
		}

		mapper.map(solicitud.getCuenta(), CuentaSSDto.class, "cuentaSolicitud");
		return solicitud;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SolicitudServicio copySolicitudServicio(SolicitudServicioRequest solicitudServicioRequest,
			DozerBeanMapper mapper) throws BusinessException, FinancialSystemException {

		SolicitudServicioProviderResult providerResult = null;
		providerResult = this.solicitudesBusinessOperator.provideEmptySolicitudServicio(solicitudServicioRequest);

		SolicitudServicio solicitud = providerResult.getSolicitudServicio();

		addCreditoFidelizacion(solicitud);

		AccessAuthorization accessAuthorization = this.solicitudesBusinessOperator
				.calculateAccessAuthorization(providerResult.getSolicitudServicio());

		if (!accessAuthorization.hasSamePermissionsAs(AccessAuthorization.fullAccess())) {
			accessAuthorization.setReasonPrefix(CUENTA_FILTRADA);
			throw new BusinessException(null, accessAuthorization.getReason());
		}

		Vendedor vendedor = sessionContextLoader.getVendedor();

		// if(no tiene permiso de edicion){
		// solicitudServicio.getCuenta().terminarOperacion();
		// }
		checkServiciosAdicionales(solicitud);

		solicitud.consultarCuentaPotencial();
		if (providerResult.wasCreationNeeded()) {
			if (!solicitud.getCuenta().isLockedByAnyone(vendedor) || solicitud.getCuenta().isUnlockedFor(vendedor)) {
				solicitud.getCuenta().iniciarOperacion(vendedor);
			}
			repository.save(solicitud);
		}

		mapper.map(solicitud.getCuenta(), CuentaSSDto.class, "cuentaSolicitud");
		return solicitud;
	}
	
	/** Controla que los servicios adicionales que poseen las lineas de la solicitud aun sean validos */
	private void checkServiciosAdicionales(SolicitudServicio solicitud) {
		// Controlo la consistencia de los servicios adicionales existentes
		for (LineaSolicitudServicio linea : solicitud.getLineas()) {
			TipoSolicitud tipoSolicitud = linea.getTipoSolicitud();
			Plan plan = linea.getPlan();
			Item item = linea.getItem();
			Cuenta cuenta = solicitud.getCuenta();
			Collection<ServicioAdicionalLineaSolicitudServicio> serviciosAdicionales = null;
			if (tipoSolicitud != null && plan != null && item != null && cuenta != null) {
				//MGR - #873 - Se agrega el Vendedor
				serviciosAdicionales = solicitudServicioRepository.getServiciosAdicionales(tipoSolicitud
						.getId(), plan.getId(), item.getId(), cuenta.getId(),sessionContextLoader.getVendedor(), cuenta.isEmpresa());
			} else {
				AppLogger.warn("No se pudo validar los Servicios Adicionales de la Linea de "
						+ "Solicitud de Servicio " + linea.getId() + " (" + linea.getAlias()
						+ "). Revisar la integridad de los datos en la base.\nDetalle:"
						+ "\nidTipoSolicitud = " + (tipoSolicitud != null ? tipoSolicitud.getId() : "")
						+ "\nidPlan = " + (plan != null ? plan.getId() : "") + "\nidItem = "
						+ (item != null ? item.getId() : "") + "\nidCuenta = "
						+ (cuenta != null ? cuenta.getId() : ""));
				continue;
			}
			Set<ServicioAdicionalLineaSolicitudServicio> servicioasForDeletion = new HashSet();
			for (ServicioAdicionalLineaSolicitudServicio saLinea : linea.getServiciosAdicionales()) {
				boolean conteinsSA = false;
				for (ServicioAdicionalLineaSolicitudServicio sa : serviciosAdicionales) {
					if (sa.getServicioAdicional().getId().equals(saLinea.getServicioAdicional().getId())) {
						conteinsSA = true;
						break;
					}
				}
				if (!conteinsSA) {
					servicioasForDeletion.add(saLinea);
				}
			}
			linea.removeAll(servicioasForDeletion);
		}
		

		for (LineaTransfSolicitudServicio lineaTransf : solicitud.getLineasTranf()) {
			Plan plan = lineaTransf.getPlanNuevo();
			List<ServicioAdicionalIncluido> serviciosAdicionales = null;
			if (plan != null) {
				serviciosAdicionales = solicitudServicioRepository.getServiciosAdicionalesContrato(plan.getId(), sessionContextLoader.getVendedor());
			} else {
				AppLogger.warn("No se pudo validar los Servicios Adicionales de la Linea de "
						+ "Solicitud de Servicio " + lineaTransf.getId() + " ( Contrato: " + lineaTransf.getContrato()
						+ "). Revisar la integridad de los datos en la base.\nDetalle:"
						+ "\nidSolicitudServicio = " + (solicitud != null ? solicitud.getId() : "")
						+ "\nidPlan = " + (plan != null ? plan.getId() : "") + "\nidCuenta = "
						+ (solicitud.getCuenta() != null ? solicitud.getCuenta().getId() : ""));
				continue;
			}
			Set<ServicioAdicionalLineaTransfSolicitudServicio> servicioasForDeletion = new HashSet<ServicioAdicionalLineaTransfSolicitudServicio>();
			for (ServicioAdicionalLineaTransfSolicitudServicio saLinea : lineaTransf.getServiciosAdicionales()) {
				boolean conteinsSA = false;
				for (ServicioAdicionalIncluido sa : serviciosAdicionales) {
					if (sa.getServicioAdicional().getId().equals(saLinea.getServicioAdicional().getId())) {
						conteinsSA = true;
						break;
					}
				}
				if (!conteinsSA) {
					servicioasForDeletion.add(saLinea);
				}
			}
			lineaTransf.removeAll(servicioasForDeletion);
		}
	}

	private void addCreditoFidelizacion(SolicitudServicio solicitudServicio) throws FinancialSystemException {
		String numeroCuenta = solicitudServicio.getCuenta().getCodigoVantive();
		EncabezadoCreditoDTO creditoFidelizacion = getCreditoFidelizacion(numeroCuenta);
		EstadoCreditoFidelizacion estadoCreditoFidelizacion = new EstadoCreditoFidelizacion(solicitudServicio
				.getCuenta(), creditoFidelizacion.getMonto().doubleValue(), creditoFidelizacion
				.getFechaVencimiento());

		solicitudServicio.getCuenta().setEstadoCreditoFidelizacion(estadoCreditoFidelizacion);
	}

	private EncabezadoCreditoDTO getCreditoFidelizacion(String numeroCuenta) throws FinancialSystemException {
		EncabezadoCreditoDTO retrieveEncabezadoCreditoFidelizacion = financialSystem
				.retrieveEncabezadoCreditoFidelizacion(numeroCuenta);
		return retrieveEncabezadoCreditoFidelizacion;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public EstadoPorSolicitud saveEstadoPorSolicitudDto(EstadoPorSolicitud estadoPorSolicitud) {
		if(estadoPorSolicitud.getSolicitud().getId()>0){
			repository.save(estadoPorSolicitud);			
	}
		
		return estadoPorSolicitud;
	}
	
	/**
	 * Mapea desde el DTO de la solicitud y luego lo salva en la base.
	 * @param solicitudServicioDto
	 * @param mapper
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SolicitudServicio saveSolicitudServicio(SolicitudServicioDto solicitudServicioDto,
			MapperExtended mapper) {
		SolicitudServicio ss = this.mapperSSDtoToSolicitudServicio(solicitudServicioDto,mapper);
		this.saveSolicitudServicio(ss);
		return ss;
	}
	 
	
	/**
	 * El mapper de DozerMapping no convierte completamente la SolicitudServicioDTO a la entidad
	 * SolicitudServicio por lo tanto utilizar esté servicio para obtener la solicitud desde su dto.
	 * @param solicitudServicioDto
	 * @param mapper
	 * @return
	 */
	public SolicitudServicio mapperSSDtoToSolicitudServicio(SolicitudServicioDto solicitudServicioDto,
			MapperExtended mapper) {
		
		SolicitudServicio solicitudServicio = repository.retrieve(SolicitudServicio.class,
				solicitudServicioDto.getId());
		solicitudServicio.setDomicilioEnvio(null);
		solicitudServicio.setDomicilioFacturacion(null);
//		EstadoSolicitud estado = repository.retrieve(EstadoSolicitud.class, solicitudServicioDto.getEstados().getCode());
//		solicitudServicio.setUltimoEstado(estado.getDescripcion());
//		if( (solicitudServicio.getVendedor() == null && solicitudServicioDto.getVendedor() != null) ||
//				(solicitudServicio.getVendedor() != null && solicitudServicioDto.getVendedor() != null &&
//				!solicitudServicio.getVendedor().getId().equals(solicitudServicioDto.getVendedor().getId())) ){
			Vendedor vendedor = repository.retrieve(Vendedor.class, solicitudServicioDto.getVendedor().getId());
			solicitudServicio.setVendedor(vendedor);
			solicitudServicioDto.setVendedor(mapper.map(vendedor, VendedorDto.class));
//		}
			
			
		if( (solicitudServicio.getCuentaCedente() == null && solicitudServicioDto.getCuentaCedente() != null) ||
				(solicitudServicio.getCuentaCedente() != null && solicitudServicioDto.getCuentaCedente() != null &&
				!solicitudServicio.getCuentaCedente().getId().equals(solicitudServicioDto.getCuentaCedente().getId())) ){
			
		
			Cuenta ctaCedente = repository.retrieve(Cuenta.class, solicitudServicioDto.getCuentaCedente().getId());
			
			// Actualizo la cuenta con la flota correspondiente, ticket mantis: 0004038.
			try {
				flotaService.updateCuentaConFlota(ctaCedente);
			} catch (FlotaServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			solicitudServicio.setCuentaCedente(ctaCedente);
		}
		
		
		//MGR - #1359
//		if( (solicitudServicio.getUsuarioCreacion() == null && solicitudServicioDto.getUsuarioCreacion() != null) ||
//				(solicitudServicio.getUsuarioCreacion() != null && solicitudServicioDto.getUsuarioCreacion() != null &&
//				!solicitudServicio.getUsuarioCreacion().getId().equals(solicitudServicioDto.getUsuarioCreacion().getId())) ){
//			Vendedor userCreacion = repository.retrieve(Vendedor.class, solicitudServicioDto.getUsuarioCreacion().getId());
//			solicitudServicio.setUsuarioCreacion(userCreacion);
//		}
		if( (solicitudServicio.getSucursal() == null && solicitudServicioDto.getIdSucursal() != null) ||
				(solicitudServicio.getSucursal() != null && solicitudServicioDto.getIdSucursal() != null &&
				!solicitudServicio.getSucursal().getId().equals(solicitudServicioDto.getIdSucursal())) ){
			//#1802: Modificaciones en SS de Transferencia
			Vendedor vendedorLogueado = sessionContextLoader.getVendedor();
			if (vendedorLogueado.isAP()) {
				solicitudServicio.setSucursal(vendedorLogueado.getSucursal());
			} else {
				Sucursal sucursal = repository.retrieve(Sucursal.class, solicitudServicioDto.getIdSucursal());
				solicitudServicio.setSucursal(sucursal);
			}
		}
		
		mapper.map(solicitudServicioDto, solicitudServicio);
		//Estefania Iguacel - Comentado para salir solo con cierre - CU#8 				
//		if(solicitudServicioDto.getControl() != null){
//			Control control =  repository.retrieve(Control.class, solicitudServicioDto.getControl().getId());
//			solicitudServicio.setControl(control);			
//		}
		
		//PARCHE: Esto es por que dozer mapea los id cuando se le indica que no
		for (LineaTransfSolicitudServicio lineaTransf : solicitudServicio.getLineasTranf()) {
			for (ContratoViewDto cto : solicitudServicioDto.getContratosCedidos()) {
				if(lineaTransf.getContrato().equals(cto.getContrato())){
					
					for (ServicioAdicionalLineaTransfSolicitudServicio servAd : lineaTransf.getServiciosAdicionales()) {
						for (ServicioAdicionalIncluidoDto serAdCto : cto.getServiciosAdicionalesInc()) {
							
							if(servAd.getServicioAdicional().getId().equals(serAdCto.getServicioAdicional().getId())){
								if(serAdCto.getIdSALineaTransf() == null){
									servAd.setId(serAdCto.getIdSALineaTransf());
								}
							}
						}
					}
				}
			}
		}


		//-MGR- Val-punto6 NO esta mapeando directamente los cambios en la cuenta, esta bien que lo haga asi?
		//Esto es por que debe cambiar el vendedor de la cta cesionario
		if(!solicitudServicio.getCuenta().getVendedor().getId().equals(
				solicitudServicioDto.getCuenta().getVendedor().getId())){
			Vendedor vendedorCta = repository.retrieve(Vendedor.class, solicitudServicioDto.getCuenta().getVendedor().getId());
			solicitudServicio.getCuenta().setVendedor(vendedorCta);
		}


		// Estas lineas son por un problema no identificado, que hace que al querer guardar el tipo de tarjeta
		// detecte que el objeto no está attachado a la session
		if (solicitudServicio.getCuenta().getDatosPago() instanceof DatosDebitoTarjetaCredito) {
			DatosDebitoTarjetaCredito datosPago = (DatosDebitoTarjetaCredito) solicitudServicio.getCuenta()
					.getDatosPago();
			datosPago.setTipoTarjeta(repository.retrieve(TipoTarjeta.class, datosPago.getTipoTarjeta()
					.getId()));
		}
		
		if (solicitudServicio.getCuentaCedente()!= null && solicitudServicio.getCuentaCedente().getDatosPago() instanceof DatosDebitoTarjetaCredito) {
			DatosDebitoTarjetaCredito datosPago = (DatosDebitoTarjetaCredito) solicitudServicio.getCuentaCedente()
					.getDatosPago();
			datosPago.setTipoTarjeta(repository.retrieve(TipoTarjeta.class, datosPago.getTipoTarjeta()
					.getId()));
		}
		
		//MGR - #1708 - Estas lineas son por un problema no identificado, que hace que al querer
		//guardar el tipo de cuenta bancaria detecte que el objeto no está attachado a la session
		if (solicitudServicio.getCuenta().getDatosPago() instanceof DatosDebitoCuentaBancaria) {
			DatosDebitoCuentaBancaria datosPago = (DatosDebitoCuentaBancaria) solicitudServicio.getCuenta()
					.getDatosPago();
			datosPago.setTipoCuentaBancaria(repository.retrieve(TipoCuentaBancaria.class, datosPago.getTipoCuentaBancaria()
					.getId()));
		}
		
		if (solicitudServicio.getCuentaCedente()!= null && solicitudServicio.getCuentaCedente().getDatosPago() instanceof DatosDebitoCuentaBancaria) {
			DatosDebitoCuentaBancaria datosPago = (DatosDebitoCuentaBancaria) solicitudServicio.getCuentaCedente()
					.getDatosPago();
			datosPago.setTipoCuentaBancaria(repository.retrieve(TipoCuentaBancaria.class, datosPago.getTipoCuentaBancaria()
					.getId()));
		}

		if (solicitudServicioDto.getIdDomicilioEnvio() != null) {
			for (Domicilio domicilioE : solicitudServicio.getCuenta().getPersona().getDomicilios()) {
				if (solicitudServicioDto.getIdDomicilioEnvio().equals(domicilioE.getId())) {
					solicitudServicio.setDomicilioEnvio(domicilioE);
					break;
				}
			}
		} else {
			solicitudServicio.setDomicilioEnvio(null);
			//MGR - #1525
			for (Domicilio domicilioE : solicitudServicio.getCuenta().getPersona().getDomicilios()) {
				if (domicilioE.esPrincipalDeEntrega()) {
					solicitudServicio.setDomicilioEnvio(domicilioE);
					break;
				}
			}
		}
		if (solicitudServicioDto.getIdDomicilioFacturacion() != null) {
			for (Domicilio domicilioF : solicitudServicio.getCuenta().getPersona().getDomicilios()) {
				if (solicitudServicioDto.getIdDomicilioFacturacion().equals(domicilioF.getId())) {
					solicitudServicio.setDomicilioFacturacion(domicilioF);
					break;
				}
			}
		} else {
			solicitudServicio.setDomicilioFacturacion(null);
			//MGR - #1525
			for (Domicilio domicilioF : solicitudServicio.getCuenta().getPersona().getDomicilios()) {
				if (domicilioF.esPrincipalDeFacturacion()) {
					solicitudServicio.setDomicilioFacturacion(domicilioF);
					break;
				}
			}
		}
		// esto limpia los ids negativos temporales
		for (Domicilio domicilio : solicitudServicio.getCuenta().getPersona().getDomicilios()) {
			if (domicilio.getId() != null && domicilio.getId() < 0) {
				domicilio.setId(null);
			}
		}
		solicitudServicio.setIdConsultaScoring(solicitudServicioDto.getIdConsultaScoring());
        solicitudServicio.setIdConsultaVeraz(solicitudServicioDto.getIdConsultaVeraz());
		
		return solicitudServicio;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public ReservaNumeroTelefonoBusinessResult reservarNumeroTelefonico(long numero, long idTipoTelefonia,
			long idModalidadCobro, long idLocalidad) throws BusinessException {
		ReservaNumeroTelefonoBusinessResult result;
		result = reservaNumeroTelefonoBusinessOperator.reservarNumeroTelefono(numero, idTipoTelefonia,
				idModalidadCobro, idLocalidad, sessionContextLoader.getVendedor());
		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void desreservarNumeroTelefono(long numero, Long idLineaSolicitudServicio)
			throws BusinessException {
		if (idLineaSolicitudServicio != null) {
			LineaSolicitudServicio linea = repository.retrieve(LineaSolicitudServicio.class,
					idLineaSolicitudServicio);
			linea.setNumeroReservaArea("");
			if (linea.getNumeroReserva() != null && linea.getNumeroReserva().length() > 4) {
				int beginIndex = linea.getNumeroReserva().length() - 4;
				linea.setNumeroReserva(linea.getNumeroReserva().substring(beginIndex));
			}
			repository.save(linea);
		}
		reservaNumeroTelefonoBusinessOperator.desreservarNumeroTelefono(numero, sessionContextLoader
				.getVendedor());

	}
	private final long unDiaEnMilis = 1000*60*60*24;

	
//	MGR - Se mueve la creacion de la cuenta, se pasa "puedeCerrar" en lugar de "cierraPorCC"
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public GeneracionCierreResponse generarCerrarSolicitud(SolicitudServicio solicitudServicio,
			String pinMaestro, boolean cerrar, int puedeCerrar, boolean pinChequeadoEnNexus) 
			throws BusinessException {
		
//		MGR - Se crea la variable que venia en la llamada	
//		MGR - Parche de emergencia
		boolean cierraPorCC = false;
		if(!solicitudServicio.getGrupoSolicitud().isGrupoTransferencia()){
			cierraPorCC = puedeCerrar != CierreYPassResult.CIERRE_NORMAL;
		}
		
		
//		#1636 mrial
		boolean esProspect = false;	
		if (solicitudServicio.getCuenta().isProspectEnCarga()
				|| solicitudServicio.getCuenta().isProspect()) {
			esProspect = true;
		}
		final Date hace4Dias = new Date(System.currentTimeMillis() - 4 * unDiaEnMilis);
		
		if (!GenericValidator.isBlankOrNull(pinMaestro) && solicitudServicio.getCuenta().isEnCarga()) {
			solicitudServicio.getCuenta().setPinMaestro(pinMaestro);
		} else {
			setScoringChecked(solicitudServicio, pinMaestro, pinChequeadoEnNexus);
		}
		if (cerrar) {
			// La SS se está invocando desde Objeto B (Interfaz web)
			solicitudServicio.setSourceModule("B");
		}
		GeneracionCierreRequest generacionCierreRequest = new GeneracionCierreRequest(pinMaestro,pinChequeadoEnNexus,
				solicitudServicio);
		GeneracionCierreResponse response = null;
		if (cerrar) {
			
			response = generacionCierreBusinessOperator.cerrarSolicitudServicio(generacionCierreRequest, cierraPorCC);
			
			if (solicitudServicio.getCuenta().getFacturaElectronica() != null
//					&& !solicitudServicio.getCuenta().getFacturaElectronica().getReplicadaAutogestion()
					&& hace4Dias.before(solicitudServicio.getCuenta().getFacturaElectronica().getLastModificationDate())
					&& !response.getMessages().hasErrors()) {
				
				//MGR - ISDN 1824 - El adm. de creditos adhiere el cliente a factura electronica y genera la gestion
				//tanto para prospect como para clientes
				Vendedor vendedor = repository.retrieve(Vendedor.class, SessionContextLoader.getInstance().getVendedor().getId());
				boolean isADMCreditos = vendedor.isADMCreditos();
				
				Long codigoBSCS = null;
				if(solicitudServicio.getCuenta().getCodigoBSCS() != null){
					codigoBSCS = new Long(solicitudServicio.getCuenta().getCodigoBSCS());
				}
				
				//MGR - ISDN 1824
				if (!esProspect) {
					facturaElectronicaService.adherirFacturaElectronica(
							codigoBSCS, solicitudServicio.getCuenta()
							.getCodigoVantive(), solicitudServicio.getCuenta()
							.getFacturaElectronica().getEmail(), "", solicitudServicio.getVendedor());
					solicitudServicio.getCuenta().getFacturaElectronica().setReplicadaAutogestion(Boolean.TRUE);
				}
				repository.save(solicitudServicio.getCuenta().getFacturaElectronica());
				
				//MGR - ISDN 1824
				
				///   saco el ! para que sea prospect...volver a ponerlo
				
				
				if (!esProspect) {
					String codigoGestion = "TRANSF_FACT_ELECTRONICA";
					ParametrosGestion parametrosGestion = repository.retrieve(ParametrosGestion.class, codigoGestion);
					
					//MGR - ISDN 1824
					String vendReal = "";
					
					// SB - #0004176 (volvi atras este cambio para que no salga en el release 2)
					if(isADMCreditos){
						vendReal = solicitudServicio.getVendedor().getUserReal();
					}else{
						vendReal = vendedor.getUserReal();
					}
					
					
					// SB - #0004176 (volvi atras este cambio para que no salga en el release 2)
					
//					String vendedorgenerico = String.valueOf(((GlobalParameter) globalParameterRetriever
//							.getObject(GlobalParameterIdentifier.USR_GENERICO_GEST_FA_SFA)).getValue());
					
					
					Long idGestion = generacionCierreBusinessOperator.lanzarGestionCerrarSS(
							vendReal,
							0L, 
							parametrosGestion, 
							"", 
							codigoBSCS, 
							cerrar);
					solicitudServicio.getCuenta().getFacturaElectronica().setIdGestion(idGestion);
				}
			}
			
			//MGR - #3120 - Guardo el cambio de estado y demas datos solo si la SS se cerro correctamente
			if(!response.getMessages().hasErrors()){
				
				//Req Pass y cierre automatico- Req #1: Registrar resultado veraz y scoring
				//registrar resultados veraz y scoring
				//setearle a la ss los dos nuevos valores de scoring y veraz
				if (!esProspect) {
					Long idConsultaScoring = scoring(solicitudServicio);
					solicitudServicio.setIdConsultaScoring(idConsultaScoring);
				}
				Long idConsultaVeraz = veraz(solicitudServicio);
				if (idConsultaVeraz > 0) {
					solicitudServicio.setIdConsultaVeraz(idConsultaVeraz);
				}
			     
			    //Req Pass y cierre automatico-Req #2: Registrar Cierre con Pin
			    // valores q deberan guardarse
				GlobalParameter pinValidoGlobalParameter = (GlobalParameter) globalParameterRetriever
						.getObject(GlobalParameterIdentifier.VALIDO_PIN);
				
				GlobalParameter pinNoValidoGlobalParameter = (GlobalParameter) globalParameterRetriever
						.getObject(GlobalParameterIdentifier.NO_VALIDO_PIN);
				
				if (!GenericValidator.isBlankOrNull(pinMaestro) || 
						solicitudServicio.getSolicitudServicioGeneracion().getScoringChecked()){
					solicitudServicio.setCierreConPin(pinValidoGlobalParameter.getValue());
					
		         }else{
		        	 solicitudServicio.setCierreConPin(pinNoValidoGlobalParameter.getValue());
		         }
				
				//Req Pass y cierre automatico-Req #3: Registrar fecha de cierre
				solicitudServicio.setFechaCierre(new Date());
				
				
				//Req Pass y cierre automatico- se deja guardado el cambio del estado de la ss en sfa_estado_por_solicitud
				EstadoPorSolicitud nuevoEstado= new EstadoPorSolicitud();
				nuevoEstado.setFecha(new Date());

				SolicitudServicio ss= repository.retrieve(SolicitudServicio.class, solicitudServicio.getId());
				nuevoEstado.setSolicitud(ss);
				
				Vendedor vendedor = repository.retrieve(Vendedor.class, solicitudServicio.getVendedor().getId());
				nuevoEstado.setUsuario(vendedor);

				EstadoSolicitud cerrada  =(EstadoSolicitud) knownInstanceRetriever
										.getObject(KnownInstanceIdentifier.ESTADO_CERRADA_SS);
			    nuevoEstado.setEstado(cerrada);
				
				repository.save(nuevoEstado);
				
//				larce - #3177: Cierre y Pass Automatico – Pass a Prospect
//				MGR - Se mueve la creacion de la cuenta. 
//				Se mueve esto desde la clase SolicitudRpcServiceImpl, metodo generarCerrarSolicitud
				if (!solicitudServicio.getCuenta().isTransferido() 
						&& puedeCerrar == CierreYPassResult.CIERRE_PASS_AUTOMATICO) {
					
//					MGR - Se mueve la creacion de la cuenta. Pongo la solicitud en carga hasta haber creado el cliente
//					Esto es por un bug raro que sucede si durante la creacion del cliente, el chron levanta la ss
					solicitudServicio.setEnCarga(Boolean.TRUE);
					repository.save(solicitudServicio);
					repository.flush();
					
					Long crearCliente = this.crearCliente(solicitudServicio);
					
//					MGR - Vuelvo a poner la solicitud en carga false
					solicitudServicio.setEnCarga(Boolean.FALSE);
					repository.save(solicitudServicio);

					
					if( crearCliente != 0L) {
						//si falla la creación del cliente no se da el pass de crédito y se envía un mail informando la situación
						enviarMailPassCreditos(solicitudServicio.getNumero());
						solicitudServicio.setPassCreditos(false);
					}
				}
				
				//mover inventario si se retira en sucursal
			    Vendedor vendedorSalon= solicitudServicio.getVendedor();
				if (vendedorSalon.isVendedorSalon() && solicitudServicio.getRetiraEnSucursal()) {
					List<String> mensajesMovimientoInventario = movimientoInventario(solicitudServicio);
					MessageList msgs = new MessageList();
					for (String str : mensajesMovimientoInventario) {
						MessageImpl msg = new MessageImpl();
						msg.setDescription(str);
						msgs.addMesage(msg);
					}
					response.setMessages(msgs);
					//Si no hubo errores en los movimientos de inventario se reservan los números
					if (msgs.isEmpty()) {
						reservarNumerosTelefonicosPorDespacho(solicitudServicio);
					}
				}
				
			}
		
		} else {
//			MGR - #3440 - Si no hace las mismas validaciones
			response = generacionCierreBusinessOperator.generarSolicitudServicio(generacionCierreRequest, cierraPorCC);
		}
		
		repository.save(solicitudServicio);
		
		return response;
	}

	private void setScoringChecked(SolicitudServicio solicitudServicio, String pinMaestro, boolean pinChequeadoEnNexus) {
		// Se generará por scoring si tiene PIN ingresado (EECC) o si se tildá el checkbox de scoring
		// (Dealers)
		boolean scoringChecked = solicitudServicio.getSolicitudServicioGeneracion().isCierreConPinOrScoringChecked(pinMaestro, pinChequeadoEnNexus);
		if (solicitudServicio.getSolicitudServicioGeneracion().isCierrePorCC()) {
			scoringChecked = solicitudServicio.getSolicitudServicioGeneracion().getCierrePorScoringCC();
		}
		solicitudServicio.getSolicitudServicioGeneracion().setScoringChecked(scoringChecked);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void cancelarOperacionEnCurso(OperacionEnCurso operacionEnCurso) throws BusinessException,
			NumberFormatException {
		if (operacionEnCurso.getIdSolicitudServicio() != null) {
			SolicitudServicio solicitudServicio = repository.retrieve(SolicitudServicio.class,
					operacionEnCurso.getIdSolicitudServicio());
			for (LineaSolicitudServicio linea : solicitudServicio.getLineas()) {
				if (linea.getNumeroReserva() != null && !"".equals(linea.getNumeroReserva().trim())) {
					desreservarNumeroTelefono(Long.parseLong(linea.getNumeroReserva()), null);
				}
			}
		}
		operacionEnCursoBusinessOperator.cancelarOperacionEnCurso(operacionEnCurso);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void generarChangeLog(Long idServicioDto, Long idVendedor) throws ConnectionDAOException {
		GenerarChangelogConfig generarChangelogConfig = getGenerarChangelogConfig();
		generarChangelogConfig.setIdServicio(idServicioDto);
		generarChangelogConfig.setIdVendedor(idVendedor);
		sfaConnectionDAO.execute(generarChangelogConfig);
	}

	public GenerarChangelogConfig getGenerarChangelogConfig() {
		return generarChangelogConfig;
	}

	public GeneracionCierreResponse crearArchivo(SolicitudServicio solicitudServicio, boolean enviarEmail) {
		GeneracionCierreRequest generacionCierreRequest = new GeneracionCierreRequest("",false, solicitudServicio);
		GeneracionCierreResponse response = generacionCierreBusinessOperator
				.crearArchivo(generacionCierreRequest, enviarEmail);
		return response;
	}
	
	public CreateSaveSSTransfResponse validarCreateSSTransf(SolicitudServicio solicitud){
		return solicitudesBusinessOperator.validarCreateSSTransf(solicitud);
	}
	
	//MGR - ISDN 1824
	public CreateSaveSSResponse validarPredicadosGuardarSS(SolicitudServicio solicitudServicio, Vendedor vendedor) {
		GuardarRequest guardarRequest = new GuardarRequest(solicitudServicio, vendedor);
		CreateSaveSSResponse response = guardarBusinessOperator.validarPredicadosGuardarSS(guardarRequest);
		return response;
	}
	
	//MGR - ISDN 1824
	public CreateSaveSSResponse validarCreateSS(SolicitudServicio solicitud){
		return solicitudesBusinessOperator.validarCreateSS(solicitud);
	}
	
	/**
	 * Traigo los datos del histórico de ventas de Vantive asociados al número de SS
	 * @param nss - Número de SS
	 * @return Histórico de ventas
	 * @throws ConnectionDAOException
	 */
    public List<SolicitudServicio> buscarHistoricoVentas(String nss) throws ConnectionDAOException {
    	VantiveHistoricoVentaRetrieveConfig config = this.getVantiveHistoricoVentaRetrieveConfig();
		config.setNss(nss);
		List result = (List) this.sfaConnectionDAO.execute(config);
		return result;
    }

	public VantiveHistoricoVentaRetrieveConfig getVantiveHistoricoVentaRetrieveConfig() {
        return vantiveHistoricoVentaRetrieveConfig;
	}
	
	/**
	 * Si el codigoVantive es null, lo busco en nxa_ra_customers
	 * @param nss - Número de SS
	 * @return Código vantive
	 * @throws ConnectionDAOException
	 */
	public String buscarClienteByNss(String nss) throws ConnectionDAOException {
		VantiveHistoricoClienteConfig config = this.getVantiveHistoricoClienteConfig();
		config.setNss(nss);
		String result = (String) this.sfaConnectionDAO.execute(config);
		return result;
	}
	
	public VantiveHistoricoClienteConfig getVantiveHistoricoClienteConfig() {
		return vantiveHistoricoClienteConfig;
	}
	
	public TransferirCuentaHistoricoConfig getTransferirCuentaHistoricoConfig() {
		return transferirCuentaHistoricoConfig;
	}

	/**
	 * Transfiere la cuenta y el historico a vantive
	 * @param solicitudServicio
	 * @param saveHistorico
	 * @throws RpcExceptionMessages
	 */
	public void transferirCuentaEHistorico(SolicitudServicioDto solicitudServicio, boolean saveHistorico) throws RpcExceptionMessages {
	    TransferirCuentaHistoricoConfig config = this.getTransferirCuentaHistoricoConfig();
		config.setIdCuenta(solicitudServicio.getCuenta().getId());
		config.setNss(solicitudServicio.getNumero());
		config.setFechaEstado(new java.sql.Date(solicitudServicio.getFechaEstado().getTime()));
		config.setFechaFirma(new java.sql.Date(solicitudServicio.getFechaFirma().getTime()));
		config.setCantidadEquipos(solicitudServicio.getCantidadEquiposH());			
		config.setEstado(solicitudServicio.getEstadoH().getDescripcion());
		
		if(saveHistorico){
			config.setSaveHistorico("T");
		}else{
			config.setSaveHistorico("F");
		}
		try {
			String result = (String) this.sfaConnectionDAO.execute(config);
		} catch (ConnectionDAOException e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
	}
	
	
	/**
	 * Obtengo la SolicitudServicio por id.
	 * @param ssDto
	 * @return SolicitudServicio
	 */
	public SolicitudServicio obtenerSSPorId(SolicitudServicioDto ssDto){
		SolicitudServicio solicitudServicio = repository.retrieve(SolicitudServicio.class, ssDto.getId());
		return solicitudServicio;
	}
	
	/**
	 * Obtengo la SolicitudServicio por id.
	 * @param idSS
	 * @return SolicitudServicio
	 */
	public SolicitudServicio getSSById(long idSS){
		SolicitudServicio solicitudServicio = repository.retrieve(SolicitudServicio.class, idSS);
		return solicitudServicio;
	}
	
	/**
	 * Actualizo la SolicitudServicio.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateSolicitudServicio(SolicitudServicio solicitudServicio){
		repository.update(solicitudServicio);
	}

	public ScoringCuentaLegacyDTO consultarScoring(SolicitudServicio ss) throws BusinessException {
		return generacionCierreBusinessOperator.consultarScoring(ss.getCuenta());
	}

	public VerazResponseDTO consultarVeraz(SolicitudServicio ss) throws BusinessException {
		VerazRequestDTO verazRequestDTO = createVerazRequestDTO(ss.getCuenta().getPersona());
		return generacionCierreBusinessOperator.consultarVeraz(verazRequestDTO);
	}
	
	//MGR - #3118
	public VerazResponseDTO consultarVerazCierreSS(SolicitudServicio solicitudServicio) throws BusinessException {
		VerazRequestDTO verazRequestDTO = createVerazRequestDTO(solicitudServicio.getCuenta().getPersona());
		return generacionCierreBusinessOperator.consultarVerazCierreSS(verazRequestDTO);
	}
	
	private VerazRequestDTO createVerazRequestDTO(Persona persona) {
		Sexo sexo = (Sexo) this.repository.retrieve(Sexo.class, persona.getSexo().getId());
		TipoDocumento tipoDocumento = (TipoDocumento) this.repository.retrieve(TipoDocumento.class,
				persona.getDocumento().getTipoDocumento().getId());
		long numeroDocumento = Long.parseLong(StringUtil.removeOcurrences(persona.getDocumento()
				.getNumero(), '-'));
		AppLogger.debug("Parametros consulta a Veraz: " + tipoDocumento.getCodigoVeraz() + " / "
				+ numeroDocumento + " / " + sexo.getCodigoVeraz() + "...");

		VerazRequestDTO verazRequestDTO = new VerazRequestDTO();
		verazRequestDTO.setNroDoc(numeroDocumento);
		verazRequestDTO.setSexo(sexo.getCodigoVeraz());
		verazRequestDTO.setTipoDoc(tipoDocumento.getCodigoVeraz());
		// verazRequestDTO.setVerazVersion(vendedor.getVerazVersion());
		return verazRequestDTO;
	}
	
	
	
/**
   * Consulta persona en el historico de scoring si ya existia una consulta antes de las 24 horas
   * 
   * @param tipoDoc
   * @param nroDoc
   * @param sexo
   * @return
   * @throws 
   */	
   //MGR - Se modifica el dato por que se agrego la referencia a la tabla sfa_tipo_documento
	public Long verHistoricoScoring(Long tipoDoc, String nroDoc, String sexo){
		Long resultado = new Long(0);
		PreparedStatement stmt;
		Date resul;
	    Date hoy= new Date();
	    int diasValidez = ((GlobalParameter) this.globalParameterRetriever
	            .getObject(GlobalParameterIdentifier.DIAS_CADUCACION_SCORING_SS_CERRADA)).getAsDouble().intValue();
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    
		String sql = "select fecha_validez from  SFA_SCORING_HISTORY where sexo='" +sexo+
				"' and NUMERO_DOC='" + nroDoc+ "' and TIPO_DOC='" + tipoDoc +"'";
		try {
			stmt = ((HibernateRepository) repository)
					.getHibernateDaoSupport().getSessionFactory()
					.getCurrentSession().connection().prepareStatement(sql);
			
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				resul=resultSet.getDate(1);
				long fechaInicialMs =resul.getTime();
				long fechaFinalMs = hoy.getTime();
				long diferencia = fechaFinalMs - fechaInicialMs;
				double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
				
				if (dias < diasValidez ) {
					resultado=(long) dias;
					break;
				}else{
					
					resultado= new Long(0);
				}
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
       
		return resultado;
	}	
		
		
	
	/**
	 * busca en el historial de scoring si ya habia una consulta para esta ss , sino llama a 
	 * la consulta basica de scoring y lo guarda en el historial para posibles consultas
	 * @param solicitudServicio
	 * @return
	 * @throws BusinessException
	 */
	public Long scoring(SolicitudServicio solicitudServicio) throws BusinessException{
		
        Persona persona= new Persona();
        ScoringHistoryItem item =new ScoringHistoryItem();
		try {
			persona = consultarPersona(solicitudServicio.getCuenta().getCodigoVantive());
		} catch (RpcExceptionMessages e1) {
		
			e1.printStackTrace();
		}
		Sexo sexo = (Sexo) this.repository.retrieve(Sexo.class, persona.getSexo().getId());
		TipoDocumento tipoDocumento = (TipoDocumento) this.repository.retrieve(TipoDocumento.class,
				persona.getIdTipoDocumento());
		String numeroDocumento = StringUtil.removeOcurrences(persona.getDocumento().getNumero(), '-');
		//MGR - Se modifica el dato por que se agrego la referencia a la tabla sfa_tipo_documento
		Long consultar= verHistoricoScoring(tipoDocumento.getId(),numeroDocumento,sexo.getCodigoVeraz());
		String scoring;
		
			if (consultar == 0){
				AppLogger.info("Iniciando llamada para averiguar Scoring.....");
				scoring = (String) this.consultarScoring(solicitudServicio).getScoringData();
			
				AppLogger.info("Scoring averiguado correctamente.....");
				//guardar ScoringHistorial
				item.setDatosScoring(scoring);
				item.setNroDoc(numeroDocumento);
				item.setSexo(sexo.getCodigoVeraz());
				//MGR -  Se cambia el tipo para hacer referencia a la tabla SFA_TIPO_DOCUMENTO
				item.setTipoDocumento(tipoDocumento);
				item.setValidez(new Date());
				repository.persist(item);
			}
			return new Long(item.getId());
		
		
	}
	
	
	public Persona consultarPersona(String codVantive) throws RpcExceptionMessages {
		ArrayList<Object> list = (ArrayList<Object>) this.repository.find("from Cuenta c where c.codigoVantive = ?", codVantive);
		if(!list.isEmpty()){
			Cuenta cta = (Cuenta) list.get(0);
			return cta.getPersona();
		}
		return null;
	
	}
	
	/**
	 * utiliza la consulta basica de veraz que ya existe solo que se le pasa por parametro q la valiz sea de un dia
	 * luego guarda en la tabla de historial de veraz este consulta, para que pueda ser utilizada en otro momento
	 * @param solicitudServicio
	 * @return
	 */
	public Long veraz(SolicitudServicio solicitudServicio){
		VerazRequestDTO verazRequestDTO = createVerazRequestDTO(solicitudServicio.getCuenta().getPersona());
		VerazResponseDTO verazRequestDTOGuardar =(VerazResponseDTO) generacionCierreBusinessOperator.consultarVerazCierreSS(verazRequestDTO);
		if (verazRequestDTOGuardar.getIdEstado()>0){
		ResultadoVeraz rtaVeraz= new ResultadoVeraz();
		rtaVeraz.setApellido(verazRequestDTOGuardar.getApellido());
		rtaVeraz.setEstado(verazRequestDTOGuardar.getEstado());
		rtaVeraz.setFileName(verazRequestDTOGuardar.getFileName());
		rtaVeraz.setIdEstado(verazRequestDTOGuardar.getIdEstado());
		rtaVeraz.setLote(verazRequestDTOGuardar.getLote());
		rtaVeraz.setMensaje(verazRequestDTOGuardar.getMensaje());
		rtaVeraz.setNombre(verazRequestDTOGuardar.getNombre());
		rtaVeraz.setNroDoc(verazRequestDTO.getNroDoc());
		rtaVeraz.setRangoTel(verazRequestDTOGuardar.getRangoTel());
		rtaVeraz.setScore(new Long(verazRequestDTOGuardar.getScore()));
		rtaVeraz.setScoreDni(new Long(verazRequestDTOGuardar.getScoreDni()));
		rtaVeraz.setRazonSocial(verazRequestDTOGuardar.getRazonSocial());
		rtaVeraz.setSexo(verazRequestDTO.getSexo());
		rtaVeraz.setTipoDoc(verazRequestDTO.getTipoDoc());
		repository.persist(rtaVeraz);
		return rtaVeraz.getId();
		}else{
			return new Long(0);
		}
		
	}
	
	public CaratulaTransferidaConfig getCaratulaTransferidaConfig() {
		return caratulaTransferidaConfig;
	}
	
	/**
	 * Crea la carátula de créditos.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Long crearCaratula(Cuenta cta, String nroSS, String resultadoVerazScoring) {
		AppLogger.info("##Log Cierre y pass - Se va a crear la caratula.");
		
		Caratula caratula = new Caratula();
		caratula.setNroSS(nroSS);
		if ("ACEPTAR".equals(resultadoVerazScoring)) {
			caratula.setLimiteCredito(new Double(625));
			List<RiskCode> risks = repository.find("from RiskCode r where r.descripcion = 'AP.On-Line'");
			caratula.setRiskCode(risks.get(0));
		} else if ("REVISAR".equals(resultadoVerazScoring)) {
			caratula.setLimiteCredito(new Double(375));
			List<RiskCode> risks = repository.find("from RiskCode r where r.descripcion = 'AP.On-Line'");
			caratula.setRiskCode(risks.get(0));
		} else {
			caratula.setLimiteCredito(new Double(100));
			List<RiskCode> risks = repository.find("from RiskCode r where r.descripcion = 'Lim.Acot.'");
			caratula.setRiskCode(risks.get(0));
		}
		List<Calificacion> calificaciones = repository.find("from Calificacion c where c.descripcion = 'No Aplica'");
		caratula.setCalificacion(calificaciones.get(0));		
		caratula.setEstadoVeraz(resultadoVerazScoring);
		caratula.setConfirmada(true);
		
		caratula.setAntiguedad(cta.getFechaCreacion());
		if (cta.getCaratulas().isEmpty()) {
			caratula.setDocumento("Crédito");
		} else {
			caratula.setDocumento("Anexo");
		}
		
		caratula.setCuenta(cta);
		caratula.setUsuarioCreacion(sessionContextLoader.getVendedor());
		repository.save(caratula);
		
		AppLogger.info("##Log Cierre y pass - Caratula creada correctamente.");
		return caratula.getId();
	}
	
//	MGR - Se mueve la creacion de la cuenta. Este metodo NO lanza excepcion, lo quito
	public void transferirCaratula(Long idCaratula, String numeroSS){
		CaratulaTransferidaConfig caratulaTransferidaConfig = getCaratulaTransferidaConfig();
		caratulaTransferidaConfig.setIdCaratula(idCaratula);
		try {
			AppLogger.info("##Log Cierre y pass - Transfiriendo caratula...");
			CaratulaTransferidaResultDto result = (CaratulaTransferidaResultDto) sfaConnectionDAO.execute(caratulaTransferidaConfig);
			
			if(result.getDescripcion() == null || result.getDescripcion().equals("")){
				AppLogger.info("##Log Cierre y pass - Caratula transferida correctamente.");
			}else{
				String error = result.getCodError() + ". " + result.getDescripcion();
				AppLogger.info("##Log Cierre y pass - Error al transferir la Caratula. " + error);
				throw new ConnectionDAOException(error);
			}
		} catch (ConnectionDAOException e) {
			//si hay un error mando un mail informando dicha situación y sigo con la ejecución.
			String mensajeErrorCaratula = "SS %. Hubo  un error en la generación de la caratula, por favor verificar.";
			String asunto = "SS % – Error al Generar Caratula";
			String destinatario = String.valueOf(((GlobalParameter) globalParameterRetriever
					.getObject(GlobalParameterIdentifier.MAIL_ERROR_TRANSF_CARATULA)).getValue());
			enviarMail(asunto.replaceAll("%", numeroSS), 
					destinatario.split(","), mensajeErrorCaratula.replaceAll("%", numeroSS));
		}
	}
	
	public InsertUpdateCuentaConfig getInsertUpdateCuentaConfig() {
		return insertUpdateCuentaConfig;
	}
	
	/**
	 * 
	 * @param idCuenta
	 * @return 0L Si el cliente de creo correctamente, otro valor en caso contrario
	 */
//	MGR - Se mueve la creacion de la cuenta. Este metodo NO lanza excepcion, lo quito
	@Transactional(readOnly = false, propagation= Propagation.REQUIRED)
	public Long crearCliente(SolicitudServicio solicitud){
//		MGR - Primero pongo todos los datos 'en_carga = F'
		solicitud.getCuenta().getCuentaRaiz().descargar();
		repository.save(solicitud.getCuenta());
		repository.flush(); //Es necesario que este cambio se refleje en la base antes de crear el cliente
		
		Long idCuenta = solicitud.getCuenta().getId();
		Long codError = prospectToCliente(idCuenta);
		
//		MGR - Actualizo la cuenta ya que la creacion del cliente la modifico
		Cuenta cta = repository.retrieve(Cuenta.class, idCuenta);
		repository.refresh(cta);
		
		return codError;
	}
	
	private Long prospectToCliente(Long idCuenta){
		AppLogger.info("Se procede a transformar el prospect en cliente.");
		PreparedStatement stmt = null;
		CallableStatement cstmt = null;
		Long codError = -1l;
		try {
			// MGR - Para que pueda transformar a cliente, debo setearle el lenguaje a la base
			Connection connection = ((HibernateRepository) repository)
					.getHibernateDaoSupport().getSessionFactory()
					.getCurrentSession().connection();
			stmt = connection
					.prepareStatement(
							"BEGIN EXECUTE IMMEDIATE 'ALTER SESSION SET NLS_LANGUAGE =''Latin American Spanish'''; END;");
			stmt.executeQuery();
			
			cstmt = connection
					.prepareCall(
							"call SFA_INSERT_UPDATE_CUENTA(?,?,?)");
			cstmt.setLong(1, idCuenta);
			cstmt.registerOutParameter(2, java.sql.Types.NUMERIC);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.execute();
			
			codError = cstmt.getLong(2);
			if (codError == 0L) {
				AppLogger.info("#Log Cierre y pass - Cliente creado correctamente.");
			} else {
				String error = codError + ". "
						+ cstmt.getString(3);
				AppLogger.info("#Log Cierre y pass - Hubo un problema al crear al cliente. "
								+ error);
				throw new ConnectionDAOException(error);
			}
			
		} catch (Exception e) {
			AppLogger.info("##Log Cierre y pass - Error al transformar el prospect en cliente.");
			AppLogger.error(e);
		}finally{
			if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException sqlEx) {}
		        stmt = null;
		    }
			
			if (cstmt != null) {
		        try {
		        	cstmt.close();
		        } catch (SQLException sqlEx) {}
		        cstmt = null;
		    }
			
		}
		return codError;
	}

	public void enviarMail(String asunto, String[] to, String mensaje) {
		generacionCierreBusinessOperator.enviarMail(asunto, to, mensaje);
	}
	
//	MGR - Se mueve la creacion de la cuenta, es necesario mover este metodo y hacerlo publico
	public void enviarMailPassCreditos(String numeroSS) {
		String destinatario = String.valueOf(((GlobalParameter) globalParameterRetriever
				.getObject(GlobalParameterIdentifier.MAIL_ERROR_CREAR_CTA)).getValue());
		String asunto = String.valueOf(((GlobalParameter) globalParameterRetriever
				.getObject(GlobalParameterIdentifier.ASUNTO_ERROR_CREAR_CTA)).getValue());
		this.enviarMail(asunto.replaceAll("%", numeroSS), 
				destinatario.split(","), MENSAJE_ERROR_CREAR_CUENTA.replaceAll("%", numeroSS));
	}


//	MGR - #3464 - Ya no es necesario
//	public List<ServicioAdicionalLineaSolicitudServicio> getServicioAdicionalLineaIncluidosNoVisibles(
//			Vendedor vendedor, LineaSolicitudServicio linea) {
//		
//		List<ServicioAdicionalLineaSolicitudServicio> lineasSSAA = 
//						new ArrayList<ServicioAdicionalLineaSolicitudServicio>();
//		Item item = null;
//		
//		if( (linea.isActivacion() || linea.isActivacionOnline()) 
//				&& linea.getModelo() != null){
//			List<Item> listItems = repository.executeCustomQuery(namedQueryItemParaActivacionOnline, linea.getModelo().getId());
//		
//			if(!listItems.isEmpty()){
//				item = listItems.get(0);
//			}
//		}else{
//			item = linea.getItem();
//		}
//		
//		if(item != null){
//			lineasSSAA = linea.getPlan()
//				.getServicioAdicionalLineaIncluidosNoVisibles(item,	vendedor, linea);
//			
//		}
//		
//		return lineasSSAA;
//	}
	
//	MGR - Parche de emergencia
	public int sonConfigurablesPorAPG(List<LineaSolicitudServicio> lineas) {
		AppLogger.info("#Log Cierre y pass - Validando que todas las líneas sean configurables por APG...");
		int cumple = 0;
		int cantidadLineasParaValidar = 0; 
		TipoVendedor tipoVendedor = sessionContextLoader.getVendedor().getTipoVendedor();
		for (Iterator<LineaSolicitudServicio> iterator = lineas.iterator(); iterator.hasNext();) {
			LineaSolicitudServicio linea = (LineaSolicitudServicio) iterator.next();
			// #6487 los accesorios no deben contabilizarse como linea para validacion			
			if (!linea.isAccesorio()){
				cantidadLineasParaValidar++;
				if (linea.getTipoSolicitud() != null && linea.getPlan() != null && linea.getItem() != null) {
					List result = repository.executeCustomQuery("configurablePorAPG", tipoVendedor.getId(), linea.getTipoSolicitud().getId());  
					if (result.size() > 0) {
						cumple++;
					}
				}
			}
		}
		if (cumple == cantidadLineasParaValidar) {
			AppLogger.info("#Log Cierre y pass - Todas las l�neas son configurables por APG");
			return CierreYPassResult.CIERRE_PASS_AUTOMATICO;
		} else if ((cumple < cantidadLineasParaValidar && cumple != 0) ) {
			AppLogger.info("#Log Cierre y pass - No todas las l�neas son configurables por APG");
			return CierreYPassResult.LINEAS_NO_CUMPLEN_CC;
		} else {
			AppLogger.info("#Log Cierre y pass - Ninguna l�nea es configurable por APG");			
			return CierreYPassResult.CIERRE_NORMAL;
		}
	}
	
//	MGR - Parche de emergencia
	public Integer calcularCantEquipos(Collection<LineaSolicitudServicio> lineaSS){
		int cantEquipos = 0;
		for (Iterator iterator = lineaSS.iterator(); iterator.hasNext();) {
			LineaSolicitudServicio lineaSolicitudServicio = (LineaSolicitudServicio) iterator
					.next();
			cantEquipos = cantEquipos + Integer.parseInt(repository.executeCustomQuery(CANTIDAD_EQUIPOS, lineaSolicitudServicio.getItem().getId()).get(0).toString());
		}
		
		return cantEquipos;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public SolicitudServicio saveSolicitudServicio(SolicitudServicio ss){
		repository.save(ss);
		return ss;
	}
	
	public List<SubsidiosDto> getSubsidiosPorItem(VendedorDto vendedor,Long idItem) {

		List<SubsidiosDto> subsidios = new ArrayList<SubsidiosDto>();
		Long idTipoVendedor = sessionContextLoader.getVendedor().getTipoVendedor().getId();
		if(asignarComboVendedor()){
			idTipoVendedor = vendedor.getTipoVendedor().getId();
		}
		
		if (idTipoVendedor==null || idItem==null){
			return subsidios;
		}
		
		List<Object[]> list = repository.executeCustomQuery(GET_SUBSIDIO_ITEM_VENDEDOR,
							idTipoVendedor, idItem);
		
		for (Object[] result : list) {
			SubsidiosDto subsidio = new SubsidiosDto();
			subsidio.setIdPlan((Long)result[0]);
			if (result[1]==null){
				subsidio.setSubsidio(0d);
			}else{
				subsidio.setSubsidio((Double)result[1]);
			}
			subsidios.add(subsidio);
		}
		
		return subsidios;
	}

    private boolean asignarComboVendedor() {
    	
		HashMap<String, Boolean> mapaPermisosClient = (HashMap<String, Boolean>) sessionContextLoader.getSessionContext().get(SessionContext.PERMISOS);
		boolean verComboVendedor = (Boolean) mapaPermisosClient.get(PermisosEnum.VER_COMBO_VENDEDOR.getValue());
		
		if (verComboVendedor && sessionContextLoader.getVendedor().isADMCreditos()){
			return true;
		}
		return false;
	}


	/**
     * Se valida que el/los numeros de sim/imei ingresados pertenezcan al
     * inventario/subinventario del usuario que realizo el ingreso de la
     * solicitud (solo si es vendedor de salon)
     *
     * @param solicitudServicio a validar
     * @return retorna lista de mensajes con errores si es que hubiese o vacia
     * en caso contrario
     */
    public List<String> validarSIM_IMEI(SolicitudServicio solicitudServicio) {
    	List<String> mensajes = this.despachoSolicitudBusinessOperator.validarSIM_IMEI(solicitudServicio);
		return mensajes;
    }

	public List<String> movimientoInventario(SolicitudServicio ss){
		return this.despachoSolicitudBusinessOperator.movimientoInventario(ss);
	};

	public void reservarNumerosTelefonicosPorDespacho(SolicitudServicio ss){
		this.reservaNumeroTelefonoBusinessOperator.reservarNumerosTelefonosPorDespacho(ss);
	};
	
	
//	MGR - Validaciones previas a la facturacion
	public List<Message> validarParaFacturar(SolicitudServicio solicitudServicio,
			int puedeCerrar){
		
		boolean cierraPorCC = puedeCerrar != CierreYPassResult.CIERRE_NORMAL;
		
		setScoringChecked(solicitudServicio, null, false);
		// La SS se está invocando desde Objeto B (Interfaz web)
		solicitudServicio.setSourceModule("B");

		return generacionCierreBusinessOperator.validarParaFacturar(solicitudServicio, cierraPorCC);
	}
}

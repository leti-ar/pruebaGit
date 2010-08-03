package ar.com.nextel.sfa.server.businessservice;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.nextel.business.cuentas.facturaelectronica.FacturaElectronicaService;
import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.financial.dto.EncabezadoCreditoDTO;
import ar.com.nextel.business.legacy.financial.exception.FinancialSystemException;
import ar.com.nextel.business.oportunidades.OperacionEnCursoBusinessOperator;
import ar.com.nextel.business.personas.reservaNumeroTelefono.ReservaNumeroTelefonoBusinessOperator;
import ar.com.nextel.business.personas.reservaNumeroTelefono.result.ReservaNumeroTelefonoBusinessResult;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.generacionCierre.GeneracionCierreBusinessOperator;
import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreRequest;
import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreResponse;
import ar.com.nextel.business.solicitudes.provider.SolicitudServicioProviderResult;
import ar.com.nextel.business.solicitudes.repository.GenerarChangelogConfig;
import ar.com.nextel.business.solicitudes.repository.SolicitudServicioRepository;
import ar.com.nextel.components.accessMode.AccessAuthorization;
import ar.com.nextel.framework.connectionDAO.ConnectionDAOException;
import ar.com.nextel.framework.connectionDAO.TransactionConnectionDAO;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.EstadoCreditoFidelizacion;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.OperacionEnCurso;
import ar.com.nextel.model.personas.beans.Domicilio;
import ar.com.nextel.model.solicitudes.beans.Item;
import ar.com.nextel.model.solicitudes.beans.LineaSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.Plan;
import ar.com.nextel.model.solicitudes.beans.ServicioAdicionalLineaSolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.TipoSolicitud;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.dto.CuentaSSDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;

@Service
public class SolicitudBusinessService {

	private SolicitudServicioBusinessOperator solicitudesBusinessOperator;
	private ReservaNumeroTelefonoBusinessOperator reservaNumeroTelefonoBusinessOperator;
	private GeneracionCierreBusinessOperator generacionCierreBusinessOperator;
	private OperacionEnCursoBusinessOperator operacionEnCursoBusinessOperator;
	private FinancialSystem financialSystem;
	private SessionContextLoader sessionContextLoader;
	private Repository repository;
	private SolicitudServicioRepository solicitudServicioRepository;
	private final String CUENTA_FILTRADA = "Acceso denegado. No puede operar con esta cuenta.";
	private TransactionConnectionDAO sfaConnectionDAO;
	private GenerarChangelogConfig generarChangelogConfig;
	private FacturaElectronicaService facturaElectronicaService;

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
			if (!solicitud.getCuenta().isLockedForAnyone() || solicitud.getCuenta().isLockedFor(vendedor)) {
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
				serviciosAdicionales = solicitudServicioRepository.getServiciosAdicionales(tipoSolicitud
						.getId(), plan.getId(), item.getId(), cuenta.getId());
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
	public SolicitudServicio saveSolicitudServicio(SolicitudServicioDto solicitudServicioDto,
			MapperExtended mapper) {
		SolicitudServicio solicitudServicio = repository.retrieve(SolicitudServicio.class,
				solicitudServicioDto.getId());
		solicitudServicio.setDomicilioEnvio(null);
		solicitudServicio.setDomicilioFacturacion(null);
		mapper.map(solicitudServicioDto, solicitudServicio);

		// Estas lineas son por un problema no identificado, que hace que al querer guardar el tipo de tarjeta
		// detecte que el objeto no está attachado a la session
		if (solicitudServicio.getCuenta().getDatosPago() instanceof DatosDebitoTarjetaCredito) {
			DatosDebitoTarjetaCredito datosPago = (DatosDebitoTarjetaCredito) solicitudServicio.getCuenta()
					.getDatosPago();
			datosPago.setTipoTarjeta(repository.retrieve(TipoTarjeta.class, datosPago.getTipoTarjeta()
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
		}
		// esto limpia los ids negativos temporales
		for (Domicilio domicilio : solicitudServicio.getCuenta().getPersona().getDomicilios()) {
			if (domicilio.getId() != null && domicilio.getId() < 0) {
				domicilio.setId(null);
			}
		}
		repository.save(solicitudServicio);
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public GeneracionCierreResponse generarCerrarSolicitud(SolicitudServicio solicitudServicio,
			String pinMaestro, boolean cerrar) {

		if (!GenericValidator.isBlankOrNull(pinMaestro) && solicitudServicio.getCuenta().isEnCarga()) {
			solicitudServicio.getCuenta().setPinMaestro(pinMaestro);
		} else {
			setScoringChecked(solicitudServicio, pinMaestro);
		}
		if (cerrar) {
			// La SS se está invocando desde Objeto B (Interfaz web)
			solicitudServicio.setSourceModule("B");
		}
		GeneracionCierreRequest generacionCierreRequest = new GeneracionCierreRequest(pinMaestro,
				solicitudServicio);
		GeneracionCierreResponse response = null;
		if (cerrar) {
			response = generacionCierreBusinessOperator.cerrarSolicitudServicio(generacionCierreRequest);
		} else {
			response = generacionCierreBusinessOperator.generarSolicitudServicio(generacionCierreRequest);
		}
		repository.save(solicitudServicio);
		if (solicitudServicio.getCuenta().getFacturaElectronica() != null)
			facturaElectronicaService.adherirFacturaElectronica(solicitudServicio.getCuenta().getId(),
					solicitudServicio.getCuenta().getCodigoVantive(), solicitudServicio.getCuenta()
							.getFacturaElectronica().getEmail(), "", solicitudServicio.getVendedor()
							.getUserName());

		return response;
	}

	private void setScoringChecked(SolicitudServicio solicitudServicio, String pinMaestro) {
		Boolean pinNotEmpty = !GenericValidator.isBlankOrNull(pinMaestro);
		// Se generará por scoring si tiene PIN ingresado (EECC) o si se tildá el checkbox de scoring
		// (Dealers)
		boolean scoringChecked = pinNotEmpty
				|| solicitudServicio.getSolicitudServicioGeneracion().getScoringChecked().booleanValue();

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
}

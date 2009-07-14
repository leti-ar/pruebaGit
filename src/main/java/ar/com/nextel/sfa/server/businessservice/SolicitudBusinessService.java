package ar.com.nextel.sfa.server.businessservice;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.financial.dto.EncabezadoCreditoDTO;
import ar.com.nextel.business.legacy.financial.exception.FinancialSystemException;
import ar.com.nextel.business.personas.reservaNumeroTelefono.ReservaNumeroTelefonoBusinessOperator;
import ar.com.nextel.business.personas.reservaNumeroTelefono.result.ReservaNumeroTelefonoBusinessResult;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.generacionCierre.GeneracionCierreBusinessOperator;
import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreRequest;
import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreResponse;
import ar.com.nextel.business.solicitudes.provider.SolicitudServicioProviderResult;
import ar.com.nextel.components.accessMode.AccessAuthorization;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.EstadoCreditoFidelizacion;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.personas.beans.Domicilio;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.server.util.MapperExtended;

@Service
public class SolicitudBusinessService {

	private SolicitudServicioBusinessOperator solicitudesBusinessOperator;
	private ReservaNumeroTelefonoBusinessOperator reservaNumeroTelefonoBusinessOperator;
	private GeneracionCierreBusinessOperator generacionCierreBusinessOperator;
	private FinancialSystem financialSystem;
	private SessionContextLoader sessionContextLoader;
	private Repository repository;
	private final String CUENTA_FILTRADA = "Acceso denegado. No puede operar con esta cuenta.";

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
	public void setRepository(@Qualifier("repository") Repository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SolicitudServicio createSolicitudServicio(SolicitudServicioRequest solicitudServicioRequest)
			throws BusinessException, FinancialSystemException {

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

		solicitud.consultarCuentaPotencial();
		if (providerResult.wasCreationNeeded()) {
			if (!solicitud.getCuenta().isLockedForAnyone() || solicitud.getCuenta().isLockedFor(vendedor)) {
				solicitud.getCuenta().iniciarOperacion(vendedor);
			}
			repository.save(solicitud);
		}

		return solicitud;
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
	public void desreservarNumeroTelefono(long numero) throws BusinessException {
		reservaNumeroTelefonoBusinessOperator.desreservarNumeroTelefono(numero, sessionContextLoader
				.getVendedor());

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public GeneracionCierreResponse generarCerrarSolicitud(SolicitudServicio solicitudServicio,
			String pinMaestro, boolean cerrar) {

		setScoringChecked(solicitudServicio, pinMaestro);
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
}

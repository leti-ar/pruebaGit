package ar.com.nextel.sfa.server.businessservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.financial.dto.EncabezadoCreditoDTO;
import ar.com.nextel.business.legacy.financial.exception.FinancialSystemException;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.provider.SolicitudServicioProviderResult;
import ar.com.nextel.components.accessMode.AccessAuthorization;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.EstadoCreditoFidelizacion;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;

@Service
public class SolicitudBusinessService {

	private SolicitudServicioBusinessOperator solicitudesBusinessOperator;
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
	public void setFinancialSystem(FinancialSystem financialSystemBean) {
		this.financialSystem = financialSystemBean;
	}

	@Autowired	
	public void setSessionContextLoader(SessionContextLoader sessionContextLoader) {
		this.sessionContextLoader = sessionContextLoader;
	}
	
	@Autowired
	public void setRepository(@Qualifier("repository")Repository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SolicitudServicio createSolicitudServicio(SolicitudServicioRequest solicitudServicioRequest) throws BusinessException {

		SolicitudServicioProviderResult providerResult = null;
			providerResult = this.solicitudesBusinessOperator
					.provideSolicitudServicio(solicitudServicioRequest);

		SolicitudServicio solicitud = providerResult.getSolicitudServicio();

		addCreditoFidelizacion(solicitud);

		AccessAuthorization accessAuthorization = this.solicitudesBusinessOperator
				.calculateAccessAuthorization(providerResult.getSolicitudServicio());

		if (!accessAuthorization.hasSamePermissionsAs(AccessAuthorization.fullAccess())) {
			accessAuthorization.setReasonPrefix(CUENTA_FILTRADA);
			throw new BusinessException(null, accessAuthorization.getReason());
		}

		// sessContext = (SessionContextLoader) context
		// .getBean("sessionContextLoader");;
		// RegistroVendedores registroVendedores;
		// Usuario usuario = new Usuario();
		// usuario.setUserName("acsa1");
		// sessContext.getSessionContext().setVendedor(registroVendedores.getVendedor(usuario));

		// Logica de negocio. Buscar lugar mas apto para ponerlo.
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

	private void addCreditoFidelizacion(SolicitudServicio solicitudServicio) {
		String numeroCuenta = solicitudServicio.getCuenta().getCodigoVantive();
		try {
			EncabezadoCreditoDTO creditoFidelizacion = getCreditoFidelizacion(numeroCuenta);
			EstadoCreditoFidelizacion estadoCreditoFidelizacion = new EstadoCreditoFidelizacion(
					solicitudServicio.getCuenta(), creditoFidelizacion.getMonto().doubleValue(),
					creditoFidelizacion.getFechaVencimiento());

			solicitudServicio.getCuenta().setEstadoCreditoFidelizacion(estadoCreditoFidelizacion);
		} catch (FinancialSystemException e) {
			e.printStackTrace();
		}
	}

	private EncabezadoCreditoDTO getCreditoFidelizacion(String numeroCuenta) throws FinancialSystemException {
		EncabezadoCreditoDTO retrieveEncabezadoCreditoFidelizacion = financialSystem
				.retrieveEncabezadoCreditoFidelizacion(numeroCuenta);
		return retrieveEncabezadoCreditoFidelizacion;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SolicitudServicio saveSolicitudServicio(SolicitudServicio solicitudServicio){
		repository.save(solicitudServicio);
		return solicitudServicio;
	}
}

package ar.com.nextel.sfa.server;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.financial.dto.EncabezadoCreditoDTO;
import ar.com.nextel.business.legacy.financial.exception.FinancialSystemException;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.provider.SolicitudServicioProviderResult;
import ar.com.nextel.components.accessMode.AccessAuthorization;
import ar.com.nextel.components.accessMode.AccessPermission;
import ar.com.nextel.model.cuentas.beans.EstadoCreditoFidelizacion;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SolicitudRpcServiceImpl extends RemoteServiceServlet implements SolicitudRpcService {

	private MapperExtended mapper;
	private WebApplicationContext context;
	private SolicitudServicioBusinessOperator solicitudesBusinessOperator;
	private final String CUENTA_FILTRADA = "Acceso denegado. No puede operar con esta cuenta.";
	private FinancialSystem financialSystem;

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		solicitudesBusinessOperator = (SolicitudServicioBusinessOperator) context
				.getBean("solicitudesBusinessOperator");
		// financialSystem
	}

	public SolicitudServicioDto createSolicitudServicio (
			SolicitudServicioRequestDto solicitudServicioRequestDto) throws RpcExceptionMessages {
		SolicitudServicioRequest request = (SolicitudServicioRequest) mapper
				.mapFromDto(solicitudServicioRequestDto);
		AppLogger.info("Creando Solicitud de Servicio con Request -> " + request.toString());

		SolicitudServicioProviderResult providerResult = null;
		try {
			providerResult = this.solicitudesBusinessOperator.provideSolicitudServicio(request);
		} catch (BusinessException e) {
			throw ExceptionUtil.wrap(e);
		}

		addCreditoFidelizacion(providerResult.getSolicitudServicio());

		AccessAuthorization accessAuthorization = this.solicitudesBusinessOperator
				.calculateAccessAuthorization(providerResult.getSolicitudServicio());

		if (!accessAuthorization.hasSamePermissionsAs(AccessAuthorization.fullAccess())) {
			accessAuthorization.setReasonPrefix(CUENTA_FILTRADA);
			throw new RpcExceptionMessages(accessAuthorization.getReason());
		}

		SolicitudServicioDto solicitudServicioDto = mapper.map(providerResult.getSolicitudServicio(),
				SolicitudServicioDto.class);

		AppLogger.info("Creacion de Solicitud de Servicio finalizada");
		return solicitudServicioDto;
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
}

package ar.com.nextel.sfa.server;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.solicitudes.beans.OrigenSolicitud;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.model.solicitudes.beans.TipoAnticipo;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.nextel.sfa.server.businessservice.SolicitudBusinessService;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

public class SolicitudRpcServiceImpl extends RemoteService implements SolicitudRpcService {

	private MapperExtended mapper;
	private WebApplicationContext context;
	// private SolicitudServicioBusinessOperator solicitudesBusinessOperator;

	private SolicitudBusinessService solicitudBusinessService;
	private Repository repository;

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		// solicitudesBusinessOperator = (SolicitudServicioBusinessOperator) context
		// .getBean("solicitudServicioBusinessOperatorBean");
		solicitudBusinessService = (SolicitudBusinessService) context.getBean("solicitudBusinessService");
		repository = (Repository) context.getBean("repository");
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

	public SolicitudInitializer getSolicitudInitializer() {
		SolicitudInitializer initializer = new SolicitudInitializer();
		initializer.setOrigenesSolicitud(mapper.convertList(repository.getAll(OrigenSolicitud.class),
				OrigenSolicitudDto.class));
		initializer.setTiposAnticipo(mapper.convertList(repository.getAll(TipoAnticipo.class),
				TipoAnticipoDto.class));
		return initializer;
	}

	public SolicitudServicioDto saveSolicituServicio(SolicitudServicioDto solicitudServicioDto) {
		return solicitudServicioDto;
	}
}

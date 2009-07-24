package ar.com.nextel.sfa.server;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.OperacionEnCurso;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.sfa.client.OperacionesRpcService;
import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaResultDto;
import ar.com.nextel.sfa.server.businessservice.SolicitudBusinessService;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

/**
 * @author esalvador
 */

public class OperacionesRpcServiceImpl extends RemoteService implements OperacionesRpcService {

	private WebApplicationContext context;
	private SessionContextLoader sessionContextLoader;
	private SolicitudBusinessService solicitudBusinessService;
	private MapperExtended mapper;
	private Repository repository;

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		sessionContextLoader = (SessionContextLoader) context.getBean("sessionContextLoader");
		solicitudBusinessService = (SolicitudBusinessService) context.getBean("solicitudBusinessService");
		mapper = (MapperExtended) context.getBean("dozerMapper");
		repository = (Repository) context.getBean("repository");
	}

	public List<OperacionEnCursoDto> searchOpEnCurso() {
		Vendedor vendedor = sessionContextLoader.getVendedor();
		AppLogger.info("Obteniendo operaciones en curso para vendedor: " + vendedor.getUserName(), this);
		List<OperacionEnCursoDto> operacionesEnCursoDto = mapper.convertList(
				vendedor.getOperacionesEnCurso(), OperacionEnCursoDto.class);
		return operacionesEnCursoDto;
	}

	public VentaPotencialVistaResultDto searchReservas() {
		Vendedor vendedor = sessionContextLoader.getVendedor();
		AppLogger.info("Obteniendo reservas para vendedor: " + vendedor.getUserName(), this);
		List<VentaPotencialVistaDto> ventasPotencialesEnCursoDto = mapper.convertList(vendedor
				.getVentasPotencialesVistaEnCurso(), VentaPotencialVistaDto.class);
		VentaPotencialVistaResultDto ventaPotencialVistaResultDto = new VentaPotencialVistaResultDto(
				ventasPotencialesEnCursoDto, vendedor
						.getCantidadCuentasPotencialesNoConsultadasActivasYVigentes().toString());
		return ventaPotencialVistaResultDto;
	}

	// private PersonaDto mapeoPersona(CuentaPotencial cuentaPotencial) {
	// PersonaDto personaDto = new PersonaDto();
	// personaDto.setRazonSocial(cuentaPotencial.getPersona().getRazonSocial());
	// //Set<Telefono> tel = reserva.getPersona().getTelefonos();
	// //Set<TelefonoDto> telDto = new HashSet<TelefonoDto>();
	// //telDto.add((TelefonoDto)tel);
	// //personaDto.setTelefonos(telDto);
	// return personaDto;
	// }

	public void cancelarOperacionEnCurso(String idOperacionEnCurso) throws RpcExceptionMessages {
		try {
			OperacionEnCurso operacionEnCurso = repository.retrieve(OperacionEnCurso.class,
					idOperacionEnCurso);
			solicitudBusinessService.cancelarOperacionEnCurso(operacionEnCurso);
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
	}

}

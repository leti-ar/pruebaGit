package ar.com.nextel.sfa.server;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.dao.GenericDao;
import ar.com.nextel.business.describable.GetAllBusinessOperator;
import ar.com.nextel.business.oportunidades.search.SearchOportunidadBusinessOperator;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.CuentaPotencial;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.sfa.client.OperacionesRpcService;
import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaDto;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.server.RemoteService;

/**
 * @author esalvador
 */


public class OperacionesRpcServiceImpl extends RemoteService implements OperacionesRpcService{

	private WebApplicationContext context;
	private SessionContextLoader sessionContextLoader;
	private RegistroVendedores registroVendedores;
	private SearchOportunidadBusinessOperator searchOportunidadBusinessOperator;
	private MapperExtended mapper;
	private GetAllBusinessOperator getAllBusinessOperator;
	private GenericDao genericDao;
 	private Repository repository;

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
		searchOportunidadBusinessOperator = (SearchOportunidadBusinessOperator) context.getBean("searchOportunidadBusinessOperatorBean");
		sessionContextLoader = (SessionContextLoader) context.getBean("sessionContextLoader");
		mapper = (MapperExtended) context.getBean("dozerMapper");
		genericDao  = (GenericDao) context.getBean("genericDao");
		repository = (Repository) context.getBean("repository");
	}

	public List<OperacionEnCursoDto> searchOpEnCurso() {
		Vendedor vendedor = sessionContextLoader.getVendedor();
		AppLogger.info("Obteniendo operaciones en curso para vendedor: " + vendedor.getUserName(), this);
		List<OperacionEnCursoDto> operacionesEnCursoDto = mapper.convertList(vendedor.getOperacionesEnCurso(), OperacionEnCursoDto.class);
		return operacionesEnCursoDto;
	}

	public List<VentaPotencialVistaDto> searchReservas() {
		Vendedor vendedor = sessionContextLoader.getVendedor();
		AppLogger.info("Obteniendo reservas para vendedor: " + vendedor.getUserName(), this);
		List<VentaPotencialVistaDto> ventasPotencialesEnCursoDto = mapper.convertList(vendedor.getVentasPotencialesVistaEnCurso(), VentaPotencialVistaDto.class);
		return ventasPotencialesEnCursoDto;
	}
	
	private PersonaDto mapeoPersona(CuentaPotencial cuentaPotencial) {
		PersonaDto personaDto = new PersonaDto();
		personaDto.setRazonSocial(cuentaPotencial.getPersona().getRazonSocial());
		//Set<Telefono> tel = reserva.getPersona().getTelefonos();
		//Set<TelefonoDto> telDto = new HashSet<TelefonoDto>();
		//telDto.add((TelefonoDto)tel);
		//personaDto.setTelefonos(telDto);
		return personaDto;
	}
	
}

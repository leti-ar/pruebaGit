package ar.com.nextel.sfa.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.dao.GenericDao;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.search.dto.SolicitudServicioCerradaSearchCriteria;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.solicitudes.beans.EstadoSolicitud;
import ar.com.nextel.model.solicitudes.beans.OrigenSolicitud;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;

import ar.com.nextel.model.solicitudes.beans.TipoAnticipo;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
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
	
	private RegistroVendedores registroVendedores;
	private GenericDao genericDao;
	
    private SolicitudServicioBusinessOperator solicitudesBusinessOperator;


	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		solicitudesBusinessOperator = (SolicitudServicioBusinessOperator) context.getBean("solicitudServicioBusinessOperatorBean");
		// solicitudesBusinessOperator = (SolicitudServicioBusinessOperator) context
		// .getBean("solicitudServicioBusinessOperatorBean");
		solicitudBusinessService = (SolicitudBusinessService) context.getBean("solicitudBusinessService");
		
		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
		genericDao = (GenericDao) context.getBean("genericDao");
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
	
	public List<SolicitudServicioCerradaResultDto> searchSSCerrada(SolicitudServicioCerradaDto solicitudServicioCerradaDto) {
		AppLogger.info("Iniciando busqueda de SS cerradas...");
		SolicitudServicioCerradaSearchCriteria solicitudServicioCerradaSearchCriteria = mapper.map(solicitudServicioCerradaDto, SolicitudServicioCerradaSearchCriteria.class);
		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Vendedor vendedor = registroVendedores.getVendedor(usuario);
		solicitudServicioCerradaSearchCriteria.setVendedor(vendedor);
		List<SolicitudServicio> list = null;
		try {
			list = this.solicitudesBusinessOperator.searchSolicitudesServicioHistoricas(solicitudServicioCerradaSearchCriteria);
		} catch (Exception e) {
			AppLogger.info("Error buscando Solicitudes de Servicio cerradas: " + e.getMessage());
		}
		
		List result = mapper.convertList(list, SolicitudServicioCerradaResultDto.class, "ssCerradaResult");
		AppLogger.info("Busqueda de SS cerradas finalizada...");		
		return result;
	}
	
	public BuscarSSCerradasInitializer getBuscarSSCerradasInitializer() {
		BuscarSSCerradasInitializer buscarSSCerradasInitializer = new BuscarSSCerradasInitializer();
					
		List<String> listaResult = new ArrayList<String>();
		String cantResult = "10;25;50;75;100";
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

		buscarSSCerradasInitializer.setOpcionesEstado(mapper.convertList(genericDao.getList(EstadoSolicitud.class), EstadoSolicitudDto.class));
		
		return buscarSSCerradasInitializer;
	}
	
	
	
	/**
	 * @author julioVesco TODO: Quitar el HardCode cuando se logre obtener los
	 *         datos reales de la Db.
	 **/
//	public SolicitudesServicioTotalesDto searchSSCerrada(
//			SolicitudServicioSearchDto solicitudServicioSearchDto) {
//		List dtoResult = new ArrayList();
//
//		for (int i = 0; i < 100; i++) {
//			createSSMock(dtoResult, 2L, 1, RandomUtils.nextBoolean(), Long
//					.valueOf(RandomUtils.nextLong()).toString(), "" + i, "0",
//					"Razon Social " + i, "Comentario " + i, "Estado " + i);
//		}
//
//		SolicitudesServicioTotalesDto solicitudes = new SolicitudesServicioTotalesDto();
//		solicitudes.setSolicitudes(dtoResult);
//		solicitudes.setTotalEquipos(10L);
//		solicitudes.setTotalPataconex(20.00);
//		solicitudes.setTotalEquiposFirmados(3L);
//
//		return solicitudes;
//	}
	
	
	/**
	 * @author julioVesco TODO: Este metodo se eliminaria cuando se logren obtener los datos da la base.
	 **/
//	private void createSSMock(List dtoResult, long cantEquipos,
//			int cantResultados, boolean firmas, String nroCuenta, String nroSS,
//			String pataconex, String razonSocial, String comentario,
//			String estado) {
//		SolicitudServicioCerradaDto solicitudServicioDto = new SolicitudServicioCerradaDto();
//		solicitudServicioDto.setCantidadEquipos(cantEquipos);
//		solicitudServicioDto.setCantidadResultados(cantResultados);
//		solicitudServicioDto.setFirmas(firmas);
//		solicitudServicioDto.setNumeroCuenta(nroCuenta);
//		solicitudServicioDto.setNumeroSS(nroSS);
//		solicitudServicioDto.setPataconex(pataconex);
//		solicitudServicioDto.setRazonSocial(razonSocial);
//		
//		List cambios = new ArrayList<CambiosSolicitudServicioDto>();
//		for (int i = 0; i < 15; i++) {
//			CambiosSolicitudServicioDto cambiosSolicitudServicioDto = new CambiosSolicitudServicioDto();
//			cambiosSolicitudServicioDto.setComentario(comentario);
//			cambiosSolicitudServicioDto.setEstado(estado);
//			cambiosSolicitudServicioDto.setFecha(new Date());
//			cambios.add(cambiosSolicitudServicioDto);
//		}
//		solicitudServicioDto.setCambios(cambios);
//		dtoResult.add(solicitudServicioDto);
//	}

	

	public SolicitudInitializer getSolicitudInitializer() {
		SolicitudInitializer initializer = new SolicitudInitializer();
		initializer.setOrigenesSolicitud(mapper.convertList(repository.getAll(OrigenSolicitud.class),
				OrigenSolicitudDto.class));
		initializer.setTiposAnticipo(mapper.convertList(repository.getAll(TipoAnticipo.class),
				TipoAnticipoDto.class));
		return initializer;
	}

	public SolicitudServicioDto saveSolicituServicio(SolicitudServicioDto solicitudServicioDto) {
		SolicitudServicio solicitudServicio = repository.retrieve(SolicitudServicio.class, solicitudServicioDto.getId());
		mapper.map(solicitudServicioDto, solicitudServicio);
		solicitudBusinessService.saveSolicitudServicio(solicitudServicio);
		solicitudServicioDto = mapper.map(solicitudServicio, SolicitudServicioDto.class);
		return solicitudServicioDto;
	}
}

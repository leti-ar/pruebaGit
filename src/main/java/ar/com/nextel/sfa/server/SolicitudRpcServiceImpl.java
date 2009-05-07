package ar.com.nextel.sfa.server;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import java.util.Collections;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.dao.GenericDao;
import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.vantive.VantiveSystem;
import ar.com.nextel.business.legacy.vantive.dto.EstadoSolicitudServicioCerradaDTO;
import ar.com.nextel.business.solicitudes.creation.SolicitudServicioBusinessOperator;
import ar.com.nextel.business.solicitudes.creation.request.SolicitudServicioRequest;
import ar.com.nextel.business.solicitudes.repository.SolicitudServicioRepository;
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
import ar.com.nextel.sfa.client.dto.CambiosSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
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
import ar.com.nextel.util.DateUtils;
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
    private VantiveSystem vantiveSystem;
    private FinancialSystem financialSystem;
    
    private SolicitudServicioRepository solicitudServicioRepository;
    

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		solicitudesBusinessOperator = (SolicitudServicioBusinessOperator) context.getBean("solicitudServicioBusinessOperatorBean");
		solicitudBusinessService = (SolicitudBusinessService) context.getBean("solicitudBusinessService");
		
		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
		genericDao = (GenericDao) context.getBean("genericDao");
		repository = (Repository) context.getBean("repository");
		
		solicitudServicioRepository = (SolicitudServicioRepository) context.getBean("solicitudServicioRepositoryBean");
		vantiveSystem = (VantiveSystem) context.getBean("vantiveSystemBean");
		financialSystem = (FinancialSystem) context.getBean("financialSystemBean");
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
	
	
	public DetalleSolicitudServicioDto getDetalleSolicitudServicio(Long idSolicitudServicio) {
        AppLogger.info("Iniciando consulta de estados de SS cerradas para idSS " + idSolicitudServicio);
        SolicitudServicio solicitudServicio = solicitudServicioRepository.getSolicitudServicioPorId(idSolicitudServicio);        	
        DetalleSolicitudServicioDto detalleSolicitudServicioDto = mapearSolicitud(solicitudServicio);
                        
        try {
        	detalleSolicitudServicioDto.setCambiosEstadoSolicitud(getEstadoSolicitudServicioCerrada(solicitudServicio.getIdVantive()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AppLogger.info("Consulta de estados de SS cerradas para idSS " + idSolicitudServicio + " finalizada.");
        return detalleSolicitudServicioDto;
    }
	
		
    private DetalleSolicitudServicioDto mapearSolicitud(SolicitudServicio solicitudServicio) {
    	DetalleSolicitudServicioDto detalleSolicitudServicioDto = new DetalleSolicitudServicioDto();
    	detalleSolicitudServicioDto.setNumero(solicitudServicio.getNumero());
    	detalleSolicitudServicioDto.setNumeroCuenta(solicitudServicio.getCuenta().getCodigoVantive());
    	detalleSolicitudServicioDto.setRazonSocialCuenta(solicitudServicio.getCuenta().getPersona().getRazonSocial());
    	return detalleSolicitudServicioDto;
    }
	
	
	
	private List<CambiosSolicitudServicioDto> getEstadoSolicitudServicioCerrada(Long idVantiveSS) throws RpcExceptionMessages {
    try {
        List<EstadoSolicitudServicioCerradaDTO> resultDTO = null;
        resultDTO = this.vantiveSystem.retrieveEstadosSolicitudServicioCerrada(idVantiveSS);
        resultDTO.addAll(this.financialSystem.retrieveEstadosSolicitudServicioCerrada(idVantiveSS));
        
        Comparator<? super EstadoSolicitudServicioCerradaDTO> estadoComparator = new Comparator<EstadoSolicitudServicioCerradaDTO>() {

            public int compare(EstadoSolicitudServicioCerradaDTO estado1, EstadoSolicitudServicioCerradaDTO estado2) {
                int ret = 0;
                try {
                    Date date1 = (estado1.getFechaCambioEstado() != null) ? DateUtils.getInstance().getDate(estado1.getFechaCambioEstado(), "dd/MM/yyyy") : null;
                    Date date2 = (estado2.getFechaCambioEstado() != null) ? DateUtils.getInstance().getDate(estado2.getFechaCambioEstado(), "dd/MM/yyyy") : null;
                    if (date1 == null || date2 == null) {
                        return 1;
                    } else {
                        return DateUtils.getInstance().compareDatesByDay(date1, date2);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return ret;
            }
        };
        
        Collections.sort(resultDTO, estadoComparator);
        return this.transformEstadoSolicitudServicioCerradaDTOToCambioEstadoSolicitudWCTO(resultDTO);
    } catch (Exception e) {
        throw ExceptionUtil.wrap(e);
    }
}    

	
	private List<CambiosSolicitudServicioDto> transformEstadoSolicitudServicioCerradaDTOToCambioEstadoSolicitudWCTO(List<EstadoSolicitudServicioCerradaDTO> resultDTO) {
		List result = mapper.convertList(resultDTO, CambiosSolicitudServicioDto.class);
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

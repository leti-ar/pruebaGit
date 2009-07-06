package ar.com.nextel.sfa.server;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.dao.GenericDao;
import ar.com.nextel.business.describable.GetAllBusinessOperator;
import ar.com.nextel.business.oportunidades.search.SearchOportunidadBusinessOperator;
import ar.com.nextel.business.oportunidades.search.businessUnits.OportunidadSearchData;
import ar.com.nextel.business.oportunidades.search.result.OportunidadNegocioSearchResult;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.EstadoOportunidad;
import ar.com.nextel.model.personas.beans.GrupoDocumento;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.GrupoDocumentoDto;
import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioSearchResultDto;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.server.RemoteService;

/**
 * @author eSalvador
 */
public class OportunidadNegocioRpcServiceImpl extends RemoteService implements OportunidadNegocioRpcService{

	private WebApplicationContext context;
	private RegistroVendedores registroVendedores;
	private SearchOportunidadBusinessOperator searchOportunidadBusinessOperator;
	private MapperExtended mapper;
	private GetAllBusinessOperator getAllBusinessOperator;
	private SessionContextLoader sessionContextLoader;
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
		// Engancho el BOperator
		setGetAllBusinessOperator((GetAllBusinessOperator) context.getBean("getAllBusinessOperatorBean"));
		genericDao  = (GenericDao) context.getBean("genericDao");
		repository = (Repository) context.getBean("repository");
	}

	public void setGetAllBusinessOperator(
			GetAllBusinessOperator getAllBusinessOperator) {
		this.getAllBusinessOperator = getAllBusinessOperator;
	}
	
	public BuscarOportunidadNegocioInitializer getBuscarOportunidadInitializer() {
		BuscarOportunidadNegocioInitializer buscarOportunidadNegocioInitializer = new BuscarOportunidadNegocioInitializer();
		buscarOportunidadNegocioInitializer.setGrupoDocumento( mapper.convertList(genericDao.getList(GrupoDocumento.class),	GrupoDocumentoDto.class));
		List<EstadoOportunidadDto> listaEstados = mapper.convertList(genericDao.getList(EstadoOportunidad.class), EstadoOportunidadDto.class);
		while (listaEstados.size() > 2) {
			listaEstados.remove(2);
		}
		buscarOportunidadNegocioInitializer.setEstadoOPP(listaEstados);
		return buscarOportunidadNegocioInitializer;
	}

	public List<OportunidadNegocioSearchResultDto> searchOportunidad(OportunidadDto oportunidadDto) {
		AppLogger.info("Iniciando busqueda de oportunidades...");
		Vendedor vendedor = sessionContextLoader.getVendedor();
		OportunidadSearchData oportunidadSearchData = mapper.map(oportunidadDto, OportunidadSearchData.class);		
		List<OportunidadNegocioSearchResult> oportunidades = null;
		try {		
			oportunidades = this.searchOportunidadBusinessOperator.searchOportunidades(oportunidadSearchData, vendedor, oportunidadDto.getOffset(), oportunidadDto.getCantidadResultados());			
		} catch (Exception e) {
			AppLogger.info("Error buscando Oportunidades: " + e.getMessage());
		}
		List result = mapper.convertList(oportunidades, OportunidadNegocioSearchResultDto.class);
		AppLogger.info("Busqueda de oportunidades finalizada.");
		return result;
		}
}

package ar.com.nextel.sfa.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.describable.GetAllBusinessOperator;
import ar.com.nextel.business.oportunidades.search.SearchOportunidadBusinessOperator;
import ar.com.nextel.business.oportunidades.search.businessUnits.OportunidadSearchData;
import ar.com.nextel.business.oportunidades.search.result.OportunidadNegocioSearchResult;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadSearchDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.snoop.gwt.commons.server.RemoteService;

/**
 * @author eSalvador
 */
public class OportunidadNegocioRpcServiceImpl extends RemoteService implements OportunidadNegocioRpcService{

	private WebApplicationContext context;
	private RegistroVendedores registroVendedores;
//	private Transformer transformer;
	private SearchOportunidadBusinessOperator searchOportunidadBusinessOperator;
	//private Transformer transformer;
	private MapperExtended mapper;
	//private Long totalCuentaRegs;
	private GetAllBusinessOperator getAllBusinessOperator;
	private SessionContextLoader sessionContextLoader;
	
	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
		searchOportunidadBusinessOperator = (SearchOportunidadBusinessOperator) context
				.getBean("searchOportunidadBusinessOperatorBean");
//		transformer = (Transformer) context
//				.getBean("cuentaToSearchResultTransformer");
		sessionContextLoader = (SessionContextLoader) context.getBean("sessionContextLoader");
		mapper = (MapperExtended) context.getBean("dozerMapper");
		// Engancho el BOperator
		setGetAllBusinessOperator((GetAllBusinessOperator) context
				.getBean("getAllBusinessOperatorBean"));
	}

	public void setGetAllBusinessOperator(
			GetAllBusinessOperator getAllBusinessOperator) {
		this.getAllBusinessOperator = getAllBusinessOperator;
	}
	
	/**
	 * @author eSalvador TODO: Quitar el HardCode cuando se logre obtener los
	 *         datos reales de la Db.
	 **/
	public BuscarOportunidadNegocioInitializer getBuscarOportunidadInitializer() {
		List<TipoDocumentoDto> listaTipoDoc = new ArrayList<TipoDocumentoDto>();
		//listaTipoDoc.add(0, new TipoDocumentoDto("0", "Documento"));
		//listaTipoDoc.add(1, new TipoDocumentoDto("1", "CUIT/CUIL"));

		List<EstadoOportunidadDto> listaEstadoOpp = new ArrayList<EstadoOportunidadDto>();
		listaEstadoOpp.add(0, new EstadoOportunidadDto("0", "Activa"));
		listaEstadoOpp.add(1, new EstadoOportunidadDto("1", "Inactiva"));

		BuscarOportunidadNegocioInitializer buscarDTOinit = new BuscarOportunidadNegocioInitializer(listaEstadoOpp, listaTipoDoc);
		return buscarDTOinit;
	}

	public List<OportunidadSearchDto> searchOportunidad(OportunidadSearchDto oportunidadSearchDto)
			 {
		List dtoResult = new ArrayList();
		OportunidadSearchData oppSearchData = (OportunidadSearchData) mapper.map(
				oportunidadSearchDto, OportunidadSearchData.class);
		
		Vendedor vendedor = sessionContextLoader.getVendedor();
		SessionContextLoader sessContext = (SessionContextLoader) context
				.getBean("sessionContextLoader");
		sessContext.getSessionContext().setVendedor(vendedor);
		try {
			List<OportunidadNegocioSearchResult> result = searchOportunidadBusinessOperator
					.searchOportunidades(oppSearchData, vendedor,oportunidadSearchDto.getOffset(),
							oportunidadSearchDto.getCantidadResultados());
			for (OportunidadNegocioSearchResult oppNegocioSearchResult : result) {
				dtoResult.add(mapper.map(oppNegocioSearchResult, OportunidadSearchDto.class));
			}

		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return dtoResult;
	}
}

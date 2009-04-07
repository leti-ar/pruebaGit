package ar.com.nextel.sfa.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.collections.Transformer;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.cuentas.search.SearchCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.search.businessUnits.CuentaSearchData;
import ar.com.nextel.business.cuentas.search.result.CuentaSearchResult;
import ar.com.nextel.business.describable.GetAllBusinessOperator;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.components.search.SearcherException;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.personas.beans.Persona;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.server.util.MapperExtended;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CuentaRpcServiceImpl extends RemoteServiceServlet implements CuentaRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8363102229209205047L;
	private WebApplicationContext context;
	// private ApplicationContextLoader context;
	private RegistroVendedores registroVendedores;
	private SearchCuentaBusinessOperator searchCuentaBusinessOperator;
	private Transformer transformer;
	private MapperExtended mapper;
	private GetAllBusinessOperator getAllBusinessOperator;

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
		searchCuentaBusinessOperator = (SearchCuentaBusinessOperator) context
				.getBean("searchCuentaBusinessOperatorBean");
		transformer = (Transformer) context.getBean("cuentaToSearchResultTransformer");
		mapper = (MapperExtended) context.getBean("dozerMapper");
		// Engancho el BOperator
		setGetAllBusinessOperator((GetAllBusinessOperator) context.getBean("getAllBusinessOperatorBean"));
	}

	public List<CuentaDto> searchCuenta(CuentaSearchDto cuentaSearchDto) {
		List dtoResult = new ArrayList();
		CuentaSearchData cuentaSearchData = (CuentaSearchData) mapper.map(cuentaSearchDto,
				CuentaSearchData.class);

		cuentaSearchData.setCantidadResultados(cuentaSearchDto.getCantidadResultados());

		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Vendedor vendedor = registroVendedores.getVendedor(usuario);
		SessionContextLoader sessContext = (SessionContextLoader) context.getBean("sessionContextLoader");
		sessContext.getSessionContext().setVendedor(vendedor);
		List filtros = (List) context.getBean("vendedorCuentaSearchFilterDefinitions");
		try {
			List<CuentaSearchResult> result = searchCuentaBusinessOperator.searchCuentas(cuentaSearchData,
					vendedor, filtros, transformer, cuentaSearchDto.getOffset(), cuentaSearchDto
							.getCantidadResultados());
			for (CuentaSearchResult cuentaSearchResult : result) {
				dtoResult.add(mapper.map(cuentaSearchResult, CuentaDto.class));
			}

		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return dtoResult;

	}
	
	public List<SolicitudServicioDto> searchSSCerrada(SolicitudServicioDto solicitudServicioDto) {
		List dtoResult = new ArrayList();

		return dtoResult;
	}

	/**
	 * @author eSalvador TODO: Quitar el HardCode cuando se logre obtener los datos reales de la Db.
	 **/
	public BuscarCuentaInitializer getBuscarCuentaInitializer() {
		List<TipoDocumentoDto> listaTipoDoc = new ArrayList<TipoDocumentoDto>();
		listaTipoDoc.add(0, new TipoDocumentoDto("0", "Documento"));
		listaTipoDoc.add(1, new TipoDocumentoDto("1", "CUIT/CUIL"));

		List<BusquedaPredefinidaDto> listaBusquedaPredef = new ArrayList<BusquedaPredefinidaDto>();
		listaBusquedaPredef.add(0, new BusquedaPredefinidaDto("1", "Ctas. propias"));
		listaBusquedaPredef.add(1, new BusquedaPredefinidaDto("2", "Últimas consultadas"));

		List<String> listaResult = new ArrayList<String>();
		String cantResult = "10;25;50;75;100";
		listaResult = Arrays.asList(cantResult.split(";"));

		List<CategoriaCuentaDto> listaCategorias = new ArrayList<CategoriaCuentaDto>();
		listaCategorias.add(0, new CategoriaCuentaDto("0", "GRAN CUENTA"));
		listaCategorias.add(1, new CategoriaCuentaDto("1", "DIVISION"));
		listaCategorias.add(2, new CategoriaCuentaDto("2", "SUSCRIPTOR"));

		BuscarCuentaInitializer buscarDTOinit = new BuscarCuentaInitializer(listaBusquedaPredef, listaResult,
				listaCategorias, listaTipoDoc);
		return buscarDTOinit;
		/*
		 * 
		 * Para traer los datos de la base:
		 * 
		 * initializer.setTiposDocumento(mapper.convertList(genericDao.getList( TipoDocumento.class),
		 * TipoDocumentoDto.class));
		 * 
		 * initializer.setCategorias(mapper.convertList(genericDao.getList( CategoriaCuenta.class),
		 * CategoriaCuentaDto.class));
		 * 
		 * List<String> listaResult = new ArrayList<String>(); String cantResult =
		 * genericDao.getParameter("sfa.business.cuenta.busqueda.cantResultados" ); listaResult =
		 * Arrays.asList(cantResult.split(";"));
		 */
	}
	
	/**
	 * @author JulioVesco TODO: Quitar el HardCode cuando se logre obtener los
	 *         datos reales de la Db.
	 **/
	public BuscarSSCerradasInitializer getBuscarSSCerradasInitializer() {
		List<String> listaResult = new ArrayList<String>();
		String cantResult = "10;25;50;75;100";
		listaResult = Arrays.asList(cantResult.split(";"));
		
		List<String> listaFirmas = new ArrayList<String>();
		String opcionesFirmas = "SI;NO;TODAS";
		listaFirmas = Arrays.asList(opcionesFirmas.split(";"));
		
		List<String> listaPataconex = new ArrayList<String>();
		String opcionesPataconex = "SI;NO;TODAS";
		listaPataconex = Arrays.asList(opcionesPataconex.split(";"));
		
		List<String> listaEstado = new ArrayList<String>();
		String opcionesEstado = "Todos;Pass;Fail";
		listaEstado = Arrays.asList(opcionesEstado.split(";"));

		BuscarSSCerradasInitializer buscarDTOinit = new BuscarSSCerradasInitializer(
				listaResult, listaFirmas, listaPataconex, listaEstado);
		return buscarDTOinit;
	}
	
	// Prueba rgm

	/**
	 * @param getAllBusinessOperator
	 *            El/La getAllBusinessOperator a setear.
	 */
	public void setGetAllBusinessOperator(GetAllBusinessOperator getAllBusinessOperator) {
		this.getAllBusinessOperator = getAllBusinessOperator;
	}

	public List<TipoContribuyenteDto> getTiposContribuyente(Long tipoDocumento) {
		return this.getAllBusinessOperator.getAll(TipoContribuyenteDto.class);

	}

	public AgregarCuentaInitializer getAgregarCuentaInitializer() {
		List<TipoDocumentoDto> listaTipoDoc = new ArrayList<TipoDocumentoDto>();
		listaTipoDoc.add(0, new TipoDocumentoDto("0", "Documento"));
		listaTipoDoc.add(1, new TipoDocumentoDto("1", "CUIT/CUIL"));

		List<TipoContribuyenteDto> listaTipoContribuy = new ArrayList<TipoContribuyenteDto>();
		listaTipoContribuy.add(0, new TipoContribuyenteDto("0", "Consumidor Final"));
		listaTipoContribuy.add(1, new TipoContribuyenteDto("1", "Monotributo"));

		List<RubroDto> listaRubro = new ArrayList<RubroDto>();
		listaRubro.add(0, new RubroDto("0", "Comercio Mayorista"));
		listaRubro.add(1, new RubroDto("1", "Papel"));

		AgregarCuentaInitializer buscarDTOinit = new AgregarCuentaInitializer(listaTipoDoc,
				listaTipoContribuy, listaRubro);
		return buscarDTOinit;
	}

	public PersonaDto saveCuenta(PersonaDto personaDto) {
		Persona persona = new Persona();
		mapper.map(personaDto, persona);
		return personaDto;

	}

}

package ar.com.nextel.sfa.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.cuentas.search.SearchCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.search.businessUnits.CuentaSearchData;
import ar.com.nextel.business.cuentas.search.result.CuentaSearchResult;
import ar.com.nextel.business.cuentas.select.SelectCuentaBusinessOperator;
import ar.com.nextel.business.dao.GenericDao;
import ar.com.nextel.business.describable.GetAllBusinessOperator;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.model.cuentas.beans.CategoriaCuenta;
import ar.com.nextel.model.cuentas.beans.ClaseCuenta;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.FormaPago;
import ar.com.nextel.model.cuentas.beans.TipoContribuyente;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.Rubro;
import ar.com.nextel.model.personas.beans.Persona;
import ar.com.nextel.model.personas.beans.Sexo;
import ar.com.nextel.model.personas.beans.TipoDocumento;
import ar.com.nextel.model.solicitudes.beans.EstadoSolicitud;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.services.nextelServices.veraz.VerazService;
import ar.com.nextel.services.nextelServices.veraz.dto.VerazResponseDTO;
import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CambiosSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.ClaseCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.FormaPagoDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioSearchDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.server.RemoteService;

public class CuentaRpcServiceImpl extends RemoteService implements
		CuentaRpcService {

	private WebApplicationContext context;
	// private ApplicationContextLoader context;
	private RegistroVendedores registroVendedores;
	private SearchCuentaBusinessOperator searchCuentaBusinessOperator;
	private SelectCuentaBusinessOperator selectCuentaBusinessOperator;
	private Transformer transformer;
	private MapperExtended mapper;
	private GetAllBusinessOperator getAllBusinessOperator;
	private GenericDao genericDao;
    private VerazService veraz;
    private Repository repository;


	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		registroVendedores = (RegistroVendedores) context
				.getBean("registroVendedores");
		searchCuentaBusinessOperator = (SearchCuentaBusinessOperator) context
				.getBean("searchCuentaBusinessOperatorBean");
		
		selectCuentaBusinessOperator = (SelectCuentaBusinessOperator) context.getBean("selectCuentaBusinessOperator");
		
		transformer = (Transformer) context
				.getBean("cuentaToSearchResultTransformer");
		mapper = (MapperExtended) context.getBean("dozerMapper");
		genericDao = (GenericDao) context.getBean("genericDao");
		veraz = (VerazService) context.getBean("verazService");
		repository = (Repository) context.getBean("repository");
		
		// Engancho el BOperator
		setGetAllBusinessOperator((GetAllBusinessOperator) context
				.getBean("getAllBusinessOperatorBean"));
	}

	public List<CuentaSearchResultDto> searchCuenta(CuentaSearchDto cuentaSearchDto) {
		List dtoResult = new ArrayList();
		CuentaSearchData cuentaSearchData = (CuentaSearchData) mapper.map(
				cuentaSearchDto, CuentaSearchData.class);

		cuentaSearchData.setCantidadResultados(cuentaSearchDto
				.getCantidadResultados());

		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Vendedor vendedor = registroVendedores.getVendedor(usuario);
		SessionContextLoader sessContext = (SessionContextLoader) context
				.getBean("sessionContextLoader");
		sessContext.getSessionContext().setVendedor(vendedor);
		List filtros = (List) context
				.getBean("vendedorCuentaSearchFilterDefinitions");
		try {
			List<CuentaSearchResult> result = searchCuentaBusinessOperator
					.searchCuentas(cuentaSearchData, vendedor, filtros,
							transformer, cuentaSearchDto.getOffset(),
							cuentaSearchDto.getCantidadResultados());
			for (CuentaSearchResult cuentaSearchResult : result) {
				dtoResult.add(mapper.map(cuentaSearchResult, CuentaSearchResultDto.class));
			}

		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return dtoResult;

	}

	/**
	 * @author julioVesco TODO: Quitar el HardCode cuando se logre obtener los
	 *         datos reales de la Db.
	 **/
	public SolicitudesServicioTotalesDto searchSSCerrada(
			SolicitudServicioSearchDto solicitudServicioSearchDto) {
		List dtoResult = new ArrayList();

		for (int i = 0; i < 100; i++) {
			createSSMock(dtoResult, 2L, 1, RandomUtils.nextBoolean(), Long
					.valueOf(RandomUtils.nextLong()).toString(), "" + i, "0",
					"Razon Social " + i, "Comentario " + i, "Estado " + i);
		}

		SolicitudesServicioTotalesDto solicitudes = new SolicitudesServicioTotalesDto();
		solicitudes.setSolicitudes(dtoResult);
		solicitudes.setTotalEquipos(10L);
		solicitudes.setTotalPataconex(20.00);
		solicitudes.setTotalEquiposFirmados(3L);

		return solicitudes;
	}

	/**
	 * @author julioVesco TODO: Este metodo se eliminaria cuando se logren obtener los datos da la base.
	 **/
	private void createSSMock(List dtoResult, long cantEquipos,
			int cantResultados, boolean firmas, String nroCuenta, String nroSS,
			String pataconex, String razonSocial, String comentario,
			String estado) {
		SolicitudServicioCerradaDto solicitudServicioDto = new SolicitudServicioCerradaDto();
		solicitudServicioDto.setCantidadEquipos(cantEquipos);
		solicitudServicioDto.setCantidadResultados(cantResultados);
		solicitudServicioDto.setFirmas(firmas);
		solicitudServicioDto.setNumeroCuenta(nroCuenta);
		solicitudServicioDto.setNumeroSS(nroSS);
		solicitudServicioDto.setPataconex(pataconex);
		solicitudServicioDto.setRazonSocial(razonSocial);
		
		List cambios = new ArrayList<CambiosSolicitudServicioDto>();
		for (int i = 0; i < 15; i++) {
			CambiosSolicitudServicioDto cambiosSolicitudServicioDto = new CambiosSolicitudServicioDto();
			cambiosSolicitudServicioDto.setComentario(comentario);
			cambiosSolicitudServicioDto.setEstado(estado);
			cambiosSolicitudServicioDto.setFecha(new Date());
			cambios.add(cambiosSolicitudServicioDto);
		}
		solicitudServicioDto.setCambios(cambios);
		dtoResult.add(solicitudServicioDto);
	}

	/**
	 * @author eSalvador TODO: Quitar el HardCode cuando se logre obtener los
	 *         datos reales de la Db.
	 **/
	public BuscarCuentaInitializer getBuscarCuentaInitializer() {
		//List<TipoDocumentoDto> listaTipoDoc = mapper.convertList(genericDao.getList(TipoDocumento.class),	TipoDocumentoDto.class);
		List<TipoDocumentoDto> listaTipoDoc = new ArrayList<TipoDocumentoDto>();
		listaTipoDoc.add(0, new TipoDocumentoDto(0L, "Documento"));
		listaTipoDoc.add(1, new TipoDocumentoDto(1L, "CUIT/CUIL"));
		
		List<CategoriaCuentaDto> listaCategorias = mapper.convertList(genericDao.getList(CategoriaCuenta.class),	CategoriaCuentaDto.class);

		List<BusquedaPredefinidaDto> listaBusquedaPredef = new ArrayList<BusquedaPredefinidaDto>();
		listaBusquedaPredef.add(0, new BusquedaPredefinidaDto("1",	"Ctas. propias"));
		listaBusquedaPredef.add(1, new BusquedaPredefinidaDto("2",	"Últimas consultadas"));
		//listaBusquedaPredef.add(1, new BusquedaPredefinidaDto("3",	"Ctas. c/Créd.Fideliz."));
		
		List<String> listaResult = new ArrayList<String>();
		String cantResult = "10;25;50;75;100";
		listaResult = Arrays.asList(cantResult.split(";"));

		BuscarCuentaInitializer buscarDTOinit = new BuscarCuentaInitializer(
				listaBusquedaPredef, listaResult, listaCategorias, listaTipoDoc);
		return buscarDTOinit;
	}


	public BuscarSSCerradasInitializer getBuscarSSCerradasInitializer() {
		BuscarSSCerradasInitializer buscarSSCerradasInitializer = new BuscarSSCerradasInitializer();
					
		List<String> listaResult = new ArrayList<String>();
		String cantResult = "10;25;50;75;100";
		listaResult = Arrays.asList(cantResult.split(";"));
		buscarSSCerradasInitializer.setCantidadesResultados(listaResult);

		List<String> listaFirmas = new ArrayList<String>();
		String opcionesFirmas = "SI;NO;TODAS";
		listaFirmas = Arrays.asList(opcionesFirmas.split(";"));
		buscarSSCerradasInitializer.setOpcionesFirmas(listaFirmas);

		List<String> listaPataconex = new ArrayList<String>();
		String opcionesPataconex = "SI;NO;TODAS";
		listaPataconex = Arrays.asList(opcionesPataconex.split(";"));
		buscarSSCerradasInitializer.setOpcionesPatacones(listaPataconex);

		buscarSSCerradasInitializer.setOpcionesEstado(mapper.convertList(genericDao.getList(EstadoSolicitud.class), EstadoSolicitudDto.class));
		
		return buscarSSCerradasInitializer;
	}

	// Prueba rgm

	/**
	 * @param getAllBusinessOperator
	 *            El/La getAllBusinessOperator a setear.
	 */
	public void setGetAllBusinessOperator(
			GetAllBusinessOperator getAllBusinessOperator) {
		this.getAllBusinessOperator = getAllBusinessOperator;
	}

	public List<TipoContribuyenteDto> getTiposContribuyente(Long tipoDocumento) {
		return this.getAllBusinessOperator.getAll(TipoContribuyenteDto.class);

	}

	public AgregarCuentaInitializer getAgregarCuentaInitializer() {
		AgregarCuentaInitializer buscarDTOinit = new AgregarCuentaInitializer();
		buscarDTOinit.setTiposContribuyentes(mapper.convertList(genericDao.getList(TipoContribuyente.class),TipoContribuyenteDto.class));
		buscarDTOinit.setTiposDocumento(mapper.convertList(genericDao.getList(TipoDocumento.class),	TipoDocumentoDto.class));
		buscarDTOinit.setRubro(mapper.convertList(genericDao.getList(Rubro.class),RubroDto.class));
		buscarDTOinit.setSexo(mapper.convertList(genericDao.getList(Sexo.class),SexoDto.class));
		buscarDTOinit.setFormaPago(mapper.convertList(genericDao.getList(FormaPago.class),FormaPagoDto.class));
		buscarDTOinit.setClaseCliente(mapper.convertList(genericDao.getList(ClaseCuenta.class),ClaseCuentaDto.class));
		return buscarDTOinit;
	}

	public PersonaDto saveCuenta(PersonaDto personaDto) {
		Persona persona = new Persona();
		mapper.map(personaDto, persona);
		return personaDto;

	}

	public VerazInitializer getVerazInitializer() {
		VerazInitializer verazInitializer = new VerazInitializer();
		verazInitializer.setTiposDocumento(mapper.convertList(genericDao.getList(TipoDocumento.class),TipoDocumentoDto.class));
		verazInitializer.setSexos(mapper.convertList(genericDao.getList(Sexo.class),SexoDto.class));
		return verazInitializer;
	}
	
	
	public VerazResponseDto consultarVeraz(PersonaDto personaDto) {
        AppLogger.info("Iniciando consulta a Veraz...");
        VerazResponseDTO responseDTO = null;
        Sexo sexo = (Sexo) this.repository.retrieve(Sexo.class, personaDto.getSexo().getCode());
		TipoDocumento tipoDocumento = (TipoDocumento) this.repository.retrieve(TipoDocumento.class, personaDto.getDocumento().getTipoDocumento().getCode());
		long numeroDocumento = Long.parseLong(personaDto.getDocumento().getNumero());
		AppLogger.debug("Parametros consulta a Veraz: " + tipoDocumento.getCodigoVeraz() + " / " + numeroDocumento + " / " + sexo.getCodigoVeraz() + "..."); 	
		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Vendedor vendedor = registroVendedores.getVendedor(usuario);
		responseDTO = this.veraz.searchPerson(tipoDocumento.getCodigoVeraz(), numeroDocumento, sexo.getCodigoVeraz(), vendedor.getVerazVersion());
		VerazResponseDto responseDto = mapper.map(responseDTO, VerazResponseDto.class);
        responseDto.setMensaje(responseDTO.getMensaje());
        AppLogger.info("Consulta a Veraz finalizada.");
        return responseDto;
    }
	
	public CuentaDto selectCuenta(Long cuentaId, String cod_vantive) {
		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Cuenta cuenta = null;
		CuentaDto ctaDTO = null;
		try {
			//cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(cuentaId);
			cuenta = selectCuentaBusinessOperator.getCuentaYLockear(cod_vantive, registroVendedores.getVendedor(usuario));
//			CondicionCuenta cd1= cuenta.getCondicionCuenta();
//			Long id = cd1.getId();
//			String code = cd1.getCodigoVantive();
//			String desc = cd1.getDescripcion();
//			CondicionCuentaDto cd2 = new CondicionCuentaDto(id,code,desc);
//			ctaDTO.setCondicionCuenta(cd2);
			ctaDTO = (CuentaDto) mapper.map(cuenta, CuentaDto.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ctaDTO;
	}
}

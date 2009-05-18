package ar.com.nextel.sfa.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.collections.Transformer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.cuentas.create.businessUnits.SolicitudCuenta;
import ar.com.nextel.business.cuentas.search.SearchCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.search.businessUnits.CuentaSearchData;
import ar.com.nextel.business.cuentas.search.result.CuentaSearchResult;
import ar.com.nextel.business.cuentas.select.SelectCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.tarjetacredito.TarjetaCreditoValidatorResult;
import ar.com.nextel.business.cuentas.tarjetacredito.TarjetaCreditoValidatorServiceAxisImpl;
import ar.com.nextel.business.cuentas.tarjetacredito.TarjetaCreditoValidatorServiceException;
import ar.com.nextel.business.dao.GenericDao;
import ar.com.nextel.business.describable.GetAllBusinessOperator;
import ar.com.nextel.business.externalConnection.exception.MerlinException;
import ar.com.nextel.business.personas.normalizarDomicilio.NormalizadorDomicilio;
import ar.com.nextel.business.personas.normalizarDomicilio.businessUnits.NormalizarDomicilioRequest;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.model.cuentas.beans.Cargo;
import ar.com.nextel.model.cuentas.beans.CategoriaCuenta;
import ar.com.nextel.model.cuentas.beans.ClaseCuenta;
import ar.com.nextel.model.cuentas.beans.CondicionCuenta;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.FormaPago;
import ar.com.nextel.model.cuentas.beans.Proveedor;
import ar.com.nextel.model.cuentas.beans.TipoCanalVentas;
import ar.com.nextel.model.cuentas.beans.TipoContribuyente;
import ar.com.nextel.model.cuentas.beans.TipoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.Rubro;
import ar.com.nextel.model.personas.beans.Documento;
import ar.com.nextel.model.personas.beans.Domicilio;
import ar.com.nextel.model.personas.beans.GrupoDocumento;
import ar.com.nextel.model.personas.beans.Sexo;
import ar.com.nextel.model.personas.beans.TipoDocumento;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.services.nextelServices.NextelServices;
import ar.com.nextel.services.nextelServices.veraz.dto.VerazRequestDTO;
import ar.com.nextel.services.nextelServices.veraz.dto.VerazResponseDTO;
import ar.com.nextel.services.nextelServices.veraz.exception.VerazException;
import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.ClaseCuentaDto;
import ar.com.nextel.sfa.client.dto.CondicionCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.FormaPagoDto;
import ar.com.nextel.sfa.client.dto.GrupoDocumentoDto;
import ar.com.nextel.sfa.client.dto.NormalizarDomicilioResultDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.TipoCanalVentasDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;
import ar.com.nextel.sfa.server.businessservice.CuentaBusinessService;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.nextel.util.StringUtil;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

/**
 * @author eSalvador
 **/
public class CuentaRpcServiceImpl extends RemoteService implements
		CuentaRpcService {

	private WebApplicationContext context;
	// private ApplicationContextLoader context;
	private RegistroVendedores registroVendedores;
	private SearchCuentaBusinessOperator searchCuentaBusinessOperator;
	private SelectCuentaBusinessOperator selectCuentaBusinessOperator;
	private TarjetaCreditoValidatorServiceAxisImpl tarjetaCreditoValidatorService;

	private CuentaBusinessService cuentaBusinessService;
	
	private Transformer transformer;
	private MapperExtended mapper;
	private GetAllBusinessOperator getAllBusinessOperator;
	private GenericDao genericDao;
//    private VerazService veraz;
	private NextelServices veraz;
 	private Repository repository;
 	private NormalizadorDomicilio normalizadorDomicilio;


	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
		searchCuentaBusinessOperator = (SearchCuentaBusinessOperator) context.getBean("searchCuentaBusinessOperatorBean");
		
		selectCuentaBusinessOperator  = (SelectCuentaBusinessOperator)  context.getBean("selectCuentaBusinessOperator");
		cuentaBusinessService = (CuentaBusinessService) context.getBean("cuentaBusinessService");
		tarjetaCreditoValidatorService = (TarjetaCreditoValidatorServiceAxisImpl) context.getBean("tarjetaCreditoValidatorService");
		
		transformer = (Transformer) context.getBean("cuentaToSearchResultTransformer");
		mapper = (MapperExtended) context.getBean("dozerMapper");
		genericDao = (GenericDao) context.getBean("genericDao");
//		veraz = (VerazService) context.getBean("verazService");
		veraz = (NextelServices) context.getBean("nextelServices");
		repository = (Repository) context.getBean("repository");
		normalizadorDomicilio = (NormalizadorDomicilio) context.getBean("normalizadorDomicilio");
		
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
	 * @author eSalvador TODO: Quitar el HardCode cuando se logre obtener los
	 *         datos reales de la Db.
	 **/
	public BuscarCuentaInitializer getBuscarCuentaInitializer() {

		List<GrupoDocumentoDto> listaGrupoDoc = mapper.convertList(repository.getAll(GrupoDocumento.class),	GrupoDocumentoDto.class);
		List<CategoriaCuentaDto> listaCategorias = mapper.convertList(genericDao.getList(CategoriaCuenta.class),	CategoriaCuentaDto.class);

		List<BusquedaPredefinidaDto> listaBusquedaPredef = new ArrayList<BusquedaPredefinidaDto>();
		listaBusquedaPredef.add(0, new BusquedaPredefinidaDto(1,"Ctas. propias"));
		listaBusquedaPredef.add(1, new BusquedaPredefinidaDto(3,"Últimas consultadas"));
		//listaBusquedaPredef.add(1, new BusquedaPredefinidaDto("3",	"Ctas. c/Créd.Fideliz."));
		
		List<String> listaResult = new ArrayList<String>();
		String cantResult = "10;25;50;75;100";
		listaResult = Arrays.asList(cantResult.split(";"));

		BuscarCuentaInitializer buscarDTOinit = new BuscarCuentaInitializer(
				listaBusquedaPredef, listaResult, listaCategorias, listaGrupoDoc);
		return buscarDTOinit;
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
		buscarDTOinit.setProveedorAnterior(mapper.convertList(genericDao.getList(Proveedor.class),ProveedorDto.class));
		buscarDTOinit.setCargo(mapper.convertList(genericDao.getList(Cargo.class),CargoDto.class));
		buscarDTOinit.setTipoCuentaBancaria(mapper.convertList(genericDao.getList(TipoCuentaBancaria.class),TipoCuentaBancariaDto.class));
		buscarDTOinit.setTipoTarjeta(mapper.convertList(genericDao.getList(TipoTarjeta.class),TipoTarjetaDto.class));
		buscarDTOinit.setTipoCanalVentas(mapper.convertList(genericDao.getList(TipoCanalVentas.class),TipoCanalVentasDto.class));
		buscarDTOinit.setAnio(Calendar.getInstance().get(Calendar.YEAR));
		return buscarDTOinit;
	}

	public void saveCuenta(CuentaDto cuentaDto) {
 	    Cuenta cuenta = cuentaBusinessService.getCuenta(cuentaDto.getId());
 	    mapper.map(cuentaDto, cuenta);
//      cuenta.setVendedor(getVendedor("acsa1"));
//		cuenta.setLastModificationDate(Calendar.getInstance().getTime());
		cuentaBusinessService.saveCuenta(cuenta);
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
		TipoDocumento tipoDocumento = (TipoDocumento) this.repository.retrieve(TipoDocumento.class, personaDto.getDocumento().getTipoDocumento().getId());
		long numeroDocumento = Long.parseLong(StringUtil.removeOcurrences(personaDto.getDocumento().getNumero(), '-'));
		AppLogger.debug("Parametros consulta a Veraz: " + tipoDocumento.getCodigoVeraz() + " / " + numeroDocumento + " / " + sexo.getCodigoVeraz() + "..."); 	
		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Vendedor vendedor = registroVendedores.getVendedor(usuario);
		
		VerazRequestDTO verazRequestDTO =  new VerazRequestDTO();
		verazRequestDTO.setNroDoc(numeroDocumento);
		verazRequestDTO.setSexo(sexo.getCodigoVeraz());
		verazRequestDTO.setTipoDoc(tipoDocumento.getCodigoVeraz());
		verazRequestDTO.setVerazVersion(vendedor.getVerazVersion());
		
		try {
			responseDTO = this.veraz.searchPerson(verazRequestDTO);
		} catch (VerazException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		VerazResponseDto responseDto = mapper.map(responseDTO, VerazResponseDto.class);
        responseDto.setMensaje(responseDTO.getMensaje());
        AppLogger.info("Consulta a Veraz finalizada.");
        return responseDto;
    }
	

	public SolicitudesServicioTotalesDto searchSSCerrada(
			SolicitudServicioCerradaDto solicitudServicioCerradaDto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public CuentaDto selectCuenta(Long cuentaId, String cod_vantive) {
		Cuenta cuenta = null;
		CuentaDto ctaDTO = null;
		try {
			if (cuentaId == 0){
				//TODO: Analizar y borrar si no va!!
				throw new RpcExceptionMessages("No tiene permisos para ver esta cuenta.");
			}else{
				cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(cuentaId);
				if (!cod_vantive.equals("***") && (!cod_vantive.equals("null"))){
					cuenta = selectCuentaBusinessOperator.getCuentaYLockear(cod_vantive, getVendedor("acsa1"));
				}
				CondicionCuenta cd1= cuenta.getCondicionCuenta();
				Long id = cd1.getId();
				String code = cd1.getCodigoVantive();
				String desc = cd1.getDescripcion();
				CondicionCuentaDto cd2 = new CondicionCuentaDto(id,code,desc);
				ctaDTO = (CuentaDto) mapper.map(cuenta, CuentaDto.class);
				ctaDTO.setCondicionCuenta(cd2);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ctaDTO;
	}

	/**
	 * 
	 * @param solicitudCuenta
	 * @return
	 */
	public CuentaDto reservaCreacionCuenta(DocumentoDto docDto) {
		Cuenta cuenta = null;
		CuentaDto cuentaDto = null;
		Vendedor vendedor = getVendedor("acsa1");
		SolicitudCuenta solicitudCta = repository.createNewObject(SolicitudCuenta.class);
		solicitudCta.setVendedor(vendedor);
		solicitudCta.setDocumento(getDocumento(docDto));
		try {
			cuenta = cuentaBusinessService.reservarCrearCta(solicitudCta);
			//cuenta = selectCuentaBusinessOperator.getCuentaYLockear(cuenta.getCodigoVantive(), vendedor);
			cuentaDto = (CuentaDto) mapper.map(cuenta, CuentaDto.class);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cuentaDto;
	}
	
	public TarjetaCreditoValidatorResultDto validarTarjeta(String numeroTarjeta, Integer mesVto, Integer anoVto) {
		TarjetaCreditoValidatorResultDto resultDto = null;
		try {
			TarjetaCreditoValidatorResult tarjetaCreditoValidatorResult =tarjetaCreditoValidatorService.validate(numeroTarjeta, mesVto, anoVto);
			resultDto = (TarjetaCreditoValidatorResultDto) mapper.map(tarjetaCreditoValidatorResult, TarjetaCreditoValidatorResultDto.class);
		} catch (TarjetaCreditoValidatorServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultDto;
	}
	
	/**
	 * 
	 * @param usu
	 * @return
	 */
    private Vendedor getVendedor(String usu) {
		Usuario usuario = new Usuario();
		usuario.setUserName(usu);
		return registroVendedores.getVendedor(usuario);
    }
    
    /**
     * 
     * @param docDto
     * @return
     */
    private Documento getDocumento(DocumentoDto docDto) {
    	TipoDocumento tipoDoc = repository.retrieve(TipoDocumento.class,docDto.getTipoDocumento().getId());
    	Documento doc = repository.createNewObject(Documento.class);
    	doc.setTipoDocumento(tipoDoc);
    	doc.setNumero(docDto.getNumero());
    	return doc;
    }

    
    /**
     * @author eSalvador 
     **/
	public NormalizarDomicilioResultDto normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar) throws RpcExceptionMessages {
		//DomiciliosCuentaDto domicilioNormalizado = null;
		NormalizarDomicilioResultDto domicilioResultNormalizacion = null;
		try {
			NormalizarDomicilioRequest normalizarDomicilioRequest = new NormalizarDomicilioRequest();
			normalizarDomicilioRequest.populateFromDomicilio(mapper.map(domicilioANormalizar, Domicilio.class));
			domicilioResultNormalizacion =  mapper.map(normalizadorDomicilio.normalizarDomicilio(normalizarDomicilioRequest),NormalizarDomicilioResultDto.class);
			//domicilioNormalizado = mapper.map(normalizadorDomicilio.normalizarDomicilio(normalizarDomicilioRequest).getDireccion(), DomiciliosCuentaDto.class);
			
		} catch (MerlinException e) {
			throw ExceptionUtil.wrap(e);
		}
		//return domicilioNormalizado;
		return domicilioResultNormalizacion;
		
	}

    
    /**
     * @author eSalvador 
     **/
	public DomiciliosCuentaDto getDomicilioPorCPA(String cpa) throws RpcExceptionMessages {
		DomiciliosCuentaDto domicilioNormalizado = null;
		/**TODO: Terminar!*/
		try {
			domicilioNormalizado = mapper.map(normalizadorDomicilio.normalizarCPA(cpa), DomiciliosCuentaDto.class);
		} catch (MerlinException e) {
			throw ExceptionUtil.wrap(e);
		}
		return domicilioNormalizado;
	}
}

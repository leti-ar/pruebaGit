package ar.com.nextel.sfa.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.collections.Transformer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.cuentas.contactos.ContactosCuentaBusinessOperator;
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
import ar.com.nextel.components.filter.Filter;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.model.cuentas.beans.Cargo;
import ar.com.nextel.model.cuentas.beans.CategoriaCuenta;
import ar.com.nextel.model.cuentas.beans.ClaseCuenta;
import ar.com.nextel.model.cuentas.beans.CondicionCuenta;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.Division;
import ar.com.nextel.model.cuentas.beans.FormaPago;
import ar.com.nextel.model.cuentas.beans.GranCuenta;
import ar.com.nextel.model.cuentas.beans.Proveedor;
import ar.com.nextel.model.cuentas.beans.Suscriptor;
import ar.com.nextel.model.cuentas.beans.TipoCanalVentas;
import ar.com.nextel.model.cuentas.beans.TipoContribuyente;
import ar.com.nextel.model.cuentas.beans.TipoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.Rubro;
import ar.com.nextel.model.personas.beans.Documento;
import ar.com.nextel.model.personas.beans.Domicilio;
import ar.com.nextel.model.personas.beans.GrupoDocumento;
import ar.com.nextel.model.personas.beans.Provincia;
import ar.com.nextel.model.personas.beans.Sexo;
import ar.com.nextel.model.personas.beans.TipoDocumento;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.services.nextelServices.NextelServices;
import ar.com.nextel.services.nextelServices.veraz.dto.VerazRequestDTO;
import ar.com.nextel.services.nextelServices.veraz.dto.VerazResponseDTO;
import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.ClaseCuentaDto;
import ar.com.nextel.sfa.client.dto.CondicionCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.FormaPagoDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.GrupoDocumentoDto;
import ar.com.nextel.sfa.client.dto.NormalizarCPAResultDto;
import ar.com.nextel.sfa.client.dto.NormalizarDomicilioResultDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.ProvinciaDto;
import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.TipoCanalVentasDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.enums.TipoCuentaEnum;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.CrearContactoInitializer;
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
	private ContactosCuentaBusinessOperator contactosCuentaBusinessOperator;
	
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
 	private SessionContextLoader sessionContextLoader;

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		sessionContextLoader = (SessionContextLoader) context.getBean("sessionContextLoader");
		registroVendedores = (RegistroVendedores)   context.getBean("registroVendedores");
		searchCuentaBusinessOperator = (SearchCuentaBusinessOperator) context.getBean("searchCuentaBusinessOperatorBean");
		selectCuentaBusinessOperator = (SelectCuentaBusinessOperator) context.getBean("selectCuentaBusinessOperator");
		contactosCuentaBusinessOperator = (ContactosCuentaBusinessOperator) context.getBean("contactosCuentaBusinessOperator");
		cuentaBusinessService = (CuentaBusinessService) context.getBean("cuentaBusinessService");
		tarjetaCreditoValidatorService = (TarjetaCreditoValidatorServiceAxisImpl) context.getBean("tarjetaCreditoValidatorService");
		
		transformer = (Transformer) context.getBean("cuentaToSearchResultTransformer");
		mapper      = (MapperExtended) context.getBean("dozerMapper");
		genericDao  = (GenericDao) context.getBean("genericDao");
//		veraz = (VerazService) context.getBean("verazService");
		veraz      = (NextelServices) context.getBean("nextelServices");
		repository = (Repository) context.getBean("repository");
		normalizadorDomicilio = (NormalizadorDomicilio) context.getBean("normalizadorDomicilio");
		
		// Engancho el BOperator
		setGetAllBusinessOperator((GetAllBusinessOperator) context
				.getBean("getAllBusinessOperatorBean"));
	}

	public List<CuentaSearchResultDto> searchCuenta(CuentaSearchDto cuentaSearchDto) throws RpcExceptionMessages {
		List<CuentaSearchResultDto> dtoResult = new ArrayList<CuentaSearchResultDto>();
		CuentaSearchData cuentaSearchData = (CuentaSearchData) mapper.map(
				cuentaSearchDto, CuentaSearchData.class);

		cuentaSearchData.setCantidadResultados(cuentaSearchDto
				.getCantidadResultados());

		Vendedor vendedor = getVendedor();
		SessionContextLoader sessContext = (SessionContextLoader) context
				.getBean("sessionContextLoader");
		sessContext.getSessionContext().setVendedor(vendedor);
		List<Filter> filtros = (List<Filter>) context
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
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
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

	public GranCuentaDto saveGranCuenta(GranCuentaDto cuentaDto) {
		Long idCuenta = cuentaBusinessService.saveCuenta(cuentaDto,mapper);
		cuentaDto = mapper.map(repository.retrieve(GranCuenta.class, idCuenta), GranCuentaDto.class);
		return cuentaDto;
	}

	public CuentaDto saveCuenta(CuentaDto cuentaDto) {
		Long idCuenta = cuentaBusinessService.saveCuenta(cuentaDto,mapper);
		if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.DIV.getTipo()))
		   cuentaDto = (DivisionDto) mapper.map(repository.retrieve(Division.class, idCuenta), DivisionDto.class);
		else if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.SUS.getTipo()))
		   cuentaDto = (SuscriptorDto) mapper.map(repository.retrieve(Suscriptor.class, idCuenta), SuscriptorDto.class);
		else 
		   cuentaDto = mapper.map(repository.retrieve(Cuenta.class, idCuenta), CuentaDto.class);

		return cuentaDto;
		
	}
	
	
	public VerazInitializer getVerazInitializer() {
		VerazInitializer verazInitializer = new VerazInitializer();
		verazInitializer.setTiposDocumento(mapper.convertList(genericDao.getList(TipoDocumento.class),TipoDocumentoDto.class));
		verazInitializer.setSexos(mapper.convertList(genericDao.getList(Sexo.class),SexoDto.class));
		return verazInitializer;
	}
	
	public CrearContactoInitializer getCrearContactoInitializer() {
		CrearContactoInitializer crearContactoInitializer = new CrearContactoInitializer();
		crearContactoInitializer.setTiposDocumento(mapper.convertList(genericDao.getList(TipoDocumento.class),TipoDocumentoDto.class));
		List<SexoDto> listaSexos = mapper.convertList(genericDao.getList(Sexo.class),SexoDto.class);
		//borra los sexos Indefinido y Organizacion que no tienen que visualizarse
		listaSexos.remove(0);
		listaSexos.remove(2);
		crearContactoInitializer.setSexos(listaSexos);
		crearContactoInitializer.setCargos(mapper.convertList(genericDao.getList(Cargo.class),CargoDto.class));
		return crearContactoInitializer;
	}
	
	public VerazResponseDto consultarVeraz(PersonaDto personaDto) throws RpcExceptionMessages  {
        AppLogger.info("Iniciando consulta a Veraz...");
        VerazResponseDTO responseDTO = null;
        Sexo sexo = (Sexo) this.repository.retrieve(Sexo.class, personaDto.getSexo().getId());
		TipoDocumento tipoDocumento = (TipoDocumento) this.repository.retrieve(TipoDocumento.class, personaDto.getDocumento().getTipoDocumento().getId());
		long numeroDocumento = Long.parseLong(StringUtil.removeOcurrences(personaDto.getDocumento().getNumero(), '-'));
		AppLogger.debug("Parametros consulta a Veraz: " + tipoDocumento.getCodigoVeraz() + " / " + numeroDocumento + " / " + sexo.getCodigoVeraz() + "..."); 	
		Vendedor vendedor = getVendedor();
		
		VerazRequestDTO verazRequestDTO =  new VerazRequestDTO();
		verazRequestDTO.setNroDoc(numeroDocumento);
		verazRequestDTO.setSexo(sexo.getCodigoVeraz());
		verazRequestDTO.setTipoDoc(tipoDocumento.getCodigoVeraz());
		//verazRequestDTO.setVerazVersion(vendedor.getVerazVersion());
		
		try {
			responseDTO = this.veraz.searchPerson(verazRequestDTO);
		} catch (Exception e) {
			AppLogger.error(e);
			AppLogger.error("Error consultando Veraz para la siguiente persona: \n" + personaDto.toString());
			throw ExceptionUtil.wrap(e);
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
	
	public CuentaDto selectCuenta(Long cuentaId, String cod_vantive) throws RpcExceptionMessages {
		Cuenta cuenta = null;
		CuentaDto cuentaDto = null;
		try {
			if (cuentaId == 0){
				throw new RpcExceptionMessages("No tiene permisos para ver esta cuenta.");
			} else {
				if (cod_vantive!=null && !cod_vantive.equals("***") && (!cod_vantive.equals("null"))){
					cuenta = selectCuentaBusinessOperator.getCuentaYLockear(cod_vantive, this.getVendedor());
				} else {
					cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(cuentaId);
				}
				String categoriaCuenta = cuenta.getCategoriaCuenta().getDescripcion();
				if (categoriaCuenta.equals(TipoCuentaEnum.CTA.getTipo())) {
					cuentaDto = (GranCuentaDto) mapper.map((GranCuenta)cuenta, GranCuentaDto.class);
				} else if (categoriaCuenta.equals(TipoCuentaEnum.DIV.getTipo())) {
					cuentaDto = (DivisionDto) mapper.map((Division)cuenta, DivisionDto.class);
				} else if (categoriaCuenta.equals(TipoCuentaEnum.SUS.getTipo())) {
					cuentaDto = (SuscriptorDto) mapper.map((Suscriptor)cuenta, SuscriptorDto.class);
				}
			}
		} catch (Exception e) {
			AppLogger.info("ERROR al seleccionar cuenta: " + e.getMessage());
			throw ExceptionUtil.wrap(e);
		}
		return cuentaDto;
	}

	/**
	 * 
	 */
	public GranCuentaDto reservaCreacionCuenta(DocumentoDto docDto) throws RpcExceptionMessages {
		GranCuenta cuenta = null;
		GranCuentaDto cuentaDto = null;
		Vendedor vendedor = getVendedor();
		Documento documento = getDocumento(docDto);
		SolicitudCuenta solicitudCta = repository.createNewObject(SolicitudCuenta.class);
		solicitudCta.setVendedor(vendedor);
		solicitudCta.setDocumento(documento);
		try {
			//crea
			cuenta = (GranCuenta)cuentaBusinessService.reservarCrearCta(solicitudCta);
			//lockea
			cuentaBusinessService.saveCuenta(selectCuentaBusinessOperator.getCuentaYLockear(cuenta.getCodigoVantive(), vendedor));
			//agrega contactos
			cuenta.addContactosCuenta(contactosCuentaBusinessOperator.obtenerContactosCuentas(cuenta.getId()));
			//mapea
			cuentaDto = (GranCuentaDto) mapper.map(cuenta, GranCuentaDto.class);
		} catch (NullPointerException npe) {
			cuenta = (GranCuenta) searchCuentaBusinessOperator.searchProspectAjenoEnCarga(documento);
			String nombre = cuenta.getVendedor().getResponsable().getNombre()+" "+cuenta.getVendedor().getResponsable().getApellido();
			String cargo  = cuenta.getVendedor().getResponsable().getCargo();
			throw new RpcExceptionMessages("El prospect/cliente tiene una operación en curso con otro vendedor. No puede ver sus datos. El " +cargo+ " es " +nombre);
		} catch (Exception e) {
			AppLogger.info("ERROR al reservarCrearCta: " + e.getMessage());
			throw ExceptionUtil.wrap(e);
		}
		return cuentaDto;
	}
	
	/**
	 * 
	 */
	public DivisionDto crearDivision(Long id_CuentaPadre) {
		Cuenta cuenta = repository.retrieve(Cuenta.class, id_CuentaPadre);
		return (DivisionDto) mapper.map(cuentaBusinessService.crearDivision(cuenta, getVendedor()), DivisionDto.class); 
	}

	/**
	 * 
	 */
	public SuscriptorDto crearSuscriptor(Long id_CuentaPadre) throws RpcExceptionMessages {
		Cuenta cuenta = repository.retrieve(Cuenta.class, id_CuentaPadre);
		return (SuscriptorDto) mapper.map(cuentaBusinessService.crearSuscriptor(cuenta, getVendedor()), SuscriptorDto.class); 
	}
	
	/**
	 * 
	 */
	public TarjetaCreditoValidatorResultDto validarTarjeta(String numeroTarjeta, Integer mesVto, Integer anoVto) throws RpcExceptionMessages {
		TarjetaCreditoValidatorResultDto resultDto = null;
		try {
			TarjetaCreditoValidatorResult tarjetaCreditoValidatorResult =tarjetaCreditoValidatorService.validate(numeroTarjeta, mesVto, anoVto);
			resultDto = (TarjetaCreditoValidatorResultDto) mapper.map(tarjetaCreditoValidatorResult, TarjetaCreditoValidatorResultDto.class);
		} catch (TarjetaCreditoValidatorServiceException e) {
			AppLogger.info("ERROR al reservarCrearCta: " + e.getMessage());
			throw ExceptionUtil.wrap(e);
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
     * @return
     */
    private Vendedor getVendedor() {
    	return sessionContextLoader.getSessionContext().getVendedor();
    	//return registroVendedores.getVendedor(((SFAUserCenter) sessionContext.getSessionContext().get("USER_CENTER")).getUsuario());
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

    private CondicionCuentaDto getCondicionCuenta(Cuenta cuenta) {
		CondicionCuenta cd1= cuenta.getCondicionCuenta();
		Long id = cd1.getId();
		String code = cd1.getCodigoVantive();
		String desc = cd1.getDescripcion();
		return new CondicionCuentaDto(id,code,desc);
    }
    /**
     * @author eSalvador 
     **/
	public NormalizarDomicilioResultDto normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar) throws RpcExceptionMessages {
		NormalizarDomicilioResultDto domicilioResultNormalizacion = null;
		try {
			NormalizarDomicilioRequest normalizarDomicilioRequest = new NormalizarDomicilioRequest();
			Domicilio domicilio = repository.createNewObject(Domicilio.class);
			mapper.map(domicilioANormalizar, domicilio);

			normalizarDomicilioRequest.populateFromDomicilio(domicilio);
			domicilioResultNormalizacion =  mapper.map(normalizadorDomicilio.normalizarDomicilio(normalizarDomicilioRequest),NormalizarDomicilioResultDto.class);
	
		} catch (MerlinException e) {
			throw ExceptionUtil.wrap(e);
		}
		return domicilioResultNormalizacion;
	}

    
    /**
     * @author eSalvador 
     **/
	public NormalizarCPAResultDto getDomicilioPorCPA(String cpa) throws RpcExceptionMessages {
		NormalizarCPAResultDto resultConCPANormalizado = null;
		try {
			resultConCPANormalizado = mapper.map(normalizadorDomicilio.normalizarCPA(cpa), NormalizarCPAResultDto.class);
		} catch (MerlinException e) {
			throw ExceptionUtil.wrap(e);
		}
		return resultConCPANormalizado;
	}

	/**
     * @author eSalvador 
     **/
	public List<ProvinciaDto> getProvinciasInitializer() {
		List<ProvinciaDto> listaProvincias = mapper.convertList(repository.getAll(Provincia.class), ProvinciaDto.class);
		return listaProvincias;
	}
}

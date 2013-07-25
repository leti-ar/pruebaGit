package ar.com.nextel.sfa.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;

import org.apache.commons.collections.Transformer;
import org.dozer.MappingException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.constants.KnownInstanceIdentifier;
import ar.com.nextel.business.cuentas.create.businessUnits.SolicitudCuenta;
import ar.com.nextel.business.cuentas.migrator.legacy.dto.DocDigitalizadoLegacyDto;
import ar.com.nextel.business.cuentas.permanencia.PermanenciaService;
import ar.com.nextel.business.cuentas.search.SearchCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.search.businessUnits.CuentaSearchData;
import ar.com.nextel.business.cuentas.search.result.CuentaSearchResult;
import ar.com.nextel.business.cuentas.tarjetacredito.TarjetaCreditoValidatorResult;
import ar.com.nextel.business.cuentas.tarjetacredito.TarjetaCreditoValidatorServiceAxisImpl;
import ar.com.nextel.business.cuentas.tarjetacredito.TarjetaCreditoValidatorServiceException;
import ar.com.nextel.business.describable.GetAllBusinessOperator;
import ar.com.nextel.business.externalConnection.exception.MerlinException;
import ar.com.nextel.business.legacy.avalon.AvalonSystem;
import ar.com.nextel.business.legacy.avalon.dto.CantidadEquiposDTO;
import ar.com.nextel.business.legacy.avalon.exception.AvalonSystemException;
import ar.com.nextel.business.legacy.vantive.VantiveSystem;
import ar.com.nextel.business.personas.normalizarDomicilio.NormalizadorDomicilio;
import ar.com.nextel.business.personas.normalizarDomicilio.businessUnits.NormalizarDomicilioRequest;
import ar.com.nextel.components.filter.Filter;
import ar.com.nextel.components.knownInstances.retrievers.message.MessageRetriever;
import ar.com.nextel.components.knownInstances.retrievers.model.KnownInstanceRetriever;
import ar.com.nextel.components.message.Message;
import ar.com.nextel.exception.LegacyDAOException;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.Banco;
import ar.com.nextel.model.cuentas.beans.CalifCrediticia;
import ar.com.nextel.model.cuentas.beans.Calificacion;
import ar.com.nextel.model.cuentas.beans.Caratula;
import ar.com.nextel.model.cuentas.beans.Cargo;
import ar.com.nextel.model.cuentas.beans.CategoriaCuenta;
import ar.com.nextel.model.cuentas.beans.ClaseCuenta;
import ar.com.nextel.model.cuentas.beans.Com;
import ar.com.nextel.model.cuentas.beans.CompPago;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.Division;
import ar.com.nextel.model.cuentas.beans.EstadoCreditBancoCentral;
import ar.com.nextel.model.cuentas.beans.Firmante;
import ar.com.nextel.model.cuentas.beans.FormaPago;
import ar.com.nextel.model.cuentas.beans.GranCuenta;
import ar.com.nextel.model.cuentas.beans.Proveedor;
import ar.com.nextel.model.cuentas.beans.RiskCode;
import ar.com.nextel.model.cuentas.beans.ScoreVeraz;
import ar.com.nextel.model.cuentas.beans.Suscriptor;
import ar.com.nextel.model.cuentas.beans.TipoCanalVentas;
import ar.com.nextel.model.cuentas.beans.TipoContribuyente;
import ar.com.nextel.model.cuentas.beans.TipoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.model.cuentas.beans.ValidacionDomicilio;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.cuentas.beans.VerazNosis;
import ar.com.nextel.model.oportunidades.beans.CuentaPotencial;
import ar.com.nextel.model.oportunidades.beans.EstadoOportunidad;
import ar.com.nextel.model.oportunidades.beans.MotivoNoCierre;
import ar.com.nextel.model.oportunidades.beans.OportunidadNegocio;
import ar.com.nextel.model.oportunidades.beans.Reserva;
import ar.com.nextel.model.oportunidades.beans.Rubro;
import ar.com.nextel.model.personas.beans.Documento;
import ar.com.nextel.model.personas.beans.Domicilio;
import ar.com.nextel.model.personas.beans.GrupoDocumento;
import ar.com.nextel.model.personas.beans.Provincia;
import ar.com.nextel.model.personas.beans.Sexo;
import ar.com.nextel.model.personas.beans.TipoDocumento;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.services.nextelServices.NextelServices;
import ar.com.nextel.services.nextelServices.veraz.dto.VerazRequestDTO;
import ar.com.nextel.services.nextelServices.veraz.dto.VerazResponseDTO;
import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.BancoDto;
import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CalifCrediticiaDto;
import ar.com.nextel.sfa.client.dto.CalificacionDto;
import ar.com.nextel.sfa.client.dto.CantEquiposDTO;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.ClaseCuentaDto;
import ar.com.nextel.sfa.client.dto.ComDto;
import ar.com.nextel.sfa.client.dto.CompPagoDto;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CrearCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.DocDigitalizadosDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoCreditBancoCentralDto;
import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.FirmanteDto;
import ar.com.nextel.sfa.client.dto.FormaPagoDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.GrupoDocumentoDto;
import ar.com.nextel.sfa.client.dto.MotivoNoCierreDto;
import ar.com.nextel.sfa.client.dto.NormalizarCPAResultDto;
import ar.com.nextel.sfa.client.dto.NormalizarDomicilioResultDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.PrioridadDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.ProvinciaDto;
import ar.com.nextel.sfa.client.dto.ReservaDto;
import ar.com.nextel.sfa.client.dto.RiskCodeDto;
import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.ScoreVerazDto;
import ar.com.nextel.sfa.client.dto.ServicioContratoDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.TipoCanalVentasDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.dto.ValidacionDomicilioDto;
import ar.com.nextel.sfa.client.dto.VerazNosisDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.CaratulaInitializer;
import ar.com.nextel.sfa.client.initializer.CrearContactoInitializer;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;
import ar.com.nextel.sfa.server.businessservice.CuentaBusinessService;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.nextel.util.StringUtil;
import ar.com.nextel.web.download.DownloadService;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

public class CuentaRpcServiceImpl extends RemoteService implements CuentaRpcService {

	private static final long serialVersionUID = 1L;
	private static final String CONSULTA_VERAZ_SERVICE="veraz-rtf";
	private static final String CHARSET = "UTF-8";
	private WebApplicationContext context;
	private SearchCuentaBusinessOperator searchCuentaBusinessOperator;
	private TarjetaCreditoValidatorServiceAxisImpl tarjetaCreditoValidatorService;
	private CuentaBusinessService cuentaBusinessService;
	
	private Transformer transformer;
	private MapperExtended mapper;
	private GetAllBusinessOperator getAllBusinessOperator;
	private NextelServices veraz;
	private DownloadService	download;
	private Repository repository;
	private NormalizadorDomicilio normalizadorDomicilio;
	private SessionContextLoader sessionContextLoader;
	private MessageRetriever messageRetriever;
	private AvalonSystem avalonSystem;
	private PermanenciaService permanencia;

	private static final String ASOCIAR_CUENTA_A_OPP_ERROR = "La cuenta ya existe. No puede asociarse a la Oportunidad.";
	private static final String ERROR_OPER_OTRO_VENDEDOR = "El prospect/cliente tiene una operación en curso con otro vendedor. No puede ver sus datos. El {1} es {2}";
	private static final String ERROR_OPORTUNIDAD_VENCIDA = "La oportunidad/Reserva está vencida";

	private static final String queryNameSexoSinIndefinido = "SEXO_SIN_INDEFINIDO";
	private static final String QUERY_NAME_CLASE_CUENTA_POR_VENDEDOR = "CLASE_CUENTAS_POR_VENDEDOR";
	
	private KnownInstanceRetriever knownInstanceRetriever;
	private static final String VALID_EXIST_TRIPTICO = "VALID_EXIST_TRIPTICO";
	private VantiveSystem vantiveSystem;
	//#LF
	private static final String QUERY_VALID_EECC = "VALID_EECC";
	private static final String QUERY_AUTOCOMP_VERAZ = "QUERY_AUTOCOMP_VERAZ";

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		sessionContextLoader = (SessionContextLoader) context.getBean("sessionContextLoader");
		searchCuentaBusinessOperator = (SearchCuentaBusinessOperator) context
				.getBean("searchCuentaBusinessOperatorBean");
		cuentaBusinessService = (CuentaBusinessService) context.getBean("cuentaBusinessService");
		tarjetaCreditoValidatorService = (TarjetaCreditoValidatorServiceAxisImpl) context
				.getBean("tarjetaCreditoValidatorService");
		transformer = (Transformer) context.getBean("cuentaToSearchResultTransformer");
		mapper = (MapperExtended) context.getBean("dozerMapper");
		veraz = (NextelServices) context.getBean("nextelServices");
		download = (DownloadService) context.getBean("downloadService");
		repository = (Repository) context.getBean("repository");
		normalizadorDomicilio = (NormalizadorDomicilio) context.getBean("normalizadorDomicilio");
		messageRetriever = (MessageRetriever) context.getBean("messageRetriever");
		avalonSystem = (AvalonSystem) context.getBean("avalonSystemBean");

		knownInstanceRetriever = (KnownInstanceRetriever) context.getBean("knownInstancesRetriever");
		vantiveSystem = (VantiveSystem) context.getBean("vantiveSystemBean");
		
		setGetAllBusinessOperator((GetAllBusinessOperator) context.getBean("getAllBusinessOperatorBean"));
		permanencia = (PermanenciaService) context.getBean("permanenciaService");
	}

	public List<CuentaSearchResultDto> searchCuenta(CuentaSearchDto cuentaSearchDto)
			throws RpcExceptionMessages {
		List<CuentaSearchResultDto> dtoResult = new ArrayList<CuentaSearchResultDto>();
		cuentaSearchDto.toUppercase();
		CuentaSearchData cuentaSearchData = (CuentaSearchData) mapper.map(cuentaSearchDto,
				CuentaSearchData.class);
		cuentaSearchData.setCantidadResultados(cuentaSearchDto.getCantidadResultados());

		List<Filter> filtros = (List<Filter>) context.getBean("vendedorCuentaSearchFilterDefinitions");
		try {
			List<CuentaSearchResult> result = searchCuentaBusinessOperator.searchCuentas(cuentaSearchData,
					getVendedor(), filtros, transformer, cuentaSearchDto.getOffset(), cuentaSearchDto
							.getCantidadResultados());
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
	 * @author eSalvador TODO: Quitar el HardCode cuando se logre obtener los datos reales de la Db.
	 **/
	public BuscarCuentaInitializer getBuscarCuentaInitializer() {

		List<GrupoDocumentoDto> listaGrupoDoc = mapper.convertList(repository.getAll(GrupoDocumento.class),
				GrupoDocumentoDto.class);
		List<CategoriaCuentaDto> listaCategorias = mapper.convertList(repository
				.getAll(CategoriaCuenta.class), CategoriaCuentaDto.class);

		List<BusquedaPredefinidaDto> listaBusquedaPredef = new ArrayList<BusquedaPredefinidaDto>();
		listaBusquedaPredef.add(0, new BusquedaPredefinidaDto(1, "Ctas. propias"));
		listaBusquedaPredef.add(1, new BusquedaPredefinidaDto(3, "Últimas consultadas"));

		List<String> listaResult = new ArrayList<String>();
		String cantResult = "10;25;50;75;100";
		listaResult = Arrays.asList(cantResult.split(";"));

		BuscarCuentaInitializer buscarDTOinit = new BuscarCuentaInitializer(listaBusquedaPredef, listaResult,
				listaCategorias, listaGrupoDoc);
		return buscarDTOinit;
	}

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
		AgregarCuentaInitializer buscarDTOinit = new AgregarCuentaInitializer();
		buscarDTOinit.setTiposContribuyentes(mapper.convertList(repository.getAll(TipoContribuyente.class),
				TipoContribuyenteDto.class));
		buscarDTOinit.setTiposDocumento(mapper.convertList(repository.getAll(TipoDocumento.class),
				TipoDocumentoDto.class));
		buscarDTOinit.setRubro(mapper.convertList(repository.getAll(Rubro.class), RubroDto.class));
		List<?> listaSexos = this.getRepository().executeCustomQuery(queryNameSexoSinIndefinido);
		buscarDTOinit.setSexo(mapper.convertList(listaSexos, SexoDto.class));
		// buscarDTOinit.setSexo(mapper.convertList(genericDao.getList(Sexo.class), SexoDto.class));
		buscarDTOinit
				.setFormaPago(mapper.convertList(repository.getAll(FormaPago.class), FormaPagoDto.class));
		
		// lo modifico para que traigo solo los del vendedor
		List<?> listaClaseCuenta = this.getRepository().executeCustomQuery(QUERY_NAME_CLASE_CUENTA_POR_VENDEDOR,sessionContextLoader.getVendedor().getTipoVendedor().getId());
		buscarDTOinit.setClaseClientePorVendedor(mapper.convertList(listaClaseCuenta,ClaseCuentaDto.class));

		buscarDTOinit.setClaseCliente(mapper.convertList(repository.getAll(ClaseCuenta.class),
				ClaseCuentaDto.class));
		
		buscarDTOinit.setProveedorAnterior(mapper.convertList(repository.getAll(Proveedor.class),
				ProveedorDto.class));
		buscarDTOinit.setCargo(mapper.convertList(repository.getAll(Cargo.class), CargoDto.class));
		buscarDTOinit.setTipoCuentaBancaria(mapper.convertList(repository.getAll(TipoCuentaBancaria.class),
				TipoCuentaBancariaDto.class));
		buscarDTOinit.setTipoTarjeta(mapper.convertList(repository.getAll(TipoTarjeta.class),
				TipoTarjetaDto.class));
		buscarDTOinit.setTipoCanalVentas(mapper.convertList(repository.getAll(TipoCanalVentas.class),
				TipoCanalVentasDto.class));
		buscarDTOinit.setMotivoNoCierre(mapper.convertList(repository.getAll(MotivoNoCierre.class),
				MotivoNoCierreDto.class));
		buscarDTOinit.setEstadoOportunidad(mapper.convertList(repository.getAll(EstadoOportunidad.class),
				EstadoOportunidadDto.class));
		buscarDTOinit.setAnio(Calendar.getInstance().get(Calendar.YEAR));
		return buscarDTOinit;
	}

	public VerazInitializer getVerazInitializer() {
		VerazInitializer verazInitializer = new VerazInitializer();
		verazInitializer.setTiposDocumento(mapper.convertList(repository.getAll(TipoDocumento.class),
				TipoDocumentoDto.class));
		List listaSexos = this.getRepository().executeCustomQuery(queryNameSexoSinIndefinido);
		verazInitializer.setSexos(mapper.convertList(listaSexos, SexoDto.class));
		// verazInitializer.setSexos(mapper.convertList(genericDao.getList(Sexo.class), SexoDto.class));
		return verazInitializer;
	}

	public CrearContactoInitializer getCrearContactoInitializer() {
		CrearContactoInitializer crearContactoInitializer = new CrearContactoInitializer();
		crearContactoInitializer.setTiposDocumento(mapper.convertList(repository.getAll(TipoDocumento.class),
				TipoDocumentoDto.class));
		List<SexoDto> listaSexosCompleta = mapper.convertList(repository.getAll(Sexo.class), SexoDto.class);
		List<SexoDto> listaSexos = new ArrayList<SexoDto>();
		for (Iterator<SexoDto> iterator = listaSexosCompleta.iterator(); iterator.hasNext();) {
			SexoDto sexo = iterator.next();
			if (("F".equals(sexo.getCodigoVantive())) || ("M".equals(sexo.getCodigoVantive()))) {
				listaSexos.add(sexo);
			}
		}
		crearContactoInitializer.setSexos(listaSexos);
		crearContactoInitializer
				.setCargos(mapper.convertList(repository.getAll(Cargo.class), CargoDto.class));
		return crearContactoInitializer;
	}

	public VerazResponseDto consultarVeraz(PersonaDto personaDto) throws RpcExceptionMessages {
		AppLogger.info("Iniciando consulta a Veraz...");
		VerazResponseDTO responseDTO = null;
		VerazRequestDTO verazRequestDTO = createVerazRequestDTO(personaDto);
		try {
			responseDTO = this.veraz.searchPerson(verazRequestDTO);
		} catch (Exception e) {
			AppLogger.error(e);
			AppLogger.error("Error consultando Veraz para la siguiente persona: \n" + personaDto.toString());
			//MGR - Se agrega mensaje para identificar que hubo problema con el Veraz
			throw ExceptionUtil.wrap("Se produjo un error al consultar al veraz.",e);
		}

		VerazResponseDto responseDto = mapper.map(responseDTO, VerazResponseDto.class);
		responseDto.setMensaje(responseDTO.getMensaje());
		AppLogger.info("Consulta a Veraz finalizada.");
		return responseDto;
	}

	public VerazResponseDto consultarDetalleVeraz(PersonaDto personaDto) throws RpcExceptionMessages {
		AppLogger.info("Iniciando consulta a Veraz...");
		VerazResponseDTO responseDTO = null;
		VerazRequestDTO verazRequestDTO = createVerazRequestDTO(personaDto);
		try {
			responseDTO = this.veraz.generateDetail(verazRequestDTO);
		} catch (Exception e) {
			AppLogger.error(e);
			AppLogger.error("Error consultando Veraz para la siguiente persona: \n" + personaDto.toString());
			//MGR - Se agrega mensaje para identificar que hubo problema con el Veraz
			throw ExceptionUtil.wrap("Se produjo un error al consultar al veraz.",e);
		}

		VerazResponseDto responseDto = mapper.map(responseDTO, VerazResponseDto.class);
		AppLogger.info("Mensaje obtenido por el veraz: "+ responseDTO.getMensaje());
		responseDto.setMensaje(responseDTO.getMensaje());
		AppLogger.info("Consulta a Veraz finalizada.");
		return responseDto;
	}

	
	private VerazRequestDTO createVerazRequestDTO(PersonaDto personaDto) {
		Sexo sexo = (Sexo) this.repository.retrieve(Sexo.class, personaDto.getSexo().getId());
		TipoDocumento tipoDocumento = (TipoDocumento) this.repository.retrieve(TipoDocumento.class,
				personaDto.getDocumento().getTipoDocumento().getId());
		long numeroDocumento = Long.parseLong(StringUtil.removeOcurrences(personaDto.getDocumento()
				.getNumero(), '-'));
		AppLogger.debug("Parametros consulta a Veraz: " + tipoDocumento.getCodigoVeraz() + " / "
				+ numeroDocumento + " / " + sexo.getCodigoVeraz() + "...");

		VerazRequestDTO verazRequestDTO = new VerazRequestDTO();
		verazRequestDTO.setNroDoc(numeroDocumento);
		verazRequestDTO.setSexo(sexo.getCodigoVeraz());
		verazRequestDTO.setTipoDoc(tipoDocumento.getCodigoVeraz());
		// verazRequestDTO.setVerazVersion(vendedor.getVerazVersion());
		return verazRequestDTO;
	}
	
	public String leerConsultaDetalleVeraz(String verazFileName) throws RpcExceptionMessages {
		AppLogger.info("Iniciando leer la consulta del detalle a Veraz...");
		
		File archVeraz = veraz.obtenerArchivoVeras(verazFileName);
		
		if(archVeraz != null){
			try {
				return new String(FileCopyUtils.copyToByteArray(archVeraz));
			
			} catch (IOException e) {
				AppLogger.error(e);
				AppLogger.error("Error leyendo detalle Veraz \n");
				throw ExceptionUtil.wrap(e);
			}
		}else{
			AppLogger.error("Error leyendo detalle Veraz. No se encontro el archivo " + verazFileName + "\n");
			return null;
		}
	}

	//MGR - #960
	public VerazResponseDto consultarVeraz(String codVantive) throws RpcExceptionMessages {
		ArrayList<Object> list = (ArrayList<Object>) this.repository.find("from Cuenta c where c.codigoVantive = ?", codVantive);
		if(!list.isEmpty()){
			Cuenta cta = (Cuenta) list.get(0);
			PersonaDto personaDto = mapper.map(cta.getPersona(), PersonaDto.class);
			return consultarVeraz(personaDto);
		}
		else{
			return new VerazResponseDto();
		}
	}

	public VerazResponseDto consultarVeraz(Long cuentaId) throws RpcExceptionMessages {
		ArrayList<Object> list = (ArrayList<Object>) this.repository.find("from Cuenta c where c.id = ?", cuentaId);
		if(!list.isEmpty()){
			Cuenta cta = (Cuenta) list.get(0);
			PersonaDto personaDto = mapper.map(cta.getPersona(), PersonaDto.class);
			return consultarVeraz(personaDto);
		}
		else{
			return new VerazResponseDto();
		}
	}

	public VerazResponseDto consultarDetalleVeraz(Long cuentaId) throws RpcExceptionMessages {
		ArrayList<Object> list = (ArrayList<Object>) this.repository.find("from Cuenta c where c.id = ?", cuentaId);
		if(!list.isEmpty()){
			Cuenta cta = (Cuenta) list.get(0);
			PersonaDto personaDto = mapper.map(cta.getPersona(), PersonaDto.class);
			return consultarDetalleVeraz(personaDto);
		}
		else{
			return new VerazResponseDto();
		}
	}

	public VerazResponseDto consultarDetalleVeraz(Long cuentaId,Long caratulaId) throws RpcExceptionMessages {
		VerazResponseDto verazResponseDto = consultarDetalleVeraz(cuentaId);
		if (caratulaId != null) {
			this.cuentaBusinessService.saveArchivoVeraz(caratulaId, verazResponseDto.getFileName());
		}
		return verazResponseDto;
	}
	
	public CuentaDto saveCuenta(CuentaDto cuentaDto) throws RpcExceptionMessages {
		try {
			Long idCuenta = cuentaBusinessService.saveCuenta(cuentaDto, mapper, sessionContextLoader
					.getVendedor());
			Cuenta cuenta = null;
			if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(
					KnownInstanceIdentifier.DIVISION.getKey())) {
				cuenta = repository.retrieve(Division.class, idCuenta);
				cuentaDto = (DivisionDto) mapper.map(cuenta, DivisionDto.class);
			} else if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(
					KnownInstanceIdentifier.SUSCRIPTOR.getKey())) {
				cuenta = repository.retrieve(Suscriptor.class, idCuenta);
				cuentaDto = (SuscriptorDto) mapper.map(cuenta, SuscriptorDto.class);
			} else {
				cuenta = repository.retrieve(Cuenta.class, idCuenta);
				cuentaDto = mapper.map(cuenta, GranCuentaDto.class);
			}
				cuentaBusinessService.cargarFacturaElectronica(cuentaDto,cuenta,mapper,
						cuenta.isEnCarga());
				} catch (MappingException e) {
			AppLogger.error("*** Error de mapeo al actualizar la cuenta: " + cuentaDto.getCodigoVantive()
					+ " *** ", e);
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return cuentaDto;
	}

	//MGR - #1466
	public CuentaDto selectCuenta(Long cuentaId, String cod_vantive, boolean filtradoPorDni, boolean deberiaLockear)
			throws RpcExceptionMessages {
		CuentaDto cuentaDto = null;
		Cuenta cuenta = cuentaBusinessService.selectCuenta(cuentaId, cod_vantive, getVendedor(),
				filtradoPorDni, mapper, deberiaLockear);
		String categoriaCuenta = cuenta.getCategoriaCuenta().getDescripcion();
		if (categoriaCuenta.equals(KnownInstanceIdentifier.GRAN_CUENTA.getKey())) {
			cuentaDto = (GranCuentaDto) mapper.map((GranCuenta) cuenta, GranCuentaDto.class);
		} else if (categoriaCuenta.equals(KnownInstanceIdentifier.DIVISION.getKey())) {
			cuentaDto = (DivisionDto) mapper.map((Division) cuenta, DivisionDto.class);
		} else if (categoriaCuenta.equals(KnownInstanceIdentifier.SUSCRIPTOR.getKey())) {
			cuentaDto = (SuscriptorDto) mapper.map((Suscriptor) cuenta, SuscriptorDto.class);
		}
		// if(!cuenta.isEnCarga()){
		cuentaBusinessService.cargarFacturaElectronica(cuentaDto, cuenta, mapper, cuenta.isEnCarga());
		// }else if(cuenta.getFacturaElectronica() != null){
		// FacturaElectronicaDto fdto = (FacturaElectronicaDto) mapper.map(cuenta.getFacturaElectronica(),
		// FacturaElectronicaDto.class);
		// cuentaDto.setFacturaElectronica(fdto);
		//			
		// }

		return cuentaDto;
	}

	public CuentaDto reservaCreacionCuenta(CrearCuentaDto crearCuentaDto) throws RpcExceptionMessages {
		Cuenta cuenta = null;
		CuentaDto cuentaDto = null;
		Vendedor vendedor = getVendedor();
		Documento documento = getDocumento(crearCuentaDto.getDocumento());

		SolicitudCuenta solicitudCta = repository.createNewObject(SolicitudCuenta.class);
		solicitudCta.setVendedor(vendedor);
		solicitudCta.setDocumento(documento);

		if (crearCuentaDto.getIdOportunidadNegocio() != null) {
			solicitudCta.setVentaPotencialOrigen((CuentaPotencial) repository.retrieve(CuentaPotencial.class,
					crearCuentaDto.getIdOportunidadNegocio()));
		}
		try {
			// crea
			cuenta = cuentaBusinessService.reservarCrearCta(solicitudCta, mapper);

			if (cuenta == null) {
				cuenta = searchCuentaBusinessOperator.searchProspectAjenoEnCarga(documento);
				String nombre = cuenta.getVendedor().getResponsable().getNombre() + " "
						+ cuenta.getVendedor().getResponsable().getApellido();
				String cargo = cuenta.getVendedor().getResponsable().getCargo();
				String errMsg = ERROR_OPER_OTRO_VENDEDOR.replaceAll("\\{1\\}", cargo);
				errMsg = errMsg.replaceAll("\\{2\\}", nombre);
				throw new RpcExceptionMessages(errMsg);
			}
			cuenta = repository.retrieve(Cuenta.class, cuenta.getId());
			cuentaBusinessService.validarAccesoCuenta(cuenta, getVendedor(), true);
			if (asociarCuentaSiCorresponde(solicitudCta, cuenta)) {
				// lockea
				cuentaBusinessService.saveCuenta(cuenta, vendedor);
				// mapea
				if (cuenta.esGranCuenta()) {
					cuentaDto = (GranCuentaDto) mapper.map(cuenta, GranCuentaDto.class);
				} else if (cuenta.esDivision()) {
					cuentaDto = (DivisionDto) mapper.map(cuenta, DivisionDto.class);
				} else if (cuenta.esSuscriptor()) {
					cuentaDto = (SuscriptorDto) mapper.map(cuenta, SuscriptorDto.class);
				}

				cuentaBusinessService.cargarFacturaElectronica(cuentaDto,cuenta,mapper, cuenta.isEnCarga());
		
			}
		} catch (BusinessException be) {
			Message message = (Message) messageRetriever.getMessage(be.getMessageIdentifier(), be
					.getParameters().toArray());
			throw new RpcExceptionMessages(message.getDescription());
		} catch (RpcExceptionMessages rem) {
			throw new RpcExceptionMessages(rem.getMessage());
		} catch (Exception e) {
			AppLogger.info("ERROR al reservarCrearCta: " + e.getMessage());
			throw ExceptionUtil.wrap(e);
		}
		return cuentaDto;
	}

	private boolean asociarCuentaSiCorresponde(SolicitudCuenta solicitudCuenta, Cuenta cuenta)
			throws RpcExceptionMessages {
		boolean isValid;
		if (solicitudCuenta.hasVentaPotencialOrigen()) {
			// Intentará asociar
			if (cuenta.isProspectEnCarga() && cuenta.getCuentaPotencialOrigen() == null) {
				// Se puede asociar correctamente
				solicitudCuenta.getVentaPotencialOrigen().asociarCuenta(cuenta);
				isValid = true;
			} else if (!cuenta.isProspectEnCarga()) {
				// Operación inválida. La cuenta ya existe o ya está asociada
				isValid = false;
				throw new RpcExceptionMessages(ASOCIAR_CUENTA_A_OPP_ERROR);
			} else {
				isValid = true;
			}
		} else {
			// No intenta asociar Opp. No hay problemas.
			isValid = true;
		}
		return isValid;
	}

	public DivisionDto crearDivision(Long id_CuentaPadre) throws RpcExceptionMessages {
		AppLogger.info("Creando Division...");
		DivisionDto divisionDto;
		try {
			Cuenta cuenta = obtenerCtaPadreSinLockear(id_CuentaPadre, KnownInstanceIdentifier.DIVISION
					.getKey());
			divisionDto = (DivisionDto) mapper.map(
					cuentaBusinessService.crearDivision(cuenta, getVendedor()), DivisionDto.class);
		} catch (Exception e) {
			throw new RpcExceptionMessages(e.getMessage());
		}
		AppLogger.info("Creacion de Division finalizada.");
		return divisionDto;
	}

	public SuscriptorDto crearSuscriptor(Long id_CuentaPadre) throws RpcExceptionMessages {
		AppLogger.info("Creando Suscriptor...");
		SuscriptorDto suscriptorDto;
		try {
			Cuenta cuenta = obtenerCtaPadreSinLockear(id_CuentaPadre, KnownInstanceIdentifier.SUSCRIPTOR
					.getKey());
			suscriptorDto = (SuscriptorDto) mapper.map(cuentaBusinessService.crearSuscriptor(cuenta,
					getVendedor()), SuscriptorDto.class);
		} catch (Exception e) {
			throw new RpcExceptionMessages(e.getMessage());
		}
		AppLogger.info("Creacion de Suscriptor finalizada.");
		return suscriptorDto;
	}

	public TarjetaCreditoValidatorResultDto validarTarjeta(String numeroTarjeta, Integer mesVto,
			Integer anoVto) throws RpcExceptionMessages {
		TarjetaCreditoValidatorResultDto resultDto = null;
		try {
			TarjetaCreditoValidatorResult tarjetaCreditoValidatorResult = tarjetaCreditoValidatorService
					.validate(numeroTarjeta, mesVto, anoVto);
			resultDto = (TarjetaCreditoValidatorResultDto) mapper.map(tarjetaCreditoValidatorResult,
					TarjetaCreditoValidatorResultDto.class);
		} catch (TarjetaCreditoValidatorServiceException e) {
			AppLogger.info("ERROR al validar tarjeta: " + e.getMessage());
			throw ExceptionUtil.wrap(e);
		}
		return resultDto;
	}

	/** Devuelve la reservaDto o la oportunidadDto según sea el caso */
	public CuentaPotencialDto getOportunidadNegocio(Long cuenta_id) throws RpcExceptionMessages {
		AppLogger.info("Obteniendo venta potencial con id: " + cuenta_id.longValue());
		CuentaPotencial cuentaPotencial = (CuentaPotencial) repository.retrieve(CuentaPotencial.class,
				cuenta_id);
		CuentaPotencialDto cuentaPotencialDto = mapper.map(cuentaPotencial, CuentaPotencialDto.class);
		if (!cuentaPotencialDto.isEsReserva()) {
			OportunidadNegocio oportunidad = (OportunidadNegocio) repository.retrieve(
					OportunidadNegocio.class, cuenta_id);
			OportunidadNegocioDto oportunidadDto = mapper.map(oportunidad, OportunidadNegocioDto.class);
			if (Calendar.getInstance().getTime().after(oportunidad.getFechaVencimiento())) {
				throw new RpcExceptionMessages(ERROR_OPORTUNIDAD_VENCIDA);
			}
			cuentaBusinessService.marcarOppComoConsultada((OportunidadNegocio) cuentaPotencial);
			if (oportunidad.getPrioridad() != null)
				oportunidadDto.setPrioridadDto(new PrioridadDto(oportunidad.getPrioridad().getId(),
						oportunidad.getPrioridad().getDescripcion()));
			String categoriaCuenta = oportunidadDto.getCuentaOrigen().getCategoriaCuenta().getDescripcion();
			if (categoriaCuenta.equals(KnownInstanceIdentifier.GRAN_CUENTA.getKey())) {
				oportunidadDto.setCuentaOrigen((GranCuentaDto) mapper.map((GranCuenta) oportunidad
						.getCuentaOrigen(), GranCuentaDto.class));
			} else if (categoriaCuenta.equals(KnownInstanceIdentifier.DIVISION.getKey())) {
				oportunidadDto.setCuentaOrigen((DivisionDto) mapper.map((Division) oportunidad
						.getCuentaOrigen(), DivisionDto.class));
			} else if (categoriaCuenta.equals(KnownInstanceIdentifier.SUSCRIPTOR.getKey())) {
				oportunidadDto.setCuentaOrigen((SuscriptorDto) mapper.map((Suscriptor) oportunidad
						.getCuentaOrigen(), SuscriptorDto.class));
			}
			AppLogger.info("Obtención finalizada");
			return oportunidadDto;
		} else {
			Reserva reserva = (Reserva) repository.retrieve(Reserva.class, cuenta_id);
			ReservaDto reservaDto = mapper.map(reserva, ReservaDto.class);
			String categoriaCuenta = reservaDto.getCuentaOrigen().getCategoriaCuenta().getDescripcion();
			if (categoriaCuenta.equals(KnownInstanceIdentifier.GRAN_CUENTA.getKey())) {
				reservaDto.setCuentaOrigen((GranCuentaDto) mapper.map((GranCuenta) reserva.getCuentaOrigen(),
						GranCuentaDto.class));
			} else if (categoriaCuenta.equals(KnownInstanceIdentifier.DIVISION.getKey())) {
				reservaDto.setCuentaOrigen((DivisionDto) mapper.map((Division) reserva.getCuentaOrigen(),
						DivisionDto.class));
			} else if (categoriaCuenta.equals(KnownInstanceIdentifier.SUSCRIPTOR.getKey())) {
				reservaDto.setCuentaOrigen((SuscriptorDto) mapper.map((Suscriptor) reserva.getCuentaOrigen(),
						SuscriptorDto.class));
			}
			AppLogger.info("Obtención finalizada");
			return reservaDto;
		}
	}

	public OportunidadNegocioDto updateEstadoOportunidad(OportunidadNegocioDto oportunidadDto)
			throws RpcExceptionMessages {
		return (OportunidadNegocioDto) mapper.map(cuentaBusinessService.updateEstadoOportunidad(
				oportunidadDto, mapper), OportunidadNegocioDto.class);
	}

	public List<CuentaDto> getCuentasAsociadasAVentaPotencial(Long idVentaPotencial)
			throws RpcExceptionMessages {
		CuentaPotencial cuentaPotencial = (CuentaPotencial) repository.retrieve(CuentaPotencial.class,
				idVentaPotencial);
		return (List<CuentaDto>) mapper.convertList(cuentaPotencial.getCuentasAsociadas(), CuentaDto.class);
	}

	private Vendedor getVendedor() {
		return sessionContextLoader.getSessionContext().getVendedor();
	}

	private Documento getDocumento(DocumentoDto docDto) {
		TipoDocumento tipoDoc = repository.retrieve(TipoDocumento.class, docDto.getTipoDocumento().getId());
		Documento doc = repository.createNewObject(Documento.class);
		doc.setTipoDocumento(tipoDoc);
		doc.setNumero(docDto.getNumero());
		return doc;
	}

	public NormalizarDomicilioResultDto normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar)
			throws RpcExceptionMessages {
//		NormalizarDomicilioResultDto domicilioResultNormalizacion = null;
		NormalizarDomicilioResultDto domicilioResultNormalizacion = new NormalizarDomicilioResultDto();
//		try {
			NormalizarDomicilioRequest normalizarDomicilioRequest = new NormalizarDomicilioRequest();
			Domicilio domicilio = repository.createNewObject(Domicilio.class);
			mapper.map(domicilioANormalizar, domicilio);
			normalizarDomicilioRequest.populateFromDomicilio(domicilio);
		//TODO CAM DESCOMENTAR!!	
//			domicilioResultNormalizacion = mapper.map(normalizadorDomicilio
//					.normalizarDomicilio(normalizarDomicilioRequest), NormalizarDomicilioResultDto.class);
			domicilioResultNormalizacion.setDireccion(domicilioANormalizar); //SACAR
			domicilioResultNormalizacion.setTipo("exito"); //SACAR
			
//		} catch (MerlinException e) {
//			throw ExceptionUtil.wrap(e);
//		}
		return domicilioResultNormalizacion;
	}

	public NormalizarCPAResultDto getDomicilioPorCPA(String cpa) throws RpcExceptionMessages {
//		NormalizarCPAResultDto resultConCPANormalizado = null;
		NormalizarCPAResultDto resultConCPANormalizado = new NormalizarCPAResultDto();
//		try {
//			resultConCPANormalizado = mapper.map(normalizadorDomicilio.normalizarCPA(cpa),
//					NormalizarCPAResultDto.class);
//		} catch (MerlinException e) {
//			throw ExceptionUtil.wrap(e);
//		}
		return resultConCPANormalizado;
	}

	public List<ProvinciaDto> getProvinciasInitializer() {
		return mapper.convertList(repository.getAll(Provincia.class), ProvinciaDto.class);
	}

	public Cuenta obtenerCtaPadreSinLockear(Long ctaPadreId, String categoriaCuenta)
			throws RpcExceptionMessages {
		return cuentaBusinessService.obtenerCtaPadreSinLockear(ctaPadreId, categoriaCuenta, getVendedor());
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	//MGR - Dado un codigo vantive, devuelve el numero de cuenta que le corresponde en SFA
	public Long selectCuenta(String codigoVantive) throws RpcExceptionMessages{
		Cuenta cuenta  = null;
		//MGR - #1466 - Esta llamada sigue funcionando como antes, entonces lockea
		cuenta = cuentaBusinessService.selectCuenta(null, codigoVantive, getVendedor(),
				true, mapper, true);
		if(cuenta != null){
			return cuenta.getId();
		}
		return null;
	}

	//MGR - #1466
	public List<CuentaDto> searchCuentasDto(CuentaSearchDto cuentaSearchDto, boolean deberiaLockear) throws RpcExceptionMessages{
		List<CuentaDto> cuentasDto = new ArrayList<CuentaDto>();
		CuentaDto auxCtaDto;
		try {
			List<CuentaSearchResultDto> cuentas = searchCuenta(cuentaSearchDto);
			for (CuentaSearchResultDto cuentaResult : cuentas) {
				
				if(cuentaSearchDto.getGrupoDocumentoId() != null){
					if(!cuentaResult.getNumero().contains("*")){
						Cuenta cta2 = cuentaBusinessService.selectCuenta(null, cuentaResult.getNumero(), getVendedor(),
								true, mapper, deberiaLockear);
						if(cta2.esGranCuenta()){
							auxCtaDto = mapper.map(cta2, CuentaDto.class);
							cuentasDto.add(auxCtaDto);
						}
					}
				}else{
					if(cuentaResult.getNumero().contains("*")){
						auxCtaDto = new CuentaDto();
						auxCtaDto.setCodigoVantive("***");
					}else{
						auxCtaDto = selectCuenta(cuentaResult.getId(), cuentaResult.getNumero(), false, deberiaLockear);
					}
					cuentasDto.add(auxCtaDto);
				}
			}
		} catch (RpcExceptionMessages e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return cuentasDto;
	}
	
	public List<ContratoViewDto> searchContratosActivos(CuentaDto ctaDto) throws RpcExceptionMessages{
		List<ContratoViewDto> contratos = new ArrayList<ContratoViewDto>();
		
		try {
			if(ctaDto.getCodigoVantive() != null && !ctaDto.getCodigoVantive().equals("")){
				contratos = mapper.convertList(
						avalonSystem.retriveContratosActivosFull(ctaDto.getCodigoVantive()), ContratoViewDto.class);
			}
		} catch (AvalonSystemException e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return contratos;
	}
	
	public List<ServicioContratoDto> searchServiciosContratados(Long numeroContrato) throws RpcExceptionMessages{
		List<ServicioContratoDto> servicios = new ArrayList<ServicioContratoDto>();
		
		try {
			if(numeroContrato != null){
				servicios = mapper.convertList(
						avalonSystem.retriveServiciosContratados(numeroContrato), ServicioContratoDto.class);
			}
		} catch (AvalonSystemException e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		return servicios;
	}
	
	public CaratulaInitializer getCaratulaInicializarte(){
		CaratulaInitializer initializer = new CaratulaInitializer();
		
		initializer.setValidDomicilio(mapper.convertList(repository.getAll(ValidacionDomicilio.class),
				ValidacionDomicilioDto.class));
		
		initializer.setBancos(mapper.convertList(repository.getAll(Banco.class),
				BancoDto.class));
		
		initializer.setTipoCtaBancaria(mapper.convertList(repository.getAll(TipoCuentaBancaria.class),
				TipoCuentaBancariaDto.class));
		
		initializer.setTipoTarjCred(mapper.convertList(repository.getAll(TipoTarjeta.class),
				TipoTarjetaDto.class));
		
		initializer.setCalifCrediticia(mapper.convertList(repository.getAll(CalifCrediticia.class),
				CalifCrediticiaDto.class));
		
		initializer.setCom(mapper.convertList(repository.getAll(Com.class),
				ComDto.class));
		
		initializer.setCalificacion(mapper.convertList(repository.getAll(Calificacion.class),
				CalificacionDto.class));
		
		//No cargo el Risk Code vacio
		Long idRiskCodeVacio = knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.RISK_CODE_VACIO);
		String query = "from RiskCode risk where risk.id not in(" + idRiskCodeVacio.toString() + ")";
		List<RiskCode> riskcodes = repository.find(query);
		initializer.setRiskCode(mapper.convertList(riskcodes,RiskCodeDto.class));

		initializer.setCompPago(mapper.convertList(repository.getAll(CompPago.class),
				CompPagoDto.class));
		
		initializer.setFirmante(mapper.convertList(repository.getAll(Firmante.class),
				FirmanteDto.class));
		
		initializer.setVerazNosis(mapper.convertList(repository.getAll(VerazNosis.class),
				VerazNosisDto.class));
		
		initializer.setEstadoCredBC(mapper.convertList(repository.getAll(EstadoCreditBancoCentral.class),
				EstadoCreditBancoCentralDto.class));
		
		
		return initializer;
	}
	
	public CaratulaDto crearCaratula(CuentaDto cuentaDto) throws RpcExceptionMessages{
		
		AppLogger.info("Creando Caratula para la cuenta: " + cuentaDto.getId());
		Caratula newCaratula = new Caratula();
		
		Cuenta cta = repository.retrieve(Cuenta.class, cuentaDto.getId());
		newCaratula.setCuenta(cta);
		
		if(cuentaDto.getCaratulas().isEmpty()){
			newCaratula.setDocumento("Crédito");
		}else{
			newCaratula.setDocumento("Anexo");
		}
		
		newCaratula.setUsuarioCreacion(getVendedor());
		newCaratula.setAntiguedad(cta.getFechaCreacion());
		newCaratula.setFechaCreacion(new Date());
		
		SolicitudServicio ultSSModificada = cuentaBusinessService.getUltimaSSModificada(cta.getId());
		
		if(ultSSModificada != null){
			newCaratula.setNroSS(ultSSModificada.getNumero());

			if(ultSSModificada.getFirmar()){
				newCaratula.setOkEECCAgente(Boolean.TRUE);
				newCaratula.setRiskCode((RiskCode)
						knownInstanceRetriever.getObject(KnownInstanceIdentifier.RISK_CODE_EECC_AGENTE));
			}
		}
		
		//Equipos Activos
		CantidadEquiposDTO resultDTO = new CantidadEquiposDTO();
		try {
			resultDTO = this.avalonSystem.retreiveEquiposPorEstado(cta.getCodigoVantive());
			newCaratula.setEquiposActivos(Long.parseLong(resultDTO.getCantidadActivos()));
        } catch (LegacyDAOException e) {
            newCaratula.setEquiposActivos(0L);
        }
		
		
		if(cuentaBusinessService.isUltimoDomicilioValidado(cta.getId())){
			newCaratula.setValidDomicilio((ValidacionDomicilio) 
					knownInstanceRetriever.getObject(KnownInstanceIdentifier.DOMICILIO_VALIDADO_X_EECC));
		}
		
		CaratulaDto caratulaDto = mapper.map(newCaratula, CaratulaDto.class);
		return caratulaDto;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public CaratulaDto confirmarCaratula(CaratulaDto caratulaDto) throws RpcExceptionMessages{
		Caratula caratula;
		//MGR - #3285 - Se separa el proceso en dos partes
		caratula =cuentaBusinessService.completarCaratula(caratulaDto);
		cuentaBusinessService.confirmarCaratula(caratula);
		mapper.map(caratula, caratulaDto);
		return caratulaDto;
	}
	
	public boolean validarExistenciaTriptico(String nro) throws RpcExceptionMessages{
		List result = null;
		try{
			AppLogger.info("Validando la existencia del número de solicitud: " + nro);
			result = repository.executeCustomQuery(VALID_EXIST_TRIPTICO, nro);
			AppLogger.info("Número de solicitud " + nro + " validado correctamente.");
		}catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}

		if(result == null || result.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	public List<DocDigitalizadosDto> getDocDigitalizados(String customerCode) throws RpcExceptionMessages{
		try{
			List<DocDigitalizadoLegacyDto> documentos = vantiveSystem.retriveDocDigitalizados(customerCode);
			return mapper.convertList(documentos, DocDigitalizadosDto.class);
		}catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
	}
	

	/**
	 * Este metodo realiza una query para verificar si el domicilio de envio y de facturacion asociado al nro de SS, esta validado
	 * por un EECC.
	 * @author fernaluc
	 * @return true si esta validado por EECC, de lo contrario false.
	 */
//	MGR - #3010 - Se pide el id de la cuenta para tenerlo en cuenta en la consulta
	public Boolean isDomicilioValidadoPorEECC(Long idCuenta, String nro_ss) throws RpcExceptionMessages{
		List result = null;
		try {
			Object[] valores = {nro_ss,idCuenta,nro_ss,idCuenta};
			AppLogger.info("Verificando que el domicilio de facturacion y de entrega asociados a la cuenta: " + idCuenta + 
					" y al nro de solicitud: " + nro_ss + ",haya sido validado por un EECC");
			result = repository.executeCustomQuery(QUERY_VALID_EECC, valores);
		}catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		
		//Si hay mas de un resultado, hay un error
		if(result.size() > 1)
			return null;
		
		if(result == null || result.isEmpty() || !result.get(0).equals("T")){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Autocompleta los valores que requiere el veraz (limCredito, Calificacion, RiskCode).
	 * @author fernaluc
	 * @return ScoreVerazDto
	 */
	public ScoreVerazDto autocompletarValoresVeraz(String score, int cantEquipos) throws RpcExceptionMessages{
		List<ScoreVeraz> result = null;
		try {
			Object[] valores = {score,cantEquipos};		
			AppLogger.info("Obteniendo los valores para autocompletar la consulta al veraz con el score: " + score + " y la cantidad de equipos: " + cantEquipos);
			result = repository.executeCustomQuery(QUERY_AUTOCOMP_VERAZ, valores);
		}catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		
		if(result != null && !result.isEmpty()){
			List<ScoreVerazDto> listVeraz = mapper.convertList(result, ScoreVerazDto.class);
			ScoreVerazDto scoreVeraz = new ScoreVerazDto();
			scoreVeraz.setLimCredito(listVeraz.get(0).getLimCredito());
			scoreVeraz.setCalificacion(listVeraz.get(0).getCalificacion());
			scoreVeraz.setRiskCode(listVeraz.get(0).getRiskCode());	
			return scoreVeraz;
		} else {
			return null;
		}
	}
	
	public String obtenerPahtArchivoVeraz(String verazFileName){
		AppLogger.info("Obteniendo path del archivo de Veraz.");
		return veraz.obtenerPahtArchivoVeraz(verazFileName);
	}

	public CantEquiposDTO retreiveEquiposPorEstado(String numeroCuenta) throws RpcExceptionMessages{
		try{
			CantidadEquiposDTO equipDto = this.avalonSystem.retreiveEquiposPorEstado(numeroCuenta);
			
			CantEquiposDTO cantEquipoDto = new CantEquiposDTO();
			cantEquipoDto.setCantEquipActivos(Integer.parseInt(equipDto.getCantidadActivos()));
			cantEquipoDto.setCantEquipDesactivados(Integer.parseInt(equipDto.getCantidadDesactivados()));
			cantEquipoDto.setCantEquipSuspendidos(Integer.parseInt(equipDto.getCantidadSuspendidos()));
			
			return cantEquipoDto;
			
		}catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
	}
	
	public Set<ContratoViewDto> checkPermanencia(Set<ContratoViewDto> listaContratos){
		for (ContratoViewDto contrato : listaContratos) {
			Object[] datosSubsidio = permanencia.getCargoAbonar(contrato.getContrato());
			if (datosSubsidio != null){
				Double cargosPermanencia = ((Double) datosSubsidio[0]).doubleValue();
				int mesesPermanencia = ((Integer) datosSubsidio[1]).intValue();
				contrato.setCargosPermanencia(cargosPermanencia);
				contrato.setMesesPermanencia(mesesPermanencia);
			}
		}
		return listaContratos;
	}
}

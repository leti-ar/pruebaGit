package ar.com.nextel.sfa.server.businessservice;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.nextel.business.constants.GlobalParameterIdentifier;
import ar.com.nextel.business.constants.KnownInstanceIdentifier;
import ar.com.nextel.business.cuentas.create.CreateCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.create.businessUnits.SolicitudCuenta;
import ar.com.nextel.business.cuentas.facturaelectronica.FacturaElectronicaService;
import ar.com.nextel.business.cuentas.select.SelectCuentaBusinessOperator;
import ar.com.nextel.business.oportunidades.CuentaPotencialBusinessOperator;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperator;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperatorResult;
import ar.com.nextel.components.accessMode.AccessAuthorization;
import ar.com.nextel.components.accessMode.accessObject.BaseAccessObject;
import ar.com.nextel.components.accessMode.accessRequest.AccessRequest;
import ar.com.nextel.components.accessMode.controller.AccessAuthorizationController;
import ar.com.nextel.components.knownInstances.GlobalParameter;
import ar.com.nextel.components.knownInstances.retrievers.DefaultRetriever;
import ar.com.nextel.components.knownInstances.retrievers.model.KnownInstanceRetriever;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.framework.repository.hibernate.HibernateRepository;
import ar.com.nextel.model.cuentas.beans.AbstractDatosPago;
import ar.com.nextel.model.cuentas.beans.ClaseCuenta;
import ar.com.nextel.model.cuentas.beans.ContactoCuenta;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.DatosDebitoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.Division;
import ar.com.nextel.model.cuentas.beans.FacturaElectronica;
import ar.com.nextel.model.cuentas.beans.FormaPago;
import ar.com.nextel.model.cuentas.beans.GranCuenta;
import ar.com.nextel.model.cuentas.beans.Suscriptor;
import ar.com.nextel.model.cuentas.beans.TipoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.EstadoOportunidad;
import ar.com.nextel.model.oportunidades.beans.MotivoNoCierre;
import ar.com.nextel.model.oportunidades.beans.OportunidadNegocio;
import ar.com.nextel.model.personas.beans.Email;
import ar.com.nextel.model.personas.beans.Persona;
import ar.com.nextel.model.personas.beans.Telefono;
import ar.com.nextel.services.components.sessionContext.SessionContext;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.dto.ContactoCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.FacturaElectronicaDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.nextel.util.PermisosUserCenter;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

@Service
public class CuentaBusinessService {

	private final String ERR_CUENTA_PREFIX = "La cuenta no puede abrirse. <BR/>";
	private final String ERR_CUENTA_NO_ACCESS = "Acceso denegado. No puede operar con esta cuenta.";
	private final String ERR_CUENTA_NO_EDITABLE = ERR_CUENTA_PREFIX
			+ "La Cuenta es de clase {1}";
	private final String ERR_CUENTA_GOBIERNO = ERR_CUENTA_PREFIX
			+ "La Cuenta: {1} ({2}) es de clase {3} y pertenece a la cartera de otro vendedor";
	private final String ERR_CUENTA_NO_PERMISO = "No tiene permiso para ver esa cuenta.";

	@Qualifier("createCuentaBusinessOperator")
	private CreateCuentaBusinessOperator createCuentaBusinessOperator;

	@Qualifier("reservaCreacionCuentaBusinessOperator")
	private ReservaCreacionCuentaBusinessOperator reservaCreacionCuentaBusinessOperator;

	@Qualifier("cuentaPotencialBusinessOperator")
	private CuentaPotencialBusinessOperator cuentaPotencialBusinessOperator;

	@Qualifier("selectCuentaBusinessOperator")
	private SelectCuentaBusinessOperator selectCuentaBusinessOperator;

	@Qualifier("accessAuthorizationController")
	private AccessAuthorizationController accessAuthorizationController;

	@Qualifier("knownInstancesRetriever")
	private KnownInstanceRetriever knownInstanceRetriever;

	private FacturaElectronicaService facturaElectronicaService;

	private Repository repository;

	private DefaultRetriever globalParameterRetriever;

	@Autowired
	public void setReservaCreacionCuentaBusinessOperator(
			@Qualifier("reservaCreacionCuentaBusinessOperatorBean") ReservaCreacionCuentaBusinessOperator reservaCreacionCuentaBusinessOperatorBean) {
		this.reservaCreacionCuentaBusinessOperator = reservaCreacionCuentaBusinessOperatorBean;
	}

	@Autowired
	public void setCuentaPotencialBusinessOperator(
			CuentaPotencialBusinessOperator cuentaPotencialBusinessOperator) {
		this.cuentaPotencialBusinessOperator = cuentaPotencialBusinessOperator;
	}

	@Autowired
	public void setSelectCuentaBusinessOperator(
			SelectCuentaBusinessOperator selectCuentaBusinessOperatorBean) {
		this.selectCuentaBusinessOperator = selectCuentaBusinessOperatorBean;
	}

	@Autowired
	public void setCreateCuentaBusinessOperator(
			CreateCuentaBusinessOperator createCuentaBusinessOperatorBean) {
		this.createCuentaBusinessOperator = createCuentaBusinessOperatorBean;
	}

	@Autowired
	public void setAccessAuthorizationController(
			AccessAuthorizationController accessAuthorizationControllerBean) {
		this.accessAuthorizationController = accessAuthorizationControllerBean;
	}

	@Autowired
	public void setKnownInstanceRetriever(
			KnownInstanceRetriever knownInstanceRetrieverBean) {
		this.knownInstanceRetriever = knownInstanceRetrieverBean;
	}

	@Autowired
	public void setRepository(@Qualifier("repository") Repository repository) {
		this.repository = repository;
	}

	@Autowired
	public void setFacturaElectronicaService(
			FacturaElectronicaService facturaElectronicaService) {
		this.facturaElectronicaService = facturaElectronicaService;
	}
	
	@Autowired
	public void setGlobalParameterRetriever(
			@Qualifier("globalParameterRetriever") DefaultRetriever globalParameterRetriever) {
		this.globalParameterRetriever = globalParameterRetriever;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta reservarCrearCta(SolicitudCuenta solicitudCta,
			MapperExtended mapper) throws BusinessException {
		ReservaCreacionCuentaBusinessOperatorResult reservarCrearCta = reservaCreacionCuentaBusinessOperator
				.reservarCrearCuenta(solicitudCta);
		Cuenta cuenta = reservarCrearCta.getCuenta();

		// FIXME: parche para evitar problemas de transaccion con cuentas
		// migradas de vantive.
		if (cuenta != null) {
			CuentaDto cuentaDto = null;
			if (cuenta.esGranCuenta()) {
				cuentaDto = (GranCuentaDto) mapper.map(cuenta,
						GranCuentaDto.class);
			} else if (cuenta.esDivision()) {
				cuentaDto = (DivisionDto) mapper.map(cuenta, DivisionDto.class);
			} else if (cuenta.esSuscriptor()) {
				cuentaDto = (SuscriptorDto) mapper.map(cuenta,
						SuscriptorDto.class);
			}
		}

		return cuenta;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveCuenta(Cuenta cuenta, Vendedor vendedor)
			throws BusinessException {
		selectCuentaBusinessOperator.getCuentaYLockear(cuenta
				.getCodigoVantive(), vendedor);
		repository.save(cuenta);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateCuenta(Cuenta cuenta) throws BusinessException {
		repository.update(cuenta);
	}

	//MGR - 05-07-2010 - Se cambian digitos de la tarjeta por asteriscos (*)
	private String changeByAsterisks(String numero) {

		StringBuffer nuevaTarj = new StringBuffer();
		nuevaTarj.append(numero.substring(0, 6));
		int cantReemp = numero.substring(6, numero.length() - 4).length();
		for (int i = 0; i < cantReemp; i++) {
			nuevaTarj.append("*");
		}
		nuevaTarj.append(numero.substring(numero.length() - 4));
		return nuevaTarj.toString();
	}

	private Long encriptarNumeroTrajeta(String numero) throws Exception {
		
		AppLogger.info("numero tarjeta: " + numero);
		CallableStatement stmt = ((HibernateRepository) repository)
				.getHibernateDaoSupport().getSessionFactory()
				.getCurrentSession().connection().prepareCall(
						"{?= call PCIED_SFA.ENCRIPTAR(?)}");
		stmt.registerOutParameter(1, java.sql.Types.INTEGER);
		stmt.setString(2, numero);
		stmt.execute();
		Long encript = stmt.getLong(1);
		stmt.close();
		return encript;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Long saveCuenta(CuentaDto cuentaDto, MapperExtended mapper,
			Vendedor vendedor) throws Exception {

		GlobalParameter pciGlobalParameter = (GlobalParameter) globalParameterRetriever
				.getObject(GlobalParameterIdentifier.PCI_ENABLED);

		Cuenta cuenta = repository.retrieve(Cuenta.class, cuentaDto.getId());

		Long numeroEncriptadoOriginal = null;
		if (cuenta.getDatosPago().isDebitoTarjetaCredito()) {
			DatosDebitoTarjetaCredito datosPagoTarjetaCredito = (DatosDebitoTarjetaCredito) repository
					.retrieve(DatosDebitoTarjetaCredito.class, cuenta
							.getDatosPago().getId());

			numeroEncriptadoOriginal = datosPagoTarjetaCredito
					.getNumeroEncriptado();
		}
		// DATOS PAGO

		AbstractDatosPago datosPagoOriginal = (AbstractDatosPago) repository
				.retrieve(AbstractDatosPago.class, cuenta.getDatosPago()
						.getId());
		cuenta.setDatosPago(null);

		repository.delete(datosPagoOriginal);
		cuenta.setFormaPago(null);

		mapper.map(cuentaDto, cuenta);

		if (cuenta.getCategoriaCuenta().getDescripcion().equals(
				KnownInstanceIdentifier.DIVISION.getKey())) {
			((Division) cuenta)
					.setNombre(((DivisionDto) cuentaDto).getNombre());
		}

		// FORMA PAGO
		FormaPago formaPagoNueva = (FormaPago) repository.retrieve(
				FormaPago.class, cuentaDto.getFormaPago().getId());
		cuenta.setFormaPago(formaPagoNueva);

		if (cuentaDto.getDatosPago() instanceof DatosDebitoCuentaBancariaDto) {
			TipoCuentaBancaria tipoCuenta = (TipoCuentaBancaria) repository
					.retrieve(TipoCuentaBancaria.class,
							((DatosDebitoCuentaBancariaDto) cuentaDto
									.getDatosPago()).getTipoCuentaBancaria()
									.getId());
			((DatosDebitoCuentaBancaria) cuenta.getDatosPago())
					.setTipoCuentaBancaria(tipoCuenta);
		} else if (cuentaDto.getDatosPago() instanceof DatosDebitoTarjetaCreditoDto) {
			TipoTarjeta tipoTarjeta = (TipoTarjeta) repository.retrieve(
					TipoTarjeta.class,
					((DatosDebitoTarjetaCreditoDto) cuentaDto.getDatosPago())
							.getTipoTarjeta().getId());
			((DatosDebitoTarjetaCredito) cuenta.getDatosPago())
					.setTipoTarjeta(tipoTarjeta);

			boolean pciEnabled = pciGlobalParameter.getValue()
					.equalsIgnoreCase("T");
			if (pciEnabled) {
				String numeroTarj = ((DatosDebitoTarjetaCredito) cuenta
						.getDatosPago()).getNumero();

				if (!numeroTarj.contains("*")) {

					String newNumber = changeByAsterisks(numeroTarj);

					Long numEncriptado = encriptarNumeroTrajeta(numeroTarj);

					// encrypt
					((DatosDebitoTarjetaCredito) cuenta.getDatosPago())
							.setNumero(newNumber);
					((DatosDebitoTarjetaCredito) cuenta.getDatosPago())
							.setNumeroEncriptado(numEncriptado);
				} else {
					// copio el encriptado original
					((DatosDebitoTarjetaCredito) cuenta.getDatosPago())
							.setNumeroEncriptado(numeroEncriptadoOriginal);

				}
				// ((DatosDebitoTarjetaCredito)cuenta.getDatosPago()).setNumero("9876543210987654321");
			}

		}
		guardarFacturaElectronica(cuenta, cuentaDto, mapper, vendedor);

		// FIXME: lo que sigue es para asegurar que los contactos se actualicen
		// cuando se están guardando
		// suscriptores o divisiones...
		Set<ContactoCuenta> listaContactos = new HashSet<ContactoCuenta>();
		for (ContactoCuentaDto contDto : getListaContactosDto(cuentaDto)) {
			ContactoCuenta contacto = (ContactoCuenta) mapper.map(contDto,
					ContactoCuenta.class);
			if (cuenta.esGranCuenta()) {
				contacto.setCuenta((GranCuenta) cuenta);
			} else if (cuenta.esSuscriptor()) {
				if (((Suscriptor) cuenta).getDivision() != null) {
					contacto.setCuenta((GranCuenta) ((Suscriptor) cuenta)
							.getDivision().getGranCuenta());
				} else {
					contacto.setCuenta((GranCuenta) ((Suscriptor) cuenta)
							.getGranCuenta());
				}
			} else if (cuenta.esDivision()) {
				contacto.setCuenta((GranCuenta) ((Division) cuenta)
						.getGranCuenta());
			}
			listaContactos.add((ContactoCuenta) mapper.map(contDto,
					ContactoCuenta.class));
		}
		cuenta.getPlainContactos().addAll(listaContactos);
		// *************************************************************************************************

		// FIXME: revisar mapeo de Persona/Telefono/Mail en dozer para no tener
		// que hacer esto
		// ***********************
		for (TelefonoDto tel : cuentaDto.getPersona().getTelefonos()) {
			updateTelefonosAPersona(tel, cuenta.getPersona(), mapper);
		}

		for (EmailDto email : cuentaDto.getPersona().getEmails()) {
			updateEmailsAPersona(email, cuenta.getPersona(), mapper);
		}

		if (cuenta.esGranCuenta()) {
			updateTelefonoEmailContactos(cuenta.getContactos(), cuentaDto,
					mapper);
		} else if (cuenta.esDivision()) {
			updateTelefonoEmailContactos(((Division) cuenta).getGranCuenta()
					.getContactos(), cuentaDto, mapper);
		} else if (cuenta.esSuscriptor()) {
			if (((Suscriptor) cuenta).getDivision() != null) {
				updateTelefonoEmailContactos(((Suscriptor) cuenta)
						.getDivision().getGranCuenta().getContactos(),
						cuentaDto, mapper);
			} else {
				updateTelefonoEmailContactos(((Suscriptor) cuenta)
						.getGranCuenta().getContactos(), cuentaDto, mapper);
			}
		}
		// **********************************************************************************************

		repository.save(cuenta.getDatosPago());
		repository.save(cuenta);
		return cuenta.getId();
	}

	private void guardarFacturaElectronica(Cuenta cuenta, CuentaDto cuentaDto,
			MapperExtended mapper, Vendedor vendedor) {

		if (cuentaDto.getFacturaElectronica() == null
				&& cuenta.getFacturaElectronica() != null) {
			repository.delete(cuenta.getFacturaElectronica());
			cuenta.setFacturaElectronica(null);
		} else if (cuentaDto.getFacturaElectronica() != null
				&& !cuentaDto.getFacturaElectronica().isCargadaEnVantive()) {

			if (cuenta.getFacturaElectronica() == null) {
				cuenta.setFacturaElectronica(mapper.map(cuentaDto
						.getFacturaElectronica(), FacturaElectronica.class));
			} else {
				mapper.map(cuentaDto.getFacturaElectronica(), cuenta
						.getFacturaElectronica());
			}

		}
		// else {
		// if (cuentaDto.getFacturaElectronica() != null
		// && !cuentaDto.getFacturaElectronica().isCargadaEnVantive()) {
		// facturaElectronicaService.adherirFacturaElectronica(cuenta.getIdVantive(),
		// cuenta
		// .getCodigoVantive(), cuentaDto.getFacturaElectronica().getEmail(),
		// "", vendedor
		// .getUserName());
		// }
		// }
	}

	public void cargarFacturaElectronica(CuentaDto cuentaDto, Cuenta cuenta,
			MapperExtended mapper, boolean isEnCarga) {
		// if (!isEnCarga) {
		// if (cuenta.getFacturaElectronica() != null) {
		// repository.delete(cuenta.getFacturaElectronica());
		// cuenta.setFacturaElectronica(null);
		// }

		if (cuenta.getFacturaElectronica() != null) {
			FacturaElectronicaDto fdto = (FacturaElectronicaDto) mapper
					.map(cuenta.getFacturaElectronica(),
							FacturaElectronicaDto.class);
			cuentaDto.setFacturaElectronica(fdto);
		} else {
			if (!isEnCarga
					&& facturaElectronicaService
							.isAdheridoFacturaElectronica(cuentaDto
									.getCodigoVantive())) {
				FacturaElectronicaDto facturaElectronica = new FacturaElectronicaDto();
				List<String> mails = facturaElectronicaService
						.obtenerMailFacturaElectronica(cuentaDto
								.getCodigoVantive());
				facturaElectronica.setEmail(mails.isEmpty() ? "" : mails
						.iterator().next());
				facturaElectronica.setCargadaEnVantive(true);
				cuentaDto.setFacturaElectronica(facturaElectronica);
			}
		}
		// }
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Division crearDivision(Cuenta cuenta, Vendedor vendedor) {
		return (Division) createCuentaBusinessOperator.createDivisionFrom(
				cuenta, vendedor);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Suscriptor crearSuscriptor(Cuenta cuenta, Vendedor vendedor) {
		return (Suscriptor) createCuentaBusinessOperator.createSuscriptorFrom(
				cuenta, vendedor);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void marcarOppComoConsultada(OportunidadNegocio oportunidad) {
		cuentaPotencialBusinessOperator.markAsConsultada(oportunidad);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public OportunidadNegocio updateEstadoOportunidad(
			OportunidadNegocioDto oportunidadDto, MapperExtended mapper) {
		OportunidadNegocio oportunidad = (OportunidadNegocio) repository
				.retrieve(OportunidadNegocio.class, oportunidadDto.getId());
		EstadoOportunidad nuevoEstado = repository.retrieve(
				EstadoOportunidad.class, oportunidadDto.getEstadoJustificado()
						.getEstado().getId());
		MotivoNoCierre nuevoMotivo = repository.retrieve(MotivoNoCierre.class,
				oportunidadDto.getEstadoJustificado().getMotivo().getId());
		oportunidad.getEstadoJustificado().setEstado(nuevoEstado);
		oportunidad.setEstado(nuevoEstado);
		oportunidad.getEstadoJustificado().setMotivo(nuevoMotivo);
		oportunidad.getEstadoJustificado().setObservacionesMotivo(
				oportunidadDto.getEstadoJustificado().getObservacionesMotivo());
		repository.save(oportunidad);
		return oportunidad;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta getCuentaSinLockear(Long ctaId) throws BusinessException {
		return selectCuentaBusinessOperator.getCuentaSinLockear(ctaId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta getCuentaSinLockear(String codVantive)
			throws BusinessException {
		return selectCuentaBusinessOperator.getCuentaSinLockear(codVantive);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta selectCuenta(Long cuentaId, String cod_vantive,
			Vendedor vendedor, boolean filtradoPorDni, DozerBeanMapper mapper)
			throws RpcExceptionMessages {
		AppLogger.info("Iniciando SelectCuenta...");
		Cuenta cuenta = null;
		BaseAccessObject accessCuenta = null;
		try {
			accessCuenta = cod_vantive != null && !cod_vantive.equals("null") ? getAccessCuenta(
					cod_vantive, vendedor)
					: getAccessCuenta(cuentaId, vendedor);
			cuenta = (Cuenta) accessCuenta.getTargetObject();

			validarAccesoCuenta(cuenta, vendedor, filtradoPorDni);

			// Lockea la cuenta
			if (vendedor.getTipoVendedor().isUsaLockeo() && ( accessCuenta.getAccessAuthorization().hasSamePermissionsAs(
					AccessAuthorization.editOnly())
					|| accessCuenta.getAccessAuthorization()
							.hasSamePermissionsAs(
									AccessAuthorization.fullAccess()))) {
				cuenta.editar(vendedor);
				repository.save(cuenta);
			}

			CuentaDto cuentaDto = null;
			String categoriaCuenta = cuenta.getCategoriaCuenta()
					.getDescripcion();
			if (categoriaCuenta.equals(KnownInstanceIdentifier.GRAN_CUENTA
					.getKey())) {
				cuentaDto = (GranCuentaDto) mapper.map((GranCuenta) cuenta,
						GranCuentaDto.class);
			} else if (categoriaCuenta.equals(KnownInstanceIdentifier.DIVISION
					.getKey())) {
				cuentaDto = (DivisionDto) mapper.map((Division) cuenta,
						DivisionDto.class);
			} else if (categoriaCuenta
					.equals(KnownInstanceIdentifier.SUSCRIPTOR.getKey())) {
				cuentaDto = (SuscriptorDto) mapper.map((Suscriptor) cuenta,
						SuscriptorDto.class);
			}
		} catch (Exception e) {
			AppLogger.error(e);
			// throw ExceptionUtil.wrap(e);
			throw new RpcExceptionMessages(e.getMessage());
		}
		AppLogger.info("SelectCuenta finalizado.");
		return cuenta;
	}

	/**
	 * 
	 * @param cuenta
	 * @return
	 */
	private List<ContactoCuentaDto> getListaContactosDto(CuentaDto cuentaDto) {
		List<ContactoCuentaDto> listaContactosDto = new ArrayList<ContactoCuentaDto>();
		if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(
				KnownInstanceIdentifier.GRAN_CUENTA.getKey())) {
			listaContactosDto = (List<ContactoCuentaDto>) ((GranCuentaDto) cuentaDto)
					.getContactos();
		} else if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(
				KnownInstanceIdentifier.SUSCRIPTOR.getKey())) {
			if (((SuscriptorDto) cuentaDto).getDivision() != null) {
				listaContactosDto = (List<ContactoCuentaDto>) ((SuscriptorDto) cuentaDto)
						.getDivision().getGranCuenta().getContactos();
			} else {
				listaContactosDto = (List<ContactoCuentaDto>) ((SuscriptorDto) cuentaDto)
						.getGranCuenta().getContactos();
			}

		} else if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(
				KnownInstanceIdentifier.DIVISION.getKey())) {
			listaContactosDto = (List<ContactoCuentaDto>) ((DivisionDto) cuentaDto)
					.getGranCuenta().getContactos();
		}
		return listaContactosDto;
	}

	public void validarAccesoCuenta(Cuenta cuenta, Vendedor vendedor,
			boolean filtradoPorDni) throws RpcExceptionMessages {
		// logueado no es el de la cuenta
		if ( !vendedor.getId().equals(cuenta.getVendedor().getId())) {
			HashMap<String, Boolean> mapaPermisosClient = (HashMap<String, Boolean>) 
			SessionContextLoader.getInstance().getSessionContext().get(SessionContext.PERMISOS);

			if (cuenta.isClaseEmpleados()) {
				throw new RpcExceptionMessages(
						ERR_CUENTA_NO_EDITABLE
								.replaceAll(
										"\\{1\\}",
										((ClaseCuenta) knownInstanceRetriever
												.getObject(KnownInstanceIdentifier.CLASE_CUENTA_EMPLEADOS))
												.getDescripcion()));
			} else if (cuenta.isGobiernoBsAs() && !(Boolean)mapaPermisosClient.get(PermisosUserCenter.TIENE_ACCESO_CTA_GOBIERNO_BS_AS.getValue())) {
				String err = ERR_CUENTA_GOBIERNO.replaceAll("\\{1\\}", cuenta
						.getCodigoVantive());
				err = err.replaceAll("\\{2\\}", cuenta.getPersona()
						.getRazonSocial());
				err = err
						.replaceAll(
								"\\{3\\}",
								((ClaseCuenta) knownInstanceRetriever
										.getObject(KnownInstanceIdentifier.CLASE_CUENTA_GOB_BS_AS))
										.getDescripcion());
				throw new RpcExceptionMessages(err);
			} else if ( cuenta.isGobierno() && !(Boolean)mapaPermisosClient.get(PermisosUserCenter.TIENE_ACCESO_CTA_GOBIERNO.getValue())){
				//MGR - #1063				
		        	String err = ERR_CUENTA_GOBIERNO.replaceAll("\\{1\\}", cuenta
							.getCodigoVantive());
					err = err.replaceAll("\\{2\\}", cuenta.getPersona()
							.getRazonSocial());
					err = err
							.replaceAll(
									"\\{3\\}",
									((ClaseCuenta) knownInstanceRetriever
											.getObject(KnownInstanceIdentifier.CLASE_CUENTA_GOBIERNO))
											.getDescripcion());
					throw new RpcExceptionMessages(err);
		  } else if (cuenta.isLAP() && !(Boolean)mapaPermisosClient.get(PermisosUserCenter.TIENE_ACCESO_CTA_LAP.getValue())) {
				throw new RpcExceptionMessages(
						ERR_CUENTA_NO_EDITABLE
								.replaceAll(
										"\\{1\\}",
										((ClaseCuenta) knownInstanceRetriever
												.getObject(KnownInstanceIdentifier.CLASE_CUENTA_LAP))
												.getDescripcion()));
			} else if (cuenta.isLA() && !(Boolean)mapaPermisosClient.get(PermisosUserCenter.TIENE_ACCESO_CTA_LA.getValue())) {
				throw new RpcExceptionMessages(
						ERR_CUENTA_NO_EDITABLE
								.replaceAll(
										"\\{1\\}",
										((ClaseCuenta) knownInstanceRetriever
												.getObject(KnownInstanceIdentifier.CLASE_CUENTA_LA))
												.getDescripcion()));
			} else if (!filtradoPorDni) {
				// no soy el de la cuenta y ademas no filtre
				// la tiene lockeada alguien y no soy yo
				if ((cuenta.getVendedorLockeo() != null)
						&& (!vendedor.getId().equals(
								cuenta.getVendedorLockeo().getId())) && vendedor.getTipoVendedor().isUsaLockeo()) {
					throw new RpcExceptionMessages(ERR_CUENTA_NO_PERMISO);
				}
			}
		}
	}

	private void updateTelefonoEmailContactos(
			Set<ContactoCuenta> listaContactos, CuentaDto cuentaDto,
			MapperExtended mapper) {
		List<ContactoCuentaDto> listaContactosDto = getListaContactosDto(cuentaDto);
		for (ContactoCuenta cont : listaContactos) {
			Persona persona = cont.getPersona();
			for (ContactoCuentaDto contDto : listaContactosDto) {
				if (cont.getId() == contDto.getId()
						&& contDto.getPersona() != null) {
					for (TelefonoDto tel : contDto.getPersona().getTelefonos()) {
						updateTelefonosAPersona(tel, persona, mapper);
					}
					for (EmailDto email : contDto.getPersona().getEmails()) {
						updateEmailsAPersona(email, persona, mapper);
					}
				}
			}
		}
	}


	private void updateTelefonosAPersona(TelefonoDto tel, Persona persona,
			MapperExtended mapper) {
		Telefono t = null;
		if (tel.getId() != null) {
			t = repository.retrieve(Telefono.class, tel.getId());
			mapper.map(tel, t);
		} else {
			t = mapper.map(tel, Telefono.class);
		}
		if (tel.getTipoTelefono().getId() == knownInstanceRetriever
				.getObjectId(KnownInstanceIdentifier.TIPO_TEL_PRINCIPAL)
				.longValue()) {
			persona.setTelefonoPrincipal(t);
		} else if (tel.getTipoTelefono().getId() == knownInstanceRetriever
				.getObjectId(KnownInstanceIdentifier.TIPO_TEL_ADICIONAL)
				.longValue()) {
			persona.setTelefonoAdicional(t);
		} else if (tel.getTipoTelefono().getId() == knownInstanceRetriever
				.getObjectId(KnownInstanceIdentifier.TIPO_TEL_CELULAR)
				.longValue()) {
			persona.setTelefonoCelular(t);
		} else if (tel.getTipoTelefono().getId() == knownInstanceRetriever
				.getObjectId(KnownInstanceIdentifier.TIPO_TEL_FAX).longValue()) {
			persona.setTelefonoFax(t);
		}
	}

	private void updateEmailsAPersona(EmailDto email, Persona persona,
			MapperExtended mapper) {
		Email e = null;
		if (email.getId() != null) {
			e = repository.retrieve(Email.class, email.getId());
			mapper.map(email, e);
		} else {
			e = mapper.map(email, Email.class);
		}
		if (email.getTipoEmail().getId().longValue() == knownInstanceRetriever
				.getObjectId(KnownInstanceIdentifier.TIPO_EMAIL_PERSONAL)
				.longValue()) {
			if (persona.getEmailPersonal() != null) {
				persona.getEmailPersonal().setEmail(e.getEmail());
			} else {
				persona.setEmailPersonalAddress(e.getEmail());
			}
		} else if (email.getTipoEmail().getId().longValue() == knownInstanceRetriever
				.getObjectId(KnownInstanceIdentifier.TIPO_EMAIL_LABORAL)
				.longValue()) {
			if (persona.getEmailLaboral() != null) {
				persona.getEmailLaboral().setEmail(e.getEmail());
			} else {
				persona.setEmailLaboralAddress(e.getEmail());
			}
		}
	}

	public Cuenta obtenerCtaPadreSinLockear(Long ctaPadreId,
			String categoriaCuenta, Vendedor vendedor)
			throws RpcExceptionMessages {
		BaseAccessObject accessCuentaPadre;
		Cuenta cuenta = null;
		try {
			accessCuentaPadre = getAccessCuenta(ctaPadreId, vendedor);
			// AccessAuthorization accessAuthorizationPadre =
			// accessCuentaPadre.getAccessAuthorization();
			cuenta = (Cuenta) accessCuentaPadre.getTargetObject();

			if (!accessCuentaPadre.getAccessAuthorization()
					.hasSamePermissionsAs(AccessAuthorization.editOnly())
					&& !accessCuentaPadre.getAccessAuthorization()
							.hasSamePermissionsAs(
									AccessAuthorization.fullAccess())) {

				accessCuentaPadre.getAccessAuthorization().setReasonPrefix(
						ERR_CUENTA_NO_ACCESS);
				throw new RpcExceptionMessages(ERR_CUENTA_NO_ACCESS);
			}
		} catch (Exception e) {
			throw new RpcExceptionMessages(e.getMessage());
		}
		return cuenta;
	}

	public BaseAccessObject getAccessCuenta(Long ctaId, Vendedor vendedor)
			throws Exception {
		Cuenta cuenta = null;
		BaseAccessObject accessCuenta = null;
		cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(ctaId);
		accessCuenta = obtenerAcceso(vendedor, cuenta);
		return accessCuenta;
	}

	public BaseAccessObject getAccessCuenta(String codVantive, Vendedor vendedor)
			throws Exception {
		Cuenta cuenta = null;
		BaseAccessObject accessCuenta = null;
		cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(codVantive);
		accessCuenta = obtenerAcceso(vendedor, cuenta);
		return accessCuenta;
	}

	public BaseAccessObject obtenerAcceso(Vendedor vendedor, Cuenta cuenta) {
		AppLogger.info("Calculando acceso de vendedor: "
				+ vendedor.getUserName() + " a cuenta "
				+ cuenta.getCodigoVantive(), this);
		AccessRequest accessRequest = new AccessRequest(vendedor, cuenta);
		AccessAuthorization accessAuthorization = accessAuthorizationController
				.accessAuthorizationFor(accessRequest);
		AppLogger.info(
				"accessAuthorization: " + accessAuthorization.toString(), this);
		BaseAccessObject accessCuenta = new BaseAccessObject(
				accessAuthorization, cuenta);
		return accessCuenta;
	}



}

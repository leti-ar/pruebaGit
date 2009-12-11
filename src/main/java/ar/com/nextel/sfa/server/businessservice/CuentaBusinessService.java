package ar.com.nextel.sfa.server.businessservice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.nextel.business.constants.KnownInstanceIdentifier;
import ar.com.nextel.business.cuentas.create.CreateCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.create.businessUnits.SolicitudCuenta;
import ar.com.nextel.business.cuentas.select.SelectCuentaBusinessOperator;
import ar.com.nextel.business.oportunidades.CuentaPotencialBusinessOperator;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperator;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperatorResult;
import ar.com.nextel.components.accessMode.AccessAuthorization;
import ar.com.nextel.components.accessMode.accessObject.BaseAccessObject;
import ar.com.nextel.components.accessMode.accessRequest.AccessRequest;
import ar.com.nextel.components.accessMode.controller.AccessAuthorizationController;
import ar.com.nextel.components.knownInstances.retrievers.model.KnownInstanceRetriever;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.AbstractDatosPago;
import ar.com.nextel.model.cuentas.beans.ClaseCuenta;
import ar.com.nextel.model.cuentas.beans.ContactoCuenta;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.DatosDebitoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.DatosPago;
import ar.com.nextel.model.cuentas.beans.Division;
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
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.dto.ContactoCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

@Service
public class CuentaBusinessService {
	
	private final String ERR_CUENTA_PREFIX      = "La cuenta no puede abrirse. <BR/>";
	private final String ERR_CUENTA_NO_ACCESS   = "Acceso denegado. No puede operar con esta cuenta.";
	private final String ERR_CUENTA_NO_EDITABLE = ERR_CUENTA_PREFIX + "La Cuenta es de clase {1}";
	private final String ERR_CUENTA_GOBIERNO    = ERR_CUENTA_PREFIX + "La Cuenta: {1} ({2}) es de clase {3} y pertenece a la cartera de otro vendedor";
	private final String ERR_CUENTA_NO_PERMISO  = "No tiene permiso para ver esa cuenta.";
	
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
	
	private Repository repository;

	@Autowired 
	public void setReservaCreacionCuentaBusinessOperator(@Qualifier("reservaCreacionCuentaBusinessOperatorBean")
			ReservaCreacionCuentaBusinessOperator reservaCreacionCuentaBusinessOperatorBean) {
		this.reservaCreacionCuentaBusinessOperator = reservaCreacionCuentaBusinessOperatorBean;
	}
	@Autowired
	public void setCuentaPotencialBusinessOperator( 
			CuentaPotencialBusinessOperator cuentaPotencialBusinessOperator) {
		this.cuentaPotencialBusinessOperator = cuentaPotencialBusinessOperator;
	}
	@Autowired
	public void setSelectCuentaBusinessOperator ( 
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
	public void setKnownInstanceRetriever(KnownInstanceRetriever knownInstanceRetrieverBean) {
		this.knownInstanceRetriever = knownInstanceRetrieverBean;
	}
	@Autowired
	public void setRepository(@Qualifier("repository")Repository repository) {
		this.repository = repository;
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta reservarCrearCta(SolicitudCuenta solicitudCta,MapperExtended mapper) throws BusinessException {
		ReservaCreacionCuentaBusinessOperatorResult reservarCrearCta = reservaCreacionCuentaBusinessOperator.reservarCrearCuenta(solicitudCta);
		Cuenta cuenta = reservarCrearCta.getCuenta();

		//FIXME: parche para evitar problemas de transaccion con cuentas migradas de vantive. 
		if (cuenta!=null) {
			CuentaDto cuentaDto = null;
			if (cuenta.esGranCuenta()) {
				cuentaDto = (GranCuentaDto) mapper.map(cuenta, GranCuentaDto.class);
			} else if (cuenta.esDivision()) {
				cuentaDto = (DivisionDto) mapper.map(cuenta, DivisionDto.class);
			} else if (cuenta.esSuscriptor()) {
				cuentaDto = (SuscriptorDto) mapper.map(cuenta, SuscriptorDto.class);
			}
		}
		return cuenta;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveCuenta(Cuenta cuenta, Vendedor vendedor) throws BusinessException {
		selectCuentaBusinessOperator.getCuentaYLockear(cuenta.getCodigoVantive(), vendedor);
		repository.save(cuenta);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateCuenta(Cuenta cuenta) throws BusinessException {
		repository.update(cuenta);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Long saveCuenta(CuentaDto cuentaDto,MapperExtended mapper) throws Exception {
		Cuenta cuenta = repository.retrieve(Cuenta.class, cuentaDto.getId());
		
        //DATOS PAGO
		DatosPago datosPagoOriginal = (DatosPago) repository.retrieve(AbstractDatosPago.class, cuenta.getDatosPago().getId());
		cuenta.setDatosPago(null);
		repository.delete(datosPagoOriginal);
		cuenta.setFormaPago(null);

		mapper.map(cuentaDto, cuenta);
		
		if (cuenta.getCategoriaCuenta().getDescripcion().equals(KnownInstanceIdentifier.DIVISION.getKey())) {
			((Division)cuenta).setNombre(((DivisionDto)cuentaDto).getNombre());
		}		
		
		//FORMA PAGO
		FormaPago formaPagoNueva = (FormaPago) repository.retrieve(FormaPago.class, cuentaDto.getFormaPago().getId());
		cuenta.setFormaPago(formaPagoNueva);

		if(cuentaDto.getDatosPago() instanceof DatosDebitoCuentaBancariaDto) { 
			TipoCuentaBancaria tipoCuenta = (TipoCuentaBancaria) repository.retrieve(TipoCuentaBancaria.class, ((DatosDebitoCuentaBancariaDto)cuentaDto.getDatosPago()).getTipoCuentaBancaria().getId());
			((DatosDebitoCuentaBancaria)cuenta.getDatosPago()).setTipoCuentaBancaria(tipoCuenta);
		} else if(cuentaDto.getDatosPago() instanceof DatosDebitoTarjetaCreditoDto) { 
			TipoTarjeta tipoTarjeta = (TipoTarjeta) repository.retrieve(TipoTarjeta.class, ((DatosDebitoTarjetaCreditoDto)cuentaDto.getDatosPago()).getTipoTarjeta().getId());
			((DatosDebitoTarjetaCredito)cuenta.getDatosPago()).setTipoTarjeta(tipoTarjeta);
		}

		//FIXME: lo que sigue es para asegurar que los contactos se actualicen cuando se están guardando suscriptores o divisiones...
		Set<ContactoCuenta>  listaContactos = new HashSet<ContactoCuenta>();
		for (ContactoCuentaDto contDto: getListaContactosDto(cuentaDto)) {
			ContactoCuenta contacto = (ContactoCuenta) mapper.map(contDto, ContactoCuenta.class);
			if(cuenta.esGranCuenta()) {
				contacto.setCuenta((GranCuenta)cuenta);	
			} else if(cuenta.esSuscriptor()) {
				if(((Suscriptor)cuenta).getDivision()!=null) {
					contacto.setCuenta((GranCuenta)((Suscriptor)cuenta).getDivision().getGranCuenta());
				} else {
					contacto.setCuenta((GranCuenta)((Suscriptor)cuenta).getGranCuenta());
				}
			} else if(cuenta.esDivision()) {
				contacto.setCuenta((GranCuenta)((Division)cuenta).getGranCuenta());
			}
			listaContactos.add( (ContactoCuenta) mapper.map(contDto, ContactoCuenta.class));
		}
		cuenta.getPlainContactos().addAll(listaContactos);
		//*************************************************************************************************

		//FIXME: revisar mapeo de Persona/Telefono/Mail en dozer para no tener que hacer esto ***********************
		removerTelefonosDePersona(cuenta.getPersona());
		for (TelefonoDto tel : cuentaDto.getPersona().getTelefonos()) {
			addTelefonosAPersona(tel,cuenta.getPersona(),mapper);                                      
		}                     
		removerEmailsDePersona(cuenta.getPersona());
		for (EmailDto email : cuentaDto.getPersona().getEmails()) {
			addEmailsAPersona(email,cuenta.getPersona());
		}
		if (cuenta.esGranCuenta()) {
			updateTelefonoEmailContactos(cuenta.getContactos(), cuentaDto, mapper);
		} else if (cuenta.esDivision()) {
			updateTelefonoEmailContactos(((Division)cuenta).getGranCuenta().getContactos(), cuentaDto, mapper);
		} else if (cuenta.esSuscriptor()) {
			if (((Suscriptor)cuenta).getDivision()!=null) {
				updateTelefonoEmailContactos(((Suscriptor)cuenta).getDivision().getGranCuenta().getContactos(), cuentaDto, mapper);
			} else {
				updateTelefonoEmailContactos(((Suscriptor)cuenta).getGranCuenta().getContactos(), cuentaDto, mapper);
			}
		}
		//**********************************************************************************************
		
		repository.save(cuenta.getDatosPago());
		repository.save(cuenta);
		return cuenta.getId();
	}
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Division crearDivision(Cuenta cuenta, Vendedor vendedor) {
		return (Division) createCuentaBusinessOperator.createDivisionFrom(cuenta, vendedor);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Suscriptor crearSuscriptor(Cuenta cuenta, Vendedor vendedor) {
		return (Suscriptor) createCuentaBusinessOperator.createSuscriptorFrom(cuenta, vendedor);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void marcarOppComoConsultada(OportunidadNegocio oportunidad) {
		cuentaPotencialBusinessOperator.markAsConsultada(oportunidad);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public OportunidadNegocio updateEstadoOportunidad(OportunidadNegocioDto oportunidadDto, MapperExtended mapper) {
        OportunidadNegocio oportunidad = (OportunidadNegocio) repository.retrieve(OportunidadNegocio.class, oportunidadDto.getId());
        EstadoOportunidad nuevoEstado = repository.retrieve(EstadoOportunidad.class, oportunidadDto.getEstadoJustificado().getEstado().getId());
        MotivoNoCierre    nuevoMotivo = repository.retrieve(MotivoNoCierre.class, oportunidadDto.getEstadoJustificado().getMotivo().getId());
        oportunidad.getEstadoJustificado().setEstado(nuevoEstado);
        oportunidad.setEstado(nuevoEstado);
        oportunidad.getEstadoJustificado().setMotivo(nuevoMotivo);
        oportunidad.getEstadoJustificado().setObservacionesMotivo(oportunidadDto.getEstadoJustificado().getObservacionesMotivo());
        repository.save(oportunidad);
	    return oportunidad;     
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta getCuentaSinLockear(Long ctaId) throws Exception {
		return selectCuentaBusinessOperator.getCuentaSinLockear(ctaId);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta getCuentaSinLockear(String codVantive) throws Exception {
		return selectCuentaBusinessOperator.getCuentaSinLockear(codVantive);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta selectCuenta(Long cuentaId, String cod_vantive, Vendedor vendedor, boolean filtradoPorDni, DozerBeanMapper mapper) throws RpcExceptionMessages {
        AppLogger.info("Iniciando SelectCuenta...");
        Cuenta cuenta = null;
        BaseAccessObject accessCuenta = null;
        try {
			accessCuenta  = cod_vantive!=null && !cod_vantive.equals("null") ? getAccessCuenta(cod_vantive,vendedor) : getAccessCuenta(cuentaId,vendedor);
			cuenta = (Cuenta) accessCuenta.getTargetObject();
			
			validarAccesoCuenta(cuenta, vendedor, filtradoPorDni);
			
			// Lockea la cuenta
			if (accessCuenta.getAccessAuthorization().hasSamePermissionsAs(AccessAuthorization.editOnly()) ||
			                accessCuenta.getAccessAuthorization().hasSamePermissionsAs(AccessAuthorization.fullAccess())) {
			    cuenta.editar(vendedor);
		        repository.save(cuenta);
			}
			
			//FIXME: parche para evitar problemas de transaccion con cuentas migradas de vantive.
		    CuentaDto cuentaDto = null;
		    String categoriaCuenta = cuenta.getCategoriaCuenta().getDescripcion();
			if (categoriaCuenta.equals(KnownInstanceIdentifier.GRAN_CUENTA.getKey())) {
				cuentaDto = (GranCuentaDto) mapper.map((GranCuenta) cuenta, GranCuentaDto.class);
			} else if (categoriaCuenta.equals(KnownInstanceIdentifier.DIVISION.getKey())) {
				cuentaDto = (DivisionDto)   mapper.map((Division) cuenta, DivisionDto.class);
			} else if (categoriaCuenta.equals(KnownInstanceIdentifier.SUSCRIPTOR.getKey())) {
				cuentaDto = (SuscriptorDto) mapper.map((Suscriptor) cuenta, SuscriptorDto.class);
			}
		} catch (Exception e) {
			AppLogger.error(e);
			//throw ExceptionUtil.wrap(e);
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
		List <ContactoCuentaDto>listaContactosDto = new ArrayList<ContactoCuentaDto>();
		if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(KnownInstanceIdentifier.GRAN_CUENTA.getKey())) {
			listaContactosDto = (List <ContactoCuentaDto>)((GranCuentaDto)cuentaDto).getContactos();
		} else if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(KnownInstanceIdentifier.SUSCRIPTOR.getKey())) {
			if(((SuscriptorDto)cuentaDto).getDivision()!=null) {
				listaContactosDto = (List <ContactoCuentaDto>) ((SuscriptorDto)cuentaDto).getDivision().getGranCuenta().getContactos();	
			} else {
				listaContactosDto = (List <ContactoCuentaDto>) ((SuscriptorDto)cuentaDto).getGranCuenta().getContactos();
			}
			
		} else if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(KnownInstanceIdentifier.DIVISION.getKey())) {
			listaContactosDto = (List <ContactoCuentaDto>) ((DivisionDto)cuentaDto).getGranCuenta().getContactos();
		}
        return listaContactosDto;
	}

	public void validarAccesoCuenta(Cuenta cuenta, Vendedor vendedor, boolean filtradoPorDni) throws RpcExceptionMessages {
			    
 		      //logueado no es el de la cuenta
		if (!vendedor.getId().equals(cuenta.getVendedor().getId())) {
			if (cuenta.isClaseEmpleados()) {
				throw new RpcExceptionMessages( ERR_CUENTA_NO_EDITABLE.replaceAll("\\{1\\}", ((ClaseCuenta)knownInstanceRetriever.getObject(KnownInstanceIdentifier.CLASE_CUENTA_EMPLEADOS)).getDescripcion()));
			} else if (cuenta.isGobiernoBsAs()) {
				String err = ERR_CUENTA_GOBIERNO.replaceAll("\\{1\\}", cuenta.getCodigoVantive()); 
				err = err.replaceAll("\\{2\\}", cuenta.getPersona().getRazonSocial());
				err = err.replaceAll("\\{3\\}", ((ClaseCuenta)knownInstanceRetriever.getObject(KnownInstanceIdentifier.CLASE_CUENTA_GOB_BS_AS)).getDescripcion());
				throw new RpcExceptionMessages(err);
			} else if (cuenta.isGobierno()) {
				String err = ERR_CUENTA_GOBIERNO.replaceAll("\\{1\\}", cuenta.getCodigoVantive()); 
				err = err.replaceAll("\\{2\\}", cuenta.getPersona().getRazonSocial());
				err = err.replaceAll("\\{3\\}", ((ClaseCuenta)knownInstanceRetriever.getObject(KnownInstanceIdentifier.CLASE_CUENTA_GOBIERNO)).getDescripcion());
				throw new RpcExceptionMessages(err);
			} else if (cuenta.isLAP()) {
				throw new RpcExceptionMessages(ERR_CUENTA_NO_EDITABLE.replaceAll("\\{1\\}", ((ClaseCuenta)knownInstanceRetriever.getObject(KnownInstanceIdentifier.CLASE_CUENTA_LAP)).getDescripcion()));
			} else if (cuenta.isLA()) {
				throw new RpcExceptionMessages(ERR_CUENTA_NO_EDITABLE.replaceAll("\\{1\\}", ((ClaseCuenta)knownInstanceRetriever.getObject(KnownInstanceIdentifier.CLASE_CUENTA_LA)).getDescripcion()));				
			} else if (!filtradoPorDni){			       		   
				// no soy el de la cuenta y ademas no filtre	
				// la tiene lockeada alguien y no soy yo				
				if ((cuenta.getVendedorLockeo()!=null) && (!vendedor.getId().equals(cuenta.getVendedorLockeo().getId()))) {				
					throw new RpcExceptionMessages(ERR_CUENTA_NO_PERMISO);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param listaContactos
	 * @param cuentaDto
	 * @param mapper
	 */
	private void updateTelefonoEmailContactos(Set<ContactoCuenta> listaContactos,CuentaDto cuentaDto,MapperExtended mapper) {
		List<ContactoCuentaDto> listaContactosDto = getListaContactosDto(cuentaDto);
		for(ContactoCuenta cont : listaContactos) {
			Persona persona = cont.getPersona();
			for (ContactoCuentaDto contDto : listaContactosDto) {
				if (cont.getId()==contDto.getId())  {
					removerTelefonosDePersona(persona);
					for (TelefonoDto tel : contDto.getPersona().getTelefonos()) {
						addTelefonosAPersona(tel,persona,mapper);
					}
					for (EmailDto email : contDto.getPersona().getEmails()) {
						addEmailsAPersona(email,persona);
					}
				}
			}
		}
	}
	
	private void removerTelefonosDePersona(Persona persona) {
		if(persona.getTelefonos()!=null) {
			List<Telefono> telefonos = new ArrayList<Telefono>(persona.getTelefonos());
			for (Telefono tel : telefonos) {
				persona.removeTelefono(tel);
				repository.delete(tel);
			}
		}
	}
	
	private void removerEmailsDePersona(Persona persona) {
		if(persona.getEmails()!=null) {
			List<Email> emails = new ArrayList<Email>(persona.getEmails());
			for (Email email : emails) {
				persona.removeEmail(email);
				repository.delete(email);
			}
		}
	}
	
	/**
	 *   
	 * @param tel
	 * @param cuenta
	 */
	private void addTelefonosAPersona(TelefonoDto tel, Persona persona, MapperExtended mapper) {
		if (tel.getTipoTelefono().getId()==knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_TEL_PRINCIPAL).longValue()) {
			persona.setTelefonoPrincipal(mapper.map(tel, Telefono.class));	
		}
		else if (tel.getTipoTelefono().getId()==knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_TEL_ADICIONAL).longValue()) {
			persona.setTelefonoAdicional(mapper.map(tel, Telefono.class));	
		}
		else if (tel.getTipoTelefono().getId()==knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_TEL_CELULAR).longValue()) {
			persona.setTelefonoCelular(mapper.map(tel, Telefono.class));	
		}
		else if (tel.getTipoTelefono().getId()==knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_TEL_FAX).longValue()) {
			persona.setTelefonoFax(mapper.map(tel, Telefono.class));	
		}
	}
	
	/**
	 * 
	 * @param mail
	 * @param cuenta
	 */
	private void addEmailsAPersona(EmailDto mail, Persona persona) {
		if (mail.getTipoEmail().getId().longValue()==knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_EMAIL_PERSONAL).longValue()) {
			if (persona.getEmailPersonal()!=null) {
				persona.getEmailPersonal().setEmail(mail.getEmail());
			} else {
				persona.setEmailPersonalAddress(mail.getEmail());
			}
		}
		else if (mail.getTipoEmail().getId().longValue()==knownInstanceRetriever.getObjectId(KnownInstanceIdentifier.TIPO_EMAIL_LABORAL).longValue()) {
			if (persona.getEmailLaboral()!=null) {
				persona.getEmailLaboral().setEmail(mail.getEmail());
			} else {
				persona.setEmailLaboralAddress(mail.getEmail());	
			}
		}
	}
	
	public Cuenta obtenerCtaPadre(Long ctaPadreId, String categoriaCuenta, Vendedor vendedor) throws RpcExceptionMessages {
		BaseAccessObject accessCuentaPadre;
		Cuenta cuenta = null;
		try {
			accessCuentaPadre = getAccessCuenta(ctaPadreId,vendedor);
			AccessAuthorization accessAuthorizationPadre = accessCuentaPadre.getAccessAuthorization();
			cuenta = (Cuenta) accessCuentaPadre.getTargetObject();

			// Lockea la cuenta
			if (accessCuentaPadre.getAccessAuthorization().hasSamePermissionsAs(
					AccessAuthorization.editOnly())
					|| accessCuentaPadre.getAccessAuthorization().hasSamePermissionsAs(
							AccessAuthorization.fullAccess())) {
				cuenta.editar(vendedor);
			} else {
				accessCuentaPadre.getAccessAuthorization().setReasonPrefix(ERR_CUENTA_NO_ACCESS);
				throw new RpcExceptionMessages(ERR_CUENTA_NO_ACCESS);
			}
		} catch (Exception e) {
			throw new RpcExceptionMessages(e.getMessage());
		}
		// TODO: ver si es necesario traducir esta parte
		// InstanceIdentifier categoriaCuentaServer =
		// knownInstanceMapper.toKnownInstanceIdentifier(categoriaCuenta);
		// Transformer crearCuentaHijoTransformer = this.crearCuentaTransformer(categoriaCuentaServer);
		// Transformer returnCuentaNoAccessMethod = this.returnCuentaNoAccessMethod();
		//
		// CuentaWCTO cuentaWCTO = (CuentaWCTO) accessAuthorizationPadre.executeIfPermission(
		// AccessPermission.EDIT, crearCuentaHijoTransformer, returnCuentaNoAccessMethod, accessCuentaPadre);
		// AppLogger.info("Creacion de Division/Suscriptor finalizada.");
		return cuenta;
	}
	
	public BaseAccessObject getAccessCuenta(Long ctaId, Vendedor vendedor) throws Exception {
		Cuenta cuenta = null;
		BaseAccessObject accessCuenta = null;
		cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(ctaId);
		accessCuenta = obtenerAcceso(vendedor, cuenta);
		return accessCuenta;
	}
	
	public BaseAccessObject getAccessCuenta(String codVantive, Vendedor vendedor) throws Exception {
		Cuenta cuenta = null;
		BaseAccessObject accessCuenta = null;
		cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(codVantive);
		accessCuenta = obtenerAcceso(vendedor, cuenta);
		return accessCuenta;
	}
	
	public BaseAccessObject obtenerAcceso(Vendedor vendedor, Cuenta cuenta) {
		AppLogger.info("Calculando acceso de vendedor: " + vendedor.getUserName() + " a cuenta "+ cuenta.getCodigoVantive(), this);
		AccessRequest accessRequest = new AccessRequest(vendedor, cuenta);
		AccessAuthorization accessAuthorization = accessAuthorizationController.accessAuthorizationFor(accessRequest);
		AppLogger.info("accessAuthorization: " + accessAuthorization.toString(), this);
		BaseAccessObject accessCuenta = new BaseAccessObject(accessAuthorization, cuenta);
		return accessCuenta;
	}

}

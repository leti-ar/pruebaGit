package ar.com.nextel.sfa.server.businessservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.nextel.business.cuentas.create.CreateCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.create.businessUnits.SolicitudCuenta;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperator;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperatorResult;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.AbstractDatosPago;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.DatosDebitoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.DatosPago;
import ar.com.nextel.model.cuentas.beans.FormaPago;
import ar.com.nextel.model.cuentas.beans.TipoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.model.personas.beans.Telefono;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.server.util.MapperExtended;

@Service
public class CuentaBusinessService {
	
	@Qualifier("createCuentaBusinessOperator")
	private CreateCuentaBusinessOperator createCuentaBusinessOperator;
//	private EdicionCuentaBusinessOperator edicionCuentaBusinessOperator;
	
	@Qualifier("reservaCreacionCuentaBusinessOperator")
	private ReservaCreacionCuentaBusinessOperator reservaCreacionCuentaBusinessOperator;
	        
	private SessionContextLoader sessionContextLoader;
	private Repository repository;

	@Autowired 
	public void setReservaCreacionCuentaBusinessOperator(@Qualifier("reservaCreacionCuentaBusinessOperatorBean")
			ReservaCreacionCuentaBusinessOperator reservaCreacionCuentaBusinessOperatorBean) {
		this.reservaCreacionCuentaBusinessOperator = reservaCreacionCuentaBusinessOperatorBean;
	}
	@Autowired
	public void setCreateCuentaBusinessOperator( 
			CreateCuentaBusinessOperator createCuentaBusinessOperatorBean) {
		this.createCuentaBusinessOperator = createCuentaBusinessOperatorBean;
	}
	@Autowired	
	public void setSessionContextLoader(SessionContextLoader sessionContextLoader) {
		this.sessionContextLoader = sessionContextLoader;
	}
	
	@Autowired
	public void setRepository(@Qualifier("repository")Repository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta reservarCrearCta(SolicitudCuenta solicitudCta) throws BusinessException {
		ReservaCreacionCuentaBusinessOperatorResult reservarCrearCta = reservaCreacionCuentaBusinessOperator.reservarCrearCuenta(solicitudCta);
		return reservarCrearCta.getCuenta();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta getCuenta(long id)  {
		return repository.retrieve(Cuenta.class, id); 
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta saveCuenta(CuentaDto cuentaDto,MapperExtended mapper)  {
		Cuenta cuenta = repository.retrieve(Cuenta.class, cuentaDto.getId());
		DatosPago datosPagoOriginal = (DatosPago) repository.retrieve(AbstractDatosPago.class, cuenta.getDatosPago().getId());
		cuenta.setDatosPago(null);
		repository.delete(datosPagoOriginal);
		cuenta.setFormaPago(null);

		mapper.map(cuentaDto, cuenta);

		FormaPago formaPagoNueva = (FormaPago) repository.retrieve(FormaPago.class, cuentaDto.getFormaPago().getId());
		cuenta.setFormaPago(formaPagoNueva);

		if(cuentaDto.getDatosPago() instanceof DatosDebitoCuentaBancariaDto) { 
//			repository.save(((DatosDebitoCuentaBancaria)cuenta.getDatosPago()).getTipoCuentaBancaria());
			TipoCuentaBancaria tipoCuenta = (TipoCuentaBancaria) repository.retrieve(TipoCuentaBancaria.class, ((DatosDebitoCuentaBancariaDto)cuentaDto.getDatosPago()).getTipoCuentaBancaria().getId());
			((DatosDebitoCuentaBancaria)cuenta.getDatosPago()).setTipoCuentaBancaria(tipoCuenta);
		} else if(cuentaDto.getDatosPago() instanceof DatosDebitoTarjetaCreditoDto) { 
			TipoTarjeta tipoTarjeta = (TipoTarjeta) repository.retrieve(TipoTarjeta.class, ((DatosDebitoTarjetaCreditoDto)cuentaDto.getDatosPago()).getTipoTarjeta().getId());
			((DatosDebitoTarjetaCredito)cuenta.getDatosPago()).setTipoTarjeta(tipoTarjeta);
		}

		//FIXME: revisar mapeo de Cuenta/Persona en dozer para no tener que hacer esto
		for (TelefonoDto tel : cuentaDto.getPersona().getTelefonos()) {
			agregarTelefonosAPersona(tel,cuenta,mapper);
		}
		for (EmailDto email : cuentaDto.getPersona().getEmails()) {
			agregarEmailsAPersona(email,cuenta);
		}
		//--------------------------------------------------------

		//cuenta.setVendedor(getVendedor("acsa1"));
		//cuenta.setLastModificationDate(Calendar.getInstance().getTime());
		repository.save(cuenta.getDatosPago());
		repository.save(cuenta);
		return cuenta;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveObject(Object obj)  {
		repository.save(obj);
	}
	
	/**
	 *   
	 * @param tel
	 * @param cuenta
	 */
	private void agregarTelefonosAPersona(TelefonoDto tel, Cuenta cuenta,MapperExtended mapper) {
		if (tel.getTipoTelefono().getId()==TipoTelefonoEnum.PRINCIPAL.getTipo()) {
			if (cuenta.getPersona().getTelefonoPrincipal()!=null) {
				cuenta.getPersona().getTelefonoPrincipal().setArea(tel.getArea());
				cuenta.getPersona().getTelefonoPrincipal().setNumeroLocal(tel.getNumeroLocal());
				cuenta.getPersona().getTelefonoPrincipal().setInterno(tel.getInterno());
			} else {
				cuenta.getPersona().setTelefonoPrincipal(mapper.map(tel, Telefono.class));	
			}
		}
		else if (tel.getTipoTelefono().getId()==TipoTelefonoEnum.ADICIONAL.getTipo()) {
			if (cuenta.getPersona().getTelefonoAdicional()!=null) {
				cuenta.getPersona().getTelefonoAdicional().setArea(tel.getArea());
				cuenta.getPersona().getTelefonoAdicional().setNumeroLocal(tel.getNumeroLocal());
				cuenta.getPersona().getTelefonoAdicional().setInterno(tel.getInterno());
			} else {
				cuenta.getPersona().setTelefonoAdicional(mapper.map(tel, Telefono.class));	
			}
		}
		else if (tel.getTipoTelefono().getId()==TipoTelefonoEnum.CELULAR.getTipo()) {
			if (cuenta.getPersona().getTelefonoCelular()!=null) {
				cuenta.getPersona().getTelefonoCelular().setArea(tel.getArea());
				cuenta.getPersona().getTelefonoCelular().setNumeroLocal(tel.getNumeroLocal());
			} else {
				cuenta.getPersona().setTelefonoCelular(mapper.map(tel, Telefono.class));	
			}
		}
		else if (tel.getTipoTelefono().getId()==TipoTelefonoEnum.FAX.getTipo()) {
			if (cuenta.getPersona().getTelefonoFax()!=null) {
				cuenta.getPersona().getTelefonoFax().setArea(tel.getArea());
				cuenta.getPersona().getTelefonoFax().setNumeroLocal(tel.getNumeroLocal());
				cuenta.getPersona().getTelefonoFax().setInterno(tel.getInterno());
			} else {
				cuenta.getPersona().setTelefonoFax(mapper.map(tel, Telefono.class));	
			}
		}
	}
	/**
	 * 
	 * @param mail
	 * @param cuenta
	 */
	private void agregarEmailsAPersona(EmailDto mail, Cuenta cuenta) {
		if (mail.getTipoEmail().getId()==TipoEmailEnum.PERSONAL.getTipo()) {
			if (cuenta.getPersona().getEmailPersonal()!=null) {
				cuenta.getPersona().getEmailPersonal().setEmail(mail.getEmail());
			} else {
				cuenta.getPersona().setEmailPersonalAddress(mail.getEmail());	
			}
		}
		else if (mail.getTipoEmail().getId()==TipoEmailEnum.LABORAL.getTipo()) {
			if (cuenta.getPersona().getEmailLaboral()!=null) {
				cuenta.getPersona().getEmailLaboral().setEmail(mail.getEmail());
			} else {
				cuenta.getPersona().setEmailLaboralAddress(mail.getEmail());	
			}
		}
		
	}

}

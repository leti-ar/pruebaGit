package ar.com.nextel.sfa.server.businessservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.nextel.business.cuentas.create.CreateCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.create.businessUnits.SolicitudCuenta;
import ar.com.nextel.business.oportunidades.CuentaPotencialBusinessOperator;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperator;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperatorResult;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.AbstractDatosPago;
import ar.com.nextel.model.cuentas.beans.ContactoCuenta;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.DatosDebitoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.DatosPago;
import ar.com.nextel.model.cuentas.beans.Division;
import ar.com.nextel.model.cuentas.beans.FormaPago;
import ar.com.nextel.model.cuentas.beans.Suscriptor;
import ar.com.nextel.model.cuentas.beans.TipoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.EstadoOportunidad;
import ar.com.nextel.model.oportunidades.beans.MotivoNoCierre;
import ar.com.nextel.model.oportunidades.beans.OportunidadNegocio;
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
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.enums.TipoCuentaEnum;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.server.util.MapperExtended;

@Service
public class CuentaBusinessService {
	
	@Qualifier("createCuentaBusinessOperator")
	private CreateCuentaBusinessOperator createCuentaBusinessOperator;
	
	@Qualifier("reservaCreacionCuentaBusinessOperator")
	private ReservaCreacionCuentaBusinessOperator reservaCreacionCuentaBusinessOperator;
	        
	@Qualifier("cuentaPotencialBusinessOperator")
	private CuentaPotencialBusinessOperator cuentaPotencialBusinessOperator;
	
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
	public void setCreateCuentaBusinessOperator( 
			CreateCuentaBusinessOperator createCuentaBusinessOperatorBean) {
		this.createCuentaBusinessOperator = createCuentaBusinessOperatorBean;
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
	public void saveCuenta(Cuenta cuenta) throws BusinessException {
		repository.save(cuenta);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Long saveCuenta(CuentaDto cuentaDto,MapperExtended mapper)  {
		Cuenta cuenta = repository.retrieve(Cuenta.class, cuentaDto.getId());
		
        //DATOS PAGO
		DatosPago datosPagoOriginal = (DatosPago) repository.retrieve(AbstractDatosPago.class, cuenta.getDatosPago().getId());
		cuenta.setDatosPago(null);
		repository.delete(datosPagoOriginal);
		cuenta.setFormaPago(null);

		mapper.map(cuentaDto, cuenta);

		if (cuenta.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.DIV.getTipo())) {
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

		//FIXME: revisar mapeo de Persona/Telefono/Mail en dozer para no tener que hacer esto ***********************
		for (TelefonoDto tel : cuentaDto.getPersona().getTelefonos()) {                                
			addTelefonosAPersona(tel,cuenta.getPersona(),mapper);                                      
		}                                                                                              
		for (EmailDto email : cuentaDto.getPersona().getEmails()) {
			addEmailsAPersona(email,cuenta.getPersona());
		}
		if (cuenta.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.CTA.getTipo())) {
			for(ContactoCuenta cont : cuenta.getContactos()) {
				Persona persona = cont.getPersona();
				for (ContactoCuentaDto contDto : (((GranCuentaDto) cuentaDto).getContactos())) {
					if (cont.getId()==contDto.getId())  {
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
	
	/**
	 *   
	 * @param tel
	 * @param cuenta
	 */
	private void addTelefonosAPersona(TelefonoDto tel, Persona persona, MapperExtended mapper) {
		if (tel.getTipoTelefono().getId()==TipoTelefonoEnum.PRINCIPAL.getTipo()) {
			if (persona.getTelefonoPrincipal()!=null) {
				persona.getTelefonoPrincipal().setArea(tel.getArea());
				persona.getTelefonoPrincipal().setNumeroLocal(tel.getNumeroLocal());
				persona.getTelefonoPrincipal().setInterno(tel.getInterno());
			} else {
				persona.setTelefonoPrincipal(mapper.map(tel, Telefono.class));	
			}
		}
		else if (tel.getTipoTelefono().getId()==TipoTelefonoEnum.ADICIONAL.getTipo()) {
			if (persona.getTelefonoAdicional()!=null) {
				persona.getTelefonoAdicional().setArea(tel.getArea());
				persona.getTelefonoAdicional().setNumeroLocal(tel.getNumeroLocal());
				persona.getTelefonoAdicional().setInterno(tel.getInterno());
			} else {
				persona.setTelefonoAdicional(mapper.map(tel, Telefono.class));	
			}
		}
		else if (tel.getTipoTelefono().getId()==TipoTelefonoEnum.CELULAR.getTipo()) {
			if (persona.getTelefonoCelular()!=null) {
				persona.getTelefonoCelular().setArea(tel.getArea());
				persona.getTelefonoCelular().setNumeroLocal(tel.getNumeroLocal());
			} else {
				persona.setTelefonoCelular(mapper.map(tel, Telefono.class));	
			}
		}
		else if (tel.getTipoTelefono().getId()==TipoTelefonoEnum.FAX.getTipo()) {
			if (persona.getTelefonoFax()!=null) {
				persona.getTelefonoFax().setArea(tel.getArea());
				persona.getTelefonoFax().setNumeroLocal(tel.getNumeroLocal());
				persona.getTelefonoFax().setInterno(tel.getInterno());
			} else {
				persona.setTelefonoFax(mapper.map(tel, Telefono.class));	
			}
		}
	}
	
	/**
	 * 
	 * @param mail
	 * @param cuenta
	 */
	private void addEmailsAPersona(EmailDto mail, Persona persona) {
		if (mail.getTipoEmail().getId().equals(TipoEmailEnum.PERSONAL.getTipo())) {
			if (persona.getEmailPersonal()!=null) {
				persona.getEmailPersonal().setEmail(mail.getEmail());
			} else {
				persona.setEmailPersonalAddress(mail.getEmail());
			}
		}
		else if (mail.getTipoEmail().getId().equals(TipoEmailEnum.LABORAL.getTipo())) {
			if (persona.getEmailLaboral()!=null) {
				persona.getEmailLaboral().setEmail(mail.getEmail());
			} else {
				persona.setEmailLaboralAddress(mail.getEmail());	
			}
		}
		
	}
}

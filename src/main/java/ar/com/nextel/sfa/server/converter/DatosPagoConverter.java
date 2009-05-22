package ar.com.nextel.sfa.server.converter;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.DatosDebitoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.DatosEfectivo;
import ar.com.nextel.model.cuentas.beans.DatosPago;
import ar.com.nextel.model.cuentas.beans.TipoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DatosEfectivoDto;
import ar.com.nextel.sfa.client.dto.DatosPagoDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;

@Component
public class DatosPagoConverter implements CustomConverter {

	public Repository repository;
	public DozerBeanMapper dozerMapper;

	@Autowired
	public void setDozerMapper(
			@Qualifier("dozerMapper") DozerBeanMapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	@Autowired
	public void setRepository(@Qualifier("repository") Repository repository) {
		this.repository = repository;
	}

	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class destClass, Class sourceClass) {
		Object result = null;
		if (sourceFieldValue instanceof HibernateProxy) {
            HibernateProxy hibernateProxy = (HibernateProxy) sourceFieldValue;
            result = (DatosPagoDto) convertDatosPagoToDto(hibernateProxy.getHibernateLazyInitializer().getImplementation());
        } else {
        	result = (DatosPago) convertDtoToDatosPago(sourceFieldValue);
        }
		return result;
	}    
        
    private Object convertDatosPagoToDto(Object source) {
    	Object result = null;
    	if (source instanceof DatosEfectivo) {
		    try {
				result = (DatosEfectivoDto)dozerMapper.map((DatosEfectivo)source, DatosEfectivoDto.class);
			} catch (MappingException e) {
			    result = new DatosEfectivoDto();
			    ((DatosEfectivoDto)result).setId(((DatosEfectivo)source).getId());
			}
    	} else if (source instanceof DatosDebitoCuentaBancaria) {
    		try {
    			result = (DatosDebitoCuentaBancariaDto)dozerMapper.map((DatosDebitoCuentaBancaria)source, DatosDebitoCuentaBancariaDto.class);
    		} catch (MappingException e) {
    			result = new DatosDebitoCuentaBancariaDto();
    			((DatosDebitoCuentaBancariaDto)result).setId(((DatosDebitoCuentaBancaria)source).getId());
    			((DatosDebitoCuentaBancariaDto)result).setCbu( ((DatosDebitoCuentaBancaria) source).getCbu());	    
    			TipoCuentaBancariaDto tcb = new TipoCuentaBancariaDto();
    			tcb.setCodigoVantive(((DatosDebitoCuentaBancaria) source).getTipoCuentaBancaria().getCodigoVantive());
    			tcb.setId(((DatosDebitoCuentaBancaria) source).getTipoCuentaBancaria().getId());
    			((DatosDebitoCuentaBancariaDto)result).setTipoCuentaBancaria(tcb );
    		}
    	} else if (source instanceof DatosDebitoTarjetaCredito) {
    		try {
    			result = (DatosDebitoTarjetaCreditoDto)dozerMapper.map((DatosDebitoTarjetaCredito)source, DatosDebitoTarjetaCreditoDto.class);
    		} catch (MappingException e) {
    			result = new DatosDebitoTarjetaCreditoDto();
    			((DatosDebitoTarjetaCreditoDto)result).setId(((DatosDebitoTarjetaCredito)source).getId());
    			((DatosDebitoTarjetaCreditoDto)result).setAnoVencimientoTarjeta(((DatosDebitoTarjetaCredito) source).getAnoVencimientoTarjeta());
    			((DatosDebitoTarjetaCreditoDto)result).setMesVencimientoTarjeta(((DatosDebitoTarjetaCredito) source).getMesVencimientoTarjeta());
    			((DatosDebitoTarjetaCreditoDto)result).setNumero(((DatosDebitoTarjetaCredito) source).getNumero());
    			TipoTarjetaDto tt =  new TipoTarjetaDto();
    			tt.setCodigoVantive(((DatosDebitoTarjetaCredito) source).getTipoTarjeta().getCodigoVantive());
    			tt.setId(((DatosDebitoTarjetaCredito) source).getTipoTarjeta().getId());
    			((DatosDebitoTarjetaCreditoDto)result).setTipoTarjeta(tt);
    		}
    	}
    	return result;

    }	
	
    private Object convertDtoToDatosPago(Object sourceFieldValue) {
    	Object result = null;

    	if (sourceFieldValue instanceof DatosEfectivoDto) {
    		try {
    			result = (DatosEfectivo)dozerMapper.map((DatosEfectivoDto)sourceFieldValue, DatosEfectivo.class);
    		} catch (MappingException e) {
    			result = repository.createNewObject(DatosEfectivo.class);
    			((DatosEfectivo)result).setId(((DatosEfectivoDto)sourceFieldValue).getId());
    		}

    	} else if (sourceFieldValue instanceof DatosDebitoCuentaBancariaDto) {
    		try {
    			result = (DatosDebitoCuentaBancaria)dozerMapper.map((DatosDebitoCuentaBancariaDto)sourceFieldValue, DatosDebitoCuentaBancaria.class);
    		} catch (MappingException e) {
    			result = repository.createNewObject(DatosDebitoCuentaBancaria.class);
    			((DatosDebitoCuentaBancaria)result).setId(((DatosDebitoCuentaBancariaDto)sourceFieldValue).getId());
    			((DatosDebitoCuentaBancaria)result).setCbu(((DatosDebitoCuentaBancariaDto)sourceFieldValue).getCbu());	    
    			TipoCuentaBancaria tcb = repository.retrieve(TipoCuentaBancaria.class, ((DatosDebitoCuentaBancariaDto) sourceFieldValue).getTipoCuentaBancaria().getId());
    			((DatosDebitoCuentaBancaria)result).setTipoCuentaBancaria(tcb );
    		}

    	} else if (sourceFieldValue instanceof DatosDebitoTarjetaCreditoDto) {
    		try {
    			result = (DatosDebitoTarjetaCredito)dozerMapper.map((DatosDebitoTarjetaCreditoDto)sourceFieldValue, DatosDebitoTarjetaCredito.class);
    		} catch (MappingException e) {
    			result = repository.createNewObject(DatosDebitoTarjetaCredito.class);
    			((DatosDebitoTarjetaCredito)result).setId(((DatosDebitoTarjetaCreditoDto)sourceFieldValue).getId());
    			((DatosDebitoTarjetaCredito)result).setAnoVencimientoTarjeta(((DatosDebitoTarjetaCreditoDto) sourceFieldValue).getAnoVencimientoTarjeta());
    			((DatosDebitoTarjetaCredito)result).setMesVencimientoTarjeta(((DatosDebitoTarjetaCreditoDto) sourceFieldValue).getMesVencimientoTarjeta());
    			((DatosDebitoTarjetaCredito)result).setNumero(((DatosDebitoTarjetaCreditoDto) sourceFieldValue).getNumero());
    			TipoTarjeta tt =  repository.retrieve(TipoTarjeta.class, ((DatosDebitoTarjetaCreditoDto) sourceFieldValue).getTipoTarjeta().getId());
    			((DatosDebitoTarjetaCredito)result).setTipoTarjeta(tt);
    		}
    	}
    	return result;
    }	

    private DatosPago convertDatosPago(DatosPago datosPago) {
        if (datosPago instanceof HibernateProxy) {
            HibernateProxy hibernateProxy = (HibernateProxy) datosPago;
            return (DatosPago) hibernateProxy.getHibernateLazyInitializer().getImplementation();
        }
        return datosPago;
    }
}

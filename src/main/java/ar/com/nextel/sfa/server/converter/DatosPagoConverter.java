package ar.com.nextel.sfa.server.converter;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.DatosDebitoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.DatosEfectivo;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DatosEfectivoDto;

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
		Object source = sourceFieldValue instanceof HibernateProxy ? 
				        ((HibernateProxy) sourceFieldValue).getHibernateLazyInitializer().getImplementation() :
				        sourceFieldValue;
        //class to dto 
		if (source instanceof DatosEfectivo) {
			result = (DatosEfectivoDto)dozerMapper.map((DatosEfectivo)source, DatosEfectivoDto.class);
		} else if (source instanceof DatosDebitoCuentaBancaria) {
			result = (DatosDebitoCuentaBancariaDto)dozerMapper.map((DatosDebitoCuentaBancaria)source, DatosDebitoCuentaBancariaDto.class);
		} else if (source instanceof DatosDebitoTarjetaCredito) {
			result = (DatosDebitoTarjetaCreditoDto)dozerMapper.map((DatosDebitoTarjetaCredito)source, DatosDebitoTarjetaCreditoDto.class);
        //dto to class
		} else if (sourceFieldValue instanceof DatosEfectivoDto) {
    		result = (DatosEfectivo)dozerMapper.map((DatosEfectivoDto)sourceFieldValue, DatosEfectivo.class);
    	} else if (sourceFieldValue instanceof DatosDebitoCuentaBancariaDto) {
    		result = (DatosDebitoCuentaBancaria)dozerMapper.map((DatosDebitoCuentaBancariaDto)sourceFieldValue, DatosDebitoCuentaBancaria.class);
    	} else if (sourceFieldValue instanceof DatosDebitoTarjetaCreditoDto) {
    		result = (DatosDebitoTarjetaCredito)dozerMapper.map((DatosDebitoTarjetaCreditoDto)sourceFieldValue, DatosDebitoTarjetaCredito.class);
    	}
		return result;
	}    
}

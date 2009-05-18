package ar.com.nextel.sfa.server.converter;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ar.com.nextel.business.constants.KnownInstanceIdentifier;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.DatosDebitoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.DatosEfectivo;
import ar.com.nextel.model.cuentas.beans.DatosPago;
import ar.com.nextel.model.cuentas.beans.FormaPago;
import ar.com.nextel.model.cuentas.beans.TipoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.TipoTarjeta;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DatosEfectivoDto;
import ar.com.nextel.sfa.client.dto.DatosPagoDto;
import ar.com.nextel.sfa.client.dto.FormaPagoDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.enums.TipoFormaPagoEnum;
import ar.com.nextel.util.ApplicationContextUtil;


@Component
public class DatosPagoConverter implements CustomConverter {

	public Repository repository;
	public DozerBeanMapper dozerMapper;
	
	@Autowired
	public void setDozerMapper(@Qualifier("dozerMapper") DozerBeanMapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	@Autowired
	public void setRepository(@Qualifier("repository") Repository repository) {
		this.repository = repository;
	}

    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class destClass, Class sourceClass) {
        Object result = null;
        
        //server -> cliente
        if (sourceFieldValue instanceof DatosEfectivo) {
		    try {
				result = (DatosEfectivoDto)dozerMapper.map((DatosEfectivo)sourceFieldValue, destClass);
				//((DatosEfectivoDto)result).setFormaPagoAsociada((FormaPagoDto)dozerMapper.map(((DatosEfectivo)sourceFieldValue).formaPagoAsociada()), FormaPagoDto.class);
			} catch (MappingException e) {
			    result = new DatosEfectivoDto();
			    ((DatosEfectivoDto)result).setFormaPagoAsociada(new FormaPagoDto(Long.parseLong(TipoFormaPagoEnum.EFECTIVO.getTipo()),"",TipoFormaPagoEnum.EFECTIVO.toString()));
			    return (DatosPagoDto) result;
			}
    	} else if (sourceFieldValue instanceof DatosDebitoCuentaBancaria) {
    	    result = new DatosDebitoCuentaBancariaDto();
            ((DatosDebitoCuentaBancariaDto)result).setCbu( ((DatosDebitoCuentaBancaria) sourceFieldValue).getCbu());	    
            TipoCuentaBancariaDto tcb = new TipoCuentaBancariaDto();
            tcb.setCodigoVantive(((DatosDebitoCuentaBancaria) sourceFieldValue).getTipoCuentaBancaria().getCodigoVantive());
            tcb.setId(((DatosDebitoCuentaBancaria) sourceFieldValue).getTipoCuentaBancaria().getId());
            ((DatosDebitoCuentaBancariaDto)result).setTipoCuentaBancaria(tcb );
            ((DatosDebitoCuentaBancariaDto)result).setFormaPagoAsociada(new FormaPagoDto(Long.parseLong(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo()),"",TipoFormaPagoEnum.CUENTA_BANCARIA.toString()));
            return (DatosPagoDto) result;
    	} else if (sourceFieldValue instanceof DatosDebitoTarjetaCredito) {
    		result = new DatosDebitoTarjetaCreditoDto();
    		((DatosDebitoTarjetaCreditoDto)result).setAnoVencimientoTarjeta(((DatosDebitoTarjetaCredito) sourceFieldValue).getAnoVencimientoTarjeta());
    		((DatosDebitoTarjetaCreditoDto)result).setMesVencimientoTarjeta(((DatosDebitoTarjetaCredito) sourceFieldValue).getMesVencimientoTarjeta());
    		((DatosDebitoTarjetaCreditoDto)result).setNumero(((DatosDebitoTarjetaCredito) sourceFieldValue).getNumero());
    		TipoTarjetaDto tt =  new TipoTarjetaDto();
    		tt.setCodigoVantive(((DatosDebitoTarjetaCredito) sourceFieldValue).getTipoTarjeta().getCodigoVantive());
    		tt.setId(((DatosDebitoTarjetaCredito) sourceFieldValue).getTipoTarjeta().getId());
    		((DatosDebitoTarjetaCreditoDto)result).setTipoTarjeta(tt);
    		((DatosDebitoTarjetaCreditoDto)result).setFormaPagoAsociada(new FormaPagoDto(Long.parseLong(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo()),"",TipoFormaPagoEnum.TARJETA_CREDITO.toString()));
    		return (DatosPagoDto) result;

   		//cliente -> server
    	} else if (sourceFieldValue instanceof DatosEfectivoDto) {
    		result = repository.createNewObject(DatosEfectivo.class);
    		FormaPago fp = repository.retrieve(FormaPago.class, Long.parseLong(TipoFormaPagoEnum.EFECTIVO.getTipo()));
    		((DatosEfectivo)result).setFormaPagoAsociada(fp);
    		return (DatosPago) result;
    	} else if (sourceFieldValue instanceof DatosDebitoCuentaBancariaDto) {
    		result = repository.createNewObject(DatosDebitoCuentaBancaria.class);
    		FormaPago fp = repository.retrieve(FormaPago.class, Long.parseLong(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo()));
    		((DatosDebitoCuentaBancaria)result).setCbu( ((DatosDebitoCuentaBancariaDto) sourceFieldValue).getCbu());	    
    		TipoCuentaBancaria tcb = repository.retrieve(TipoCuentaBancaria.class, ((DatosDebitoCuentaBancariaDto) sourceFieldValue).getTipoCuentaBancaria().getId());
    		((DatosDebitoCuentaBancaria)result).setTipoCuentaBancaria(tcb );
    		((DatosDebitoCuentaBancaria)result).setFormaPagoAsociada(fp);
    		
//    		result = repository.createNewObject(DatosDebitoCuentaBancaria.class);
//    		FormaPago fp = repository.retrieve(FormaPago.class, Long.parseLong(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo()));
//    		((DatosDebitoCuentaBancaria)existingDestinationFieldValue).setFormaPagoAsociada(fp);
//    		((DatosDebitoCuentaBancaria)existingDestinationFieldValue).setCbu(((DatosDebitoCuentaBancariaDto) sourceFieldValue).getCbu());
//    		TipoCuentaBancaria tcb = repository.retrieve(TipoCuentaBancaria.class, ((DatosDebitoCuentaBancariaDto) sourceFieldValue).getTipoCuentaBancaria().getId());
//    		((DatosDebitoCuentaBancaria)existingDestinationFieldValue).setTipoCuentaBancaria(tcb);
  		
  		
    		
    		
    		return (DatosPago) result;
    	} else if (sourceFieldValue instanceof DatosDebitoTarjetaCreditoDto) {
    		result = repository.createNewObject(DatosDebitoTarjetaCredito.class);
    		FormaPago fp = repository.retrieve(FormaPago.class, Long.parseLong(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo()));
    		((DatosDebitoTarjetaCredito)result).setAnoVencimientoTarjeta(((DatosDebitoTarjetaCreditoDto) sourceFieldValue).getAnoVencimientoTarjeta());
    		((DatosDebitoTarjetaCredito)result).setMesVencimientoTarjeta(((DatosDebitoTarjetaCreditoDto) sourceFieldValue).getMesVencimientoTarjeta());
    		((DatosDebitoTarjetaCredito)result).setNumero(((DatosDebitoTarjetaCreditoDto) sourceFieldValue).getNumero());
    		TipoTarjeta tt =  repository.retrieve(TipoTarjeta.class, ((DatosDebitoTarjetaCreditoDto) sourceFieldValue).getTipoTarjeta().getId());
    		((DatosDebitoTarjetaCredito)result).setTipoTarjeta(tt);
    		((DatosDebitoTarjetaCredito)result).setFormaPagoAsociada(fp);
    		return (DatosPago) result;
    	}

        return result;
    }
    

}

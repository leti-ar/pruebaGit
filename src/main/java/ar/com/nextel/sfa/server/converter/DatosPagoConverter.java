package ar.com.nextel.sfa.server.converter;

import org.dozer.CustomConverter;

import ar.com.nextel.model.cuentas.beans.DatosDebitoCuentaBancaria;
import ar.com.nextel.model.cuentas.beans.DatosDebitoTarjetaCredito;
import ar.com.nextel.model.cuentas.beans.DatosEfectivo;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DatosEfectivoDto;
import ar.com.nextel.sfa.client.dto.DatosPagoDto;
import ar.com.nextel.sfa.client.dto.FormaPagoDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;

public class DatosPagoConverter implements CustomConverter {
	
    /**
     * @see net.sf.dozer.util.mapping.converters.CustomConverter#convert(java.lang.Object, java.lang.Object,
     *      java.lang.Class, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class destClass, Class sourceClass) {
        Object result = null;
        FormaPagoDto fp = new FormaPagoDto();

        if (sourceFieldValue instanceof DatosEfectivo) {
		    result =  new DatosEfectivoDto();
		    fp.setId( ((DatosEfectivo)sourceFieldValue).getId());
		    ((DatosEfectivoDto)result).setFormaPagoAsociada(fp);
    	} else if (sourceFieldValue instanceof DatosDebitoCuentaBancaria) {
    	    result = new DatosDebitoCuentaBancariaDto();
            ((DatosDebitoCuentaBancariaDto)result).setCbu( ((DatosDebitoCuentaBancaria) sourceFieldValue).getCbu());	    
            TipoCuentaBancariaDto tcb = new TipoCuentaBancariaDto();
            tcb.setCodigoVantive(((DatosDebitoCuentaBancaria) sourceFieldValue).getTipoCuentaBancaria().getCodigoVantive());
            tcb.setId(((DatosDebitoCuentaBancaria) sourceFieldValue).getTipoCuentaBancaria().getId());
            ((DatosDebitoCuentaBancariaDto)result).setTipoCuentaBancaria(tcb );
            fp.setId( ((DatosDebitoCuentaBancaria)sourceFieldValue).getId());
            ((DatosDebitoCuentaBancariaDto)result).setFormaPagoAsociada(fp);
    	} else if (sourceFieldValue instanceof DatosDebitoTarjetaCredito) {
    		result =  new DatosDebitoTarjetaCreditoDto();
    		((DatosDebitoTarjetaCreditoDto)result).setAnoVencimientoTarjeta(((DatosDebitoTarjetaCredito) sourceFieldValue).getAnoVencimientoTarjeta());
    		((DatosDebitoTarjetaCreditoDto)result).setMesVencimientoTarjeta(((DatosDebitoTarjetaCredito) sourceFieldValue).getMesVencimientoTarjeta());
    		((DatosDebitoTarjetaCreditoDto)result).setNumero(((DatosDebitoTarjetaCredito) sourceFieldValue).getNumero());
    		TipoTarjetaDto tt =  new TipoTarjetaDto();
    		tt.setCodigoVantive(((DatosDebitoTarjetaCredito) sourceFieldValue).getTipoTarjeta().getCodigoVantive());
    		tt.setId(((DatosDebitoTarjetaCredito) sourceFieldValue).getTipoTarjeta().getId());
    		((DatosDebitoTarjetaCreditoDto)result).setTipoTarjeta(tt);
            fp.setId( ((DatosDebitoTarjetaCredito)sourceFieldValue).getId());
    		((DatosDebitoTarjetaCreditoDto)result).setFormaPagoAsociada(fp);
    	}
    	return (DatosPagoDto) result;
    }

}

package ar.com.nextel.sfa.server.converter;

import org.dozer.CustomConverter;

import ar.com.nextel.business.legacy.vantive.model.VantiveDatosDebitoTarjetaCredito;
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
		    result =  new DatosEfectivoDto();//(DatosEfectivoDto) repository.createNewObject(DatosEfectivoDto.class);
		    fp.setId( ((DatosEfectivo)sourceFieldValue).getId());
		    ((DatosEfectivoDto)result).setFormaPagoAsociada(fp);
		    //((DatosEfectivoDto)result).setFormaPagoAsociada((FormaPagoDto) knownInstanceRetriever.getObject(KnownInstanceIdentifier.FORMA_PAGO_EFECTIVO));
    	} else if (sourceFieldValue instanceof DatosDebitoCuentaBancaria) {
    	    result = new DatosDebitoCuentaBancariaDto();//(DatosDebitoCuentaBancariaDto) repository.createNewObject(DatosDebitoCuentaBancariaDto.class);
            ((DatosDebitoCuentaBancariaDto)result).setCbu( ((DatosDebitoCuentaBancaria) sourceFieldValue).getCbu());	    
            TipoCuentaBancariaDto tcb = new TipoCuentaBancariaDto();//repository.createNewObject(TipoCuentaBancaria.class);
            tcb.setCodigoVantive(((DatosDebitoCuentaBancariaDto) sourceFieldValue).getTipoCuentaBancaria().getCodigoVantive());
            ((DatosDebitoCuentaBancariaDto)result).setTipoCuentaBancaria(tcb );
            fp.setId( ((DatosDebitoCuentaBancaria)sourceFieldValue).getId());
            ((DatosDebitoCuentaBancariaDto)result).setFormaPagoAsociada(fp);
    	} else if (sourceFieldValue instanceof VantiveDatosDebitoTarjetaCredito) {
    		result =  new DatosDebitoTarjetaCreditoDto();//(DatosDebitoTarjetaCredito) repository.createNewObject(DatosDebitoTarjetaCredito.class);
    		((DatosDebitoTarjetaCreditoDto)result).setAnoVencimientoTarjeta(((DatosDebitoTarjetaCredito) sourceFieldValue).getAnoVencimientoTarjeta());
    		((DatosDebitoTarjetaCreditoDto)result).setMesVencimientoTarjeta(((DatosDebitoTarjetaCredito) sourceFieldValue).getMesVencimientoTarjeta());
    		((DatosDebitoTarjetaCreditoDto)result).setNumero(((VantiveDatosDebitoTarjetaCredito) sourceFieldValue).getNumero());
    		TipoTarjetaDto tt =  new TipoTarjetaDto();//repository.createNewObject(TipoTarjeta.class);
    		tt.setCodigoVantive(((DatosDebitoTarjetaCredito) sourceFieldValue).getTipoTarjeta().getCodigoVantive());
    		((DatosDebitoTarjetaCreditoDto)result).setTipoTarjeta(tt);
            fp.setId( ((DatosDebitoTarjetaCredito)sourceFieldValue).getId());
    		((DatosDebitoTarjetaCreditoDto)result).setFormaPagoAsociada(fp);
    	}
    	return (DatosPagoDto) result;
    }

}

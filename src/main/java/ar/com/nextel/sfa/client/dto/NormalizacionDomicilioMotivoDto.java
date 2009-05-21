package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class NormalizacionDomicilioMotivoDto  implements IsSerializable{

    private String motivo;

    /*
     * duda_standard|duda_altura_invalida|calle_invalida|
     * localidad_invalida|provincia_invalida|localidad_equivalente|
     * localidad_vecina|partido|delta_cp|distancia_minima|exact_match
     */

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
    	this.motivo = motivo;
    }
}

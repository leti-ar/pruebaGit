package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class TipoDomicilioDto implements IsSerializable{

	   	private String codigoVantive;
	    private String descripcion;
	    private Long id;

	    public void setId(Long id){
	    	this.id = id;
	    };

	    public Long getId(){
	    	return this.id;
	    };
	    
	    /**
	     * @return Returns the descripcion.
	     */
	    public String getDescripcion() {
	        return descripcion;
	    }

	    /**
	     * @param descripcion
	     *            The descripcion to set.
	     */
	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

	    public String getCodigoVantive() {
	        return codigoVantive;
	    }

	    public void setCodigoVantive(String codigoVantive) {
	        this.codigoVantive = codigoVantive;
	    }
}

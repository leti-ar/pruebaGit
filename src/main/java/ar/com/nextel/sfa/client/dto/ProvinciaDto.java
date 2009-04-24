package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProvinciaDto implements IsSerializable{

	    private String codigoVantive;
	    private String descripcion;

	    public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

	    /**
	     * @see java.lang.Object#equals(Object)
	     */
	    public boolean equals(Object object) {
	        if (!(object instanceof ProvinciaDto)) {
	            return false;
	        }
	        return super.equals(object);
	    }
	    /**
	     * @see ar.com.nextel.framework.AbstractIdentifiableObject#hashCode()
	     */
	    public int hashCode() {
	        return super.hashCode();
	    }


	    public String getCodigoVantive() {
	        return codigoVantive;
	    }

	    public void setCodigoVantive(String codigoVantive) {
	        this.codigoVantive = codigoVantive;
	    }
	}


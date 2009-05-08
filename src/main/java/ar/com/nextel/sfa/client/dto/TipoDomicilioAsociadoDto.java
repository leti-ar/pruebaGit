package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class TipoDomicilioAsociadoDto implements IsSerializable{

//    private long idTipoDomicilio;
//    private Boolean principal;
//	
//    public long getIdTipoDomicilio() {
//		
//    	return idTipoDomicilio;
//	}
//	public void setIdTipoDomicilio(long idTipoDomicilio) {
//		this.idTipoDomicilio = idTipoDomicilio;
//	}
//	public Boolean getPrincipal() {
//		return principal;
//	}
//	public void setPrincipal(Boolean principal) {
//		this.principal = principal;
//	}
	/***/
	
    private DomiciliosCuentaDto domicilio;
    private TipoDomicilioDto tipoDomicilio;
    private Boolean principal;
    private String codigoFNCL;
    private String estado;
    private Long idVantive;

    /**
     * Solo si est� activo es de interes para la PDA.
     */
    private Boolean activo;

    public TipoDomicilioAsociadoDto() {
    }

    /**
     * @return Retorna el/la activo.
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo
     *            El/La activo a setear.
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getCodigoFNCL() {
        return codigoFNCL;
    }

    public void setCodigoFNCL(String codigoFNCL) {
        this.codigoFNCL = codigoFNCL;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DomiciliosCuentaDto getDomicilio() {
        return domicilio;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public TipoDomicilioDto getTipoDomicilio() {
        return tipoDomicilio;
    }

    public void setTipoDomicilio(TipoDomicilioDto tipoDomicilio) {
        this.tipoDomicilio = tipoDomicilio;
    }
    
    /**
     * @see ar.com.nextel.framework.AbstractIdentifiableObject#hashCode()
     */
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @return Retorna el/la idVantive.
     */
    public Long getIdVantive() {
        return this.idVantive;
    }

    /**
     * @param idVantive
     *            El/La idVantive a setear.
     */
    public void setIdVantive(Long idVantive) {
        this.idVantive = idVantive;
    }

    /**
     * DeepCopy del TipoDomicilioAsociado.
     * 
     * @return TipoDomicilioAsociado nuevo con un estado copia de este
     */
//    protected TipoDomicilioAsociadoDto copy() {
//        TipoDomicilioAsociadoDto nuevoTipoDomicilioAsociado = new TipoDomicilioAsociadoDto();
//        nuevoTipoDomicilioAsociado.setActivo(this.getActivo());
//        nuevoTipoDomicilioAsociado.setEstado(this.getEstado());
//        nuevoTipoDomicilioAsociado.setPrincipal(this.getPrincipal());
//        nuevoTipoDomicilioAsociado.setTipoDomicilio(this.getTipoDomicilio());
//        return nuevoTipoDomicilioAsociado;
//    }	
	
}

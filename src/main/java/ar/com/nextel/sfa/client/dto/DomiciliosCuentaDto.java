package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DomiciliosCuentaDto implements IsSerializable, IdentifiableDto {
	
	private Long id;
	private String calle;
	private Long numero;
	private String piso;
	private String codigo_postal;
	private String departamento;
	private String entre_calle;
	private String manzana;
	private String puerta;
	private String y_calle;	
	private String observaciones;
	private String localidad;
	private String partido;
	private ProvinciaDto provincia;
	private String cpa;
	private String torre;
	private String unidad_funcional;
	private Boolean no_normalizar;
	private Boolean validado;
//	private String no_normalizar;
//	private String validado;
	private String en_carga;
	private String nombre_usuario_ultima_modificacion;
	private String fecha_ultima_modificacion;
	private String activo;
    private List<TipoDomicilioAsociadoDto> tiposDomicilioAsociado = new ArrayList<TipoDomicilioAsociadoDto>();
	//Lo seteo en True por defecto, porque esto no se guarda en la base, 
    //y cuando venga el dato, siempre estara deshabilitado, a menos que sea un domicilio Nuevo.
    private boolean locked = true;
	
	public String getDomicilios() {
	/** Muestra la concatenación de Calle, Número, Piso, Dpto., UF, Torre, Localidad, Partido, Provincia, Código Postal y CPA para cada domicilio habilitado.*/
	//String domicilios = this.calle + " " + (this.numero!=null? this.numero:"") + " " + (this.piso!=null? this.piso:"") + " " + (this.departamento!=null? this.departamento:"") + " " + (this.torre!=null? this.torre:"") + " " + this.localidad + " " + this.partido + " " + this.provincia.getDescripcion() + " " + (this.codigo_postal!=null? this.codigo_postal:"") + " " + this.cpa;	
	
	//TODO: Va el de arriba!!!
	String domicilios = this.calle + " " + (this.numero!=null? this.numero:"") + " " + (this.piso!=null? this.piso:"") + " " + (this.departamento!=null? this.departamento:"") + " " + (this.torre!=null? this.torre:"") + " " + this.localidad + " " + (this.codigo_postal!=null? this.codigo_postal:"") + " " + this.cpa;
		
	return domicilios;
	}
	
//	public List<TipoDomicilioAsociadoDto> getTiposDomicilioAsociado() {
//		return tiposDomicilioAsociado;
//	}

//	public void setTiposDomicilioAsociado(Set<TipoDomicilioAsociadoDto> tipoDomicilioAsociado) {
//		this.tiposDomicilioAsociado = tipoDomicilioAsociado;
//	}
	
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getCodigo_postal() {
		return codigo_postal;
	}
	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getEntre_calle() {
		return entre_calle;
	}
	public void setEntre_calle(String entre_calle) {
		this.entre_calle = entre_calle;
	}
	public String getManzana() {
		return manzana;
	}
	public void setManzana(String manzana) {
		this.manzana = manzana;
	}
	public String getPuerta() {
		return puerta;
	}
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}
	public String getY_calle() {
		return y_calle;
	}
	public void setY_calle(String y_calle) {
		this.y_calle = y_calle;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getPartido() {
		return partido;
	}
	public void setPartido(String partido) {
		this.partido = partido;
	}
	public ProvinciaDto getProvincia() {
		return provincia;
	}
	public void setProvincia(ProvinciaDto provincia) {
		this.provincia = provincia;
	}
	public String getCpa() {
		return cpa;
	}
	public void setCpa(String cpa) {
		this.cpa = cpa;
	}
	public String getTorre() {
		return torre;
	}
	public void setTorre(String torre) {
		this.torre = torre;
	}
	public String getUnidad_funcional() {
		return unidad_funcional;
	}
	public void setUnidad_funcional(String unidad_funcional) {
		this.unidad_funcional = unidad_funcional;
	}
	public Boolean getNo_normalizar() {
		return no_normalizar;
	}
	public void setNo_normalizar(Boolean no_normalizar) {
		this.no_normalizar = no_normalizar;
	}
	public Boolean getValidado() {
		return validado;
	}
	public void setValidado(Boolean validado) {
		this.validado = validado;
	}
	public String getEn_carga() {
		return en_carga;
	}
	public void setEn_carga(String en_carga) {
		this.en_carga = en_carga;
	}
	public String getNombre_usuario_ultima_modificacion() {
		return nombre_usuario_ultima_modificacion;
	}
	public void setNombre_usuario_ultima_modificacion(String nombre_usuario_ultima_modificacion) {
		this.nombre_usuario_ultima_modificacion = nombre_usuario_ultima_modificacion;
	}
	public String getFecha_ultima_modificacion() {
		return fecha_ultima_modificacion;
	}
	public void setFecha_ultima_modificacion(String fecha_ultima_modificacion) {
		this.fecha_ultima_modificacion = fecha_ultima_modificacion;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
    /**
     * @return Retorna el/la tipoDomicilio.
     */
    public List<TipoDomicilioAsociadoDto> getTiposDomicilioAsociado() {
        return tiposDomicilioAsociado;
    }

    /**
     * @param tiposDomicilio El/La tipoDomicilio a setear.
     */
    public void setTiposDomicilioAsociado(List<TipoDomicilioAsociadoDto> tiposDomicilioAsociado) {
        this.tiposDomicilioAsociado = tiposDomicilioAsociado;
    }

    /**
     * @param tipoDomicilioAsociado
//     */
//    public void addTipoDomicilioAsociado(TipoDomicilioAsociadoDto tipoDomicilioAsociado) {
//        boolean added = this.tiposDomicilioAsociado.add(tipoDomicilioAsociado);
//        if (added && tipoDomicilioAsociado != null) {
//            tipoDomicilioAsociado.setDomicilio(this);
//        }
//    }

    /**
     * Agrega la colecci�n de tipoDomicilioAsociado a los tipo domicilio asociado al domicilio
     * 
     * @param tipoDomicilioAsociados
     */
//    public void addTipoDomicilioAsociados(Collection<TipoDomicilioAsociadoDto> tipoDomicilioAsociados) {
//        for (TipoDomicilioAsociadoDto tipoDomicilioAsociado : tipoDomicilioAsociados) {
//            this.addTipoDomicilioAsociado(tipoDomicilioAsociado);
//        }
//    }

    /**
     * @param tipoDomicilioAsociado
     */
    public void removeTipoDomicilioAsociado(TipoDomicilioAsociadoDto tipoDomicilioAsociado) {
        this.tiposDomicilioAsociado.remove(tipoDomicilioAsociado);
    }

    /**
     * Devuelve los tipos domicilio activos asociados a este domicilio
     * 
     * @return
     */
    public List<TipoDomicilioAsociadoDto> getTiposDomicilioAsociadosActivos() {
    	List<TipoDomicilioAsociadoDto> result = new ArrayList<TipoDomicilioAsociadoDto>();
        for (TipoDomicilioAsociadoDto tipoDomicilioAsociado : this.tiposDomicilioAsociado) {
            if (tipoDomicilioAsociado.getActivo().booleanValue()) {
                result.add(tipoDomicilioAsociado);
            }
        }
        return result;
    }
    
    public TipoDomicilioAsociadoDto getTipoDomicilioAsociado(TipoDomicilioDto tipoDomicilio) {
    	TipoDomicilioAsociadoDto tipoDomicilioAsociado = null;
        for (TipoDomicilioAsociadoDto currentTipoDomicilioAsociado : this.tiposDomicilioAsociado) {
            if (currentTipoDomicilioAsociado.getTipoDomicilio().equals(tipoDomicilio)) {
                tipoDomicilioAsociado = currentTipoDomicilioAsociado;
            }
        }
        return tipoDomicilioAsociado;
    }
    
    /**
     * Copia todos los tipos de domicilio asociados.
     * 
     * @param nuevoDomicilio nuevo domicilio a agregar los tipos de documento
     */
//    private void copyAllTiposDomicilioAsociados(DomiciliosCuentaDto nuevoDomicilio) {
//        for (TipoDomicilioAsociadoDto tipo : this.getTiposDomicilioAsociado()) {
//        	TipoDomicilioAsociadoDto nuevoTipo = tipo.copy();
//            nuevoDomicilio.addTipoDomicilioAsociado(nuevoTipo);
//        }
//    }
    
    public boolean esPrincipalDeEntrega(TipoDomicilioDto tipoDomicilioEntregaDto) {
        return esPrincipalDe(tipoDomicilioEntregaDto);
    }
    
    public boolean esPrincipalDeFacturacion(TipoDomicilioDto tipoDomicilioFacturacionDto) {
        return esPrincipalDe(tipoDomicilioFacturacionDto);
    }
    
    private boolean esPrincipalDe(TipoDomicilioDto tipoDomicilio) {
        List<TipoDomicilioAsociadoDto> tiposDomicilioAsociadosActivos = this.getTiposDomicilioAsociadosActivos();
        
        for (TipoDomicilioAsociadoDto tipoDomicilioAsoc : tiposDomicilioAsociadosActivos) {
            if  (tipoDomicilioAsoc.getPrincipal()) {
            		//OJO con esto de abajo, puede ser que no sea TRUE!!!
            		//&& tipoDomicilioAsoc.getTipoDomicilio().getCodigoVantive().equals(tipoDomicilio.getCodigoVantive())) {
                return true;
            }
        }
        return false;
    }

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
}

package ar.com.nextel.sfa.client.dto;

import ar.com.nextel.services.nextelServices.veraz.VerazHistoryItem;

public class VerazResponseInfoDto {

	private static final int MAX_LENGTH_APE_NOMBRE = 19;
	private static final int MAX_LENGTH_RAZON_SOCIAL = 40;

    protected static final String REVISAR = "REVISAR";
    protected static final String ACEPTAR = "ACEPTAR";
    protected static final String RECHAZAR = "RECHAZAR";
    
    private String estado;
    private long idEstado;
    private String nombre;
    private String apellido;
    private String razonSocial;

    private String rangoTel;
    private String lote;
    private int scoreDni;
    private int score;
    private String explicacion;

    private static final String IDENTIDAD = "IDENTIDAD";
    private static final String ACEPTAR_3 = "ACEPTAR_3";
    private static final String ACEPTAR_5 = "ACEPTAR_5";
    
    private String mensaje;
    
    private static long estadoAceptar = 1L, estadoRevisar = 2L, estadoRechazar = 3L;

    /**
     * popula el mensaje.
     * @param item 
     */
    protected void populateMenssage(VerazHistoryItem item) {
        this.setMensaje(item.getVerazRule().getMsg());
    }
    
    /**
     * @param mensaje
     *            El/La mensaje a setear.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    /**
     * @return Retorna el/la rangoTel.
     */
    public String getRangoTel() {
        return rangoTel;
    }

    /**
     * @param rangoTel
     *            El/La rangoTel a setear.
     */
    public void setRangoTel(String rangoTel) {
        this.rangoTel = rangoTel;
    }


    /**
     * @return Retorna el/la razonSocial.
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial
     *            El/La razonSocial a setear.
     */
    public void setRazonSocial(String razonSocial) {
    	this.razonSocial = razonSocial;

    	// Por limitaciones de la DB se restringe la logitud m�xima de este campo
    	if (this.razonSocial != null && this.razonSocial.length() > MAX_LENGTH_RAZON_SOCIAL) {
    		this.razonSocial = this.razonSocial.substring(0, MAX_LENGTH_RAZON_SOCIAL);
    	}
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
        
        // Por limitaciones de la DB se restringe la logitud m�xima de este campo
        if (this.apellido != null && this.apellido.length() > MAX_LENGTH_APE_NOMBRE) {
        	this.apellido = this.apellido.substring(0, MAX_LENGTH_APE_NOMBRE);
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        
        // Por limitaciones de la DB se restringe la logitud m�xima de este campo
        if (this.nombre != null && this.nombre.length() > MAX_LENGTH_APE_NOMBRE) {
        	this.nombre = this.nombre.substring(0, MAX_LENGTH_APE_NOMBRE);
        }
    }
    
	public void populate(VerazHistoryItem item) {
		this.setLote(item.getLote());
        this.setApellido(item.getApellido());
        this.setEstado(item.getEstado());
        this.setNombre(item.getNombre());
        this.setRazonSocial(item.getRazonSocial());
        
        if (item.getScoreDni() != null) {
            this.setScoreDni(item.getScoreDni().intValue());
        }
        if (item.getScore() != null) {
            this.setScore(item.getScore().intValue());
        }
        if (item.getExplicacion() != null) {
            this.setExplicacion(item.getExplicacion());
        }
        this.populateState();
        this.populateMenssage(item);
    }

	 protected void populateState() {
	        if (IDENTIDAD.equals(this.getEstado())) {
	            this.setEstado(REVISAR);
	        }
	        if (ACEPTAR_3.equals(this.getEstado())) {
	            this.setEstado(ACEPTAR);
	        }
	        if (ACEPTAR_5.equals(this.getEstado())) {
	            this.setEstado(ACEPTAR);
	        }
	        if(RECHAZAR.equals(this.getEstado())){
	            this.setEstado(RECHAZAR);
	        }
	        this.populateStateID();
	    }
	 
    protected void populateStateID() {
    	if (ACEPTAR.equals(estado)) {
            this.setIdEstado(estadoAceptar);
        } else if (RECHAZAR.equals(estado)) {
            this.setIdEstado(estadoRechazar);
        } else {
            this.setIdEstado(estadoRevisar);
        }		
	}

	public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getScoreDni() {
        return scoreDni;
    }

    public void setScoreDni(int scoreDni) {
        this.scoreDni = scoreDni;
    }

    
    /**
     * Indica si el <tt>Veraz</tt> fue <tt>Aceptado</tt>.
     * 
     * @return <code>true</code> si el <tt>Veraz</tt> fue <tt>Aceptado</tt>, <code>false</code> en caso contrario
     */
    public boolean isAceptada() {
        return ACEPTAR.equalsIgnoreCase(this.estado);
    }
    
    /**
     * Indica si el <tt>Veraz</tt> fue <tt>Rechazado</tt>.
     * 
     * @return <code>true</code> si el <tt>Veraz</tt> fue <tt>Rechazado</tt>, <code>false</code> en caso contrario
     */
    public boolean isRechazada() {
        return RECHAZAR.equalsIgnoreCase(this.estado);
    }
    
    /**
     * Indica si el <tt>Veraz</tt> debe ser <tt>Revisado</tt>.
     * 
     * @return <code>true</code> si el <tt>Veraz</tt> debe ser <tt>Revisado</tt>, <code>false</code> en caso contrario
     */
    public boolean isRevisada() {
        return REVISAR.equalsIgnoreCase(this.estado);
    }
    
    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }  
    
    public String getMensaje(){
        return this.mensaje;
    }
    
    protected String getExplicacion(){
        return this.explicacion;
    }
	
}

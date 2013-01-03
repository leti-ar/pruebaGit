package ar.com.nextel.sfa.client.dto;

import java.util.HashMap;

import ar.com.nextel.business.constants.KnownInstanceIdentifier;
import ar.com.nextel.model.cuentas.beans.TipoVendedor;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VendedorDto implements IsSerializable, ListBoxItem {
	
	private Long id;
	private UsuarioDto usuarioDto;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalidadDto localidad;
    private TipoVendedorDto tipoVendedor;
    private Long idSucursal;
    private boolean telemarketing;
    public static final String TIPO_VENDEDOR_DEALER = "TIPO_VENDEDOR_DEALER";
    public static final String TIPO_VENDEDOR_AP = "TIPO_VENDEDOR_AP";
    public static final String TIPO_VENDEDOR_ADM_CREDITOS = "TIPO_VENDEDOR_CREDITOS";
    public static final String TIPO_VENDEDOR_TELEMARKETING = "TIPO_VENDEDOR_TELEMARKETING";
    public static final String TIPO_VENDEDOR_EECC = "TIPO_VENDEDOR_EECC";
    public static final String TIPO_VENDEDOR_LAP = "TIPO_VENDEDOR_LAP";
    public static final String TIPO_VENDEDOR_MINORISTA = "TIPO_VENDEDOR_MINORISTA";
    public static final String TIPO_VENDEDOR_MAYORISTA = "TIPO_VENDEDOR_MAYORISTA";
    public static final String TIPO_VENDEDOR_SALON = "TIPO_VENDEDOR_SALON";    
    
    public boolean isEECC(){
    	HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
    	if(instancias != null && this.getTipoVendedor() != null){
    		return instancias.get(TIPO_VENDEDOR_EECC).equals(this.getTipoVendedor().getId());
    	}
    	return false;
    }
    
    public boolean isVendedorSalon() {
    	HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
    	if(instancias != null && this.getTipoVendedor() != null){
    		return instancias.get(TIPO_VENDEDOR_SALON).equals(this.getTipoVendedor().getId());
    	}
    	return false;
    }
    public boolean isLap(){
    	HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
    	if(instancias != null && this.getTipoVendedor() != null){
    		return instancias.get(TIPO_VENDEDOR_LAP).equals(this.getTipoVendedor().getId());
    	}
    	return false;
    }
    
//    public boolean isTelemarketing(){
//    	HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
//    	if(instancias != null && this.getTipoVendedor() != null){
//    		return instancias.get(TIPO_VENDEDOR_TELEMARKETING).equals(this.getTipoVendedor().getId());
//    	}
//    	return false;
//    }

    //JM Mejoras telemarketing
    //en lugar del id del tipo del vendedor se evalúa el codigo vantive por eso lo trae el dozer al mapear con Vendedor
    public boolean isTelemarketing() {
    	return this.telemarketing;
    }
    
    public void setTelemarketing(boolean telemarketing) {
    	this.telemarketing = telemarketing;
    }
    
    public boolean isDealer(){
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null && this.getTipoVendedor() != null){
			return instancias.get(TIPO_VENDEDOR_DEALER).equals(this.getTipoVendedor().getId());
		}
		return false;
	}
	
	public boolean isAP(){
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null && this.getTipoVendedor() != null){
			return instancias.get(TIPO_VENDEDOR_AP).equals(this.getTipoVendedor().getId());
		}
		return false;
	}
	
	public boolean isADMCreditos(){
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null && this.getTipoVendedor() != null){
			return instancias.get(TIPO_VENDEDOR_ADM_CREDITOS).equals(this.getTipoVendedor().getId());
		}
		return false;
	}

	public Long getId() {
		return id; 
	}

	public UsuarioDto getUsuarioDto() {
		return usuarioDto;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsuarioDto(UsuarioDto usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalidadDto getLocalidad() {
		return localidad;
	}

	public void setLocalidad(LocalidadDto localidad) {
		this.localidad = localidad;
	}

	public String getItemText() {
		return apellido + ", " + nombre;
	}

	public String getItemValue() {
		return id + "";
	}

	public void setTipoVendedor(TipoVendedorDto tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

	public TipoVendedorDto getTipoVendedor() {
		return tipoVendedor; 
	}
	
	public String getApellidoYNombre() {
    	return getApellido() + ", " + getNombre();
    }
	
	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

}

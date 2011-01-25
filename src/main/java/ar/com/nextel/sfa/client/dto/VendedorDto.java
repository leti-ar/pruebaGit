package ar.com.nextel.sfa.client.dto;

import java.util.HashMap;

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
    
    public static final String TIPO_VENDEDOR_DEALER = "TIPO_VENDEDOR_DEALER";
    public static final String TIPO_VENDEDOR_AP = "TIPO_VENDEDOR_AP";
    public static final String TIPO_VENDEDOR_ADM_CREDITOS = "TIPO_VENDEDOR_CREDITOS";
    public static final String TIPO_VENDEDOR_TELEMARKETING = "TIPO_VENDEDOR_TELEMARKETING";


    
    public boolean isTelemarketing(){
    	HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
    	if(instancias != null && this.getTipoVendedor() != null){
    		return instancias.get(TIPO_VENDEDOR_TELEMARKETING).toString().equals(this.getTipoVendedor().getCodigo());
    	}
    	return false;
    }

    public boolean isDealer(){
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null && this.getTipoVendedor() != null){
			return instancias.get(TIPO_VENDEDOR_DEALER).toString().equals(this.getTipoVendedor().getCodigo());
		}
		return false;
	}
	
	public boolean isAP(){
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null && this.getTipoVendedor() != null){
			return instancias.get(TIPO_VENDEDOR_AP).toString().equals(this.getTipoVendedor().getCodigo());
		}
		return false;
	}
	
	public boolean isADMCreditos(){
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null && this.getTipoVendedor() != null){
			return instancias.get(TIPO_VENDEDOR_ADM_CREDITOS).toString().equals(this.getTipoVendedor().getCodigo());
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
}

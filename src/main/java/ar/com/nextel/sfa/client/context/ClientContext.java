package ar.com.nextel.sfa.client.context;

import java.util.HashMap;

import ar.com.nextel.sfa.client.dto.ClienteNexusDto;
import ar.com.nextel.sfa.client.dto.UsuarioDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;

/**
 * @author jlgperez
 * 
 */
public class ClientContext {

	private static ClientContext instance;
	private UsuarioDto usuario;
	private VendedorDto vendedor;
	private HashMap<String, Boolean> mapaPermisos;
	private HashMap<String, String> secretParams = new HashMap();
	//MGR - Integracion
	private ClienteNexusDto clienteNexus;
	//MGR - #1050
	private HashMap<String, Long> knownInstance;
    public static final String ST_PARAMS_KEY_RECARGA_TIPO_ORDEN = "RecargaTipoOrden";
    public static final String ST_PARAMS_VALUE_TRUE = "true";
	
	private ClientContext() {
	}

	public static ClientContext getInstance() {
		if (instance == null) {
			instance = new ClientContext();
		}
		return instance;
	}

	public static void setInstance(ClientContext instance) {
		ClientContext.instance = instance;
	}

	public boolean checkPermiso(String tag) {
		return getMapaPermisos().get(tag) != null ? getMapaPermisos().get(tag) : false;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public HashMap<String, Boolean> getMapaPermisos() {
		return mapaPermisos;
	}

	public void setMapaPermisos(HashMap<String, Boolean> mapaPermisos) {
		this.mapaPermisos = mapaPermisos;
	}

	public VendedorDto getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorDto vendedor) {
		this.vendedor = vendedor;
	}

	public HashMap<String, String> getSecretParams() {
		return secretParams;
	}

	public ClienteNexusDto getClienteNexus() {
		return clienteNexus;
	}

	public void setClienteNexus(ClienteNexusDto clienteNexus) {
		this.clienteNexus = clienteNexus;
	}
	
	//MGR - Integracion
	/**
	 * Indica si se viene de Nexus o no
	 */
	public boolean vengoDeNexus(){
		if(this.clienteNexus != null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Verifica si se llamo de Nexus a la aplicacion enviandole un cliente o no
	 * @return True si se llamo desde Nexus con un cliente existente. 
	 * False si se llamo desde Nexus sin un cliente existente	
	 */
	public boolean soyClienteNexus(){
		if(vengoDeNexus() && this.getClienteNexus().getCustomerCode() != null){
			return true;
		}else{
			return false;
		}
	}

	public HashMap<String, Long> getKnownInstance() {
		return knownInstance;
	}

	public void setKnownInstance(HashMap<String, Long> knownInstance) {
		this.knownInstance = knownInstance;
	}

}

package ar.com.nextel.sfa.client.context;

import java.util.HashMap;

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

}

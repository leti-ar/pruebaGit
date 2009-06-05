package ar.com.nextel.sfa.client.context;

import java.util.Map;

import ar.com.nextel.sfa.client.dto.UsuarioDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;

/**
 * @author jlgperez
 * 
 */
public class ClientContext {

	private static ClientContext instance;
	private UsuarioDto usuario;
	private Map <String,Boolean>mapaPermisos;

	private ClientContext() {
	}
	
	public static ClientContext getInstance() {
		if(instance == null){
			instance = new ClientContext();
		}
		return instance;
	}

	public static void setInstance(ClientContext instance) {
		ClientContext.instance = instance;
	}

	public boolean checkPermiso(String tag) {
		return getMapaPermisos().get(tag);
	}
	
	public UsuarioDto getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public Map<String, Boolean> getMapaPermisos() {
		return mapaPermisos;
	}
	public void setMapaPermisos(Map<String, Boolean> mapaPermisos) {
		this.mapaPermisos = mapaPermisos;
	}
}

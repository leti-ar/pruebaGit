package ar.com.nextel.sfa.client.context;

import ar.com.nextel.sfa.client.dto.UsuarioDto;

/**
 * @author jlgperez
 * 
 */
public class ClientContext {

	private static ClientContext instance;
	private UsuarioDto usuario;

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

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

}

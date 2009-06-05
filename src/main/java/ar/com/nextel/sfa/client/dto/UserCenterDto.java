package ar.com.nextel.sfa.client.dto;

import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserCenterDto implements IsSerializable {

	public UsuarioDto usuario;
	public Map <String,Boolean>mapaPermisos;
	
	
	public UsuarioDto getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}
	
	public Map <String,Boolean>getMapaPermisos() {
		return mapaPermisos;
	}
	public void setMapaPermisos(Map <String,Boolean>mapaPermisos) {
		this.mapaPermisos = mapaPermisos;
	}
	
}

package ar.com.nextel.sfa.client.dto;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserCenterDto implements IsSerializable {

	private UsuarioDto usuario;
	private VendedorDto vendedor;
	private HashMap<String, Boolean> mapaPermisos;

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

}

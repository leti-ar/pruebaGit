package ar.com.nextel.sfa.client.dto;

import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserCenterDto implements IsSerializable {

	private UsuarioDto usuario;
	private VendedorDto vendedor;
	private Map<String, Boolean> mapaPermisos;

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

	public VendedorDto getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorDto vendedor) {
		this.vendedor = vendedor;
	}

}

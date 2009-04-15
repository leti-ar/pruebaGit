package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VendedorDto implements IsSerializable{

	private Long id;
	private UsuarioDto usuarioDto;

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

}

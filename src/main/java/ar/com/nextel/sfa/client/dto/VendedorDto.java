package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VendedorDto implements IsSerializable {

	private Long id;
	private UsuarioDto usuarioDto;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

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
	

}

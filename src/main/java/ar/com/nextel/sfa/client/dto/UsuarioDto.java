package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UsuarioDto implements IsSerializable {

	private Long id;
	private String userName;
	private List<String> roles = new ArrayList();

	public UsuarioDto() {
	}
	
	public UsuarioDto(Long id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}

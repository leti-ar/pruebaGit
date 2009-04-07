package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDto {

	private Long id;
	private String userName;
	private List roles = new ArrayList();

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public List getRoles() {
		return roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRoles(List roles) {
		this.roles = roles;
	}

}

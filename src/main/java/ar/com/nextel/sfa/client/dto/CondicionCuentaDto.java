package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CondicionCuentaDto implements IsSerializable {

	private long id;
	private String code;
	private String descripcion;
	private String codigoVantive;

	public CondicionCuentaDto() {
	}
	public CondicionCuentaDto(long id, String code, String descripcion) {
		super();
		this.id = id;
		this.code = code;
		this.descripcion=descripcion;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}
	public String getCodigoVantive() {
		return codigoVantive;
	}
}

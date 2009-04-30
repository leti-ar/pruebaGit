package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class GrupoDocumentoDto implements ListBoxItem, IsSerializable {

	private long code;
	private String descripcion;

	public GrupoDocumentoDto() {
	}

	public GrupoDocumentoDto(long code, String descripcion) {
		super();
		this.code = code;
		this.descripcion = descripcion;
	}

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return code + "";
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

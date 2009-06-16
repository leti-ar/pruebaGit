package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class GrupoDocumentoDto implements ListBoxItem, IsSerializable {

	private Long id;
	private String descripcion;

	public GrupoDocumentoDto() {
	}

	public GrupoDocumentoDto(long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return id + "";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

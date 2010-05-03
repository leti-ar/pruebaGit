package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoPlanDto implements IsSerializable, ListBoxItem {

	public static String TIPO_PLAN_DIRECTO_O_EMPRESA_CODE = "Y";
	private static long TIPO_PLAN_DIRECTO_ID = 8;
	private static long TIPO_PLAN_EMPRESA_ID = 7;

	private Long id;
	private String descripcion;
	private String codigoBSCS;

	public TipoPlanDto() {
	}

	public TipoPlanDto(Long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoBSCS() {
		return codigoBSCS;
	}

	public void setCodigoBSCS(String codigoBSCS) {
		this.codigoBSCS = codigoBSCS;
	}

	public boolean isEmpresa() {
		return id.longValue() == TIPO_PLAN_EMPRESA_ID;
	}
	
	public boolean isDirecto() {
		return id.longValue() == TIPO_PLAN_DIRECTO_ID;
	}
}

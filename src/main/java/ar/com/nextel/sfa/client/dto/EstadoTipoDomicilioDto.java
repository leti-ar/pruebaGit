package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;

public enum EstadoTipoDomicilioDto {
	PRINCIPAL(2L, "Principal"), SI(0L, "Si"), NO(1L, "No");

	private Long id;
	private String descripcion;

	private EstadoTipoDomicilioDto(Long id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static List<ListBoxItem> getListBoxItems() {
		List<ListBoxItem> lista = new ArrayList();
		for (int i = 0; i < values().length; i++) {
			lista.add(new ListBoxItemImpl(values()[i].getDescripcion(), "" + values()[i].getId()));
		}
		return lista;
	}
}

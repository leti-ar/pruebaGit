package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemSolicitudTasadoDto implements IsSerializable, ListBoxItem {

	private Long id;
	private Double precioLista;
	private ItemSolicitudDto item;
	
	//MGR -#ISDM 1785 - Para saber como mostrar el texto
	private ListaPreciosDto listaPrecios;

	/** Descripcion del ItemSolicitudDto */
	public String getItemText() {
		//MGR -#ISDM 1785 - Si la lista concatena, como Texto se debe mostrar:
		//		'Segmento1 - Descripcion'
		if(listaPrecios != null && listaPrecios.getConcatena() != null &&
				listaPrecios.getConcatena()){
			return item.getSegment1().substring(8) + " - " + item.getDescripcion();
		}
		return item.getDescripcion();
	}

	/** Id del ItemSolicitudDto */
	public String getItemValue() {
		return "" + item.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(Double precioLista) {
		this.precioLista = precioLista;
	}

	public ItemSolicitudDto getItem() {
		return item;
	}

	public void setItem(ItemSolicitudDto item) {
		this.item = item;
	}

	public ListaPreciosDto getListaPrecios() {
		return listaPrecios;
	}

	public void setListaPrecios(ListaPreciosDto listaPrecios) {
		this.listaPrecios = listaPrecios;
	}
}

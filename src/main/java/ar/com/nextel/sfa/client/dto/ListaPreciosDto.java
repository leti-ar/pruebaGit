package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

//MGR - #998 - Se agrega que implemente Comparable
public class ListaPreciosDto extends EnumDto implements IsSerializable, ListBoxItem, Comparable {

	//MGR - #1039
	public static final String AR_EQUIP_VTA_SOLO_EQUIP = "AR_EQUIP_VTA_SOLO_EQUIP";
	
	private List<TerminoPagoValidoDto> terminosPagoValido;
	private List<ItemSolicitudTasadoDto> itemsListaPrecioVisibles;
	private List<AjustesDto> ajustes;
	
	//MGR - #998
	private int ordenAparicion;
	
	private String codigoBSCS;
	
	//MGR - #ISDM 1785 - Indica si se contatena Segment1 y Descripcion como descripci�n
	private Boolean concatena;

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

	public List<TerminoPagoValidoDto> getTerminosPagoValido() {
		return terminosPagoValido;
	}

	public void setTerminosPagoValido(List<TerminoPagoValidoDto> terminosPagoValido) {
		this.terminosPagoValido = terminosPagoValido;
	}

	public List<ItemSolicitudTasadoDto> getItemsListaPrecioVisibles() {
		return itemsListaPrecioVisibles;
	}

	public void setItemsListaPrecioVisibles(List<ItemSolicitudTasadoDto> itemsListaPrecioVisibles) {
		this.itemsListaPrecioVisibles = itemsListaPrecioVisibles;
	}

	public List<AjustesDto> getAjustes() {
		return ajustes;
	}

	public void setAjustes(List<AjustesDto> ajustes) {
		this.ajustes = ajustes;
	}

	//MGR - #998
	public int getOrdenAparicion() {
		return ordenAparicion;
	}

	public void setOrdenAparicion(int ordenAparicion) {
		this.ordenAparicion = ordenAparicion;
	}
	
	//MGR - #998 - La lista de precio debe estar ordenada por el campo Orden_Aparicion
	public int compareTo(Object o) {
		ListaPreciosDto o1 = (ListaPreciosDto)o;
		int codigoBSCSint = Integer.parseInt(this.codigoBSCS);
		int o1CodigoBSCSint = Integer.parseInt(o1.codigoBSCS);
		//return this.ordenAparicion <= o1.ordenAparicion? -1 : 1;
		return codigoBSCSint <= o1CodigoBSCSint? -1 : 1;
	}

	public Boolean getConcatena() {
		return concatena;
	}

	public void setConcatena(Boolean concatena) {
		this.concatena = concatena;
	}

	public String getCodigoBSCS() {
		return codigoBSCS;
	}

	public void setCodigoBSCS(String codigoBSCS) {
		this.codigoBSCS = codigoBSCS;
	}
}

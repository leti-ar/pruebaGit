package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TerminoPagoValidoDto extends EnumDto implements IsSerializable, ListBoxItem {

	private TerminoPagoDto terminoPago;
	private Double ajuste;
	
	//MGR - #1077
	/**
     * Indica si se aplica como default
     */
    private Boolean terminoPagoDefault;
    
	public String getItemText() {
		return getDescripcion();
	}

	public String getItemValue() {
		return "" + id;
	}

	public Double getAjuste() {
		return ajuste;
	}

	public String getDescripcion() {
		return terminoPago.getDescripcion();
	}

	public void setAjuste(Double ajuste) {
		this.ajuste = ajuste;
	}

	public TerminoPagoDto getTerminoPago() {
		return terminoPago;
	}

	public void setTerminoPago(TerminoPagoDto terminoPago) {
		this.terminoPago = terminoPago;
	}
	
	//MGR - #1077
	public Boolean getTerminoPagoDefault() {
		return terminoPagoDefault;
	}

	public void setTerminoPagoDefault(Boolean terminoPagoDefault) {
		this.terminoPagoDefault = terminoPagoDefault;
	}
}

package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextArea;

public class CrearSSUIData extends UIData{

	private RegexTextBox nss;
	private RegexTextBox nflota;
	private ListBox origen;
	private ListBox entrega;
	private ListBox facturacion;
	private TextArea aclaracion;
	private InlineHTML credFidelDisponibleText;
	private InlineHTML credFidelVencimientoText;
	private RegexTextBox credFidelizacion;
	private RegexTextBox pataconex;
	private CheckBox firmarss;
	private ListBox anticipo;
	private TextArea observaciones;
	private InlineHTML precioListaText;
	private InlineHTML desvioText;
	private InlineHTML credFidelText;
	private InlineHTML pataconexText;
	private InlineHTML precioVentaText;

	public CrearSSUIData() {
		nss = new RegexTextBox();
		nflota = new RegexTextBox();
		origen = new ListBox();
		entrega = new ListBox();
		facturacion = new ListBox();
		aclaracion = new TextArea();
		credFidelizacion = new RegexTextBox();
		pataconex = new RegexTextBox();
		firmarss = new CheckBox();
		anticipo = new ListBox();
		observaciones = new TextArea();
		
		credFidelDisponibleText = new InlineHTML("$ 0.00");
		credFidelVencimientoText = new InlineHTML("");
		precioListaText = new InlineHTML("$ 0.00");
		desvioText = new InlineHTML("$ 0.00");
		credFidelText = new InlineHTML("$ 0.00");
		pataconexText = new InlineHTML("$ 0.00");
		precioVentaText = new InlineHTML("$ 0.00");
	}
	
	public RegexTextBox getNss() {
		return nss;
	}

	public RegexTextBox getNflota() {
		return nflota;
	}

	public ListBox getOrigen() {
		return origen;
	}

	public ListBox getEntrega() {
		return entrega;
	}

	public ListBox getFacturacion() {
		return facturacion;
	}

	public TextArea getAclaracion() {
		return aclaracion;
	}

	public InlineHTML getCredFidelDisponibleText() {
		return credFidelDisponibleText;
	}

	public InlineHTML getCredFidelVencimientoText() {
		return credFidelVencimientoText;
	}

	public RegexTextBox getCredFidelizacion() {
		return credFidelizacion;
	}

	public RegexTextBox getPataconex() {
		return pataconex;
	}

	public CheckBox getFirmarss() {
		return firmarss;
	}

	public ListBox getAnticipo() {
		return anticipo;
	}

	public TextArea getObservaciones() {
		return observaciones;
	}

	public InlineHTML getPrecioListaText() {
		return precioListaText;
	}

	public InlineHTML getDesvioText() {
		return desvioText;
	}

	public InlineHTML getCredFidelText() {
		return credFidelText;
	}

	public InlineHTML getPataconexText() {
		return pataconexText;
	}

	public InlineHTML getPrecioVentaText() {
		return precioVentaText;
	}

	public void setNss(RegexTextBox nss) {
		this.nss = nss;
	}

	public void setNflota(RegexTextBox nflota) {
		this.nflota = nflota;
	}

	public void setOrigen(ListBox origen) {
		this.origen = origen;
	}

	public void setEntrega(ListBox entrega) {
		this.entrega = entrega;
	}

	public void setFacturacion(ListBox facturacion) {
		this.facturacion = facturacion;
	}

	public void setAclaracion(TextArea aclaracion) {
		this.aclaracion = aclaracion;
	}

	public void setCredFidelDisponibleText(InlineHTML credFidelDisponibleText) {
		this.credFidelDisponibleText = credFidelDisponibleText;
	}

	public void setCredFidelVencimientoText(InlineHTML credFidelVencimientoText) {
		this.credFidelVencimientoText = credFidelVencimientoText;
	}

	public void setCredFidelizacion(RegexTextBox credFidelizacion) {
		this.credFidelizacion = credFidelizacion;
	}

	public void setPataconex(RegexTextBox pataconex) {
		this.pataconex = pataconex;
	}

	public void setFirmarss(CheckBox firmarss) {
		this.firmarss = firmarss;
	}

	public void setAnticipo(ListBox anticipo) {
		this.anticipo = anticipo;
	}

	public void setObservaciones(TextArea observaciones) {
		this.observaciones = observaciones;
	}

	public void setPrecioListaText(InlineHTML precioListaText) {
		this.precioListaText = precioListaText;
	}

	public void setDesvioText(InlineHTML desvioText) {
		this.desvioText = desvioText;
	}

	public void setCredFidelText(InlineHTML credFidelText) {
		this.credFidelText = credFidelText;
	}

	public void setPataconexText(InlineHTML pataconexText) {
		this.pataconexText = pataconexText;
	}

	public void setPrecioVentaText(InlineHTML precioVentaText) {
		this.precioVentaText = precioVentaText;
	}

}

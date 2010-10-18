package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.nextel.sfa.client.widget.ValidationTextBox;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

public class DescuentoUIData extends UIData {

	private InlineLabel descuentoAplicadoLabel;
	private TextArea descuentoAplicado;
	private InlineLabel descuentoLabel;
	private ListBox tipoDeDescuento;
	private InlineLabel montoLabel;
	private InlineLabel porcentajeLabel;
	private ValidationTextBox montoTB;
	private ValidationTextBox porcentajeTB;
	private Label precioLista;
	private Label precioVenta;
	private InlineLabel precioListaLabel;
	private InlineLabel precioVentaLabel;

	public DescuentoUIData() {
		
		descuentoAplicadoLabel = new InlineLabel("Tipo de descuento aplicado: ");
		descuentoAplicadoLabel.setStyleName("labelDesc");
		descuentoAplicado = new TextArea();
		descuentoAplicado.setEnabled(false);
		descuentoAplicado.setReadOnly(true);
		descuentoLabel = new InlineLabel("Tipo de descuento: ");
		descuentoLabel.setStyleName("labelDesc");
		tipoDeDescuento = new ListBox();
		
		montoLabel = new InlineLabel("Monto: ");
		montoLabel.setStyleName("labelDesc");
		porcentajeLabel = new InlineLabel("Porcentaje: ");
		porcentajeLabel.setStyleName("labelDesc");
		montoTB = new ValidationTextBox("[0-9]+,?[0-9]{0,2}");
		montoTB.setStyleName("textBoxDesc");
		porcentajeTB = new ValidationTextBox("[0-9]+,?[0-9]{0,2}");
		porcentajeTB.setStyleName("textBoxDesc");
				
		precioListaLabel = new InlineLabel(Sfa.constant().precioLista());
		precioListaLabel.setStyleName("labelDesc");
		precioVentaLabel = new InlineLabel(Sfa.constant().precioVenta());
		precioVentaLabel.setStyleName("labelDesc");
		precioLista = new Label();
		precioVenta = new Label();
	}

	public InlineLabel getDescuentoAplicadoLabel() {
		return descuentoAplicadoLabel;
	}

	public void setDescuentoAplicadoLabel(
			InlineLabel descuentoAplicadoLabel) {
		this.descuentoAplicadoLabel = descuentoAplicadoLabel;
	}
	
	public TextArea getDescuentoAplicado() {
		return descuentoAplicado;
	}

	public void setDescuentoAplicado(TextArea descuentoAplicado) {
		this.descuentoAplicado = descuentoAplicado;
	}

	public InlineLabel getDescuentoLabel() {
		return descuentoLabel;
	}

	public void setDescuentoLabel(InlineLabel descuentoLabel) {
		this.descuentoLabel = descuentoLabel;
	}

	public ListBox getTipoDeDescuento() {
		return tipoDeDescuento;
	}

	public void setTipoDeDescuento(ListBox tipoDeDescuento) {
		this.tipoDeDescuento = tipoDeDescuento;
	}
	
	public InlineLabel getMontoLabel() {
		return montoLabel;
	}

	public void setMontoLabel(InlineLabel montoLabel) {
		this.montoLabel = montoLabel;
	}

	public void setMontoRB(InlineLabel montoLabel) {
		this.montoLabel = montoLabel;
	}

	public InlineLabel getPorcentajeLabel() {
		return porcentajeLabel;
	}

	public void setPorcentajeLabel(InlineLabel porcentajeLabel) {
		this.porcentajeLabel = porcentajeLabel;
	}

	public ValidationTextBox getMontoTB() {
		return montoTB;
	}

	public void setMontoTB(ValidationTextBox montoTB) {
		this.montoTB = montoTB;
	}

	public ValidationTextBox getPorcentajeTB() {
		return porcentajeTB;
	}

	public void setPorcentajeTB(ValidationTextBox porcentajeTB) {
		this.porcentajeTB = porcentajeTB;
	}

	public Label getPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(Label precioLista) {
		this.precioLista = precioLista;
	}

	public Label getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Label precioVenta) {
		this.precioVenta = precioVenta;
	}
	
	public InlineLabel getPrecioListaLabel() {
		return precioListaLabel;
	}

	public void setPrecioListaLabel(InlineLabel precioListaLabel) {
		this.precioListaLabel = precioListaLabel;
	}

	public InlineLabel getPrecioVentaLabel() {
		return precioVentaLabel;
	}
	
	public void setPrecioVentaLabel(InlineLabel precioVentaLabel) {
		this.precioVentaLabel = precioVentaLabel;
	}

}
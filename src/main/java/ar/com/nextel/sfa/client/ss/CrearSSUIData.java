package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextArea;

public class CrearSSUIData extends UIData {

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

	private SolicitudServicioDto solicitudServicio;

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

	public void setSolicitud(SolicitudServicioDto solicitud) {
		NumberFormat formatter = NumberFormat.getDecimalFormat();
		solicitudServicio = solicitud;
		nss.setText(solicitud.getNumero());
		nflota.setText(solicitud.getNumeroFlota());
		// origen.setSelectedItem();
		entrega.setSelectedItem(solicitud.getDomicilioEnvio());
		facturacion.setSelectedItem(solicitud.getDomicilioFacturacion());
		aclaracion.setText(solicitud.getAclaracionEntrega());
		// credFidelDisponibleText;
		// credFidelVencimientoText;
		double credFidelizacionValue = solicitud.getMontoCreditoFideliacion() != null ? solicitud
				.getMontoCreditoFideliacion() : 0;
		credFidelizacion.setText(formatter.format(credFidelizacionValue));
		double pataconexValue = solicitud.getPataconex() != null ? solicitud
				.getPataconex() : 0;
		pataconex.setText(formatter.format(pataconexValue));
		firmarss.setChecked(solicitud.getFirmar());
		// anticipo.setSelectedItem(solicitud.get);
		observaciones.setText(solicitud.getObservaciones());
		// precioListaText;
		// desvioText;
		// credFidelText;
		// pataconexText;
		// precioVentaText;
	}
}

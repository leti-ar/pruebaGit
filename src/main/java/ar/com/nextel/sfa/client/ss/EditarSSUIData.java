package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.dto.DomicilioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextArea;

public class EditarSSUIData extends UIData {

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
	private NumberFormat decFormatter = NumberFormat.getDecimalFormat();
	private NumberFormat currFormatter = NumberFormat.getCurrencyFormat();

	private SolicitudServicioDto solicitudServicio;

	public EditarSSUIData() {
		nss = new RegexTextBox(RegularExpressionConstants.getCantCaracteres(10));
		nflota = new RegexTextBox(RegularExpressionConstants.getCantCaracteres(5));
		origen = new ListBox();
		entrega = new ListBox();
		entrega.setWidth("480px");
		facturacion = new ListBox();
		facturacion.setWidth("480px");
		aclaracion = new TextArea();
		aclaracion.setWidth("480px");
		aclaracion.setHeight("35px");
		credFidelizacion = new RegexTextBox(RegularExpressionConstants.importe);
		pataconex = new RegexTextBox(RegularExpressionConstants.importe);
		firmarss = new CheckBox();
		anticipo = new ListBox();
		observaciones = new TextArea();
		observaciones.setWidth("480px");
		observaciones.setHeight("35px");

		credFidelDisponibleText = new InlineHTML("$ 0.00");
		credFidelVencimientoText = new InlineHTML("");
		precioListaText = new InlineHTML("$ 0.00");
		desvioText = new InlineHTML("$ 0.00");
		credFidelText = new InlineHTML("$ 0.00");
		pataconexText = new InlineHTML("$ 0.00");
		precioVentaText = new InlineHTML("$ 0.00");

		credFidelDisponibleText.addStyleName("normalText");
		credFidelVencimientoText.addStyleName("normalText");
		precioListaText.addStyleName("normalText");
		desvioText.addStyleName("normalText");
		credFidelText.addStyleName("normalText");
		pataconexText.addStyleName("normalText");
		precioVentaText.addStyleName("normalText");
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

		solicitudServicio = solicitud;
		nss.setText(solicitud.getNumero());
		nflota.setText(solicitud.getNumeroFlota());
		// origen.setSelectedItem();
		// entrega.clear();
		// facturacion.clear();
		// entrega.addAllItems(solicitud.getCuenta().getPersona().getDomicilios);
		// facturacion.addAllItems(solicitud.getCuenta().getPersona().getDomicilios);
		entrega.setSelectedItem(solicitud.getDomicilioEnvio());
		facturacion.setSelectedItem(solicitud.getDomicilioFacturacion());
		aclaracion.setText(solicitud.getAclaracionEntrega());
		credFidelDisponibleText.setHTML(solicitud.getMontoDisponible() != null ? currFormatter
				.format(solicitud.getMontoDisponible()) : "0");
		credFidelVencimientoText.setHTML(solicitud.getFechaVencimientoCreditoFidelizacion());
		double credFidelizacionValue = solicitud.getMontoCreditoFidelizacion() != null ? solicitud
				.getMontoCreditoFidelizacion() : 0;
		credFidelizacion.setText(decFormatter.format(credFidelizacionValue));
		double pataconexValue = solicitud.getPataconex() != null ? solicitud.getPataconex() : 0;
		pataconex.setText(decFormatter.format(pataconexValue));
		firmarss.setChecked(solicitud.getFirmar());
		// anticipo.setSelectedItem(solicitud.get);
		observaciones.setText(solicitud.getObservaciones());
		// precioListaText;
		// desvioText;

		recarcularValores();
		// precioVentaText;
	}

	public SolicitudServicioDto getSolicitudServicio() {
		solicitudServicio.setNumero(nss.getText());
		solicitudServicio.setNumeroFlota(nflota.getText());
		solicitudServicio.setDomicilioEnvio((DomicilioDto) entrega.getSelectedItem());
		solicitudServicio.setDomicilioFacturacion((DomicilioDto) facturacion.getSelectedItem());
		solicitudServicio.setAclaracionEntrega(aclaracion.getText());
		if (!"".equals(credFidelizacion.getText().trim())) {
			solicitudServicio.setMontoCreditoFidelizacion(Double.parseDouble(credFidelizacion.getText()));
		}
		if (!"".equals(pataconex.getText().trim())) {
			solicitudServicio.setPataconex(Double.parseDouble(pataconex.getText()));
		}
		solicitudServicio.setTipoAnticipo((TipoAnticipoDto) anticipo.getSelectedItem());
		solicitudServicio.setAclaracionEntrega(aclaracion.getText());
		solicitudServicio.setFirmar(firmarss.isChecked());
		solicitudServicio.setObservaciones(observaciones.getText());
		return solicitudServicio;
	}

	public List<String> validarCompletitud() {
		recarcularValores();
		GwtValidator validator = new GwtValidator();
		validator.addTarget(nss).required("Debe ingresar un Nº de Solicitud").maxLength(10,
				"El Nº de solicitud debe tener menos de 10 dígitos");
		validator.addTarget(entrega).required("Debe ingresar un domicilio de entrega");
		validator.addTarget(facturacion).required("Debe ingresar un domicilio de facturación");
		if (solicitudServicio.getMontoDisponible() != null)
			validator
					.addTarget(credFidelizacion)
					.greater(
							"La Cantidad de crédito de fidelización a utilizar no puede exceder el máximo disponible",
							solicitudServicio.getMontoDisponible());
		// validator.addTarget(pataconex).greater("La cantidad de pataconex ingresada excede el Precio de Venta Total. Por favor modifique el monto",
		// )
		validator.fillResult();
		return validator.getErrors();
	}

	public void recarcularValores() {
		precioListaText.setHTML("$ 0.00");
		desvioText.setHTML("$ 0.00");
		precioVentaText.setHTML("$ 0.00");
		if (solicitudServicio.getMontoCreditoFidelizacion() != null) {
			credFidelText.setHTML(currFormatter.format(solicitudServicio.getMontoCreditoFidelizacion()));
		} else {
			credFidelText.setHTML("$ 0.00");
		}
		if (solicitudServicio.getPataconex() != null) {
			pataconexText.setHTML(currFormatter.format(solicitudServicio.getPataconex()));
		} else {
			pataconexText.setHTML("$ 0.00");
		}
	}
}

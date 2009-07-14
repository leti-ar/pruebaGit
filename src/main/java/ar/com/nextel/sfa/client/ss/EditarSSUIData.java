package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoTipoDomicilioDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioGeneracionDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class EditarSSUIData extends UIData implements ChangeListener, ClickListener {

	private RegexTextBox nss;
	private RegexTextBox nflota;
	private ListBox origen;
	private ListBox entrega;
	private ListBox facturacion;
	private TextArea aclaracion;
	private RegexTextBox email;
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
	private DateTimeFormat dateTimeFormat = DateTimeFormat.getMediumDateFormat();
	private List<List<ServicioAdicionalLineaSolicitudServicioDto>> serviciosAdicionales;
	private boolean saved = true;
	private long lastFakeId = -1;

	private static final String FORMA_CONTRATACION_ALQUILER = "Alquiler";

	private SolicitudServicioDto solicitudServicio;

	public EditarSSUIData() {
		serviciosAdicionales = new ArrayList();

		fields.add(nss = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(10)));
		fields.add(nflota = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(5)));
		fields.add(origen = new ListBox());
		fields.add(entrega = new ListBox());
		entrega.setWidth("480px");
		fields.add(facturacion = new ListBox());
		facturacion.setWidth("480px");
		fields.add(aclaracion = new TextArea());
		aclaracion.setWidth("480px");
		aclaracion.setHeight("35px");
		fields.add(email = new RegexTextBox(RegularExpressionConstants.lazyEmail));
		email.setWidth("480px");
		fields.add(credFidelizacion = new RegexTextBox(RegularExpressionConstants.importe));
		fields.add(pataconex = new RegexTextBox(RegularExpressionConstants.importe));
		fields.add(firmarss = new CheckBox());
		fields.add(anticipo = new ListBox());
		fields.add(observaciones = new TextArea());
		observaciones.setWidth("480px");
		observaciones.setHeight("35px");

		// Change listener
		for (Widget field : fields) {
			if (field instanceof SourcesChangeEvents) {
				((SourcesChangeEvents) field).addChangeListener(this);
			} else if (field instanceof SourcesClickEvents) {
				((SourcesClickEvents) field).addClickListener(this);

			}
		}

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

	public void onChange(Widget sender) {
		saved = false;
	}

	public void onClick(Widget sender) {
		saved = false;
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

	public RegexTextBox getEmail() {
		return email;
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
		saved = true;
		lastFakeId = -1;
		serviciosAdicionales.clear();
		for (int i = 0; i < solicitud.getLineas().size(); i++) {
			serviciosAdicionales.add(new ArrayList());
		}
		solicitudServicio = solicitud;
		nss.setText(solicitud.getNumero());
		nflota.setText(solicitud.getNumeroFlota());
		entrega.clear();
		facturacion.clear();
		refreshDomiciliosListBox();
		entrega.selectByValue("" + solicitud.getIdDomicilioEnvio());
		facturacion.selectByValue("" + solicitud.getIdDomicilioFacturacion());
		aclaracion.setText(solicitud.getAclaracionEntrega());
		credFidelDisponibleText.setHTML(solicitud.getMontoDisponible() != null ? currFormatter
				.format(solicitud.getMontoDisponible()) : "0");
		if (solicitud.getFechaVencimientoCreditoFidelizacion() != null) {
			credFidelVencimientoText.setHTML(dateTimeFormat.format(solicitud
					.getFechaVencimientoCreditoFidelizacion()));
		} else {
			credFidelVencimientoText.setHTML("");
		}
		double credFidelizacionValue = solicitud.getMontoCreditoFidelizacion() != null ? solicitud
				.getMontoCreditoFidelizacion() : 0;
		credFidelizacion.setText(decFormatter.format(credFidelizacionValue));
		if (credFidelizacionValue > 0) {
			credFidelizacion.setEnabled(true);
		} else {
			credFidelizacion.setEnabled(false);
		}
		double pataconexValue = solicitud.getPataconex() != null ? solicitud.getPataconex() : 0;
		pataconex.setText(decFormatter.format(pataconexValue));
		firmarss.setChecked(solicitud.getFirmar());
		observaciones.setText(solicitud.getObservaciones());

		if (anticipo.getItemCount() != 0) {
			origen.setSelectedItem(solicitud.getOrigen());
			anticipo.setSelectedItem(solicitud.getTipoAnticipo());
		} else {
			deferredLoad();
		}
		recarcularValores();
	}

	private void deferredLoad() {
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (anticipo.getItemCount() == 0) {
					return true;
				}
				origen.setSelectedItem(solicitudServicio.getOrigen());
				anticipo.setSelectedItem(solicitudServicio.getTipoAnticipo());
				return false;
			}
		});
	}

	public Long getCuentaId() {
		return solicitudServicio.getCuenta().getId();
	}

	public SolicitudServicioDto getSolicitudServicio() {
		solicitudServicio.setNumero(nss.getText());
		solicitudServicio.setNumeroFlota(nflota.getText());
		solicitudServicio.setOrigen((OrigenSolicitudDto) origen.getSelectedItem());
		solicitudServicio.setIdDomicilioEnvio(entrega.getSelectedItemId() != null ? Long.valueOf(entrega
				.getSelectedItemId()) : null);
		solicitudServicio.setIdDomicilioFacturacion(facturacion.getSelectedItemId() != null ? Long
				.valueOf(facturacion.getSelectedItemId()) : null);
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
		String v1 = "\\{1\\}";
		validator.addTarget(nss).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Nº de Solicitud")).maxLength(10,
				Sfa.constant().ERR_NSS_LONG());
		validator.addTarget(facturacion).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Facturación"));
		if (!solicitudServicio.getGrupoSolicitud().isCDW()) {
			validator.addTarget(entrega).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Entrega"));
		} else {
			validator.addTarget(email).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Email"));
		}
		if (solicitudServicio.getMontoDisponible() != null)
			validator.addTarget(credFidelizacion).smallerOrEqual(Sfa.constant().ERR_FIDELIZACION(),
					solicitudServicio.getMontoDisponible());
		validator.addTarget(pataconex).smallerOrEqual(Sfa.constant().ERR_PATACONEX(),
				solicitudServicio.getMontoDisponible());

		if (solicitudServicio.getLineas().isEmpty()) {
			validator.addError(Sfa.constant().ERR_REQUIRED_LINEA());
		}

		for (LineaSolicitudServicioDto linea : solicitudServicio.getLineas()) {
			// Pregunta si es de alquiler y busca si tiene uno seleccionado
			if (linea.getTipoSolicitud().getTipoSolicitudBase().getFormaContratacion().equals(
					FORMA_CONTRATACION_ALQUILER)) {
				boolean hasAlquiler = false;
				for (ServicioAdicionalLineaSolicitudServicioDto servicioAdicional : linea
						.getServiciosAdicionales()) {
					if (servicioAdicional.isEsAlquiler() && servicioAdicional.isChecked()) {
						hasAlquiler = true;
						break;
					}
				}
				if (!hasAlquiler) {
					validator.addError(Sfa.constant().ERR_FALTA_ALQUILER().replaceAll(v1, linea.getAlias()));
				}
			}
		}
		// Para el cierre
		SolicitudServicioGeneracionDto ssg = solicitudServicio.getSolicitudServicioGeneracion();
		if (ssg != null) {
			if (ssg.isEmailNuevoChecked()) {
				validator.addTarget(ssg.getEmailNuevo()).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Nuevo Email")).regEx(
						Sfa.constant().ERR_FORMATO().replaceAll(v1, "Nuevo Email"),
						RegularExpressionConstants.email);
			}
		}
		validator.fillResult();
		return validator.getErrors();
	}

	public void recarcularValores() {
		double precioLista = 0;
		double precioVenta = 0;
		for (LineaSolicitudServicioDto linea : solicitudServicio.getLineas()) {
			linea.refreshPrecioServiciosAdicionales();
			precioLista = precioLista + linea.getPrecioLista() + linea.getPrecioListaPlan()
					+ linea.getPrecioServiciosAdicionalesLista() + linea.getPrecioGarantiaLista();
			precioVenta = precioVenta + linea.getPrecioVenta() + linea.getPrecioVentaPlan()
					+ linea.getPrecioServiciosAdicionalesVenta() + linea.getPrecioGarantiaVenta();
		}

		precioListaText.setHTML(currFormatter.format(precioLista));
		desvioText.setHTML(currFormatter.format(precioLista - precioVenta));
		precioVentaText.setHTML(currFormatter.format(precioVenta));
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

	public int addLineaSolicitudServicio(LineaSolicitudServicioDto linea) {
		saved = false;
		Long index = linea.getNumeradorLinea();
		if (index == null) {
			linea.setNumeradorLinea(Long.valueOf(solicitudServicio.getLineas().size()));
			solicitudServicio.getLineas().add(linea);
			serviciosAdicionales.add(new ArrayList());
		} else {
			solicitudServicio.getLineas().remove(index.intValue());
			solicitudServicio.getLineas().add(index.intValue(), linea);
			linea.getServiciosAdicionales().clear();
			serviciosAdicionales.get(index.intValue()).clear();
		}
		return linea.getNumeradorLinea().intValue();
	}

	/** Elimina la linea y renumera las restantes */
	public int removeLineaSolicitudServicio(int index) {
		saved = false;
		solicitudServicio.getLineas().remove(index);
		for (; index < solicitudServicio.getLineas().size(); index++) {
			solicitudServicio.getLineas().get(index).setNumeradorLinea(Long.valueOf(index));
		}
		return index;
	}

	public List<LineaSolicitudServicioDto> getLineasSolicitudServicio() {
		return solicitudServicio.getLineas();
	}

	public List<List<ServicioAdicionalLineaSolicitudServicioDto>> getServiciosAdicionales() {
		return serviciosAdicionales;
	}

	/**
	 * Carga la lista de todos los servicios adicionales para una LineaSolicitudServicioDto. Tambien agrega a
	 * la linea los servicios adicionales obligatorios
	 */
	public void loadServiciosAdicionales(int indexLinea, List<ServicioAdicionalLineaSolicitudServicioDto> list) {
		serviciosAdicionales.get(indexLinea).addAll(list);
		List serviciosAdGuardados = getLineasSolicitudServicio().get(indexLinea).getServiciosAdicionales();
		for (ServicioAdicionalLineaSolicitudServicioDto servicioAd : list) {
			if (servicioAd.isChecked() && !serviciosAdGuardados.contains(servicioAd)) {
				serviciosAdGuardados.add(servicioAd);
			}
		}
	}

	public void getModificarValorServicioAdicional(int indexLinea, int indexSA, double valor) {
		saved = false;
		ServicioAdicionalLineaSolicitudServicioDto servicio = serviciosAdicionales.get(indexLinea).get(
				indexSA);
		List<ServicioAdicionalLineaSolicitudServicioDto> serviciosAdGuardados = getLineasSolicitudServicio()
				.get(indexLinea).getServiciosAdicionales();
		int indexSAGuardado = serviciosAdGuardados.indexOf(servicio);
		if (indexSAGuardado >= 0) {
			serviciosAdGuardados.get(indexSAGuardado).setPrecioVenta(valor);
		} else {
			servicio.setPrecioVenta(valor);
			getLineasSolicitudServicio().get(indexLinea).getServiciosAdicionales().add(servicio);
		}
	}

	public String getNombreMovil() {
		String nombreMovil = "";
		boolean encontrado = false;
		for (int i = 0; i < 1000; i++) {
			nombreMovil = "Movil" + (i + 1);
			encontrado = false;
			for (LineaSolicitudServicioDto linea : getLineasSolicitudServicio()) {
				if (nombreMovil.equals(linea.getAlias())) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado) {
				return nombreMovil;
			}
		}
		return nombreMovil;
	}

	public CuentaDto getCuenta() {
		return solicitudServicio.getCuenta();
	}

	public void refreshDomiciliosListBox() {
		entrega.clear();
		facturacion.clear();
		List<DomiciliosCuentaDto> domicilios = solicitudServicio.getCuenta().getPersona().getDomicilios();
		for (DomiciliosCuentaDto domicilio : domicilios) {
			if (domicilio.getIdEntrega().equals(EstadoTipoDomicilioDto.PRINCIPAL.getId())) {
				entrega.addItem(domicilio);
				entrega.setSelectedItem(domicilio);
			} else if (domicilio.getIdEntrega().equals(EstadoTipoDomicilioDto.SI.getId())) {
				entrega.addItem(domicilio);
			}
			if (domicilio.getIdFacturacion().equals(EstadoTipoDomicilioDto.PRINCIPAL.getId())) {
				facturacion.addItem(domicilio);
				facturacion.setSelectedItem(domicilio);
			} else if (domicilio.getIdFacturacion().equals(EstadoTipoDomicilioDto.SI.getId())) {
				facturacion.addItem(domicilio);
			}
		}
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public boolean isSaved() {
		return saved;
	}

	public void addDomicilio(DomiciliosCuentaDto domicilio) {
		PersonaDto persona = solicitudServicio.getCuenta().getPersona();
		if (domicilio.getId() == null) {
			domicilio.setId(lastFakeId--);
			persona.getDomicilios().add(domicilio);
		} else {
			int index = persona.getDomicilios().indexOf(domicilio);
			persona.getDomicilios().remove(index);
			persona.getDomicilios().add(index, domicilio);
		}
	}

	public GrupoSolicitudDto getGrupoSolicitud() {
		return solicitudServicio != null ? solicitudServicio.getGrupoSolicitud() : null;
	}

	public SolicitudServicioGeneracionDto getSolicitudServicioGeneracion() {
		return solicitudServicio.getSolicitudServicioGeneracion();
	}

	public void setSolicitudServicioGeneracion(SolicitudServicioGeneracionDto solicitudServicioGeneracion) {
		solicitudServicio.setSolicitudServicioGeneracion(solicitudServicioGeneracion);
	}

}

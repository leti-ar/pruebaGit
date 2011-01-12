package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.cuenta.CuentaDomiciliosForm;
import ar.com.nextel.sfa.client.dto.CuentaSSDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoTipoDomicilioDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioGeneracionDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;
import ar.com.nextel.sfa.client.dto.TipoDescuentoDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudBaseDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class EditarSSUIData extends UIData implements ChangeListener, ClickHandler {

	private RegexTextBox nss;
	private RegexTextBox nflota;
	private ListBox origen;
	private ListBox entrega;
	private ListBox facturacion;
	private TextArea aclaracion;
	//MGR - #1027
	private RegexTextBox ordenCompra;
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
	/** Contiene las listas de servicios adicionales de cada LineaSolicitudServicio */
	private List<List<ServicioAdicionalLineaSolicitudServicioDto>> serviciosAdicionales;
	private boolean saved = true;
	private long lastFakeId = -1;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private EditarSSUIController controller;

	private static final String V1 = "\\{1\\}";
	private static final String FORMA_CONTRATACION_ALQUILER = "Alquiler";
	private static final int MAX_LENGHT_OBSERVACIONES = 150;
	private static final int MAX_LENGHT_ACLARACION = 200;
	//MGR - #1027
	private static final int MAX_LENGHT_ORDEN_COMPRA = 150;

	private SolicitudServicioDto solicitudServicio;

	private ListBox descuentoTotal;
	private Button tildeVerde;
	
	public EditarSSUIData(EditarSSUIController controller) {
		this.controller = controller;
		serviciosAdicionales = new ArrayList();

		fields.add(nss = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(10), true));
		fields.add(nflota = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(5)));
		fields.add(origen = new ListBox(""));
		fields.add(entrega = new ListBox());
		fields.add(descuentoTotal = new ListBox());
		tildeVerde = new Button();
		tildeVerde.addStyleName("icon-tildeVerde");
		fields.add(tildeVerde);
		//MGR - #1027
		fields.add(ordenCompra = new RegexTextBox(RegularExpressionConstants.getCantCaracteres(150)));

		entrega.setWidth("480px");
		fields.add(facturacion = new ListBox());
		facturacion.setWidth("480px");
		fields.add(aclaracion = new TextArea());
		aclaracion.setWidth("480px");
		aclaracion.setHeight("35px");
		fields.add(email = new RegexTextBox(RegularExpressionConstants.lazyEmail));
		email.setWidth("480px");
		fields.add(credFidelizacion = new RegexTextBox(RegularExpressionConstants.decimal));
		fields.add(pataconex = new RegexTextBox(RegularExpressionConstants.decimal));
		fields.add(firmarss = new CheckBox());
		fields.add(anticipo = new ListBox());
		fields.add(observaciones = new TextArea());
		observaciones.setWidth("480px");
		observaciones.setHeight("35px");

		observaciones.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				showMaxLengthTextAreaError(observaciones, MAX_LENGHT_OBSERVACIONES);
			}
		});

		aclaracion.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				showMaxLengthTextAreaError(aclaracion, MAX_LENGHT_ACLARACION);
			}
		});
		
		// Change listener para detectar cambios
		for (Widget field : fields) {
			if (field instanceof SourcesChangeEvents) {
				((SourcesChangeEvents) field).addChangeListener(this);
			} else if (field instanceof HasClickHandlers) {
				((HasClickHandlers) field).addClickHandler(this);
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

	private void showMaxLengthTextAreaError(TextArea textArea, int maxLength) {
		if (textArea.getText().length() > maxLength) {
			ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
			ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
			textArea.setText(textArea.getText().substring(0, maxLength));
		}
	}

	public void onChange(Widget sender) {
		saved = false;
		boolean isEmpty = false;
		if (sender == pataconex) {
			isEmpty = "".equals(pataconex.getText().trim());
			try {
				solicitudServicio
						.setPataconex(!isEmpty ? decFormatter.parse(pataconex.getText().trim()) : 0d);
			} catch (NumberFormatException e) {
				pataconex.setText("0");
				solicitudServicio.setPataconex(0d);
			}
			recarcularValores();
		} else if (sender == credFidelizacion) {
			isEmpty = "".equals(credFidelizacion.getText().trim());
			try {
				solicitudServicio.setMontoCreditoFidelizacion(!isEmpty ? decFormatter.parse(credFidelizacion
						.getText().trim()) : 0d);
			} catch (NumberFormatException e) {
				credFidelizacion.setText("0");
				solicitudServicio.setMontoCreditoFidelizacion(0d);
			}
			recarcularValores();
		}
	}

	public void onClick(ClickEvent clickEvent) {
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

	//MGR - #1027
	public RegexTextBox getOrdenCompra() {
		return ordenCompra;
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

	public ListBox getDescuentoTotal() {
		return descuentoTotal;
	}
	
	public Button getTildeVerde() {
		return tildeVerde;
	}
	
	public void setDescuentoTotal(ListBox descuentoTotal) {
		this.descuentoTotal = descuentoTotal;
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
		
		//MGR - #1152
		boolean esProspect =RegularExpressionConstants.isVancuc(solicitud.getCuenta().getCodigoVantive());
		
		StringBuffer linea = new StringBuffer();
		linea.append("###- Verificando si deshabilita el campo triptico de la SS.");
		linea.append("###-El vendedor logeado es: " + ClientContext.getInstance().getVendedor().getId() +
				" - Apellido y Nombre: " + ClientContext.getInstance().getVendedor().getApellido() + " " +
				ClientContext.getInstance().getVendedor().getNombre());
		linea.append("###-El numero de la solicitud es: " + solicitud.getNumero());
		linea.append("###-El permiso NRO_SS_EDITABLE devolvio: " + ClientContext.getInstance().
				checkPermiso(PermisosEnum.NRO_SS_EDITABLE.getValue()));
		linea.append("###-El codigo vantive de la cuenta de la solicitud es: " + 
				solicitud.getCuenta().getCodigoVantive());
		linea.append("###-Es prospect devolvio: " + esProspect);
		SolicitudRpcService.Util.getInstance().loginServer(linea.toString());
		
		//MGR - #1026
		if(!ClientContext.getInstance().
				checkPermiso(PermisosEnum.NRO_SS_EDITABLE.getValue())){
			//MGR - #1167
			if(esProspect){
				nss.setEnabled(true);
			}
			else{
				nss.setEnabled(false);
			}
			
		}
		
		nflota.setEnabled(solicitud.getCuenta().getIdVantive() == null);
		nflota.setReadOnly(!nflota.isEnabled());
		nflota.setText(solicitud.getNumeroFlota());
		//MGR - #1027
		ordenCompra.setText(solicitud.getOrdenCompra());
		
		entrega.clear();
		facturacion.clear();
		refreshDomiciliosListBox();
		entrega.selectByValue("" + solicitud.getIdDomicilioEnvio());
		facturacion.selectByValue("" + solicitud.getIdDomicilioFacturacion());
		aclaracion.setText(solicitud.getAclaracionEntrega());
		credFidelVencimientoText.setHTML("");
		if (solicitud.getFechaVencimientoCreditoFidelizacion() != null) {
			credFidelVencimientoText.setHTML(dateTimeFormat.format(solicitud
					.getFechaVencimientoCreditoFidelizacion()));
		}
		// Si posee monto diponible de credito de fidelizacion habilito el textbox y cargo el monto actual. En
		// caso contrario deshabilito el textbox y seteo todo en cero.
		double montoDisponibleValue = solicitud.getMontoDisponible() != null ? solicitud.getMontoDisponible()
				: 0;
		double credFidelizacionValue = 0;
		boolean fechaCredFidelizacionValida = solicitud.getFechaVencimientoCreditoFidelizacion() == null
				|| solicitud.getFechaVencimientoCreditoFidelizacion().after(new Date());
		if (montoDisponibleValue > 0 && fechaCredFidelizacionValida) {
			credFidelizacion.setEnabled(true);
			credFidelizacion.setReadOnly(false);
			credFidelizacionValue = solicitud.getMontoCreditoFidelizacion() != null ? solicitud
					.getMontoCreditoFidelizacion() : 0;
		} else {
			credFidelizacion.setEnabled(false);
			credFidelizacion.setReadOnly(true);
		}
		credFidelDisponibleText.setHTML(currFormatter.format(montoDisponibleValue));
		credFidelizacion.setText(decFormatter.format(credFidelizacionValue));
		double pataconexValue = solicitud.getPataconex() != null ? solicitud.getPataconex() : 0;
		pataconex.setText(decFormatter.format(pataconexValue));
		firmarss.setValue(solicitud.getFirmar());
		observaciones.setText(solicitud.getObservaciones());
		if (solicitud.getGrupoSolicitud().isCDW()) {
			email.setText(solicitud.getEmail());
		}
		if (anticipo.getItemCount() != 0) {
			origen.setSelectedItem(solicitud.getOrigen());
			anticipo.setSelectedItem(solicitud.getTipoAnticipo());
		} else {
			deferredLoad();
		}
//		comprobarDescuentoTotal();		
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
		//MGR - #1027
		solicitudServicio.setOrdenCompra(ordenCompra.getText());
		
		solicitudServicio.setIdDomicilioEnvio(entrega.getSelectedItemId() != null ? Long.valueOf(entrega
				.getSelectedItemId()) : null);
		solicitudServicio.setIdDomicilioFacturacion(facturacion.getSelectedItemId() != null ? Long
				.valueOf(facturacion.getSelectedItemId()) : null);
		solicitudServicio.setAclaracionEntrega(aclaracion.getText());
		if (!"".equals(credFidelizacion.getText().trim())) {
			solicitudServicio.setMontoCreditoFidelizacion(decFormatter.parse(credFidelizacion.getText()));
		} else {
			solicitudServicio.setMontoCreditoFidelizacion(0d);
		}
		if (!"".equals(pataconex.getText().trim())) {
			solicitudServicio.setPataconex(decFormatter.parse(pataconex.getText()));
		} else {
			solicitudServicio.setPataconex(0d);
		}
		solicitudServicio.setTipoAnticipo((TipoAnticipoDto) anticipo.getSelectedItem());
		solicitudServicio.setAclaracionEntrega(aclaracion.getText());
		solicitudServicio.setFirmar(firmarss.getValue());
		solicitudServicio.setObservaciones(observaciones.getText());
		if (solicitudServicio.getGrupoSolicitud().isCDW()) {
			solicitudServicio.setEmail(email.getText());
		}
		return solicitudServicio;
	}

	public List<String> validarCompletitud() {
		return CuentaDomiciliosForm.validarCompletitud(solicitudServicio.getCuenta().getPersona()
				.getDomicilios());
	}

	public List<String> validarParaGuardar() {
		GwtValidator validator = new GwtValidator();
		for (LineaSolicitudServicioDto linea : solicitudServicio.getLineas()) {
			validarAlquileresDeLineaSS(validator, linea);
			validarServicioMDS(validator, linea);
			validarCargoActivacion(validator, linea);
		}
		solicitudServicio.refreshPreciosTotales();
		validator.addTarget(origen).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().origen()));
		String pataconexConPunto = "0";
		try {
			pataconexConPunto = "" + decFormatter.parse(pataconex.getText());
		} catch (NumberFormatException e) {
		}
		validator.addTarget(pataconexConPunto).smallerOrEqual(
				Sfa.constant().ERR_PATACONEX() + " ( "
						+ currencyFormat.format(solicitudServicio.getPrecioItemTotal()) + " )",
				solicitudServicio.getPrecioItemTotal());
		if (solicitudServicio.getMontoDisponible() != null) {
			String credFidelizacionConPunto = "" + decFormatter.parse(credFidelizacion.getText());
			validator.addTarget(credFidelizacionConPunto).smallerOrEqual(Sfa.constant().ERR_FIDELIZACION(),
					solicitudServicio.getMontoDisponible());
		}
		
		//MGR - #1027
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
			
		if(solicitudServicio.getGrupoSolicitud() != null && instancias != null &&
				instancias.get(GrupoSolicitudDto.ID_FAC_MENSUAL).equals(solicitudServicio.getGrupoSolicitud().getId())){
			validator.addTarget(ordenCompra).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().ordenCompra()));
		}
		
		validator.fillResult();
		return validator.getErrors();
	}

	/**
	 * @param generacionCierreDefinitivo
	 *            true si debe validar para la generacion o cierre definitiva de la solicitud, que seria el
	 *            aceptar de la pantalla que pide los mails
	 * @return Lista de errores
	 */
	public List<String> validarParaCerrarGenerar(boolean generacionCierreDefinitivo) {
		recarcularValores();
		GwtValidator validator = new GwtValidator();
		validator.addTarget(nss).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Nº de Solicitud")).maxLength(10,
				Sfa.constant().ERR_NSS_LONG());
		GrupoSolicitudDto grupoSS = solicitudServicio.getGrupoSolicitud();

		// Validacion rango NSS con y sin PIN
		Long numeroSS = "".equals(nss.getText()) ? null : Long.valueOf(nss.getText());
		if (numeroSS != null && grupoSS.getRangoMinimoSinPin() != null
				&& grupoSS.getRangoMaximoSinPin() != null && grupoSS.getRangoMinimoConPin() != null
				&& grupoSS.getRangoMaximoConPin() != null) {
			boolean enRangoSinPin = numeroSS >= grupoSS.getRangoMinimoSinPin()
					&& numeroSS <= grupoSS.getRangoMaximoSinPin();
			boolean enRangoConPin = numeroSS >= grupoSS.getRangoMinimoConPin()
					&& numeroSS <= grupoSS.getRangoMaximoConPin();
			if (!enRangoSinPin && !enRangoConPin) {
				validator.addError(Sfa.constant().ERR_NNS_RANGO());
			}
		}
		validator.addTarget(origen).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().origen()));
		validator.addTarget(facturacion).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Facturación"));
		if (!solicitudServicio.getGrupoSolicitud().isCDW()) {
			validator.addTarget(entrega).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Entrega"));
		} else {
			validator.addTarget(email).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Email"));
		}
		if (solicitudServicio.getMontoDisponible() != null) {
			String credFidelizacionConPunto = "" + decFormatter.parse(credFidelizacion.getText());
			validator.addTarget(credFidelizacionConPunto).smallerOrEqual(Sfa.constant().ERR_FIDELIZACION(),
					solicitudServicio.getMontoDisponible());
		}
		solicitudServicio.refreshPreciosTotales();
		String pataconexConPunto = "" + decFormatter.parse(pataconex.getText());
		validator.addTarget(pataconexConPunto).smallerOrEqual(Sfa.constant().ERR_PATACONEX(),
				solicitudServicio.getPrecioItemTotal()).smallerOrEqual(
				Sfa.constant().ERR_PATACONEX() + " ( "
						+ currencyFormat.format(solicitudServicio.getPrecioItemTotal()) + " )",
				solicitudServicio.getPrecioItemTotal());

		if (solicitudServicio.getLineas().isEmpty()) {
			validator.addError(Sfa.constant().ERR_REQUIRED_LINEA());
		}

		for (LineaSolicitudServicioDto linea : solicitudServicio.getLineas()) {
			validarAlquileresDeLineaSS(validator, linea);
			validarCargoActivacion(validator, linea);
		}

		// Para el cierre
		SolicitudServicioGeneracionDto ssg = solicitudServicio.getSolicitudServicioGeneracion();
		if (generacionCierreDefinitivo == true && ssg != null) {
			if (ssg.isEmailNuevoChecked()) {
				validator.addTarget(ssg.getEmailNuevo()).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Nuevo Email")).regEx(
						Sfa.constant().ERR_FORMATO().replaceAll(V1, "Nuevo Email"),
						RegularExpressionConstants.email);
			}
		}
		validator.fillResult();
		List<String> errores = validator.getErrors();
		errores.addAll(validarCompletitud());
		return errores;
	}

	private void validarAlquileresDeLineaSS(GwtValidator validator, LineaSolicitudServicioDto linea) {
		// Pregunta si es de alquiler y busca si tiene uno seleccionado
		if (linea.getTipoSolicitud().getTipoSolicitudBase().getFormaContratacion().equals(
				FORMA_CONTRATACION_ALQUILER)) {
			int alquileres = 0;
			for (ServicioAdicionalLineaSolicitudServicioDto servicioAdicional : linea
					.getServiciosAdicionales()) {
				if (servicioAdicional.isEsAlquiler() && servicioAdicional.isChecked()) {
					alquileres++;
				}
			}
			if (alquileres != 1) {
				validator.addError(Sfa.constant().ERR_FALTA_ALQUILER().replaceAll(V1, linea.getAlias()));
			}
		}
	}

	private void validarServicioMDS(GwtValidator validator, LineaSolicitudServicioDto linea) {
		// Pregunta si es de tipo MDS y busca si tiene un servicio MDS seleccionado
		if ((linea.getTipoSolicitud().getTipoSolicitudBase().getId()
				.equals(TipoSolicitudBaseDto.VENTA_EQUIPOS_NUEVOS_G4))
				|| (linea.getTipoSolicitud().getTipoSolicitudBase().getId()
						.equals(TipoSolicitudBaseDto.ALQUILER_EQUIPOS_NUEVOS_G4))
				|| (linea.getTipoSolicitud().getTipoSolicitudBase().getId()
						.equals(TipoSolicitudBaseDto.VENTA_EQUIPOS_USADOS_G4))
				|| (linea.getTipoSolicitud().getTipoSolicitudBase().getId()
						.equals(TipoSolicitudBaseDto.ALQUILER_EQUIPOS_USADOS_G4))) {
			int servicioMds = 0;
			for (ServicioAdicionalLineaSolicitudServicioDto servicioAdicional : linea
					.getServiciosAdicionales()) {
				if ((servicioAdicional.isEsWap() || servicioAdicional.isEsTethered())
						&& servicioAdicional.isChecked()) {
					servicioMds++;
				}
			}
			if (servicioMds != 1) {
				validator.addError(Sfa.constant().ERR_FALTA_MDS().replaceAll(V1, linea.getAlias()));
			}
		}
	}

	private void validarCargoActivacion(GwtValidator validator, LineaSolicitudServicioDto linea) {
		if (linea.getTipoSolicitud().getTipoSolicitudBase().getId().equals(TipoSolicitudBaseDto.ID_VENTA_CDW)) {
			boolean hasCargoActivacionCDW = false;
			for (ServicioAdicionalLineaSolicitudServicioDto servicioAdicional : linea
					.getServiciosAdicionales()) {
				if (servicioAdicional.isUnicaVez() && servicioAdicional.isChecked()) {
					hasCargoActivacionCDW = true;
					break;
				}
			}
			if (!hasCargoActivacionCDW) {
				validator.addError(Sfa.constant().ERR_FALTA_CARGO_ACTIVACION_CDW().replaceAll(V1,
						linea.getAlias()));
			}
		}
	}

	/** Recalcula y actualiza el valor de las etiquetas */
	public void recarcularValores() {
		solicitudServicio.refreshPreciosTotales();
		double precioLista = solicitudServicio.getPrecioListaTotal();
		double precioVenta = solicitudServicio.getPrecioVentaTotal();

		precioListaText.setHTML(currFormatter.format(precioLista));
		desvioText.setHTML(currFormatter.format(precioLista - precioVenta));
		double precioVentaTotal = solicitudServicio.getMontoCreditoFidelizacion() != null ? precioVenta
				- solicitudServicio.getMontoCreditoFidelizacion() : precioVenta;
		precioVentaText.setHTML(currFormatter.format(precioVentaTotal));
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
			// Los servicios adicionales de la linea los limpia en
			// ItemSolicitudUIData.getLineaSolicitudServicio()
			if (linea.getServiciosAdicionales().isEmpty()) {
				serviciosAdicionales.get(index.intValue()).clear();
			}
		}
		return linea.getNumeradorLinea().intValue();
	}

	/** Elimina la linea, renumera las restantes y borra los servicios adicionales asociados */
	public int removeLineaSolicitudServicio(int index) {
		saved = false;
		LineaSolicitudServicioDto linea = solicitudServicio.getLineas().get(index);
		String numeroReservado = linea.getNumeroReserva();
		boolean tieneNReserva = numeroReservado != null && numeroReservado.length() > 0;
		if (tieneNReserva) {
			controller.desreservarNumeroTelefonico(Long.parseLong(numeroReservado), linea.getId(),
					new DefaultWaitCallback() {
						public void success(Object result) {
						}
					});
		}
		solicitudServicio.getLineas().remove(index);
		serviciosAdicionales.remove(index);
		for (; index < solicitudServicio.getLineas().size(); index++) {
			solicitudServicio.getLineas().get(index).setNumeradorLinea(Long.valueOf(index));
		}
		return index;
	}

	public List<LineaSolicitudServicioDto> getLineasSolicitudServicio() {
		return solicitudServicio.getLineas();
	}

	/** @return Lista de listas de los servicios adicionales por cada linea de SS */
	public List<List<ServicioAdicionalLineaSolicitudServicioDto>> getServiciosAdicionales() {
		return serviciosAdicionales;
	}

	/**
	 * Carga la lista de todos los servicios adicionales para una LineaSolicitudServicioDto. Tambien agrega a
	 * la linea los servicios adicionales obligatorios
	 */
	public void loadServiciosAdicionales(int indexLinea, List<ServicioAdicionalLineaSolicitudServicioDto> list) {
		serviciosAdicionales.get(indexLinea).clear();
		serviciosAdicionales.get(indexLinea).addAll(list);
		mergeServiciosAdicionalesConLineaSolicitudServicio(indexLinea, list);
	}

	/** Agrega a la LineaSolicitudServicio los servicios adicionales que vienen por defecto */
	public void mergeServiciosAdicionalesConLineaSolicitudServicio(int indexLinea,
			List<ServicioAdicionalLineaSolicitudServicioDto> list) {
		List serviciosAGuardar = getLineasSolicitudServicio().get(indexLinea).getServiciosAdicionales();
		for (ServicioAdicionalLineaSolicitudServicioDto servicioAd : list) {
			if (servicioAd.isChecked() && !serviciosAGuardar.contains(servicioAd)) {
				serviciosAGuardar.add(servicioAd);
			}
		}
	}

	public void modificarValorServicioAdicional(int indexLinea, int indexSA, double valor) {
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

	public void modificarValorPlan(int indexLinea, double valor) {
		getLineasSolicitudServicio().get(indexLinea).setPrecioVentaPlan(valor);
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

	public CuentaSSDto getCuenta() {
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

	public boolean isCDW() {
		//MGR - #1050
		//return solicitudServicio.getGrupoSolicitud().getId().equals(GrupoSolicitudDto.ID_CDW);
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null){
			return solicitudServicio.getGrupoSolicitud().getId().equals(instancias.get(GrupoSolicitudDto.ID_CDW));
		}
		return false;
	}

	public boolean isMDS() {
		//MGR - #1050
		//return solicitudServicio.getGrupoSolicitud().getId().equals(GrupoSolicitudDto.ID_MDS);
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias != null){
			return solicitudServicio.getGrupoSolicitud().getId().equals(instancias.get(GrupoSolicitudDto.ID_MDS));
		}
		return false;
	}

	/** Indica si contiene lineas de solicitud con item BlackBerry */
	public boolean hasItemBB() {
		for (LineaSolicitudServicioDto linea : solicitudServicio.getLineas()) {
			ModeloDto modelo = linea.getModelo();
			if (modelo != null && modelo.isEsBlackberry()) {
				return true;
			}
		}
		return false;
	}

	public void desreservarNumerosNoGuardados() {
		for (LineaSolicitudServicioDto linea : solicitudServicio.getLineas()) {
			if (linea.getId() == null && linea.getNumeroReservaArea() != null
					&& linea.getNumeroReservaArea().length() > 0) {
				controller.desreservarNumeroTelefonico(Long.parseLong(linea.getNumeroReserva()), null,
						new DefaultWaitCallback() {
							public void success(Object result) {
							};
						});
			}
		}

	}

//	public void comprobarDescuentoTotal() {
//		List<LineaSolicitudServicioDto> lineas = solicitudServicio.getLineas();
//		descuentoTotal.clear();
//		descuentoTotal.setEnabled(true);
//		SolicitudRpcService.Util.getInstance().puedeAplicarDescuento(lineas, new DefaultWaitCallback<Boolean>() {
//			@Override
//			public void success(Boolean result) {
//				if (!result) {
//					descuentoTotal.setEnabled(false);
//				} else {
//						SolicitudRpcService.Util.getInstance().getInterseccionTiposDescuento(solicitudServicio.getLineas(),
//											new DefaultWaitCallback<List<TipoDescuentoDto>>() {
//						@Override
//						public void success(List<TipoDescuentoDto> result) {
//							for (Iterator<TipoDescuentoDto> iterator = result.iterator(); iterator.hasNext();) {
//								TipoDescuentoDto tipoDescuento = (TipoDescuentoDto) iterator.next();
//								descuentoTotal.addItem(tipoDescuento.getDescripcion());
//							}
//						}
//					});
//				}
//			}
//		});
//	}

//	public void deshabilitarDescuentoTotal() {
//		descuentoTotal.setEnabled(false);
//		tildeVerde.setEnabled(false);
//	}

	/**
	 * Elimino del listBox descuentoTotal aquellos tipos de descuento que el usuario haya elegido
	 * para cada linea de solicitud de servicio 
	 * @param descuentosSeleccionados
	 */
//	public void modificarDescuentoTotal(List<TipoDescuentoSeleccionado> descuentosSeleccionados) {
//		for (int i = 0; i < descuentoTotal.getItemCount(); i++) {
//			Iterator<TipoDescuentoSeleccionado> it = descuentosSeleccionados.iterator();
//			while (it.hasNext()) {
//				TipoDescuentoSeleccionado seleccionado = (TipoDescuentoSeleccionado) it.next();
//				if (seleccionado.getDescripcion().equals(descuentoTotal.getItemText(i))) {
//					descuentoTotal.removeItem(i);
//					break;
//				}
//			}
//		}
//	}
	
}

package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.cuenta.CuentaDatosForm;
import ar.com.nextel.sfa.client.cuenta.CuentaDomiciliosForm;
import ar.com.nextel.sfa.client.dto.ComentarioAnalistaDto;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.ControlDto;
import ar.com.nextel.sfa.client.dto.CuentaSSDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.EstadoTipoDomicilioDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalIncluidoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioGeneracionDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudBaseDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.event.EventBusUtil;
import ar.com.nextel.sfa.client.event.RefrescarPantallaSSEvent;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.nextel.sfa.client.util.FormUtils;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidationUtils;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditarSSUIData extends UIData implements ChangeListener, ClickHandler {

	private RegexTextBox nss;
	private RegexTextBox nflota;
	private InfocomInitializer infocom;
    private ListBox control;
    private Label estado;
	private ListBox origen;
	private ListBox origenTR;
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
	private CheckBox enviar = new CheckBox(" Enviar");
	private NumberFormat decFormatter = NumberFormat.getDecimalFormat();
	private NumberFormat currFormatter = NumberFormat.getCurrencyFormat();
	private DateTimeFormat dateTimeFormat = DateTimeFormat.getMediumDateFormat();
	/** Contiene las listas de servicios adicionales de cada LineaSolicitudServicio */
	private List<List<ServicioAdicionalLineaSolicitudServicioDto>> serviciosAdicionales;
	private boolean saved = false;
	private long lastFakeId = -1;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private EditarSSUIController controller;

	private static final String V1 = "\\{1\\}";
	private static final String FORMA_CONTRATACION_ALQUILER = "Alquiler";
	private static final int MAX_LENGHT_OBS_ACLARACION = 150;
	private static final int MAX_LENGHT_OBSERVACIONES = 1000;
	private static final int MAX_LENGHT_ACLARACION = 200;
	//MGR - #1027
	private static final int MAX_LENGHT_ORDEN_COMPRA = 150;

	private SolicitudServicioDto solicitudServicio;

	private ListBox descuentoTotal;
	private Button tildeVerde;
	
	private InlineHTML clienteCedente;
	private HTML refreshCedente; 
	private TextBox canalVtas;
	private ListBox sucursalOrigen;
	private ListBox vendedor;
	private ListBox criterioBusqContrato;
	private RegexTextBox parametroBusqContrato;
	private List<ServicioAdicionalIncluidoDto> serviciosAdicionalesContrato;
	
	private static final String ITEM_CONTRATO = "Contrato";
	private static final String ITEM_TELEFONO = "Teléfono";
	private static final String ITEM_FLOTA_ID = "Flota*ID";
	private static final String ITEM_SUSCRIPTOR = "Suscriptor";
	
	private static final String VALUE_CONTRATO = "1";
	private static final String VALUE_TELEFONO = "2";
	private static final String VALUE_FLOTA_ID = "3";
	private static final String VALUE_SUSCRIPTOR = "4";
	private static final String TIPO_CANAL_VTA_TRANSFERENCIA = "TIPO_CANAL_VENTAS_TRANSFERENCIA";
	private static final String CANAL_VTA_TRANSFERENCIA = "Transferencia";
	public static final String NO_COMISIONABLE = "No Comisionable";
	
	private RegexTextBox cantidadEquipos;
	private SimpleDatePicker fechaFirma;
	private ListBox estadoH;
	private SimpleDatePicker fechaEstado;
	private RegexTextBox cantidadEquiposTr;
	private SimpleDatePicker fechaFirmaTr;
	private ListBox estadoTr;
	private SimpleDatePicker fechaEstadoTr;
	private static final String ITEM_PENDIENTE = "Pendiente";
	private static final String ITEM_PASS = "Pass";
	private String clienteHistorico = "";
	
	//analisis
	private RegexTextBox titulo;
	private RegexTextBox enviarA;
    private ListBox nuevoEstado;
	private ListBox comentarioAnalista;
	private TextArea notaAdicional;
	Label cantEquipos;
	private List<ComentarioAnalistaDto> comentarioAnalistaMensaje = new ArrayList<ComentarioAnalistaDto>();
    private List<EstadoSolicitudDto> opcionesEstado = new ArrayList<EstadoSolicitudDto>();
    //finde analisis
	private CheckBox retiraEnSucursal;
    private RegexTextBox numeroSSWeb; //Mejoras Perfil Telemarketing. REQ#2 - Nro de SS Web en la Solicitud de Servicio.
    
	public EditarSSUIData(EditarSSUIController controller) {
		this.controller = controller;
		serviciosAdicionales = new ArrayList();
		serviciosAdicionalesContrato = new ArrayList<ServicioAdicionalIncluidoDto>();
        fields.add(retiraEnSucursal= new CheckBox());
		fields.add(nss = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(10), true));
		fields.add(nflota = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(5)));
		fields.add(origen = new ListBox(""));
		fields.add(control = new ListBox(""));
		fields.add(origenTR = new ListBox(""));
		fields.add(entrega = new ListBox());
		fields.add(descuentoTotal = new ListBox());
		tildeVerde = new Button();
		tildeVerde.addStyleName("icon-tildeVerde");
		fields.add(tildeVerde);
		//MGR - #1027
		fields.add(ordenCompra = new RegexTextBox(RegularExpressionConstants.getCantCaracteres(150)));

		//solapa analisis
		fields.add(notaAdicional = new TextArea());
		notaAdicional.setWidth("250px");
		notaAdicional.setHeight("100px");
		fields.add(nuevoEstado = new ListBox(""));
		fields.add(comentarioAnalista = new ListBox(""));
		titulo = new RegexTextBox();
		titulo.setWidth("450px");
		fields.add(titulo);
		enviarA = new RegexTextBox();
		enviarA.setWidth("450px");
		fields.add(enviarA);
		
		//-------------------------------------------------------------
		fields.add(cantEquipos = new Label());
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
				if(getGrupoSolicitud() != null && getGrupoSolicitud().isTransferencia()){
					showMaxLengthTextAreaError(observaciones, MAX_LENGHT_OBSERVACIONES);
				}else{
					showMaxLengthTextAreaError(observaciones, MAX_LENGHT_OBS_ACLARACION);	
				}
				
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
		
		fields.add(clienteCedente = new InlineHTML());
		//MGR - #1444
		refreshCedente = IconFactory.lupa("Cambiar Cedente");
		refreshCedente.addStyleName("floatRight mr10 mt3");
		fields.add(refreshCedente);
		fields.add(canalVtas = new TextBox());
		fields.add(sucursalOrigen = new ListBox(""));
		fields.add(vendedor = new ListBox(""));
		vendedor.addChangeListener(new ChangeListener() {
			public void onChange(Widget arg0) {
				VendedorDto vendSelected = (VendedorDto)vendedor.getSelectedItem();
				if( vendSelected != null && vendSelected.getApellido() != null ){
					sucursalOrigen.selectByValue(((VendedorDto)vendedor.getSelectedItem()).getIdSucursal().toString());
				}
			}
		});
		
		fields.add(criterioBusqContrato = new ListBox());
		criterioBusqContrato.setWidth("150px");
		criterioBusqContrato.addChangeHandler(new ChangeHandler() {
			
			public void onChange(ChangeEvent arg0) {
				parametroBusqContrato.setText("");
				String critBusq = criterioBusqContrato.getValue(criterioBusqContrato.getSelectedIndex());
				if(critBusq.equals(VALUE_CONTRATO)){
					parametroBusqContrato.setPattern(RegularExpressionConstants.numeros);
					parametroBusqContrato.setMaxLength(25);
				}
				else if(critBusq.equals(VALUE_TELEFONO)){
					parametroBusqContrato.setPattern(RegularExpressionConstants.getNumerosLimitado(10));
				}
				else if(critBusq.equals(VALUE_FLOTA_ID)){
					parametroBusqContrato.setPattern("[0-9\\*]*");
					parametroBusqContrato.setMaxLength(11);
				}
				else if(critBusq.equals(VALUE_SUSCRIPTOR)){
					parametroBusqContrato.setPattern(RegularExpressionConstants.numerosYPunto);
					parametroBusqContrato.setMaxLength(25);
				}
			}
		});
		
		fields.add(parametroBusqContrato = new RegexTextBox());
		
		//larce - Comentado para salir solo con cierre
//		fields.add(cantidadEquipos = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(3), true));
//		fields.add(fechaFirma = new SimpleDatePicker(false, true));
//		fields.add(estadoH = new ListBox(""));
//		fields.add(fechaEstado = new SimpleDatePicker(false, true));
//		fields.add(cantidadEquiposTr = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(3), true));
//		fields.add(fechaFirmaTr = new SimpleDatePicker(false, true));
//		fields.add(estadoTr = new ListBox(""));
//		fields.add(fechaEstadoTr = new SimpleDatePicker(false, true));
		
		//LF
		if(!controller.isEditable()) {
			FormUtils.disableFields(fields);
		}
		
		//larce - Busco en vantive y completo los campos si están en blanco
		//larce - Comentado para salir solo con cierre
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_HISTORICO.getValue())) {
//			nss.addBlurHandler(new BlurHandler() {
//				public void onBlur(BlurEvent event) {
//					SolicitudRpcService.Util.getInstance().buscarHistoricoVentas(nss.getText(), 
//							new DefaultWaitCallback<List<SolicitudServicioDto>>() {
//						public void success(List<SolicitudServicioDto> result) {
//							if (result.size() > 0) {
//								SolicitudServicioDto ss = result.get(0);
//								completarCamposHistorico(ss);
//							}
//						}
//					});
//				}
//			});
//		}
		fields.add(numeroSSWeb = new RegexTextBox(RegularExpressionConstants.getCantCaracteres(10), true));
				
//		MGR - Facturacion - Necesito saber cuando se presiono el check para evaluar que opciones mostrar
		retiraEnSucursal.addClickListener(new ClickListener() {
				public void onClick(Widget arg0) {
					EventBusUtil.getEventBus().fireEvent(
							new RefrescarPantallaSSEvent(solicitudServicio, 
									retiraEnSucursal.getValue()));
				}
			});
		
		inicializarBusquedaContratos();
	}

	public void completarCamposHistorico(SolicitudServicioDto ss) {
		if ("".equals(cantidadEquipos.getText())) {
			cantidadEquipos.setText(ss.getCantidadEquiposH().toString());
		}
		if ("".equals(fechaFirma.getTextBox().getText())) {
			fechaFirma.getTextBox().setText(dateTimeFormat.format(ss.getFechaFirma()));
		}
		if (!ss.getEstadoH().getDescripcion().equals(estadoH.getSelectedItemText())) {
			estadoH.setSelectedItem(ss.getEstadoH());
		}
		if ("".equals(fechaEstado.getTextBox().getText())) {
			fechaEstado.getTextBox().setText(dateTimeFormat.format(ss.getFechaEstado()));
		}
		if ("".equals(cantidadEquiposTr.getText())) {
			cantidadEquiposTr.setText(ss.getCantidadEquiposH().toString());
		}
		if ("".equals(fechaFirmaTr.getTextBox().getText())) {
			fechaFirmaTr.getTextBox().setText(dateTimeFormat.format(ss.getFechaFirma()));
		}
		if (!ss.getEstadoH().getDescripcion().equals(estadoTr.getSelectedItemText())) {
			estadoTr.setSelectedItem(ss.getEstadoH());
		}
		if ("".equals(fechaEstadoTr.getTextBox().getText())) {
			fechaEstadoTr.getTextBox().setText(dateTimeFormat.format(ss.getFechaEstado()));
		}
		clienteHistorico = ss.getClienteHistorico();
	}

	/** Carga los datos de Header de infocom */
	public void setInfocom(InfocomInitializer infocom) {
		this.infocom = infocom;  
		nflota.setText(infocom.getFlota());
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

//	public void copiarSS(){
//		
//		if(solicitudServicio != null){
//			EditarSSUI edicion = (EditarSSUI) this.controller;
////			SolicitudServicioDto ssDto = solicitudServicio;
//			edicion.loadCopiarSS(solicitudServicio);
//		}
//	}
	
	public RegexTextBox getNss() {
		return nss;
	}

	public RegexTextBox getNflota() {
		return nflota;
	}

	public ListBox getOrigen() {
		return origen;
	}
	
	public ListBox getOrigenTR() {
		return origenTR;
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
	
	public InlineHTML getClienteCedente() {
		return clienteCedente;
	}

	public HTML getRefreshCedente() {
		return refreshCedente;
	}
	
	public TextBox getCanalVtas() {
		return canalVtas;
	}
	
	public ListBox getSucursalOrigen(){
		return sucursalOrigen;
	}

	public ListBox getVendedor() {
		return vendedor;
	}

	public ListBox getCriterioBusqContrato() {
		return criterioBusqContrato;
	}

	public RegexTextBox getParametroBusqContrato() {
		return parametroBusqContrato;
	}

	public void setDescuentoTotal(ListBox descuentoTotal) {
		this.descuentoTotal = descuentoTotal;
	}
	
	
	public void setEstado(String estado){
		if (this.estado == null) this.estado = new Label(estado);
		this.estado.setText(estado);
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
		
//		MGR - #6706
		if(solicitud.getRetiraEnSucursal()!= null && 
				(solicitud.getGrupoSolicitud().isEquiposAccesorios() || solicitud.getGrupoSolicitud().isVtaSoloSIM())){
			retiraEnSucursal.setValue(solicitud.getRetiraEnSucursal());
		}
		
		//MGR - #1152
//		MGR - Mejoras Perfil Telemarketing. REQ#1. Cambia la definicion de prospect para Telemarketing
		//Si no es cliente, es prospect o prospect en carga
//		boolean esProspect =RegularExpressionConstants.isVancuc(solicitud.getCuenta().getCodigoVantive());
		boolean esProspect = !solicitud.getCuenta().isCliente();
		
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
//		ControlesDto newControl= new ControlDto();
//	    newControl.setDescripcion(solicitud.getControl());
//	    newControl.setId(new Long(1));
		control.setSelectedItem(solicitud.getControl());
		nuevoEstado.setSelectedItem(solicitud.getEstados());
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
			origenTR.setSelectedItem(solicitud.getOrigen());
			anticipo.setSelectedItem(solicitud.getTipoAnticipo());
			//larce - Comentado para salir solo con cierre
//			if (solicitudServicio.getEstadoH() != null) {
//				estadoH.setSelectedItem(solicitudServicio.getEstadoH());
//				estadoTr.setSelectedItem(solicitudServicio.getEstadoH());
//			}
		} else {
			deferredLoad();
		}
//		comprobarDescuentoTotal();		
		recarcularValores();
		 String ss="";
		 if (solicitudServicio.getNumero()==null){
			 ss="";
		 }else{
			ss=solicitudServicio.getNumero(); 
		 }
		  String textoDeEnvio= "";
		if (solicitudServicio.getUsuarioCreacion().isEECC()){
			
			textoDeEnvio= solicitudServicio.getUsuarioCreacion().getEmail() +"-"+ solicitudServicio.getUsuarioCreacion().getTelefono();
		}else{
			if (solicitudServicio.getUsuarioCreacion().isDealer()){
				textoDeEnvio= solicitudServicio.getUsuarioCreacion().getEmail();
			}
			
		}
	 
		enviarA.setText(textoDeEnvio);
		titulo.setText("Nro de SS:" + ss + "  Razon Social:" +solicitudServicio.getCuenta().getPersona().getRazonSocial()  );
		vendedor.setSelectedItem(solicitudServicio.getVendedor());
		if(solicitudServicio.getIdSucursal() != null){
			sucursalOrigen.selectByValue(solicitudServicio.getIdSucursal().toString());
		} else{ //Para que cargue correctamente la opcion del combo
			sucursalOrigen.selectByValue(solicitudServicio.getVendedor().getIdSucursal().toString());
		}
		
		if(solicitud.getGrupoSolicitud().isTransferencia()){
			cargarDatosTransferencia();
			//larce
			//larce - Comentado para salir solo con cierre
//			cantidadEquiposTr.setText(solicitudServicio.getCantidadEquiposH() != null ? solicitudServicio
//					.getCantidadEquiposH().toString() : "");
//			fechaFirmaTr.getTextBox().setText(solicitudServicio.getFechaFirma() != null ? dateTimeFormat.
//					format(solicitudServicio.getFechaFirma()) : "");
//			fechaEstadoTr.getTextBox().setText(solicitudServicio.getFechaEstado() != null ? dateTimeFormat.
//					format(solicitudServicio.getFechaEstado()) : "");
		} 
//		else {
//			cantidadEquipos.setText(solicitudServicio.getCantidadEquiposH() != null ? solicitudServicio.
//					getCantidadEquiposH().toString() : "");
//			fechaFirma.getTextBox().setText(solicitudServicio.getFechaFirma() != null ? dateTimeFormat.
//					format(solicitudServicio.getFechaFirma()) : "");
//			fechaEstado.getTextBox().setText(solicitudServicio.getFechaEstado() != null ? dateTimeFormat.
//					format(solicitudServicio.getFechaEstado()) : "");
//		}
		//Mejoras Perfil Telemarketing. REQ#2 - N° de SS Web en la Solicitud de Servicio.
		if (ClientContext.getInstance().getVendedor().isTelemarketing() &&
				(solicitud.getGrupoSolicitud().isEquiposAccesorios() ||
//						MGR - #6719
						solicitud.getGrupoSolicitud().isVtaSoloSIM())) {
			numeroSSWeb.setText(solicitud.getNumeroSSWeb());
		}
	}

	private void cargarDatosTransferencia(){
		if(solicitudServicio.getCuentaCedente() != null){
			clienteCedente.setText(solicitudServicio.getCuentaCedente().getCodigoVantive());
		}
		canalVtas.setText(CANAL_VTA_TRANSFERENCIA);
//		if (!solicitudServicio.getVendedor().isADMCreditos()) {
//			vendedor.setSelectedItem(solicitudServicio.getVendedor());
//			if(solicitudServicio.getIdSucursal() != null){
//				sucursalOrigen.selectByValue(solicitudServicio.getIdSucursal().toString());
//			} else{ //Para que cargue correctamente la opcion del combo
//				sucursalOrigen.selectByValue(solicitudServicio.getVendedor().getIdSucursal().toString());
//			}
//		}
	}

	private void deferredLoad() {
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (anticipo.getItemCount() == 0) {
					return true;
				}
				origen.setSelectedItem(solicitudServicio.getOrigen());
				origenTR.setSelectedItem(solicitudServicio.getOrigen());
				anticipo.setSelectedItem(solicitudServicio.getTipoAnticipo());
				if (solicitudServicio.getEstadoH() != null) {
					estadoH.setSelectedItem(solicitudServicio.getEstadoH());
					estadoTr.setSelectedItem(solicitudServicio.getEstadoH());
				}
				return false;
			}
		});
	}

	public Long getCuentaId() {
		return solicitudServicio.getCuenta().getId();
	}
	
	public Long getIdSucursalSolicitud(){
		if(solicitudServicio == null){
			return null;
		}
		return solicitudServicio.getIdSucursal();
	}

	public SolicitudServicioDto getSolicitudServicio() {
		solicitudServicio.setNumero(nss.getText());
		solicitudServicio.setNumeroFlota(nflota.getText());
		EstadoSolicitudDto estadoDto = (EstadoSolicitudDto) nuevoEstado.getSelectedItem();
		solicitudServicio.setEstados(estadoDto);
		if (ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_ESTADO.getValue())) {
		
		solicitudServicio.setControl((ControlDto) control.getSelectedItem());// .getSelectedItem().getItemText());
		}
		if (origen.getSelectedItem() != null) {
			solicitudServicio.setOrigen((OrigenSolicitudDto) origen.getSelectedItem());
		} else if (origenTR.getSelectedItem() != null) {
			solicitudServicio.setOrigen((OrigenSolicitudDto) origenTR.getSelectedItem());
		}
		if (ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())) {
			if(sucursalOrigen.getSelectedItem() != null) {
				solicitudServicio.setIdSucursal(Long.valueOf(sucursalOrigen.getSelectedItem().getItemValue()));
			}
		} else {
			solicitudServicio.setIdSucursal(solicitudServicio.getVendedor().getIdSucursal());
		}
		if (ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())) {
			VendedorDto vendedorDto = (VendedorDto) vendedor.getSelectedItem();
			solicitudServicio.setVendedor(vendedorDto);
		}
		
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
		
//		MGR - #6706
		if(solicitudServicio.getGrupoSolicitud().isEquiposAccesorios() ||
				solicitudServicio.getGrupoSolicitud().isVtaSoloSIM()){
			solicitudServicio.setRetiraEnSucursal(retiraEnSucursal.getValue());
		}
		
		//larce
		//larce - Comentado para salir solo con cierre
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_HISTORICO.getValue())) {
//			if(!cantidadEquipos.getText().equals("")) {
//				solicitudServicio.setCantidadEquiposH(new Long(cantidadEquipos.getText()));
//			}
//			if(!fechaFirma.getTextBox().getText().equals("")) {
//				solicitudServicio.setFechaFirma(dateTimeFormat.parse(fechaFirma.getTextBox().getText()));
//			}
//			solicitudServicio.setEstadoH((EstadoHistoricoDto) estadoH.getSelectedItem());
//			if(!fechaEstado.getTextBox().getText().equals("")) {
//				solicitudServicio.setFechaEstado(dateTimeFormat.parse(fechaEstado.getTextBox().getText()));
//			}
//			solicitudServicio.setClienteHistorico(clienteHistorico);
//		}
//		Mejoras Perfil Telemarketing. REQ#2 - N° de SS Web en la Solicitud de Servicio.
		if (ClientContext.getInstance().getVendedor().isTelemarketing() && 
				(solicitudServicio.getGrupoSolicitud().isEquiposAccesorios()
//						MGR - #6719
						|| solicitudServicio.getGrupoSolicitud().isVtaSoloSIM())) {
			solicitudServicio.setNumeroSSWeb(numeroSSWeb.getText());
		}		
		return solicitudServicio;
	}

	public List<String> validarCompletitud() {
		List<String> errores = CuentaDomiciliosForm.validarCompletitud(solicitudServicio.getCuenta().getPersona()
				.getDomicilios());
		errores.addAll(CuentaDatosForm.validarCompletitud(solicitudServicio.getCuenta().getPersona()));
		return errores;
	}

	public List<String> validarParaGuardar() {
		final GwtValidator validator = new GwtValidator();
		for (LineaSolicitudServicioDto linea : solicitudServicio.getLineas()) {
			validarAlquileresDeLineaSS(validator, linea);
			validarPortabilidadAdicional(validator, linea);
			validarServicioMDS(validator, linea);
			validarCargoActivacion(validator, linea);
		}
		solicitudServicio.refreshPreciosTotales();
		validator.addTarget(origen).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().origen()));
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())){
			validator.addTarget(vendedor).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().vendedor()));
		}
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())){
			validator.addTarget(sucursalOrigen).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().sucOrigen()));
		}
		
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
		//larce - Comentado para salir solo con cierre
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_HISTORICO.getValue())) {
//			validator.addTarget(nss).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().nssReq()));
//			validator.addTarget(cantidadEquipos).required(
//					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().cantidadEquipos()));
//			validator.addTarget(fechaFirma.getTextBox()).required(
//					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().fechaFirma()));
//			if (estadoH.getSelectedIndex() == 0) {
//				validator.addError(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().estado()));
//			} else if (RegularExpressionConstants.isVancuc(solicitudServicio.getCuenta().getCodigoVantive())
//						&& "Pass".equals(estadoH.getSelectedItemText())) {
//					validator.addError("No puede elegir el estado Pass para este tipo de clientes.");
//				}
//			if ("".equals(fechaEstado.getTextBox().getText())) {
//				fechaEstado.getTextBox().setText(dateTimeFormat.format(new Date()));
//			}
//		}
		//Portabilidad
		for (LineaSolicitudServicioDto linea : solicitudServicio.getLineas()) {
			if(linea.getPortabilidad() != null){
				if(linea.getPortabilidad().getTelefonoPortar() == null){
					validator.addError("Falta validar el numero de telefono a portar del " + linea.getAlias());
				}
			}
		}

		if(existeSIM_IMEIRepetidas(validator)){
			validator.addError("No puede tener la misma SIM o IMEI en mas de una linea");
		}
		
		validator.fillResult();
		return validator.getErrors();
	}

	private boolean existeSIM_IMEIRepetidas(GwtValidator validator) {
		boolean haySIM_IMEIRepetidas = false;
		if (solicitudServicio.getRetiraEnSucursal() || solicitudServicio.getGrupoSolicitud().isVtaSoloSIM()) {//#6705
			for (LineaSolicitudServicioDto linea : solicitudServicio.getLineas()) {
				int cantIMEI = 0;
				int cantSIM = 0;
				if (!haySIM_IMEIRepetidas) {
					for (LineaSolicitudServicioDto li : solicitudServicio.getLineas()) {
						if (GwtValidationUtils.validateNotEmpty(linea.getNumeroIMEI()) 
								&& GwtValidationUtils.validateEquals(linea.getNumeroIMEI(),li.getNumeroIMEI())) {
							cantIMEI = cantIMEI + 1;
						}
						if (GwtValidationUtils.validateNotEmpty(linea.getNumeroSimcard()) 
								&& GwtValidationUtils.validateEquals(linea.getNumeroSimcard(),li.getNumeroSimcard())) {
							cantSIM = cantSIM + 1;
						}
					}
					haySIM_IMEIRepetidas = cantIMEI > 1 || cantSIM > 1;
				}
			}
		}
		return haySIM_IMEIRepetidas;
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
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())){
			validator.addTarget(vendedor).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().vendedor()));
		}
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())){
			validator.addTarget(sucursalOrigen).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().sucOrigen()));
		}
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
			validarPortabilidadAdicional(validator,linea);
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
		//larce - Se valida que la fecha estado no sea menor a 2 meses y mayor a 1 mes de la fecha del día.
		//larce - Comentado para salir solo con cierre
//		if (ClientContext.getInstance().checkPermiso(PermisosEnum.VER_HISTORICO.getValue())) {
//			final long unDiaEnMilis = 1000*60*60*24;
//			final Date hace2Meses = new Date(System.currentTimeMillis() - 60*unDiaEnMilis);
//			final Date dentroDe1mes = new Date(System.currentTimeMillis() + 30*unDiaEnMilis);
//			final Date fechaEstadoTB = dateTimeFormat.parse(fechaEstado.getTextBox().getText());;
//			
//			if (fechaEstadoTB.before(hace2Meses) || fechaEstadoTB.after(dentroDe1mes)) {
//				validator.addError("La fecha de estado no debe ser menor a 2 meses o mayor a 1 mes con respecto a la fecha de hoy.");
//			}
//			solicitudServicio.setClienteHistorico(clienteHistorico);
//			if ("".equals(solicitudServicio.getClienteHistorico()) || solicitudServicio.getClienteHistorico() == null) {
//				validator.addError("El cliente no se encuentra asociado al historico para el numero de solicitud ingresado.");
//			} else if (!solicitudServicio.getCuenta().getCodigoVantive().equals(solicitudServicio.getClienteHistorico())) {
//				validator.addError("El cliente difiere entre el de la SS y el ingresado en el Histórico de Ventas.");
//			}
//			
//			if (RegularExpressionConstants.isVancuc(solicitudServicio.getCuenta().getCodigoVantive())
//					&& "Pass".equals(estadoH.getSelectedItemText())) {
//				validator.addError("No puede elegir el estado Pass para este tipo de clientes.");
//			}
//		}
		
//		Mejoras Perfil Telemarketing. REQ#2 - N° de SS Web en la Solicitud de Servicio.
		if (ClientContext.getInstance().getVendedor().isTelemarketing() &&
				(solicitudServicio.getGrupoSolicitud().isEquiposAccesorios() ||
//						MGR - #6719
						solicitudServicio.getGrupoSolicitud().isVtaSoloSIM())) {
			OrigenSolicitudDto origenSolicitudDto = (OrigenSolicitudDto) origen.getSelectedItem();
			if (origenSolicitudDto != null && origenSolicitudDto.getUsaNumeroSSWeb()) {
				validator.addTarget(numeroSSWeb).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Nro SS Web"));
			}
		}
		
		//#6705
		if(existeSIM_IMEIRepetidas(validator)){
			validator.addError("No puede tener la misma SIM o IMEI en mas de una linea");
		}
		
		validator.fillResult();
		List<String> errores = validator.getErrors();
		errores.addAll(validarCompletitud());
		
		return errores;
	}
	
	/**
	 * TODO: Portabilidad
	 * @param validator
	 * @param linea
	 */
	private void validarPortabilidadAdicional(GwtValidator validator,LineaSolicitudServicioDto linea){
		if(linea.getPortabilidad() != null){
//			boolean portabilidadAdicional = false;
			for(ServicioAdicionalLineaSolicitudServicioDto servicioAdicional : linea.getServiciosAdicionales()){
				if(servicioAdicional.getServicioAdicional().getEsPortabilidad()){
					servicioAdicional.setChecked(true);
					servicioAdicional.setObligatorio(true);
				}
//				if(servicioAdicional.getServicioAdicional().getEsPortabilidad() && servicioAdicional.isChecked()) portabilidadAdicional = true;
			}
//			if(!portabilidadAdicional) validator.addError(Sfa.constant().ERR_FALTA_PORTABILIDAD().replaceAll(V1, linea.getAlias()));
		}
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
		
//		MGR - Facturacion
		EventBusUtil.getEventBus().fireEvent(
				new RefrescarPantallaSSEvent(solicitudServicio, this.getRetiraEnSucursal().getValue()));
		
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
		solicitudServicio.getLineas().get(index).setPortabilidad(null);
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
	public void mergeServiciosAdicionalesConLineaSolicitudServicio(int indexLinea,List<ServicioAdicionalLineaSolicitudServicioDto> list) {
		List<ServicioAdicionalLineaSolicitudServicioDto> serviciosAGuardar = getLineasSolicitudServicio().get(indexLinea).getServiciosAdicionales();
		
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
			} else if (domicilio.getIdEntrega().equals(EstadoTipoDomicilioDto.SI.getId())) {
				entrega.addItem(domicilio);
			}
			if (domicilio.getIdFacturacion().equals(EstadoTipoDomicilioDto.PRINCIPAL.getId())) {
				facturacion.addItem(domicilio);
			} else if (domicilio.getIdFacturacion().equals(EstadoTipoDomicilioDto.SI.getId())) {
				facturacion.addItem(domicilio);
			}
			if (domicilio.getId() == -1) { // si lo acaba de crear lo selecciono
				entrega.setSelectedItem(domicilio);
				facturacion.setSelectedItem(domicilio);
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
		if (solicitudServicio != null && solicitudServicio.getGrupoSolicitud() != null) {
			return solicitudServicio.getGrupoSolicitud().isCDW();
		}
		return false;
	}
	
	public boolean isEquiposAccesorios(){
		if (solicitudServicio != null && solicitudServicio.getGrupoSolicitud() != null) {
			return solicitudServicio.getGrupoSolicitud().isEquiposAccesorios();
		}
		return false;
	}

	public boolean isMDS() {
		if (solicitudServicio != null && solicitudServicio.getGrupoSolicitud() != null) {
			return solicitudServicio.getGrupoSolicitud().isMDS();
		}
		return false;
	}
	
	public boolean isTRANSFERENCIA() {
		if (solicitudServicio != null && solicitudServicio.getGrupoSolicitud() != null) {
		    return solicitudServicio.getGrupoSolicitud().isTransferencia();
		}
		return false;
	}
	
//	MGR - #6719
	public boolean isVentaSoloSIM(){
		if (solicitudServicio != null && solicitudServicio.getGrupoSolicitud() != null) {
			return solicitudServicio.getGrupoSolicitud().isVtaSoloSIM();
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
	
	
	private void inicializarBusquedaContratos() {
		criterioBusqContrato.addItem(ITEM_CONTRATO, VALUE_CONTRATO);
		criterioBusqContrato.addItem(ITEM_TELEFONO, VALUE_TELEFONO);
		criterioBusqContrato.addItem(ITEM_FLOTA_ID, VALUE_FLOTA_ID);
		criterioBusqContrato.addItem(ITEM_SUSCRIPTOR, VALUE_SUSCRIPTOR);

		criterioBusqContrato.setSelectedIndex(0);
		parametroBusqContrato.setPattern(RegularExpressionConstants.numeros);
		parametroBusqContrato.setMaxLength(25);
	}

	public SolicitudServicioDto getSolicitudServicioTranferencia() {
		solicitudServicio.setNumero(nss.getText());
		solicitudServicio.setOrigen((OrigenSolicitudDto) origenTR.getSelectedItem());
		solicitudServicio.setObservaciones(observaciones.getText());
		solicitudServicio.setControl((ControlDto) control.getSelectedItem());
	
		//MGR - #1359
		//solicitudServicio.setUsuarioCreacion(ClientContext.getInstance().getVendedor());
		if (ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())) {
			solicitudServicio.setIdSucursal(Long.valueOf(sucursalOrigen.getSelectedItem().getItemValue()));
		} else {
			solicitudServicio.setIdSucursal(solicitudServicio.getVendedor().getIdSucursal());
		}
		if (ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())) {
			VendedorDto vendedorDto = (VendedorDto) vendedor.getSelectedItem();
			solicitudServicio.setVendedor(vendedorDto);
		}
		
		if(canalVtas.getText().equals(CANAL_VTA_TRANSFERENCIA)){
			HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
			if(instancias != null){
				solicitudServicio.setTipoCanalVentas(instancias.get(TIPO_CANAL_VTA_TRANSFERENCIA));
			}
		}
		//larce
		//larce - Comentado para salir solo con cierre
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_HISTORICO.getValue())) {
//			solicitudServicio.setCantidadEquiposH(new Long(cantidadEquiposTr.getText()));
//			solicitudServicio.setFechaFirma(dateTimeFormat.parse(fechaFirmaTr.getTextBox().getText()));
//			solicitudServicio.setEstadoH((EstadoHistoricoDto) estadoTr.getSelectedItem());
//			solicitudServicio.setFechaEstado(dateTimeFormat.parse(fechaEstadoTr.getTextBox().getText()));
//			solicitudServicio.setClienteHistorico(clienteHistorico);
//		}
		return solicitudServicio;
	}
	
	public List<String> validarTransferenciaParaGuardar(List<ContratoViewDto> contratos) {
		setContratosCedidos(contratos);

		GwtValidator validator = new GwtValidator();
		
		validator.addTarget(origenTR).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().origen()));
		
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())){
			validator.addTarget(vendedor).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().vendedor()));
		}
		
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())){
			validator.addTarget(sucursalOrigen).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().sucOrigen()));
		}
		
		validator.addTarget(canalVtas).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Canal de Ventas"));
		//larce - Comentado para salir solo con cierre
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_HISTORICO.getValue())) {
//			validator.addTarget(nss).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().nssReq()));
//			validator.addTarget(cantidadEquiposTr).required(
//					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().cantidadEquipos()));
//			validator.addTarget(fechaFirmaTr.getTextBox()).required(
//					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().fechaFirma()));
//			if (estadoTr.getSelectedIndex() == 0) {
//				validator.addError(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().estado()));
//			} else if (RegularExpressionConstants.isVancuc(solicitudServicio.getCuenta().getCodigoVantive())
//					&& "Pass".equals(estadoTr.getSelectedItemText())) {
//				validator.addError("No puede elegir el estado Pass para este tipo de clientes.");
//			}
//			if ("".equals(fechaEstado.getTextBox().getText())) {
//				fechaEstado.getTextBox().setText(dateTimeFormat.format(new Date()));
//			}
//		}
		validator.fillResult();
		return validator.getErrors();
	}
	
	/**
	 * @param generacionCierreDefinitivo
	 *            true si debe validar para la generacion o cierre definitiva de la solicitud, que seria el
	 *            aceptar de la pantalla que pide los mails
	 * @return Lista de errores
	 */
	public List<String> validarTransferenciaParaCerrarGenerar(List<ContratoViewDto> contratos, boolean generacionCierreDefinitivo) {
		setContratosCedidos(contratos);
		
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
		validator.addTarget(origenTR).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().origen()));
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())){
			validator.addTarget(vendedor).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().vendedor()));
		}
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())){
			validator.addTarget(sucursalOrigen).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().sucOrigen()));
		}
		
		validator.addTarget(canalVtas).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Canal de Ventas"));
		
		if (solicitudServicio.getContratosCedidos().isEmpty()) {
			validator.addError(Sfa.constant().ERR_REQUIRED_CONTRATO_TRANSFERENCIA());
		}

		validator.fillResult();
		List<String> errores = validator.getErrors();
		errores.addAll(validarCompletitud());
		if(!validator.getErrors().isEmpty()){
			return errores;
		}
		
//		validator.addErrors(validarPlanesCedentes());
		
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
		return validator.getErrors();
	}
	
	/**
	 * Le setea a la solicitud los contratos cedidos
	 */
	private void setContratosCedidos(List<ContratoViewDto> contratos){
		if(solicitudServicio != null){
			solicitudServicio.setContratosCedidos(contratos);
		}
	}

	public void loadServiciosAdicionalesContrato(List<ServicioAdicionalIncluidoDto> list) {
		serviciosAdicionalesContrato.clear();
		serviciosAdicionalesContrato.addAll(list);
	}
	
	public List<ServicioAdicionalIncluidoDto> getServiciosAdicionalesContrato() {
		return serviciosAdicionalesContrato;
	}

	public List<ContratoViewDto> getContratosCedidos() {
		return solicitudServicio.getContratosCedidos();
	}
	
//	public List<String> validarCompletitudTransferencia() {
//		
//		GwtValidator validator = new GwtValidator();
//		validator.addTarget(nss).required(
//				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Nº de Solicitud")).maxLength(10,
//				Sfa.constant().ERR_NSS_LONG());
//
//		validator.addTarget(origen).required(
//				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().origen()));
//		
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())){
//			validator.addTarget(vendedor).required(
//					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().vendedorReq()));
//		}
//		
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())){
//			validator.addTarget(sucursalOrigen).required(
//					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, Sfa.constant().sucOrigen()));
//		}
//		
//		validator.addTarget(canalVtas).required(
//				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Canal de Ventas"));
//		
//		validator.fillResult();
//		List<String> errores = validator.getErrors();
//		return errores;
//	}
	
	public void validarPlanesCedentes(final DefaultWaitCallback<List<String>> defaultWaitCallback, boolean isSaving){
		
		SolicitudRpcService.Util.getInstance().validarPlanesCedentes(solicitudServicio.getContratosCedidos(),
				getCuenta().isEmpresa(), isSaving, defaultWaitCallback);
	}
	
//	public void validarStockAgregarItem(final DefaultWaitCallback<List<String>> defaultWaitCallback, boolean isSaving){
//		SolicitudRpcService.Util.getInstance().validarSIM_IMEI(solicitudServicio, defaultWaitCallback);
//	}
	
	//MGR - #1415
	public Long getIdSolicitudServicio(){
		if(solicitudServicio != null){
			return solicitudServicio.getId();
		}
		return null;
	}

	public ListBox getControl() {
		return control;
	}

	public void setControl(ListBox control) {
		this.control = control;
	}

	public Label getEstado() {
		return estado;
	}

	public void setEstado(Label estado) {
		this.estado = estado;
	}
	
	
	public RegexTextBox getCantidadEquipos() {
		return cantidadEquipos;
	}

	public Widget getFechaFirma() {
		Grid datePickerFull = new Grid(1, 2);
		fechaFirma.setWeekendSelectable(true);
		fechaFirma.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
		datePickerFull.setWidget(0, 0, fechaFirma.getTextBox());
		datePickerFull.setWidget(0, 1, fechaFirma);
		return datePickerFull;
	}

	public ListBox getEstadoH() {
		return estadoH;
	}

	public Widget getFechaEstado() {
		Grid datePickerFull = new Grid(1, 2);
		fechaEstado.setWeekendSelectable(true);
		fechaEstado.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
		datePickerFull.setWidget(0, 0, fechaEstado.getTextBox());
		datePickerFull.setWidget(0, 1, fechaEstado);
		return datePickerFull;
	}
	
	public RegexTextBox getCantidadEquiposTr() {
		return cantidadEquiposTr;
	}

	public Widget getFechaFirmaTr() {
		Grid datePickerFull = new Grid(1, 2);
		fechaFirmaTr.setWeekendSelectable(true);
		fechaFirmaTr.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
		datePickerFull.setWidget(0, 0, fechaFirmaTr.getTextBox());
		datePickerFull.setWidget(0, 1, fechaFirmaTr);
		return datePickerFull;
	}

	public ListBox getEstadoTr() {
		return estadoTr;
	}

	public Widget getFechaEstadoTr() {
		Grid datePickerFull = new Grid(1, 2);
		fechaEstadoTr.setWeekendSelectable(true);
		fechaEstadoTr.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
		datePickerFull.setWidget(0, 0, fechaEstadoTr.getTextBox());
		datePickerFull.setWidget(0, 1, fechaEstadoTr);
		return datePickerFull;
	}

	public void inicializarEstado(ListBox listBoxEstado, boolean esProspect) {
		listBoxEstado.clear();
		listBoxEstado.addItem(ITEM_PENDIENTE);
		if (!esProspect) {
			listBoxEstado.addItem(ITEM_PASS);
		}
		listBoxEstado.setSelectedIndex(0);
	}
	
	public RegexTextBox getTitulo() {
		return titulo;
	}

	public void setTitulo(RegexTextBox titulo) {
		this.titulo = titulo;
	}

	public RegexTextBox getEnviarA() {
		return enviarA;
	}

	public void setEnviarA(RegexTextBox enviarA) {
		this.enviarA = enviarA;
	}

	public ListBox getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(ListBox nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	public ListBox getComentarioAnalista() {
		return comentarioAnalista;
	}

	public void setComentarioAnalista(ListBox comentarioAnalista) {
		this.comentarioAnalista = comentarioAnalista;
	}

	public TextArea getNotaAdicional() {
		return notaAdicional;
	}

	public void setNotaAdicional(TextArea notaAdicional) {
		this.notaAdicional = notaAdicional;
	}
	
	public List<ComentarioAnalistaDto> getComentarioAnalistaMensaje() {
		return comentarioAnalistaMensaje;
	}

	public void setComentarioAnalistaMensaje(
			List<ComentarioAnalistaDto> comentarioAnalistaMensaje) {
		this.comentarioAnalistaMensaje = comentarioAnalistaMensaje;
	}

	public List<EstadoSolicitudDto> getOpcionesEstado() {
		return opcionesEstado;
	}

	public void setOpcionesEstado(List<EstadoSolicitudDto> opcionesEstado) {
		this.opcionesEstado = opcionesEstado;
	}
	
	public EstadoSolicitudDto getEstadoPorEstadoText(List<EstadoSolicitudDto> lista, String text) {
		for (int i = 0; i < lista.size(); i++) {
			if(lista.get(i) != null){
				if(lista.get(i).getItemText().equals(text)){
						return lista.get(i);
				}
			}
		}
		return null;
	}
	
	public List<EstadoSolicitudDto> getOpcionesEstadoPorEstadoIds(List<EstadoSolicitudDto> lista, List<Long> opciones) {
		List<EstadoSolicitudDto> estado = new ArrayList<EstadoSolicitudDto>();
		for (int i = 0; i < lista.size(); i++) {
			if(lista.get(i) != null){
				for (int j = 0; j < opciones.size(); j++) {
					
					if(lista.get(i).getCode() == opciones.get(j)){
						estado.add(lista.get(i));
					}
				}
			}
		}
		return estado;
	}

	public List<ComentarioAnalistaDto> getComentarioAnalistaMensajePorEstado(List<ComentarioAnalistaDto> comentarioAnalistaMensaje, Long idEstado) {
		
		List<ComentarioAnalistaDto> comentario = new ArrayList<ComentarioAnalistaDto>();
		
		for (int i = 0; i < comentarioAnalistaMensaje.size(); i++) {
			if(comentarioAnalistaMensaje.get(i) != null){
				if(comentarioAnalistaMensaje.get(i).getEstadoSolicitud().getCode() == idEstado){
					comentario.add(comentarioAnalistaMensaje.get(i));
				}
			}
		}
		return comentario;
	}

	public Label getCantEquipos() {
		return cantEquipos;
	}

	public void setCantEquipos(Label cantEquipos) {
		this.cantEquipos = cantEquipos;
	}

	public CheckBox getEnviar() {
		return enviar;
	}

	public void setEnviar(CheckBox enviar) {
		this.enviar = enviar;
	}

	public CheckBox getRetiraEnSucursal() {
		return retiraEnSucursal;
	}

	public void setRetiraEnSucursal(CheckBox retiraEnSucursal) {
		this.retiraEnSucursal = retiraEnSucursal;
	}

	public RegexTextBox getNumeroSSWeb() {
		return numeroSSWeb;
	}
	
}

package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.util.FormUtils;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.DatePicker;
import ar.com.snoop.gwt.commons.client.widget.datepicker.DatePickerListener;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BuscarSSAnalistaCreditosFilterUIData extends UIData {
	
	private Label tituloLabel = new Label("Búsqueda de SS Cerradas");
	private TextBox nroCuenta;
	private TextBox nroSS;
	private RegexTextBox nroDoc;
	private ListBox estadoCombo;
	private ListBox resultadosCombo;
	private ListBox tipoDocumento;
	private SimpleDatePicker cierreDesde;
	private SimpleDatePicker cierreHasta;
	private SimpleDatePicker creacionDesde;
	private SimpleDatePicker creacionHasta;
	
	private Button buscarButton;
	private Button limpiarButton;
	
	public BuscarSSAnalistaCreditosFilterUIData () {
		
		fields = new ArrayList<Widget>();
		fields.add(nroCuenta = new TextBox());
		fields.add(nroSS = new TextBox());
		fields.add(nroDoc = new RegexTextBox());
		fields.add(tipoDocumento = new ListBox());
		fields.add(estadoCombo = new ListBox(""));
		fields.add(resultadosCombo = new ListBox());
		fields.add(creacionDesde = new SimpleDatePicker(false, true));
		fields.add(creacionHasta = new SimpleDatePicker(false, true));		
		fields.add(cierreDesde = new SimpleDatePicker(false, true));
		fields.add(cierreHasta = new SimpleDatePicker(false, true));

		buscarButton = new Button(Sfa.constant().buscar());
		limpiarButton = new Button(Sfa.constant().limpiar());
		buscarButton.addStyleName("btn-bkg");
		limpiarButton.addStyleName("btn-bkg");
		
		nroSS.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(nroSS != null && !nroSS.getText().equals("")){
					nroCuenta.setEnabled(false);
					creacionDesde.setEnabled(false);
					creacionHasta.setEnabled(false);
					cierreDesde.setEnabled(false);
					cierreHasta.setEnabled(false);
					estadoCombo.setEnabled(false);
					tipoDocumento.setEnabled(false);
					nroDoc.setEnabled(false);
				} else {
					nroCuenta.setEnabled(true);
					creacionDesde.setEnabled(true);
					creacionHasta.setEnabled(true);
					cierreDesde.setEnabled(true);
					cierreHasta.setEnabled(true);
					estadoCombo.setEnabled(true);
					tipoDocumento.setEnabled(true);
					nroDoc.setEnabled(true);
				}
				
			}
		});
		
		nroCuenta.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(nroCuenta != null && !nroCuenta.getText().equals("")){
					nroSS.setEnabled(false);
					tipoDocumento.setEnabled(false);
					nroDoc.setEnabled(false);
					if(!creacionDesde.getTextBox().getText().equals("") || !creacionHasta.getTextBox().getText().equals("")){
						cierreDesde.setEnabled(false);
						cierreHasta.setEnabled(false);
						estadoCombo.setEnabled(false);
					} else if(!cierreDesde.getTextBox().getText().equals("") || !cierreHasta.getTextBox().getText().equals("")){
						creacionDesde.setEnabled(false);
						creacionHasta.setEnabled(false);
						estadoCombo.setEnabled(false);
					} else if(estadoCombo.getSelectedIndex() != 0){
						cierreDesde.setEnabled(false);
						cierreHasta.setEnabled(false);
						creacionDesde.setEnabled(false);
						creacionHasta.setEnabled(false);						
					}
				} else {
					if(creacionDesde.getTextBox().getText().equals("") && creacionHasta.getTextBox().getText().equals("") &&
							cierreDesde.getTextBox().getText().equals("") && cierreHasta.getTextBox().getText().equals("") && 
							estadoCombo.getSelectedItem() == null) {
						nroSS.setEnabled(true);
					}
					tipoDocumento.setEnabled(true);
					nroDoc.setEnabled(true);
				}
			}
		});
		
		nroDoc.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(nroDoc != null && !nroDoc.getText().equals("")){
					nroSS.setEnabled(false);
					nroCuenta.setEnabled(false);
					if(!creacionDesde.getTextBox().getText().equals("") || !creacionHasta.getTextBox().getText().equals("")){
						cierreDesde.setEnabled(false);
						cierreHasta.setEnabled(false);
						estadoCombo.setEnabled(false);
					} else if(!cierreDesde.getTextBox().getText().equals("") || !cierreHasta.getTextBox().getText().equals("")){
						creacionDesde.setEnabled(false);
						creacionHasta.setEnabled(false);
						estadoCombo.setEnabled(false);
					} else if(estadoCombo.getSelectedIndex() != 0){
						cierreDesde.setEnabled(false);
						cierreHasta.setEnabled(false);
						creacionDesde.setEnabled(false);
						creacionHasta.setEnabled(false);						
					}
				} else {
					if(creacionDesde.getTextBox().getText().equals("") && creacionHasta.getTextBox().getText().equals("") &&
							cierreDesde.getTextBox().getText().equals("") && cierreHasta.getTextBox().getText().equals("")) {
						nroSS.setEnabled(true);
					}
					nroCuenta.setEnabled(true);
				}
			}
		});
				
		creacionDesde.addListener(new DatePickerListener() {
			public void onBrowserEvent(Event event) {}
			public void onAccept(DatePicker sender, List<Date> selectedDates) {}
			public void onCancel(DatePicker sender) {}
			public void onSelect(DatePicker sender, Date selectedDate) {
				if(selectedDate != null) {
					combinarCamposFechaCreacion(selectedDate.toString());
				}
			}
			public void onUnSelect(DatePicker sender, Date unSelectedDate) {}
		});
		
		creacionDesde.getTextBox().addValueChangeHandler(new ValueChangeHandler<String>() {			
			public void onValueChange(ValueChangeEvent<String> event) {
				combinarCamposFechaCreacion(event.getValue());				
			}
		});
		
		creacionHasta.addListener(new DatePickerListener() {
			public void onBrowserEvent(Event event) {}
			public void onAccept(DatePicker sender, List<Date> selectedDates) {}
			public void onCancel(DatePicker sender) {}
			public void onSelect(DatePicker sender, Date selectedDate) {
				if(selectedDate != null) {
					combinarCamposFechaCreacion(selectedDate.toString());
				}
			}
			public void onUnSelect(DatePicker sender, Date unSelectedDate) {}
		});
		
		creacionHasta.getTextBox().addValueChangeHandler(new ValueChangeHandler<String>() {			
			public void onValueChange(ValueChangeEvent<String> event) {
				combinarCamposFechaCreacion(event.getValue());				
			}
		});		
		
		cierreDesde.addListener(new DatePickerListener() {
			public void onBrowserEvent(Event event) {}
			public void onAccept(DatePicker sender, List<Date> selectedDates) {}
			public void onCancel(DatePicker sender) {}
			public void onSelect(DatePicker sender, Date selectedDate) {
				if(selectedDate != null) {
					combinarCamposFechaCierre(selectedDate.toString());
				}
			}
			public void onUnSelect(DatePicker sender, Date unSelectedDate) {}
		});
		
		cierreDesde.getTextBox().addValueChangeHandler(new ValueChangeHandler<String>() {			
			public void onValueChange(ValueChangeEvent<String> event) {
				combinarCamposFechaCierre(event.getValue());				
			}
		});
		
		cierreHasta.addListener(new DatePickerListener() {
			public void onBrowserEvent(Event event) {}
			public void onAccept(DatePicker sender, List<Date> selectedDates) {}
			public void onCancel(DatePicker sender) {}
			public void onSelect(DatePicker sender, Date selectedDate) {
				if(selectedDate != null) {
					combinarCamposFechaCierre(selectedDate.toString());
				}
			}
			public void onUnSelect(DatePicker sender, Date unSelectedDate) {}
		});
		
		cierreHasta.getTextBox().addValueChangeHandler(new ValueChangeHandler<String>() {			
			public void onValueChange(ValueChangeEvent<String> event) {
				combinarCamposFechaCierre(event.getValue());				
			}
		});				
		
		estadoCombo.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if(estadoCombo.getSelectedItem() != null){
					nroSS.setEnabled(false);
					creacionDesde.setEnabled(false);
					creacionHasta.setEnabled(false);
					cierreDesde.setEnabled(false);
					cierreHasta.setEnabled(false);					
				} else {
					if(nroCuenta.getText().equals("")) {
						nroSS.setEnabled(true);
					}					
					creacionDesde.setEnabled(true);
					creacionHasta.setEnabled(true);
					cierreDesde.setEnabled(true);
					cierreHasta.setEnabled(true);
				}
			}
		});
		
		limpiarButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				clean();
				nroSS.setEnabled(true);
				tipoDocumento.setEnabled(true);
				nroDoc.setEnabled(true);
				nroCuenta.setEnabled(true);
				creacionDesde.setEnabled(true);
				creacionHasta.setEnabled(true);
				cierreDesde.setEnabled(true);
				cierreHasta.setEnabled(true);	
				estadoCombo.setEnabled(true);
//				cierreDesde.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
//				cierreDesde.getTextBox().setText(dateFormatter.format(DateUtil.getStartDayOfMonth(DateUtil.today())));
//				cierreHasta.setSelectedDate(DateUtil.today());
//				cierreHasta.getTextBox().setText(dateFormatter.format(DateUtil.today()));
			}
		});


		

		
	    SolicitudRpcService.Util.getInstance().getBuscarSSInitializer(true, new DefaultWaitCallback<BuscarSSCerradasInitializer>() {
		  			public void success(BuscarSSCerradasInitializer result) {
						setCombos(result);		
					}
				});
	}
	
	private void setCombos(BuscarSSCerradasInitializer datos){
		if (datos.getCantidadesResultados() != null ){
			for (String cantResultado : datos.getCantidadesResultados()) {
				resultadosCombo.addItem(new ListBoxItemImpl(cantResultado,cantResultado));	
			}
		}
		if (datos.getOpcionesEstado() != null ){
			estadoCombo.addAllItems(datos.getOpcionesEstado());	
		}

		if(datos.getDocumentos() != null) {
			for (Iterator iterator = datos.getDocumentos().iterator(); iterator.hasNext();) {
				TipoDocumentoDto doc = (TipoDocumentoDto) iterator.next();
				tipoDocumento.addItem(doc.getItemText());
			}
		}
	}
	
	public Label getTituloLabel() {
		return tituloLabel;
	}

	public TextBox getRazonSocialTextBox() {
		nroSS.setWidth("90%");
		return nroSS;
	}

	public ListBox getResultadosCombo() {
		return resultadosCombo;
	}

	public Button getBuscarButton() {
		return buscarButton;
	}

	public Button getLimpiarButton() {
		return limpiarButton;
	}
	
	public TextBox getNroCliente() {
		return nroCuenta;
	}

	public TextBox getNroSS() {
		return nroSS;
	}

	public ListBox getEstadoCombo() {
		return estadoCombo;
	}
	
	public Widget getCreacionDesde() {
		Grid datePickerFull = new Grid(1, 2);
		creacionDesde.setWeekendSelectable(true);
//		creacionDesde.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
		datePickerFull.setWidget(0, 0, creacionDesde.getTextBox());
		datePickerFull.setWidget(0, 1, creacionDesde);
		return datePickerFull;
	}

	public Widget getCreacionHasta() {
		Grid datePickerFull = new Grid(1, 2);
		creacionHasta.setWeekendSelectable(true);
//		creacionHasta.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
		datePickerFull.setWidget(0, 0, creacionHasta.getTextBox());
		datePickerFull.setWidget(0, 1, creacionHasta);
		return datePickerFull;
	}

	
	public Widget getCierreDesde() {
		Grid datePickerFull = new Grid(1, 2);
		cierreDesde.setWeekendSelectable(true);
//		cierreDesde.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
//		cierreDesde.getTextBox().setText(dateFormatter.format(DateUtil.getStartDayOfMonth(DateUtil.today())));
		datePickerFull.setWidget(0, 0, cierreDesde.getTextBox());
		datePickerFull.setWidget(0, 1, cierreDesde);
		return datePickerFull;
	}

	public Widget getCierreHasta() {
		Grid datePickerFull = new Grid(1, 2);
		cierreHasta.setWeekendSelectable(true);
//		cierreHasta.setSelectedDate(DateUtil.today());
//		cierreHasta.getTextBox().setText(dateFormatter.format(DateUtil.today()));
		datePickerFull.setWidget(0, 0, cierreHasta.getTextBox());
		datePickerFull.setWidget(0, 1, cierreHasta);
		return datePickerFull;
	}

	public SolicitudServicioCerradaDto getSSCerradaSearch() {
		SolicitudServicioCerradaDto solicitudServicioCerradaDto = new SolicitudServicioCerradaDto();
		solicitudServicioCerradaDto.setNumeroCuenta(nroCuenta.getText());
		solicitudServicioCerradaDto.setNumeroSS(nroSS.getText());
		solicitudServicioCerradaDto.setFechaCierreDesde(cierreDesde.getSelectedDate());
		solicitudServicioCerradaDto.setFechaCierreHasta(cierreHasta.getSelectedDate());
		solicitudServicioCerradaDto.setFechaCreacionDesde(cierreDesde.getSelectedDate());
		solicitudServicioCerradaDto.setFechaCreacionHasta(cierreHasta.getSelectedDate());		
		solicitudServicioCerradaDto.setIdEstadoAprobacionSS(obtenerLong(estadoCombo.getSelectedItemText()));
		solicitudServicioCerradaDto.setCantidadResultados(Long.valueOf(resultadosCombo.getSelectedItem().getItemValue()));
		solicitudServicioCerradaDto.setIdSucursal(ClientContext.getInstance().getVendedor().getIdSucursal());
		solicitudServicioCerradaDto.setPerfiles(BuscarSSAnalistaCreditosUI.obtenerPerfiles());
		solicitudServicioCerradaDto.setPerfilAC(ClientContext.getInstance().getKnownInstance().get(VendedorDto.TIPO_VENDEDOR_ADM_CREDITOS));
		
		if(nroDoc.getText() != null) {
			Integer tipoDoc = tipoDocumento.getSelectedIndex() + 1;
			solicitudServicioCerradaDto.setTipoDoc(tipoDoc);
			solicitudServicioCerradaDto.setNroDoc(nroDoc.getText());
		}
		return solicitudServicioCerradaDto;
	}

	public void combinarCamposFechaCreacion(String valor){
		if(!valor.equals("")) {
			nroSS.setEnabled(false);
			cierreDesde.setEnabled(false);
			cierreHasta.setEnabled(false);
			estadoCombo.setEnabled(false);
			if(!nroCuenta.getText().equals("")) {
				tipoDocumento.setEnabled(false);
				nroDoc.setEnabled(false);
			} else if(!nroDoc.getText().equals("")) {
				nroCuenta.setEnabled(false);
			}
		} else if(creacionDesde.getTextBox().getText().equals("") && creacionHasta.getTextBox().getText().equals("")){
			if(nroCuenta.getText().equals("")){
				nroSS.setEnabled(true);
			}
			cierreDesde.setEnabled(true);
			cierreHasta.setEnabled(true);
			estadoCombo.setEnabled(true);
			if(!nroCuenta.getText().equals("")) {
				tipoDocumento.setEnabled(false);
				nroDoc.setEnabled(false);
			} else if(!nroDoc.getText().equals("")) {
				nroCuenta.setEnabled(false);
			} 		
			
		}
	}
	
	public void combinarCamposFechaCierre(String valor){
		if(!valor.equals("")) {
			nroSS.setEnabled(false);
			creacionDesde.setEnabled(false);
			creacionHasta.setEnabled(false);
			estadoCombo.setEnabled(false);
			if(!nroCuenta.getText().equals("")) {
				tipoDocumento.setEnabled(false);
				nroDoc.setEnabled(false);
			} else if(!nroDoc.getText().equals("")) {
				nroCuenta.setEnabled(false);
			}
		} else if(cierreDesde.getTextBox().getText().equals("") && cierreHasta.getTextBox().getText().equals("")){
			if(nroCuenta.getText().equals("")){
				nroSS.setEnabled(true);
			}
			creacionDesde.setEnabled(true);
			creacionHasta.setEnabled(true);
			estadoCombo.setEnabled(true);
			if(!nroCuenta.getText().equals("")) {
				tipoDocumento.setEnabled(false);
				nroDoc.setEnabled(false);
			} else if(!nroDoc.getText().equals("")) {
				nroCuenta.setEnabled(false);
			} 	
		}
	}
	
	private Long obtenerLong(String string) {
		if ("Pass".equals(string)) {
			return Long.valueOf("2");
		}else if ("Fail".equals(string)) {
			return Long.valueOf("3");
		} else if("En carga".equals(string)) {
			return Long.valueOf("4");
		} else if("A Confirmar".equals(string)) {
			return Long.valueOf("5");
		} else if("Carpeta Incompleta".equals(string)) {
			return Long.valueOf("6");
		}
		return null;
	}
	
	public void cleanAndEnableFields() {
		FormUtils.cleanAndEnableFields(fields);	
	}
	public SimpleDatePicker getCierreDesdeDatePicker() {
		return this.cierreDesde;
	}
	public SimpleDatePicker getCierreHastaDatePicker() {
		return this.cierreHasta;
	}
	public RegexTextBox getNroDoc() {
		return nroDoc;
	}
	public ListBox getTipoDocumento() {
		return tipoDocumento;
	}
	

	
	
}

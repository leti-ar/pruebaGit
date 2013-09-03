package ar.com.nextel.sfa.client.oportunidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.GrupoDocumentoDto;
import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.enums.TipoDocumentoEnum;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author mrial
 * 
 */

public class BuscarOportunidadFilterUIData extends UIData {

	private RegexTextBox numeroClienteTextBox;
	private TextBox razonSocialTextBox;
	private TextBox nombreTextBox;
	private TextBox apellidoTextBox;
	private ListBox tipoDocListBox;
	private RegexTextBox numeroDocumentoTextBox;
	private ListBox estadoOPPListBox;
	private SimpleDatePicker desdeDate;
	private SimpleDatePicker hastaDate;

	private Button buscarButton;
	private Button limpiarButton;

	private SimpleLink crearSS;
	private SimpleLink crearCuenta;

	private DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy");
	private Widget lastWidget;
	private List<String> errorList = new ArrayList();
	
	private Grid datePickerFullDesde = new Grid(1, 2);
	private Grid datePickerFullHasta = new Grid(1, 2);

	public TextBox getNumeroCuentaTextBox() {
		return numeroClienteTextBox;
	}

	public TextBox getRazonSocialTextBox() {
		return razonSocialTextBox;
	}

	public TextBox getNombreTextBox() {
		return nombreTextBox;
	}

	public TextBox getApellidoTextBox() {
		return apellidoTextBox;
	}

	public ListBox getTipoDocListBox() {
		return tipoDocListBox;
	}

	public TextBox getNumeroDocumentoTextBox() {
		return numeroDocumentoTextBox;
	}

	public ListBox getEstadoOPPListBox() {
		return estadoOPPListBox;
	}

	public SimpleLink getCrearSS() {
		return crearSS;
	}

	public void setCrearSS(SimpleLink crearSS) {
		this.crearSS = crearSS;
	}

	public SimpleLink getCrearCuenta() {
		return crearCuenta;
	}

	public void setCrearCuenta(SimpleLink crearCuenta) {
		this.crearCuenta = crearCuenta;
	}

	public Widget getDesdeDate() {
		desdeDate.setWeekendSelectable(true);
		desdeDate.setSelectedDate(DateUtil.getDaysBeforeADate(60l, new Date()));
		desdeDate.getTextBox().setText(dateFormatter.format(DateUtil.getDaysBeforeADate(60l, new Date())));
		datePickerFullDesde.setWidget(0, 0, desdeDate.getTextBox());
		datePickerFullDesde.setWidget(0, 1, desdeDate);
		return datePickerFullDesde;
	}

	public Widget getHastaDate() {
		hastaDate.setWeekendSelectable(true);
		hastaDate.setSelectedDate(DateUtil.today());
		hastaDate.getTextBox().setText(dateFormatter.format(DateUtil.today()));
		datePickerFullHasta.setWidget(0, 0, hastaDate.getTextBox());
		datePickerFullHasta.setWidget(0, 1, hastaDate);
		return datePickerFullHasta;
	}

	public Button getBuscarButton() {
		return buscarButton;
	}

	public Button getLimpiarButton() {
		return limpiarButton;
	}

	public BuscarOportunidadFilterUIData() {
		fields.add(numeroClienteTextBox = new RegexTextBox(RegularExpressionConstants.numerosYPunto));
		numeroClienteTextBox.setMaxLength(25);
		fields.add(razonSocialTextBox = new TextBox());
		razonSocialTextBox.setWidth("70%");
		fields.add(nombreTextBox = new TextBox());
		fields.add(apellidoTextBox = new TextBox());
		fields.add(tipoDocListBox = new ListBox());
		fields.add(numeroDocumentoTextBox = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(10)));
		numeroDocumentoTextBox.setMaxLength(13);
		fields.add(estadoOPPListBox = new ListBox("Todos"));
		this.addFocusListeners(fields);
		fields.add(desdeDate = new SimpleDatePicker(false, true));
		fields.add(hastaDate = new SimpleDatePicker(false, true));


		crearSS = new SimpleLink("Crear SS", "#", true);
		crearCuenta = new SimpleLink("Crear Cuenta", "#", true);

		buscarButton = new Button(Sfa.constant().buscar());
		limpiarButton = new Button(Sfa.constant().limpiar());
		buscarButton.addStyleName("btn-bkg");
		limpiarButton.addStyleName("btn-bkg");

		limpiarButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cleanAndEnableFields();
				desdeDate.setSelectedDate(DateUtil.getDaysBeforeADate(60, new Date()));
				hastaDate.setSelectedDate(new Date());
				estadoOPPListBox.setSelectedIndex(1);
			}
		});
		
		tipoDocListBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent arg0) {
				numeroDocumentoTextBox.setText("");
				if (tipoDocListBox.getSelectedItemId().equals(TipoDocumentoEnum.CUITCUIL.getTipo() + "")) {
					numeroDocumentoTextBox.setPattern(RegularExpressionConstants.cuilCuit);
				} else if (tipoDocListBox.getSelectedItemId().equals(TipoDocumentoEnum.DNI.getTipo() + "")) {
					numeroDocumentoTextBox.setPattern(RegularExpressionConstants.getNumerosLimitado(10));
				}
			}
		});

		/** @author eSalvador: Carga inicial de combos */
		OportunidadNegocioRpcService.Util.getInstance().getBuscarOportunidadInitializer(
				new DefaultWaitCallback<BuscarOportunidadNegocioInitializer>() {
					public void success(BuscarOportunidadNegocioInitializer result) {
						setCombos(result);
					}
				});
	}

	/** Metodo para controlar el comportamiento al hacer foco en los componentes */
	public void addFocusListeners(List<Widget> fields) {
		FocusWidget field;
		for (int i = 0; i < fields.size(); i++) {
			field = (FocusWidget) fields.get(i);
			field.addBlurHandler(new BlurHandler() {
				public void onBlur(BlurEvent event) {
					Widget w = (Widget) event.getSource();
					if (w == numeroClienteTextBox) {
						validateNumeroCliente((RegexTextBox) w);
					} else if (w == numeroDocumentoTextBox) {
						validateNumeroDocumento((RegexTextBox) w);
					} else if (w == razonSocialTextBox || w == nombreTextBox || w == apellidoTextBox) {
						validateRazonSocialNombreApellido((TextBox) w);
					} else if (w == estadoOPPListBox) {
						validateEstadoOpp((ListBox) w);
					}
				}
			});
		}
	}

	private void validateNumeroCliente(RegexTextBox w) {
		if ((estadoOPPListBox.getSelectedIndex() == 0) || (estadoOPPListBox.getSelectedIndex() == 2)) {
		} else {
			if(!"".equals(w.getText())) {
				lastWidget = w;
				disableFields();
				disableLabels();
				setLabelVisibility(0, true);
				numeroClienteTextBox.setEnabled(true);
				numeroClienteTextBox.setReadOnly(false);
				setLabelVisibility(4, true);
				tipoDocListBox.setEnabled(true);
			}  else if (w == lastWidget) {
				cleanAndEnableFields();
			}	
		}
	}
	
	private void validateNumeroDocumento(RegexTextBox w) {
		if ((estadoOPPListBox.getSelectedIndex() == 0) || (estadoOPPListBox.getSelectedIndex() == 2)) {
		} else {
			if(!"".equals(w.getText())) { 
				disableFields();
				disableLabels();
				setLabelVisibility(4, true);
				tipoDocListBox.setEnabled(true);
				setLabelVisibility(5, true);
				numeroDocumentoTextBox.setEnabled(true);
				numeroDocumentoTextBox.setReadOnly(false);
			} else {
				cleanAndEnableFields();
			}	
		}
	}
	
	private void validateRazonSocialNombreApellido(TextBox w) {
		if ((estadoOPPListBox.getSelectedIndex() == 0) || (estadoOPPListBox.getSelectedIndex() == 2)) {			
		} else {	
			if((!"".equals(nombreTextBox.getText())) || (!"".equals(apellidoTextBox.getText())) || (!"".equals(razonSocialTextBox.getText()))) {
				nombreTextBox.setText(nombreTextBox.getText().toUpperCase());
				apellidoTextBox.setText(apellidoTextBox.getText().toUpperCase());
				razonSocialTextBox.setText(razonSocialTextBox.getText().toUpperCase());
				enableFields();
				enableLabels();
				setLabelVisibility(0, false);
				numeroClienteTextBox.setEnabled(false);
				numeroClienteTextBox.setReadOnly(true);
				setLabelVisibility(5, false);
				numeroDocumentoTextBox.setEnabled(false);
				numeroDocumentoTextBox.setReadOnly(true);
			} else {
				cleanAndEnableFields();
			}
		}
	}
	
	private void validateEstadoOpp(ListBox w) {
		if ((w.getSelectedIndex() == 0) || (w.getSelectedIndex() == 2)) {
			enableFields();
			enableLabels();
			setLabelVisibility(0, false);
			numeroClienteTextBox.setEnabled(false);
			numeroClienteTextBox.setReadOnly(true);
			setLabelVisibility(5, false);
			numeroDocumentoTextBox.setEnabled(false);
			numeroDocumentoTextBox.setReadOnly(true);
		} else {
			cleanAndEnableFields();
		}
	}
	

	public void cleanAndEnableFields() {
		super.cleanAndEnableFields();
		getDesdeDate();
		getHastaDate();
		getEstadoOPPListBox().setSelectedItem(new ListBoxItemImpl("Activa", "1"));
	}

	private void setLabelVisibility(int index, boolean enabled) {
		List<Label> labels = getListaLabels();
		if (enabled) {
			labels.get(index).removeStyleName("gwt-labelDisabled");
		} else {
			labels.get(index).addStyleName("gwt-labelDisabled");
		}
	}

	public void enableLabels() {
		List<Label> labels = getListaLabels();
		for (int index = 0; index < labels.size(); index++) {
			labels.get(index).removeStyleName("gwt-labelDisabled");
		}
	}
	
	public void disableLabels() {
		List<Label> labels = getListaLabels();
		for (int index = 0; index < labels.size(); index++) {
			labels.get(index).addStyleName("gwt-labelDisabled");
		}
	}
	
	/**
	 * @author eSalvador
	 **/
	private void setCombos(BuscarOportunidadNegocioInitializer datos) {
		estadoOPPListBox.addAllItems(datos.getEstadoOPP());
		estadoOPPListBox.setSelectedIndex(1);
		tipoDocListBox.addAllItems(datos.getGrupoDocumento());
	}

	public List<String> validarCriterioBusqueda() {
		errorList.clear();
		Date date1 = desdeDate.getSelectedDate();
		Date date2 = DateUtil.getDaysBeforeADate(60, new Date());
		int dif = DateUtil.compareJustDate(date1, date2);
		if (dif == -1) {
			errorList
					.add("El valor del campo “Fecha Desde” es inválido, solo puede buscar oportunidades asignadas en los últimos 60 días");
		}
		return errorList;
	}

	public OportunidadDto getOportunidadSearch() {
		OportunidadDto oportunidadSearchDto = new OportunidadDto();
		oportunidadSearchDto.setNumeroCliente(numeroClienteTextBox.getText());
		oportunidadSearchDto.setRazonSocial(razonSocialTextBox.getText());
		oportunidadSearchDto.setNombre(nombreTextBox.getText());
		oportunidadSearchDto.setApellido(apellidoTextBox.getText());
		if ((numeroDocumentoTextBox == null) || ("".equals(numeroDocumentoTextBox.getText()))) {
			oportunidadSearchDto.setGrupoDocumentoId(null);
		} else {
			oportunidadSearchDto.setGrupoDocumentoId((GrupoDocumentoDto) tipoDocListBox.getSelectedItem());
		}
		oportunidadSearchDto.setNumeroDocumento(numeroDocumentoTextBox.getText());
		oportunidadSearchDto.setEstadoOportunidad((EstadoOportunidadDto) estadoOPPListBox.getSelectedItem());
		oportunidadSearchDto.setFechaDesde(desdeDate.getSelectedDate());
		oportunidadSearchDto.setFechaHasta(hastaDate.getSelectedDate());
		// trae siempre 10 resultados, la pantalla no tiene combo para elegir este valor
		oportunidadSearchDto.setCantidadResultados(10);
		// El offset es a partir de que registro quiero traer
		oportunidadSearchDto.setOffset(0);
		return oportunidadSearchDto;
	}
}

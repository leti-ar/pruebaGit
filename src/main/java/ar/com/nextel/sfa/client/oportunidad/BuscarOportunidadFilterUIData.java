package ar.com.nextel.sfa.client.oportunidad;

import java.util.Date;
import java.util.List;

import ar.com.nextel.model.oportunidades.beans.EstadoOportunidad;
import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.nextel.sfa.client.widget.ValidationListBox;
import ar.com.nextel.sfa.client.widget.ValidationTextBox;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
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
	
	private RegularExpressionConstants regularExpression;
	private DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy");
	private Widget lastWidget;

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

	public Widget getDesdeDate() {
		Grid datePickerFull = new Grid(1, 2);
		desdeDate.setWeekendSelectable(true);
		desdeDate.setSelectedDate(DateUtil.getDaysBeforeADate(60l, new Date()));
		desdeDate.getTextBox().setText(dateFormatter.format(DateUtil.getDaysBeforeADate(60l, new Date())));
		datePickerFull.setWidget(0, 0, desdeDate.getTextBox());
		datePickerFull.setWidget(0, 1, desdeDate);
		return datePickerFull;
	}

	public Widget getHastaDate() {
		Grid datePickerFull = new Grid(1, 2);
		hastaDate.setWeekendSelectable(true);
		hastaDate.setSelectedDate(DateUtil.today());
		hastaDate.getTextBox().setText(dateFormatter.format(DateUtil.today()));
		datePickerFull.setWidget(0, 0, hastaDate.getTextBox());
		datePickerFull.setWidget(0, 1, hastaDate);
		return datePickerFull;
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
		fields.add(nombreTextBox = new TextBox());
		fields.add(apellidoTextBox = new TextBox());
		fields.add(tipoDocListBox = new ListBox());
		fields.add(numeroDocumentoTextBox = new RegexTextBox(RegularExpressionConstants.numeros));
		numeroDocumentoTextBox.setMaxLength(13);
		fields.add(estadoOPPListBox = new ListBox("Todos"));
		fields.add(desdeDate = new SimpleDatePicker(false, true));
		fields.add(hastaDate = new SimpleDatePicker(false, true));
		this.addFocusListeners(fields);

		buscarButton = new Button(Sfa.constant().buscar());
		limpiarButton = new Button(Sfa.constant().limpiar());
		buscarButton.addStyleName("btn-bkg");
		limpiarButton.addStyleName("btn-bkg");

		limpiarButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				cleanAndEnableFields();
				desdeDate.setSelectedDate(DateUtil.getDaysBeforeADate(60, new Date()));
				hastaDate.setSelectedDate(new Date());
				estadoOPPListBox.setSelectedIndex(1);
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
		fields.remove(7);
		fields.remove(7);
		for (int i = 0; i < fields.size(); i++) {
			field = (FocusWidget) fields.get(i);
			field.addFocusListener(new FocusListener() {
				public void onFocus(Widget arg0) {
				}

				public void onLostFocus(Widget w) {
					validateEnabledWidget(w);
				}
			});
		}
	}
	
	private void validateEnabledWidget(Widget w) {
		if (w instanceof RegexTextBox) {
			RegexTextBox box = (RegexTextBox) w;
			validateWidget(box);
		} else if (w instanceof TextBox) {
			TextBox box = (TextBox) w;
			validateWidget(box);
		} else if (w instanceof ListBox) {
			ListBox box = (ListBox) w;
			validateWidget(box);
		}
	}

	private void validateWidget(RegexTextBox box) {
		if (!"".equals(box.getText())) {
			lastWidget = box;
			if (box == numeroClienteTextBox) {
				//inhablitar todos menos TipoDoc
				setLabelVisibility(0, true);
				numeroClienteTextBox.setEnabled(true);
				setLabelVisibility(1, false);
				razonSocialTextBox.setEnabled(false);
				setLabelVisibility(2, false);
				nombreTextBox.setEnabled(false);
				setLabelVisibility(3, false);
				apellidoTextBox.setEnabled(false);
				setLabelVisibility(4, true);
				tipoDocListBox.setEnabled(true);
				setLabelVisibility(5, false);
				numeroDocumentoTextBox.setEnabled(false);
				setLabelVisibility(6, false);
				estadoOPPListBox.setEnabled(false);
				setLabelVisibility(7, false);
				desdeDate.setEnabled(false);
				setLabelVisibility(8, false);
				hastaDate.setEnabled(false);
			} else if (box == numeroDocumentoTextBox) {
				//inhabilitar todos menos TipoDoc
				setLabelVisibility(0, false);
				numeroClienteTextBox.setEnabled(false);
				setLabelVisibility(1, false);
				razonSocialTextBox.setEnabled(false);
				setLabelVisibility(2, false);
				nombreTextBox.setEnabled(false);
				setLabelVisibility(3, false);
				apellidoTextBox.setEnabled(false);
				setLabelVisibility(4, true);
				tipoDocListBox.setEnabled(true);
				setLabelVisibility(5, true);
				numeroDocumentoTextBox.setEnabled(true);
				setLabelVisibility(6, false);
				estadoOPPListBox.setEnabled(false);
				setLabelVisibility(7, false);
				desdeDate.setEnabled(false);
				setLabelVisibility(8, false);
				hastaDate.setEnabled(false);
			}
		} else 
			if (box == lastWidget) {
				enableAll();
			}
	}

	private void validateWidget(TextBox box) {
		if (!"".equals(box.getText())) {
			lastWidget = box;
			if (box == razonSocialTextBox) {
				//deshabilitar NroCliente y Numero 
				setLabelVisibility(0, false);
				numeroClienteTextBox.setEnabled(false);
				setLabelVisibility(1, true);
				razonSocialTextBox.setEnabled(true);
				setLabelVisibility(2, true);
				nombreTextBox.setEnabled(true);
				setLabelVisibility(3, true);
				apellidoTextBox.setEnabled(true);
				setLabelVisibility(4, true);
				tipoDocListBox.setEnabled(true);
				setLabelVisibility(5, false);
				numeroDocumentoTextBox.setEnabled(false);
				setLabelVisibility(6, true);
				estadoOPPListBox.setEnabled(true);
				setLabelVisibility(7, true);
				desdeDate.setEnabled(true);
				setLabelVisibility(8, true);
				hastaDate.setEnabled(true);
			} else if (box == nombreTextBox) {
				//deshabilitar Numero
				setLabelVisibility(0, false);
				numeroClienteTextBox.setEnabled(false);
				setLabelVisibility(1, true);
				razonSocialTextBox.setEnabled(true);
				setLabelVisibility(2, true);
				nombreTextBox.setEnabled(true);
				setLabelVisibility(3, true);
				apellidoTextBox.setEnabled(true);
				setLabelVisibility(4, true);
				tipoDocListBox.setEnabled(true);
				setLabelVisibility(5, false);
				numeroDocumentoTextBox.setEnabled(false);
				setLabelVisibility(6, true);
				estadoOPPListBox.setEnabled(true);
				setLabelVisibility(7, true);
				desdeDate.setEnabled(true);
				setLabelVisibility(8, true);
				hastaDate.setEnabled(true);
			} else if (box == apellidoTextBox) {
				//deshabilitar NroCliente y Numero
				setLabelVisibility(0, false);
				numeroClienteTextBox.setEnabled(false);
				setLabelVisibility(1, true);
				razonSocialTextBox.setEnabled(true);
				setLabelVisibility(2, true);
				nombreTextBox.setEnabled(true);
				setLabelVisibility(3, true);
				apellidoTextBox.setEnabled(true);
				setLabelVisibility(4, true);
				tipoDocListBox.setEnabled(true);
				setLabelVisibility(5, false);
				numeroDocumentoTextBox.setEnabled(false);
				setLabelVisibility(6, true);
				estadoOPPListBox.setEnabled(true);
				setLabelVisibility(7, true);
				desdeDate.setEnabled(true);
				setLabelVisibility(8, true);
				hastaDate.setEnabled(true);
			}
		} else 
			if (box == lastWidget) {
				enableAll();
			}		
	}

	private void validateWidget(ListBox box) {
		//La opcion "Todos" deshabilita NroCliente y Numero
		if (box == estadoOPPListBox) { 
			if (box.getSelectedIndex()==0) {
				setLabelVisibility(0, false);
				numeroClienteTextBox.setEnabled(false);
				setLabelVisibility(1, true);
				razonSocialTextBox.setEnabled(true);
				setLabelVisibility(2, true);
				nombreTextBox.setEnabled(true);
				setLabelVisibility(3, true);
				apellidoTextBox.setEnabled(true);
				setLabelVisibility(4, true);
				tipoDocListBox.setEnabled(true);
				setLabelVisibility(5, false);
				numeroDocumentoTextBox.setEnabled(false);
				setLabelVisibility(6, true);
				estadoOPPListBox.setEnabled(true);
				setLabelVisibility(7, true);
				desdeDate.setEnabled(true);
				setLabelVisibility(8, true);
				hastaDate.setEnabled(true);			
			} else {
				//con las opciones "Activa" y "No Cerrada" 
				enableAll();
			}
				
		}
	}
	
	private void enableAll() {
		setLabelVisibility(0, true);
		numeroClienteTextBox.setEnabled(true);
		setLabelVisibility(1, true);
		razonSocialTextBox.setEnabled(true);
		setLabelVisibility(2, true);
		nombreTextBox.setEnabled(true);
		setLabelVisibility(3, true);
		apellidoTextBox.setEnabled(true);
		setLabelVisibility(4, true);
		tipoDocListBox.setEnabled(true);
		setLabelVisibility(5, true);
		numeroDocumentoTextBox.setEnabled(true);
		setLabelVisibility(5, true);
		estadoOPPListBox.setEnabled(true);
		setLabelVisibility(7, true);
		desdeDate.setEnabled(true);
		setLabelVisibility(8, true);
		hastaDate.setEnabled(true);
	}
	
	private void setLabelVisibility(int index,boolean enabled){
		List<Label> labels = getListaLabels();
		if(enabled){
			labels.get(index).removeStyleName("gwt-labelDisabled");	
		}else{
			labels.get(index).addStyleName("gwt-labelDisabled");	
		}
	}
	
	/**
	 * @author eSalvador
	 **/
	private void setCombos(BuscarOportunidadNegocioInitializer datos) {
		estadoOPPListBox.addAllItems(datos.getEstadoOPP());
		estadoOPPListBox.setSelectedIndex(1);
		tipoDocListBox.addAllItems(datos.getTiposDocumento());
	}

	public OportunidadDto getOportunidadSearch() {
		OportunidadDto oportunidadSearchDto = new OportunidadDto();
		oportunidadSearchDto.setNumeroCliente(numeroClienteTextBox.getText());
		oportunidadSearchDto.setRazonSocial(razonSocialTextBox.getText());
		oportunidadSearchDto.setNombre(nombreTextBox.getText());
		oportunidadSearchDto.setApellido(apellidoTextBox.getText());
		oportunidadSearchDto.setTipoDocumento((TipoDocumentoDto) tipoDocListBox.getSelectedItem());
		oportunidadSearchDto.setNumeroDocumento(numeroDocumentoTextBox.getText());
		oportunidadSearchDto.setEstadoOportunidad((EstadoOportunidadDto)estadoOPPListBox.getSelectedItem());
		oportunidadSearchDto.setFechaDesde(desdeDate.getSelectedDate());
		oportunidadSearchDto.setFechaHasta(hastaDate.getSelectedDate());
		return oportunidadSearchDto;
	}
}

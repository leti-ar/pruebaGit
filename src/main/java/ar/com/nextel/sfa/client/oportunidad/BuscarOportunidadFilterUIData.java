package ar.com.nextel.sfa.client.oportunidad;

import java.util.Date;

import ar.com.nextel.model.oportunidades.beans.EstadoOportunidad;
import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author mrial
 *
 */

public class BuscarOportunidadFilterUIData extends UIData {

	private TextBox numeroClienteTextBox;
	private TextBox razonSocialTextBox;
	private TextBox nombreTextBox;
	private TextBox apellidoTextBox;
	private ListBox tipoDocListBox;
	private TextBox numeroDocumentoTextBox;
	private ListBox estadoOPPListBox;
	private SimpleDatePicker desdeDate;
	private SimpleDatePicker hastaDate;

	private Button buscarButton;
	private Button limpiarButton;
	
	private DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy");

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
		fields.add(numeroClienteTextBox = new TextBox());
		fields.add(razonSocialTextBox = new TextBox());
		fields.add(nombreTextBox = new TextBox());
		fields.add(apellidoTextBox = new TextBox());
		fields.add(tipoDocListBox = new ListBox());
		fields.add(numeroDocumentoTextBox = new TextBox());
		fields.add(estadoOPPListBox = new ListBox("Todos"));
		fields.add(desdeDate = new SimpleDatePicker(false, true));
		fields.add(hastaDate = new SimpleDatePicker(false, true));

		buscarButton = new Button(Sfa.constant().buscar());
		limpiarButton = new Button(Sfa.constant().limpiar());
		buscarButton.addStyleName("btn-bkg");
		limpiarButton.addStyleName("btn-bkg");

		limpiarButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				clean();
				desdeDate.setSelectedDate(DateUtil.getDaysBeforeADate(60, new Date()));
				//desdeDate.getTextBox().setText(dateFormatter.format(DateUtil.getDaysBeforeADate(60, new Date())));
				hastaDate.setSelectedDate(new Date());
				//hastaDate.getTextBox().setText(dateFormatter.format(new Date()));
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

	/**
	 * @author eSalvador
	 **/
	private void setCombos(BuscarOportunidadNegocioInitializer datos) {
		estadoOPPListBox.addAllItems(datos.getEstadoOPP());
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

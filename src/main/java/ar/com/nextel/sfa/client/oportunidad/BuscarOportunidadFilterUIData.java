package ar.com.nextel.sfa.client.oportunidad;

import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.OportunidadSearchDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BuscarOportunidadFilterUIData extends UIData {

	private TextBox numeroCuentaTextBox;
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

	public TextBox getNumeroCuentaTextBox() {
		return numeroCuentaTextBox;
	}

	public void setNumeroCuentaTextBox(TextBox numeroCuentaTextBox) {
		this.numeroCuentaTextBox = numeroCuentaTextBox;
	}

	public TextBox getRazonSocialTextBox() {
		return razonSocialTextBox;
	}

	public void setRazonSocialTextBox(TextBox razonSocialTextBox) {
		this.razonSocialTextBox = razonSocialTextBox;
	}

	public TextBox getNombreTextBox() {
		return nombreTextBox;
	}

	public void setNombreTextBox(TextBox nombreTextBox) {
		this.nombreTextBox = nombreTextBox;
	}

	public TextBox getApellidoTextBox() {
		return apellidoTextBox;
	}

	public void setApellidoTextBox(TextBox apellidoTextBox) {
		this.apellidoTextBox = apellidoTextBox;
	}

	public ListBox getTipoDocListBox() {
		return tipoDocListBox;
	}

	public void setTipoDocListBox(ListBox tipoDocListBox) {
		this.tipoDocListBox = tipoDocListBox;
	}

	public TextBox getNumeroDocumentoTextBox() {
		return numeroDocumentoTextBox;
	}

	public void setNumeroDocumentoTextBox(TextBox numeroDocumentoTextBox) {
		this.numeroDocumentoTextBox = numeroDocumentoTextBox;
	}

	public ListBox getEstadoOPPListBox() {
		return estadoOPPListBox;
	}

	public void setEstadoOPPListBox(ListBox estadoOPPListBox) {
		this.estadoOPPListBox = estadoOPPListBox;
	}

	public SimpleDatePicker getDesdeDate() {
		return desdeDate;
	}

	public void setDesdeDate(SimpleDatePicker desdeDate) {
		this.desdeDate = desdeDate;
	}

	public SimpleDatePicker getHastaDate() {
		return hastaDate;
	}

	public void setHastaDate(SimpleDatePicker hastaDate) {
		this.hastaDate = hastaDate;
	}

	public Button getBuscarButton() {
		return buscarButton;
	}

	public void setBuscarButton(Button buscarButton) {
		this.buscarButton = buscarButton;
	}

	public Button getLimpiarButton() {
		return limpiarButton;
	}

	public void setLimpiarButton(Button limpiarButton) {
		this.limpiarButton = limpiarButton;
	}

	public BuscarOportunidadFilterUIData() {
		fields.add(numeroCuentaTextBox = new TextBox());
		fields.add(razonSocialTextBox = new TextBox());
		fields.add(nombreTextBox = new TextBox());
		fields.add(apellidoTextBox = new TextBox());
		fields.add(tipoDocListBox = new ListBox());
		fields.add(numeroDocumentoTextBox = new TextBox());
		fields.add(estadoOPPListBox = new ListBox());
		fields.add(desdeDate = new SimpleDatePicker(false, true));
		fields.add(hastaDate = new SimpleDatePicker(false, true));

		buscarButton = new Button(Sfa.constant().buscar());
		limpiarButton = new Button(Sfa.constant().limpiar());
		buscarButton.addStyleName("btn-bkg");
		limpiarButton.addStyleName("btn-bkg");

		limpiarButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				clean();
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

	public OportunidadSearchDto getOportunidadSearch() {
		OportunidadSearchDto oportunidadSearchDto = new OportunidadSearchDto();

		oportunidadSearchDto.setNumeroCuenta(numeroCuentaTextBox.getText());
		oportunidadSearchDto.setRazonSocial(razonSocialTextBox.getText());
		oportunidadSearchDto.setNombre(nombreTextBox.getText());
		oportunidadSearchDto.setApellido(apellidoTextBox.getText());
		oportunidadSearchDto.setTipoDocumento((TipoDocumentoDto) tipoDocListBox.getSelectedItem());
		oportunidadSearchDto.setNumeroDocumento(numeroDocumentoTextBox.getText());

		oportunidadSearchDto.setEstadoOPP(estadoOPPListBox.getSelectedItemId().toString());
		oportunidadSearchDto.setDesde(desdeDate.getSelectedDate());
		oportunidadSearchDto.setHasta(hastaDate.getSelectedDate());

		return oportunidadSearchDto;
	}

}

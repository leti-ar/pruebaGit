package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Editor del CuentaSearchDto. Contiene todos los campos (Widgets) necesarios para su edición.
 * 
 * @author jlgperez
 * 
 */
public class BuscarCuentaFilterUIData extends UIData {

	private Label tituloLabel = new Label("Búsqueda de Cuentas");
	private ListBox categoriaCombo;
	private TextBox numeroCuentaTextBox;
	private TextBox razonSocialTextBox;
	private TextBox numeroNextelTextBox;
	private TextBox flotaIdTextBox;
	private TextBox numeroSolicitudServicioTextBox;
	private TextBox responsableTextBox;
	private ListBox tipoDocumentoCombo;
	private TextBox numeroDocumentoTextBox;
	private ListBox predefinidasCombo;
	private ListBox resultadosCombo;

	private Button buscarButton;
	private Button limpiarButton;

	public BuscarCuentaFilterUIData() {

		fields.add(categoriaCombo = new ListBox(""));
		fields.add(numeroCuentaTextBox = new TextBox());
		fields.add(razonSocialTextBox = new TextBox());
		fields.add(numeroNextelTextBox = new TextBox());
		fields.add(flotaIdTextBox = new TextBox());
		fields.add(numeroSolicitudServicioTextBox = new TextBox());
		fields.add(responsableTextBox = new TextBox());
		fields.add(tipoDocumentoCombo = new ListBox());
		fields.add(numeroDocumentoTextBox = new TextBox());
		fields.add(predefinidasCombo = new ListBox(""));
		fields.add(resultadosCombo = new ListBox());

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
		CuentaRpcService.Util.getInstance().getBuscarCuentaInitializer(
				new DefaultWaitCallback<BuscarCuentaInitializer>() {
					public void success(BuscarCuentaInitializer result) {
						setCombos(result);
					}
				});
	}

	/**
	 * @author eSalvador
	 **/
	private void setCombos(BuscarCuentaInitializer datos) {
		categoriaCombo.addAllItems(datos.getCategorias());
		tipoDocumentoCombo.addAllItems(datos.getTiposDocumento());
		predefinidasCombo.addAllItems(datos.getBusquedasPredefinidas());
		if (datos.getCantidadesResultados() != null) {
			for (String cantResultado : datos.getCantidadesResultados()) {
				resultadosCombo.addItem(new ListBoxItemImpl(cantResultado, cantResultado));
			}
		}
	}

	public Label getTituloLabel() {
		return tituloLabel;
	}

	public ListBox getCategoriaCombo() {
		return categoriaCombo;
	}

	public TextBox getNumeroCuentaTextBox() {
		return numeroCuentaTextBox;
	}

	public TextBox getRazonSocialTextBox() {
		razonSocialTextBox.setWidth("90%");
		return razonSocialTextBox;
	}

	public TextBox getNumeroNextelTextBox() {
		return numeroNextelTextBox;
	}

	public TextBox getFlotaIdTextBox() {
		return flotaIdTextBox;
	}

	public TextBox getNumeroSolicitudServicioTextBox() {
		return numeroSolicitudServicioTextBox;
	}

	public TextBox getResponsableTextBox() {
		return responsableTextBox;
	}

	public ListBox getTipoDocumentoCombo() {
		return tipoDocumentoCombo;
	}

	public TextBox getNumeroDocumentoTextBox() {
		return numeroDocumentoTextBox;
	}

	public ListBox getPredefinidasCombo() {
		return predefinidasCombo;
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

	public CuentaSearchDto getCuentaSearch() {
		CuentaSearchDto cuentaSearchDto = new CuentaSearchDto();
		cuentaSearchDto.setCategoria((CategoriaCuentaDto) categoriaCombo.getSelectedItem());
		cuentaSearchDto.setNumeroCuenta(nul(numeroCuentaTextBox.getText()));
		cuentaSearchDto.setRazonSocial(nul(razonSocialTextBox.getText()));
		Long numeroNextelLong = "".equals(numeroNextelTextBox.getText()) ? null : Long
				.valueOf(numeroNextelTextBox.getText());
		cuentaSearchDto.setNumeroNextel(numeroNextelLong);
		cuentaSearchDto.setFlotaId(nul(flotaIdTextBox.getText()));
		Long numeroSolicitudServicioLong = "".equals(numeroSolicitudServicioTextBox.getText()) ? null : Long
				.valueOf(numeroSolicitudServicioTextBox.getText());
		cuentaSearchDto.setNumeroSolicitudServicio(numeroSolicitudServicioLong);
		cuentaSearchDto.setResponsable(nul(responsableTextBox.getText()));
		cuentaSearchDto.setTipoDocumento((TipoDocumentoDto) tipoDocumentoCombo.getSelectedItem());
		cuentaSearchDto.setNumeroDocumento(nul(numeroDocumentoTextBox.getText()));
		cuentaSearchDto.setBusquedaPredefinida((BusquedaPredefinidaDto) predefinidasCombo.getSelectedItem());
		cuentaSearchDto.setCantidadResultados(Integer.parseInt(resultadosCombo.getSelectedItem()
				.getItemValue()));
		cuentaSearchDto.setOffset(0);
		return cuentaSearchDto;
	}

	private String nul(String s) {
		return "".equals(s) ? null : s;
	}

}

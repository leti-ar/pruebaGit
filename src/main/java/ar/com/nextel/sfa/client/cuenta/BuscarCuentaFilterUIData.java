package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.widget.ExcluyenteWidget;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.nextel.sfa.client.widget.ValidationListBox;
import ar.com.nextel.sfa.client.widget.ValidationTextBox;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusWidget;
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
	private ValidationListBox categoriaCombo;
	private ValidationTextBox numeroCuentaTextBox = new ValidationTextBox("[0-9\\.]*") {};
	private ValidationTextBox razonSocialTextBox = new ValidationTextBox("[a-zA-Z0-9\\s]*") {};
	private ValidationTextBox numeroNextelTextBox = new ValidationTextBox("[0-9]*") {};
	private ValidationTextBox flotaIdTextBox = new ValidationTextBox("[0-9]*") {};
	private ValidationTextBox numeroSolicitudServicioTextBox = new ValidationTextBox("[0-9]*") {};
	private ValidationTextBox responsableTextBox = new ValidationTextBox("[a-zA-Z ]*") {};
	private ValidationListBox tipoDocumentoCombo;
	private ValidationTextBox numeroDocumentoTextBox = new ValidationTextBox("[0-9]*") {};
	private ValidationListBox predefinidasCombo;
	private ListBox resultadosCombo;

	private Button buscarButton;
	private Button limpiarButton;

	public BuscarCuentaFilterUIData() {
		configFields();
		buscarButton = new Button(Sfa.constant().buscar());
		limpiarButton = new Button(Sfa.constant().limpiar());
		buscarButton.addStyleName("btn-bkg");
		limpiarButton.addStyleName("btn-bkg");

		limpiarButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				cleanAndEnableFields();
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
	private void configFields() {
		// Combo Categoria
		fields.add(categoriaCombo = new ValidationListBox(""));
		// Numero de Cuenta/Clienta
		numeroCuentaTextBox.setMaxLength(25);
		numeroCuentaTextBox.setExcluyente(true);
		fields.add(numeroCuentaTextBox);
		// Razon Social
		razonSocialTextBox.setMaxLength(100);
		fields.add(razonSocialTextBox);
		razonSocialTextBox.setExcluyente(true);
		// Numero de Nextel
		numeroNextelTextBox.setMaxLength(10);
		fields.add(numeroNextelTextBox);
		numeroNextelTextBox.setExcluyente(true);
		// Id de Flota
		flotaIdTextBox.setMaxLength(11);
		fields.add(flotaIdTextBox);
		flotaIdTextBox.setExcluyente(true);
		// Numero de Solicitud de Servicio.
		numeroSolicitudServicioTextBox.setMaxLength(10);
		fields.add(numeroSolicitudServicioTextBox);
		numeroSolicitudServicioTextBox.setExcluyente(true);
		// Responsable
		responsableTextBox.setMaxLength(19);
		fields.add(responsableTextBox);
		responsableTextBox.setExcluyente(true);
		// Combo Tipo de Documento
		fields.add(tipoDocumentoCombo = new ValidationListBox());
		// Numero de Documento
		fields.add(numeroDocumentoTextBox);
		numeroDocumentoTextBox.setMaxLength(13);
		numeroDocumentoTextBox.setExcluyente(true);
		// Combos
		fields.add(predefinidasCombo = new ValidationListBox());
		fields.add(resultadosCombo = new ListBox());
		this.addFocusListeners(fields);
	}

	/**
	 * @author eSalvador Metodo que agrega comportamiento al captar o perder el foco en los fields.
	 **/
	public void addFocusListeners(List<Widget> fields) {
		FocusWidget field;
		for (int i = 0; i < fields.size(); i++) {
			field = (FocusWidget) fields.get(i);
			field.addFocusListener(new FocusListener() {
				public void onFocus(Widget arg0) {
				}

				public void onLostFocus(Widget w) {
					// Si el field es un TextBox:
					boolean habilitar = true;
					if (w instanceof ValidationTextBox) {
						ValidationTextBox box = (ValidationTextBox) w;
						if (!"".equals(box.getText())) {
							habilitar = false;
						}
					}else if (w instanceof ValidationListBox) {
						//TODO: hacer un metodo que contenga la logica de los listbox.
						validateCombos(w);
						if (true)
							habilitar = false;
					}
					setEnableFields(habilitar);
					((FocusWidget) w).setEnabled(true);
				}
			});
		}
	}

	/**
	 * @author eSalvador 
	 * Metodo privado que implementa la logica particular, para habilitar/deshabilitar combos en la busqueda de cuentas.
	 **/
	private void validateCombos(Widget w) {
	} 
	
	/**
	 * @author eSalvador Metodo privado que hace la implementacion del focusListener, deshabilitando los
	 *         TextBoxsFields que no estan activos.
	 **/
	private void setEnableFields(boolean enabled) {
		for (Widget widget : fields) {
			if (widget instanceof ExcluyenteWidget) {
				((FocusWidget) widget).setEnabled(enabled);
			}
		}
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

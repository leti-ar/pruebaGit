package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.debug.DebugConstants;
import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.GrupoDocumentoDto;
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
	private ValidationTextBox numeroCuentaTextBox = new ValidationTextBox("[0-9\\.]*");
	private ValidationTextBox razonSocialTextBox = new ValidationTextBox("[a-zA-Z0-9\\s\\%]*");
	private ValidationTextBox numeroNextelTextBox = new ValidationTextBox("[0-9]*");
	private ValidationTextBox flotaIdTextBox = new ValidationTextBox("[0-9\\*]*");
	private ValidationTextBox numeroSolicitudServicioTextBox = new ValidationTextBox("[0-9]*");
	private ValidationTextBox responsableTextBox = new ValidationTextBox("[a-zA-Z\\%]*");
	private ValidationListBox grupoDocumentoCombo;
	private ValidationTextBox numeroDocumentoTextBox = new ValidationTextBox("[0-9\\-]*");
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
		buscarButton.ensureDebugId(DebugConstants.BUSQUEDA_CUENTAS_BOTON_BUSCAR);
		flotaIdTextBox.ensureDebugId(DebugConstants.BUSQUEDA_CUENTAS_TEXTBOX_FLOTAID);
		predefinidasCombo.ensureDebugId(DebugConstants.BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS);
		
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
		fields.add(grupoDocumentoCombo = new ValidationListBox());
		// Numero de Documento
		fields.add(numeroDocumentoTextBox);
		numeroDocumentoTextBox.setMaxLength(13);
		numeroDocumentoTextBox.setExcluyente(true);
		// Combos
		fields.add(predefinidasCombo = new ValidationListBox(""));
		fields.add(resultadosCombo = new ListBox());
		this.addFocusListeners(fields);
	}

	/**
	 * @author eSalvador: Metodo que agrega comportamiento al captar o perder el foco en los fields.
	 **/
	public void addFocusListeners(List<Widget> fields) {
		FocusWidget field;
		for (int i = 0; i < fields.size(); i++) {
			field = (FocusWidget) fields.get(i);
			field.addFocusListener(new FocusListener() {
				public void onFocus(Widget arg0) {
				}

				public void onLostFocus(Widget w) {
					boolean habilitar = true;
					validateCriteria(w, habilitar);
				}
			});
		}
	}

	/**
	 * @author eSalvador: Metodo que realiza la validacion inicial de fields, donde diferencia si son textBoxs o listBoxs.
	 **/
	private boolean validateCriteria(Widget w, boolean habilitar) {
		//Si es TextBox:
		if (w instanceof ValidationTextBox) {
			ValidationTextBox box = (ValidationTextBox) w;
			if (!"".equals(box.getText())) {
			   //Si el TextBox NO ES Vacio y pierde el Foco:
				habilitar = false;
			}
			if (!validationTextBoxs(w)){
				return false;
			}
			((FocusWidget) w).setEnabled(true);
		//FIN - Si es TextBox

		//Si es ListBox:
		}else if (w instanceof ValidationListBox) {
			validateListBoxs(w);
			if (true)
				habilitar = false;
		}
		//FIN - Si es ListBox

		return habilitar;
	}
	
	/**
	 * @author eSalvador
	 * Metodo privado que implementa las logicas particulares, para habilitar/deshabilitar textBoxs en la busqueda de cuentas.
	 **/
	private boolean validationTextBoxs(Widget w) {
		boolean flag = true;
		/** Validaciones de TextBoxs especiales combinadas:*/
	  ValidationTextBox box = (ValidationTextBox) w;

	  if ((w == razonSocialTextBox)) {
		  if ("".equals(((ValidationTextBox)responsableTextBox).getText())) {
			  setEnableFields(true);
		  }
	  }
	  
	  if ((w == responsableTextBox)) {
		  if ("".equals(((ValidationTextBox)razonSocialTextBox).getText())) {
			  setEnableFields(true);
		  }
	  }
	  
	  if ("".equals(box.getText()) && (w != responsableTextBox) && (w != razonSocialTextBox)) {
		  setEnableFields(true);
	  }else{  
		  if (w == flotaIdTextBox){
			setEnableFields(false);
			((FocusWidget) w).setEnabled(true);
			setLabelVisibility(3,true);
		}
		
		if (w == numeroSolicitudServicioTextBox){
			setEnableFields(false);
			((FocusWidget) w).setEnabled(true);
			setLabelVisibility(4,true);
		}
		
		if (w == numeroCuentaTextBox){
			setEnableFields(false);
			((FocusWidget) w).setEnabled(true);
			setLabelVisibility(1,true);
		}
		
		if (w == numeroNextelTextBox){
			setEnableFields(false);
			((FocusWidget) w).setEnabled(true);
			setLabelVisibility(2,true);
		}
		
		//Si w es Num.deDoc, el combo de Tipo de Doc. esta Enabled y además alguno de los demas textBox
		//esta deshabilitado, no hacer nada (O sea, saltear. Rerornar FALSE) 
		if (w == numeroDocumentoTextBox && (grupoDocumentoCombo.isEnabled()) && (!numeroSolicitudServicioTextBox.isEnabled())){
			flag = false;
		}
		
		//Si w es numeroDocumentoTextBox y está vacío, y el combo tipoDocumento esta habilitado:
		if ((w == numeroDocumentoTextBox) && (grupoDocumentoCombo.isEnabled())){
			if (!"".equals(box.getText())) {
				setEnableFields(false);
				((FocusWidget) w).setEnabled(true);
				setLabelVisibility(6,true);
				setLabelVisibility(7,true);
				grupoDocumentoCombo.setEnabled(true);
				flag = false;
			}
		}
		
		//Si w es responsableTextBox o razonSocialTextBox: se deshabilitaran todos los campos exceptuando “Responsable”, “Razon Social” y comboCategoria.
		if ((w == responsableTextBox) || (w == razonSocialTextBox)){
			if (!"".equals(box.getText())) {
				setEnableFields(false);
				this.responsableTextBox.setEnabled(true);
				this.razonSocialTextBox.setEnabled(true);
				this.categoriaCombo.setEnabled(true);
				setLabelVisibility(0,true);
				setLabelVisibility(5,true);
				setLabelVisibility(9,true);
				razonSocialTextBox.setText(razonSocialTextBox.getText().toUpperCase());
				responsableTextBox.setText(responsableTextBox.getText().toUpperCase());
				flag = false;
			}else if (("".equals(box.getText()) && (!predefinidasCombo.isEnabled()))) {
				setEnableFields(false);
				this.responsableTextBox.setEnabled(true);
				this.razonSocialTextBox.setEnabled(true);
				this.categoriaCombo.setEnabled(true);
				setLabelVisibility(0,true);
				setLabelVisibility(5,true);
				setLabelVisibility(9,true);
				flag = false;
			}
		}
	  }
		//Si w es Categoria y el combo de Responsable esta Enabled, no hacer nada.
	  
		return flag;
	}
	
	/**
	 * @author eSalvador
	 * Metodo privado que implementa las logicas particulares, para habilitar/deshabilitar combos en la busqueda de cuentas.
	 **/
	private void validateListBoxs(Widget w) {
	  /**Si completa un criterio excluyente el sistema deshabilitará el resto de los campos (excepto Nº Resultados).*/
		
		//Combo Categoría: se deshabilitaran todos los campos exceptuando “Responsable” y “Razon Social”
		if (w == categoriaCombo){
			setEnableFields(false);
			this.responsableTextBox.setEnabled(true);
			this.razonSocialTextBox.setEnabled(true);
			((FocusWidget) w).setEnabled(true);
			setLabelVisibility(0,true);
			setLabelVisibility(9,true);
			setLabelVisibility(5,true);
		}
		
		//Combo Tipo de documento: no deshabilita el campo Número de documento
		if (w == grupoDocumentoCombo){
			setEnableFields(false);
			this.numeroDocumentoTextBox.setEnabled(true);
			((FocusWidget) w).setEnabled(true);
			setLabelVisibility(6,true);
			setLabelVisibility(7,true);
		}
		
		//Combo predefinidasCombo: ??
		if (w == predefinidasCombo){
			setEnableFields(false);
			((FocusWidget) w).setEnabled(true);
			setLabelVisibility(8,true);
		}
		
		//Combo predefinidasCombo == ""
		if ((w == predefinidasCombo) && (predefinidasCombo.getSelectedIndex() == 0)){
			setEnableFields(true);
		}
		
		//Si Combo Categoría selecciona vacio, y responsableTextBox Y razonSocialTextBox ambos estan vacios, se habilitan todos.
		if ((w == categoriaCombo) && (categoriaCombo.isItemSelected(0))){
			ValidationTextBox boxRS = (ValidationTextBox) razonSocialTextBox;
			ValidationTextBox boxRE = (ValidationTextBox) responsableTextBox;
			if (("".equals(boxRS.getText())) && ("".equals(boxRE.getText()))) {
				setEnableFields(true);
			}
		}
		
		//Combo Nº Resultados: Siempre habilitado!!!
		  //TODO nada!
 
	}
	
	public List<String> validatePreSearch() {
		boolean vacio = true;
		List<String> list = new ArrayList<String>();
		
		//Valida que todos los campos TextBoxs no sean vacios:
		for (Widget fieldsfieldTextBox : fields) {
			if (fieldsfieldTextBox instanceof ValidationTextBox){
				if (!"".equals(((ValidationTextBox) fieldsfieldTextBox).getText())){
					vacio = false;
					break;
				}
			}
		}
		
		if (vacio) {
			//Valida que todos los campos ListBoxs no sean vacios (excepto tipoDocumento y cantResultados que no tienen valor nulo para cargar):
			for (Widget fieldListBox : fields) {
				if (fieldListBox instanceof ValidationListBox){
					if ((fieldListBox != resultadosCombo) && (fieldListBox != grupoDocumentoCombo)){
						if (((ValidationListBox) fieldListBox).getSelectedItemText() != null){
							vacio = false;
							break;
						}
					}
				}
			}
		}

		if(vacio){
			list.add("Por favor ingrese por lo menos un criterio de busqueda.");
		}else{
			if(flotaIdTextBox.isEnabled() && !flotaIdTextBox.getText().equals(""))  {
				if(!validaFlotaId(flotaIdTextBox)){
					list.add("Formato incorrecto de Flota*Id.");
				}
			}
		}
		return list;
	}
	
	/**
	 * @author eSalvador
	 **/
	private boolean validaFlotaId(ValidationTextBox flota) {
		String flotaModelo = new String("[0-9]{3,5}[\\*]{1}[0-9]{1,5}");
		return flota.validatePattern(flotaModelo);
	}
	
	/**
	 * @author eSalvador Metodo privado que hace la implementacion del focusListener, deshabilitando los
	 *   TextBoxsFields que no estan activos.
	 **/
	private void setEnableFields(boolean enabled) {
		for (Widget widget : fields) {
			if (widget instanceof ExcluyenteWidget) {
				((FocusWidget) widget).setEnabled(enabled);
				for (int i = 0; i < getListaLabels().size(); i++) {
					if (enabled){
						getListaLabels().get(i).removeStyleName("gwt-labelDisabled");	
					}else{
						getListaLabels().get(i).addStyleName("gwt-labelDisabled");
					}
				}
			}
		}
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
	private void setCombos(BuscarCuentaInitializer datos) {
		categoriaCombo.addAllItems(datos.getCategorias());
		grupoDocumentoCombo.addAllItems(datos.getGrupoDocumento());
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

	public ValidationListBox getCategoriaCombo() {
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

	public ValidationListBox getGrupoDocumentoCombo() {
		return grupoDocumentoCombo;
	}

	public TextBox getNumeroDocumentoTextBox() {
		return numeroDocumentoTextBox;
	}

	public ValidationListBox getPredefinidasCombo() {
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
		
		if ((numeroDocumentoTextBox == null) || ("".equals(numeroDocumentoTextBox.getText()))){
			cuentaSearchDto.setGrupoDocumentoId(null);
		}else{
			cuentaSearchDto.setGrupoDocumentoId((GrupoDocumentoDto) grupoDocumentoCombo.getSelectedItem());
		}
		
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

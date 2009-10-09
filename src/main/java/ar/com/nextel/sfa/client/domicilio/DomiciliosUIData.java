package ar.com.nextel.sfa.client.domicilio;

import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoTipoDomicilioDto;
import ar.com.nextel.sfa.client.dto.NormalizarCPAResultDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProvinciaDto;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.nextel.sfa.client.widget.ValidationTextBox;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author eSalvador
 **/
public class DomiciliosUIData extends UIData {

	private DomiciliosCuentaDto domicilio;
	private boolean tienePrincipalFacturacion = false;
	private boolean tienePrincipalEntrega = false;

	private ValidationTextBox cpa = new ValidationTextBox("[0-9a-zA-Z]*");
	private ValidationTextBox numero = new ValidationTextBox("[0-9a-zA-Z]*");
	private TextBox calle = new TextBox();
	private TextBox piso = new TextBox();
	private ValidationTextBox codigoPostal = new ValidationTextBox("[0-9]*");
	private ValidationTextBox departamento = new ValidationTextBox("[0-9a-zA-Z]*");
	private TextBox entreCalle = new TextBox();
	private ValidationTextBox manzana = new ValidationTextBox("[0-9a-zA-Z]*");
	private TextBox ycalle = new TextBox();
	private ValidationTextBox torre = new ValidationTextBox("[0-9a-zA-Z]*");
	private ValidationTextBox unidadFuncional = new ValidationTextBox("[0-9a-zA-Z]*");
	private TextArea observaciones = new TextArea();
	private ValidationTextBox localidad = new ValidationTextBox("[0-9a-zA-Z\\s]*");
	private ValidationTextBox partido = new ValidationTextBox("[0-9a-zA-Z\\s]*");
	private PersonaDto persona = new PersonaDto();
	private ListBox facturacion = new ListBox();
	private ListBox entrega = new ListBox();
	private ListBox provincia = new ListBox();

	// Boolean noNormalizar;
	private CheckBox validado = new CheckBox();
	private Label nombreUsuarioUltimaModificacion = new Label();
	private Label fechaUltimaModificacion = new Label();

	public DomiciliosUIData() {
		configFields();
		fields.add(cpa);
		fields.add(calle);
		fields.add(numero);
		fields.add(piso);
		fields.add(departamento);
		fields.add(unidadFuncional);
		fields.add(torre);
		fields.add(manzana);
		fields.add(entreCalle);
		fields.add(ycalle);
		fields.add(localidad);
		fields.add(codigoPostal);
		fields.add(provincia);
		fields.add(partido);
		fields.add(entrega);
		fields.add(facturacion);
		fields.add((Widget) validado);
		fields.add(observaciones);
		this.addFocusListeners(fields);
		entrega.addAllItems(EstadoTipoDomicilioDto.getListBoxItems());
		facturacion.addAllItems(EstadoTipoDomicilioDto.getListBoxItems());

		int i = 1;
		for (Widget field : fields) {
			((FocusWidget) field).setTabIndex(i++);
		}
	}

	public void cargarListBox(List<DomiciliosCuentaDto> listaDomicilios, DomiciliosCuentaDto domicilioAEditar) {
		if (listaDomicilios != null) {
			if ((entrega.getItemCount() != 0) && (facturacion.getItemCount() != 0)) {
				entrega.clear();
				facturacion.clear();
			}
			if (domicilioAEditar != null) {
				Long idPrincipal = EstadoTipoDomicilioDto.PRINCIPAL.getId();
				tienePrincipalEntrega = !idPrincipal.equals(domicilioAEditar.getIdEntrega())
						&& tienePrincipalEntrega;
				tienePrincipalFacturacion = !idPrincipal.equals(domicilioAEditar.getIdFacturacion())
						&& tienePrincipalFacturacion;
			} else {
				tienePrincipalEntrega = containsPpalEntrega(listaDomicilios);
				tienePrincipalFacturacion = containsPpalEntrega(listaDomicilios);
			}
		} else {
			tienePrincipalEntrega = false;
			tienePrincipalFacturacion = false;
		}
		loadTipoDomicilioListBox(entrega, !tienePrincipalEntrega);
		loadTipoDomicilioListBox(facturacion, !tienePrincipalFacturacion);
	}

	public void loadTipoDomicilioListBox(ListBox listBox, boolean selectPrincipal) {
		List<ListBoxItem> estados = EstadoTipoDomicilioDto.getListBoxItems();
		for (int i = selectPrincipal ? 0 : 1; i < estados.size(); i++) {
			listBox.addItem(estados.get(i));
		}
	}

	private boolean containsPpalEntrega(List<DomiciliosCuentaDto> listaDomicilios) {
		for (Iterator iter = listaDomicilios.iterator(); iter.hasNext();) {
			DomiciliosCuentaDto domicilioCuenta = (DomiciliosCuentaDto) iter.next();
			if (EstadoTipoDomicilioDto.PRINCIPAL.getId().equals(domicilioCuenta.getIdEntrega())) {
				return true;
			}
		}
		return false;
	}

	private boolean containsPpalFacturacion(List<DomiciliosCuentaDto> listaDomicilios) {
		for (Iterator iter = listaDomicilios.iterator(); iter.hasNext();) {
			DomiciliosCuentaDto domicilioCuenta = (DomiciliosCuentaDto) iter.next();
			if (EstadoTipoDomicilioDto.PRINCIPAL.getId().equals(domicilioCuenta.getIdFacturacion())) {
				return true;
			}
		}
		return false;
	}

	public void setDomicilio(DomiciliosCuentaDto domicilio) {
		if (domicilio == null) {
			this.domicilio = new DomiciliosCuentaDto();
		} else {
			this.domicilio = domicilio;
			/** TODO: Terminar este mapeo! */
			calle.setText(domicilio.getCalle());
			codigoPostal.setText(domicilio.getCodigo_postal());
			localidad.setText(domicilio.getLocalidad());
			partido.setText(domicilio.getPartido());
			numero.setText(domicilio.getNumero() != null ? "" + domicilio.getNumero() : "");
			piso.setText(domicilio.getPiso());
			departamento.setText(domicilio.getDepartamento());
			entreCalle.setText(domicilio.getEntre_calle());
			manzana.setText(domicilio.getManzana());
			ycalle.setText(domicilio.getY_calle());
			cpa.setText(domicilio.getCpa());
			torre.setText(domicilio.getTorre());
			unidadFuncional.setText(domicilio.getUnidad_funcional());
			validado.setValue(domicilio.getValidado());
			observaciones.setText(domicilio.getObservaciones());
			nombreUsuarioUltimaModificacion.setText(domicilio.getNombre_usuario_ultima_modificacion());
			fechaUltimaModificacion.setText(domicilio.getFecha_ultima_modificacion());
			entrega.selectByValue("" + domicilio.getIdEntrega());
			facturacion.selectByValue("" + domicilio.getIdFacturacion());
		}
	}

	public DomiciliosCuentaDto getDomicilio() {
		domicilio.setCalle(calle.getText());
		domicilio.setEntre_calle(entreCalle.getText());
		domicilio.setY_calle(ycalle.getText());
		domicilio.setCodigo_postal(codigoPostal.getText());
		domicilio.setLocalidad(localidad.getText());
		domicilio.setPartido(partido.getText());
		domicilio.setCpa(cpa.getText());
		domicilio.setDepartamento(departamento.getText());
		domicilio.setManzana(manzana.getText());
		domicilio.setNumero(numero.getText());
		domicilio.setObservaciones(observaciones.getText());
		domicilio.setPiso(piso.getText());
		domicilio.setProvincia((ProvinciaDto) provincia.getSelectedItem());
		domicilio.setTorre(torre.getText());
		domicilio.setUnidad_funcional(unidadFuncional.getText());
		domicilio.setValidado(validado.getValue());
		domicilio.setNombre_usuario_ultima_modificacion(nombreUsuarioUltimaModificacion.getText());
		domicilio.setFecha_ultima_modificacion(fechaUltimaModificacion.getText());
		domicilio.setIdEntrega(Long.parseLong(entrega.getSelectedItem().getItemValue()));
		domicilio.setIdFacturacion(Long.parseLong(facturacion.getSelectedItem().getItemValue()));
		return domicilio;
	}

	/**
	 * @author eSalvador
	 **/
	private void configFields() {
		numero.setMaxLength(5);
		numero.setExcluyente(true);
		departamento.setMaxLength(3);
		unidadFuncional.setMaxLength(3);
		torre.setMaxLength(2);
		manzana.setMaxLength(2);
		localidad.setMaxLength(40);
		partido.setMaxLength(40);
		cpa.setMaxLength(10);
		codigoPostal.setMaxLength(5);
		piso.setMaxLength(3);
	}

	/**
	 * @author eSalvador: Metodo que agrega comportamiento al captar o perder el foco en los fields.
	 **/
	public void addFocusListeners(List<Widget> fields) {
		FocusWidget field;
		for (int i = 0; i < fields.size(); i++) {
			field = (FocusWidget) fields.get(i);
			field.addBlurHandler(new BlurHandler() {
				public void onBlur(BlurEvent event) {
					Widget sender = (Widget) event.getSource();
					validateFields(sender);
					if (sender instanceof TextBox) {
						((TextBox) sender).setText(((TextBox) sender).getText().trim().toUpperCase());
					}
				}
			});
		}
	}

	private void validateFields(Widget w) {
		/** TODO: Terminar validacion de fields del DomicilioUI. */
		if (w == cpa) {
			if ((!cpa.getText().equals("")) && (cpa.getText().length() > 4)) {
				// Aca llama al ServiceRpcCuenta
				CuentaRpcService.Util.getInstance().getDomicilioPorCPA(cpa.getText().toUpperCase(),
						new DefaultWaitCallback<NormalizarCPAResultDto>() {
							public void success(NormalizarCPAResultDto domicilioNormalizado) {
								codigoPostal.setText(domicilioNormalizado.getCodigoPostal());
								calle.setText(domicilioNormalizado.getCalle());
								numero.setFocus(true);
								localidad.setText(domicilioNormalizado.getLocalidad());
								partido.setText(domicilioNormalizado.getPartido());
								if (domicilioNormalizado.getIdProvincia() != null) {
									provincia.selectByValue(domicilioNormalizado.getIdProvincia().toString());
								}
							}
						});
			}
		}
		if (w == numero) {
			if (numero.getText().length() == 0) {
				MessageDialog.getInstance().setDialogTitle("SFA - Alert");
				MessageDialog.getInstance().showAceptar("Debe ingresar un Numero correcto.",
						MessageDialog.getCloseCommand());
			}
		}
	}

	public List<String> validarCamposObligatorios() {
		GwtValidator validator = new GwtValidator();
		validator.addTarget(numero).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", Sfa.constant().numero()));
		validator.addTarget(calle).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", Sfa.constant().calle()));
		validator.addTarget(localidad).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", Sfa.constant().localidad()));
		validator.addTarget(codigoPostal).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", Sfa.constant().cp()));
		validator.fillResult();
		return validator.getErrors();
	}

	public TextBox getCalle() {
		calle.setWidth("300px");
		return calle;
	}

	public TextBox getNumero() {
		numero.setWidth("70px");
		return numero;
	}

	public TextBox getPiso() {
		piso.setWidth("30px");
		return piso;
	}

	public TextBox getCodigoPostal() {
		return codigoPostal;
	}

	public TextBox getDepartamento() {
		departamento.setWidth("30px");
		return departamento;
	}

	public TextBox getEntreCalle() {
		return entreCalle;
	}

	public TextBox getManzana() {
		manzana.setWidth("30px");
		return manzana;
	}

	public TextBox getYcalle() {
		return ycalle;
	}

	public TextBox getCpa() {
		cpa.setWidth("120px");
		return cpa;
	}

	public TextBox getTorre() {
		torre.setWidth("30px");
		return torre;
	}

	public TextBox getUnidadFuncional() {
		unidadFuncional.setWidth("30px");
		return unidadFuncional;
	}

	public TextArea getObservaciones() {
		observaciones.setWidth("615px");
		return observaciones;
	}

	public TextBox getLocalidad() {
		return localidad;
	}

	public TextBox getPartido() {
		return partido;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public CheckBox getValidado() {
		return validado;
	}

	public ListBox getFacturacion() {
		return facturacion;
	}

	public ListBox getEntrega() {
		return entrega;
	}

	public Label getNombreUsuarioUltimaModificacion() {
		nombreUsuarioUltimaModificacion.setWidth("60px");
		return nombreUsuarioUltimaModificacion;
	}

	public Label getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public ListBox getProvincia() {
		return provincia;
	}

	public boolean isTienePrincipalFacturacion() {
		return tienePrincipalFacturacion;
	}

	public boolean isTienePrincipalEntrega() {
		return tienePrincipalEntrega;
	}

}

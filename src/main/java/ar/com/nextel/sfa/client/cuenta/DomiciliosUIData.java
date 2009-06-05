package ar.com.nextel.sfa.client.cuenta;

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
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusListener;
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
	TextBox calle = new TextBox();
	ValidationTextBox numero = new ValidationTextBox("[0-9a-zA-Z]*");
	TextBox piso = new TextBox();
	ValidationTextBox codigoPostal = new ValidationTextBox("[0-9]*");
	ValidationTextBox departamento = new ValidationTextBox("[0-9a-zA-Z]*");
	TextBox entreCalle = new TextBox();
	ValidationTextBox manzana = new ValidationTextBox("[0-9a-zA-Z]*");
	TextBox puerta = new TextBox();
	TextBox ycalle = new TextBox();
	ValidationTextBox cpa = new ValidationTextBox("[0-9a-zA-Z]*");
	ValidationTextBox torre = new ValidationTextBox("[0-9a-zA-Z]*");
	ValidationTextBox unidadFuncional = new ValidationTextBox("[0-9a-zA-Z]*");
	TextArea observaciones = new TextArea();
	ValidationTextBox localidad = new ValidationTextBox("[0-9a-zA-Z]*");
	ValidationTextBox partido = new ValidationTextBox("[0-9a-zA-Z]*");
	PersonaDto persona = new PersonaDto();
	ListBox facturacion = new ListBox();
	ListBox entrega = new ListBox();
	ListBox provincia = new ListBox();

	// Boolean noNormalizar;

	CheckBox validado = new CheckBox();
	TextBox codigoFNCL = new TextBox();
	CheckBox enCarga = new CheckBox();
	Label nombreUsuarioUltimaModificacion = new Label();
	Label fechaUltimaModificacion = new Label();

	public DomiciliosUIData() {
		configFields();
		fields.add(calle);
		fields.add(numero);
		fields.add(piso);
		fields.add(codigoPostal);
		fields.add(departamento);
		fields.add(entreCalle);
		fields.add(manzana);
		fields.add(puerta);
		fields.add(ycalle);
		fields.add(cpa);
		fields.add(torre);
		fields.add(unidadFuncional);
		fields.add(observaciones);
		fields.add(localidad);
		fields.add(partido);
		fields.add((Widget) validado);
		fields.add(codigoFNCL);
		fields.add(enCarga);
		fields.add(entrega);
		fields.add(facturacion);
		fields.add(provincia);
		this.addFocusListeners(fields);

		entrega.addAllItems(EstadoTipoDomicilioDto.getListBoxItems());
		facturacion.addAllItems(EstadoTipoDomicilioDto.getListBoxItems());
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
			numero.setText(domicilio.getNumero().toString());
			piso.setText(domicilio.getPiso());
			departamento.setText(domicilio.getDepartamento());
			entreCalle.setText(domicilio.getEntre_calle());
			manzana.setText(domicilio.getManzana());
			puerta.setText(domicilio.getPuerta());
			ycalle.setText(domicilio.getY_calle());
			cpa.setText(domicilio.getCpa());
			torre.setText(domicilio.getTorre());
			unidadFuncional.setText(domicilio.getUnidad_funcional());
			if (domicilio.getValidado()) {
				validado.setChecked(true);
			} else {
				validado.setChecked(false);
			}
			observaciones.setText(domicilio.getObservaciones());
			nombreUsuarioUltimaModificacion.setText(domicilio.getNombre_usuario_ultima_modificacion());
			fechaUltimaModificacion.setText(domicilio.getFecha_ultima_modificacion());

			Long idEntrega = domicilio.getIdEntrega();
			Long idFacturacion = domicilio.getIdFacturacion();

			// VER EstadoTipoDomicilioDto
			entrega.selectByValue("" + idEntrega);
			facturacion.selectByValue("" + idFacturacion);

		}
	}

//	public DomiciliosCuentaDto getDomicilioCopiado() {
//		DomiciliosCuentaDto domicilioCopiado = new DomiciliosCuentaDto();
//		domicilioCopiado.setCalle(calle.getText());
//		domicilioCopiado.setEntre_calle(entreCalle.getText());
//		domicilioCopiado.setY_calle(ycalle.getText());
//		domicilioCopiado.setCodigo_postal(codigoPostal.getText());
//		domicilioCopiado.setLocalidad(localidad.getText());
//		domicilioCopiado.setPartido(partido.getText());
//		domicilioCopiado.setCpa(cpa.getText());
//		domicilioCopiado.setDepartamento(departamento.getText());
//		domicilioCopiado.setManzana(manzana.getText());
//		if (!"".equals(numero.getText())) {
//			domicilioCopiado.setNumero(Long.parseLong(numero.getText()));
//		}
//		domicilioCopiado.setObservaciones(observaciones.getText());
//		domicilioCopiado.setPiso(piso.getText());
//		domicilioCopiado.setProvincia((ProvinciaDto) provincia.getSelectedItem());
//		domicilioCopiado.setPuerta(puerta.getText());
//		domicilioCopiado.setTorre(torre.getText());
//		domicilioCopiado.setValidado(validado.isChecked());
//		domicilioCopiado.setNombre_usuario_ultima_modificacion(nombreUsuarioUltimaModificacion.getText());
//		domicilioCopiado.setFecha_ultima_modificacion(fechaUltimaModificacion.getText());
//		domicilioCopiado.setIdEntrega(Long.parseLong(entrega.getSelectedItem().getItemValue()));
//		domicilioCopiado.setIdFacturacion(Long.parseLong(facturacion.getSelectedItem().getItemValue()));
//
//		// Esto del locked se setea en FALSE para que le permita la edicion del mismo, hasta que se guarde la
//		// cuenta, cuando se setea en TRUE.
//		// domicilioCopiado.setLocked(false);
//		//
//		return domicilioCopiado;
//	}

	public DomiciliosCuentaDto getDomicilio() {
		/** TODO: Deberia hacer alguna validacion?? */
		domicilio.setCalle(calle.getText());
		domicilio.setEntre_calle(entreCalle.getText());
		domicilio.setY_calle(ycalle.getText());
		domicilio.setCodigo_postal(codigoPostal.getText());
		domicilio.setLocalidad(localidad.getText());
		domicilio.setPartido(partido.getText());
		domicilio.setCpa(cpa.getText());
		domicilio.setDepartamento(departamento.getText());
		domicilio.setManzana(manzana.getText());
		if (!"".equals(numero.getText())) {
			domicilio.setNumero(Long.parseLong(numero.getText()));
		}
		domicilio.setObservaciones(observaciones.getText());
		domicilio.setPiso(piso.getText());
		domicilio.setProvincia((ProvinciaDto) provincia.getSelectedItem());
		domicilio.setPuerta(puerta.getText());
		domicilio.setTorre(torre.getText());
		domicilio.setUnidad_funcional(unidadFuncional.getText());
		domicilio.setValidado(validado.isChecked());
		domicilio.setNombre_usuario_ultima_modificacion(nombreUsuarioUltimaModificacion.getText());
		domicilio.setFecha_ultima_modificacion(fechaUltimaModificacion.getText());
		domicilio.setIdEntrega(Long.parseLong(entrega.getSelectedItem().getItemValue()));
		domicilio.setIdFacturacion(Long.parseLong(facturacion.getSelectedItem().getItemValue()));
		return domicilio;
	}


	/**
	 * @author eSalvador 
	 **/
	private void configFields(){
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
					validateFields(w);
				}
			});
		}
	}

	private Command getComandoAceptarAlert() {
		Command comandoAceptar = new Command() {
			public void execute() {
				MessageDialog.getInstance().hide();
			}
		};
		return comandoAceptar;
	}
	
	private void validateFields(Widget w){
	/**TODO: Terminar validacion de fields del DomicilioUI. */
		if(w == cpa){
			if ((!cpa.getText().equals("")) && (cpa.getText().length() > 4)){
			//Aca llama al ServiceRpcCuenta
			CuentaRpcService.Util.getInstance().getDomicilioPorCPA(cpa.getText(),
					new DefaultWaitCallback<NormalizarCPAResultDto>() {
						public void success(NormalizarCPAResultDto domicilioNormalizado) {
							codigoPostal.setText(domicilioNormalizado.getCodigoPostal());
							calle.setText(domicilioNormalizado.getCalle());
							localidad.setText(domicilioNormalizado.getLocalidad());
							partido.setText(domicilioNormalizado.getPartido());
							if (domicilioNormalizado.getIdProvincia() != null){
								provincia.selectByValue(domicilioNormalizado.getIdProvincia().toString());
							}
						}
			});
		}}
		if (w == numero) {
			if (numero.getText().length() == 0) {
				MessageDialog.getInstance().setDialogTitle("SFA - Alert");
				MessageDialog.getInstance().showAceptar("Debe ingresar un Numero correcto.",
						getComandoAceptarAlert());
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

	public TextBox getPuerta() {
		return puerta;
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

	public TextBox getCodigoFNCL() {
		return codigoFNCL;
	}

	public CheckBox getEnCarga() {
		return enCarga;
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

	public void hideLabelsParaContactos() {
		facturacion.setVisible(false);
		entrega.setVisible(false);
		validado.setVisible(false);
		nombreUsuarioUltimaModificacion.setVisible(false);
		fechaUltimaModificacion.setVisible(false);
	}
}

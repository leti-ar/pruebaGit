package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author mrial
 * 
 */

public class CuentaUIData extends UIData {

	private ListBox tipoDocumento    = new ListBox();
	private ListBox sexo             = new ListBox();
	private ListBox contribuyente    = new ListBox();
	private ListBox rubro            = new ListBox();
	private ListBox formaPago        = new ListBox();
	private ListBox claseCliente     = new ListBox();
	
	private TextBox numeroDocumento  = new TextBox();
	private TextBox razonSocial      = new TextBox();
	private TextBox nombre           = new TextBox();
	private TextBox apellido         = new TextBox();
	private TextBox categoria        = new TextBox();
	private TextBox cicloFacturacion = new TextBox();
	private TextBox provedorAnterior = new TextBox();
	private TextBox emailPersonal    = new TextBox();
	private TextBox emailLaboral     = new TextBox();
	
	private TextArea observaciones = new TextArea();

	private SimpleDatePicker fechaNacimiento = new SimpleDatePicker(false);
	
	private Label veraz = new Label("Si puede");
	private Label usuario;
	private Label fechaCreacion;

	private SimpleLink guardar  = new SimpleLink(Sfa.constant().guardar() , "#", true);
	private SimpleLink crearSS  = new SimpleLink(Sfa.constant().crear()+Sfa.constant().whiteSpace()+Sfa.constant().ss(), "#", true);
	private SimpleLink agregar  = new SimpleLink(Sfa.constant().agregar() , "#", true);
	private SimpleLink cancelar = new SimpleLink(Sfa.constant().cancelar(), "#", true);

	PersonaDto persona = new PersonaDto();

	public CuentaUIData() {
		observaciones.addStyleName("textAreaCuentaData");
		fields.add(tipoDocumento);
		fields.add(numeroDocumento);
		fields.add(razonSocial);
		fields.add(nombre);
		fields.add(apellido);
		fields.add(sexo);
		fields.add(fechaNacimiento);
		fields.add(contribuyente);
		fields.add(provedorAnterior);
		fields.add(rubro);
		fields.add(claseCliente);
		fields.add(categoria);
		fields.add(cicloFacturacion);
		fields.add(veraz);
		fields.add(observaciones);
		fields.add(emailPersonal);
		fields.add(emailLaboral);
		fields.add(formaPago);
		fields.add(guardar);
		fields.add(crearSS);
		fields.add(agregar);
		fields.add(cancelar);
		setCombos();

	}

	/** Getters de todos los Widgets **/
	public ListBox getTipoDocumento() {
		return tipoDocumento;
	}

	public TextBox getNumeroDocumento() {
		return numeroDocumento;
	}

	public TextBox getRazonSocial() {
		return razonSocial;
	}

	public TextBox getNombre() {
		return nombre;
	}

	public TextBox getApellido() {
		return apellido;
	}

	public ListBox getSexo() {
		return sexo;
	}

	public Widget getFechaNacimiento() {
		Grid datePickerFull = new Grid(1, 2);
		datePickerFull.setWidget(0, 0, fechaNacimiento.getTextBox());
		datePickerFull.setWidget(0, 1, fechaNacimiento);
		return datePickerFull;
	}

	public ListBox getContribuyente() {
		return contribuyente;
	}

	public TextBox getProvedorAnterior() {
		return provedorAnterior;
	}

	public ListBox getRubro() {
		return rubro;
	}

	public ListBox getClaseCliente() {
		return claseCliente;
	}

	public TextBox getCategoria() {
		return categoria;
	}

	public TextBox getCicloFacturacion() {
		return cicloFacturacion;
	}

	public Label getVeraz() {
		return veraz;
	}

	public TextArea getObservaciones() {
		return observaciones;
	}

	public TextBox getEmailPersonal() {
		return emailPersonal;
	}

	public TextBox getEmailLaboral() {
		return emailLaboral;
	}

	public ListBox getFormaPago() {
		return formaPago;
	}

	public Label getUsuario() {
		return usuario;
	}

	public Label getFechaCreacion() {
		return fechaCreacion;
	}

	public void setGuardar(SimpleLink guardar) {
		this.guardar = guardar;
	}

	public SimpleLink getGuardar() {
		return guardar;
	}

	public void setCrearSS(SimpleLink crearSS) {
		this.crearSS = crearSS;
	}

	public SimpleLink getCrearSS() {
		return crearSS;
	}

	public void setAgregar(SimpleLink agregar) {
		this.agregar = agregar;
	}

	public SimpleLink getAgregar() {
		return agregar;
	}

	public void setCancelar(SimpleLink cancelar) {
		this.cancelar = cancelar;
	}

	public SimpleLink getCancelar() {
		return cancelar;
	}
	
	private void setCombos() {
		CuentaRpcService.Util.getInstance().getAgregarCuentaInitializer(
			new DefaultWaitCallback<AgregarCuentaInitializer>() {
				public void success(AgregarCuentaInitializer result) {
					tipoDocumento.addAllItems(result.getTiposDocumento());
					contribuyente.addAllItems(result.getTiposContribuyentes());
					rubro.addAllItems(result.getRubro());
					sexo.addAllItems(result.getSexo());
					claseCliente.addAllItems(result.getClaseCliente());
					formaPago.addAllItems(result.getFormaPago());
				}
			});

	}

	public PersonaDto getPersona() {
		persona.setApellido(apellido.getText());
		persona.getDocumento().setNumero(numeroDocumento.getText());
		persona.getDocumento().setTipoDocumento(new TipoDocumentoDto(Long.parseLong(tipoDocumento.getSelectedItem().getItemValue()),tipoDocumento.getSelectedItem().getItemText()));
		persona.setFechaNacimiento(fechaNacimiento.getSelectedDate());
		persona.setNombre(nombre.getText());
		persona.setRazonSocial(razonSocial.getText());
		persona.setSexo(new SexoDto(Long.parseLong(sexo.getSelectedItem().getItemValue()),sexo.getSelectedItem().getItemText()));
		return persona;
	}
}

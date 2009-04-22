package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.util.FormUtils;
import ar.com.nextel.sfa.client.widget.UIData;
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

	private ListBox tipoDocumento = new ListBox();
	private TextBox numeroDocumento = new TextBox();
	private TextBox razonSocial = new TextBox();
	private TextBox nombre = new TextBox();
	private TextBox apellido = new TextBox();
	private TextBox sexo = new TextBox();
	private SimpleDatePicker fechaNacimiento = new SimpleDatePicker(false);
	private ListBox contribuyente = new ListBox();
	private TextBox provedorAnterior = new TextBox();
	private ListBox rubro = new ListBox();
	private TextBox claseCliente = new TextBox();
	private TextBox categoria = new TextBox();
	private TextBox cicloFacturacion = new TextBox();
	private Label veraz = new Label("Si puede");
	private TextArea observaciones = new TextArea();
	private TextBox emailPersonal = new TextBox();
	private TextBox emailLaboral = new TextBox();
	private ListBox modalidadPago = new ListBox();
	private Label usuario;
	private Label fechaCreacion;
	private SimpleLink guardar;
	private SimpleLink crearSS;
	private SimpleLink agregar;
	private SimpleLink cancelar;

	PersonaDto persona = new PersonaDto();

	public CuentaUIData() {
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
		fields.add(modalidadPago);
		fields.add(guardar  = new SimpleLink("Guardar", "#", true));
		fields.add(crearSS  = new SimpleLink("Crear SS", "#", true));
		fields.add(agregar  = new SimpleLink("Agregar", "#", true));
		fields.add(cancelar = new SimpleLink("Cancelar", "#", true));

		// CuentaRpcService.Util.getInstance().getAgregarCuentaInitializer(new
		// DefaultWaitCallback<AgregarCuentaInitializer>() {
		// public void success(AgregarCuentaInitializer result) {
		// setCombos(result);
		// }
		// });

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

	public TextBox getSexo() {
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

	public TextBox getClaseCliente() {
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

	public ListBox getModalidadPago() {
		return modalidadPago;
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
	
	private void setCombos(AgregarCuentaInitializer datos) {
		tipoDocumento.addAllItems(datos.getTiposDocumento());
		rubro.addAllItems(datos.getRubros());
		// modalidadPago.addAllItems(datos.get);
	}

	public PersonaDto getPersona() {
		persona.setApellido(apellido.getText());
		persona.setDocumento(numeroDocumento.getText());
		persona.setFechaNacimiento(fechaNacimiento.getSelectedDate());
		persona.setNombre(nombre.getText());
		persona.setRazonSocial(razonSocial.getText());
		//persona.setSexo(sexo.getText());
		return persona;
	}
}

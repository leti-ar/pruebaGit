package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
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

	private ListBox tipoDocumento;
	private TextBox numeroDocumento;
	private TextBox razonSocial;
	private TextBox nombre;
	private TextBox apellido;
	private TextBox sexo;
	private SimpleDatePicker fechaNacimiento;
	private ListBox contribuyente;
	private TextBox provedorAnterior;
	private ListBox rubro;
	private TextBox claseCliente;
	private TextBox categoria;
	private TextBox cicloFacturacion;
	private Label veraz;
	private TextArea observaciones;
	private TextBox emailPersonal;
	private TextBox emailLaboral;
	private ListBox modalidadPago;
	private Label usuario;
	private Label fechaCreacion;
	private SimpleLink guardar;
	private SimpleLink crearSS;
	private SimpleLink agregar;
	private SimpleLink cancelar;

	PersonaDto persona = new PersonaDto();

	public CuentaUIData() {
		fields.add(tipoDocumento = new ListBox());
		fields.add(numeroDocumento = new TextBox());
		fields.add(razonSocial = new TextBox());
		fields.add(nombre = new TextBox());
		fields.add(apellido = new TextBox());
		fields.add(sexo = new TextBox());
		fields.add(fechaNacimiento = new SimpleDatePicker(false));
		fields.add(contribuyente = new ListBox());
		fields.add(provedorAnterior = new TextBox());
		fields.add(rubro = new ListBox());
		fields.add(claseCliente = new TextBox());
		fields.add(categoria = new TextBox());
		fields.add(cicloFacturacion = new TextBox());
		fields.add(veraz = new Label("Si puede"));
		fields.add(observaciones = new TextArea());
		fields.add(emailPersonal = new TextBox());
		fields.add(emailLaboral = new TextBox());
		fields.add(modalidadPago = new ListBox());
		fields.add(guardar = new SimpleLink("Guardar", "#", true));
		fields.add(crearSS = new SimpleLink("Crear SS", "#", true));
		fields.add(agregar = new SimpleLink("Agregar", "#", true));
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
		persona.setSexo(sexo.getText());
		return persona;
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

}

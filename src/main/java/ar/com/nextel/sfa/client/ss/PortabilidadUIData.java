package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PortabilidadUIData extends Composite {

	 interface MyUiBinder extends UiBinder<Widget, PortabilidadUIData> {}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField(provided=true)
	 RegexTextBox nroSS = new RegexTextBox();

	@UiField(provided=true)
	RegexTextBox nroUltimaFactura = new RegexTextBox();
	
	@UiField(provided=true)
	ListBox tipoDocumento = new ListBox();
	@UiField(provided=true)
	RegexTextBox numeroDocumento  = new RegexTextBox();
	@UiField(provided=true)
	RegexTextBox razonSocial  = new RegexTextBox();
	@UiField(provided=true)
	RegexTextBox nombre  = new RegexTextBox();
	@UiField(provided=true)
	RegexTextBox apellido  = new RegexTextBox();
	@UiField(provided=true)
	RegexTextBox email = new RegexTextBox();
	@UiField(provided=true)
	TelefonoTextBox telPrincipalTextBox = new TelefonoTextBox();
	@UiField(provided=true)
	ListBox proveedorAnterior = new ListBox();
	@UiField(provided=true)
	ListBox tipoTelefonia = new ListBox();
	@UiField(provided=true)
	TelefonoTextBox telPortar = new TelefonoTextBox();
	@UiField(provided=true)
	ListBox modalidadCobro = new ListBox();
	@UiField(provided=true)
	CheckBox recibeSMS = new CheckBox();

	
	public PortabilidadUIData() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	


	public RegexTextBox getNroUltimaFactura() {
		return nroUltimaFactura;
	}

	public void setNroUltimaFactura(RegexTextBox nroUltimaFactura) {
		this.nroUltimaFactura = nroUltimaFactura;
	}

	public ListBox getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(ListBox tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public RegexTextBox getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(RegexTextBox numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public RegexTextBox getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(RegexTextBox razonSocial) {
		this.razonSocial = razonSocial;
	}

	public RegexTextBox getNombre() {
		return nombre;
	}

	public void setNombre(RegexTextBox nombre) {
		this.nombre = nombre;
	}

	public RegexTextBox getApellido() {
		return apellido;
	}

	public void setApellido(RegexTextBox apellido) {
		this.apellido = apellido;
	}

	public RegexTextBox getEmail() {
		return email;
	}

	public void setEmail(RegexTextBox email) {
		this.email = email;
	}

	public TelefonoTextBox getTelPrincipalTextBox() {
		return telPrincipalTextBox;
	}

	public void setTelPrincipalTextBox(TelefonoTextBox telPrincipalTextBox) {
		this.telPrincipalTextBox = telPrincipalTextBox;
	}

	public ListBox getProveedorAnterior() {
		return proveedorAnterior;
	}

	public void setProveedorAnterior(ListBox proveedorAnterior) {
		this.proveedorAnterior = proveedorAnterior;
	}

	public ListBox getTipoTelefonia() {
		return tipoTelefonia;
	}

	public void setTipoTelefonia(ListBox tipoTelefonia) {
		this.tipoTelefonia = tipoTelefonia;
	}

	public TelefonoTextBox getTelPortar() {
		return telPortar;
	}

	public void setTelPortar(TelefonoTextBox telPortar) {
		this.telPortar = telPortar;
	}

	public ListBox getModalidadCobro() {
		return modalidadCobro;
	}

	public void setModalidadCobro(ListBox modalidadCobro) {
		this.modalidadCobro = modalidadCobro;
	}

	public CheckBox getRecibeSMS() {
		return recibeSMS;
	}

	public void setRecibeSMS(CheckBox recibeSMS) {
		this.recibeSMS = recibeSMS;
	}

}

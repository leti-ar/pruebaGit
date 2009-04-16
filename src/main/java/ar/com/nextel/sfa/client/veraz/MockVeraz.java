package ar.com.nextel.sfa.client.veraz;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MockVeraz implements IsSerializable{

	private String estado;
	private String razonSocial;
	private String nombre;
	private String apellido;
	private String mensaje;
	
	public MockVeraz getresultado () {
		
		MockVeraz mockVeraz = new MockVeraz();
		
		mockVeraz.estado = "Aceptar";
		mockVeraz.razonSocial = "Meli Rial";
		mockVeraz.nombre = "Meli";
		mockVeraz.apellido = "Rial";
		mockVeraz.mensaje = "hola";
		return mockVeraz;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}
	
}

package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import ar.com.nextel.model.solicitudes.beans.EstadoSolicitud;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EstadoPorSolicitudDto implements ListBoxItem, IsSerializable {

	private long id;
	private String usuario;
    private EstadoSolicitud estado;
    private Date fecha;
	
	
	
	public EstadoPorSolicitudDto() {
	}

	public EstadoPorSolicitudDto(String usuario, Date fecha, EstadoSolicitud estado) {
		super();
		
		this.usuario = usuario;
		this.fecha = fecha;
		this.estado= estado;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public EstadoSolicitud getEstado() {
		return estado;
	}

	public void setEstado(EstadoSolicitud estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getItemText() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getItemValue() {
		// TODO Auto-generated method stub
		return null;
	}

	

}

package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import ar.com.nextel.model.solicitudes.beans.EstadoSolicitud;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EstadoPorSolicitudDto implements ListBoxItem, IsSerializable {

	private long id;
	private String usuario;
    private EstadoSolicitudDto estado;
    private Date fecha;
	private long numeroSolicitud;
	
	public EstadoPorSolicitudDto() {
	}

	public EstadoPorSolicitudDto(String usuario, Date fecha, EstadoSolicitudDto estado) {
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

	public EstadoSolicitudDto getEstado() {
		return estado;
	}

	public void setEstado(EstadoSolicitudDto estado) {
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

	public long getNumeroSolicitud() {
		return numeroSolicitud;
	}

	public void setNumeroSolicitud(long numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}
}
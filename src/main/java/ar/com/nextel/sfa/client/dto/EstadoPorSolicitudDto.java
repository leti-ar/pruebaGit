package ar.com.nextel.sfa.client.dto;

import java.util.Date;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EstadoPorSolicitudDto implements ListBoxItem, IsSerializable {

	private long id;
	private long usuario;
    private EstadoSolicitudDto estado;
    private Date fecha;
	private long idSolicitud;
	
	public EstadoPorSolicitudDto() {
	}

	public EstadoPorSolicitudDto(long usuario, Date fecha, EstadoSolicitudDto estado) {
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



	public EstadoSolicitudDto getEstado() {
		return estado;
	}

	public void setEstado(EstadoSolicitudDto estado) {
		this.estado = estado;
	}

	public long getUsuario() {
		return usuario;
	}

	public void setUsuario(long usuario) {
		this.usuario = usuario;
	}

	public long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}


	
}
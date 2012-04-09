package ar.com.nextel.sfa.client.dto;

import java.util.Date;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EstadoPorSolicitudDto implements ListBoxItem, IsSerializable {

	private long id;
	private VendedorDto usuario;
    private EstadoSolicitudDto estado;
    private Date fecha;
	private SolicitudServicioDto solicitud;
	
	public EstadoPorSolicitudDto() {
	}

	public EstadoPorSolicitudDto(VendedorDto usuario, Date fecha, EstadoSolicitudDto estado) {
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

	public VendedorDto getUsuario() {
		return usuario;
	}

	public void setUsuario(VendedorDto usuario) {
		this.usuario = usuario;
	}

	public SolicitudServicioDto getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudServicioDto solicitud) {
		this.solicitud = solicitud;
	}

	

	
}
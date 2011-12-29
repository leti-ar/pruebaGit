package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;


import ar.com.nextel.sfa.client.dto.ComentarioAnalistaDto;
import ar.com.nextel.sfa.client.dto.ControlesDto;
import ar.com.nextel.sfa.client.dto.EstadoHistoricoDto;
import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.SucursalDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;


import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudInitializer implements IsSerializable {

	private List<OrigenSolicitudDto> origenesSolicitud;
	private List<TipoAnticipoDto> tiposAnticipo;
	private List<VendedorDto> vendedores;
	private List<SucursalDto> sucursales;
	private List<ControlesDto>control;
    private String estado;
    private List<EstadoHistoricoDto> estadosHistorico;
    private List<EstadoSolicitudDto> opcionesEstado = new ArrayList<EstadoSolicitudDto>();
    private List<ComentarioAnalistaDto> comentarioAnalistaMensaje = new ArrayList<ComentarioAnalistaDto>();

	public void setOrigenesSolicitud(List<OrigenSolicitudDto> origenesSolicitud) {
		this.origenesSolicitud = origenesSolicitud;
	}

	public List<OrigenSolicitudDto> getOrigenesSolicitud() {
		return origenesSolicitud;
	}

	public void setTiposAnticipo(List<TipoAnticipoDto> tiposAnticipo) {
		this.tiposAnticipo = tiposAnticipo;
	}

	public List<TipoAnticipoDto> getTiposAnticipo() {
		return tiposAnticipo;
	}

	public List<VendedorDto> getVendedores() {
		return vendedores;
	}

	public void setVendedores(List<VendedorDto> vendedores) {
		this.vendedores = vendedores;
	}

	public List<SucursalDto> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<SucursalDto> sucursales) {
		this.sucursales = sucursales;
	}

	public List<ControlesDto> getControl() {
		return control;
	}

	public void setControl(List<ControlesDto> control) {
		this.control = control;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setEstadosHistorico(List<EstadoHistoricoDto> estadosHistorico) {
		this.estadosHistorico = estadosHistorico;
	}

	public List<EstadoHistoricoDto> getEstadosHistorico() {
		return estadosHistorico;
	}
	
	public List<EstadoSolicitudDto> getOpcionesEstado() {
		return opcionesEstado;
	}

	public void setOpcionesEstado(List<EstadoSolicitudDto> opcionesEstado) {
		this.opcionesEstado = opcionesEstado;
	}

	public List<ComentarioAnalistaDto> getComentarioAnalistaMensaje() {
		return comentarioAnalistaMensaje;
	}

	public void setComentarioAnalistaMensaje(
			List<ComentarioAnalistaDto> comentarioAnalistaMensaje) {
		this.comentarioAnalistaMensaje = comentarioAnalistaMensaje;
	}
}
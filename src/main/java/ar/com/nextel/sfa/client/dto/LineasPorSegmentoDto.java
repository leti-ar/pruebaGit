package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LineasPorSegmentoDto implements IsSerializable{
	
	int idLineasPorSegmento;
//	LF - #3249
//	TipoSolicitudDto tipoSolicitud;
	TipoVendedorDto tipoVendedor;
	SegmentoDto SegmentoCliente;
	int cantLineas;
	
	public LineasPorSegmentoDto(){}
	
	public int getIdLineasPorSegmento() {
		return idLineasPorSegmento;
	}
	
	public void setIdLineasPorSegmento(int idLineasPorSegmento) {
		this.idLineasPorSegmento = idLineasPorSegmento;
	}
	
//	LF - #3249
//	public TipoSolicitudDto getTipoSolicitud() {
//		return tipoSolicitud;
//	}
//	
//	public void setTipoSolicitud(TipoSolicitudDto tipoSolicitud) {
//		this.tipoSolicitud = tipoSolicitud;
//	}
	
	public TipoVendedorDto getTipoVendedor() {
		return tipoVendedor;
	}
	
	public void setTipoVendedor(TipoVendedorDto tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}
	
	public SegmentoDto getSegmentoCliente() {
		return SegmentoCliente;
	}
	
	public void setSegmentoCliente(SegmentoDto segmentoCliente) {
		SegmentoCliente = segmentoCliente;
	}
	
	public int getCantLineas() {
		return cantLineas;
	}
	
	public void setCantLineas(int cantLineas) {
		this.cantLineas = cantLineas;
	}
}
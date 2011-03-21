package ar.com.nextel.sfa.client.initializer;

import java.util.Collections;
import java.util.List;

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
	
	public List<SucursalDto> getSucursalesOrdenado() {
		if (sucursales != null && !sucursales.isEmpty()) {
			Collections.sort(sucursales);
		}
		return sucursales;
	}
	
}

package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.ClaseCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.FormaPagoDto;
import ar.com.nextel.sfa.client.dto.MotivoNoCierreDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TipoCanalVentasDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.dto.TipoTelefonoDto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class AgregarCuentaInitializer implements IsSerializable {

	private List<TipoDocumentoDto> tiposDocumento = new ArrayList<TipoDocumentoDto>();
	private List<TipoContribuyenteDto> tiposContribuyentes = new ArrayList<TipoContribuyenteDto>();
	private List<SexoDto> sexo = new ArrayList<SexoDto>();
	private List<FormaPagoDto> formaPago = new ArrayList<FormaPagoDto>();
	private List<RubroDto> rubro = new ArrayList<RubroDto>();
	private List<ClaseCuentaDto> claseClientePorVendedor = new ArrayList<ClaseCuentaDto>();
	private List<ClaseCuentaDto> claseCliente = new ArrayList<ClaseCuentaDto>();
	private List<ProveedorDto> proveedorAnterior = new ArrayList<ProveedorDto>();
	private List<TipoTelefonoDto> tipoTelefono = new ArrayList<TipoTelefonoDto>();
	private List<CargoDto> cargo = new ArrayList<CargoDto>();
	private List<TipoCuentaBancariaDto> tipoCuentaBancaria = new ArrayList<TipoCuentaBancariaDto>();
	private List<TipoTarjetaDto> tipoTarjeta = new ArrayList<TipoTarjetaDto>();
	private List<TipoCanalVentasDto> tipoCanalVentas = new ArrayList<TipoCanalVentasDto>();
	private List<MotivoNoCierreDto> motivoNoCierre = new ArrayList<MotivoNoCierreDto>();
	private List<EstadoOportunidadDto> estadoOportunidad = new ArrayList<EstadoOportunidadDto>();
	
	private int anio;
	
	public AgregarCuentaInitializer() {	};  
	
	public List<TipoDocumentoDto> getTiposDocumento() {
		return tiposDocumento;
	}
	public void setTiposDocumento(List<TipoDocumentoDto> tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}
	public List<TipoContribuyenteDto> getTiposContribuyentes() {
		return tiposContribuyentes;
	}
	public void setTiposContribuyentes(
			List<TipoContribuyenteDto> tiposContribuyentes) {
		this.tiposContribuyentes = tiposContribuyentes;
	}
	public List<RubroDto> getRubro() {
		return rubro;
	}
	public void setRubro(List<RubroDto> rubro) {
		this.rubro = rubro;
	}

	public List<SexoDto> getSexo() {
		return sexo;
	}

	public void setSexo(List<SexoDto> sexo) {
		this.sexo = sexo;
	}

	public List<FormaPagoDto> getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(List<FormaPagoDto> formaPago) {
		this.formaPago = formaPago;
	}

	public List<ClaseCuentaDto> getClaseCliente() {
		return claseCliente;
	}

	public void setClaseCliente(List<ClaseCuentaDto> claseCliente) {
		this.claseCliente = claseCliente;
	}

	public List<TipoCuentaBancariaDto> getTipoCuentaBancaria() {
		return tipoCuentaBancaria;
	}

	public void setTipoCuentaBancaria(List<TipoCuentaBancariaDto> tipoCuentaBancaria) {
		this.tipoCuentaBancaria = tipoCuentaBancaria;
	}

	public List<TipoTarjetaDto> getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(List<TipoTarjetaDto> tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public List<ProveedorDto> getProveedorAnterior() {
		return proveedorAnterior;
	}

	public void setProveedorAnterior(List<ProveedorDto> proveedorAnterior) {
		this.proveedorAnterior = proveedorAnterior;
	}

	public List<TipoTelefonoDto> getTipoTelefono() {
		return tipoTelefono;
	}

	public void setTipoTelefono(List<TipoTelefonoDto> tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}

	public List<CargoDto> getCargo() {
		return cargo;
	}

	public void setCargo(List<CargoDto> cargo) {
		this.cargo = cargo;
	}
	public List<TipoCanalVentasDto> getTipoCanalVentas() {
		return tipoCanalVentas;
	}
	public void setTipoCanalVentas(List<TipoCanalVentasDto> tipoCanalVentas) {
		this.tipoCanalVentas = tipoCanalVentas;
	}
	public List<MotivoNoCierreDto> getMotivoNoCierre() {
		return motivoNoCierre;
	}
	public void setMotivoNoCierre(List<MotivoNoCierreDto> motivoNoCierre) {
		this.motivoNoCierre = motivoNoCierre;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public void setEstadoOportunidad(List<EstadoOportunidadDto> estadoOportunidad) {
		this.estadoOportunidad = estadoOportunidad;
	}
	public List<EstadoOportunidadDto> getEstadoOportunidad() {
		return estadoOportunidad;
	}

	public void setClaseClientePorVendedor(
			List<ClaseCuentaDto> claseClientePorVendedor) {
		this.claseClientePorVendedor = claseClientePorVendedor;
	}
	
	public List<ClaseCuentaDto> getClaseClientePorVendedor() {
		return claseClientePorVendedor;
	}
	
	
}

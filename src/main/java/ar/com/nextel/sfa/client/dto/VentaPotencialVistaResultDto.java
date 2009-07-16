package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VentaPotencialVistaResultDto implements IsSerializable {

	List<VentaPotencialVistaDto> ventasPotencialesVistaDto;
	private String numeroVtasPotNoConsultadas;
	
	public VentaPotencialVistaResultDto() {
		
	}
	
	public VentaPotencialVistaResultDto(
			List<VentaPotencialVistaDto> ventasPotencialesVistaDto,
			String numeroVtasPotNoConsultadas) {
		super();
		this.ventasPotencialesVistaDto = ventasPotencialesVistaDto;
		this.numeroVtasPotNoConsultadas = numeroVtasPotNoConsultadas;
	}
	
	public List<VentaPotencialVistaDto> getVentasPotencialesVistaDto() {
		return ventasPotencialesVistaDto;
	}
	
	public void setVentasPotencialesVistaDto(
			List<VentaPotencialVistaDto> ventasPotencialesVistaDto) {
		this.ventasPotencialesVistaDto = ventasPotencialesVistaDto;
	}
	
	public String getNumeroVtasPotNoConsultadas() {
		return numeroVtasPotNoConsultadas;
	}
	
	public void setNumeroVtasPotNoConsultadas(String numeroVtasPotNoConsultadas) {
		this.numeroVtasPotNoConsultadas = numeroVtasPotNoConsultadas;
	}	
	
}

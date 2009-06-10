package ar.com.nextel.sfa.client.dto;

import java.util.List;

public class DivisionDto extends CuentaDto {
    private GranCuentaDto granCuenta;
    private String nombre;
    private List<SuscriptorDto> suscriptores;
	public GranCuentaDto getGranCuenta() {
		return granCuenta;
	}
	public void setGranCuenta(GranCuentaDto granCuenta) {
		this.granCuenta = granCuenta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<SuscriptorDto> getSuscriptores() {
		return suscriptores;
	}
	public void setSuscriptores(List<SuscriptorDto> suscriptores) {
		this.suscriptores = suscriptores;
	}
}

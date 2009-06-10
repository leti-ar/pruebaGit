package ar.com.nextel.sfa.client.dto;

public class SuscriptorDto extends CuentaDto {
    private CuentaDto granCuenta;
    private DivisionDto division;
	public CuentaDto getGranCuenta() {
		return granCuenta;
	}
	public void setGranCuenta(CuentaDto granCuenta) {
		this.granCuenta = granCuenta;
	}
	public DivisionDto getDivision() {
		return division;
	}
	public void setDivision(DivisionDto division) {
		this.division = division;
	}
}

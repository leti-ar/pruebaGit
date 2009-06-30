package ar.com.nextel.sfa.client.dto;

public class SuscriptorDto extends CuentaDto {
    private GranCuentaDto granCuenta;
    private DivisionDto division;

    public GranCuentaDto getGranCuenta() {
		return granCuenta;
	}
	public void setGranCuenta(GranCuentaDto granCuenta) {
		this.granCuenta = granCuenta;
	}
	public DivisionDto getDivision() {
		return division;
	}
	public void setDivision(DivisionDto division) {
		this.division = division;
	}
}

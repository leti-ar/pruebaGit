package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EstadoCreditoFidelizacionDto implements IsSerializable {
    private static int HASH_CODE_MULTIPLIER = 1600749865;
    private static int HASH_CODE_INITIAL = -275183237;
    private Double monto;
    private Date vencimiento;
    private CuentaDto cuenta;
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public Date getVencimiento() {
		return vencimiento;
	}
	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	public CuentaDto getCuenta() {
		return cuenta;
	}
	public void setCuenta(CuentaDto cuenta) {
		this.cuenta = cuenta;
	}
	public static int getHASH_CODE_MULTIPLIER() {
		return HASH_CODE_MULTIPLIER;
	}
	public static int getHASH_CODE_INITIAL() {
		return HASH_CODE_INITIAL;
	}
	public static void setHASH_CODE_MULTIPLIER(int hash_code_multiplier) {
		HASH_CODE_MULTIPLIER = hash_code_multiplier;
	}
	public static void setHASH_CODE_INITIAL(int hash_code_initial) {
		HASH_CODE_INITIAL = hash_code_initial;
	}
	
}

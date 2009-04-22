package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CuentaCorrienteDto implements IsSerializable{
	    private CuentaResultsDto cuenta;
	    private String tipo;
	    private String numeroComprobante;
	    private Double monto;
	    private Date fechaVencimiento;
		public CuentaResultsDto getCuenta() {
			return cuenta;
		}
		public void setCuenta(CuentaResultsDto cuenta) {
			this.cuenta = cuenta;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		public String getNumeroComprobante() {
			return numeroComprobante;
		}
		public void setNumeroComprobante(String numeroComprobante) {
			this.numeroComprobante = numeroComprobante;
		}
		public Double getMonto() {
			return monto;
		}
		public void setMonto(Double monto) {
			this.monto = monto;
		}
		public Date getFechaVencimiento() {
			return fechaVencimiento;
		}
		public void setFechaVencimiento(Date fechaVencimiento) {
			this.fechaVencimiento = fechaVencimiento;
		}
}


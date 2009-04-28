package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FidelizacionDto implements IsSerializable {

	private String monto;
	private String estado;
	private Date vencimiento;
	private List<DetalleFidelizacionDto> detalles = new ArrayList<DetalleFidelizacionDto>();

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public List<DetalleFidelizacionDto> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFidelizacionDto> detalles) {
		this.detalles = detalles;
	}

}

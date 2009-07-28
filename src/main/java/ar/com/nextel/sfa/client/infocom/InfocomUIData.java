package ar.com.nextel.sfa.client.infocom;

import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DetalleFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;

public class InfocomUIData extends UIData {

	private EstadoTerminales estadoTerminales;
	private InlineHTML ciclo;
	private Label cicloLabel;
	private InlineHTML flota;
	private Label flotaLabel;
	private InlineHTML limCredito;
	private Label limCreditoLabel;
	private ListBox responsablePago;
	private Label montoLabel;
	private Label vencimientoLabel;
	
	private Label numResponsable;
	

	private InlineHTML monto;
	private Label estadoLabel;
	private InlineHTML vencimiento;
	private InlineHTML estado;

	private Grid cuentaCorriente;
	private Grid creditoFidelizacion;
	
	private SimpleLink scoring; 
	private SimpleLink resumenPorEquipo;

	private final NumberFormat numberFormat = NumberFormat.getCurrencyFormat();
	private final DateTimeFormat dateFormat = DateTimeFormat.getMediumDateFormat();

	public InfocomUIData() {
		estadoTerminales = new EstadoTerminales();
		cicloLabel = new Label("Ciclo: ");
		ciclo = new InlineHTML();
		flotaLabel = new Label("Flota: ");
		flota = new InlineHTML();
		limCreditoLabel = new Label("Lím. Crédito: ");
		limCredito = new InlineHTML();
		responsablePago = new ListBox();
		montoLabel = new Label("Monto: ");
		monto = new InlineHTML();
		estadoLabel = new Label("Estado");
		vencimientoLabel = new Label("Vencimiento: ");
		vencimiento = new InlineHTML();
		numResponsable = new Label("N° Responsable Pago:");
		scoring = new SimpleLink("Scoring");
		scoring.addStyleName("infocomSimpleLink");
		resumenPorEquipo = new SimpleLink("Resumen por equipo");
		resumenPorEquipo.addStyleName("infocomSimpleLink");
		estado = new InlineHTML();
	}


	/** Carga los datos de Header de infocom */
	public void setInfocom(InfocomInitializer infocom) {
		estadoTerminales.setEstado(Integer.parseInt(infocom.getTerminalesActivas()), Integer.parseInt(infocom.getTerminalesSuspendidas()), Integer.parseInt(infocom.getTerminalesDesactivadas()));
		ciclo.setText(infocom.getCiclo());
		flota.setText(infocom.getFlota());
		limCredito.setText(infocom.getLimiteCredito());
		for (Iterator iterator = (infocom.getResponsablePago()).iterator(); iterator.hasNext();) {
			CuentaDto cuentaDto = (CuentaDto) iterator.next();
			responsablePago.addItem(cuentaDto.getCodigoVantive());
		}
	}

	/** Carga el panel de Credito de Fidelizacion de infocom */
	public void setCreditoFidelizacion(CreditoFidelizacionDto creditoFidelizacionDto) {
		monto.setText(numberFormat.format(creditoFidelizacionDto.getMonto()));
		estado.setText(creditoFidelizacionDto.getEstado());
		if (creditoFidelizacionDto.getVencimiento() != null) {
			vencimiento.setText(dateFormat.format(creditoFidelizacionDto.getVencimiento()));
		} else {
			vencimiento.setText("");
		}
		refreshCreditoFidelizacionTable(creditoFidelizacionDto.getDetalles());
	}

	/** Carga el panel de Cuenta Corriente de infocom */
	public void setCuentaCorriente(List<TransaccionCCDto> transacciones) {
		refreshCuentaCorrienteTable(transacciones);
	}

	private void refreshCreditoFidelizacionTable(List<DetalleFidelizacionDto> detallesFidelizacion) {
//		creditoFidelizacion.resizeRows(1);
//		creditoFidelizacion.resizeRows(detallesFidelizacion.size() + 1);
		int row = 1;
		DateTimeFormat dateFormatter = DateTimeFormat.getMediumDateFormat();
		for (Iterator iterator = detallesFidelizacion.iterator(); iterator.hasNext();) {
			DetalleFidelizacionDto detalle = (DetalleFidelizacionDto) iterator.next();
//			creditoFidelizacion.setHTML(row, 0, dateFormatter.format(detalle.getFecha()));
//			creditoFidelizacion.setHTML(row, 1, detalle.getDescripcion());
//			creditoFidelizacion.setHTML(row, 2, detalle.getMonto());
//			creditoFidelizacion.setHTML(row, 3, detalle.getFactura());
		}
	}

	private void refreshCuentaCorrienteTable(List<TransaccionCCDto> transacciones) {
		cuentaCorriente.resizeRows(1);
		cuentaCorriente.resizeRows(transacciones.size() + 1);
		for (int row = 1; row <= transacciones.size(); row++) {
			String[] transaccionRow = transacciones.get(row - 1).getRow();
			for (int col = 0; col < 6; col++) {
				cuentaCorriente.setHTML(row, col, transaccionRow[col]);
			}
		}
	}


	public EstadoTerminales getEstadoTerminales() {
		return estadoTerminales;
	}


	public void setEstadoTerminales(EstadoTerminales estadoTerminales) {
		this.estadoTerminales = estadoTerminales;
	}


	public InlineHTML getCiclo() {
		return ciclo;
	}


	public void setCiclo(InlineHTML ciclo) {
		this.ciclo = ciclo;
	}


	public Label getCicloLabel() {
		return cicloLabel;
	}


	public void setCicloLabel(Label cicloLabel) {
		this.cicloLabel = cicloLabel;
	}


	public InlineHTML getFlota() {
		return flota;
	}


	public void setFlota(InlineHTML flota) {
		this.flota = flota;
	}


	public Label getFlotaLabel() {
		return flotaLabel;
	}


	public void setFlotaLabel(Label flotaLabel) {
		this.flotaLabel = flotaLabel;
	}


	public InlineHTML getLimCredito() {
		return limCredito;
	}


	public void setLimCredito(InlineHTML limCredito) {
		this.limCredito = limCredito;
	}


	public Label getLimCreditoLabel() {
		return limCreditoLabel;
	}


	public void setLimCreditoLabel(Label limCreditoLabel) {
		this.limCreditoLabel = limCreditoLabel;
	}


	public ListBox getResponsablePago() {
		return responsablePago;
	}


	public void setResponsablePago(ListBox responsablePago) {
		this.responsablePago = responsablePago;
	}


	public Label getNumResponsable() {
		return numResponsable;
	}


	public void setNumResponsable(InlineHTML numResponsable) {
		this.numResponsable = numResponsable;
	}


	public InlineHTML getMonto() {
		return monto;
	}


	public void setMonto(InlineHTML monto) {
		this.monto = monto;
	}


	public Label getEstadoLabel() {
		return estadoLabel;
	}


	public void setEstadoLabel(Label estado) {
		this.estadoLabel = estado;
	}


	public InlineHTML getVencimiento() {
		return vencimiento;
	}


	public void setVencimiento(InlineHTML vencimiento) {
		this.vencimiento = vencimiento;
	}


	public Grid getCuentaCorriente() {
		return cuentaCorriente;
	}


	public void setCuentaCorriente(Grid cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}


	public Grid getCreditoFidelizacion() {
		return creditoFidelizacion;
	}


	public void setCreditoFidelizacion(Grid creditoFidelizacion) {
		this.creditoFidelizacion = creditoFidelizacion;
	}


	public SimpleLink getScoring() {
		return scoring;
	}


	public void setScoring(SimpleLink scoring) {
		this.scoring = scoring;
	}


	public SimpleLink getResumenPorEquipo() {
		return resumenPorEquipo;
	}


	public void setResumenPorEquipo(SimpleLink resumenPorEquipo) {
		this.resumenPorEquipo = resumenPorEquipo;
	}


	public Label getMontoLabel() {
		return montoLabel;
	}


	public void setMontoLabel(Label montoLabel) {
		this.montoLabel = montoLabel;
	}


	public Label getVencimientoLabel() {
		return vencimientoLabel;
	}


	public void setVencimientoLabel(Label vencimientoLabel) {
		this.vencimientoLabel = vencimientoLabel;
	}


	public InlineHTML getEstado() {
		return estado;
	}


	public void setEstado(InlineHTML estado) {
		this.estado = estado;
	}	
	
}

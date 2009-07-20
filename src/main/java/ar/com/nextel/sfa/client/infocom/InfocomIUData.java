package ar.com.nextel.sfa.client.infocom;

import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.DetalleFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class InfocomIUData extends UIData {

	private EstadoTerminales estadoTerminales;
	private InlineHTML ciclo;
	private InlineHTML flota;
	private InlineHTML limCredito;
	private ListBox responsablePago;

	private InlineHTML monto;
	private InlineHTML estado;
	private InlineHTML vencimiento;

	private Grid cuentaCorriente;
	private Grid creditoFidelizacion;

	private final NumberFormat numberFormat = NumberFormat.getCurrencyFormat();
	private final DateTimeFormat dateFormat = DateTimeFormat.getMediumDateFormat();

	public InfocomIUData() {
		estadoTerminales = new EstadoTerminales();
		ciclo = new InlineHTML();
		flota = new InlineHTML();
		limCredito = new InlineHTML();
		responsablePago = new ListBox();
		monto = new InlineHTML();
		estado = new InlineHTML();
		vencimiento = new InlineHTML();
		initCreditoFidelizacionTable();
		initCuentaCorrienteTable();
	}

	private void initCreditoFidelizacionTable() {
		creditoFidelizacion = new Grid();
		SimplePanel contTable = new SimplePanel();
		contTable.addStyleName("contTable");
		String[] widths = { "149px", "489px", "149px", "149px" };
		String[] titles = { "Fecha", "Descripcion", "Monto", "Factura aplicada" };
		for (int col = 0; col < widths.length; col++) {
			creditoFidelizacion.setHTML(0, col, titles[col]);
			creditoFidelizacion.getColumnFormatter().setWidth(col, widths[col]);
			creditoFidelizacion.getColumnFormatter().addStyleName(col, "alignCenter");
		}
		creditoFidelizacion.setCellPadding(0);
		creditoFidelizacion.setCellSpacing(0);
		creditoFidelizacion.addStyleName("dataTable");
		creditoFidelizacion.getRowFormatter().addStyleName(0, "header");
	}

	private void initCuentaCorrienteTable() {
		cuentaCorriente = new Grid();

		cuentaCorriente = new Grid();
		SimplePanel contTable = new SimplePanel();
		contTable.addStyleName("contTable");
		String[] widths = { "164px", "164px", "149px", "149px", "149px", "149px", "149px", "149px", "149px" };
		String[] titles = { "Número cuenta", "Plan", "Modelo", "Tipo telefonía", "Forma contratación",
				"Fecha", "Número contrato", "Motivo", "Numero solicitud" };
		for (int col = 0; col < widths.length; col++) {
			cuentaCorriente.setHTML(0, col, titles[col]);
			cuentaCorriente.getColumnFormatter().setWidth(col, widths[col]);
			cuentaCorriente.getColumnFormatter().addStyleName(col, "alignCenter");
		}
		cuentaCorriente.setCellPadding(0);
		cuentaCorriente.setCellSpacing(0);
		cuentaCorriente.addStyleName("dataTable");
		cuentaCorriente.getRowFormatter().addStyleName(0, "header");
	}

	/** Carga los datos de Header de infocom */
	public void setInfocom(InfocomInitializer infocom) {
		estadoTerminales.setEstado(infocom.getTerminalesActivas(), infocom.getTerminalesSuspendidas(),
				infocom.getTerminalesDesactivadas());
		ciclo.setHTML(infocom.getCiclo());
		flota.setHTML(infocom.getFlota());
		limCredito.setHTML(infocom.getLimCredito());
		responsablePago.addAllItems(infocom.getResponsablePago());
	}

	/** Carga el panel de Credito de Fidelizacion de infocom */
	public void setCreditoFidelizacion(CreditoFidelizacionDto creditoFidelizacionDto) {
		monto.setHTML(numberFormat.format(creditoFidelizacionDto.getMonto()));
		estado.setHTML(creditoFidelizacionDto.getEstado());
		vencimiento.setHTML(dateFormat.format(creditoFidelizacionDto.getVencimiento()));
		refreshCreditoFidelizacionTable(creditoFidelizacionDto.getDetalles());
	}

	/** Carga el panel de Cuenta Corriente de infocom */
	public void setCuentaCorriente(List<TransaccionCCDto> transacciones) {
		refreshCuentaCorrienteTable(transacciones);
	}

	private void refreshCreditoFidelizacionTable(List<DetalleFidelizacionDto> detallesFidelizacion) {
		creditoFidelizacion.resizeRows(1);
		creditoFidelizacion.resizeRows(detallesFidelizacion.size() + 1);
		int row = 1;
		DateTimeFormat dateFormatter = DateTimeFormat.getMediumDateFormat();
		for (Iterator iterator = detallesFidelizacion.iterator(); iterator.hasNext();) {
			DetalleFidelizacionDto detalle = (DetalleFidelizacionDto) iterator.next();
			creditoFidelizacion.setHTML(row, 0, dateFormatter.format(detalle.getFecha()));
			creditoFidelizacion.setHTML(row, 1, detalle.getDescripcion());
			creditoFidelizacion.setHTML(row, 2, detalle.getMonto());
			creditoFidelizacion.setHTML(row, 3, detalle.getFactura());
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

	public InlineHTML getCiclo() {
		return ciclo;
	}

	public InlineHTML getFlota() {
		return flota;
	}

	public InlineHTML getLimCredito() {
		return limCredito;
	}

	public ListBox getResponsablePago() {
		return responsablePago;
	}

	public InlineHTML getMonto() {
		return monto;
	}

	public InlineHTML getEstado() {
		return estado;
	}

	public InlineHTML getVencimiento() {
		return vencimiento;
	}

	public Grid getCuentaCorriente() {
		return cuentaCorriente;
	}

	public Grid getCreditoFidelizacion() {
		return creditoFidelizacion;
	}
	
}

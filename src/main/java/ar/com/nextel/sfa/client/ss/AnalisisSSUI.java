package ar.com.nextel.sfa.client.ss;



import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.dev.asm.Label;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class AnalisisSSUI extends Composite {

	private FlowPanel mainpanel;
	private EditarSSUIController controller;
	private EditarSSUIData editarSSUIData;
	private Grid cambiarEstadoSS;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();

	public AnalisisSSUI(EditarSSUIController controller) {
		mainpanel = new FlowPanel();
		this.controller = controller;
		initWidget(mainpanel);
		this.controller = controller;
		editarSSUIData = controller.getEditarSSUIData();
	
		mainpanel.add(getCambiarEstadoSS());
	

	}




	private Widget getCambiarEstadoSS() {
		TitledPanel cambiarEstadoPanel = new TitledPanel(Sfa.constant().whiteSpace());
		final SimpleLink cambio = new SimpleLink("Cambiar Estado");
		cambio.addStyleName("ml5");
		cambio.addStyleName("infocomSimpleLink");
		cambiarEstadoPanel.add(cambio);
		cambiarEstadoPanel.add(getEstadoTable());
		Grid nuevoEstado= new Grid(1,2);
		
		nuevoEstado.setHTML(0,0,Sfa.constant().nuevoEstado());
		nuevoEstado.setWidget(0, 1,editarSSUIData.getNuevoEstado());
        cambiarEstadoPanel.add(nuevoEstado);
		Grid mail = new Grid(7,2);
		mail.addStyleName("layout");
		mail.setHTML(0, 0, Sfa.constant().mail());
		mail.setHTML(0, 1, Sfa.constant().whiteSpace());
		mail.setHTML(1, 0, Sfa.constant().titulo());
		mail.setWidget(1, 1, editarSSUIData.getTitulo());
        mail.setHTML(2, 0,Sfa.constant().comentAnalista() );
		mail.setWidget(2, 1, editarSSUIData.getComentarioAnalista());
		mail.setHTML(3, 0, Sfa.constant().notaAdicional());
     	mail.setWidget(3, 1,editarSSUIData.getNotaAdicional());
		mail.setHTML(4, 0,  Sfa.constant().enviarA());
		mail.setWidget(4, 1,editarSSUIData.getEnviarA());
	    mail.setWidget(5, 0, new CheckBox("Enviar"));
	    mail.setHTML(5, 1, Sfa.constant().whiteSpace());
	    mail.setHTML(6, 0, Sfa.constant().whiteSpace());
	    mail.setWidget(6, 1, new Button("Cancelar Cambio"));
	    cambiarEstadoPanel.add(mail);
		return cambiarEstadoPanel;
	}

	private Widget getEstadoTable() {
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("detalleSSTableWrapper mlr5");
		cambiarEstadoSS = new Grid(1, 3);
		String[] titles = { "Estado", "Fecha", "Usuario"};
		for (int i = 0; i < titles.length; i++) {
			cambiarEstadoSS.setHTML(0, i, titles[i]);
		}
		cambiarEstadoSS.setCellPadding(0);
		cambiarEstadoSS.setCellSpacing(0);
		cambiarEstadoSS.addStyleName("dataTable");
		cambiarEstadoSS.setWidth("98%");
		cambiarEstadoSS.getRowFormatter().addStyleName(0, "header");
		wrapper.add(cambiarEstadoSS);
		return wrapper;
	}

	

	

//	/** Realiza la actualizacion visual necesaria para mostrar los datos correctos */
//	public void refresh() {
//		int row = 1;
//		double[] totales = { 0, 0, 0, 0, 0, 0, 0, 0 };
//		resumenSS.resizeRows(row);
//		for (LineaSolicitudServicioDto linea : editarSSUIData.getLineasSolicitudServicio()) {
//			linea.refreshPrecioServiciosAdicionales();
//			resumenSS.resizeRows(row + 1);
//			resumenSS.setHTML(row, 0, linea.getItem().getDescripcion());
//			resumenSS.setHTML(row, 1, "" + linea.getCantidad());
//			totales[1] = totales[1] + linea.getCantidad();
//			resumenSS.setHTML(row, 2, linea.getTipoSolicitud().getTipoSolicitudBase() != null ? linea
//					.getTipoSolicitud().getTipoSolicitudBase().getFormaContratacion() : "");
//			resumenSS.setHTML(row, 3, currencyFormat.format(linea.getPrecioVenta()
//					+ linea.getPrecioAlquilerVenta()));
//			//MGR - #1135
//			totales[3] = totales[3] + (linea.getPrecioVenta() * linea.getCantidad() ) 
//							+ (linea.getPrecioAlquilerVenta() * linea.getCantidad());
//			resumenSS.setHTML(row, 4, linea.getPlan() != null ? linea.getPlan().getDescripcion() : "");
//			resumenSS.setHTML(row, 5, currencyFormat.format(linea.getPrecioVentaPlan()));
//			totales[5] = totales[5] + linea.getPrecioVentaPlan();
//			resumenSS.setHTML(row, 6, currencyFormat.format(linea.getPrecioGarantiaVenta()));
//			totales[6] = totales[6] + linea.getPrecioGarantiaVenta();
//			resumenSS.setHTML(row, 7, currencyFormat.format(linea.getPrecioServiciosAdicionalesVenta()));
//			totales[7] = totales[7] + linea.getPrecioServiciosAdicionalesVenta();
//			row++;
//		}
//		resumenSS.resizeRows(row + 1);
//		resumenSS.setHTML(row, 0, "TOTAL");
//		resumenSS.setHTML(row, 1, "" + Math.round(totales[1]));
//		resumenSS.setHTML(row, 3, currencyFormat.format(totales[3]));
//		resumenSS.setHTML(row, 5, currencyFormat.format(totales[5]));
//		resumenSS.setHTML(row, 6, currencyFormat.format(totales[6]));
//		resumenSS.setHTML(row, 7, currencyFormat.format(totales[7]));
//
//		editarSSUIData.recarcularValores();
//	}

}

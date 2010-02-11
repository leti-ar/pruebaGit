package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.ComentarioDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

public class CuentaNotasForm extends Composite {

	private FlowPanel mainPanel;
	private FlexTable datosTabla = new FlexTable();
	private FlowPanel panelComentario = new FlowPanel();
	private HTML mainHtml = new HTML();
	private HTML htmlTitulo = new HTML();
	private HTML htmlComentario = new HTML();
	
	private List<ComentarioDto> listaComentarios = new ArrayList<ComentarioDto>();
	private static CuentaNotasForm instance = null;

	public static CuentaNotasForm getInstance() {
		if (instance == null) {
			instance = new CuentaNotasForm();
		}
		return instance;
	}

	private CuentaNotasForm() {
		mainPanel = new FlowPanel();
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		initWidget(mainPanel);
		
		datosTabla.setWidth("100%");
		datosTabla.addTableListener(new Listener());
		initTable(datosTabla);
		
		initPanelComentario(panelComentario);

//		mainPanel.getColumnFormatter().addStyleName(0, "alignRight");
//		mainPanel.getRowFormatter().addStyleName(0, "alignRight");
		mainPanel.add(datosTabla);
		mainPanel.add(panelComentario);
	}

	private void initTable(FlexTable table) {

		for (int col = 0; col < 4; col++) {
			table.getColumnFormatter().setWidth(col, "25%");
		}
		table.getColumnFormatter().addStyleName(0, "alignLeft");
		table.getColumnFormatter().addStyleName(1, "alignLeft");
		table.getColumnFormatter().addStyleName(2, "alignLeft");
		table.getColumnFormatter().addStyleName(3, "alignLeft");
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().fecha());
		table.setHTML(0, 1, Sfa.constant().origen());
		table.setHTML(0, 2, Sfa.constant().titulo());
		table.setHTML(0, 3, Sfa.constant().usuario());
	}
	
	private void initPanelComentario(FlowPanel panel) {
		panel.setWidth("100%");
		panel.addStyleName("fondoGris pb5");
		
		mainHtml.setStyleName("fontNormalGris m5");
		mainHtml.setText(Sfa.constant().titulo());
		htmlTitulo.addStyleName("fontNormalGris");
		htmlComentario.addStyleName("fontNormalGris");
		htmlTitulo.addStyleName("htmlTituloAreaCuentaNotas");
		htmlComentario.addStyleName("htmlComentarioAreaCuentaNotas");
		
		panel.add(mainHtml);
		panel.add(htmlTitulo);
		panel.add(htmlComentario);
	}

	public void cargarTabla() {
		int row = 1;
		while (datosTabla.getRowCount() > 1) {
			datosTabla.removeRow(1);
		}
		
		htmlTitulo.setText("");
		htmlComentario.setText("");
		
		if (listaComentarios != null) {
			for (Iterator iter = listaComentarios.iterator(); iter.hasNext();) {
				ComentarioDto comentario = (ComentarioDto) iter.next();
				datosTabla.setHTML(row, 0, DateTimeFormat.getMediumDateFormat()
						.format(comentario.getFechaAlta()));
				datosTabla.setHTML(row, 1, comentario.getAreaOrigen());
				datosTabla.setHTML(row, 2, comentario.getTitulo());
				datosTabla.setHTML(row, 3, comentario.getUsuario());
				row++;
			}
		}
	}

	private class Listener implements TableListener {
		public void onCellClicked(SourcesTableEvents arg0, int row, int columna) {
			// Cambia la tabla de abajo
			ComentarioDto comentario = (ComentarioDto) listaComentarios.get(row - 1);
			htmlTitulo.setText(comentario.getTitulo());
			htmlComentario.setText(comentario.getTextoComentario());
		}
	}
	
	public void cargarTablaCuentas(CuentaPotencialDto cuentaPotencialDto) {
		listaComentarios.clear();
		listaComentarios.addAll(cuentaPotencialDto.getComentarios());
		cargarTabla();
	}

	public List<ComentarioDto> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(List<ComentarioDto> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}

}

package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.constant.Sfa;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class CuentaContactoForm extends Composite {

	private FlexTable mainPanel;

	private static CuentaContactoForm instance = null;

	public static CuentaContactoForm getInstance() {
		if(instance == null){
			instance = new CuentaContactoForm();
		}
		return instance;
	}

	private CuentaContactoForm() {

		mainPanel = new FlexTable();
		mainPanel.setWidth("100%");
		FlexTable datosTabla = new FlexTable();
		initWidget(mainPanel);
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		mainPanel.setCellPadding(20);
		Button crear = new Button("Crear nuevo");
		crear.addStyleName("crearDomicilioButton");
		mainPanel.setWidget(0, 0, crear);
		initTable(datosTabla);
		mainPanel.getColumnFormatter().addStyleName(0, "alignRight");
		mainPanel.getRowFormatter().addStyleName(0, "alignRight");
		mainPanel.setWidget(1, 0, datosTabla);

		crear.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				/**TODO: Antes aca deberias setear la accion a tomar cuando se apreta eel boton Aceptar, dependiendo si es Copiar o Nuevo contacto.
				 * (crear un metodo que setee el comando en el ContactosUI, y setearlo!)*/
				ContactosUI.getInstance().cargaPopupNuevoContacto();
			}
		});

	}

	private void initTable(FlexTable table) {

		String[] widths = { "24px", "24px", "150px", "150px", "150px", "65%", };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().whiteSpace());
		table.setHTML(0, 2, Sfa.constant().nombre());
		table.setHTML(0, 3, Sfa.constant().apellido());
		table.setHTML(0, 4, Sfa.constant().telefono());
		table.setHTML(0, 5, Sfa.constant().whiteSpace());

	}
}

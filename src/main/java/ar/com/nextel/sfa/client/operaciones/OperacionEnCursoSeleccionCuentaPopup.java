package ar.com.nextel.sfa.client.operaciones;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.cuenta.CuentaClientService;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class OperacionEnCursoSeleccionCuentaPopup extends NextelDialog {
	private NextelTable resultTable;
	private SimplePanel resultTableWrapper;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
	private List<CuentaDto> cuentas;

	private static OperacionEnCursoSeleccionCuentaPopup seleccionCuentaPopUp = null;

	ClickListener listener = new ClickListener() {
		public void onClick(Widget sender) {
			if (sender == aceptar) {
				Long idCuenta = null;
				if (resultTable != null && resultTable.getRowSelected() > 0) {
					idCuenta = cuentas.get(resultTable.getRowSelected() - 1).getId();
				}
				if (idCuenta == null) {
					MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_DIALOG_TITLE(),
							Sfa.constant().ERR_NO_CUENTA_SELECTED(), MessageDialog.getCloseCommand());
				} else {
					CuentaClientService.cargarDatosCuenta(idCuenta, null);
					hide();
				}
			} else if (sender == cancelar) {
				hide();
			}
		}
	};

	public static OperacionEnCursoSeleccionCuentaPopup getInstance() {
		if (seleccionCuentaPopUp == null) {
			seleccionCuentaPopUp = new OperacionEnCursoSeleccionCuentaPopup();
		}
		return seleccionCuentaPopUp;
	}

	private OperacionEnCursoSeleccionCuentaPopup() {
		super(Sfa.constant().LBL_SELECCION_CUENTA(), false, true);
		init();
	}

	private void init() {
		resultTableWrapper = new SimplePanel();
		resultTableWrapper.addStyleName("resultTableWrapper");

		resultTable = new NextelTable();
		resultTableWrapper.add(resultTable);
		Label seleCta = new Label(Sfa.constant().MSG_SELECCION_CUENTA());
		seleCta.addStyleName("fontNormalGris");
		add(new HTML("<br/>"));
		add(seleCta);
		add(resultTableWrapper);

		aceptar = new SimpleLink(Sfa.constant().aceptar());
		aceptar.addClickListener(listener);

		cancelar = new SimpleLink(Sfa.constant().cancelar());
		cancelar.addClickListener(listener);

		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
	}

	/**
	 *     
	 */
	public void cargarPopup() {
		aceptar.setVisible(true);
		showAndCenter();
	}

	public void loadTable() {
		resultTable.clearContent();
		initTable(resultTable);
		int row = 1;
		for (CuentaDto cuenta : cuentas) {
			resultTable.setHTML(row, 0, cuenta.getCodigoVantive());
			resultTable.setHTML(row, 1, cuenta.getPersona().getRazonSocial());
			resultTable.setHTML(row, 2, cuenta.getPersona().getApellido());
			resultTable.setHTML(row, 3, (((TelefonoDto) (cuenta.getPersona().getTelefonos()).get(0))
					.getArea() != null ? "("
					+ ((TelefonoDto) (cuenta.getPersona().getTelefonos()).get(0)).getArea() + ") " : "")
					+ ((TelefonoDto) (cuenta.getPersona().getTelefonos()).get(0)).getNumeroLocal());
			resultTable.setHTML(row, 4, /* credito fide ??? */"");
			row++;
		}
		setVisible(true);
	}

	private void initTable(FlexTable table) {
		String[] widths = { "140px", "180px", "120px", "100px", "120px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.setHTML(0, 0, Sfa.constant().numeroCliente());
		table.setHTML(0, 1, Sfa.constant().razonSocial());
		table.setHTML(0, 2, Sfa.constant().responsable());
		table.setHTML(0, 3, Sfa.constant().telefono());
		table.setHTML(0, 4, Sfa.constant().creditoFidel());
	}

	public void setCuentas(List<CuentaDto> cuentas) {
		this.cuentas = cuentas;
	}

	public List<CuentaDto> getCuentas() {
		return this.cuentas;
	}
}

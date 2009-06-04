package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Esta página contiene el formulario de busquedas de cuentas (BuscarCuentaFilterForm) y la tabla de
 * resultados (BuscarCuentaResultPanel)
 * 
 * @author jlgperez
 * 
 */
public class BuscarCuentaUI extends ApplicationUI implements BuscarCuentaController, ClickListener {

	protected boolean firstLoad = true;
	private BuscarCuentaFilterUI buscadorCuentaFilterForm;
	private BuscarCuentaResultUI buscarCuentaResultPanel;
	private FormButtonsBar formButtonsBar;
	private SimpleLink crearSSButton;
	private SimpleLink agregarCuentaButton;
	private PopupPanel popupCrearSS;
	private PopupPanel popupAgregarCuenta;
	private Hyperlink crearEquipos;
	private Hyperlink crearCDW;
	private Hyperlink crearMDS;
	private Hyperlink agregarDivision;
	private Hyperlink agregarSuscriptor;
	public static final String ID_CUENTA = "idCuenta";

	public BuscarCuentaUI() {
		super();
	}

	public void load() {
		if (firstLoad) {
			firstLoad = false;
			buscadorCuentaFilterForm = new BuscarCuentaFilterUI(this);
			buscarCuentaResultPanel = new BuscarCuentaResultUI(this);
			buscadorCuentaFilterForm.setBuscarCuentaResultPanel(buscarCuentaResultPanel);

			mainPanel.add(buscadorCuentaFilterForm);
			mainPanel.add(buscarCuentaResultPanel);
			mainPanel.addStyleName("gwt-central-panel");

			formButtonsBar = new FormButtonsBar();
			formButtonsBar.setVisible(false);
			mainPanel.add(formButtonsBar);
			formButtonsBar.addStyleName("mt10");
			crearSSButton = new SimpleLink("^Crear SS", "#", true);
			agregarCuentaButton = new SimpleLink("^Agregar", "#", true);
			formButtonsBar.addLink(crearSSButton);
			formButtonsBar.addLink(agregarCuentaButton);
			crearSSButton.addClickListener(this);
			agregarCuentaButton.addClickListener(this);

			popupCrearSS = new PopupPanel(true);
			popupAgregarCuenta = new PopupPanel(true);
			popupCrearSS.addStyleName("dropUpStyle");
			popupAgregarCuenta.addStyleName("dropUpStyle");

			FlowPanel linksCrearSS = new FlowPanel();
			linksCrearSS.add(crearEquipos = new Hyperlink("Equipos/Accesorios", "" + UILoader.BUSCAR_CUENTA));
			linksCrearSS.add(crearCDW = new Hyperlink("CDW", "" + UILoader.BUSCAR_CUENTA));
			linksCrearSS.add(crearMDS = new Hyperlink("MDS", "" + UILoader.BUSCAR_CUENTA));
			popupCrearSS.setWidget(linksCrearSS);
			crearEquipos.addClickListener(this);
			crearCDW.addClickListener(this);
			crearMDS.addClickListener(this);

			FlowPanel linksAgregarCuenta = new FlowPanel();
			linksAgregarCuenta.add(agregarDivision = new Hyperlink("División", "" + UILoader.BUSCAR_CUENTA));
			linksAgregarCuenta.add(agregarSuscriptor = new Hyperlink("Suscriptor", ""
					+ UILoader.BUSCAR_CUENTA));
			popupAgregarCuenta.setWidget(linksAgregarCuenta);
			agregarDivision.addClickListener(this);
			agregarSuscriptor.addClickListener(this);

		}
	}

	public void onClick(Widget sender) {
		Long idCuenta = buscarCuentaResultPanel.getSelectedCuentaId();
		if (sender == crearSSButton) {
			if (idCuenta != null) {
				String targetHistoryToken = UILoader.AGREGAR_SOLICITUD + "?" + ID_CUENTA + "=" + idCuenta;
				crearEquipos.setTargetHistoryToken(targetHistoryToken);
				// crearCDW.setTargetHistoryToken(targetHistoryToken);
				// crearMDS.setTargetHistoryToken(targetHistoryToken);
				popupCrearSS.show();
				popupCrearSS.setPopupPosition(crearSSButton.getAbsoluteLeft() - 10, crearSSButton
						.getAbsoluteTop() - 50);
			} else {
				MessageDialog.getInstance().showAceptar("Error", "Debe seleccionar una Cuenta",
						MessageDialog.getCloseCommand());
			}
		} else if (sender == agregarCuentaButton) {
			popupAgregarCuenta.show();
			popupAgregarCuenta.setPopupPosition(agregarCuentaButton.getAbsoluteLeft(), agregarCuentaButton
					.getAbsoluteTop() - 35);
		} else if (sender == crearEquipos || sender == crearCDW || sender == crearMDS) {
			popupCrearSS.hide();
		} else if (sender == agregarDivision || sender == agregarSuscriptor) {
			popupAgregarCuenta.hide();
		}

	}

	public void unload() {
	}

	public void searchCuentas(CuentaSearchDto cuentaSearchDto) {
		buscarCuentaResultPanel.searchCuentas(cuentaSearchDto);
		formButtonsBar.setVisible(true);
	}
}

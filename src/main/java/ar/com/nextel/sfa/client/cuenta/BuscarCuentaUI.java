package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.ss.EditarSSUI;
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

	private BuscarCuentaFilterUI buscadorCuentaFilterForm;
	private BuscarCuentaResultUI buscarCuentaResultPanel;
	private FormButtonsBar formButtonsBar;
	private SimpleLink crearSSButton;
	private SimpleLink agregarCuentaButton;
	private PopupPanel popupCrearSS;
	private PopupPanel popupAgregarCuenta;
	private Hyperlink crearEquipos;
	private Hyperlink crearCDW;
	// private Hyperlink crearMDS;
	private Hyperlink agregarDivision;
	private Hyperlink agregarSuscriptor;

	public BuscarCuentaUI() {
		super();
	}

	public void firstLoad() {
		buscadorCuentaFilterForm = new BuscarCuentaFilterUI(this);
		buscarCuentaResultPanel = new BuscarCuentaResultUI(this);

		mainPanel.add(buscadorCuentaFilterForm);
		mainPanel.add(buscarCuentaResultPanel);
		mainPanel.addStyleName("gwt-central-panel");

		formButtonsBar = new FormButtonsBar();
		formButtonsBar.setVisible(false);
		mainPanel.add(formButtonsBar);
		formButtonsBar.addStyleName("mt10");
		crearSSButton = new SimpleLink(Sfa.constant().crearSS(), "#", true);
		agregarCuentaButton = new SimpleLink(Sfa.constant().agregarDivSusc(), "#", true);
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
		// linksCrearSS.add(crearMDS = new Hyperlink("MDS", "" + UILoader.BUSCAR_CUENTA));
		popupCrearSS.setWidget(linksCrearSS);
		crearEquipos.addClickListener(this);
		crearCDW.addClickListener(this);
		// crearMDS.addClickListener(this);

		FlowPanel linksAgregarCuenta = new FlowPanel();
		linksAgregarCuenta.add(agregarDivision = new Hyperlink(Sfa.constant().division(), ""
				+ UILoader.BUSCAR_CUENTA));
		linksAgregarCuenta.add(agregarSuscriptor = new Hyperlink(Sfa.constant().suscriptor(), ""
				+ UILoader.BUSCAR_CUENTA));
		popupAgregarCuenta.setWidget(linksAgregarCuenta);
		agregarDivision.addClickListener(this);
		agregarSuscriptor.addClickListener(this);

	}

	public boolean load() {
		buscadorCuentaFilterForm.cleanAndEnableFields();
		buscarCuentaResultPanel.setVisible(false);
		formButtonsBar.setVisible(false);
		return true;
	}

	public void onClick(Widget sender) {
		//Long idCuenta = buscarCuentaResultPanel.getSelectedCuentaId();
		CuentaSearchResultDto cuentaSearch = buscarCuentaResultPanel.getSelectedCuenta();
		Long idCuenta = cuentaSearch!=null?cuentaSearch.getId():null;
		if (idCuenta != null) {
			if (sender == crearSSButton) {
				if (cuentaSearch.getRazonSocial().equals("***")) {
					MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_DIALOG_TITLE(), Sfa.constant().ERR_NO_ACCESO_CREAR_SS(),	MessageDialog.getCloseCommand());
				} else {
					crearEquipos.setTargetHistoryToken(EditarSSUI.getEditarSSUrl(idCuenta,	GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS, null));
					crearCDW.setTargetHistoryToken(EditarSSUI.getEditarSSUrl(idCuenta, GrupoSolicitudDto.ID_CDW, null));
					// crearMDS.setTargetHistoryToken(getEditarSSUrl(idCuenta, GrupoSolicitudDto.ID_MDS));
					popupCrearSS.show();
					popupCrearSS.setPopupPosition(crearSSButton.getAbsoluteLeft() - 10, crearSSButton.getAbsoluteTop() - 50);
				}
			} else if (sender == agregarCuentaButton) {
				if (cuentaSearch.getRazonSocial().equals("***")) {
					MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_DIALOG_TITLE(), Sfa.constant().ERR_NO_ACCESO_CUENTA(), MessageDialog.getCloseCommand());
				} else {
					popupAgregarCuenta.show();
					popupAgregarCuenta.setPopupPosition(agregarCuentaButton.getAbsoluteLeft(), agregarCuentaButton.getAbsoluteTop() - 35);
				}
			} else if (sender == crearEquipos || sender == crearCDW) { // || sender == crearMDS
				popupCrearSS.hide();
			} else if (sender == agregarDivision) {
				popupAgregarCuenta.hide();
				CuentaClientService.crearDivision(idCuenta);
			} else if (sender == agregarSuscriptor) {
				popupAgregarCuenta.hide();
				CuentaClientService.crearSuscriptor(idCuenta);
			}
		} else {
			MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_DIALOG_TITLE(), Sfa.constant().ERR_NO_CUENTA_SELECTED(),	MessageDialog.getCloseCommand());
		}
	}

	public boolean unload(String token) {
		return true;
	}

	public void searchCuentas(CuentaSearchDto cuentaSearchDto) {
		buscarCuentaResultPanel.searchCuentas(cuentaSearchDto);

	}

	public void setResultadoVisible(boolean visible) {
		buscarCuentaResultPanel.setVisible(visible);
		formButtonsBar.setVisible(visible);
	}
}

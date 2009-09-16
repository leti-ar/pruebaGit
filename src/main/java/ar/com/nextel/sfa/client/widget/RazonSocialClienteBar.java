package ar.com.nextel.sfa.client.widget;

import java.util.HashMap;
import java.util.Map;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.cuenta.CuentaClientService;
import ar.com.nextel.sfa.client.image.IconFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;

public class RazonSocialClienteBar extends Composite {

	private FlowPanel left;
	private InlineHTML razonSocial;
	private InlineHTML cliente;
	private HTML cuentaLink;
	private Map<String, String> params;

	// private String url = "" + UILoader.EDITAR_CUENTA;

	public RazonSocialClienteBar() {
		left = new FlowPanel();
		initWidget(left);
		Grid right = new Grid(1, 3);
		left.add(right);
		addStyleName("gwt-RazonSocialClienteBar");
		right.addStyleName("right");
		left.add(new InlineLabel(Sfa.constant().razonSocial() + ": "));
		left.add(razonSocial = new InlineHTML());
		// cuentaLink = IconFactory.silvioSoldanAnchor(url);
		cuentaLink = IconFactory.silvioSoldan();
		cuentaLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				CuentaClientService.cargarDatosCuenta(Long.parseLong(params.get("cuenta_id")), null);
			}
		});
		right.setWidget(0, 0, cuentaLink);
		right.setWidget(0, 1, new InlineLabel(Sfa.constant().cliente() + ": "));
		right.setWidget(0, 2, cliente = new InlineHTML());
		right.getCellFormatter().setWidth(0, 2, "180px");
	}

	public void setDisabledSilvioSoldan() {
		cuentaLink.setVisible(false);
	}
	
	public void setEnabledSilvioSoldan() {
		cuentaLink.setVisible(true);
	}
	
	public void setRazonSocial(String razonSocial) {
		this.razonSocial.setText(razonSocial);
	}

	public void setCliente(String cliente) {
		this.cliente.setText(cliente);
	}

	public void setIdCuenta(Long idCuenta, Long idVantive) {
		params = new HashMap<String, String>();
		if (idCuenta != null) {
			params.put("cuenta_id", idCuenta.toString());
		}
		if (idVantive != null) {
			params.put("idVantive", idVantive.toString());
		}
		// cuentaLink.setTargetHistoryToken(UILoader.EDITAR_CUENTA + HistoryUtils.getParamsFromMap(params));
	}
}

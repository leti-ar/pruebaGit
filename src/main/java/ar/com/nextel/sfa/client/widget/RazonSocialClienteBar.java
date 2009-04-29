package ar.com.nextel.sfa.client.widget;

import java.util.HashMap;
import java.util.Map;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;

public class RazonSocialClienteBar extends Composite {

	private FlowPanel left;
	private HTML razonSocial;
	private HTML cliente;
	private SimpleLink cuentaLink;

	public RazonSocialClienteBar() {
		left = new FlowPanel();
		initWidget(left);
		FlowPanel right = new FlowPanel();
		left.add(right);
		addStyleName("gwt-RazonSocialClienteBar");
		right.addStyleName("right");
		left.add(new InlineLabel(Sfa.constant().razonSocial()));
		left.add(razonSocial = new HTML());
		cuentaLink = new SimpleLink(IconFactory.silvioSoldan(), "#" + UILoader.EDITAR_CUENTA, false);
		right.add(cuentaLink);
		right.add(new InlineLabel(Sfa.constant().cliente()));
		right.add(cliente = new HTML());

	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial.setText(razonSocial);
	}

	public void setCliente(String cliente) {
		this.cliente.setText(cliente);
	}

	public void setIdCuenta(Long idCuenta, Long idVantive) {
		Map<String, String> params = new HashMap<String, String>();
		if (idCuenta != null) {
			params.put("cuenta_id", idCuenta.toString());
		}
		if (idVantive != null) {
			params.put("idVantive", idVantive.toString());
		}
		cuentaLink.setTargetHistoryToken(UILoader.EDITAR_CUENTA + HistoryUtils.getParamsFromMap(params));
	}
}

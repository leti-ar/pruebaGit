package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.image.IconFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;

public class RazonSocialClienteBar extends Composite{

	private FlowPanel left;
	private HTML razonSocial;
	private HTML cliente;
	
	public RazonSocialClienteBar() {
		left = new FlowPanel();
		initWidget(left);
		FlowPanel right = new FlowPanel();
		left.add(right);
		addStyleName("gwt-RazonSocialClienteBar");
		right.addStyleName("right");
		left.add(new InlineLabel(Sfa.constant().razonSocial()));
		left.add(razonSocial = new HTML());
		right.add(IconFactory.silvioSoldan());
		right.add(new InlineLabel(Sfa.constant().cliente()));
		right.add(cliente = new HTML());
	}
	
	public void setRazonSocial(String razonSocial){
		this.razonSocial.setText(razonSocial);
	}
	
	public void setCliente(String cliente){
		this.cliente.setText(cliente);
	}
}

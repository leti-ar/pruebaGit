package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.image.IconFactory;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;

public class ExportarRemitoSSResultUI extends FlowPanel {

	private HTML icon;
	private InlineHTML numResultados = new InlineHTML("Número de Resultados: ");
	
	public ExportarRemitoSSResultUI() {
		this.setHeight("30px");
		icon = IconFactory.excel();
		icon.addStyleName("exportarExcelSS mr10");
		numResultados.setWidth("200px");
		numResultados.addStyleName("floatLeft");
		numResultados.addStyleName("mt5");
		add(numResultados);
		add(icon);
	}
	
	public InlineHTML getNumResultados() {
		return numResultados;
	}
	public void setNumResultados(String numResultados) {
		this.numResultados.setText(numResultados);
	}
	public HTML getIcon() {
		return icon;
	}
	public void setIcon(HTML icon) {
		this.icon = icon;
	}
					
}

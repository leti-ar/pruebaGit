package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

/**
 * Esta es la clase "padre" de BuscarSSCerradasUI y BuscarSSAnalistaCreditosUI, todo las operaciones que haga 
 * esta clase la utilizarán sus hijos, como al exportacion a excel, la grilla de estados, etc..
 *
 * @author fernaluc
 *
 */

public abstract class BuscarSSGenericoUI extends ApplicationUI{

	protected ExportarExcelSSResultUI exportarExcelSSResultUI;
	protected BuscarSSCerradasResultUI buscarSSCerradasResultPanel;
	protected BuscarSSTotalesResultUI buscarSSTotalesResultUI;
	protected CambiosSSCerradasResultUI cambiosSSCerradasResultUI;
	
	public abstract boolean esAnalistaCreditos();
	
	@Override
	public void firstLoad() {
		buscarSSTotalesResultUI = new BuscarSSTotalesResultUI();    
		exportarExcelSSResultUI = new ExportarExcelSSResultUI();   
		cambiosSSCerradasResultUI = new CambiosSSCerradasResultUI();
		buscarSSTotalesResultUI.setVisible(false);
		exportarExcelSSResultUI.setVisible(false);
		cambiosSSCerradasResultUI.setVisible(false);
		buscarSSCerradasResultPanel.setExportarExcelSSResultUI(exportarExcelSSResultUI);
		buscarSSCerradasResultPanel.setBuscarSSTotalesResultUI(buscarSSTotalesResultUI);
		buscarSSCerradasResultPanel.setCambiosSSCerradasResultUI(cambiosSSCerradasResultUI);
		
		mainPanel.add(exportarExcelSSResultUI);
		mainPanel.add(buscarSSCerradasResultPanel);
		mainPanel.add(buscarSSTotalesResultUI);
		mainPanel.add(cambiosSSCerradasResultUI);
		mainPanel.addStyleName("gwt-central-panel");
	}
	
	@Override
	public boolean unload(String token) {
		return true;
	}
}

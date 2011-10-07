package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.widget.EventWrapper;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;

public class BusqClienteCedenteUI extends Composite{
	private FlowPanel mainPanel;
	private Grid busqLayout;
	private Command buscarCommand;
	
	private BusqClienteCedenteUIData busqClienteCedenteUIData;

	public BusqClienteCedenteUI(BusqClienteCedenteUIData busqClienteCedenteUIData){
		this.busqClienteCedenteUIData = busqClienteCedenteUIData;
		
		mainPanel = new FlowPanel();
		initWidget(mainPanel);
		initGenericPanel();
	}
	
	private void initGenericPanel(){
		busqLayout = new Grid(1,2);
		busqLayout.addStyleName("layout");
		busqLayout.getCellFormatter().setWidth(0, 0, "100px");
		busqLayout.setWidget(0, 0, busqClienteCedenteUIData.getCriterioBusqCedente());
		busqLayout.setWidget(0, 1, busqClienteCedenteUIData.getParametroBusqCedente());
		
		EventWrapper eventWrapper = new EventWrapper() {
			//#1422: "Enter" en Busqueda de Cuenta Cedente
			public void doEnter() {
				List<String> errores = busqClienteCedenteUIData.validarCriterioBusqueda();
				if(errores.isEmpty()){
					buscarCommand.execute();
				}else{
					StringBuilder error = new StringBuilder();
					for (int i = 0; i < errores.size(); i++) {
						error.append(errores.get(i) + "<br />");
					}
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(error.toString(), false);
				}
			}
		};
		eventWrapper.add(busqLayout);
		mainPanel.add(busqLayout);
	}
	
	public void setBuscarCommand(Command buscarCommand) {
		this.buscarCommand = buscarCommand;
	}
	
}

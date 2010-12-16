package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class BusqClienteCedenteDialog extends NextelDialog implements ChangeHandler, ClickListener{
	
	private SimplePanel busqClienteCedentePanel;
	private SimpleLink buscar;
	private Command buscarCommand;
	private SimpleLink cancelar;
	
	private BusqClienteCedenteUI busqClienteCedenteUI;
	private BusqClienteCedenteUIData busqClienteCedenteUIData;
	private EditarSSUIController controller;
	
	public BusqClienteCedenteDialog(String title, EditarSSUIController controller) {
		super(title, false, true);
		addStyleName("gwt-ItemSolicitudDialog");
		this.controller = controller;
		this.busqClienteCedenteUIData = new BusqClienteCedenteUIData(controller);
		
		busqClienteCedentePanel = new SimplePanel();
		busqClienteCedentePanel.add(getBusqClienteCedenteUI());
		
		buscar = new SimpleLink("Buscar");
		cancelar = new SimpleLink("Cancelar");
		buscar.addClickListener(this);
		cancelar.addClickListener(this);
		//buscarCommand = getBuscarCommand();
		
		add(busqClienteCedentePanel);
		addFormButtons(buscar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
	}

	private BusqClienteCedenteUI getBusqClienteCedenteUI(){
		if(busqClienteCedenteUI == null){
			busqClienteCedenteUI = new BusqClienteCedenteUI(this.busqClienteCedenteUIData);
		}
		return busqClienteCedenteUI;
	}

	public void setBuscarCommand(Command command){
		buscarCommand = command;
	}
//	private Command getBuscarCommand(){
//		return new Command() {
//			public void execute() {
//				CuentaRpcService.Util.getInstance().searchCuenta(busqClienteCedenteUIData.getCuentaSearch(),
//						new DefaultWaitCallback<List<CuentaSearchResultDto>>() {
//							public void success(List<CuentaSearchResultDto> result) {
//								if (result.isEmpty()) {
//									ErrorDialog.getInstance().show(
//											"No se encontraron datos con el criterio utilizado.", false);
//								}
//								
//								CuentaSearchResultDto ctaSearch = result.get(0);
//								controller.getEditarSSUIData().getClienteCedente().setText(ctaSearch.getNumero());
//								String text = "";
//								for (CuentaSearchResultDto cuentaSearchResultDto : result) {
//									text += cuentaSearchResultDto.getCodigoVantive() + " " + 
//											cuentaSearchResultDto.getRazonSocial() + "/n";
//								}
//								controller.getEditarSSUIData().getObservaciones().setText(text);
//								
//							}
//						});
//				hide();
//			}
//		};
//	}
	
	public BusqClienteCedenteUIData getBusqClienteCedenteUIData() {
		return busqClienteCedenteUIData;
	}
	
	public void onChange(ChangeEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void onClick(Widget sender) {
		if(sender == buscar){
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
		else if(sender ==  cancelar){
			this.hide();
		}
	}
	
	public void mostrarDialogo(){
		busqClienteCedenteUIData.getParametroBusqCedente().setText("");
		this.showAndCenter();
	}
}

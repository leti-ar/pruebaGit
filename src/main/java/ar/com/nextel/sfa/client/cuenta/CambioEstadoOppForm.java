package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.RadioButtonWithValue;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;


public class CambioEstadoOppForm extends NextelDialog {

	private FlexTable  mainTable;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
    private CuentaUIData cuentaData = CuentaDatosForm.getInstance().getCamposTabDatos();	
    private FlexTable radioOpsTable = new FlexTable();
    private Command aceptarCommand;
	
	private static CambioEstadoOppForm cambioEstadoPopUp = null;

	ClickListener listener = new ClickListener(){
		public void onClick(Widget sender){
		if(sender == aceptar){
			List errors = null;
			if(errors==null) {
            	 GWT.log(cuentaData.getRadioGroupMotivos().getLabelChecked() +
            			 " : " +
            			 cuentaData.getRadioGroupMotivos().getValueChecked(),null);
//				aceptarCommand.execute();
				hide();
			} else {
				ErrorDialog.getInstance().show(errors);
			}
		}
		else if(sender == cancelar){
			hide();
		}

	}};

    public static CambioEstadoOppForm getInstance() {
    	if (cambioEstadoPopUp==null) {
    		cambioEstadoPopUp = new CambioEstadoOppForm();
    	}
    	return cambioEstadoPopUp;
    }
	
	private  CambioEstadoOppForm() {
		super("Cambiar Estado");
		init();
	}

	private void init() {
		armarRadioOption();
		radioOpsTable.setCellSpacing(5);
		setWidth("600px");
		FlexTable marco = new FlexTable();
		marco.setCellSpacing(25);
		mainTable = new FlexTable();
		mainTable.setWidth("100%");
		
		mainTable.setWidget(0, 0, cuentaData.getEstadoOpp());
		mainTable.setWidget(1, 0, radioOpsTable);
		
		marco.setWidget(0, 0, mainTable);
		add(marco);

		aceptar = new SimpleLink("Aceptar");
		aceptar.addClickListener(listener);

		cancelar = new SimpleLink("Cerrar");
		cancelar.addClickListener(listener);
		
		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
	}
    
	private void armarRadioOption() {
		int indexRow = 0;
		int indexCol = 0;
		for (RadioButtonWithValue radioBtn : cuentaData.getRadioGroupMotivos().getRadios()) {
			radioOpsTable.setWidget(indexRow,indexCol++, radioBtn);
			if (indexCol==3) {
				indexCol=0;
				indexRow++;
			}
		}
	}
	
	public void cargarPopup() {
		aceptar.setVisible(true);
		showAndCenter();
	}
	
}


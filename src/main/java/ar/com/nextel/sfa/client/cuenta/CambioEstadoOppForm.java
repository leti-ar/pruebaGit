package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.OperacionesRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.MotivoNoCierreDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.enums.EstadoOportunidadEnum;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;


public class CambioEstadoOppForm extends NextelDialog {

	private FlexTable  mainTable;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
    private CuentaUIData cuentaData = CuentaDatosForm.getInstance().getCamposTabDatos();
    private FlexTable blankTable = new FlexTable();
    
	private static CambioEstadoOppForm cambioEstadoPopUp = null;

	ClickListener listener = new ClickListener() {
		public void onClick(Widget sender) {
			if(sender == aceptar) {
				if (CuentaDatosForm.getInstance().hayOperacionesEnCurso) {
					MessageDialog.getInstance().showAceptarCancelar(Sfa.constant().ERR_DIALOG_TITLE(), Sfa.constant().MSG_EXISTE_SS(), getAceptarCommand(), MessageDialog.getInstance().getCloseCommand());
				} else {
					updateEstadoOportunidad();
					MessageDialog.getInstance().hide();
				}
			}else if(sender == cancelar){
				hide();
			}
		}};
		
    public static CambioEstadoOppForm getInstance() {
    	if (cambioEstadoPopUp==null) {
    		cambioEstadoPopUp = new CambioEstadoOppForm();
    	}
    	return cambioEstadoPopUp;
    }

    private Command getAceptarCommand() {
    	return new Command() {
    		public void execute() {
    			if(cuentaData.getRadioGroupMotivos().getValueChecked()!=null) {
    				OperacionesRpcService.Util.getInstance().cancelarOperacionEnCurso(new Long (CuentaDatosForm.getInstance().idCuentaSolicitudOperacionEnCurso),new DefaultWaitCallback() {
    					public void success(Object result) {
    						updateEstadoOportunidad();
    						MessageDialog.getInstance().hide();
    					}
    					public void failure(Throwable caught) {
    						LoadingModalDialog.getInstance().hide();
    						super.failure(caught);
    					}
    				});
    			} else {
    				MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_DIALOG_TITLE(), Sfa.constant().ERR_NO_OPCION_SELECCIONADA(),	MessageDialog.getCloseCommand());
    			}
    		}
    	};
    }
    
	private  CambioEstadoOppForm() {
		super(Sfa.constant().LBL_CAMBIAR_ESTADO(), false, true);
		init();
	}

	private void init() {
		initBlankTable();
		cuentaData.getOppObservaciones().addStyleName("obsTextAreaCuentaDatos");
		cuentaData.getRadioOpsTable().setCellSpacing(5);
		cuentaData.getRadioOpsTable().setWidth("100%");
		cuentaData.getRadioOpsTable().setVisible(false);
		cuentaData.getEstadoOpp().addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				showHideTablaMotivos();
			}
		});
		
		setWidth("600px");
		FlexTable marco = new FlexTable();
		marco.setCellSpacing(25);
		marco.setWidth("100%");
		marco.setBorderWidth(1);
		mainTable = new FlexTable();
		mainTable.setWidth("100%");
		mainTable.setWidget(0, 0, cuentaData.getOppNroLabel());
		mainTable.setWidget(0, 1, cuentaData.getOppNroOpp());
		mainTable.getFlexCellFormatter().setWidth(0, 0, "15%");
		mainTable.setWidget(1, 0, cuentaData.getOppEstadoPopupLabel());
		mainTable.setWidget(1, 1, cuentaData.getEstadoOpp());
		mainTable.getFlexCellFormatter().setWidth(1, 0, "15%");
		mainTable.setWidget(2, 0, cuentaData.getRadioOpsTable());
		mainTable.setWidget(3, 0, blankTable);
		mainTable.getFlexCellFormatter().setColSpan(2, 0, 2);

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
	
	private void initBlankTable() {
		blankTable.setWidget(0, 0, new HTML("<br/>"));
		blankTable.getFlexCellFormatter().setHeight(0, 1, "156");
	}
	
	public void cargarPopup() {
		aceptar.setVisible(true);
		showAndCenter();
	}
	
	public void showHideTablaMotivos() {
		boolean show = cuentaData.getEstadoOpp().getSelectedItemId()!=null && cuentaData.getEstadoOpp().getSelectedItemId().equals(EstadoOportunidadEnum.NO_CERRADA.getId().toString());
		cuentaData.getRadioOpsTable().setVisible(show);
		blankTable.setVisible(!show);
	}
	
	public void updateEstadoOportunidad() {
		OportunidadNegocioDto oportunidadDto = (OportunidadNegocioDto)CuentaDatosForm.getInstance().getOportunidadDto();
        oportunidadDto.getEstadoJustificado().setEstado(new EstadoOportunidadDto(new Long(cuentaData.getEstadoOpp().getSelectedItemId()),cuentaData.getEstadoOpp().getSelectedItemText()));
        oportunidadDto.getEstadoJustificado().setMotivo(new MotivoNoCierreDto(new Long(cuentaData.getRadioGroupMotivos().getValueChecked()),cuentaData.getRadioGroupMotivos().getLabelChecked()));
        oportunidadDto.getEstadoJustificado().setObservacionesMotivo(cuentaData.getOppObservaciones().getText());
        
		CuentaRpcService.Util.getInstance().updateEstadoOportunidad(oportunidadDto,new DefaultWaitCallback<OportunidadNegocioDto>() {
			public void success(OportunidadNegocioDto oportunidadDto) {
				CuentaDatosForm.getInstance().setOportunidadDto(oportunidadDto);
				cuentaData.getOppEstado().setText(cuentaData.getEstadoOpp().getSelectedItemText());
				hide();
			}
		});
	}
}


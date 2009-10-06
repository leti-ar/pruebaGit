package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.debug.DebugConstants;
import ar.com.nextel.sfa.client.enums.TipoDocumentoEnum;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.EventWrapper;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BuscadorDocumentoPopup extends NextelDialog {
	
	private SimplePanel buscadorDocumentoPanel; 
	private FlexTable buscadorDocumentoTable;
	private FlexTable botonesTable;
	private ListBox tipoDocumento = new ListBox();
	private RegexTextBox numeroDocTextBox = new RegexTextBox(RegularExpressionConstants.dni);
	private Label tipoDocLabel;
	private Label numeroDocLabel;
	private Hyperlink aceptar = new Hyperlink(Sfa.constant().aceptar(),null);
	private SimpleLink cerrar = new SimpleLink(Sfa.constant().cerrar(), "#", true);
	public static Long idOpp;
	public static boolean fromMenu = true;
	
	public BuscadorDocumentoPopup(String title) {
		super(title, false, true);
        init();	
	
		aceptar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				reservaCreacionCuenta();
			}
		});		
	
		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				hide();
				//History.newItem("");
				cleanForm();
			}
		});
		tipoDocumento.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				numeroDocTextBox.setText("");
				if (tipoDocumento.getSelectedItemId().equals(TipoDocumentoEnum.CUIL.getTipo()+"") ||
					tipoDocumento.getSelectedItemId().equals(TipoDocumentoEnum.CUIT.getTipo()+"") ||
					tipoDocumento.getSelectedItemId().equals(TipoDocumentoEnum.CUIT_EXT.getTipo()+"") ){
				   numeroDocTextBox.setPattern(RegularExpressionConstants.cuilCuit);
				} else if (tipoDocumento.getSelectedItemId().equals(TipoDocumentoEnum.DNI.getTipo()+"")) {
				   numeroDocTextBox.setPattern(RegularExpressionConstants.dni);
				} else {
					numeroDocTextBox.setPattern(RegularExpressionConstants.documentoOtros);
				}
			}
		});
	}
	
	public void init() {
		buscadorDocumentoPanel = new SimplePanel();
		buscadorDocumentoPanel.setWidth("250");
		buscadorDocumentoPanel.setHeight("70");
		
		buscadorDocumentoTable = new FlexTable();
		botonesTable = new FlexTable();
		botonesTable.setWidth("100%");
		
		buscadorDocumentoTable.setWidth("100%");
		tipoDocumento    = new ListBox();
		//numeroDocTextBox = new TextBox();
		//numeroDocTextBox.setMaxLength(13);
		tipoDocLabel   = new Label(Sfa.constant().tipoDocumento().trim());
		numeroDocLabel = new Label(Sfa.constant().numero().trim());
		
		tipoDocLabel.addStyleName("req");
		numeroDocLabel.addStyleName("req");
		
		CuentaRpcService.Util.getInstance().getAgregarCuentaInitializer(
			new DefaultWaitCallback<AgregarCuentaInitializer>() {
				public void success(AgregarCuentaInitializer result) {
					tipoDocumento.addAllItems(result.getTiposDocumento());
				}
			});
		
		buscadorDocumentoTable.setWidget(0, 0, tipoDocLabel);
		buscadorDocumentoTable.setWidget(0, 1, tipoDocumento);
		buscadorDocumentoTable.setWidget(1, 0, numeroDocLabel);
		buscadorDocumentoTable.setWidget(1, 1, numeroDocTextBox);
		EventWrapper ew = new EventWrapper() {
			public void doEnter() {
				reservaCreacionCuenta();
			}
		};
		ew.add(buscadorDocumentoTable);
		buscadorDocumentoPanel.add(ew);
		
		botonesTable.setWidget(0,0,aceptar);
		botonesTable.setWidget(0,1,cerrar);
		botonesTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		botonesTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		addFooter(botonesTable);
		add(buscadorDocumentoPanel);

		numeroDocTextBox.ensureDebugId(DebugConstants.AGREGAR_CUENTAS_POPUP_TEXTBOX_NUM_DOC_ID);
		tipoDocumento.ensureDebugId(DebugConstants.AGREGAR_CUENTAS_POPUP_COMBO_TIPO_DOC_ID);
		cerrar.ensureDebugId(DebugConstants.AGREGAR_CUENTAS_POPUP_BUTTON_CERRAR_ID);
		aceptar.ensureDebugId(DebugConstants.AGREGAR_CUENTAS_POPUP_BUTTON_ACEPTAR_ID);
	}
	
	private boolean validarNumeroDocumento() {
		GwtValidator validator = new GwtValidator();
		if (numeroDocTextBox.getText().equals("")) {
			validator.addError(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));
		} else {
			if (tipoDocumento.getSelectedItemId().equals(Long.toString(TipoDocumentoEnum.CUIL.getTipo())) ||
					tipoDocumento.getSelectedItemId().equals(Long.toString(TipoDocumentoEnum.CUIT.getTipo())) ||
					  tipoDocumento.getSelectedItemId().equals(Long.toString(TipoDocumentoEnum.CUIT_EXT.getTipo()))) {
				if (!numeroDocTextBox.getText().matches(RegularExpressionConstants.cuilCuit)) {
					validator.addError(Sfa.constant().ERR_FORMATO_CUIL());
				} else {
					validator.addTarget(numeroDocTextBox).cuil(Sfa.constant().ERR_DATO_CUIL());	
				}
			}
		}
		validator.fillResult();
		if (!validator.getErrors().isEmpty()) {
			ErrorDialog.getInstance().show(validator.getErrors(),false);
		}	
		return validator.getErrors().isEmpty();
	}
	
	private void reservaCreacionCuenta() {
		if (validarNumeroDocumento()) {
			CuentaClientService.reservaCreacionCuenta(new Long(tipoDocumento.getSelectedItemId()), numeroDocTextBox.getText(), fromMenu?null:idOpp);
			hide();
			cleanForm();	
		}
	}
	
	private void cleanForm() {
		numeroDocTextBox.setText("");
		numeroDocTextBox.setPattern(RegularExpressionConstants.dni);
		tipoDocumento.selectByValue(TipoDocumentoEnum.DNI.toString());	
	}
}

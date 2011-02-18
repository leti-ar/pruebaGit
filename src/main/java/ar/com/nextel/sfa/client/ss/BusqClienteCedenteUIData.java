package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.GrupoDocumentoDto;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.nextel.sfa.client.widget.ValidationTextBox;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Widget;

public class BusqClienteCedenteUIData extends UIData implements ChangeHandler{
	
	private static final String CODIGO_CLIENTE = "1";
	private static final String NUM_DOCUMENTO = "2";
	private static final String CUIT_CUIL = "3";
	private static final String NUM_NEXTEL = "4";
	private static final String FLOTA_ID = "5";
	
	private ListBox criterioBusqCedente;
	private RegexTextBox parametroBusqCedente;
	
	private EditarSSUIController controller;
	
	private static final String GRUPO_DOCUMENTO_DOCUMENTO = "GRUPO_DOCUMENTO_DOCUMENTO";
	private static final String GRUPO_DOCUMENTO_CUIT = "GRUPO_DOCUMENTO_CUIT";
	
	public BusqClienteCedenteUIData(EditarSSUIController controller){
		this.controller = controller;
		
		fields = new ArrayList<Widget>();
		fields.add(criterioBusqCedente = new ListBox());
		fields.add(parametroBusqCedente = new RegexTextBox());
		
		criterioBusqCedente.setWidth("150px");
		criterioBusqCedente.addChangeHandler(this);
		parametroBusqCedente.setWidth("150px");
		
		inicializarCombos();
	}

	public ListBox getCriterioBusqCedente() {
		return criterioBusqCedente;
	}

	public RegexTextBox getParametroBusqCedente() {
		return parametroBusqCedente;
	}

	private void inicializarCombos(){
		//TODO: Obtener las opciones de busqueda, o pasar el los valores a varibles staticas
		criterioBusqCedente.addItem("Código de cliente", CODIGO_CLIENTE);
		criterioBusqCedente.addItem("N° Documento", NUM_DOCUMENTO);
		criterioBusqCedente.addItem("CUIT/CUIL", CUIT_CUIL);
		criterioBusqCedente.addItem("N° Nextel", NUM_NEXTEL);
		criterioBusqCedente.addItem("Flota*ID", FLOTA_ID);

		criterioBusqCedente.setSelectedIndex(0);
		parametroBusqCedente.setPattern(RegularExpressionConstants.numerosYPunto);
		parametroBusqCedente.setMaxLength(25);
	}
	
	public CuentaSearchDto getCuentaSearch(){
		CuentaSearchDto cuentaSearchDto = new CuentaSearchDto();
		String critBusq = criterioBusqCedente.getValue(criterioBusqCedente.getSelectedIndex());
		String paramBusq = parametroBusqCedente.getText();
		if(critBusq.equals(CODIGO_CLIENTE)){
			cuentaSearchDto.setNumeroCuenta(paramBusq);
		}
		else if(critBusq.equals(NUM_DOCUMENTO)){
			HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
			GrupoDocumentoDto grupo = new GrupoDocumentoDto();
			grupo.setId(Long.parseLong(instancias.get(GRUPO_DOCUMENTO_DOCUMENTO).toString()));
			
			cuentaSearchDto.setGrupoDocumentoId(grupo);
			cuentaSearchDto.setNumeroDocumento(paramBusq);
		}
		else if(critBusq.equals(CUIT_CUIL)){
			HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
			GrupoDocumentoDto grupo = new GrupoDocumentoDto();
			grupo.setId(Long.parseLong(instancias.get(GRUPO_DOCUMENTO_CUIT).toString()));
			
			cuentaSearchDto.setGrupoDocumentoId(grupo);
			cuentaSearchDto.setNumeroDocumento(paramBusq);
		}
		else if(critBusq.equals(NUM_NEXTEL)){
			cuentaSearchDto.setNumeroNextel(Long.parseLong(paramBusq));
		}
		else if(critBusq.equals(FLOTA_ID)){
			cuentaSearchDto.setFlotaId(paramBusq);
		}
		cuentaSearchDto.setCantidadResultados(10);
		cuentaSearchDto.setOffset(0);
		return cuentaSearchDto;
	}
	
	public void onChange(ChangeEvent arg0) {
		parametroBusqCedente.setText("");
		String critBusq = criterioBusqCedente.getValue(criterioBusqCedente.getSelectedIndex());
		if(critBusq.equals(CODIGO_CLIENTE)){
			parametroBusqCedente.setPattern(RegularExpressionConstants.numerosYPunto);
			parametroBusqCedente.setMaxLength(25);
		}
		else if(critBusq.equals(NUM_DOCUMENTO)){
			parametroBusqCedente.setPattern(RegularExpressionConstants.getNumerosLimitado(10));
		}
		else if(critBusq.equals(CUIT_CUIL)){
			parametroBusqCedente.setPattern(RegularExpressionConstants.cuilCuit);
		}
		else if(critBusq.equals(NUM_NEXTEL)){
			parametroBusqCedente.setPattern(RegularExpressionConstants.getNumerosLimitado(10));
		}
		else if(critBusq.equals(FLOTA_ID)){
			parametroBusqCedente.setPattern("[0-9\\*]*");
			parametroBusqCedente.setMaxLength(11);
		}
	}
	
	public List<String> validarCriterioBusqueda(){
		List<String> errores = new ArrayList<String>();
		if(criterioBusqCedente.getSelectedItemText() == null){
			errores.add("Por favor ingrese por lo menos un criterio de busqueda.");
		}
		else if(parametroBusqCedente.getText() == null || parametroBusqCedente.getText().equals("")){
			errores.add("Debe ingresar el criterio de busqueda.");
		} 
		//#1343
		else if (criterioBusqCedente.getValue(criterioBusqCedente.getSelectedIndex()).equals(CUIT_CUIL)) {
			if (!valdaCuilCuit(parametroBusqCedente.getText()))
				errores.add("Formato de cuit/cuil incorrecto");
		} 
		else if(criterioBusqCedente.getValue(criterioBusqCedente.getSelectedIndex()).equals(CODIGO_CLIENTE) && 
				this.controller.getEditarSSUIData().getCuenta().getCodigoVantive().startsWith(parametroBusqCedente.getText())){
			errores.add("La cuenta cedente es la misma que el cesionario. Por favor, elija otra cuenta.");
		}
		else if(criterioBusqCedente.getValue(criterioBusqCedente.getSelectedIndex()).equals(FLOTA_ID)){
			//Valido el formato de la flota
			if (!validaFlotaId(parametroBusqCedente.getText())) {
				errores.add("Formato incorrecto de Flota*Id.");
			}
		}
		return errores;
	}
	
	private static final String cuilCuitFinalPattern = "[0-9]{2,2}-[0-9]{8,8}-[0-9]{1,1}";
	private boolean valdaCuilCuit(String text) {
		ValidationTextBox cuitCuil = new ValidationTextBox(RegularExpressionConstants.cuilCuit);
		cuitCuil.setText(text);
		return cuitCuil.validatePattern(cuilCuitFinalPattern);
	}

	private boolean validaFlotaId(String text) {
		ValidationTextBox flota = new ValidationTextBox("[0-9\\*]*");
		flota.setText(text);
		
		String flotaModelo = new String("[0-9]{3,5}[\\*]{1}[0-9]{1,5}");
		return flota.validatePattern(flotaModelo);
	}

}

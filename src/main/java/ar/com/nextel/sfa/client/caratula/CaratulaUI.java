package ar.com.nextel.sfa.client.caratula;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.initializer.CaratulaInitializer;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

/**
 * Componente para poder editar caratulas
 * 
 * @author mrotger
 *
 */
public class CaratulaUI extends NextelDialog{// implements ClickListener{

	private static CaratulaUI instance = null;
	private CaratulaDto caratulaAEditar;
	
	private Grid gridCabecera;
	private CaratulaUIData caratulaData;
	private SimpleLink aceptar;
//	private FormButtonsBar footerBar;
	private SimpleLink cancelar;
	private Command aceptarCommand;
	
	public static CaratulaUI getInstance(){
		if(instance == null){
			instance = new CaratulaUI();
		}
		return instance;
	}
	
	private CaratulaUI(){
		super(Sfa.constant().crearCaratula(), false, true);
		init();
	}
	
	private void init(){
		setWidth("740px");
		caratulaData = new CaratulaUIData();
		
		gridCabecera = new Grid(2,8);
		gridCabecera.setHTML(0, 0, Sfa.constant().nssReq());
		gridCabecera.setWidget(0, 1, caratulaData.getNroSS());
		gridCabecera.setText(0, 2, Sfa.constant().fechaInicio());
		gridCabecera.setWidget(0, 3, caratulaData.getFechaInicio());
		gridCabecera.setText(0, 4, Sfa.constant().actividad());
		gridCabecera.setWidget(0, 5, caratulaData.getActividad());
		gridCabecera.setText(0, 6, Sfa.constant().validacionDomicilio());
		gridCabecera.setWidget(0, 7, caratulaData.getValidDomicilio());
		add(gridCabecera);
		
//		footerBar = new FormButtonsBar();
		cancelar = new SimpleLink("Cerrar");
		cancelar.setStyleName("link");
		aceptar = new SimpleLink("Aceptar");
		aceptar.setStyleName("link");
//		footerBar.addLink(aceptar);
//		footerBar.addLink(cancelar);
		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
//		mainPanel.add(footerBar);
		
		aceptar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
//				if (comandoAceptar != null) {
//					getComandoAceptarDomicilioServiceCall().execute();
//				}
			}
		});
		cancelar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				hide();
			}
		});
		
//		cargarCombos();
	}

//	private void cargarCombos() {
//		CuentaRpcService.Util.getInstance().getCaratulaInicializarte(
//				new DefaultWaitCallback<CaratulaInitializer>() {
//			
//			@Override
//			public void success(CaratulaInitializer initializer) {
//				caratulaData.getValidDomicilio().addAllItems(initializer.getValidDomicilio());
//				
//			}
//		});
//		
//	}

	/**
	 * Metodo que setea la accion a tomar por el botón Aceptar del popup CaratulaUI.
	 **/
	public void setAceptarCommand(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}
	
	public void showAndCenter() {
		super.showAndCenter();
	}
	
	//El pop-up se muestra con el título "Crear Caratula"
	public void cargarPopupNuevaCaratula(CaratulaDto caratulaDto){
		cargarPopupCaratula(caratulaDto, Sfa.constant().crearCaratula());
	}
	
	//El pop-up se muestra con el título "Editar Caratula"
	public void cargarPopupEditarCaratula(CaratulaDto caratulaDto){
		cargarPopupCaratula(caratulaDto, Sfa.constant().editarCaratula());
	}
	
	private void cargarPopupCaratula(CaratulaDto caratula, String titulo){
		this.caratulaAEditar = caratula;
		if(caratulaAEditar.getNroSS() != null)
			caratulaData.getNroSS().setText(caratulaAEditar.getNroSS());
		//TODO Terminar
		showAndCenter();
		//Habilitar campos
		setDialogTitle(titulo);
	}
}

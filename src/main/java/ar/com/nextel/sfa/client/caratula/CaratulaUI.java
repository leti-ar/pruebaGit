package ar.com.nextel.sfa.client.caratula;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Componente para poder editar caratulas
 * 
 * @author mrotger
 *
 */
public class CaratulaUI extends NextelDialog implements ChangeListener, ClickListener{

	private static CaratulaUI instance = null;
	private CaratulaDto caratulaAEditar;
	
	private Grid gridCabecera;
	
	private Grid gridBanco;
	private Grid gridDatosBanco;
	private HorizontalPanel panelBanco;
	
	private Grid gridIngDem;
	private Grid gridDatosIngDem;
	private HorizontalPanel panelIngDem;
	
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
		
		gridCabecera = new Grid(1,8);
		gridCabecera.setHTML(0, 0, Sfa.constant().nssReq());
		gridCabecera.setWidget(0, 1, caratulaData.getNroSS());
		gridCabecera.setText(0, 2, Sfa.constant().fechaInicio());
		gridCabecera.setWidget(0, 3, caratulaData.getWidgetFechaInicio());
		gridCabecera.setText(0, 4, Sfa.constant().actividad());
		gridCabecera.setWidget(0, 5, caratulaData.getActividad());
		gridCabecera.setText(0, 6, Sfa.constant().validacionDomicilio());
		gridCabecera.setWidget(0, 7, caratulaData.getValidDomicilio());
		
		//MGR**** Adm_Vtas R2 TODO: Solo a modo de prueba
		add(gridCabecera);
		
		gridBanco = new Grid(1,2);
		gridBanco.setText(0, 0, Sfa.constant().banco());
		gridBanco.setWidget(0, 1, caratulaData.getBanco());
		
		gridDatosBanco = new Grid(2, 4);
		gridDatosBanco.setText(0, 0, Sfa.constant().refBancaria());
		gridDatosBanco.setWidget(0, 1, caratulaData.getRefBancaria());
		gridDatosBanco.setText(0, 2, Sfa.constant().tipoCuenta());
		gridDatosBanco.setWidget(0, 3, caratulaData.getTipoCuenta());
		gridDatosBanco.setText(1, 0, Sfa.constant().mayorSaldo());
		gridDatosBanco.setWidget(1, 1, caratulaData.getMayorSaldoFavor());
		gridDatosBanco.setText(1, 2, Sfa.constant().ingresoProm());
		gridDatosBanco.setWidget(1, 3, caratulaData.getIngPromedio());
		
		panelBanco = new HorizontalPanel();
		panelBanco.add(gridBanco);
		panelBanco.add(gridDatosBanco);
		add(panelBanco);
		
		caratulaData.getBanco().addChangeListener(this);
		onChange(caratulaData.getBanco());
		
		HorizontalPanel h2 = new HorizontalPanel();
		h2.add(new Label(Sfa.constant().tarjCredito()));
		h2.add(caratulaData.getTarjCredito());
		h2.add(new Label(Sfa.constant().limiteTarj()));
		h2.add(caratulaData.getLimiteTarj());
		add(h2);
		
		gridIngDem = new Grid(1,2);
		gridIngDem.setText(0, 0, Sfa.constant().ingresoDemostrado());
		gridIngDem.setWidget(0, 1, caratulaData.getIngDemostrado());
		
		gridDatosIngDem = new Grid(3,6);
		gridDatosIngDem.setText(0, 0, Sfa.constant().IVA());
		gridDatosIngDem.setWidget(0, 1, caratulaData.getIva());
		gridDatosIngDem.setText(0, 2, Sfa.constant().ganancia());
		gridDatosIngDem.setWidget(0, 3, caratulaData.getGanancias());
		gridDatosIngDem.setText(0, 4, Sfa.constant().balance());
		gridDatosIngDem.setWidget(0, 5, caratulaData.getBalance());
		gridDatosIngDem.setText(1, 0, Sfa.constant().iibb());
		gridDatosIngDem.setWidget(1, 1, caratulaData.getIngBrutos());
		gridDatosIngDem.setText(1, 2, Sfa.constant().reciboHaberes());
		gridDatosIngDem.setWidget(1, 3, caratulaData.getReciboHaberes());
		gridDatosIngDem.setText(2, 0, Sfa.constant().PYP());
		gridDatosIngDem.setWidget(2, 1, caratulaData.getPyp());
		gridDatosIngDem.setText(2, 2, Sfa.constant().factCelular());
		gridDatosIngDem.setWidget(2, 3, caratulaData.getFactCelular());
		
		panelIngDem = new HorizontalPanel();
		panelIngDem.add(gridIngDem);
		panelIngDem.add(gridDatosIngDem);
		add(panelIngDem);
		
		caratulaData.getIngDemostrado().addClickListener(this);
		onClick(caratulaData.getIngDemostrado());
		
		HorizontalPanel h4 = new HorizontalPanel();
		h4.add(new Label(Sfa.constant().oKEECCAgente()));
		h4.add(caratulaData.getOkEECCAgente());
		add(h4);
		HorizontalPanel h5 = new HorizontalPanel();
		h5.add(new Label(Sfa.constant().COM()));
		h5.add(caratulaData.getCom());
		h5.add(new Label(Sfa.constant().antiguedad()));
		h5.add(caratulaData.getAntiguedad());
		h5.add(new Label(Sfa.constant().limCreditoReq()));
		h5.add(caratulaData.getLimiteCred());
		h5.add(new Label(Sfa.constant().calificacionReq()));
		h5.add(caratulaData.getCalificacion());
		add(h5);
		HorizontalPanel h6 = new HorizontalPanel();
		h6.add(new Label(Sfa.constant().riskCodeReq()));
		h6.add(caratulaData.getRiskCode());
		h6.add(new Label(Sfa.constant().compPagoReq()));
		h6.add(caratulaData.getComprPago());
		h6.add(new Label(Sfa.constant().cargoReq()));
		h6.add(caratulaData.getCargo());
		h6.add(new Label(Sfa.constant().consumoProm()));
		h6.add(caratulaData.getConsumoProm());
		add(h6);
		HorizontalPanel h7 = new HorizontalPanel();
		h7.add(new Label(Sfa.constant().equiposActivos()));
		h7.add(caratulaData.getEquiposActivos());
		h7.add(new Label(Sfa.constant().veraz()));
		h7.add(caratulaData.getVeraz());
		h7.add(new Label(Sfa.constant().nosis()));
		h7.add(caratulaData.getNosis());
		h7.add(new Label(Sfa.constant().BCRA()));
		h7.add(caratulaData.getBcra());
		add(h7);
		HorizontalPanel h8 = new HorizontalPanel();
		h8.add(new Label(Sfa.constant().otras()));
		h8.add(caratulaData.getOtras());
		h8.add(new Label(Sfa.constant().validFirma()));
		h8.add(caratulaData.getValidFirma());
		h8.add(new Label(Sfa.constant().deposGarantia()));
		h8.add(caratulaData.getDepGarantia());
		h8.add(new Label(Sfa.constant().anticipo()));
		h8.add(caratulaData.getAnticipo());
		add(h8);
		HorizontalPanel h9 = new HorizontalPanel();
		h9.add(new Label(Sfa.constant().soloHibridos()));
		h9.add(caratulaData.getSoloHibridos());
		h9.add(new Label(Sfa.constant().findIMEI()));
		h9.add(caratulaData.getFindImei());
		h9.add(new Label(Sfa.constant().comentAnalista()));
		h9.add(caratulaData.getComentAnalista());
		h9.add(new Label(Sfa.constant().scoringTitle()));
		h9.add(caratulaData.getScoring());
		add(h9);
		
//		add(gridCabecera);
		
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

				System.out.println("Click en aceptar del popup de caratula");
				System.out.println("Puedo hacer la validacion");
				if(caratulaData.validarCamposObligatorios()){

					if(aceptarCommand != null){
						System.out.println("Cierro popuup");
						hide();
						System.out.println("Ejecuto comando");
						aceptarCommand.execute();
					}else{
						System.out.println("no hay comando");
						System.out.println("Cierro popuup");
					}
					
				}else{
					System.out.println("No paso validacion, muestro error");
				}
				
			}
		});
		cancelar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				hide();
			}
		});
		
	}

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
		caratulaData.setDatosCaratula(caratulaAEditar);
		setDialogTitle(titulo);
		showAndCenter();
	}
	
	public CaratulaDto getCaraturaAEditar(){
		return caratulaData.getDatosCaratula();
	}
	
	public void onChange(Widget sender) {
		if(sender == caratulaData.getBanco()){
			
			if(caratulaData.getBanco().getSelectedItem() != null){
				gridDatosBanco.setVisible(true);
			}else{
				gridDatosBanco.setVisible(false);
			}
			
		} 
	}

	public void onClick(Widget sender) {
		if(sender == caratulaData.getIngDemostrado()){
			
			if(caratulaData.getIngDemostrado().getValue()){
				gridDatosIngDem.setVisible(true);
			}else
				gridDatosIngDem.setVisible(false);
		}
	}
}

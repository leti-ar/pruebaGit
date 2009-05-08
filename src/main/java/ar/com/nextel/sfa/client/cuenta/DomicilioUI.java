package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.TipoDomicilioDto;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author eSalvador
 **/
public class DomicilioUI extends NextelDialog {

	private Grid gridUp;
	private Grid gridMed;
	private Grid gridDown;
	private Grid gridObs;
	private Grid gridUser;
	private Command comandoAceptar;
	private FormButtonsBar footerBar;
	private SimpleLink linkCerrar;
	private SimpleLink linkAceptar;
	private DomiciliosUIData domiciliosData;
	private boolean editable;
	private static DomicilioUI instance = new DomicilioUI();

	public static DomicilioUI getInstance() {
		return instance;
	}

	public DomicilioUI() {
		super("Editar Domicilio");
		init();
	}

	@Override
	public void clear() {
		super.clear();
	}

	/**
	 * @author esalvador
	 **/
	public void cargarPopupNuevoDomicilio() {
		domiciliosData.clean();
		domiciliosData.setDomicilio(null);
		linkAceptar.setVisible(true);
		domiciliosData.enableFields();
		showAndCenter();
		//TODO: Arreglar lo del Titulo!!!
		setDialogTitle("Crear Domicilio");
	}

	/**
	 * @author esalvador
	 **/
	public void cargarPopupCopiarDomicilio(DomiciliosCuentaDto domicilio) {
		domiciliosData.setDomicilio(domicilio);
		linkAceptar.setVisible(true);
		domiciliosData.enableFields();
		showAndCenter();
		//TODO: Arreglar lo del Titulo!!!
		setDialogTitle("Copiar Domicilio");
	}
	
	/**
	 * @author esalvador
	 **/
	public void cargarPopupEditarDomicilio(DomiciliosCuentaDto domicilio, boolean editable) {
		domiciliosData.setDomicilio(domicilio);
		showAndCenter();
		if (!editable){
			domiciliosData.disableFields();
			linkAceptar.setVisible(false);
		}else{
			domiciliosData.enableFields();
			linkAceptar.setVisible(true);
		}
		
		//Boolean locked = domicilio.isLocked();
		//GWT.log("Locked?: " + locked.toString(), null);

		//TODO: Arreglar lo del Titulo!!!
		setDialogTitle("Editar Domicilio");
	}
	
	/**
	 *@author eSalvador
	 **/
	public void init() {
		domiciliosData = new DomiciliosUIData();
		footerBar = new FormButtonsBar();
		linkCerrar = new SimpleLink("Cerrar");
		linkAceptar = new SimpleLink("Aceptar");
		gridUp = new Grid(3, 5);
		gridMed = new Grid(1, 11);
		gridDown = new Grid(5, 5);
		gridObs = new Grid(2, 3);
		gridUser = new Grid(1, 5);
		setWidth("635px");

		gridUp.getColumnFormatter().setWidth(1, "85px");
		gridUp.getColumnFormatter().setWidth(3, "85px");
		gridUp.addStyleName("layout");
		gridUp.setText(1, 1, Sfa.constant().cpa());
		gridUp.setWidget(1, 2, domiciliosData.getCpa());
		gridUp.setText(2, 1, Sfa.constant().calle());
		gridUp.setWidget(2, 2, domiciliosData.getCalle());
		gridUp.setText(2, 3, Sfa.constant().numeroCalle());
		gridUp.setWidget(2, 4, domiciliosData.getNumero());
		//
		gridMed.getColumnFormatter().setWidth(1, "85px");
		gridMed.getColumnFormatter().setWidth(3, "65px");
		gridMed.getColumnFormatter().setWidth(5, "65px");
		gridMed.getColumnFormatter().setWidth(7, "65px");
		gridMed.getColumnFormatter().setWidth(9, "65px");
		gridMed.addStyleName("layout");
		gridMed.setText(0, 1, Sfa.constant().piso());
		gridMed.setWidget(0, 2, domiciliosData.getPiso());
		gridMed.setText(0, 3, Sfa.constant().dpto());
		gridMed.setWidget(0, 4, domiciliosData.getDepartamento());
		gridMed.setText(0, 5, Sfa.constant().uf());
		gridMed.setWidget(0, 6, domiciliosData.getUnidadFuncional());
		gridMed.setText(0, 7, Sfa.constant().torre());
		gridMed.setWidget(0, 8, domiciliosData.getTorre());
		gridMed.setText(0, 9, Sfa.constant().manzana());
		gridMed.setWidget(0, 10, domiciliosData.getManzana());
		//
		gridDown.getColumnFormatter().setWidth(1, "85px");
		gridDown.getColumnFormatter().setWidth(3, "80px");
		gridDown.addStyleName("layout");
		gridDown.setText(0, 1, Sfa.constant().entre_calle());
		gridDown.setWidget(0, 2, domiciliosData.getEntreCalle());
		gridDown.setText(0, 3, Sfa.constant().y_calle());
		gridDown.setWidget(0, 4, domiciliosData.getYcalle());
		gridDown.setText(1, 1, Sfa.constant().localidad());
		gridDown.setWidget(1, 2, domiciliosData.getLocalidad());
		gridDown.setText(1, 3, Sfa.constant().cp());
		gridDown.setWidget(1, 4, domiciliosData.getCodigoPostal());
		gridDown.setText(2, 1, Sfa.constant().provincia());
		// TODO: Cambiar a provincia cuando arregle el otro!
		gridDown.setWidget(2, 2, domiciliosData.getPuerta());
		//
		gridDown.setText(2, 3, Sfa.constant().partido());
		gridDown.setWidget(2, 4, domiciliosData.getPartido());
		//
		tiposDomicilioDtoInit();
		//
		gridDown.setText(3, 1, Sfa.constant().facturacion());
		gridDown.setWidget(3, 2, domiciliosData.getFacturacion());
		//
		gridDown.setText(3, 3, Sfa.constant().entrega());
		gridDown.setWidget(3, 4, domiciliosData.getEntrega());
		
		gridDown.setText(4, 2, Sfa.constant().validado());
		gridDown.setWidget(4, 3, domiciliosData.getValidado());
		// marco.setText(9, 0, Sfa.constant().obs_domicilio());
		// marco.setWidget(10, 0, domiciliosData.getObservaciones());
		// marco.setText(11, 0, Sfa.constant().usuario_domicilio());
		// //TODO: Cambiar a usuario_domicilio cuando arregle el otro!
		// marco.setWidget(11, 1, domiciliosData.getPartido());
		// marco.setText(11, 3, Sfa.constant().fecha_Modificacion());
		// //TODO: Cambiar a fecha_Modificacion cuando arregle el otro!
		// marco.setWidget(11, 4, domiciliosData.getPartido());
		// mainPanel.add(marco);

		//
		gridObs.addStyleName("layout");
		gridObs.setText(0, 1, Sfa.constant().obs_domicilio());
		gridObs.setWidget(1, 1, domiciliosData.getObservaciones());
		//
		gridUser.addStyleName("layout");
		gridUser.setText(0, 1, Sfa.constant().usuario_domicilio());
		gridUser.setWidget(0, 2, domiciliosData.getNombreUsuarioUltimaModificacion());
		gridUser.setText(0, 3, Sfa.constant().fecha_Modificacion());
		// gridUser.setWidget(0, 4, domiciliosData.get);
		//
		add(gridUp);
		add(gridMed);
		add(gridDown);
		add(gridObs);
		add(gridUser);

		linkCerrar.setStyleName("link");
		linkAceptar.setStyleName("link");
		footerBar.addLink(linkAceptar);
		footerBar.addLink(linkCerrar);
		mainPanel.add(footerBar);
		footer.setVisible(false);
		linkAceptar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				// TODO: Logica de Aceptar y guardar nuevo Domicilio!
				if (comandoAceptar != null){
					comandoAceptar.execute();
				}
			}
		});
		linkCerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				hide();
			}
		});
		this.showAndCenter();
	}

	/**
	 * @author eSalvador
	 */
	private List<TipoDomicilioDto> tiposDomicilioDtoInit(){
		List<TipoDomicilioDto> listaTiposDomicilioDto = new ArrayList();
		
		TipoDomicilioDto tipoDomicilioItem1 = new TipoDomicilioDto();
		tipoDomicilioItem1.setId(new Long(1));
		tipoDomicilioItem1.setDescripcion("Bill To");
		cargaComboTipoDomicilioEntregaDto(tipoDomicilioItem1);

		TipoDomicilioDto tipoDomicilioItem2 = new TipoDomicilioDto();
		tipoDomicilioItem2.setId(new Long(4));
		tipoDomicilioItem2.setDescripcion("Ship To");
		cargaComboTipoDomicilioFacturacionDto(tipoDomicilioItem2);
		
		listaTiposDomicilioDto.add(tipoDomicilioItem1);
		listaTiposDomicilioDto.add(tipoDomicilioItem2);
		
		return listaTiposDomicilioDto;
	}
	
	/**
	 * @author eSalvador
	 */
	private void cargaComboTipoDomicilioEntregaDto(TipoDomicilioDto tipoDomicilioDto){
		domiciliosData.getEntrega().addItem(new TipoDomicilioItem(tipoDomicilioDto,true,"Principal"));
		domiciliosData.getEntrega().addItem(new TipoDomicilioItem(tipoDomicilioDto,false,"Si"));
		TipoDomicilioDto tipoDomicilioItemNo = new TipoDomicilioDto();
		tipoDomicilioItemNo.setId(new Long(0));
		tipoDomicilioItemNo.setDescripcion("EntregaNo");
		cargaItemNoComboEntrega(tipoDomicilioItemNo);
	}

	private void cargaComboTipoDomicilioFacturacionDto(TipoDomicilioDto tipoDomicilioDto){
		domiciliosData.getFacturacion().addItem(new TipoDomicilioItem(tipoDomicilioDto,true,"Principal"));
		domiciliosData.getFacturacion().addItem(new TipoDomicilioItem(tipoDomicilioDto,false,"Si"));
		TipoDomicilioDto tipoDomicilioItemNo = new TipoDomicilioDto();
		tipoDomicilioItemNo.setId(new Long(0));
		tipoDomicilioItemNo.setDescripcion("FacturacionNo");
		cargaItemNoComboFacturacion(tipoDomicilioItemNo);
	}
	
	private void cargaItemNoComboFacturacion(TipoDomicilioDto tipoDomicilioDto){
		domiciliosData.getFacturacion().addItem(new TipoDomicilioItem(tipoDomicilioDto,false,"No"));
	}
	
	private void cargaItemNoComboEntrega(TipoDomicilioDto tipoDomicilioDto){
		domiciliosData.getEntrega().addItem(new TipoDomicilioItem(tipoDomicilioDto,false,"No"));
	}
	
	/**
	 * @author eSalvador
	 * Metodo que setea la accion a tomar por el botón Aceptar del popup DomicilioUI.
	 **/
	public void setComandoAceptar(Command comandoAceptar) {
		this.comandoAceptar = comandoAceptar;
	}

	public DomiciliosUIData getDomiciliosData() {
		return domiciliosData;
	}
	
	@Override
	public void showAndCenter() {
		super.showAndCenter();
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
}

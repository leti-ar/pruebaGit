package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.NormalizacionDomicilioMotivoDto;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author eSalvador 
 **/
public class NormalizarDomicilioUI extends NextelDialog {

	private Grid grillaPpal;
	private SimplePanel grillaDomicilio;
	private NextelTable domicilioResult;
	private Command comandoAceptar;
	private Command comandoNoNormalizar;
	private FormButtonsBar footerBar;
	private SimpleLink linkCerrar;
	private SimpleLink linkAceptar;
	private SimpleLink linkNoNormalizar;
	private DomiciliosCuentaDto domicilio;
	private List<DomiciliosCuentaDto> domiciliosEnDuda;
	//
	private boolean normalizado = true;	
    //private List<DomiciliosCuentaDto> dudas = new ArrayList();
    private List<NormalizacionDomicilioMotivoDto> motivos = new ArrayList();
	//
	private int rowSelected;
    
	private static NormalizarDomicilioUI instance = new NormalizarDomicilioUI();

	public static NormalizarDomicilioUI getInstance() {
		return instance;
	}
	
	public NormalizarDomicilioUI() {
		super("Normalización");
		init();
	}
	
	@Override
	public void clear() {
		super.clear();
	}
	
	private void setearFormatoNormalizador(){
		if(!normalizado){
			grillaDomicilio.setVisible(false);
			linkAceptar.setVisible(false);
			grillaPpal.setText(3, 0, "No se pudo normalizar el domicilio");
			//OJO con el get(0):
			grillaPpal.setText(4, 0, motivos.get(0).getMotivo());
		}else{
			grillaDomicilio.setVisible(true);
			linkAceptar.setVisible(true);
			grillaPpal.setText(3, 0, "");
			grillaPpal.setText(4, 0, "");
		}
	}
	
	/**
	 * @author eSalvador
	 * Metodo que setea la accion a tomar por el botón Aceptar del popup NormalizarDomicilioUI.
	 **/
	public void setComandoAceptar(Command comandoAceptar) {
		this.comandoAceptar = comandoAceptar;
	}
	
	/**
	 * @author eSalvador
	 * Metodo que setea la accion a tomar por el botón NoNormalizar del popup NormalizarDomicilioUI.
	 **/
	public void setComandoNoNormalizar(Command comandoNoNormalizar) {
		this.comandoNoNormalizar = comandoNoNormalizar;
	}
	
	/**
	 *@author eSalvador
	 **/
	public void init() {
		//domiciliosData = new DomiciliosUIData();
		footerBar = new FormButtonsBar();
		linkNoNormalizar = new SimpleLink("No Normalizar");
		linkAceptar = new SimpleLink("Aceptar");
		linkCerrar = new SimpleLink("Cerrar");
		grillaDomicilio = new SimplePanel();
		domicilioResult = new NextelTable();
		addStyleName("gwt-NormalizarDomicilioUI");
		grillaDomicilio.addStyleName("resultTableScroll");
		grillaPpal = new Grid(6, 1);
		grillaDomicilio.setVisible(true);
		setWidth("480px");

		grillaPpal.getColumnFormatter().setWidth(0, "550px");
		grillaPpal.setText(0, 0, "Domicilio Ingresado:");
		grillaPpal.getRowFormatter().addStyleName(0, "layout");
		grillaPpal.getCellFormatter().setHeight(1, 0, "25px");
		grillaPpal.getRowFormatter().setStyleName(1, "gwt-TitledPanel");
		grillaPpal.setWidget(1, 0, new Label());
		grillaPpal.setWidget(2, 0, grillaDomicilio);
		grillaPpal.setText(3, 0, "");
		grillaPpal.setText(4, 0, "Motivo:");
		grillaPpal.setWidget(5, 0, new Label());
		
		add(grillaPpal);

		linkNoNormalizar.setStyleName("link");
		linkAceptar.setStyleName("link");
		linkAceptar.setVisible(true);
		linkCerrar.setStyleName("link");

		footerBar.addLink(linkNoNormalizar);
		footerBar.addLink(linkAceptar);
		footerBar.addLink(linkCerrar);
		mainPanel.add(footerBar);
		footer.setVisible(false);
		linkNoNormalizar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				// Logica para NoNormalizar Domicilio!
				if (comandoNoNormalizar != null){
					comandoNoNormalizar.execute();
				}
			}
		});
		linkAceptar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
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
	
	public void setDomicilios(DomiciliosCuentaDto domicilio) {
		this.domicilio = domicilio;
		setearFormatoNormalizador();
		loadTable();
	}

	public void setDomiciliosConDudas(List<DomiciliosCuentaDto> domicilios) {
		this.domiciliosEnDuda = domicilios;
		setearFormatoNormalizador();
		loadTableConVariosDomicilios();
	}

	private void loadTableConVariosDomicilios() {
		if (domicilioResult != null) {
			domicilioResult.unsinkEvents(Event.getEventsSunk(domicilioResult.getElement()));
			domicilioResult.removeFromParent();
		}
		domicilioResult = new NextelTable();
		initTable(domicilioResult);
		grillaDomicilio.setWidget(domicilioResult);
		grillaPpal.getCellFormatter().addStyleName(0, 0, "layout");
		grillaPpal.getCellFormatter().addStyleName(1, 0, "alignCenter");
		grillaPpal.setText(1, 0, domicilio.getDomicilios());
		for (int i = 0; i < domiciliosEnDuda.size(); i++) {
			domicilioResult.setHTML(i+1, 0, domiciliosEnDuda.get(i).getDomicilios());
		}
		setVisible(true);
	}

	
	private void loadTable() {
		if (domicilioResult != null) {
			domicilioResult.unsinkEvents(Event.getEventsSunk(domicilioResult.getElement()));
			domicilioResult.removeFromParent();
		}
		domicilioResult = new NextelTable();
		initTable(domicilioResult);
		grillaDomicilio.setWidget(domicilioResult);
		grillaPpal.getCellFormatter().addStyleName(0, 0, "layout");
		grillaPpal.getCellFormatter().addStyleName(1, 0, "alignCenter");
		grillaPpal.setText(1, 0, domicilio.getDomicilios());
		domicilioResult.setHTML(1, 0, domicilio.getDomicilios());
		setVisible(true);
	}
	
	private void initTable(FlexTable domicilioResult){
		//
		String[] widths = { "650px",};
		for (int col = 0; col < widths.length; col++) {
			domicilioResult.getColumnFormatter().setWidth(col, widths[col]);
		}
		domicilioResult.getColumnFormatter().addStyleName(0, "alignCenter");
		domicilioResult.getColumnFormatter().addStyleName(1, "alignCenter");
		domicilioResult.setCellPadding(0);
		domicilioResult.setCellSpacing(0);
		domicilioResult.addStyleName("gwt-BuscarCuentaResultTable");
		domicilioResult.getRowFormatter().addStyleName(0, "header");
		domicilioResult.setHTML(0, 0, "Seleccione alguna de estas opciones");
	}
	
	@Override
	public void showAndCenter() {
		super.showAndCenter();
	}

	public boolean isNormalizado() {
		return normalizado;
	}

	public void setNormalizado(boolean normalizado) {
		this.normalizado = normalizado;
	}

//	public List<DomiciliosCuentaDto> getDudas() {
//		return dudas;
//	}
//
//	public void setDudas(List<DomiciliosCuentaDto> dudas) {
//		this.dudas = dudas;
//	}

	public List<NormalizacionDomicilioMotivoDto> getMotivos() {
		return motivos;
	}

	public void setMotivos(List<NormalizacionDomicilioMotivoDto> motivos) {
		this.motivos = motivos;
	}

	public List<DomiciliosCuentaDto> getDomiciliosEnDuda() {
		return domiciliosEnDuda;
	}

	public void setDomiciliosEnDuda(List<DomiciliosCuentaDto> domiciliosEnDuda) {
		this.domiciliosEnDuda = domiciliosEnDuda;
	}
	
	public void setDomicilio(DomiciliosCuentaDto domicilio) {
		this.domicilio = domicilio;
	}

	public DomiciliosCuentaDto getDomicilio() {
		return domicilio;
	}

	public DomiciliosCuentaDto getDomicilioCopiado() {
		DomiciliosCuentaDto domicilioCopiado = new DomiciliosCuentaDto(); 
		domicilioCopiado.setCalle(domicilio.getCalle());
		
		return domicilioCopiado;
	}
	
	public int getRowSelected() {
		return domicilioResult.getRowSelected();
	}

	public void setRowSelected(int rowSelected) {
		this.rowSelected = rowSelected;
	}
	
	public DomiciliosCuentaDto getDomicilioEnDudaSelected(){
		return domiciliosEnDuda.get(getRowSelected());
	}
}

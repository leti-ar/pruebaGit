package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author eSalvador 
 **/
public class NormalizarDomicilioUI extends NextelDialog {

	private Grid grillaPpal;
	private SimplePanel grillaDomicilio;
	private FlexTable domicilioResult;
	private Command comandoAceptar;
	private Command comandoNoNormalizar;
	private FormButtonsBar footerBar;
	private SimpleLink linkCerrar;
	private SimpleLink linkAceptar;
	private SimpleLink linkNoNormalizar;
	private DomiciliosUIData domiciliosData;
	private DomiciliosCuentaDto domicilio;
	
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
		domiciliosData = new DomiciliosUIData();
		footerBar = new FormButtonsBar();
		linkNoNormalizar = new SimpleLink("No Normalizar");
		linkAceptar = new SimpleLink("Aceptar");
		linkCerrar = new SimpleLink("Cerrar");
		grillaDomicilio = new SimplePanel();
		domicilioResult = new FlexTable();
		addStyleName("gwt-NormalizarDomicilioUI");
		grillaDomicilio.addStyleName("resultTableScroll");
		grillaPpal = new Grid(5, 1);
		setWidth("480px");

		grillaPpal.getColumnFormatter().setWidth(0, "550px");
		grillaPpal.setText(0, 0, "Domicilio Ingresado:");
		grillaPpal.getRowFormatter().addStyleName(0, "layout");
		grillaPpal.getCellFormatter().setHeight(1, 0, "25px");
		grillaPpal.getRowFormatter().setStyleName(1, "gwt-TitledPanel");
		grillaPpal.setWidget(1, 0, new Label());
		grillaPpal.setWidget(2, 0, grillaDomicilio);
		grillaPpal.setText(3, 0, "Motivo:");
		grillaPpal.setWidget(4, 0, new Label());
		
		add(grillaPpal);

		linkNoNormalizar.setStyleName("link");
		linkAceptar.setStyleName("link");
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
		loadTable();
	}

	public void agregaTableListeners(){
		domicilioResult.addTableListener(new TableListener() {
			public void onCellClicked(SourcesTableEvents arg0, int row, int col) {
				if (row != 0) {
					//Cambiar de estilo cuando haga click en las celdas:
					domicilioResult.getRowFormatter().setStyleName(row, "layout");
				}
			}
		});
	}
	
	private void loadTable() {
		if (domicilioResult != null) {
			domicilioResult.unsinkEvents(Event.getEventsSunk(domicilioResult.getElement()));
			domicilioResult.removeFromParent();
		}
		domicilioResult = new FlexTable();
		initTable(domicilioResult);
		grillaDomicilio.setWidget(domicilioResult);
		grillaPpal.getCellFormatter().addStyleName(0, 0, "layout");
		grillaPpal.getCellFormatter().addStyleName(1, 0, "alignCenter");
		grillaPpal.setText(1, 0, domicilio.getDomicilios());
		domicilioResult.setHTML(1, 0, domicilio.getDomicilios());
		setVisible(true);
		agregaTableListeners();
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
}

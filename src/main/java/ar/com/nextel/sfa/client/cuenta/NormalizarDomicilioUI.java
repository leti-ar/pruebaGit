package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;
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

	//private TablePageBar tablePageBar;
	private List<CuentaSearchResultDto> cuentas;
	//
	
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
		domicilioResult.addStyleName("gwt-BuscarCuentaResultPanel");
		grillaDomicilio.addStyleName("resultTableWrapper");
		grillaPpal = new Grid(5, 1);
		setWidth("450px");
		
		grillaDomicilio.addStyleName("layout");
		domicilioResult.setText(0, 0, "Seleccione alguna de estas opciones");
		domicilioResult.setWidget(1, 0, linkAceptar); //TODO: Aca va insertado en la grilla, todos los DomiciliosCuentaDto.getDomicilios()
		grillaDomicilio.setWidget(domicilioResult);
		
		grillaPpal.addStyleName("layout");
		grillaPpal.getColumnFormatter().setWidth(0, "85px");
		grillaPpal.addStyleName("layout");
		grillaPpal.setText(0, 0, "Domicilio Ingresado:");
		grillaPpal.setWidget(1, 0, domiciliosData.getCalle()); //TODO: Aca van los datos del domicilio que llega! Averiguar cuales!
		grillaPpal.setWidget(2, 0, grillaDomicilio); //TODO: Aca va la grilla!
		grillaPpal.setText(3, 0, "Motivo:");
		grillaPpal.setWidget(4, 0, domiciliosData.getNumero()); //TODO: Aca va el motivo!
		
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
	
	public void setCuentas(List<CuentaSearchResultDto> cuentas) {
		this.cuentas = cuentas;
		loadTable();
	}

	private void loadTable() {
		if (domicilioResult != null) {
			domicilioResult.unsinkEvents(Event.getEventsSunk(domicilioResult.getElement()));
			domicilioResult.removeFromParent();
		}
		domicilioResult = new FlexTable();
		initTable(domicilioResult);
		grillaDomicilio.setWidget(domicilioResult);
		int row = 1;
		for (CuentaSearchResultDto cuenta : cuentas) {
			domicilioResult.setWidget(row, 0 , null); //TODO: Aca deberia poner un CheckBox en vez del NULL!
			domicilioResult.setHTML(row, 1, cuenta.getNumero());
			domicilioResult.setHTML(row, 2, cuenta.getRazonSocial());
			domicilioResult.setHTML(row, 3, cuenta.getApellidoContacto());
			domicilioResult.setHTML(row, 4, cuenta.getNumeroTelefono() != null ? cuenta.getNumeroTelefono() : "");
			row++;
		}
		setVisible(true);
	}
	
	private void initTable(FlexTable domicilioResult){
		//
		String[] widths = { "24px", "24px", "24px", "150px", "250px",};
		for (int col = 0; col < widths.length; col++) {
			domicilioResult.getColumnFormatter().setWidth(col, widths[col]);
		}
		domicilioResult.getColumnFormatter().addStyleName(0, "alignCenter");
		domicilioResult.getColumnFormatter().addStyleName(1, "alignCenter");
		domicilioResult.getColumnFormatter().addStyleName(2, "alignCenter");
		domicilioResult.setCellPadding(0);
		domicilioResult.setCellSpacing(0);
		domicilioResult.addStyleName("gwt-BuscarCuentaResultTable");
		domicilioResult.getRowFormatter().addStyleName(0, "header");
		domicilioResult.setHTML(0, 0, Sfa.constant().whiteSpace()); //TODO: Aca deberia poner un CheckBox!
		domicilioResult.setHTML(0, 1, "Número");
		domicilioResult.setHTML(0, 2, "Razón Social");
		domicilioResult.setHTML(0, 3, "Responsable");
		domicilioResult.setHTML(0, 4, "Número de teléfono");
	}
	
	@Override
	public void showAndCenter() {
		super.showAndCenter();
	}
}

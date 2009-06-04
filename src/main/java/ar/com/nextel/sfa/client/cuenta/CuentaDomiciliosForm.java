package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author eSalvador 
 **/
public class CuentaDomiciliosForm extends Composite {

	private static CuentaDomiciliosForm instance = new CuentaDomiciliosForm();
	private FlowPanel mainPanel;
	private FormButtonsBar footerBar;
	private FlexTable datosTabla;
//	private List<SolicitudServicioCerradaDto> ssCerradasAsociadas = new ArrayList<SolicitudServicioCerradaDto>();
	private DomiciliosCuentaDto domicilioAEditar;
//	private int rowDomicilioABorrar;
	public CuentaDto cuentaDto;
//	private String estadoNormalizacion;

	public static CuentaDomiciliosForm getInstance() {
		return instance;
	}

	private CuentaDomiciliosForm() {
		mainPanel = new FlowPanel();
		footerBar = new FormButtonsBar();
		datosTabla = new FlexTable();
		agregaTableListeners();
		initWidget(mainPanel);
		mainPanel.clear();
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		//
		Button crearDomicilio = new Button("Crear nuevo");
		crearDomicilio.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				//DomicilioUI.getInstance().setCuentaDto(cuentaDto);
				DomicilioUI.getInstance().setDomicilioAEditar(new DomiciliosCuentaDto());
				DomicilioUI.getInstance().setComandoAceptar(new Command(){
					public void execute() {
						   DomiciliosCuentaDto domicilio = DomicilioUI.getInstance().getDomicilioAEditar();
						   PersonaDto persona = cuentaDto.getPersona();
						   persona.getDomicilios().add(domicilio);
						   refrescaTablaConNuevoDomicilio();
						}
				});
				DomicilioUI.getInstance().cargarPopupNuevoDomicilio();
			}
		});
		crearDomicilio.addStyleName("crearDomicilioButton");
		SimplePanel crearDomicilioWrapper = new SimplePanel();
		crearDomicilioWrapper.add(crearDomicilio);
		crearDomicilioWrapper.addStyleName("crearDomicilioBWrapper");
		mainPanel.add(crearDomicilioWrapper);
		//
		initTable(datosTabla);
		mainPanel.add(datosTabla);
		mainPanel.add(footerBar);
	}

	private void initTable(FlexTable table) {

		String[] widths = { "24px", "24px", "24px", "100px", "100px", "75%", "50px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().whiteSpace());
		table.setHTML(0, 2, Sfa.constant().whiteSpace());
		table.setHTML(0, 3, Sfa.constant().entrega());
		table.setHTML(0, 4, Sfa.constant().facturacion());
		table.setHTML(0, 5, Sfa.constant().domicilios());
		table.setHTML(0, 6, Sfa.constant().whiteSpace());
	}

	/**
	 * @author eSalvador
	 * Refresca la grilla de domicilios
	 **/
	public void refrescaTablaConNuevoDomicilio(){
		datosTabla.clear();
		cargaTablaDomicilios(cuentaDto);
	}
	
	public void refrescaTablaConDomiciliosBorrados(int row){
		datosTabla.removeRow(row);
		cargaTablaDomicilios(cuentaDto);
	}
	
	/**
	 * @author eSalvador
	 **/
	public void cargaTablaDomicilios(final CuentaDto cuentaDto) {
		this.cuentaDto = cuentaDto;
		
		List<DomiciliosCuentaDto> domicilios;
		domicilios = cuentaDto.getPersona().getDomicilios();
		
		//Limpia la tabla de domicilios incialmente, si esta con datos:
		if (datosTabla.getRowCount() > 1){
			for (int j = 1; j < (datosTabla.getRowCount() - 1); j++) {
				datosTabla.removeRow(j);
			}
		}
		//
		for (int i = 0; i < domicilios.size(); i++) {
			if (domicilios.get(i) != null) {
				// Carga los iconos:
				datosTabla.setWidget(i + 1, 0, IconFactory.lapiz());

				// if (cuenta.isPuedeVerInfocom()) {
				datosTabla.setWidget(i + 1, 1, IconFactory.copiar());
				// }
				if (true) {
					datosTabla.setWidget(i + 1, 2, IconFactory.cancel());
				}
				
				if ((domicilios.get(i).getIdEntrega() != null) && (domicilios.get(i).getIdFacturacion() != null)){
				
				Long idEntrega = domicilios.get(i).getIdEntrega();
				Long idFacturacion = domicilios.get(i).getIdFacturacion();
					/** Logica para mostrar tipoDomicilio en la grilla de Resultados: */

					datosTabla.getCellFormatter().addStyleName(i + 1, 3, "alignCenter");
					datosTabla.getCellFormatter().addStyleName(i + 1, 4, "alignCenter");

					//Entrega;
					if (idEntrega == 2) { 
						datosTabla.setHTML(i + 1, 3, "Principal");	
					} else if(idEntrega == 0) {
						datosTabla.setHTML(i + 1, 3, "Si");
					} else if(idEntrega == 1) {
						datosTabla.setHTML(i + 1, 3, "No");
					}
					
					// Facturacion:
					if  (idFacturacion == 2){
						datosTabla.setHTML(i + 1, 4, "Principal");
					} else if(idFacturacion == 0){
						datosTabla.setHTML(i + 1, 4, "Si");
					}else if(idFacturacion == 1) {
						datosTabla.setHTML(i + 1, 4, "No");
					}
				}
				datosTabla.setHTML(i + 1, 5, domicilios.get(i).getDomicilios());
			}
		}
	}
	
	public void agregaTableListeners(){
		datosTabla.addTableListener(new TableListener() {
			public void onCellClicked(SourcesTableEvents arg0, int row, int col) {
				if (row != 0) {
					DomiciliosCuentaDto domicilio = cuentaDto.getPersona().getDomicilios().get(row - 1);
					//Acciones a tomar cuando haga click en los lapices de edicion:
					if (col == 0) {
						domicilioAEditar = domicilio;
						DomicilioUI.getInstance().setDomicilioAEditar(domicilioAEditar);
						DomicilioUI.getInstance().hide();
						if (domicilio.getVantiveId() != null){
							DomicilioUI.getInstance().openPopupAdviseDialog(DomicilioUI.getInstance().getOpenDomicilioUICommand());
						}else{
							DomicilioUI.getInstance().setComandoAceptar(new Command(){
								public void execute() {
									PersonaDto persona = cuentaDto.getPersona();
									int index = persona.getDomicilios().indexOf(domicilioAEditar);
									persona.getDomicilios().remove(index);
									persona.getDomicilios().add(index, DomicilioUI.getInstance().getDomicilioAEditar());
									domicilioAEditar = DomicilioUI.getInstance().getDomicilioAEditar();
									refrescaTablaConNuevoDomicilio();
									}
								});
							DomicilioUI.getInstance().cargarPopupEditarDomicilio(domicilioAEditar);
						}
					}
					// Acciones a tomar cuando haga click en iconos de copiado de domicilios:
					if (col == 1) {
						domicilioAEditar = domicilio;
						DomiciliosCuentaDto domicilioCopiado = domicilioAEditar.clone();
						domicilioCopiado.setId(null);
						domicilioCopiado.setNombre_usuario_ultima_modificacion(null);
						domicilioCopiado.setFecha_ultima_modificacion(null);
						DomicilioUI.getInstance().setDomicilioAEditar(domicilioCopiado);
						DomicilioUI.getInstance().setComandoAceptar(new Command(){
							public void execute() {
								   DomiciliosCuentaDto domicilio = DomicilioUI.getInstance().getDomicilioAEditar();
								   PersonaDto persona = cuentaDto.getPersona();
								   persona.getDomicilios().add(domicilio);
								   refrescaTablaConNuevoDomicilio();
								}
							});
						DomicilioUI.getInstance().cargarPopupCopiarDomicilio();
					}
					// Acciones a tomar cuando haga click en iconos de borrado de domicilios:
					if (col == 2) {
						DomicilioUI.getInstance().setRowDomicilioABorrar(row);
						DomicilioUI.getInstance().hide();
						domicilioAEditar = domicilio;
						DomicilioUI.getInstance().setCuentaDto(cuentaDto);
						DomicilioUI.getInstance().setDomicilioAEditar(domicilioAEditar);
						if (domicilio.getVantiveId() != null){
							DomicilioUI.getInstance().openPopupDeleteDialog(DomicilioUI.getInstance().getOpenDialogAdviceCommand());
						}else{ 
							DomicilioUI.getInstance().openPopupDeleteDialog(DomicilioUI.getInstance().getComandoBorrar());
						}
						
					}
				}
			}
		});
	}
	
	public List<String> validarCompletitud() {
		GwtValidator validator = CuentaEdicionTabPanel.getInstance().getValidator();
		validator.clear();
        boolean hayDomicilioEntrega = false;
        boolean hayDomicilioFacturacion = false;
        
		List<DomiciliosCuentaDto> listaDomicilios = cuentaDto.getPersona().getDomicilios();
		if (listaDomicilios==null || listaDomicilios.size()<0) {
			validator.addError(Sfa.constant().ERR_DOMICILIO_ENTREGA());
			validator.addError(Sfa.constant().ERR_DOMICILIO_FACTURACION());
		} else {
			for (DomiciliosCuentaDto domi : listaDomicilios) {
				
			/**TODO: Arreglar esto de abajo! Avisarle a Raul que se comento porque se cambio Todo lo de TipoDomicilioAsociado! */
//				if( domi.getTiposDomicilioAsociado()!=null) {
//					for (TipoDomicilioAsociadoDto tipoDom :domi.getTiposDomicilioAsociado()) {
//						hayDomicilioEntrega = tipoDom.getTipoDomicilio().getId()==TipoDomicilioAsociadoEnum.ENTREGA.getId();
//						hayDomicilioFacturacion = tipoDom.getTipoDomicilio().getId()==TipoDomicilioAsociadoEnum.FACTURACION.getId();
//					}
//				}
			}
			if (!hayDomicilioEntrega) 
				validator.addError(Sfa.constant().ERR_DOMICILIO_ENTREGA());
			if (!hayDomicilioFacturacion) 			
				validator.addError(Sfa.constant().ERR_DOMICILIO_FACTURACION());
		}
		validator.fillResult();
		return validator.getErrors();
	}

	public FlexTable getDatosTabla() {
		return datosTabla;
	}

	public void setDatosTabla(FlexTable datosTabla) {
		this.datosTabla = datosTabla;
	}
}
package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.dto.TipoDomicilioAsociadoDto;
import ar.com.nextel.sfa.client.dto.TipoDomicilioDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

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
	private CuentaUIData cuentaUIData;
	private List<SolicitudServicioCerradaDto> ssCerradasAsociadas = new ArrayList<SolicitudServicioCerradaDto>();
	private DomiciliosCuentaDto domicilioAEditar;
	private int rowDomicilioABorrar;
	
	// TODO; Analizar!!
	// private DomiciliosUIData domiciliosData;
	private CuentaDto cuentaDto;

	//

	public static CuentaDomiciliosForm getInstance() {
		return instance;
	}

	private CuentaDomiciliosForm() {
		mainPanel = new FlowPanel();
		footerBar = new FormButtonsBar();
		datosTabla = new FlexTable();
		initWidget(mainPanel);
		mainPanel.clear();
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		//
		Button crearDomicilio = new Button("Crear nuevo");
		crearDomicilio.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				// Abajo se setea en el DomicilioUI la accion a tomar al apretar Aceptar.
				DomicilioUI.getInstance().setComandoAceptar(getComandoNuevoDomicilio());
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
	 * Devuelve el comando que Agrega un nuevo Domicilio a la Persona.
	 **/
	private Command getComandoNuevoDomicilio() {
		/**TODO:Aca de beria hacer la validacion de datos de entrada, 
		 **     y despues abrir el popup de Normalizacion.
		 **     El addDomicilio a persona lo debe hacer el Aceptar del PopUp.*/
		Command comandoAceptar = new Command() {
			public void execute() {
				DomiciliosCuentaDto domicilioNuevo = DomicilioUI.getInstance().getDomiciliosData().getDomicilio();
				PersonaDto persona = cuentaDto.getPersona();
				persona.getDomicilios().add(domicilioNuevo);
				DomicilioUI.getInstance().hide();
				datosTabla.clear();
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
			}
		};
		return comandoAceptar;
	}
	
	
	/**
	 * @author eSalvador
	 * Devuelve el comando que Agrega el domicilio editado que le llega, con los datos nuevos.
	 **/
	private Command getComandoCopiarDomicilio() {
		/**TODO:Aca deberia hacer la validacion de datos de entrada, 
		 **     y despues abrir el popup de Normalizacion.
		 **     El addDomicilio a persona lo debe hacer el Aceptar del PopUp.*/
		Command comandoAceptar = new Command() {
			public void execute() {
				PersonaDto persona = cuentaDto.getPersona();
				persona.getDomicilios().add(DomicilioUI.getInstance().getDomiciliosData().getDomicilioCopiado());
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().refrescaTablaConDomiciliosEditados();
				DomicilioUI.getInstance().hide();
			}
		};
		return comandoAceptar;
	}
	
	/**
	 * @author eSalvador
	 * Devuelve el comando que Agrega el domicilio editado que le llega, con los datos nuevos.
	 **/
	private Command getComandoEditarDomicilio() {
		/**TODO:Aca deberia hacer la validacion de datos de entrada, 
		 **     y despues abrir el popup de Normalizacion.
		 **     El addDomicilio a persona lo debe hacer el Aceptar del PopUp.*/
		Command comandoAceptar = new Command() {
			public void execute() {
				DomiciliosCuentaDto domicilioEditado = DomicilioUI.getInstance().getDomiciliosData().getDomicilio();
				DomicilioUI.getInstance().hide();
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().refrescaTablaConNuevoDomicilio(domicilioEditado);
			}
		};
		return comandoAceptar;
	}
	
	/**
	 * @author eSalvador
	 * Devuelve el comando que Borra el domicilio seleccionado en la grilla de resultados.
	 **/
	private Command getComandoBorrarDomicilio(DomiciliosCuentaDto domicilio) {
		domicilioAEditar = domicilio;
		Command comandoAceptar = new Command() {
			public void execute() {
				//domicilioAEditar;
				PersonaDto persona = cuentaDto.getPersona();
				List<DomiciliosCuentaDto> domicilios = persona.getDomicilios();
				for (int j = 0; j < domicilios.size(); j++) {
					if (domicilios.get(j) == domicilioAEditar){
						persona.removeDomicilio(domicilioAEditar);
					}
				}
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().refrescaTablaConDomiciliosBorrados(rowDomicilioABorrar);
				MessageDialog.getInstance().hide();
			}
		};
		return comandoAceptar;
	}
	
	/**
	 *@author eSalvador
	 **/
	private List<SolicitudServicioCerradaDto> buscaSSCerradasAsociadas(String idCuenta) {
		// Defino el Dto para la busqueda:
		SolicitudServicioCerradaDto ssCerradaDto = new SolicitudServicioCerradaDto();
		ssCerradaDto.setNumeroCuenta(idCuenta);
		ssCerradaDto.setCantidadResultados(new Long(10));
		// Llamo al servicio buscando SSCerradas:
		SolicitudRpcService.Util.getInstance().searchSSCerrada(ssCerradaDto,
				new DefaultWaitCallback<List<SolicitudServicioCerradaResultDto>>() {
					@Override
					public void success(List<SolicitudServicioCerradaResultDto> result) {
						for (int i = 0; i < result.size(); i++) {
							ssCerradasAsociadas.add((SolicitudServicioCerradaDto) result);
						}
					}
				});
		return ssCerradasAsociadas;
	}

	/**
	 * @author eSalvador
	 * Aca llama al validador para ver si tiene SSCerradas, y si tiene, advertir con un
	 * popup, e inhabilitar los campos de edicion del Domicilio.
	 **/
	public void validaHabilitacionDeCampos(){
		if (buscaSSCerradasAsociadas(cuentaDto.getCodigoVantive()).size() != 0) {
			domicilioAEditar.setLocked(true);
		} else {
			domicilioAEditar.setLocked(false);
		}
	}
	
	/**
	 * @author eSalvador
	 * Refresca la grilla de domicilios
	 **/
	public void refrescaTablaConNuevoDomicilio(DomiciliosCuentaDto domicilioNuevoOEditado){
		this.domicilioAEditar = domicilioNuevoOEditado;
		validaHabilitacionDeCampos();
		datosTabla.clear();
		CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
	}
	
	public void refrescaTablaConDomiciliosEditados(){
		datosTabla.clear();
		CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
	}
	
	public void refrescaTablaConDomiciliosBorrados(int row){
		datosTabla.removeRow(row);
		CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
	}
	
	/**
	 * @author eSalvador
	 **/
	public void cargaTablaDomicilios(final CuentaDto cuentaDto) {
		this.cuentaDto = cuentaDto;

		List<DomiciliosCuentaDto> domicilios;
		domicilios = cuentaDto.getPersona().getDomicilios();
		agregaTableListeners();
		
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
				List<TipoDomicilioAsociadoDto> listaTipoDomicilioAsociado = domicilios.get(i).getTiposDomicilioAsociado();
				for (int j = 0; j < listaTipoDomicilioAsociado.size(); j++) {
					/** Logica para mostrar tipoDomicilio en la grilla de Resultados: */
					TipoDomicilioDto tipoDomicilio = listaTipoDomicilioAsociado.get(j).getTipoDomicilio();
					datosTabla.getCellFormatter().addStyleName(i + 1, 3, "alignCenter");
					datosTabla.getCellFormatter().addStyleName(i + 1, 4, "alignCenter");

					//Entrega;
					if (tipoDomicilio.getId() == 4) { 
						if (listaTipoDomicilioAsociado.get(j).getPrincipal()){
							datosTabla.setHTML(i + 1, 3, "Principal");	
						} else {
							datosTabla.setHTML(i + 1, 3, "Si");
						}
					}else if (tipoDomicilio.getId() == 1) {
						// Facturacion:
						if (listaTipoDomicilioAsociado.get(j).getPrincipal()){
							datosTabla.setHTML(i + 1, 4, "Principal");
						} else {
							datosTabla.setHTML(i + 1, 4, "Si");
						}
					}else if (tipoDomicilio.getId() == 0){
						if (tipoDomicilio.getDescripcion().equals("FacturacionNo")){
							datosTabla.setHTML(i + 1, 4, "No");	
						}else if (tipoDomicilio.getDescripcion().equals("EntregaNo")){
							datosTabla.setHTML(i + 1, 3, "No");
						}
					}
				}
				datosTabla.setHTML(i + 1, 5, domicilios.get(i).getDomicilios());
			}
		}
	}
	
	public void agregaTableListeners(){
		datosTabla.addTableListener(new TableListener() {
			public void onCellClicked(SourcesTableEvents arg0, int row, int col) {
				DomiciliosCuentaDto domicilio = cuentaDto.getPersona().getDomicilios().get(row - 1);
				if (row != 0) {
					// Acciones a tomar cuando haga click en los lapices de edicion:
					if (col == 0) {
						domicilioAEditar = domicilio;
						DomicilioUI.getInstance().setComandoAceptar(getComandoEditarDomicilio());
						validaHabilitacionDeCampos();
						DomicilioUI.getInstance().cargarPopupEditarDomicilio(domicilio);
					}
					// Acciones a tomar cuando haga click en iconos de copiado de domicilios:
					if (col == 1) {
						DomicilioUI.getInstance().setComandoAceptar(getComandoCopiarDomicilio());	
						DomicilioUI.getInstance().cargarPopupCopiarDomicilio(domicilio);
					}
					// Acciones a tomar cuando haga click en iconos de borrado de domicilios:
					if (col == 2) {
						rowDomicilioABorrar = row;
						MessageDialog.getInstance().setDialogTitle("Eliminar Domicilio");
						MessageDialog.getInstance().showSiNo("¿Esta seguro que desea eliminar el domicilio seleccionado?",getComandoBorrarDomicilio(domicilio),MessageDialog.getInstance().getCloseCommand());
					}
				}
			}
		});
	}
}

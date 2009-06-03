package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.NormalizarDomicilioResultDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProvinciaDto;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
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
	private boolean noEditable;
	private static DomicilioUI instance = new DomicilioUI();
	private DomiciliosCuentaDto domicilioAEditar;
	private String estadoNormalizacion;
	private int rowDomicilioABorrar;
	public CuentaDto cuentaDto;
	
	Label calleLabel = new Label(Sfa.constant().calle());
	Label numCalleLabel = new Label(Sfa.constant().numeroCalle());
	Label localidadLabel = new Label(Sfa.constant().localidad());
	Label cpLabel = new Label(Sfa.constant().cp());
	Label provinciaLabel = new Label(Sfa.constant().provincia());
	Label labelEntrega = new Label(Sfa.constant().entrega());
	Label labelFacturacion = new Label(Sfa.constant().facturacion());
	Label labelValidado1 = new Label(Sfa.constant().validado1());
	Label labelValidado2 = new Label (Sfa.constant().validado2());
	Label labelUsuario = new Label (Sfa.constant().usuario_domicilio());
	Label labelFecha = new Label (Sfa.constant().fecha_Modificacion());
	
	public void hideLabelsParaContactos() {
		labelEntrega.setVisible(false);
		labelUsuario.setVisible(false);
		labelFecha.setVisible(false);
		labelFacturacion.setVisible(false);
		labelValidado1.setVisible(false);
		labelValidado2.setVisible(false);
	}
	
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

	public CuentaDto getCuentaDto() {
		return cuentaDto;
	}

	public void setCuentaDto(CuentaDto cuentaDto) {
		this.cuentaDto = cuentaDto;
	}

	/**
	 * @author esalvador
	 **/
	public void cargarPopupNuevoDomicilioParaContactos() {
		domiciliosData.clean();
		domiciliosData.setDomicilio(null);
		linkAceptar.setVisible(true);
		domiciliosData.hideLabelsParaContactos();
		domiciliosData.enableFields();
		showAndCenter();
		setDialogTitle("Crear Domicilio");
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
		setDialogTitle("Crear Domicilio");
	}

	/**
	 * @author esalvador
	 **/
	public void cargarPopupCopiarDomicilio(DomiciliosCuentaDto domicilio) {
		domiciliosData.setDomicilio(domicilio);
		showAndCenter();
		domiciliosData.enableFields();
		linkAceptar.setVisible(true);
		setDialogTitle("Copiar Domicilio");
	}
	
	/**
	 * @author esalvador
	 **/
	public void cargarPopupEditarDomicilio(DomiciliosCuentaDto domicilio) {
		if (domicilio.getVantiveId() != null){
			noEditable = true;	
		}else{
			noEditable = false;
		}
		domiciliosData.setDomicilio(domicilio);
		showAndCenter();
		if (noEditable){
			domiciliosData.disableFields();
			linkAceptar.setVisible(false);
		}else{
			domiciliosData.enableFields();
			linkAceptar.setVisible(true);
		}
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
		
		//Campos Obligatorios:
		calleLabel.addStyleName("req");
		numCalleLabel.addStyleName("req");
		localidadLabel.addStyleName("req");
		cpLabel.addStyleName("req");
		provinciaLabel.addStyleName("req");
		//
		gridUp.getColumnFormatter().setWidth(1, "85px");
		gridUp.getColumnFormatter().setWidth(3, "85px");
		gridUp.addStyleName("layout");
		gridUp.setText(1, 1, Sfa.constant().cpa());
		gridUp.setWidget(1, 2, domiciliosData.getCpa());
		gridUp.setWidget(2, 1, calleLabel);
		gridUp.setWidget(2, 2, domiciliosData.getCalle());
		gridUp.setWidget(2, 3, numCalleLabel);
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
		gridDown.setWidget(1, 1, localidadLabel);
		gridDown.setWidget(1, 2, domiciliosData.getLocalidad());
		gridDown.setWidget(1, 3, cpLabel);
		gridDown.setWidget(1, 4, domiciliosData.getCodigoPostal());
		cargaComboProvinciasDto();
		gridDown.setWidget(2, 1, provinciaLabel);
		gridDown.setWidget(2, 2, domiciliosData.getProvincia());
		//
		gridDown.setText(2, 3, Sfa.constant().partido());
		gridDown.setWidget(2, 4, domiciliosData.getPartido());
		gridDown.setWidget(3, 1, labelEntrega);
		gridDown.setWidget(3, 2, domiciliosData.getEntrega());
		gridDown.setWidget(3, 3, labelFacturacion);
		gridDown.setWidget(3, 4, domiciliosData.getFacturacion());
		gridDown.setWidget(4, 1, labelValidado1);
		gridDown.setWidget(4, 2, labelValidado2);
		gridDown.setWidget(4, 3, domiciliosData.getValidado());
		gridObs.addStyleName("layout");
		gridObs.setText(0, 1, Sfa.constant().obs_domicilio());
		gridObs.setWidget(1, 1, domiciliosData.getObservaciones());
		gridUser.addStyleName("layout");
		gridUser.setWidget(0, 1, labelUsuario);
		gridUser.setWidget(0, 2, domiciliosData.getNombreUsuarioUltimaModificacion());
		gridUser.setWidget(0, 3, labelFecha);
		gridUser.setWidget(0, 4, domiciliosData.getFechaUltimaModificacion());

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
	private void cargaComboProvinciasDto(){
		CuentaRpcService.Util.getInstance().getProvinciasInitializer(new DefaultWaitCallback<List<ProvinciaDto>>() {
			@Override
			public void success(List<ProvinciaDto> result) {
				domiciliosData.getProvincia().addAllItems(result);
			}
		});
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
		return noEditable;
	}

	public void setEditable(boolean editable) {
		this.noEditable = editable;
	}
	
	////////////////////////////////////////////////////////////
	
	
	
	public int getRowDomicilioABorrar() {
		return rowDomicilioABorrar;
	}

	public DomiciliosCuentaDto getDomicilioAEditar() {
		return domicilioAEditar;
	}

	public void setDomicilioAEditar(DomiciliosCuentaDto domicilioAEditar) {
		this.domicilioAEditar = domicilioAEditar;
	}

	public void setRowDomicilioABorrar(int rowDomicilioABorrar) {
		this.rowDomicilioABorrar = rowDomicilioABorrar;
	}

	/**
	 * @author eSalvador
	 **/
	public Command getComandoAceptarDomicilioCopiadoServiceCall() {
		Command comandoCopiar = new Command() {
			public void execute() {
				if (camposValidos()){
				domicilioAEditar = DomicilioUI.getInstance().getDomiciliosData().getDomicilioCopiado(); 	
				CuentaRpcService.Util.getInstance().normalizarDomicilio(domicilioAEditar,
						new DefaultWaitCallback<NormalizarDomicilioResultDto>() {
							public void success(NormalizarDomicilioResultDto result) {
							   
							   List<DomiciliosCuentaDto> listaDomicilios = new ArrayList();
							   listaDomicilios.add(result.getDireccion());
								
								//tipo: exito|no_parseado|no_encontrado|dudas
								if (result.getTipo().equals("exito")){
									NormalizarDomicilioUI.getInstance().setNormalizado(true);
									abrirPopupNormalizacion(listaDomicilios,getComandoAgregarDomicilioCopiadoSinNormalizar(),getComandoAgregarDomicilioCopiado(result.getTipo()));
								}else if(result.getTipo().equals("no_encontrado")){
									setMotivosNoNormalizacion(result);
									abrirPopupNormalizacion(listaDomicilios,getComandoAgregarDomicilioCopiadoSinNormalizar(),getComandoAgregarDomicilioCopiado(result.getTipo()));
								}else if(result.getTipo().equals("dudas")){
									NormalizarDomicilioUI.getInstance().setNormalizado(true);
									abrirPopupNormalizacion(result.getDudas(),getComandoAgregarDomicilioCopiadoSinNormalizar(),getComandoAgregarDomicilioCopiado(result.getTipo()));
								}else if(result.getTipo().equals("no_parseado")){
									setMotivosNoNormalizacion(result);
									abrirPopupNormalizacion(listaDomicilios,getComandoAgregarDomicilioCopiadoSinNormalizar(),getComandoAgregarDomicilioCopiado(result.getTipo()));
								}
							}
							@Override
							public void failure(Throwable exception) {
							}
						});
			}
		   } 
		};
	return comandoCopiar;
	}
	
	public Command getComandoAceptarEdicionServiceCall() {
		Command comandoEditar = new Command() {
			public void execute() {
				if (camposValidos()){
				domicilioAEditar = DomicilioUI.getInstance().getDomiciliosData().getDomicilio(); 	
				CuentaRpcService.Util.getInstance().normalizarDomicilio(domicilioAEditar,
						new DefaultWaitCallback<NormalizarDomicilioResultDto>() {
							public void success(NormalizarDomicilioResultDto result) {
								
								List<DomiciliosCuentaDto> listaDomicilios = new ArrayList();
								listaDomicilios.add(result.getDireccion());
								
								//tipo: exito|no_parseado|no_encontrado|dudas
								if (result.getTipo().equals("exito")){
									//domicilioAEditar = result.getDireccion();
									NormalizarDomicilioUI.getInstance().setNormalizado(true);
									abrirPopupNormalizacion(listaDomicilios ,getComandoAgregarDomicilioEditadoSinNormalizar(),getComandoAgregarDomicilioEditado(result.getTipo()));
								}else if(result.getTipo().equals("no_encontrado")){
									setMotivosNoNormalizacion(result);
									abrirPopupNormalizacion(listaDomicilios,getComandoAgregarDomicilioEditadoSinNormalizar(),getComandoAgregarDomicilioEditado(result.getTipo()));
								}else if(result.getTipo().equals("dudas")){
									NormalizarDomicilioUI.getInstance().setNormalizado(true);
									abrirPopupNormalizacion(result.getDudas(),getComandoAgregarDomicilioEditadoSinNormalizar(),getComandoAgregarDomicilioEditado(result.getTipo()));
								}else if(result.getTipo().equals("no_parseado")){
									setMotivosNoNormalizacion(result);
									abrirPopupNormalizacion(listaDomicilios,getComandoAgregarDomicilioEditadoSinNormalizar(),getComandoAgregarDomicilioEditado(result.getTipo()));
								}
							}
							@Override
							public void failure(Throwable exception) {
								GWT.log(exception.getMessage(), exception.getCause());
							}
						});
			  }
		    }
		 };
	return comandoEditar;
	}
	
	public Command getComandoAceptarNuevoDomicilioServiceCall() {
		Command comandoEditar = new Command() {
			public void execute() {
				if (camposValidos()){
				domicilioAEditar = DomicilioUI.getInstance().getDomiciliosData().getDomicilio();	
				CuentaRpcService.Util.getInstance().normalizarDomicilio(domicilioAEditar,
						new DefaultWaitCallback<NormalizarDomicilioResultDto>() {
							public void success(NormalizarDomicilioResultDto result) {
								
								List<DomiciliosCuentaDto> listaDomicilios = new ArrayList();
								listaDomicilios.add(domicilioAEditar);
								
								//tipo: exito|no_parseado|no_encontrado|dudas
								if (result.getTipo().equals("exito")){
									mapeaIdCombosTipoDomicilio(result);
									NormalizarDomicilioUI.getInstance().setNormalizado(true);
									abrirPopupNormalizacion(listaDomicilios,getComandoAgregarNuevoDomicilioSinNormalizar(),getComandoAgregarNuevoDomicilio(result.getTipo()));
								}else if(result.getTipo().equals("no_encontrado")){
									setMotivosNoNormalizacion(result);
									abrirPopupNormalizacion(listaDomicilios,getComandoAgregarNuevoDomicilioSinNormalizar(),getComandoAgregarNuevoDomicilio(result.getTipo()));
								}else if(result.getTipo().equals("dudas")){
									NormalizarDomicilioUI.getInstance().setNormalizado(true);
									abrirPopupNormalizacion(result.getDudas(),getComandoAgregarNuevoDomicilioSinNormalizar(),getComandoAgregarNuevoDomicilio(result.getTipo()));
								}else if(result.getTipo().equals("no_parseado")){
									setMotivosNoNormalizacion(result);
									abrirPopupNormalizacion(listaDomicilios,getComandoAgregarNuevoDomicilioSinNormalizar(),getComandoAgregarNuevoDomicilio(result.getTipo()));
								}
							}
							@Override
							public void failure(Throwable exception) {
							}
						});
				}
			}
		 };
	return comandoEditar;
	}
	
	private Command getComandoAgregarDomicilioCopiadoSinNormalizar(){
		Command agregaDomicilioCopiado = new Command(){

			public void execute() {
				PersonaDto persona = cuentaDto.getPersona();
				persona.getDomicilios().add(domicilioAEditar);
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().refrescaTablaConDomiciliosEditados();
				DomicilioUI.getInstance().hide();
				NormalizarDomicilioUI.getInstance().hide();
			}
		};
		return agregaDomicilioCopiado;
	}
	
	private Command getComandoAgregarDomicilioCopiado(String estadoNorm){
		estadoNormalizacion = estadoNorm;
		Command agregaDomicilioCopiado = new Command(){
			public void execute() {
				PersonaDto persona = cuentaDto.getPersona();
				persona.getDomicilios().add(obtieneDomicilioEnGrilla());
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().refrescaTablaConNuevoDomicilio(obtieneDomicilioEnGrilla());
			}
		};
		return agregaDomicilioCopiado;
	}

	private DomiciliosCuentaDto obtieneDomicilioEnGrilla(){
		DomiciliosCuentaDto domicilioNormalizadoCopiado = null;
		if (NormalizarDomicilioUI.getInstance().getRowSelected() == 0){
			openPopupSelectDomicilioDialog();
		}else{
			domicilioNormalizadoCopiado = NormalizarDomicilioUI.getInstance().getDomicilioEnGrillaSelected();
			domicilioNormalizadoCopiado = mapeoDomicilioNormalizado(domicilioNormalizadoCopiado);
			DomicilioUI.getInstance().hide();
			NormalizarDomicilioUI.getInstance().hide();
		}
		return domicilioNormalizadoCopiado;
	}
	
	private Command getComandoAgregarDomicilioEditado(String estadoNorm){
		estadoNormalizacion = estadoNorm;
		Command agregaDomicilioEditado = new Command() {
			public void execute() {
				PersonaDto persona = cuentaDto.getPersona();
				int index = persona.getDomicilios().indexOf(domicilioAEditar);
				persona.getDomicilios().remove(index);
				persona.getDomicilios().add(index, obtieneDomicilioEnGrilla());
				DomicilioUI.getInstance().hide();
				NormalizarDomicilioUI.getInstance().hide();
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().refrescaTablaConNuevoDomicilio(obtieneDomicilioEnGrilla());
			}
		 };
	return agregaDomicilioEditado;
	}

	private Command getComandoAgregarDomicilioEditadoSinNormalizar(){
		Command agregaDomicilioEditado = new Command() {
			public void execute() {
				DomiciliosCuentaDto domicilioEditado = DomicilioUI.getInstance().getDomiciliosData().getDomicilio();
				DomicilioUI.getInstance().hide();
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().refrescaTablaConNuevoDomicilio(domicilioEditado);
				NormalizarDomicilioUI.getInstance().hide();
			}
		 };
	return agregaDomicilioEditado;
	}

	
	private DomiciliosCuentaDto mapeoDomicilioNormalizado(DomiciliosCuentaDto domicilioNormalizado){
		   DomiciliosUIData datosDomicilioNuevo = DomicilioUI.getInstance().getDomiciliosData();  
		   //Mapeos a Mano para no perder Datos:
		   domicilioNormalizado.setObservaciones(datosDomicilioNuevo.getObservaciones().getText());
		   domicilioNormalizado.setValidado(datosDomicilioNuevo.getValidado().isChecked());
		   //domicilioNormalizado.setCodigo_postal(domicilioAEditar.getCodigo_postal());
		   //REVISAR:
		   domicilioNormalizado.setIdEntrega(domicilioAEditar.getIdEntrega());
		   domicilioNormalizado.setIdFacturacion(domicilioAEditar.getIdFacturacion());
		   //
		   domicilioNormalizado.setEn_carga(domicilioAEditar.getEn_carga());
		   domicilioNormalizado.setNombre_usuario_ultima_modificacion(domicilioAEditar.getNombre_usuario_ultima_modificacion());
		   domicilioNormalizado.setFecha_ultima_modificacion(domicilioAEditar.getFecha_ultima_modificacion());
		   domicilioNormalizado.setActivo(domicilioAEditar.getActivo());
		   return domicilioNormalizado;
	}
	
	private Command getComandoAgregarNuevoDomicilio(String estadoNorm) {
		Command comandoAceptar = new Command() {
			public void execute() {
				domicilioAEditar = obtieneDomicilioEnGrilla();
				PersonaDto persona = cuentaDto.getPersona();
				persona.getDomicilios().add(obtieneDomicilioEnGrilla());
				CuentaDomiciliosForm.getInstance().getDatosTabla().clear();
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
			}
		};
		return comandoAceptar;
	}
	
	private Command getComandoAgregarNuevoDomicilioSinNormalizar() {
		Command comandoAceptar = new Command() {
			public void execute() {
				DomiciliosCuentaDto domicilioNuevo = DomicilioUI.getInstance().getDomiciliosData().getDomicilio();
				domicilioAEditar = domicilioNuevo;
				PersonaDto persona = cuentaDto.getPersona();
				persona.getDomicilios().add(domicilioNuevo);
				DomicilioUI.getInstance().hide();
				CuentaDomiciliosForm.getInstance().getDatosTabla().clear();
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
				NormalizarDomicilioUI.getInstance().hide();
			}
		};
		return comandoAceptar;
	}
	
	/**
	 * @author eSalvador
	 **/
	private void abrirPopupNormalizacion(List<DomiciliosCuentaDto> domiciliosAGrilla, Command comandoNoNormalizar, Command comandoAceptar) {
	//Aca si apreta el boton Aceptar
		NormalizarDomicilioUI.getInstance().setDomicilio(domicilioAEditar);
		NormalizarDomicilioUI.getInstance().agregaDomiciliosAGrilla(domiciliosAGrilla);
		NormalizarDomicilioUI.getInstance().setComandoAceptar(comandoAceptar);
		NormalizarDomicilioUI.getInstance().setComandoNoNormalizar(comandoNoNormalizar);
		NormalizarDomicilioUI.getInstance().showAndCenter();
	}
	
	public Command getComandoBorrar(){
		Command comandoBorrar = new Command() {
			public void execute() {
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
	return comandoBorrar;
	}
		
	private boolean camposValidos(){
		boolean valido = true;
		List<String> errores = DomicilioUI.getInstance().getDomiciliosData().validarCamposObligatorios();
		if (errores.size() != 0){
			valido = false;
			ErrorDialog.getInstance().setDialogTitle("Error");
			ErrorDialog.getInstance().show(errores);
		}
		return valido;
	}
	
	private DomiciliosCuentaDto mapeaIdCombosTipoDomicilio(NormalizarDomicilioResultDto result){
		Long idEntrega = domicilioAEditar.getIdEntrega();
		Long idFacturacion = domicilioAEditar.getIdFacturacion();
		domicilioAEditar = result.getDireccion();
		domicilioAEditar.setIdEntrega(idEntrega);
		domicilioAEditar.setIdFacturacion(idFacturacion);
		return domicilioAEditar;
	}
	
	private void setMotivosNoNormalizacion(NormalizarDomicilioResultDto result){
		NormalizarDomicilioUI.getInstance().setNormalizado(false);
		NormalizarDomicilioUI.getInstance().setMotivos(result.getMotivos());
		NormalizarDomicilioUI.getInstance().setDomiciliosEnGrilla(result.getDudas());
	}
	
	/**
	 * @author eSalvador
	 **/
	public void openPopupSelectDomicilioDialog() {
			MessageDialog.getInstance().setDialogTitle("Error");
			MessageDialog.getInstance().showAceptar("Para realizar la acción debe seleccionar una fila de la tabla.", getDummyCommand());
	}	
	
	/**
	 * @author eSalvador
	 **/
	public void openPopupAdviseDialog(Command comandoGenerico) {
			MessageDialog.getInstance().setDialogTitle("Advertencia");
			MessageDialog.getInstance().showAceptar("No puede modificar o borrar el domicilio. Ya se cerró una solicitud de servicio para la cuenta desde su creación.", comandoGenerico);
	}
	
	/**
	 * @author eSalvador
	 **/
	public void openPopupDeleteDialog(Command comandoGenerico) {
		MessageDialog.getInstance().setDialogTitle("Eliminar Domicilio");
		MessageDialog.getInstance().setSize("300px", "100px");
		MessageDialog.getInstance().showSiNo("¿Esta seguro que desea eliminar el domicilio seleccionado?",comandoGenerico,MessageDialog.getInstance().getCloseCommand());
	}

	
	private Command getDummyCommand(){
		Command dummyCommand = new Command() {
			public void execute() {
				MessageDialog.getInstance().hide();
			}
		};
	return dummyCommand;
	}
	
	public Command getOpenDomicilioUICommand(){
		Command openUICommand = new Command() {
			public void execute() {
				DomicilioUI.getInstance().setComandoAceptar(getDummyCommand());
				DomicilioUI.getInstance().cargarPopupEditarDomicilio(domicilioAEditar);
				MessageDialog.getInstance().hide();
			}
		};
	return openUICommand;
	}
	
	public Command getOpenDialogAdviceCommand(){
		Command openDialogCommand = new Command() {
			public void execute() {
				openPopupAdviseDialog(getDummyCommand());
			}
		};
	return openDialogCommand;
	}	
	////////////////////////////////////////////////////////////
	
}
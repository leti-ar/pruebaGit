package ar.com.nextel.sfa.client.domicilio;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoTipoDomicilioDto;
import ar.com.nextel.sfa.client.dto.NormalizarDomicilioResultDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProvinciaDto;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Componente para editar DomicilioDto.<br>
 * Antes de abrirlo, se debe llamar a:<br>
 * <ul>
 * <li>cargarListBoxEntregaFacturacion();
 * <li>setParentContacto()
 * <li>setComandoAceptar()
 * </ul>
 * Para abrirlo en modo no editable se puede setear el Domicilio con setDomicilioAEditar() y utilizar el
 * getOpenDomicilioUICommand. <br>
 * Para abrirlo en modo editable, se puede usar cargarPopupEditarDomicilio(), cargarPopupNuevoDomicilio() o
 * cargarPopupCopiarDomicilio().
 **/
public class DomicilioUI extends NextelDialog {

	private Grid gridUp;
	private Grid gridMed;
	private Grid gridDown;
	private Grid gridObs;
	private Grid gridUser;
	private HorizontalPanel validadoPorPanel;
	private Command comandoAceptar;
	private FormButtonsBar footerBar;
	private SimpleLink linkCerrar;
	private SimpleLink linkAceptar;
	private DomiciliosUIData domiciliosUIData;
	private boolean noEditable;
	private static DomicilioUI instance = new DomicilioUI();
	private DomiciliosCuentaDto domicilioAEditar;

	private PersonaDto persona;
	private boolean parentContacto;
	private boolean isDomicilioPpalEntrega;
	private boolean isDomicilioPpalFacturacion;

	Label calleLabel = new Label(Sfa.constant().calle());
	Label numCalleLabel = new Label(Sfa.constant().numeroCalle());
	Label localidadLabel = new Label(Sfa.constant().localidad());
	Label cpLabel = new Label(Sfa.constant().cp());
	Label provinciaLabel = new Label(Sfa.constant().provincia());
	Label labelEntrega = new Label(Sfa.constant().entrega());
	Label labelFacturacion = new Label(Sfa.constant().facturacion());
	Label labelValidado1 = new Label(Sfa.constant().validado1());
	Label labelValidado2 = new Label(Sfa.constant().validado2());
	Label labelUsuario = new Label(Sfa.constant().usuario_domicilio());
	Label labelFecha = new Label(Sfa.constant().fecha_Modificacion());

	public static DomicilioUI getInstance() {
		return instance;
	}

	private DomicilioUI() {
		super("Editar Domicilio", false, true);
		init();
	}

	public void clear() {
		super.clear();
	}

	/** Carga y muestra el dialog con el titulo 'Crear Domicilio' */
	public void cargarPopupNuevoDomicilio(DomiciliosCuentaDto domicilio) {
		cargarPopupDomicilio(domicilio, "Crear Domicilio");
	}

	/** Carga y muestra el dialog con el titulo 'Copiar Domicilio' */
	public void cargarPopupCopiarDomicilio(DomiciliosCuentaDto domicilio) {
		cargarPopupDomicilio(domicilio, "Copiar Domicilio");
	}

	/** Carga y muestra el dialog con el titulo 'Editar Domicilio' */
	public void cargarPopupEditarDomicilio(DomiciliosCuentaDto domicilio) {
		cargarPopupDomicilio(domicilio, "Editar Domicilio");
	}

	private void cargarPopupDomicilio(DomiciliosCuentaDto domicilio, String title) {
		isDomicilioPpalEntrega = EstadoTipoDomicilioDto.PRINCIPAL.getId().equals(domicilio.getIdEntrega());
		isDomicilioPpalFacturacion = EstadoTipoDomicilioDto.PRINCIPAL.getId().equals(
				domicilio.getIdFacturacion());
		noEditable = domicilio.getVantiveId() != null;
		domicilioAEditar = domicilio;
		domiciliosUIData.clean();
		domiciliosUIData.setDomicilio(domicilio);
		showAndCenter();
		if (noEditable) {
			domiciliosUIData.disableFields();
			linkAceptar.setVisible(false);
		} else {
			domiciliosUIData.enableFields();
			linkAceptar.setVisible(true);
		}
		setDialogTitle(title);
	}

	private void init() {
		domiciliosUIData = new DomiciliosUIData();
		footerBar = new FormButtonsBar();
		linkCerrar = new SimpleLink("Cerrar");
		linkAceptar = new SimpleLink("Aceptar");
		gridUp = new Grid(3, 5);
		gridMed = new Grid(1, 11);
		gridDown = new Grid(5, 5);
		gridObs = new Grid(2, 3);
		gridUser = new Grid(1, 5);
		setWidth("635px");
		validadoPorPanel = new HorizontalPanel();
		validadoPorPanel.add(domiciliosUIData.getValidado());
		validadoPorPanel.add(labelValidado1);
		validadoPorPanel.addStyleName("ml15");

		// Campos Obligatorios:
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
		gridUp.setWidget(1, 2, domiciliosUIData.getCpa());
		gridUp.setWidget(2, 1, calleLabel);
		gridUp.setWidget(2, 2, domiciliosUIData.getCalle());
		gridUp.setWidget(2, 3, numCalleLabel);
		gridUp.setWidget(2, 4, domiciliosUIData.getNumero());
		//
		gridMed.getColumnFormatter().setWidth(1, "85px");
		gridMed.getColumnFormatter().setWidth(3, "65px");
		gridMed.getColumnFormatter().setWidth(5, "65px");
		gridMed.getColumnFormatter().setWidth(7, "65px");
		gridMed.getColumnFormatter().setWidth(9, "65px");
		gridMed.addStyleName("layout");
		gridMed.setText(0, 1, Sfa.constant().piso());
		gridMed.setWidget(0, 2, domiciliosUIData.getPiso());
		gridMed.setText(0, 3, Sfa.constant().dpto());
		gridMed.setWidget(0, 4, domiciliosUIData.getDepartamento());
		gridMed.setText(0, 5, Sfa.constant().uf());
		gridMed.setWidget(0, 6, domiciliosUIData.getUnidadFuncional());
		gridMed.setText(0, 7, Sfa.constant().torre());
		gridMed.setWidget(0, 8, domiciliosUIData.getTorre());
		gridMed.setText(0, 9, Sfa.constant().manzana());
		gridMed.setWidget(0, 10, domiciliosUIData.getManzana());
		//
		gridDown.getColumnFormatter().setWidth(1, "85px");
		gridDown.getColumnFormatter().setWidth(3, "80px");
		gridDown.addStyleName("layout");
		gridDown.setText(0, 1, Sfa.constant().entre_calle());
		gridDown.setWidget(0, 2, domiciliosUIData.getEntreCalle());
		gridDown.setText(0, 3, Sfa.constant().y_calle());
		gridDown.setWidget(0, 4, domiciliosUIData.getYcalle());
		gridDown.setWidget(1, 1, localidadLabel);
		gridDown.setWidget(1, 2, domiciliosUIData.getLocalidad());
		gridDown.setWidget(1, 3, cpLabel);
		gridDown.setWidget(1, 4, domiciliosUIData.getCodigoPostal());
		cargaComboProvinciasDto();
		gridDown.setWidget(2, 1, provinciaLabel);
		gridDown.setWidget(2, 2, domiciliosUIData.getProvincia());
		//
		gridDown.setText(2, 3, Sfa.constant().partido());
		gridDown.setWidget(2, 4, domiciliosUIData.getPartido());
		gridDown.setWidget(3, 1, labelEntrega);
		gridDown.setWidget(3, 2, domiciliosUIData.getEntrega());
		gridDown.setWidget(3, 3, labelFacturacion);
		gridDown.setWidget(3, 4, domiciliosUIData.getFacturacion());
		gridObs.addStyleName("layout");
		gridObs.setText(0, 1, Sfa.constant().obs_domicilio());
		gridObs.setWidget(1, 1, domiciliosUIData.getObservaciones());
		gridUser.addStyleName("layout");
		gridUser.setWidget(0, 1, labelUsuario);
		gridUser.setWidget(0, 2, domiciliosUIData.getNombreUsuarioUltimaModificacion());
		gridUser.setWidget(0, 3, labelFecha);
		gridUser.setWidget(0, 4, domiciliosUIData.getFechaUltimaModificacion());

		add(gridUp);
		add(gridMed);
		add(gridDown);
		add(validadoPorPanel);
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
				if (comandoAceptar != null) {
					getComandoAceptarDomicilioServiceCall().execute();
				}
			}
		});
		linkCerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				hide();
			}
		});
		setParentContacto(false);
		this.showAndCenter();
	}

	/**
	 * @author eSalvador
	 */
	private void cargaComboProvinciasDto() {
		CuentaRpcService.Util.getInstance().getProvinciasInitializer(
				new DefaultWaitCallback<List<ProvinciaDto>>() {
					public void success(List<ProvinciaDto> result) {
						domiciliosUIData.getProvincia().addAllItems(result);
					}
				});
	}

	/**
	 * @author eSalvador Metodo que setea la accion a tomar por el botón Aceptar del popup DomicilioUI.
	 **/
	public void setComandoAceptar(Command comandoAceptar) {
		this.comandoAceptar = comandoAceptar;
	}

	public void showAndCenter() {
		enableFieldsParaContactos(!parentContacto);
		super.showAndCenter();
	}

	public boolean isEditable() {
		return noEditable;
	}

	public void setEditable(boolean editable) {
		this.noEditable = editable;
	}

	public DomiciliosCuentaDto getDomicilioAEditar() {
		return domicilioAEditar;
	}

	public void setDomicilioAEditar(DomiciliosCuentaDto domicilioAEditar) {
		this.domicilioAEditar = domicilioAEditar;
	}

	/**
	 * @author eSalvador
	 **/
	public Command getComandoAceptarDomicilioServiceCall() {
		Command comandoAceptar = new Command() {
			public void execute() {
				domicilioAEditar = domiciliosUIData.getDomicilio();
				if (camposValidos()) {
					CuentaRpcService.Util.getInstance().normalizarDomicilio(domicilioAEditar,
							new DefaultWaitCallback<NormalizarDomicilioResultDto>() {
								public void success(NormalizarDomicilioResultDto result) {

									List<DomiciliosCuentaDto> listaDomicilios = new ArrayList();
									listaDomicilios.add(result.getDireccion());

									// tipo: exito|no_parseado|no_encontrado|dudas
									if (result.getTipo().equals("exito")) {
										NormalizarDomicilioUI.getInstance().setNormalizado(true);
										abrirPopupNormalizacion(listaDomicilios,
												getComandoAgregarDomicilioSinNormalizar(),
												getComandoAceptarNormalizado());
									} else if (result.getTipo().equals("no_encontrado")) {
										setMotivosNoNormalizacion(result);
										abrirPopupNormalizacion(listaDomicilios,
												getComandoAgregarDomicilioSinNormalizar(),
												getComandoAceptarNormalizado());
									} else if (result.getTipo().equals("dudas")) {
										NormalizarDomicilioUI.getInstance().setNormalizado(true);
										abrirPopupNormalizacion(result.getDudas(),
												getComandoAgregarDomicilioSinNormalizar(),
												getComandoAceptarNormalizado());
									} else if (result.getTipo().equals("no_parseado")) {
										setMotivosNoNormalizacion(result);
										abrirPopupNormalizacion(listaDomicilios,
												getComandoAgregarDomicilioSinNormalizar(),
												getComandoAceptarNormalizado());
									}
								}

								@Override
								public void failure(Throwable exception) {
								}
							});
				}
			}
		};
		return comandoAceptar;
	}

	private DomiciliosCuentaDto mapeoDomicilioNormalizado(DomiciliosCuentaDto domicilioNormalizado) {
		// Mapeos a Mano para no perder Datos: REVISAR:
		domicilioNormalizado.setObservaciones(domiciliosUIData.getObservaciones().getText());
		domicilioNormalizado.setValidado(domiciliosUIData.getValidado().getValue());
		domicilioNormalizado.setId(domicilioAEditar.getId());
		domicilioNormalizado.setIdEntrega(domicilioAEditar.getIdEntrega());
		domicilioNormalizado.setIdFacturacion(domicilioAEditar.getIdFacturacion());
		domicilioNormalizado.setNombre_usuario_ultima_modificacion(domicilioAEditar
				.getNombre_usuario_ultima_modificacion());
		domicilioNormalizado.setFecha_ultima_modificacion(domicilioAEditar.getFecha_ultima_modificacion());
		domicilioNormalizado.setActivo(domicilioAEditar.getActivo());
		return domicilioNormalizado;
	}

	/** Comando para el boton 'Normalizar' del NormalizadorUI */
	private Command getComandoAceptarNormalizado() {
		Command comandoAceptarNormalizado = new Command() {
			public void execute() {
				if (NormalizarDomicilioUI.getInstance().getRowSelected() < 0) {
					openPopupSelectDomicilioDialog();
				} else {
					DomiciliosCuentaDto domicilioNormalizado = NormalizarDomicilioUI.getInstance()
							.getDomicilioEnGrillaSelected();
					domicilioAEditar = mapeoDomicilioNormalizado(domicilioNormalizado);
					DomicilioUI.getInstance().hide();
					NormalizarDomicilioUI.getInstance().hide();
					comandoAceptar.execute();
				}
			}
		};
		return comandoAceptarNormalizado;
	}

	/** Comando para el boton 'No Normalizar' del NormalizadorUI */
	private Command getComandoAgregarDomicilioSinNormalizar() {
		Command comandoAceptarSinNormalizar = new Command() {
			public void execute() {
				DomicilioUI.getInstance().hide();
				NormalizarDomicilioUI.getInstance().hide();
				comandoAceptar.execute();
			}
		};
		return comandoAceptarSinNormalizar;
	}

	/**
	 * @author eSalvador
	 **/
	private void abrirPopupNormalizacion(List<DomiciliosCuentaDto> domiciliosAGrilla,
			Command comandoNoNormalizar, Command comandoAceptar) {
		NormalizarDomicilioUI.getInstance().setDomicilio(domicilioAEditar);
		NormalizarDomicilioUI.getInstance().agregaDomiciliosAGrilla(domiciliosAGrilla);
		NormalizarDomicilioUI.getInstance().setComandoAceptar(comandoAceptar);
		NormalizarDomicilioUI.getInstance().setComandoNoNormalizar(comandoNoNormalizar);
		NormalizarDomicilioUI.getInstance().showAndCenter();
		MessageDialog.getInstance().showAceptar("Domicilio Modificado", "cpa modificado al normalizar por diferencia de altura",
				MessageDialog.getCloseCommand());
	}

	private Command getComandoBorrar(final Command afterDelete) {
		Command comandoBorrar = new Command() {
			public void execute() {
				List<DomiciliosCuentaDto> domicilios = persona.getDomicilios();
				for (int j = 0; j < domicilios.size(); j++) {
					if (domicilios.get(j) == domicilioAEditar) {
						persona.removeDomicilio(domicilioAEditar);
					}
				}
				if (afterDelete != null) {
					afterDelete.execute();
				}
				MessageDialog.getInstance().hide();
			}
		};
		return comandoBorrar;
	}

	private boolean camposValidos() {
		boolean valido = true;
		List<String> errores = domiciliosUIData.validarCamposObligatorios();
		if (errores.size() != 0) {
			valido = false;
			ErrorDialog.getInstance().setDialogTitle("Error");
			ErrorDialog.getInstance().show(errores);
		}
		return valido;
	}

	private void setMotivosNoNormalizacion(NormalizarDomicilioResultDto result) {
		NormalizarDomicilioUI.getInstance().setNormalizado(false);
		NormalizarDomicilioUI.getInstance().setMotivos(result.getMotivos());
		NormalizarDomicilioUI.getInstance().setDomiciliosEnGrilla(result.getDudas());
	}

	/**
	 * Advierte que no se seleccionó una fila.
	 **/
	public void openPopupSelectDomicilioDialog() {
		MessageDialog.getInstance().setDialogTitle("Error");
		MessageDialog.getInstance().showAceptar(
				"Para realizar la acción debe seleccionar una fila de la tabla.",
				MessageDialog.getCloseCommand());
	}

	/**
	 * Advierte la imposibilidad de modificación del domicilio. Ejecuta el comando al hacer click en aceptar
	 **/
	public void openPopupAdviseDialog(Command comandoGenerico) {
		MessageDialog.getInstance().setDialogTitle("Advertencia");
		MessageDialog.getInstance().showAceptar(
				"No puede modificar o borrar el domicilio. "
						+ "Ya se cerró una solicitud de servicio para la cuenta desde su creación.",
				comandoGenerico);
	}

	/**
	 * Pregunta si desea borrar el domicilio. Si se elije el 'SI', ejecuta el comando.
	 **/
	public void openPopupDeleteDialog(PersonaDto persona, DomiciliosCuentaDto domicilio, Command afterDelete) {
		MessageDialog.getInstance().setDialogTitle("Eliminar Domicilio");
		if (domicilio.getVantiveId() == null) {
			domicilioAEditar = domicilio;
			this.persona = persona;
			MessageDialog.getInstance().showSiNo(
					"¿Esta seguro que desea eliminar el domicilio seleccionado?",
					getComandoBorrar(afterDelete), MessageDialog.getCloseCommand());
		} else {
			openPopupAdviseDialog(MessageDialog.getCloseCommand());
		}
	}

	/**
	 * Devuelve un comando que habre el DomicilioUI cargando el domicilioAEditar. Llamar antes a
	 * setDomicilioAEditar()
	 */
	public Command getOpenDomicilioUICommand() {
		Command openUICommand = new Command() {
			public void execute() {
				DomicilioUI.getInstance().setComandoAceptar(MessageDialog.getCloseCommand());
				DomicilioUI.getInstance().cargarPopupEditarDomicilio(domicilioAEditar);
				MessageDialog.getInstance().hide();
			}
		};
		return openUICommand;
	}

	/** Comprueba si la cuenta tiene algun domicilio principal (Tomando el estado actual del editado) */
	public boolean getTieneDomiciliosPrincipales() {
		Long idDomicilioFacturacionSelected = Long.parseLong(domiciliosUIData.getFacturacion()
				.getSelectedItemId());
		if (!isDomicilioPpalFacturacion
				&& EstadoTipoDomicilioDto.PRINCIPAL.getId().equals(idDomicilioFacturacionSelected)
				&& domiciliosUIData.isTienePrincipalFacturacion()) {
			return true;
		}
		Long idDomicilioEntregaSelected = Long.parseLong(domiciliosUIData.getEntrega().getSelectedItemId());
		if (!isDomicilioPpalEntrega
				&& EstadoTipoDomicilioDto.PRINCIPAL.getId().equals(idDomicilioEntregaSelected)
				&& domiciliosUIData.isTienePrincipalEntrega()) {
			return true;
		}
		return false;
	}

	public boolean isParentContacto() {
		return parentContacto;
	}

	public void setParentContacto(boolean parentContacto) {
		this.parentContacto = parentContacto;
	}

	public void enableFieldsParaContactos(boolean enable) {
		labelEntrega.setVisible(enable);
		labelUsuario.setVisible(enable);
		labelFecha.setVisible(enable);
		labelFacturacion.setVisible(enable);
		labelValidado1.setVisible(enable);
		labelValidado2.setVisible(enable);
		domiciliosUIData.getEntrega().setVisible(enable);
		domiciliosUIData.getFacturacion().setVisible(enable);
		domiciliosUIData.getValidado().setVisible(enable);
	}

	public void cargarListBoxEntregaFacturacion(List<DomiciliosCuentaDto> domicilios, DomiciliosCuentaDto domicilioAEditar) {
		domiciliosUIData.cargarListBox(domicilios, domicilioAEditar);
	}
}
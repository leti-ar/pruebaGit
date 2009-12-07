package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.contacto.ContactosUI;
import ar.com.nextel.sfa.client.dto.ContactoCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.enums.TipoCuentaEnum;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;

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

public class CuentaContactoForm extends Composite {

	private FlowPanel mainPanel;
	private FlexTable datosTabla = new FlexTable();
	private FlexTable datosTablaRO = new FlexTable();
	private List<ContactoCuentaDto> listaContactos = new ArrayList<ContactoCuentaDto>();
	private ContactosUI contactosUI;
	private boolean formDirty = false;	
	private Button crearButton;
	private SimplePanel crearContactoWrapper;
	private static CuentaContactoForm instance = null;
	private ContactoCuentaDto contactoAEditar;

	public static CuentaContactoForm getInstance() {
		if (instance == null) {
			instance = new CuentaContactoForm();
		}
		return instance;
	}

	private CuentaContactoForm() {
		contactosUI = new ContactosUI();
		mainPanel = new FlowPanel();
		mainPanel.setWidth("100%");
		initWidget(mainPanel);
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		crearButton = new Button("Crear nuevo");
		crearButton.addStyleName("crearDomicilioButton");
		crearContactoWrapper = new SimplePanel();
		crearContactoWrapper.add(crearButton);
		crearContactoWrapper.addStyleName("h20");
		mainPanel.add(crearContactoWrapper);
		
		datosTabla.addTableListener(new Listener());
		
		mainPanel.add(datosTabla);
		contactosUI.setAceptarCommand(new Command() {
			public void execute() {
				int index = listaContactos.indexOf(contactoAEditar);
				if (index >= 0) {
					listaContactos.remove(index);
					listaContactos.add(index, contactosUI.getContacto());
				} else {
					listaContactos.add(contactosUI.getContacto());
				}
				cargarTabla();
			}
		});

		crearButton.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				contactoAEditar = new ContactoCuentaDto();
				contactosUI.cargarPopupEditarContacto(contactoAEditar);
			}
		});
	}

	private void initTable(FlexTable table) {
		limpiarPrimeraFilaTabla();
		String[] widths = { "24px", "24px", "150px", "150px", "200px", "45%" };
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
		table.setHTML(0, 2, Sfa.constant().nombre());
		table.setHTML(0, 3, Sfa.constant().apellido());
		table.setHTML(0, 4, Sfa.constant().telefono());
		table.setHTML(0, 5, Sfa.constant().whiteSpace());
	}
	
	private void initTableRO(FlexTable table) {
		limpiarPrimeraFilaTabla();
		String[] widths = {"150px", "150px", "200px", "55%" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().nombre());
		table.setHTML(0, 1, Sfa.constant().apellido());
		table.setHTML(0, 2, Sfa.constant().telefono());
		table.setHTML(0, 3, Sfa.constant().whiteSpace());
	}
	
	private void limpiarPrimeraFilaTabla() {
		if (datosTabla.isCellPresent(0, 0)) {
			datosTabla.removeRow(0);
		}
	}
	
	public void cargarTabla() {
		crearContactoWrapper.setVisible(!EditarCuentaUI.edicionReadOnly);
		if (EditarCuentaUI.edicionReadOnly) {
			initTableRO(datosTabla);
			datosTabla.removeTableListener(new Listener());
		} else {
			initTable(datosTabla);
			datosTabla.addTableListener(new Listener());
		}
		
		int row = 1;
		int col = 0;
		while (datosTabla.getRowCount() > 1) {
			datosTabla.removeRow(1);
		}
		if (listaContactos != null) {
			for (Iterator iter = listaContactos.iterator(); iter.hasNext();) {
				ContactoCuentaDto contacto = (ContactoCuentaDto) iter.next();
				if (contacto.getPersona()!=null) {
					col = 0;
					if(!EditarCuentaUI.edicionReadOnly) { 
						datosTabla.setWidget(row, col++, IconFactory.lapiz());
						datosTabla.setWidget(row, col++, IconFactory.cancel());
					}
					datosTabla.setHTML(row, col++, contacto.getPersona().getNombre());
					datosTabla.setHTML(row, col++, contacto.getPersona().getApellido());
					datosTabla.setHTML(row, col++, obtenerTelefonoPrincipal(contacto));
					datosTabla.setHTML(row, col++, Sfa.constant().whiteSpace());
					row++;
				}
			}
		}
	}

	private class Listener implements TableListener {
		public void onCellClicked(SourcesTableEvents arg0, int fila, int columna) {
			// boton editar
			if (!EditarCuentaUI.edicionReadOnly) {
				if ((fila >= 1) && (columna == 0)) {
					contactoAEditar = listaContactos.get(fila -1);
					contactosUI.cargarPopupEditarContacto(contactoAEditar);
				}
				// boton eliminar
				if ((fila >= 1) && (columna == 1)) {
					ModalMessageDialog.getInstance().showAceptarCancelar("Eliminar Contacto",
							"¿Esta seguro que desea eliminar el contacto seleccionado?",
							getComandoAceptar(fila - 1), ModalMessageDialog.getInstance().getCloseCommand());
				}
			}
		}
	}

	private Command getComandoAceptar(final int numeroContacto) {
		Command comandoAceptar = new Command() {
			public void execute() {
				eliminarContacto(numeroContacto);
				ModalMessageDialog.getInstance().hide();
				cargarTabla();
				formDirty=true;
			}
		};
		return comandoAceptar;
	}

	public String obtenerTelefonoPrincipal(ContactoCuentaDto contactoDto) {
		for (TelefonoDto telefonoDto:contactoDto.getPersona().getTelefonos()) {
			if (telefonoDto.getTipoTelefono().getDescripcion().equals(TipoTelefonoEnum.PRINCIPAL.getDesc())) {
				return comprobarArea(telefonoDto.getArea())	+ 
				       comprobarNumero(telefonoDto.getNumeroLocal()) + 
				       comprobarInterno(telefonoDto.getInterno());
			}
		}
		return null;
	}

	public void cargarTablaContactos(CuentaDto cuentaDto) {
		String categoriaCuenta =  cuentaDto.getCategoriaCuenta().getDescripcion();
		List<ContactoCuentaDto> contactos = null;
		if (categoriaCuenta.equals(TipoCuentaEnum.CTA.getTipo())) {
			contactos = ((GranCuentaDto) cuentaDto).getContactos();
		} else if (categoriaCuenta.equals(TipoCuentaEnum.SUS.getTipo())) {
			if (((SuscriptorDto) cuentaDto).getDivision() != null)
				contactos = ((SuscriptorDto) cuentaDto).getDivision().getGranCuenta().getContactos();
			else
				contactos = ((SuscriptorDto) cuentaDto).getGranCuenta().getContactos();
		} else if (categoriaCuenta.equals(TipoCuentaEnum.DIV.getTipo())) {
			contactos = ((DivisionDto) cuentaDto).getGranCuenta().getContactos();
		}
		if (contactos != null) {
			setListaContactos(contactos);
			cargarTabla();
		}
	}
	
	private String comprobarArea(String area) {
		return (area!=null && !"".equals(area)) ? "(" + area + ")" + " ":  "";
	}

	private String comprobarNumero(String numero) {
		return (numero!=null && !"".equals(numero)) ?  numero + " " :  "";
	}

	private String comprobarInterno(String interno) {
		return (interno!=null && !"".equals(interno)) ? "(" + interno + ")" + " " : "";
	}

	public void eliminarContacto(int numeroContacto) {
		listaContactos.remove(numeroContacto);
	}

	public List<ContactoCuentaDto> getListaContactos() {
		return listaContactos;
	}

	public void setListaContactos(List<ContactoCuentaDto> listaContactos) {
		this.listaContactos = listaContactos;
	}
	public boolean isFormDirty() {
		return formDirty;
	}
	public void setFormDirty(boolean formDirty) {
		this.formDirty = formDirty;
	}
	public boolean formContactosDirty() {
		return formDirty;
	}
	public Button getCrearButton() {
		return crearButton;
	}
	
}

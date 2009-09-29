package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.contacto.ContactosUI;
import ar.com.nextel.sfa.client.dto.ContactoCuentaDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

public class CuentaContactoForm extends Composite {

	private FlexTable mainPanel;
	private FlexTable datosTabla = new FlexTable();
	private List<ContactoCuentaDto> listaContactos = new ArrayList<ContactoCuentaDto>();
	private ContactosUI contactosUI;
	private boolean formDirty = false;	
	private Button crearButton;
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
		mainPanel = new FlexTable();
		mainPanel.setWidth("100%");
		initWidget(mainPanel);
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		mainPanel.setCellPadding(20);
		crearButton = new Button("Crear nuevo");
		crearButton.addStyleName("crearDomicilioButton");
		mainPanel.setWidget(0, 0, crearButton);
		datosTabla.addTableListener(new Listener());
		initTable(datosTabla);
		mainPanel.getColumnFormatter().addStyleName(0, "alignRight");
		mainPanel.getRowFormatter().addStyleName(0, "alignRight");
		mainPanel.setWidget(1, 0, datosTabla);
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

		String[] widths = { "24px", "24px", "150px", "150px", "200px", "45%", };
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

	public void cargarTabla() {
		int row = 1;
		while (datosTabla.getRowCount() > 1) {
			datosTabla.removeRow(1);
		}
		if (listaContactos != null) {
			for (Iterator iter = listaContactos.iterator(); iter.hasNext();) {
				ContactoCuentaDto contacto = (ContactoCuentaDto) iter.next();
				if (contacto.getPersona()!=null) {
					datosTabla.setWidget(row, 0, IconFactory.lapiz());
					datosTabla.setWidget(row, 1, IconFactory.cancel());
					datosTabla.setHTML(row, 2, contacto.getPersona().getNombre());
					datosTabla.setHTML(row, 3, contacto.getPersona().getApellido());
					datosTabla.setHTML(row, 4, obtenerTelefonoPrincipal(contacto));
					datosTabla.setHTML(row, 5, Sfa.constant().whiteSpace());
					row++;
				}
			}
		}
	}

	private class Listener implements TableListener {
		public void onCellClicked(SourcesTableEvents arg0, int fila, int columna) {
			// boton editar
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

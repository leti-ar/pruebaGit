package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.ContactoDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.MessageDialog;

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
	FlexTable datosTabla = new FlexTable();
	private List<ContactoDto> listaContactos = new ArrayList();
		
	private static CuentaContactoForm instance = null;

	public static CuentaContactoForm getInstance() {
		if(instance == null){
			instance = new CuentaContactoForm();
		}
		return instance;
	}

	private CuentaContactoForm() {

		mainPanel = new FlexTable();
		mainPanel.setWidth("100%");
		initWidget(mainPanel);
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		mainPanel.setCellPadding(20);
		Button crear = new Button("Crear nuevo");
		crear.addStyleName("crearDomicilioButton");
		mainPanel.setWidget(0, 0, crear);
		datosTabla.addTableListener(new Listener());
		initTable(datosTabla);
		mainPanel.getColumnFormatter().addStyleName(0, "alignRight");
		mainPanel.getRowFormatter().addStyleName(0, "alignRight");
		mainPanel.setWidget(1, 0, datosTabla);

		crear.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				/**TODO: Antes aca deberias setear la accion a tomar cuando se apreta eel boton Aceptar, dependiendo si es Copiar o Nuevo contacto.
				 * (crear un metodo que setee el comando en el ContactosUI, y setearlo!)*/
				ContactosUI.getInstance().cargaPopupNuevoContacto();
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
		if (listaContactos != null) {
			for (Iterator iter = listaContactos.iterator(); iter.hasNext();) {
				ContactoDto contacto = (ContactoDto) iter.next();
				datosTabla.setWidget(row, 0, IconFactory.lapiz());
				datosTabla.setWidget(row, 1, IconFactory.cancel());
				datosTabla.setHTML(row, 2, contacto.getPersonaDto().getNombre());
				datosTabla.setHTML(row, 3, contacto.getPersonaDto().getApellido());
				datosTabla.setHTML(row, 4, obtenerTelefonoPrincipal(contacto));
				datosTabla.setHTML(row, 5, Sfa.constant().whiteSpace());
				row++;
			} 
		} 
		while(datosTabla.getRowCount() > row){
			datosTabla.removeRow(row++);
		}
	}		
	
		
	public void setearContactos(ContactoDto contactoDto, int contactoABorrar) {
		if (contactoABorrar == -1) {
			listaContactos.add(contactoDto);
		} else 
			listaContactos.add(contactoABorrar, contactoDto);
		cargarTabla();
	}

	
	private class Listener implements TableListener {
		public void onCellClicked(SourcesTableEvents arg0, int fila, int columna) {
			//boton editar
			if ((fila>=1) && (columna==0)) {
				ContactosUI.getInstance().cargarPopupEditarContacto(listaContactos.get(fila-1), fila-1);
			}
			//boton eliminar
			if ((fila>=1) && (columna==1)) {
				MessageDialog.getInstance().setDialogTitle("Eliminar Contacto");
				MessageDialog.getInstance().setSize("300px", "100px");
				MessageDialog.getInstance().showAceptarCancelar("¿Esta seguro que desea eliminar el contacto seleccionado?",getComandoAceptar(fila-1),MessageDialog.getInstance().getCloseCommand());
			}
		}
	}
	
	private Command getComandoAceptar(final int numeroContacto){
		Command comandoAceptar = new Command() {
			public void execute() {
				eliminarContacto(numeroContacto);
				MessageDialog.getInstance().hide();
				cargarTabla();
			}
		};
	return comandoAceptar;
	}	
	
	public String obtenerTelefonoPrincipal(ContactoDto contactoDto) {
		List<TelefonoDto> listaTelefonos = new ArrayList();
		listaTelefonos = contactoDto.getPersonaDto().getTelefonos();
		
		if (listaTelefonos != null) {
			for (Iterator iter = listaTelefonos.iterator(); iter.hasNext();) {
				TelefonoDto telefonoDto = (TelefonoDto) iter.next();
				if (telefonoDto.getPrincipal()) {
					return ("(" + telefonoDto.getArea() + ") " + telefonoDto.getNumeroLocal() + "(" + telefonoDto.getInterno() + ")");
				}
			}
		}
		return null;
	}
	
	public void eliminarContacto(int numeroContacto) {
		listaContactos.remove(numeroContacto);
	}
}

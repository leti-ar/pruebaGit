package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioSearchDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Editor del SolicitudServicioSearchDto. Contiene todos los campos (Widgets) necesarios para su edición.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSCerradasFilterUIData extends UIData {

	private Label tituloLabel = new Label("Búsqueda de SS Cerradas");
	private TextBox nroCliente;
	private TextBox nroSS;
	private ListBox estadoCombo;
	private ListBox pataconesCombo;
	private ListBox firmasCombo;
	private ListBox resultadosCombo;
	private SimpleDatePicker desde;
	private SimpleDatePicker hasta;
	
	private Button buscarButton;
	private Button limpiarButton;

	public BuscarSSCerradasFilterUIData() {

		fields = new ArrayList<Widget>();
		fields.add(nroCliente = new TextBox());
		fields.add(nroSS = new TextBox());
		fields.add(estadoCombo = new ListBox("Todos"));
		fields.add(pataconesCombo = new ListBox());
		fields.add(firmasCombo = new ListBox());
		fields.add(resultadosCombo = new ListBox());
		fields.add(desde = new SimpleDatePicker(false));
		fields.add(hasta = new SimpleDatePicker(false));

		buscarButton = new Button(Sfa.constant().buscar());
		limpiarButton = new Button(Sfa.constant().limpiar());
		buscarButton.addStyleName("btn-bkg");
		limpiarButton.addStyleName("btn-bkg");

		limpiarButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				clean();
			}
		});

		/** @author juliovesco: Carga inicial de combos */
	    CuentaRpcService.Util.getInstance().getBuscarSSCerradasInitializer(new DefaultWaitCallback<BuscarSSCerradasInitializer>() {
		  			public void success(BuscarSSCerradasInitializer result) {
						setCombos(result);			
					}
				});
	}

	/**
	 * @author juliovesco
	 **/
	private void setCombos(BuscarSSCerradasInitializer datos){
		if (datos.getCantidadesResultados() != null ){
			for (String cantResultado : datos.getCantidadesResultados()) {
				resultadosCombo.addItem(new ListBoxItemImpl(cantResultado,cantResultado));	
			}
		}
		if (datos.getOpcionesEstado() != null ){
				estadoCombo.addAllItems(datos.getOpcionesEstado());	
		}
		if (datos.getOpcionesFirmas() != null ){
			for (String opcionesFirmas : datos.getOpcionesFirmas()) {
				firmasCombo.addItem(new ListBoxItemImpl(opcionesFirmas,opcionesFirmas));	
			}
		}
		if (datos.getOpcionesPatacones() != null ){
			for (String opcionesPatacones : datos.getOpcionesPatacones()) {
				pataconesCombo.addItem(new ListBoxItemImpl(opcionesPatacones,opcionesPatacones));	
			}
		}
	}
	
	public Label getTituloLabel() {
		return tituloLabel;
	}

	public TextBox getRazonSocialTextBox() {
		nroSS.setWidth("90%");
		return nroSS;
	}

	public ListBox getResultadosCombo() {
		return resultadosCombo;
	}

	public Button getBuscarButton() {
		return buscarButton;
	}

	public Button getLimpiarButton() {
		return limpiarButton;
	}
	
	public TextBox getNroCliente() {
		return nroCliente;
	}

	public TextBox getNroSS() {
		return nroSS;
	}

	public ListBox getPataconesCombo() {
		return pataconesCombo;
	}

	public ListBox getFirmasCombo() {
		return firmasCombo;
	}

	public ListBox getEstadoCombo() {
		return estadoCombo;
	}
	
	public Widget getDesde() {
		Grid datePickerFull = new Grid(1, 2);
		datePickerFull.setWidget(0, 0, desde.getTextBox());
		datePickerFull.setWidget(0, 1, desde);
		return datePickerFull;
	}

	public Widget getHasta() {
		Grid datePickerFull = new Grid(1, 2);
		datePickerFull.setWidget(0, 0, hasta.getTextBox());
		datePickerFull.setWidget(0, 1, hasta);
		return datePickerFull;
	}

	public SolicitudServicioSearchDto getSSCerradaSearch() {
		SolicitudServicioSearchDto solicitudServicioSearchDto = new SolicitudServicioSearchDto();
		solicitudServicioSearchDto.setNumeroCuenta(nroCliente.getText());
		solicitudServicioSearchDto.setNumeroSS(nroSS.getText());
		solicitudServicioSearchDto.setFechaDesde(desde.getSelectedDateAsString());
		solicitudServicioSearchDto.setFechaHasta(hasta.getSelectedDateAsString());
		solicitudServicioSearchDto.setEstado(estadoCombo.getSelectedItemText());
		solicitudServicioSearchDto.setPataconex(pataconesCombo.getSelectedItemText());
		solicitudServicioSearchDto.setFirmas(firmasCombo.getSelectedItemText());
		solicitudServicioSearchDto.setCantidadResultados(Integer.parseInt(resultadosCombo.getSelectedItem().getItemValue()));
		return solicitudServicioSearchDto;
	}
	
}

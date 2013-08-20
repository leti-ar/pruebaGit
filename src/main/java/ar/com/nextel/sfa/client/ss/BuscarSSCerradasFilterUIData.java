package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.nextel.sfa.client.util.FormUtils;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.i18n.client.DateTimeFormat;
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
	
	private DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy");
	private List<String> errorList = new ArrayList();


	public BuscarSSCerradasFilterUIData() {

		fields = new ArrayList<Widget>();
		fields.add(nroCliente = new TextBox());
		fields.add(nroSS = new TextBox());
		fields.add(estadoCombo = new ListBox("Todos"));
		fields.add(pataconesCombo = new ListBox("Todos"));
		fields.add(firmasCombo = new ListBox("Todos"));
		fields.add(resultadosCombo = new ListBox());
		fields.add(desde = new SimpleDatePicker(false, true));
		fields.add(hasta = new SimpleDatePicker(false, true));

		buscarButton = new Button(Sfa.constant().buscar());
		limpiarButton = new Button(Sfa.constant().limpiar());
		buscarButton.addStyleName("btn-bkg");
		limpiarButton.addStyleName("btn-bkg");

		limpiarButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				clean();
				desde.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
				desde.getTextBox().setText(dateFormatter.format(DateUtil.getStartDayOfMonth(DateUtil.today())));
				hasta.setSelectedDate(DateUtil.today());
				hasta.getTextBox().setText(dateFormatter.format(DateUtil.today()));
			}
		});

		/** @author juliovesco: Carga inicial de combos */
	    SolicitudRpcService.Util.getInstance().getBuscarSSCerradasInitializer(new DefaultWaitCallback<BuscarSSCerradasInitializer>() {
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
		desde.setWeekendSelectable(true);
		desde.setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
		desde.getTextBox().setText(dateFormatter.format(DateUtil.getStartDayOfMonth(DateUtil.today())));
		datePickerFull.setWidget(0, 0, desde.getTextBox());
		datePickerFull.setWidget(0, 1, desde);
		return datePickerFull;
	}

	public Widget getHasta() {
		Grid datePickerFull = new Grid(1, 2);
		hasta.setWeekendSelectable(true);
		hasta.setSelectedDate(DateUtil.today());
		hasta.getTextBox().setText(dateFormatter.format(DateUtil.today()));
		datePickerFull.setWidget(0, 0, hasta.getTextBox());
		datePickerFull.setWidget(0, 1, hasta);
		return datePickerFull;
	}

	public SolicitudServicioCerradaDto getSSCerradaSearch() {
		SolicitudServicioCerradaDto solicitudServicioCerradaDto = new SolicitudServicioCerradaDto();
		solicitudServicioCerradaDto.setNumeroCuenta(nroCliente.getText());
		solicitudServicioCerradaDto.setNumeroSS(nroSS.getText());
		solicitudServicioCerradaDto.setFechaCierreDesde(desde.getSelectedDate());
		solicitudServicioCerradaDto.setFechaCierreHasta(hasta.getSelectedDate());
		solicitudServicioCerradaDto.setIdEstadoAprobacionSS(obtenerLong(estadoCombo.getSelectedItemText()));
		solicitudServicioCerradaDto.setPataconex(obtenerBoolean(pataconesCombo.getSelectedItemText()));
		solicitudServicioCerradaDto.setFirmas(obtenerBoolean(firmasCombo.getSelectedItemText()));
		solicitudServicioCerradaDto.setCantidadResultados(Long.valueOf(resultadosCombo.getSelectedItem().getItemValue()));
		return solicitudServicioCerradaDto;
	}
	
	private Boolean obtenerBoolean(String string) {
		if ("Si".equals(string)) {
			return Boolean.TRUE;
		}else if ("No".equals(string)) {
			return Boolean.FALSE;
		}
		return null;
	}

	private Long obtenerLong(String string) {
		if ("Pass".equals(string)) {
			return Long.valueOf("2");
		}else if ("Fail".equals(string)) {
			return Long.valueOf("3");
		}
		return null;
	}
	
	public void cleanAndEnableFields() {
		FormUtils.cleanAndEnableFields(fields);	
	}
	public SimpleDatePicker getDesdeDatePicker() {
		return this.desde;
	}
	public SimpleDatePicker getHastaDatePicker() {
		return this.hasta;
	}
	
	public List<String> validarCriterioBusquedaSSCerradas() {
		errorList.clear();
		
		Date date1 = desde.getSelectedDate();
		Date date2 = hasta.getSelectedDate();
		int dif = getDiasEntreDosFechas(date1, date2);
		
		if (dif > 30) {
			errorList
					.add("Fecha invalida, solo puede buscar SS cerradas en un rango no mayor a los 30 días");
		}
		return errorList;
	}
	
    /**
     * Me dice cual es la cantidad de dias que existen entre la fecha fin y la de comienzo.</br>
     * Compara por milisegundos.
     * @return int cantidad de dias entre las dos fechas.
     */
    public static int getDiasEntreDosFechas(Date desde, Date hasta) {
         long MILLISEGUNDOS_POR_DIA = 86400000;
    	
            if(desde == null) {
                    throw new IllegalArgumentException("la fecha de comienzo es null");
            }
            if(hasta == null) {
                    throw new IllegalArgumentException("la fecha de fin es null");
            }
            return new Long((hasta.getTime() - desde.getTime()) / MILLISEGUNDOS_POR_DIA).intValue();
    }

	
	
}
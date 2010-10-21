package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.model.solicitudes.beans.TipoDescuento;
import ar.com.nextel.sfa.client.dto.DescuentoDto;
import ar.com.nextel.sfa.client.dto.DescuentoLineaDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.TipoDescuentoDto;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.ValidationTextBox;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class DescuentoDialog extends NextelDialog implements ChangeHandler, ClickListener {

	private Command aceptarCommand;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
	private DescuentoUIData descuentoUIData;
	
	private FlowPanel topBar = new FlowPanel();
	private InlineLabel descuentoAplicadoLabel;
	private TextArea descuentoAplicado;
	private InlineLabel descuentoLabel;
	private ListBox tipoDeDescuento;
	private InlineLabel montoLabel;
	private InlineLabel porcentajeLabel;
	private ValidationTextBox montoTB;
	private ValidationTextBox porcentajeTB;
	private InlineLabel precioVentaLabel;
	private InlineLabel precioListaLabel;
	private Label precioLista;
	private Label precioVenta;
	
	private Double precioConDescuento = 0.0;
	private Double valorTotal = 0.0;
	private NumberFormat decimalFormat = NumberFormat.getFormat("##########.##");
	private List<TipoDescuentoDto> tiposDeDescuento;
	private List<TipoDescuentoSeleccionado> descuentosSeleccionados;
	private TipoDescuentoSeleccionado seleccionado;
	private boolean noAplicarDesc = false;
	
	public DescuentoDialog(String title, EditarSSUIController controller) {
		super(title, false, true);
		tiposDeDescuento = new ArrayList<TipoDescuentoDto>();
		descuentosSeleccionados = new ArrayList<TipoDescuentoSeleccionado>();
		addStyleName("gwt-DescuentoDialog");
		aceptar = new SimpleLink("ACEPTAR");
		cancelar = new SimpleLink("CANCELAR");
		descuentoUIData = new DescuentoUIData();
		
		FlexTable tablaUno = new FlexTable();
		tablaUno.addStyleName("tableDesc");
		descuentoAplicadoLabel = descuentoUIData.getDescuentoAplicadoLabel();
		descuentoAplicado = descuentoUIData.getDescuentoAplicado();
		descuentoLabel = descuentoUIData.getDescuentoLabel();
		tipoDeDescuento = descuentoUIData.getTipoDeDescuento();
		tablaUno.setWidget(0, 0, descuentoAplicadoLabel);
		tablaUno.setWidget(0, 2, descuentoAplicado);
		tablaUno.setWidget(1, 0, descuentoLabel);
		tablaUno.setWidget(1, 2, tipoDeDescuento);
		topBar.add(tablaUno);
		
		FlexTable tablaDos = new FlexTable();
		tablaDos.addStyleName("tableDesc");
		montoLabel = descuentoUIData.getMontoLabel();
		porcentajeLabel = descuentoUIData.getPorcentajeLabel();
		montoTB = descuentoUIData.getMontoTB();
		porcentajeTB = descuentoUIData.getPorcentajeTB();
		tablaDos.setWidget(0, 0, montoLabel);
		tablaDos.setWidget(0, 2, montoTB);
		tablaDos.setWidget(1, 0, porcentajeLabel);
		tablaDos.setWidget(1, 2, porcentajeTB);
		topBar.add(tablaDos);

		FlexTable tablaTres = new FlexTable();
		tablaTres.addStyleName("tableDesc");
		precioListaLabel = descuentoUIData.getPrecioListaLabel();
		precioVentaLabel = descuentoUIData.getPrecioVentaLabel();
		precioLista = descuentoUIData.getPrecioLista();
		precioVenta = descuentoUIData.getPrecioVenta();
		tablaTres.setWidget(0, 0, precioListaLabel);
		tablaTres.setWidget(0, 1, precioLista);
		tablaTres.setWidget(1, 0, precioVentaLabel);
		tablaTres.setWidget(1, 1, precioVenta);
		topBar.add(tablaTres);
		
		add(topBar);
		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
		aceptar.addClickListener(this);
		cancelar.addClickListener(this);

		montoTB.addChangeHandler(new ChangeHandler() {			
			public void onChange(ChangeEvent arg0) {
				try {
					if (Double.valueOf(montoTB.getValue()) <= precioConDescuento) {
						valorTotal = precioConDescuento - Double.valueOf(montoTB.getValue());
						precioVenta.setText(String.valueOf(decimalFormat.format(valorTotal)).replace(",", "."));
					} else {
						MessageDialog.getInstance().setDialogTitle("Advertencia");
						MessageDialog.getInstance().showAceptar(
								"El monto no puede ser mayor al precio de lista",
								MessageDialog.getCloseCommand());
						noAplicarDesc = true;
					}
				} catch (Exception e) {
				}
				
			}
		});
			
		porcentajeTB.addChangeHandler(new ChangeHandler() {			
			public void onChange(ChangeEvent arg0) {
				try {
					if (Double.valueOf(porcentajeTB.getValue()) < 100) {
						valorTotal = precioConDescuento - (precioConDescuento
									* Double.valueOf(porcentajeTB.getValue()) / 100);
						precioVenta.setText(String.valueOf(decimalFormat.format(valorTotal)).replace(",", "."));
					} else {
						MessageDialog.getInstance().setDialogTitle("Advertencia");
						MessageDialog.getInstance().showAceptar(
								"El porcentaje no puede ser mayor al 100%",
								MessageDialog.getCloseCommand());
						noAplicarDesc = true;
					}
				} catch (Exception e) {
				}
			}
		});
	}
	
	public void setAceptarCommand(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}

	public void onChange(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void show(LineaSolicitudServicioDto linea, DescuentoDto descuento, List<TipoDescuentoDto> descuentosAplicados, List<TipoDescuentoDto> descuentosAAplicar) {
		//reseteo valores
		montoTB.setText("");
		porcentajeTB.setText("");
		precioVenta.setText("");
		tiposDeDescuento.clear();
		tipoDeDescuento.clear();
		descuentoUIData.getPrecioLista().setText(String.valueOf(linea.getPrecioLista()));
		precioConDescuento = linea.getPrecioConDescuento();
		porcentajeTB.setEnabled(true);
		montoTB.setEnabled(true);
		
		//si la linea ya tiene un porcentaje de descuento por default, lo seteo
		if (!descuento.isSobreescribir()) {
			porcentajeTB.setText(String.valueOf(descuento.getOperand()));
			porcentajeTB.setEnabled(false);
			montoTB.setEnabled(false);
			valorTotal = precioConDescuento - (precioConDescuento
					* Double.valueOf(porcentajeTB.getValue()) / 100);
			precioVenta.setText(String.valueOf(decimalFormat.format(valorTotal)).replace(",", "."));
		}
		
		if(descuentosAAplicar.size() > 0) {
			//cargo el listBox con los descuento que puede aplicar el usuario
			for (Iterator<TipoDescuentoDto> iterator = descuentosAAplicar.iterator(); iterator.hasNext();) {
				TipoDescuentoDto tipoDescuentoDto = (TipoDescuentoDto) iterator.next();
				tipoDeDescuento.addItem(tipoDescuentoDto.getDescripcion());
			}
		}
		this.tiposDeDescuento = descuentosAAplicar;
		
		//cargo el textArea con los descuento que ya aplicó el usuario
		String label = "";
		for (Iterator<TipoDescuentoDto> iterator = descuentosAplicados.iterator(); iterator.hasNext();) {
			TipoDescuentoDto tipoDescuento = (TipoDescuentoDto) iterator.next();
			label += tipoDescuento.getDescripcion() + "\n";
		}
		descuentoAplicado.setText(label);
		
		seleccionado = new TipoDescuentoSeleccionado();
		seleccionado.setIdLinea(linea.getId());
		showAndCenter();
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			if ("".equals(montoTB.getValue()) && "".equals(porcentajeTB.getValue())) {
				MessageDialog.getInstance().setDialogTitle("Advertencia");
				MessageDialog.getInstance().showAceptar(
						"Debe ingresar un Monto o Porcentaje para aplicar el descuento",
						MessageDialog.getCloseCommand());
			} else {
				if (!noAplicarDesc) {
					//agrego el tipo de descuento que eligió para que no pueda volverlo a elegir
					seleccionado.setDescripcion(tipoDeDescuento.getSelectedItemText());
					descuentosSeleccionados.add(seleccionado);
				}
				noAplicarDesc = false;
				aceptarCommand.execute();
				hide();
			}
		} else if (sender == cancelar) {
			hide();
		}
	}

	public void modificarPrecioConDescuento(LineaSolicitudServicioDto linea, List<DescuentoDto> descuentos) {		
		//guardo en la linea los valores que modificó el usuario
		if (!"".equals(porcentajeTB.getValue())) {
			linea.setPorcentaje(Double.valueOf(porcentajeTB.getValue()));
		} else {
			linea.setPorcentaje(new Double(0.0));
		}
		linea.setMonto(precioConDescuento - valorTotal);
		if (!"".equals(precioVenta.getText())) {
			linea.setPrecioConDescuento(new Double(precioVenta.getText()));		
		} else {
			linea.setPrecioConDescuento(new Double(0.0));
		}
		
		//creo una linea de descuento y la agrego a la linea de solicitud de servicio
		DescuentoLineaDto descuentoLinea = new DescuentoLineaDto();
		descuentoLinea.setDescripcionTipoDescuento(tipoDeDescuento.getSelectedItemText());
		
		Long idTipoDescuento = null;
		for (Iterator<TipoDescuentoDto> iterator = tiposDeDescuento.iterator(); iterator.hasNext();) {
			TipoDescuentoDto tipoDescuentoDto = (TipoDescuentoDto) iterator.next();
			if (tipoDeDescuento.getSelectedItemText().equals(tipoDescuentoDto.getDescripcion())) {
				idTipoDescuento = tipoDescuentoDto.getId();
			}
		}
		for (Iterator<DescuentoDto> iterator = descuentos.iterator(); iterator.hasNext();) {
			DescuentoDto descuentoDto = (DescuentoDto) iterator.next();
			if (descuentoDto.getIdTipoDescuento().equals(idTipoDescuento)) {
				descuentoLinea.setIdDescuento(descuentoDto.getId());
			}
		}
		descuentoLinea.setIdLinea(linea.getId());
		descuentoLinea.setMonto(linea.getMonto());
		descuentoLinea.setPorcentaje(linea.getPorcentaje());
		linea.addDescuentoLinea(descuentoLinea);
	}

	public List<TipoDescuentoSeleccionado> getDescuentosSeleccionados() {
		return descuentosSeleccionados;
	}
	
}
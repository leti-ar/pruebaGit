package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.initializer.ContratoViewInitializer;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PlanTransferenciaDialog  extends NextelDialog implements ClickListener {

	private FlowPanel topBar = new FlowPanel();
	private SimpleLink aceptar;
	private Command aceptarCommand;
	private SimpleLink cerrar;
	private static TipoPlanDto tipoPlanPorDefecto = null;

	private PlanTransferenciaUIData planTransferenciaUIData;
	private EditarSSUIController controller;
	private List<TipoPlanDto> tiposPlan = null;
	private PlanDto selectedPlan; 
	private String selectedPlanText;
	private String selectedPlanId;
	private String precioVenta;
	private boolean empresa = false;

	public PlanTransferenciaDialog(String title, EditarSSUIController controller) {
		super(title, false, true);
		addStyleName("gwt-ItemSolicitudDialog");
		this.controller = controller;
		aceptar = new SimpleLink("ACEPTAR");
		cerrar = new SimpleLink("CERRAR");
		planTransferenciaUIData = new PlanTransferenciaUIData(controller);

		Grid grid = new Grid(2, 2);
		grid.addStyleName("layout");
		grid.getCellFormatter().setWidth(0, 0, "100px");
		grid.setHTML(0, 0, Sfa.constant().tipoPlan());
		grid.setWidget(0, 1, planTransferenciaUIData.getTipoPlan());
		grid.setHTML(1, 0, Sfa.constant().plan());
		grid.setWidget(1, 1, planTransferenciaUIData.getPlan());
		topBar.add(grid);

		FlexTable tablaDos = new FlexTable();
		tablaDos.setWidget(0, 0, new Label("Servicios Adicionales"));
		tablaDos.setWidget(1, 0, planTransferenciaUIData.getServiciosAdicionales());
		topBar.add(tablaDos);
		
		add(topBar);
		addFormButtons(aceptar);
		addFormButtons(cerrar);
		setFormButtonsVisible(true);
		setFooterVisible(false);

		aceptar.addClickListener(this);
		cerrar.addClickListener(this);

		controller.getContratoViewInitializer(initTiposPlanesCallback());
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			selectedPlan = (PlanDto) planTransferenciaUIData.getPlan().getSelectedItem();
			selectedPlanText = planTransferenciaUIData.getPlan().getSelectedItemText();
			selectedPlanId = planTransferenciaUIData.getPlan().getSelectedItemId();
			precioVenta = String.valueOf(planTransferenciaUIData.getPrecioVenta());
			executeItemCreation(sender);
		} else if (sender == cerrar) {
			hide();
		}
	}

	private void executeItemCreation(final Widget sender) {
		List<String> errors = planTransferenciaUIData.validate();
		if (errors.isEmpty()) {
			guardarItem(sender == aceptar);
		} else {
			ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
			ErrorDialog.getInstance().show(errors, false);
		}
	}

	private void guardarItem(boolean soloGuardar) {
		aceptarCommand.execute();
		if (soloGuardar) {
			hide();
		} else {
			show(new ContratoViewDto(), 0);
		}
	}

	private DefaultWaitCallback initTiposPlanesCallback() {
		return new DefaultWaitCallback<ContratoViewInitializer>() {
			public void success(ContratoViewInitializer initializer) {
				tiposPlan = initializer.getTiposPlanes();
			}
		};
	}

	public PlanTransferenciaUIData getPlanTransferenciaUIData() {
		return planTransferenciaUIData;
	}

	public void setAceptarCommand(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}
	
	public PlanDto getSelectedPlan() {
		return selectedPlan;
	}
	
	public String getSelectedPlanText() {
		return selectedPlanText;
	}
	
	public String getSelectedPlanId() {
		return selectedPlanId;
	}
	
	public String getPrecioVenta() {
		return precioVenta;
	}
	
	public void setCuentaEmpresa(boolean empresa) {
		this.empresa = empresa;
	}

	public void show(ContratoViewDto contrato, int row) {
		planTransferenciaUIData.getTipoPlan().clear();
		planTransferenciaUIData.getTipoPlan().clearPreseleccionados();
//		planTransferenciaUIData.getServiciosAdicionales().clear();
		planTransferenciaUIData.setRow(row);
		planTransferenciaUIData.setContrato(contrato);

		final TipoPlanDto tipoPlan = contrato != null && contrato.getPlan() != null ? contrato.getPlan().getTipoPlan() : null;
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (tiposPlan == null) {
					return true;
				}
				tipoPlanPorDefecto = null;
				for (TipoPlanDto tipoPlan : tiposPlan) {
					if (tipoPlan.getCodigoBSCS().equals(TipoPlanDto.TIPO_PLAN_DIRECTO_O_EMPRESA_CODE)) {
						if (empresa == tipoPlan.isEmpresa()) {
							planTransferenciaUIData.getTipoPlan().addItem(tipoPlan);
							tipoPlanPorDefecto = tipoPlan;
						}
					} else {
						planTransferenciaUIData.getTipoPlan().addItem(tipoPlan);
					}
				}
				planTransferenciaUIData.getTipoPlan().setSelectedItem(tipoPlan != null ? tipoPlan : tipoPlanPorDefecto);
				planTransferenciaUIData.onChange(planTransferenciaUIData.getTipoPlan());
				return false;
			}
		});
		
		showAndCenter();
	}
	
}
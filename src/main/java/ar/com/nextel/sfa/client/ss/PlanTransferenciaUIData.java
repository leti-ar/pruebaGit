package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalIncluidoDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class PlanTransferenciaUIData extends UIData implements ChangeListener, ClickHandler {

	private ListBox tipoPlan;
	private ListBox plan;
	private Long precioVenta;
	private ContratoViewDto contrato;
	private EditarSSUIController controller;
	private ServiciosAdicionalesTable serviciosAdicionales;
	private int row;
	private static final String v1 = "\\{1\\}";
	private static final String v2 = "\\{2\\}";
	private Long idPlanAnterior;
	private Long idContratoAnterior; 
	
	private List<ServicioAdicionalIncluidoDto> serviciosAdicionalesInc;
	
	public PlanTransferenciaUIData(EditarSSUIController controller) {
		this.controller = controller;
		fields = new ArrayList<Widget>();
		fields.add(tipoPlan = new ListBox());
		fields.add(plan = new ListBox(" "));
		tipoPlan.setWidth("400px");
		plan.setWidth("400px");
		tipoPlan.addChangeListener(this);
		plan.addChangeListener(this);
		serviciosAdicionales = new ServiciosAdicionalesTable(controller);
		serviciosAdicionales.getTable().addClickHandler(this);
	}
	
	public ListBox getTipoPlan() {
		return tipoPlan;
	}

	public void setTipoPlan(ListBox tipoPlan) {
		this.tipoPlan = tipoPlan;
	}

	public ListBox getPlan() {
		return plan;
	}

	public void setPlan(ListBox plan) {
		this.plan = plan;
	}
	
	public Long getPrecioVenta() {
		return precioVenta;
	}
	
	public ContratoViewDto getContrato() {
		PlanDto planSelected = (PlanDto) plan.getSelectedItem();

		if (planSelected != null) {
			if (contrato.getPlan() == null
					|| !planSelected.getId().equals(contrato.getPlan().getId())) {
				contrato.setPlan(planSelected);
				contrato.setPlanCesionario(Long.valueOf(plan.getSelectedItemId()));
				contrato.setPrecioPlanCesionario(String.valueOf(planSelected.getPrecioLista()));
			}
		} else {
			contrato.setPlan(planSelected);
			contrato.setPlanCesionario(new Long(""));
			contrato.setPrecioPlanCesionario("");
		}
		return contrato;
	}
	
	public void setContrato(ContratoViewDto contrato) {
		plan.clear();
		plan.clearPreseleccionados();
		serviciosAdicionales.clear();
		this.contrato = contrato;
		if (contrato.getPlan() != null) {
			serviciosAdicionales.setServiciosAdicionalesForContrato(contrato, contrato.getPlan().getId());
			tipoPlan.setSelectedItem(contrato.getPlan().getTipoPlan());
		} else {
			tipoPlan.setSelectedItem(new TipoPlanDto(Long.valueOf(8), "Plan Directo"));
		}
		plan.setSelectedItem(contrato.getPlan());
		idPlanAnterior = contrato.getPlan() != null ? contrato.getPlan().getId() : null;
		idContratoAnterior = contrato != null ? contrato.getContrato() : null;
	}
	
	public ServiciosAdicionalesTable getServiciosAdicionales() {
		return serviciosAdicionales;
	}
	
	public void setServiciosAdicionales(ServiciosAdicionalesTable serviciosAdicionales) {
		this.serviciosAdicionales = serviciosAdicionales;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void onChange(Widget sender) {
		if (sender == tipoPlan) {
			// Cargo los planes correspondientes al tipo de plan seleccionado
			if (tipoPlan.getSelectedItem() != null) {
				controller.getPlanesPorTipoPlan((TipoPlanDto) tipoPlan
						.getSelectedItem(), getActualizarPlanCallback());
			}
		} else if (sender == plan) {
			PlanDto planDto = (PlanDto) plan.getSelectedItem();
			if (planDto != null) {
				
				//MGR**** El if y el else hacen lo mismo?
				if (contrato.getPlan() == null || (!(contrato.getPlan().getId().equals(idPlanAnterior))
						&& contrato.getContrato().equals(idContratoAnterior))) {
					serviciosAdicionales.setServiciosAdicionalesForContrato(contrato, planDto.getId());
				} else {
					serviciosAdicionales.setServiciosAdicionalesForContrato(contrato, planDto.getId());
				}
			}
		}
	}
	//TODO
	public void onClick(ClickEvent clickEvent) {
		Widget sender = (Widget) clickEvent.getSource();
		if (sender == serviciosAdicionales.getTable()) {
			Cell cell = ((HTMLTable) sender).getCellForEvent(clickEvent);
			if (cell != null) {
				if (cell.getCellIndex() == 0 && cell.getRowIndex() > 0) {
					serviciosAdicionales.agregarQuitarServicioAdicionalContrato(cell.getRowIndex(), row);
				}
			}
		}
	}
	
	public DefaultWaitCallback<List<PlanDto>> getActualizarPlanCallback() {
		return new DefaultWaitCallback<List<PlanDto>>() {
			public void success(List<PlanDto> result) {
				if (plan.getItemCount() > 0) {
					plan.clear();
				}
				plan.addAllItems(result);
				if (result.size() == 1) {
					plan.setSelectedIndex(1);
				}
			}
		};
	}
	
	public List<String> validate() {
		GwtValidator validator = new GwtValidator();
		validator.addTarget(tipoPlan).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Tipo Plan"));
		validator.addTarget(plan).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Plan"));
		return validator.fillResult().getErrors();
	}
	
}

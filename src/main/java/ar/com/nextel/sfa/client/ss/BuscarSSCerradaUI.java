package ar.com.nextel.sfa.client.ss;

import ar.com.snoop.gwt.commons.client.util.DateUtil;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Esta página contiene el formulario de busquedas de SS cerradas, la tabla de
 * resultados y la tabla informativa de cambios.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSCerradaUI extends BuscarSSGenericoUI {//ApplicationUI {

	private BuscarSSCerradasFilterUI buscadorSSCerradasFilterForm;
	private DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy");
	public BuscarSSCerradaUI() {
		super();
	}

	@Override
	public void firstLoad() {
		buscadorSSCerradasFilterForm = new BuscarSSCerradasFilterUI();
		buscarSSCerradasResultPanel = new BuscarSSCerradasResultUI(this);
		buscadorSSCerradasFilterForm.setBuscarCuentaResultPanel(buscarSSCerradasResultPanel);
		mainPanel.add(buscadorSSCerradasFilterForm);
		super.firstLoad();
	}
	
	@Override
	public boolean load() {		
		buscadorSSCerradasFilterForm.getBuscadorSSCerradasFilterEditor().cleanAndEnableFields();
		buscadorSSCerradasFilterForm.getBuscadorSSCerradasFilterEditor().getDesdeDatePicker().setSelectedDate(DateUtil.getStartDayOfMonth(DateUtil.today()));
		buscadorSSCerradasFilterForm.getBuscadorSSCerradasFilterEditor().getDesdeDatePicker().getTextBox().setText(dateFormatter.format(DateUtil.getStartDayOfMonth(DateUtil.today())));
		buscadorSSCerradasFilterForm.getBuscadorSSCerradasFilterEditor().getHastaDatePicker().setSelectedDate(DateUtil.today());
		buscadorSSCerradasFilterForm.getBuscadorSSCerradasFilterEditor().getHastaDatePicker().getTextBox().setText(dateFormatter.format(DateUtil.today()));
		exportarExcelSSResultUI.setVisible(false);
		buscarSSCerradasResultPanel.setVisible(false);
		buscarSSTotalesResultUI.setVisible(false);
		cambiosSSCerradasResultUI.setVisible(false);
		return true;
	}

	@Override
	public boolean esAnalistaCreditos() {
		return false;
	}
	
//	public boolean esClienteDeNexus() {
//		return ClientContext.getInstance().vengoDeNexus() && ClientContext.getInstance().soyClienteNexus();
//	}
	
	
}

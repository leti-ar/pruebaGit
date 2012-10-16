package ar.com.nextel.sfa.client.widget;

import java.util.Date;

import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;

public class FechaDatePicker extends Composite {

	private Grid datePickerGrid = new Grid(1, 2);
	private SimpleDatePicker simpleDatePicker = new SimpleDatePicker(false,true);
	private static int FORM_LENGHT_FECHA = 10;


	
	
	
	public FechaDatePicker() {
		initWidget(datePickerGrid);
		simpleDatePicker.setWeekendSelectable(true);
		simpleDatePicker.setSelectedDate(DateUtil.getDaysBeforeADate(60l, new Date()));
		datePickerGrid.setWidget(0, 0, getTextBox());
		datePickerGrid.setWidget(0, 1, simpleDatePicker);
		
	}


	public SimpleDatePicker getSimpleDatePicker() {
		return simpleDatePicker;
	}


	public void setSimpleDatePicker(SimpleDatePicker simpleDatePicker) {
		this.simpleDatePicker = simpleDatePicker;
	}

	public void clean() {
		simpleDatePicker.setSelectedDate(null);
		if (getTextBox() != null) {
			getTextBox().setText("");
		}
	}
	
	public void bloquear(boolean bloquear) {
		if(bloquear) {
			getTextBox().setText("");
			this.setFecha(null);
		}
		getSimpleDatePicker().setEnabled(!bloquear);
		getSimpleDatePicker().getTextBox().setEnabled(!bloquear);
	}
	
	public TextBox getTextBox(){
		return simpleDatePicker.getTextBox();
	}
	
	public boolean validarFecha() {
		String fecha = getTextBox().getText();
		if (fecha.equals("") || fecha.equals(null) || fecha.length() != FORM_LENGHT_FECHA) 
			return false;
		
		if(!fecha.substring(2,3).equals("/") || !fecha.substring(5,6).equals("/"))
			return false;
		
		return true;
	}
	
	public Date getFecha() {
		return simpleDatePicker.getSelectedDate();
	}
	
	public void setFecha(Date fecha) {
		simpleDatePicker.setSelectedDate(fecha);
	}
}

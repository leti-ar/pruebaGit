package ar.com.nextel.sfa.client.validator.target;

import ar.com.snoop.gwt.commons.client.widget.ListBox;

/**
 * Validation target for ListBox
 * 
 * 
 * 
 */
public class ListBoxValidationTarget extends ValidationTarget {

	private ListBox listBox;

	public ListBoxValidationTarget(ListBox listBox) {
		this.listBox = listBox;
	}

	public ListBoxValidationTarget required(String errorMsg) {
		if (isOk()) {
			if (listBox.getSelectedItem() == null) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

}

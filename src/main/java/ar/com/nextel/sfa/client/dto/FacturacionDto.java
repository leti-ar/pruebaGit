package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

	public class FacturacionDto implements ListBoxItem, IsSerializable {

		private String id;
		private String descripcion;

		public FacturacionDto() {
		}
		
		public FacturacionDto(String code, String descripcion) {
			super();
			this.id = code;
			this.descripcion = descripcion;
		}

		public String getItemText() {
			return descripcion;
		}

		public String getItemValue() {
			return id;
		}

		public String getCode() {
			return id;
		}

		public void setCode(String code) {
			this.id = code;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
	}
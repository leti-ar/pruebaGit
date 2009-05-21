package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author mrial
 *
 */
public class CrearContactoInitializer implements IsSerializable {
	
		private List<TipoDocumentoDto> tiposDocumento = new ArrayList<TipoDocumentoDto>();
		private List<SexoDto> sexos = new ArrayList<SexoDto>();
		private List<CargoDto> cargos = new ArrayList<CargoDto>();

		public CrearContactoInitializer() {
			
		}
		
		public CrearContactoInitializer(
				List<TipoDocumentoDto> tiposDocumento,
				List<SexoDto> sexos, 
				List<CargoDto> cargos) {
			this.tiposDocumento = tiposDocumento;
			this.sexos = sexos;
			this.cargos = cargos;
		}

		public List<TipoDocumentoDto> getTiposDocumento() {
			return tiposDocumento;
		}

		public void setTiposDocumento(List<TipoDocumentoDto> tiposDocumento) {
			this.tiposDocumento = tiposDocumento;
		}

		public List<SexoDto> getSexos() {
			return sexos;
		}

		public void setSexos(List<SexoDto> sexos) {
			this.sexos = sexos;
		}

		public List<CargoDto> getCargos() {
			return cargos;
		}

		public void setCargos(List<CargoDto> cargos) {
			this.cargos = cargos;
		}
	
}

package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

public interface EditarSSUIController {

	EditarSSUIData getEditarSSUIData();

	void getLineasSolicitudServicioInitializer(
			DefaultWaitCallback<LineasSolicitudServicioInitializer> defaultWaitCallback);

	void getListaPrecios(TipoSolicitudDto tipoSolicitudDto,
			DefaultWaitCallback<ListaPreciosDto> defaultWaitCallback);
}

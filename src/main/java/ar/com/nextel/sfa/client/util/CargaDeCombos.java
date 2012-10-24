package ar.com.nextel.sfa.client.util;

import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.StockRpcService;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class CargaDeCombos {

	public static void cargarComboTipoOrden(final ListBox lst, final ListBox listaPreciosLst, final ListBox item) {
		StockRpcService.Util.getInstance().obtenerTiposDeSolicitudParaVendedor(
						ClientContext.getInstance().getVendedor(),
						new AsyncCallback<List<TipoSolicitudDto>>() {

							public void onSuccess(List<TipoSolicitudDto> result) {
								lst.addAllItems(result);
								TipoSolicitudDto tipoSolicitud = (TipoSolicitudDto)lst.getSelectedItem();
								cargarListaDePrecios(tipoSolicitud, listaPreciosLst, item);
								WaitWindow.hide();
							}

							public void onFailure(Throwable caught) {
								new NextelDialog("Error: "
										+ caught.getMessage(), false, true);

							}
						});
	}

	public static void cargarListaDePrecios(
			final TipoSolicitudDto tipoSolicitud, final ListBox lst, final ListBox cantItemsLst) {
		lst.clear();
		if (tipoSolicitud != null) {
			if (tipoSolicitud.getListasPrecios() == null) {
				WaitWindow.show();
				SolicitudRpcService.Util.getInstance().getListasDePrecios(
						tipoSolicitud, false,
						new DefaultWaitCallback<List<ListaPreciosDto>>() {
							public void success(
									List<ListaPreciosDto> listBoxItem) {
								lst.addAllItems(listBoxItem);
								tipoSolicitud.setListasPrecios(listBoxItem);
								ListaPreciosDto item = (ListaPreciosDto)lst.getSelectedItem(); 
								List<ItemSolicitudTasadoDto> items = (List<ItemSolicitudTasadoDto>)item.getItemsListaPrecioVisibles();
								cantItemsLst.addAllItems(items);
							}

						});
				WaitWindow.hide();
			} else {
				lst.addAllItems(tipoSolicitud.getListasPrecios());
			}
		}
	}
}

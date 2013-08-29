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

	public static void cargarComboTipoOrden(final ListBox listaTipoOrden, final ListBox listasPrecios, final ListBox listaItems) {
		listaTipoOrden.clear();
		StockRpcService.Util.getInstance().obtenerTiposDeSolicitudParaVendedor(
						ClientContext.getInstance().getVendedor(),
						new AsyncCallback<List<TipoSolicitudDto>>() {

							public void onSuccess(List<TipoSolicitudDto> result) {
								listaTipoOrden.addAllItems(result);
								TipoSolicitudDto tipoSolicitud = (TipoSolicitudDto)listaTipoOrden.getSelectedItem();
								cargarListaDePrecios(tipoSolicitud, listasPrecios, listaItems);
								WaitWindow.hide();
							}

							public void onFailure(Throwable caught) {
								new NextelDialog("Error: "
										+ caught.getMessage(), false, true);

							}
						});
	}

	public static void cargarListaDePrecios(
			final TipoSolicitudDto tipoSolicitud, final ListBox listasPrecios, final ListBox listaItems) {
		listasPrecios.clear();
		if (tipoSolicitud != null) {
			if (tipoSolicitud.getListasPrecios() == null) {
				WaitWindow.show();
				SolicitudRpcService.Util.getInstance().getListasDePrecios(
						tipoSolicitud, false,
						new DefaultWaitCallback<List<ListaPreciosDto>>() {
							public void success(
									List<ListaPreciosDto> listBoxItem) {
								listasPrecios.addAllItems(listBoxItem);
								tipoSolicitud.setListasPrecios(listBoxItem);
								cargarListaDeItems(listasPrecios, listaItems);
							}

						});
				WaitWindow.hide();
			} else {
				listasPrecios.addAllItems(tipoSolicitud.getListasPrecios());
				cargarListaDeItems(listasPrecios, listaItems);
			}
		}
	}

	private static void cargarListaDeItems(final ListBox listasPrecios,
			final ListBox listaItems) {
		ListaPreciosDto item = (ListaPreciosDto)listasPrecios.getSelectedItem(); 
		if (item != null) {
			List<ItemSolicitudTasadoDto> items = (List<ItemSolicitudTasadoDto>)item.getItemsListaPrecioVisibles();
			listaItems.addAllItems(items);
		}
	}
}

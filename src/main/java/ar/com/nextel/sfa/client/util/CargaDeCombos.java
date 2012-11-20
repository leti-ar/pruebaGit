package ar.com.nextel.sfa.client.util;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.StockRpcService;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.ItemSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Clase que carga diferentes combos. Algunos poseen elementos nulos al principio y otros no.
 * 
 * TODO Buscar un criterio mas homogéneo.
 *
 * @author mmanto
 *
 */
public class CargaDeCombos {

	/**
	 * Carga de tipo de orden. Por defecto se carga el primer item de la lista.
	 * 
	 * @param lboxTipoOrden
	 */
	public static void cargarComboTipoOrden(final ListBox lboxTipoOrden) {
		StockRpcService.Util.getInstance().obtenerTiposDeSolicitudParaVendedor(
						ClientContext.getInstance().getVendedor(),
						new AsyncCallback<List<TipoSolicitudDto>>() {

							public void onSuccess(List<TipoSolicitudDto> result) {
								lboxTipoOrden.addAllItems(result);
								WaitWindow.hide();
							}

							public void onFailure(Throwable caught) {
								new NextelDialog("Error: "
										+ caught.getMessage(), false, true);

							}
						});
	}

	/**
	 * Carga de la lista de precios. Por defecto se incorpora un elemento vacío para que el valor por defecto 
	 * del combo este nulo.
	 * 
	 * @param tipoSolicitud
	 * @param lboxListaDePrecios
	 */
	public static void cargarListaDePrecios(
			final TipoSolicitudDto tipoSolicitud, final ListBox lboxListaDePrecios) {
		lboxListaDePrecios.clear();
		if (tipoSolicitud != null) {
			if (tipoSolicitud.getListasPrecios().size() > 0 ) {
				WaitWindow.show();
				SolicitudRpcService.Util.getInstance().getListasDePrecios(
						tipoSolicitud, false,
						new DefaultWaitCallback<List<ListaPreciosDto>>() {
							public void success(
									List<ListaPreciosDto> listaDePrecios) {
								lboxListaDePrecios.clear();
								ListaPreciosDto itemVacio = new ListaPreciosDto();
								tipoSolicitud.setListasPrecios(listaDePrecios);
								// Ponemos el elemento nulo al principio de la lista.
								listaDePrecios.add(0,itemVacio);
								lboxListaDePrecios.addAllItems(listaDePrecios);
							}
						});
				WaitWindow.hide();
			} 
		}
	}
	
	/**
	 * Carga de items. Por defecto el primer elemento del combo es nulo.
	 * 
	 * @param lboxListaDePrecios
	 * @param lboxCantItemsLst
	 */
	public static void cargarComboItems(final ListBox lboxListaDePrecios, final ListBox lboxCantItemsLst){
		lboxCantItemsLst.clear();
		ListaPreciosDto itemSeleccionado = (ListaPreciosDto)lboxListaDePrecios.getSelectedItem();
		if (itemSeleccionado.getId() != null){
			// Copio los elementos del item selecionado asi puedo incorporarle un elemento nulo sin afectar el dto.
			List<ItemSolicitudTasadoDto> items = new ArrayList<ItemSolicitudTasadoDto>();
			if(itemSeleccionado.getItemsListaPrecioVisibles() != null){
				items.addAll((List<ItemSolicitudTasadoDto>)itemSeleccionado.getItemsListaPrecioVisibles());
				ItemSolicitudTasadoDto itemTasadaoDTO = new ItemSolicitudTasadoDto();
				ItemSolicitudDto itemDto = new ItemSolicitudDto();
				itemTasadaoDTO.setItem(itemDto);
				// Ponemos el elemento nulo al principio de la lista.
				items.add(0,itemTasadaoDTO);
				lboxCantItemsLst.addAllItems(items);
			}
		}
		
	}
}

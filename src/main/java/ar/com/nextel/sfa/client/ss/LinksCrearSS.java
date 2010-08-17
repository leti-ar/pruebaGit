package ar.com.nextel.sfa.client.ss;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.widget.UILoader;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;

public class LinksCrearSS extends Composite {
	
	private FlowPanel flwPanel = null;
	private HashMap<Long, Hyperlink> links = null;
	
	public LinksCrearSS(VendedorDto vend){
		
		flwPanel = new FlowPanel();
		links = new HashMap<Long, Hyperlink>();
		
		if(vend != null){
			List<GrupoSolicitudDto> grupos = vend.getTipoVendedor().getGrupos();
			
			if(!grupos.isEmpty()){
				Hyperlink hypLink;
				for (GrupoSolicitudDto gp : grupos) {
					
					hypLink = new Hyperlink(gp.getDescripcion(), "");
					flwPanel.add(hypLink);
					links.put(gp.getId(), hypLink);
				}
			}
		}
		
		initWidget(flwPanel);
	}
	
	public void setHistoryToken(Long idCuentaPotencial, String codigoVanvite, Long idCuenta){
		
		Hyperlink hypLink;
		Iterator<Long> it = links.keySet().iterator();
		
		while(it.hasNext()){
			Long id = it.next();
			hypLink = links.get(id);
			hypLink.setTargetHistoryToken(
					getEditarSSUrl(idCuentaPotencial, id, codigoVanvite,idCuenta));
		}
	}
	
	public void setHistoryToken(Long idCuenta){
		setHistoryToken(null, null, idCuenta);
	}
	
	private String getEditarSSUrl(Long idCuentaPotencial, Long idGrupo, String codigoVanvite,
			Long idCuenta) {
		StringBuilder builder = new StringBuilder(UILoader.AGREGAR_SOLICITUD + "?");
		if (idCuentaPotencial != null)
			builder.append(EditarSSUI.ID_CUENTA_POTENCIAL + "=" + idCuentaPotencial + "&");
		if (codigoVanvite != null)
			builder.append(EditarSSUI.CODIGO_VANTIVE + "=" + codigoVanvite + "&");
		if (idCuenta != null && idCuenta > 0)
			builder.append(EditarSSUI.ID_CUENTA + "=" + idCuenta + "&");
		if (idGrupo != null)
			builder.append(EditarSSUI.ID_GRUPO_SS + "=" + idGrupo);
		return builder.toString();
	}
}

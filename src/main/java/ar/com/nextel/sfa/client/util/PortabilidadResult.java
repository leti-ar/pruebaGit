package ar.com.nextel.sfa.client.util;

import java.util.ArrayList;
import java.util.List;


import com.google.gwt.user.client.rpc.IsSerializable;

public class PortabilidadResult  implements IsSerializable{
	private boolean conError;
	private boolean permiteGrabar;
	private String descripcionError;
	public enum ERROR_ENUM {ERROR,WARNING};
	private List<String> erroresDesc = new ArrayList<String>();

	private List<ERROR_ENUM> erroresComp = new ArrayList<ERROR_ENUM>();
	
	public PortabilidadResult(){
		
	}

	public void addError(ERROR_ENUM err,String desc){
		erroresComp.add(err);
		erroresDesc.add(desc);
	}
	
	public PortabilidadResult generar(){
		if(erroresComp.size() > 0){
			int contERROR = 0;
			int contWARNING = 0;
			
			for (ERROR_ENUM item : erroresComp) {
				switch(item){
				case ERROR:
					contERROR++;
					break;
				case WARNING:
					contWARNING++;
					break;
				}
			}
			
			conError = true;
			if(contERROR > 0) permiteGrabar = false;
			else permiteGrabar = true;
			
			descripcionError = generarDescripcionError();
		}else{
			conError = false;
			permiteGrabar = true;
			descripcionError = "";
		}
		
		return this;
	}
	
	private String generarDescripcionError(){
		String desc = "";
		
		for(int i = 0; i < erroresDesc.size(); i++)
			desc = (i == 0)? erroresDesc.get(i) : desc + "\n" + erroresDesc.get(i);
		
		return desc;
	}
	
	public boolean isConError() {
		return conError;
	}

	public void setConError(boolean conError) {
		this.conError = conError;
	}

	public boolean getPermiteGrabar() {
		return permiteGrabar;
	}

	public void setPermiteGrabar(boolean permiteGrabar) {
		this.permiteGrabar = permiteGrabar;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	public List<String> getErroresDesc() {
		return erroresDesc;
	}

}

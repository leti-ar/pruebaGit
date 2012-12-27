package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * DTO para indicar la cantidad de equipos de la cuenta segun su estado.
 * Analogo al que mapea la respuesta de <tt>EquiposPorEstadoProcedureImpl</tt> del lado del servidor
 *
 * Creada: Oct 23, 2012
 *
 * @author mrotger
 *
 */
public class CantEquiposDTO implements IsSerializable{

	private int cantEquipActivos;
	private int cantEquipDesactivados;
	private int cantEquipSuspendidos;

	/** Constructor sin parámetros */
	public CantEquiposDTO() {
	}
	
	/** Constructor */
	public CantEquiposDTO(int cantEquipActivos, int cantEquipDesactivados, int cantEquipSuspendidos) {
		this.cantEquipActivos = cantEquipActivos;
		this.cantEquipDesactivados = cantEquipDesactivados;
		this.cantEquipSuspendidos = cantEquipSuspendidos;
	}

	public int getCantEquipActivos() {
		return cantEquipActivos;
	}

	public void setCantEquipActivos(int cantEquipActivos) {
		this.cantEquipActivos = cantEquipActivos;
	}

	public int getCantEquipDesactivados() {
		return cantEquipDesactivados;
	}

	public void setCantEquipDesactivados(int cantEquipDesactivados) {
		this.cantEquipDesactivados = cantEquipDesactivados;
	}

	public int getCantEquipSuspendidos() {
		return cantEquipSuspendidos;
	}

	public void setCantEquipSuspendidos(int cantEquipSuspendidos) {
		this.cantEquipSuspendidos = cantEquipSuspendidos;
	}
}

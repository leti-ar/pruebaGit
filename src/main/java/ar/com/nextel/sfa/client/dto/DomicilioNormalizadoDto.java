package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class DomicilioNormalizadoDto  implements IsSerializable{

    // Direccion principal
    private String calle;
    private String numero;
    private String piso;
    private String depto;
    private String edificio;
    private String localidad;
    private String barrio;
    private String partido;
    private ProvinciaDto provincia;
    private String cp;
    private String cpa;
    private String obs;
    // Direccion complemento
    private String distancia;
    private String sinonimo;
    private String entre1;
    private String entre2;
    private String desde;
    private String hasta;

	public String getDomicilioCompleto() {
		/** Muestra la concatenación de Calle, Número, Piso, Dpto., UF, Torre, Localidad, Partido, Provincia, Código Postal y CPA para cada domicilio habilitado.*/
		//String domicilios = this.calle + " " + (this.numero!=null? this.numero:"") + " " + (this.piso!=null? this.piso:"") + " " + (this.departamento!=null? this.departamento:"") + " " + (this.torre!=null? this.torre:"") + " " + this.localidad + " " + this.partido + " " + this.provincia.getDescripcion() + " " + (this.codigo_postal!=null? this.codigo_postal:"") + " " + this.cpa;	
		
		//TODO: Va el de arriba!!!
		String domicilio = this.calle + " " + (this.numero!=null? this.numero:"") + " " + (this.piso!=null? this.piso:"") + " " + (this.depto!=null? this.depto:"") + " " + (this.edificio!=null? this.edificio:"") + " " + this.localidad + " " + (this.cp!=null? this.cp:"") + " " + this.cpa;
		return domicilio;
		}
    
    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCpa() {
        return cpa;
    }

    public void setCpa(String cpa) {
        this.cpa = cpa;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getEntre1() {
        return entre1;
    }

    public void setEntre1(String entre1) {
        this.entre1 = entre1;
    }

    public String getEntre2() {
        return entre2;
    }

    public void setEntre2(String entre2) {
        this.entre2 = entre2;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getSinonimo() {
        return sinonimo;
    }

    public void setSinonimo(String sinonimo) {
        this.sinonimo = sinonimo;
    }

    public ProvinciaDto getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaDto provincia) {
        this.provincia = provincia;
    }
}

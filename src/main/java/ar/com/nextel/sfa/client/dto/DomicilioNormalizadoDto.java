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

    public String getBarrio() {
        return barrio;
    }

//    public String toXml() {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append("<direccion>");
//        buffer.append("<calle>" + this.getCalle() + "</calle>");
//        buffer.append("<barrio>" + this.getBarrio() + "</barrio>");
//        buffer.append("<cp>" + this.getCp() + "</cp>");
//        buffer.append("<cpa>" + this.getCpa() + "</cpa>");
//        buffer.append("<depto>" + this.getDepto() + "</depto>");
//        buffer.append("<desde>" + this.getDesde() + "</desde>");
//        buffer.append("<distancia>" + this.getDistancia() + "</distancia>");
//        buffer.append("<edificio>" + this.getEdificio() + "</edificio>");
//        buffer.append("<entre1>" + this.getEntre1() + "</entre1>");
//        buffer.append("<entre2>" + this.getEntre2() + "</entre2>");
//        buffer.append("<hasta>" + this.getHasta() + "</hasta>");
//        buffer.append("<localidad>" + this.getLocalidad() + "</localidad>");
//        buffer.append("<numero>" + this.getNumero() + "</numero>");
//        buffer.append("<obs>" + this.getObs() + "</obs>");
//        buffer.append("<partido>" + this.getPartido() + "</partido>");
//        buffer.append("<piso>" + this.getPiso() + "</piso>");
//        buffer.append("<provincia>" + this.getProvincia().getId() + "</provincia>");
//        buffer.append("<sinonimo>" + this.getSinonimo() + "</sinonimo>");
//        buffer.append("</direccion>");
//        return buffer.toString();
//    }

//    public void fromXml(String xml, Map provincias) throws DocumentException {
//        Document document = DocumentHelper.createDocument("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + xml);
//        Element ndireccion = document.getRootElement();
//        Element ndireccionPrincipal = ndireccion.element("ndireccion_principal");
//        Element ndireccionComplemento = ndireccion.element("ndireccion_complemento");
//
//        // Direccion principal
//        this.calle = ndireccionPrincipal.element("calle").getText();
//        this.numero = ndireccionPrincipal.element("numero").getText();
//        this.piso = ndireccionPrincipal.element("piso").getText();
//        this.depto = ndireccionPrincipal.element("depto").getText();
//        this.edificio = ndireccionPrincipal.element("edificio").getText();
//        this.localidad = ndireccionPrincipal.element("localidad").getText();
//        this.barrio = ndireccionPrincipal.element("barrio").getText();
//        this.partido = ndireccionPrincipal.element("partido").getText();
//        String provinciaString = ndireccionPrincipal.element("provincia").getText();
//        this.provincia = (ProvinciaDto) provincias.get(provinciaString);
//        this.cp = ndireccionPrincipal.element("cp").getText();
//        this.cpa = ndireccionPrincipal.element("cpa").getText();
//
//        // Direccion complemento
//        this.distancia = ndireccionComplemento.element("distancia").getText();
//        this.sinonimo = ndireccionComplemento.element("sinonimo").getText();
//        this.entre1 = ndireccionComplemento.element("entre1").getText();
//        this.entre2 = ndireccionComplemento.element("entre2").getText();
//        this.desde = ndireccionComplemento.element("desde").getText();
//        this.hasta = ndireccionComplemento.element("hasta").getText();
//    }

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

package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador
 */
public class NormalizarDomicilioResultDto implements IsSerializable{

    private String tipo; // exito|no_parseado|no_encontrado|dudas

    private List dudas; // MerlinDireccionDTO

    private List motivos; // MotivoDudaDTO

    //private MerlinDireccionDTO direccion;
    private DomiciliosCuentaDto direccion;

    public NormalizarDomicilioResultDto() {
        this.dudas = new ArrayList();
        this.motivos = new ArrayList();
    }
//
//    public boolean isCorrect() {
//        return "exito".equals(tipo);
//    }
//
//    public boolean hasDoubts() {
//        return "dudas".equals(tipo);
//    }
//
//    public boolean notFound() {
//        return "no_encontrado".equals(tipo);
//    }

//    public void populateDomicilio(Domicilio domicilio) {
//        if (isCorrect()) {
//            MerlinDireccionDTO direccionNormalizada = this.getDireccion();
//            domicilio.setCalle(direccionNormalizada.getCalle());
//            domicilio.setNumero(direccionNormalizada.getNumero());
//            domicilio.setPiso(direccionNormalizada.getPiso());
//            domicilio.setDepartamento(direccionNormalizada.getDepto());
//            domicilio.setCodigoPostal(direccionNormalizada.getCp());
//            domicilio.setCpa(direccionNormalizada.getCpa());
//            domicilio.setLocalidad(direccionNormalizada.getLocalidad());
//            domicilio.setObservaciones(domicilio.getObservaciones() + " (" + direccion.getObs() + ")");
//            domicilio.setPartido(direccionNormalizada.getPartido());
//            domicilio.setProvincia(direccionNormalizada.getProvincia());
//        }
//    }
//
//    public String toXml() {
//        if (isCorrect()) {
//            return exitoToXml();
//        } else if (hasDoubts() || notFound()) {
//            return dudasToXml();
//        } else {
//            return noParseadoToXml();
//        }
//    }
//
//    private String encabezadoXml() {
//        return "";
//    }
//
//    private String exitoToXml() {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(encabezadoXml());
//        MerlinDireccionDTO direccionRta = this.getDireccion();
//        buffer.append("<direccion-normalizada>");
//        buffer.append(direccionRta.toXml());
//        buffer.append("</direccion-normalizada>");
//        return buffer.toString();
//    }
//
//    private String dudasToXml() {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(encabezadoXml());
//        buffer.append("<dudas>");
//        buffer.append("<posibles-direcciones>");
//        List listaDudas = this.getDudas();
//        Iterator dudasIt = listaDudas.iterator();
//        while (dudasIt.hasNext()) {
//            MerlinDireccionDTO direccionDuda = (MerlinDireccionDTO) dudasIt.next();
//            buffer.append(direccionDuda.toXml());
//        }
//        buffer.append("</posibles-direcciones>");
//        buffer.append("<motivos-dudas>");
//        List motivosDuda = this.getMotivos();
//        Iterator motivosIt = motivosDuda.iterator();
//        while (motivosIt.hasNext()) {
//            MotivoDudaDTO motivo = (MotivoDudaDTO) motivosIt.next();
//            buffer.append(motivo.toXml());
//        }
//        buffer.append("</motivos-dudas>");
//        buffer.append("</dudas>");
//        return buffer.toString();
//    }
//
//    private String noParseadoToXml() {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(encabezadoXml());
//        buffer.append("<error>");
//        buffer.append("no parseado");
//        buffer.append("</error>");
//        return buffer.toString();
//    }
//
//    public void populateFromXml(String xml, Map provincias) throws DocumentException {
//        AppLogger.debug("Respuesta original de merlin: \n" + xml, this);
//        xml = this.filterNull(xml);
//        AppLogger.debug("Respuesta filtrada de merlin: \n" + xml, this);
//        Document document = DocumentHelper.createDocument(xml);
//
//        Element rtaDireccion = document.getRootElement();
//        tipo = rtaDireccion.attributeValue("tipo");
//        Iterator listaMotivos = rtaDireccion.element("lista_motivos").elementIterator();
//        Element ndireccion = rtaDireccion.element("ndireccion");
//        Iterator listaDudas = rtaDireccion.element("lista_dudas").elementIterator();
//
//        while (listaMotivos.hasNext()) {
//            Element motivoE = (Element) listaMotivos.next();
//            MotivoDudaDTO motivo = new MotivoDudaDTO();
//            motivo.fromXml(motivoE.asXML());
//            motivos.add(motivo);
//        }
//
//        MerlinDireccionDTO direccionRta = new MerlinDireccionDTO();
//        direccionRta.fromXml(ndireccion.asXML(), provincias);
//        direccion = direccionRta;
//
//        while (listaDudas.hasNext()) {
//            Element direccionElement = (Element) listaDudas.next();
//            MerlinDireccionDTO direccionDuda = new MerlinDireccionDTO();
//            direccionDuda.fromXml(direccionElement.asXML(), provincias);
//            dudas.add(direccionDuda);
//        }
//    }
//
//    private String filterNull(String xml) {
//        return xml.replaceAll("null|NULL", "");
//    }

//    /**
//     * @return Retorna el/la direccion.
//     */
//    public MerlinDireccionDTO getDireccion() {
//        return direccion;
//    }
//
//    /**
//     * @param direccion
//     *            El/La direccion a setear.
//     */
//    public void setDireccion(MerlinDireccionDTO direccion) {
//        this.direccion = direccion;
//    }


    public DomiciliosCuentaDto getDireccion() {
        return direccion;
    }
    
    public void setDireccion(DomiciliosCuentaDto direccion) {
        this.direccion = direccion;
    }
    
    /**
     * @return Retorna el/la dudas.
     */
    public List getDudas() {
        return dudas;
    }

    /**
     * @param dudas
     *            El/La dudas a setear.
     */
    public void setDudas(List dudas) {
        this.dudas = dudas;
    }

    /**
     * @return Retorna el/la motivos.
     */
    public List getMotivos() {
        return motivos;
    }

    /**
     * @param motivos
     *            El/La motivos a setear.
     */
    public void setMotivos(List motivos) {
        this.motivos = motivos;
    }

    /**
     * @return Retorna el/la tipo.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo
     *            El/La tipo a setear.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

package ar.com.nextel.sfa.client.dto;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import ar.com.nextel.util.documento.DocumentHelper;

/**
 * @author eSalvador 
 **/
public class NormalizacionDomicilioMotivoDto {

    private String motivo;
    private static final String EXACT_MATCH = "exact_match";
    private static final String ALTURA_INVALIDA = "altura_invalida";

    /*
     * duda_standard|duda_altura_invalida|calle_invalida|
     * localidad_invalida|provincia_invalida|localidad_equivalente|
     * localidad_vecina|partido|delta_cp|distancia_minima|exact_match
     */

    public String getMotivo() {
        return motivo;
    }

    public String toXml() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<motivo-duda>");
        buffer.append(this.getMotivo());
        buffer.append("</motivo-duda>");
        return buffer.toString();
    }

    public void fromXml(String xml) throws DocumentException {
        Document document = DocumentHelper.createDocument("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + xml);
        Element motivoDuda = document.getRootElement();
        this.setMotivo(motivoDuda.attributeValue("valor"));
    }

    public void setMotivo(String motivo) {
        if (EXACT_MATCH.equals(motivo)) {
            this.motivo = "Coincidencia exacta";
        } else {
            if (ALTURA_INVALIDA.equals(motivo)) {
                this.motivo = "Altura inválida";
            } else {
                if ("calle_invalida".equals(motivo)) {
                    this.motivo = "Calle inválida";
                }
            }
        }
    }
}

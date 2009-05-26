package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador
 */
public class NormalizarDomicilioResultDto implements IsSerializable{

    private String tipo; // exito|no_parseado|no_encontrado|dudas

    private List<DomiciliosCuentaDto> dudas; // DomiciliosCuentaDto

    private List<NormalizacionDomicilioMotivoDto> motivos; // NormalizacionDomicilioMotivoDto

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

    public DomiciliosCuentaDto getDireccion() {
        return direccion;
    }
    
    public void setDireccion(DomiciliosCuentaDto direccion) {
        this.direccion = direccion;
    }
    
    /**
     * @return Retorna el/la dudas.
     */
    public List<DomiciliosCuentaDto> getDudas() {
        return dudas;
    }

    /**
     * @param dudas
     *            El/La dudas a setear.
     */
    public void setDudas(List<DomiciliosCuentaDto> dudas) {
        this.dudas = dudas;
    }

    /**
     * @return Retorna el/la motivos.
     */
    public List<NormalizacionDomicilioMotivoDto> getMotivos() {
        return motivos;
    }

    /**
     * @param motivos
     *            El/La motivos a setear.
     */
    public void setMotivos(List<NormalizacionDomicilioMotivoDto> motivos) {
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

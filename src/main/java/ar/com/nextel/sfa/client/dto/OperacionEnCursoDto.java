package ar.com.nextel.sfa.client.dto;

import java.io.Serializable;

public class OperacionEnCursoDto implements Serializable {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;

    private String razonSocial;
    private String numeroCliente;
    private String descripcionGrupo;

    /**
     * Constructor default.
     */
    public OperacionEnCursoDto() {
    }


    /**
     * Devuelve la raz�n social de la cuenta.
     * 
     * @return raz�n social
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Devuelve el n�mero de la cuenta.
     * 
     * @return n�mero de la cuenta
     */
    public String getNumeroCliente() {
        return numeroCliente;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
//    public boolean equals(Object obj) {
//        if (obj instanceof OperacionEnCurso) {
//            OperacionEnCurso operacion = (OperacionEnCurso) obj;
////            return this.getId().equals(operacion.getId());
//            return new EqualsBuilder().append(vendedor, operacion.vendedor).append(cuenta, operacion.cuenta).append(
//                solicitud, operacion.solicitud).isEquals();
//        } else {
//            return false;
//        }
//    }
    
    /**
     * Retorna el/la descripcionGrupo.
     *
     * @return descripcionGrupo
     */
    public String getDescripcionGrupo() {
        return descripcionGrupo;
    }
    
    /**
     * Settea el/la descripcionGrupo.
     *
     * @param descripcionGrupo descripcionGrupo
     */
    public void setDescripcionGrupo(String descripcionGrupo) {
        this.descripcionGrupo = descripcionGrupo;
    }

    /**
     * Settea el/la razonSocial.
     *
     * @param razonSocial razonSocial
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Settea el/la numeroCliente.
     *
     * @param numeroCliente numeroCliente
     */
    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }
}

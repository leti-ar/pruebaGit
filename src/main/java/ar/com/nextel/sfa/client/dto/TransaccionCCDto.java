package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Representa una transaccion (registro) en la tabla de Cuenta Corriente. Se modifico para mejorar la
 * performance de renderizado
 * 
 * @author <a href="mailto:jperez@snoop.com.ar">Jorge L Garcia</a>
 */
public class TransaccionCCDto implements IsSerializable {

	public static final String DESC_FACTURA = "Factura";
	public static final String DESC_PAGO = "Pago";
	public static final String DESC_NOTA_CRED = "Nota";
	public static final String DESC_REVER = "Reversión";
	public static final String DESC_AJUSTE = "Ajuste";
	public static final String DESC_DEP_GARAN = "Dep. Garantía";
	public static final String COD_FACTURA = "IN";
	public static final String COD_PAGO = "P";
	public static final String COD_NOTA_CRED = "CM";
	public static final String COD_REVER = "R";
	public static final String COD_AJUSTE = "FC";
	public static final String COD_DEP_GARAN = "DP";

	private String[] row;

	public TransaccionCCDto() {
		row = new String[9];
	}

	/**
	 * Setea la posicion 0 de row con la clase
	 */
	public void setClase(String clase) {
		if (clase != null && clase.indexOf("\\n") != -1) {
			row[0] = clase.replaceAll("\\n", " ");
		} else {
			row[0] = clase;
		}
	}

	/**
	 * Setea la posicion 1 de row con el numero
	 */
	public void setNumero(String numero) {
		row[1] = numero;
	}

	/**
	 * Setea la posicion 2 de row con el tipo
	 */
	public void setTipo(String tipo) {
		row[2] = tipo;
	}

	/**
	 * Setea la posicion 3 de row con la descripcion
	 */
	public void setDescripcion(String descripcion) {
		row[3] = descripcion;
	}

	/**
	 * Setea la posicion 4 de row con la fechaDoc
	 */
	public void setFechaDoc(String fechaDoc) {
		row[4] = fechaDoc;
	}

	/**
	 * Setea la posicion 5 de row con la fechaVenc
	 */
	public void setFechaVenc(String fechaVenc) {
		row[5] = fechaVenc;
	}

	/**
	 * Setea la posicion 6 de row con el importe
	 */
	public void setImporte(String importe) {
		row[6] = importe;
	}

	/**
	 * Setea la posicion 7 de row con el saldo
	 */
	public void setSaldo(String saldo) {
		row[7] = saldo;
	}

	/**
	 * Setea la posicion 8 de row con el xact
	 */
	public void setXact(String xact) {
		row[8] = xact;
	}

	public String getClase() {
		return row[0];
	}

	public String getNumero() {
		return row[1];
	}

	public String getTipo() {
		return row[2];
	}

	public String getDescripcion() {
		return row[3];
	}

	public String getFechaDoc() {
		return row[4];
	}

	public String getFechaVenc() {
		return row[5];
	}

	public String getImporte() {
		return row[6];
	}

	public String getSaldo() {
		return row[7];
	}

	public String getXact() {
		return row[8];
	}

	public String getCodigoDeClase() {
		String codigo = "";
		String clase = row[0];
		if (DESC_FACTURA.equals(clase)) {
			codigo = COD_FACTURA;
		} else if (DESC_NOTA_CRED.equals(clase.substring(0, 4))) {
			codigo = COD_NOTA_CRED;
		} else if (DESC_PAGO.equals(clase)) {
			codigo = COD_PAGO;
		} else if (DESC_AJUSTE.equals(clase)) {
			codigo = COD_AJUSTE;
		} else if ((DESC_REVER.substring(0, 4)).equals(clase.substring(0, 4))) {
			codigo = COD_REVER;
		} else if ((DESC_DEP_GARAN.substring(0, 4)).equals(clase.substring(0, 4))) {
			codigo = COD_DEP_GARAN;
		}
		return codigo;
	}

	/**
	 * @return Una lista de strings con clase(0), numero(1), tipo(2), descripcion(3), fechaDoc(4),
	 *         fechaVenc(5), importe(6), saldo(7) y xact(8);
	 */
	public String[] getRow() {
		return row;
	}

}

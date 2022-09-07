/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util;

/**
 *
 * @author Andres Guachisaca
 */
public class CostoSaldoBodegasNombres {
	private String codigo;
	private String producto;

	public CostoSaldoBodegasNombres(String codigo, String producto) {
		this.codigo = codigo;
		this.producto = producto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

}

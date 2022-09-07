/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.helper;

/**
 *
 * @author Andres Guachisaca
 */
public class CuentasContablesConNombre {
	private String cuenta;
	private String nombre;

	public CuentasContablesConNombre() {
	}

	public CuentasContablesConNombre(String cuenta, String nombre) {
		this.cuenta = cuenta;
		this.nombre = nombre;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

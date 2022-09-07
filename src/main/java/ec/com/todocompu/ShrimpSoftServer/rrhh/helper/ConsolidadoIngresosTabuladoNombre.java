/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.helper;

/**
 *
 * @author Andres Guachisaca
 */
public class ConsolidadoIngresosTabuladoNombre {
	private String cedula;
	private String nombre;
	private String fechaIngreso;
	private String fechaSalida;

	public ConsolidadoIngresosTabuladoNombre() {
	}

	public ConsolidadoIngresosTabuladoNombre(String cedula, String nombre, String fechaIngreso, String fechaSalida) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

}

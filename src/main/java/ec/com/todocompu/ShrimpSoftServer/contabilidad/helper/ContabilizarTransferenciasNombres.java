/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.helper;

/**
 *
 * @author Andres Guachisaca
 */
public class ContabilizarTransferenciasNombres {
	private String fecha;
	private String codigo;
	private String producto;
	private String medida;
	private String cuentaDebito;
	private String cuentaCredito;

	public ContabilizarTransferenciasNombres() {
	}

	public ContabilizarTransferenciasNombres(String fecha, String codigo, String producto, String medida,
			String cuentaDebito, String cuentaCredito) {
		this.fecha = fecha;
		this.codigo = codigo;
		this.producto = producto;
		this.medida = medida;
		this.cuentaDebito = cuentaDebito;
		this.cuentaCredito = cuentaCredito;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCuentaCredito() {
		return cuentaCredito;
	}

	public void setCuentaCredito(String cuentaCredito) {
		this.cuentaCredito = cuentaCredito;
	}

	public String getCuentaDebito() {
		return cuentaDebito;
	}

	public void setCuentaDebito(String cuentaDebito) {
		this.cuentaDebito = cuentaDebito;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}
}

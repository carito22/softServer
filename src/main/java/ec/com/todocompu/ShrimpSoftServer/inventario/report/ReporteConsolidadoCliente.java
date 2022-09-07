/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteConsolidadoCliente {
	/// CABECERA
	private String desde;
	private String hasta;
	private String sector;

	/// DETALLE
	private String vtaIdentificacion;
	private String vtaNombreCliente;
	private java.math.BigDecimal vtaNumeroComprobantes;
	private java.math.BigDecimal vtaBase0;
	private java.math.BigDecimal vtaBaseImponible;
	private java.math.BigDecimal vtaMontoIva;
	private java.math.BigDecimal vtaTotal;

	public ReporteConsolidadoCliente() {
	}

	public ReporteConsolidadoCliente(String desde, String hasta, String sector, String vtaIdentificacion,
			String vtaNombreCliente, BigDecimal vtaNumeroComprobantes, BigDecimal vtaBase0, BigDecimal vtaBaseImponible,
			BigDecimal vtaMontoIva, BigDecimal vtaTotal) {

		this.desde = desde;
		this.hasta = hasta;
		this.sector = sector;
		this.vtaIdentificacion = vtaIdentificacion;
		this.vtaNombreCliente = vtaNombreCliente;
		this.vtaNumeroComprobantes = vtaNumeroComprobantes;
		this.vtaBase0 = vtaBase0;
		this.vtaBaseImponible = vtaBaseImponible;
		this.vtaMontoIva = vtaMontoIva;
		this.vtaTotal = vtaTotal;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getVtaIdentificacion() {
		return vtaIdentificacion;
	}

	public void setVtaIdentificacion(String vtaIdentificacion) {
		this.vtaIdentificacion = vtaIdentificacion;
	}

	public String getVtaNombreCliente() {
		return vtaNombreCliente;
	}

	public void setVtaNombreCliente(String vtaNombreCliente) {
		this.vtaNombreCliente = vtaNombreCliente;
	}

	public java.math.BigDecimal getVtaNumeroComprobantes() {
		return vtaNumeroComprobantes;
	}

	public void setVtaNumeroComprobantes(java.math.BigDecimal vtaNumeroComprobantes) {
		this.vtaNumeroComprobantes = vtaNumeroComprobantes;
	}

	public java.math.BigDecimal getVtaBase0() {
		return vtaBase0;
	}

	public void setVtaBase0(java.math.BigDecimal vtaBase0) {
		this.vtaBase0 = vtaBase0;
	}

	public java.math.BigDecimal getVtaBaseImponible() {
		return vtaBaseImponible;
	}

	public void setVtaBaseImponible(java.math.BigDecimal vtaBaseImponible) {
		this.vtaBaseImponible = vtaBaseImponible;
	}

	public java.math.BigDecimal getVtaMontoIva() {
		return vtaMontoIva;
	}

	public void setVtaMontoIva(java.math.BigDecimal vtaMontoIva) {
		this.vtaMontoIva = vtaMontoIva;
	}

	public java.math.BigDecimal getVtaTotal() {
		return vtaTotal;
	}

	public void setVtaTotal(java.math.BigDecimal vtaTotal) {
		this.vtaTotal = vtaTotal;
	}

}

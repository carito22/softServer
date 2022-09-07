/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.produccion.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteCostoDetalladoCorrida {
	private String codigoSector;
	private String codigoPiscina;
	private String corridaNumero;
	private java.math.BigDecimal hectareas;
	private Integer numeroLarvas;
	private String desde;
	private String hasta;

	////////////// DETALLE
	private String costoProducto;
	private String costoCodigo;
	private String costoMedida;
	private java.math.BigDecimal costoCantidad;
	private java.math.BigDecimal costoTotal;
	private java.math.BigDecimal costoPorcentaje;

	public ReporteCostoDetalladoCorrida() {
	}

	public ReporteCostoDetalladoCorrida(String codigoSector, String codigoPiscina, String corridaNumero,
			BigDecimal hectareas, Integer numeroLarvas, String desde, String hasta, String costoProducto,
			String costoCodigo, String costoMedida, java.math.BigDecimal costoCantidad, java.math.BigDecimal costoTotal,
			java.math.BigDecimal costoPorcentaje) {
		this.codigoSector = codigoSector;
		this.codigoPiscina = codigoPiscina;
		this.corridaNumero = corridaNumero;
		this.hectareas = hectareas;
		this.numeroLarvas = numeroLarvas;
		this.desde = desde;
		this.hasta = hasta;
		this.costoProducto = costoProducto;
		this.costoCodigo = costoCodigo;
		this.costoMedida = costoMedida;
		this.costoCantidad = costoCantidad;
		this.costoTotal = costoTotal;
		this.costoPorcentaje = costoPorcentaje;
	}

	public String getCodigoPiscina() {
		return codigoPiscina;
	}

	public void setCodigoPiscina(String codigoPiscina) {
		this.codigoPiscina = codigoPiscina;
	}

	public String getCodigoSector() {
		return codigoSector;
	}

	public void setCodigoSector(String codigoSector) {
		this.codigoSector = codigoSector;
	}

	public String getCorridaNumero() {
		return corridaNumero;
	}

	public void setCorridaNumero(String corridaNumero) {
		this.corridaNumero = corridaNumero;
	}

	public java.math.BigDecimal getCostoCantidad() {
		return costoCantidad;
	}

	public void setCostoCantidad(java.math.BigDecimal costoCantidad) {
		this.costoCantidad = costoCantidad;
	}

	public String getCostoCodigo() {
		return costoCodigo;
	}

	public void setCostoCodigo(String costoCodigo) {
		this.costoCodigo = costoCodigo;
	}

	public String getCostoMedida() {
		return costoMedida;
	}

	public void setCostoMedida(String costoMedida) {
		this.costoMedida = costoMedida;
	}

	public java.math.BigDecimal getCostoPorcentaje() {
		return costoPorcentaje;
	}

	public void setCostoPorcentaje(java.math.BigDecimal costoPorcentaje) {
		this.costoPorcentaje = costoPorcentaje;
	}

	public String getCostoProducto() {
		return costoProducto;
	}

	public void setCostoProducto(String costoProducto) {
		this.costoProducto = costoProducto;
	}

	public java.math.BigDecimal getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(java.math.BigDecimal costoTotal) {
		this.costoTotal = costoTotal;
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

	public BigDecimal getHectareas() {
		return hectareas;
	}

	public void setHectareas(BigDecimal hectareas) {
		this.hectareas = hectareas;
	}

	public Integer getNumeroLarvas() {
		return numeroLarvas;
	}

	public void setNumeroLarvas(Integer numeroLarvas) {
		this.numeroLarvas = numeroLarvas;
	}
}

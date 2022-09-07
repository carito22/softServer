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
public class ReporteConsumoPorPiscina {
	///// CABECERA
	private String sector;
	private String piscina;
	private String corrida;
	private java.math.BigDecimal hectareas;
	private Integer numLarvas;
	private String desde;
	private String hasta;

	///// DETALLE
	private String consumoProducto;
	private String consumoCodigo;
	private String consumoMedida;
	private java.math.BigDecimal consumoCantidad;

	public ReporteConsumoPorPiscina() {
	}

	public ReporteConsumoPorPiscina(String sector, String piscina, String corrida, BigDecimal hectareas,
			Integer numLarvas, String desde, String hasta, String consumoProducto, String consumoCodigo,
			String consumoMedida, BigDecimal consumoCantidad) {
		this.sector = sector;
		this.piscina = piscina;
		this.corrida = corrida;
		this.hectareas = hectareas;
		this.numLarvas = numLarvas;
		this.desde = desde;
		this.hasta = hasta;
		this.consumoProducto = consumoProducto;
		this.consumoCodigo = consumoCodigo;
		this.consumoMedida = consumoMedida;
		this.consumoCantidad = consumoCantidad;
	}

	public BigDecimal getConsumoCantidad() {
		return consumoCantidad;
	}

	public void setConsumoCantidad(BigDecimal consumoCantidad) {
		this.consumoCantidad = consumoCantidad;
	}

	public String getConsumoCodigo() {
		return consumoCodigo;
	}

	public void setConsumoCodigo(String consumoCodigo) {
		this.consumoCodigo = consumoCodigo;
	}

	public String getConsumoMedida() {
		return consumoMedida;
	}

	public void setConsumoMedida(String consumoMedida) {
		this.consumoMedida = consumoMedida;
	}

	public String getConsumoProducto() {
		return consumoProducto;
	}

	public void setConsumoProducto(String consumoProducto) {
		this.consumoProducto = consumoProducto;
	}

	public String getCorrida() {
		return corrida;
	}

	public void setCorrida(String corrida) {
		this.corrida = corrida;
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

	public Integer getNumLarvas() {
		return numLarvas;
	}

	public void setNumLarvas(Integer numLarvas) {
		this.numLarvas = numLarvas;
	}

	public String getPiscina() {
		return piscina;
	}

	public void setPiscina(String piscina) {
		this.piscina = piscina;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
}

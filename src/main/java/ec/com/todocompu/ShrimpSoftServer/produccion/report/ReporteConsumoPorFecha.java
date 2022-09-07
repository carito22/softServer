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
public class ReporteConsumoPorFecha {
	///// CABECERA
	private String sector;
	private String desde;
	private String hasta;

	///// DETALLE
	private String consumoProducto;
	private String consumoCodigo;
	private String consumoMedida;
	private java.math.BigDecimal consumoCantidad;

	public ReporteConsumoPorFecha() {
	}

	public ReporteConsumoPorFecha(String sector, String desde, String hasta, String consumoProducto,
			String consumoCodigo, String consumoMedida, BigDecimal consumoCantidad) {
		this.sector = sector;
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
}

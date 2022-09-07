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
public class ReporteCostoDetalleFecha {
	private String desde;
	private String hasta;

	///// DETALLE
	private String costoProducto;
	private String costoCodigo;
	private String costoMedida;
	private java.math.BigDecimal costoCantidad;
	private java.math.BigDecimal costoTotal;
	private java.math.BigDecimal costoPorcentaje;

	public ReporteCostoDetalleFecha() {
	}

	public ReporteCostoDetalleFecha(String desde, String hasta, String costoProducto, String costoCodigo,
			String costoMedida, BigDecimal costoCantidad, BigDecimal costoTotal, BigDecimal costoPorcentaje) {
		this.desde = desde;
		this.hasta = hasta;
		this.costoProducto = costoProducto;
		this.costoCodigo = costoCodigo;
		this.costoMedida = costoMedida;
		this.costoCantidad = costoCantidad;
		this.costoTotal = costoTotal;
		this.costoPorcentaje = costoPorcentaje;
	}

	public BigDecimal getCostoCantidad() {
		return costoCantidad;
	}

	public void setCostoCantidad(BigDecimal costoCantidad) {
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

	public BigDecimal getCostoPorcentaje() {
		return costoPorcentaje;
	}

	public void setCostoPorcentaje(BigDecimal costoPorcentaje) {
		this.costoPorcentaje = costoPorcentaje;
	}

	public String getCostoProducto() {
		return costoProducto;
	}

	public void setCostoProducto(String costoProducto) {
		this.costoProducto = costoProducto;
	}

	public BigDecimal getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(BigDecimal costoTotal) {
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
}

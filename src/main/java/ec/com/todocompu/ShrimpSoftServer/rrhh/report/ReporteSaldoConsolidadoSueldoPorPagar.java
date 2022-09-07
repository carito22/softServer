/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteSaldoConsolidadoSueldoPorPagar {
	private String fechaHasta;

	///// detalle
	private String scspCategoria;
	private String scspId;
	private String scspNombres;
	private BigDecimal scspValor;
	private String scspOrden;

	public ReporteSaldoConsolidadoSueldoPorPagar() {
	}

	public ReporteSaldoConsolidadoSueldoPorPagar(String fechaHasta, String scspCategoria, String scspId,
			String scspNombres, BigDecimal scspValor, String scspOrden) {
		this.fechaHasta = fechaHasta;
		this.scspCategoria = scspCategoria;
		this.scspId = scspId;
		this.scspNombres = scspNombres;
		this.scspValor = scspValor;
		this.scspOrden = scspOrden;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getScspCategoria() {
		return scspCategoria;
	}

	public void setScspCategoria(String scspCategoria) {
		this.scspCategoria = scspCategoria;
	}

	public String getScspId() {
		return scspId;
	}

	public void setScspId(String scspId) {
		this.scspId = scspId;
	}

	public String getScspNombres() {
		return scspNombres;
	}

	public void setScspNombres(String scspNombres) {
		this.scspNombres = scspNombres;
	}

	public String getScspOrden() {
		return scspOrden;
	}

	public void setScspOrden(String scspOrden) {
		this.scspOrden = scspOrden;
	}

	public BigDecimal getScspValor() {
		return scspValor;
	}

	public void setScspValor(BigDecimal scspValor) {
		this.scspValor = scspValor;
	}
}

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
public class ReporteSaldoConsolidadoAnticipoPrestamo {
	private String fechaHasta;

	///////// detalle
	private String scapCategoria;
	private String scapId;
	private String scapNombres;
	private BigDecimal scapAnticipos;
	private BigDecimal scapPrestamos;
	private BigDecimal scapTotal;
	private String scapOrden;

	public ReporteSaldoConsolidadoAnticipoPrestamo() {
	}

	public ReporteSaldoConsolidadoAnticipoPrestamo(String fechaHasta, String scapCategoria, String scapId,
			String scapNombres, BigDecimal scapAnticipos, BigDecimal scapPrestamos, BigDecimal scapTotal,
			String scapOrden) {
		this.fechaHasta = fechaHasta;
		this.scapCategoria = scapCategoria;
		this.scapId = scapId;
		this.scapNombres = scapNombres;
		this.scapAnticipos = scapAnticipos;
		this.scapPrestamos = scapPrestamos;
		this.scapTotal = scapTotal;
		this.scapOrden = scapOrden;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public BigDecimal getScapAnticipos() {
		return scapAnticipos;
	}

	public void setScapAnticipos(BigDecimal scapAnticipos) {
		this.scapAnticipos = scapAnticipos;
	}

	public String getScapCategoria() {
		return scapCategoria;
	}

	public void setScapCategoria(String scapCategoria) {
		this.scapCategoria = scapCategoria;
	}

	public String getScapId() {
		return scapId;
	}

	public void setScapId(String scapId) {
		this.scapId = scapId;
	}

	public String getScapNombres() {
		return scapNombres;
	}

	public void setScapNombres(String scapNombres) {
		this.scapNombres = scapNombres;
	}

	public String getScapOrden() {
		return scapOrden;
	}

	public void setScapOrden(String scapOrden) {
		this.scapOrden = scapOrden;
	}

	public BigDecimal getScapPrestamos() {
		return scapPrestamos;
	}

	public void setScapPrestamos(BigDecimal scapPrestamos) {
		this.scapPrestamos = scapPrestamos;
	}

	public BigDecimal getScapTotal() {
		return scapTotal;
	}

	public void setScapTotal(BigDecimal scapTotal) {
		this.scapTotal = scapTotal;
	}
}

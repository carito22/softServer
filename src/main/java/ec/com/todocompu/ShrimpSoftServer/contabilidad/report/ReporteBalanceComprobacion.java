/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteBalanceComprobacion {
	private String fechaDesde;
	private String fechaHasta;
	private String bcCuenta;
	private String bcDetalle;
	private java.math.BigDecimal bcSaldoAnterior;
	private java.math.BigDecimal bcTotalDebito;
	private java.math.BigDecimal bcTotalCredito;
	private java.math.BigDecimal bcSaldoFinal;

	public ReporteBalanceComprobacion() {
	}

	public ReporteBalanceComprobacion(String fechaDesde, String fechaHasta, String bcCuenta, String bcDetalle,
			BigDecimal bcSaldoAnterior, BigDecimal bcTotalDebito, BigDecimal bcTotalCredito, BigDecimal bcSaldoFinal) {
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.bcCuenta = bcCuenta;
		this.bcDetalle = bcDetalle;
		this.bcSaldoAnterior = bcSaldoAnterior;
		this.bcTotalDebito = bcTotalDebito;
		this.bcTotalCredito = bcTotalCredito;
		this.bcSaldoFinal = bcSaldoFinal;
	}

	public String getBcCuenta() {
		return bcCuenta;
	}

	public void setBcCuenta(String bcCuenta) {
		this.bcCuenta = bcCuenta;
	}

	public String getBcDetalle() {
		return bcDetalle;
	}

	public void setBcDetalle(String bcDetalle) {
		this.bcDetalle = bcDetalle;
	}

	public BigDecimal getBcSaldoAnterior() {
		return bcSaldoAnterior;
	}

	public void setBcSaldoAnterior(BigDecimal bcSaldoAnterior) {
		this.bcSaldoAnterior = bcSaldoAnterior;
	}

	public BigDecimal getBcSaldoFinal() {
		return bcSaldoFinal;
	}

	public void setBcSaldoFinal(BigDecimal bcSaldoFinal) {
		this.bcSaldoFinal = bcSaldoFinal;
	}

	public BigDecimal getBcTotalCredito() {
		return bcTotalCredito;
	}

	public void setBcTotalCredito(BigDecimal bcTotalCredito) {
		this.bcTotalCredito = bcTotalCredito;
	}

	public BigDecimal getBcTotalDebito() {
		return bcTotalDebito;
	}

	public void setBcTotalDebito(BigDecimal bcTotalDebito) {
		this.bcTotalDebito = bcTotalDebito;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
}

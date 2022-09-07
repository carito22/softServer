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
public class ReporteBalanceGeneralComparativo {
	private String codigoCP;
	private String fechaAnterior;
	private String fechaActual;
	private String bgCuenta;
	private String bgDetalle;
	private java.math.BigDecimal bgSaldoAnterior;
	private java.math.BigDecimal bgSaldoActual;

	public ReporteBalanceGeneralComparativo() {
	}

	public ReporteBalanceGeneralComparativo(String codigoCP, String fechaAnterior, String fechaActual, String bgCuenta,
			String bgDetalle, BigDecimal bgSaldoAnterior, BigDecimal bgSaldoActual) {
		this.codigoCP = codigoCP;
		this.fechaAnterior = fechaAnterior;
		this.fechaActual = fechaActual;
		this.bgCuenta = bgCuenta;
		this.bgDetalle = bgDetalle;
		this.bgSaldoAnterior = bgSaldoAnterior;
		this.bgSaldoActual = bgSaldoActual;
	}

	public String getBgCuenta() {
		return bgCuenta;
	}

	public void setBgCuenta(String bgCuenta) {
		this.bgCuenta = bgCuenta;
	}

	public String getBgDetalle() {
		return bgDetalle;
	}

	public void setBgDetalle(String bgDetalle) {
		this.bgDetalle = bgDetalle;
	}

	public BigDecimal getBgSaldoActual() {
		return bgSaldoActual;
	}

	public void setBgSaldoActual(BigDecimal bgSaldoActual) {
		this.bgSaldoActual = bgSaldoActual;
	}

	public BigDecimal getBgSaldoAnterior() {
		return bgSaldoAnterior;
	}

	public void setBgSaldoAnterior(BigDecimal bgSaldoAnterior) {
		this.bgSaldoAnterior = bgSaldoAnterior;
	}

	public String getCodigoCP() {
		return codigoCP;
	}

	public void setCodigoCP(String codigoCP) {
		this.codigoCP = codigoCP;
	}

	public String getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(String fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getFechaAnterior() {
		return fechaAnterior;
	}

	public void setFechaAnterior(String fechaAnterior) {
		this.fechaAnterior = fechaAnterior;
	}

}

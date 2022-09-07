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
public class ReporteBalanceResultado {
	private String codigoCP;
	private String fechaDesde;
	private String fechaHasta;
	private String bgCuenta;
	private String bgDetalle;
	private java.math.BigDecimal bgSaldo;

	public ReporteBalanceResultado() {
	}

	public ReporteBalanceResultado(String codigoCP, String fechaDesde, String fechaHasta, String bgCuenta,
			String bgDetalle, BigDecimal bgSaldo) {
		this.codigoCP = codigoCP;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.bgCuenta = bgCuenta;
		this.bgDetalle = bgDetalle;
		this.bgSaldo = bgSaldo;
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

	public BigDecimal getBgSaldo() {
		return bgSaldo;
	}

	public void setBgSaldo(BigDecimal bgSaldo) {
		this.bgSaldo = bgSaldo;
	}

	public String getCodigoCP() {
		return codigoCP;
	}

	public void setCodigoCP(String codigoCP) {
		this.codigoCP = codigoCP;
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

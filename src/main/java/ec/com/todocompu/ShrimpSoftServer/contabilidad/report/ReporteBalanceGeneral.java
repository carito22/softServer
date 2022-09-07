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
public class ReporteBalanceGeneral {
	private String codigoCP;
	private String fechaHasta;
	private String bgCuenta;
	private String bgDetalle;
	private java.math.BigDecimal bgSaldo1;
	private java.math.BigDecimal bgSaldo2;
	private java.math.BigDecimal bgSaldo3;
	private java.math.BigDecimal bgSaldo4;
	private java.math.BigDecimal bgSaldo5;
	private java.math.BigDecimal bgSaldo6;

	public ReporteBalanceGeneral() {
	}

	public ReporteBalanceGeneral(String codigoCP, String fechaHasta, String bgCuenta, String bgDetalle,
			BigDecimal bgSaldo1, BigDecimal bgSaldo2, BigDecimal bgSaldo3, BigDecimal bgSaldo4, BigDecimal bgSaldo5,
			BigDecimal bgSaldo6) {
		this.codigoCP = codigoCP;
		this.fechaHasta = fechaHasta;
		this.bgCuenta = bgCuenta;
		this.bgDetalle = bgDetalle;
		this.bgSaldo1 = bgSaldo1;
		this.bgSaldo2 = bgSaldo2;
		this.bgSaldo3 = bgSaldo3;
		this.bgSaldo4 = bgSaldo4;
		this.bgSaldo5 = bgSaldo5;
		this.bgSaldo6 = bgSaldo6;
	}

	public String getCodigoCP() {
		return codigoCP;
	}

	public void setCodigoCP(String codigoCP) {
		this.codigoCP = codigoCP;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
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

	public BigDecimal getBgSaldo1() {
		return bgSaldo1;
	}

	public void setBgSaldo1(BigDecimal bgSaldo1) {
		this.bgSaldo1 = bgSaldo1;
	}

	public BigDecimal getBgSaldo2() {
		return bgSaldo2;
	}

	public void setBgSaldo2(BigDecimal bgSaldo2) {
		this.bgSaldo2 = bgSaldo2;
	}

	public BigDecimal getBgSaldo3() {
		return bgSaldo3;
	}

	public void setBgSaldo3(BigDecimal bgSaldo3) {
		this.bgSaldo3 = bgSaldo3;
	}

	public BigDecimal getBgSaldo4() {
		return bgSaldo4;
	}

	public void setBgSaldo4(BigDecimal bgSaldo4) {
		this.bgSaldo4 = bgSaldo4;
	}

	public BigDecimal getBgSaldo5() {
		return bgSaldo5;
	}

	public void setBgSaldo5(BigDecimal bgSaldo5) {
		this.bgSaldo5 = bgSaldo5;
	}

	public BigDecimal getBgSaldo6() {
		return bgSaldo6;
	}

	public void setBgSaldo6(BigDecimal bgSaldo6) {
		this.bgSaldo6 = bgSaldo6;
	}

}

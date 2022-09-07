/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */

public class ReporteSaldoBodegaComprobacion {
	private String bodega;
	private String desde;
	private String hasta;

	////// DETALLE
	private String sbcProductoNombre;
	private String sbcProductoCodigo;
	private String sbcMedida;
	private java.math.BigDecimal sbcInicial;
	private java.math.BigDecimal sbcCompra;
	private java.math.BigDecimal sbcVenta;
	private java.math.BigDecimal sbcConsumo;
	private java.math.BigDecimal sbcTransferenciaI;
	private java.math.BigDecimal sbcTransferenciaE;
	private java.math.BigDecimal sbcDevolucionC;
	private java.math.BigDecimal sbcDevolucionV;
	private java.math.BigDecimal sbcFinal;
	private java.math.BigDecimal sbcDiferencia;

	public ReporteSaldoBodegaComprobacion() {
	}

	public ReporteSaldoBodegaComprobacion(String bodega, String desde, String hasta, String sbcProductoNombre,
			String sbcProductoCodigo, String sbcMedida, BigDecimal sbcInicial, BigDecimal sbcCompra,
			BigDecimal sbcVenta, BigDecimal sbcConsumo, BigDecimal sbcTransferenciaI, BigDecimal sbcTransferenciaE,
			BigDecimal sbcDevolucionC, BigDecimal sbcDevolucionV, BigDecimal sbcFinal, BigDecimal sbcDiferencia) {
		this.bodega = bodega;
		this.desde = desde;
		this.hasta = hasta;
		this.sbcProductoNombre = sbcProductoNombre;
		this.sbcProductoCodigo = sbcProductoCodigo;
		this.sbcMedida = sbcMedida;
		this.sbcInicial = sbcInicial;
		this.sbcCompra = sbcCompra;
		this.sbcVenta = sbcVenta;
		this.sbcConsumo = sbcConsumo;
		this.sbcTransferenciaI = sbcTransferenciaI;
		this.sbcTransferenciaE = sbcTransferenciaE;
		this.sbcDevolucionC = sbcDevolucionC;
		this.sbcDevolucionV = sbcDevolucionV;
		this.sbcFinal = sbcFinal;
		this.sbcDiferencia = sbcDiferencia;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public BigDecimal getSbcCompra() {
		return sbcCompra;
	}

	public void setSbcCompra(BigDecimal sbcCompra) {
		this.sbcCompra = sbcCompra;
	}

	public BigDecimal getSbcConsumo() {
		return sbcConsumo;
	}

	public void setSbcConsumo(BigDecimal sbcConsumo) {
		this.sbcConsumo = sbcConsumo;
	}

	public BigDecimal getSbcDevolucionC() {
		return sbcDevolucionC;
	}

	public void setSbcDevolucionC(BigDecimal sbcDevolucionC) {
		this.sbcDevolucionC = sbcDevolucionC;
	}

	public BigDecimal getSbcDevolucionV() {
		return sbcDevolucionV;
	}

	public void setSbcDevolucionV(BigDecimal sbcDevolucionV) {
		this.sbcDevolucionV = sbcDevolucionV;
	}

	public BigDecimal getSbcDiferencia() {
		return sbcDiferencia;
	}

	public void setSbcDiferencia(BigDecimal sbcDiferencia) {
		this.sbcDiferencia = sbcDiferencia;
	}

	public BigDecimal getSbcFinal() {
		return sbcFinal;
	}

	public void setSbcFinal(BigDecimal sbcFinal) {
		this.sbcFinal = sbcFinal;
	}

	public BigDecimal getSbcInicial() {
		return sbcInicial;
	}

	public void setSbcInicial(BigDecimal sbcInicial) {
		this.sbcInicial = sbcInicial;
	}

	public String getSbcMedida() {
		return sbcMedida;
	}

	public void setSbcMedida(String sbcMedida) {
		this.sbcMedida = sbcMedida;
	}

	public String getSbcProductoCodigo() {
		return sbcProductoCodigo;
	}

	public void setSbcProductoCodigo(String sbcProductoCodigo) {
		this.sbcProductoCodigo = sbcProductoCodigo;
	}

	public String getSbcProductoNombre() {
		return sbcProductoNombre;
	}

	public void setSbcProductoNombre(String sbcProductoNombre) {
		this.sbcProductoNombre = sbcProductoNombre;
	}

	public BigDecimal getSbcTransferenciaE() {
		return sbcTransferenciaE;
	}

	public void setSbcTransferenciaE(BigDecimal sbcTransferenciaE) {
		this.sbcTransferenciaE = sbcTransferenciaE;
	}

	public BigDecimal getSbcTransferenciaI() {
		return sbcTransferenciaI;
	}

	public void setSbcTransferenciaI(BigDecimal sbcTransferenciaI) {
		this.sbcTransferenciaI = sbcTransferenciaI;
	}

	public BigDecimal getSbcVenta() {
		return sbcVenta;
	}

	public void setSbcVenta(BigDecimal sbcVenta) {
		this.sbcVenta = sbcVenta;
	}

}

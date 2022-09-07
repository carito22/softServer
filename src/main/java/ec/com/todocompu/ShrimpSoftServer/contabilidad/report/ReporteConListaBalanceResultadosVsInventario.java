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
public class ReporteConListaBalanceResultadosVsInventario {

	private String desde;
	private String hasta;
	//////////////// DETALLE
	private String vriCuentaContable;
	private String vriNombre;
	private BigDecimal vriSaldoContable;
	private BigDecimal vriInventarioInicial;
	private BigDecimal vriDiferencia;

	public ReporteConListaBalanceResultadosVsInventario() {
	}

	public ReporteConListaBalanceResultadosVsInventario(String desde, String hasta, String vriCuentaContable,
			String vriNombre, BigDecimal vriSaldoContable, BigDecimal vriInventarioInicial, BigDecimal vriDiferencia) {
		this.desde = desde;
		this.hasta = hasta;
		this.vriCuentaContable = vriCuentaContable;
		this.vriNombre = vriNombre;
		this.vriSaldoContable = vriSaldoContable;
		this.vriInventarioInicial = vriInventarioInicial;
		this.vriDiferencia = vriDiferencia;
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

	public String getVriCuentaContable() {
		return vriCuentaContable;
	}

	public void setVriCuentaContable(String vriCuentaContable) {
		this.vriCuentaContable = vriCuentaContable;
	}

	public BigDecimal getVriDiferencia() {
		return vriDiferencia;
	}

	public void setVriDiferencia(BigDecimal vriDiferencia) {
		this.vriDiferencia = vriDiferencia;
	}

	public BigDecimal getVriInventarioInicial() {
		return vriInventarioInicial;
	}

	public void setVriInventarioInicial(BigDecimal vriInventarioInicial) {
		this.vriInventarioInicial = vriInventarioInicial;
	}

	public String getVriNombre() {
		return vriNombre;
	}

	public void setVriNombre(String vriNombre) {
		this.vriNombre = vriNombre;
	}

	public BigDecimal getVriSaldoContable() {
		return vriSaldoContable;
	}

	public void setVriSaldoContable(BigDecimal vriSaldoContable) {
		this.vriSaldoContable = vriSaldoContable;
	}
}

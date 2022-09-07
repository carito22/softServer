/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.banco.report;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteListadoChequeEmisionVencimientoCobrarNumero {
	///// CABECERA
	private String cuentaBancaria;
	private String desde;
	private String hasta;
	private String tipo;

	///// DETALLE
	private String chqCuentaCodigo;
	private String chqCuentaDetalle;
	private String chqBeneficiario;
	private String chqNumero;
	private java.math.BigDecimal chqValor;
	private String chqFechaEmision;
	private String chqFechaVencimiento;
	private String chqContablePeriodo;
	private String chqContableTipo;
	private String chqContableNumero;
	private java.math.BigInteger chqSecuencia;
	private java.math.BigInteger chqOrden;

	public ReporteListadoChequeEmisionVencimientoCobrarNumero() {
	}

	public ReporteListadoChequeEmisionVencimientoCobrarNumero(String cuentaBancaria, String desde, String hasta,
			String tipo, String chqCuentaCodigo, String chqCuentaDetalle, String chqBeneficiario, String chqNumero,
			BigDecimal chqValor, String chqFechaEmision, String chqFechaVencimiento, String chqContablePeriodo,
			String chqContableTipo, String chqContableNumero, BigInteger chqSecuencia, BigInteger chqOrden) {
		this.cuentaBancaria = cuentaBancaria;
		this.desde = desde;
		this.hasta = hasta;
		this.tipo = tipo;
		this.chqCuentaCodigo = chqCuentaCodigo;
		this.chqCuentaDetalle = chqCuentaDetalle;
		this.chqBeneficiario = chqBeneficiario;
		this.chqNumero = chqNumero;
		this.chqValor = chqValor;
		this.chqFechaEmision = chqFechaEmision;
		this.chqFechaVencimiento = chqFechaVencimiento;
		this.chqContablePeriodo = chqContablePeriodo;
		this.chqContableTipo = chqContableTipo;
		this.chqContableNumero = chqContableNumero;
		this.chqSecuencia = chqSecuencia;
		this.chqOrden = chqOrden;
	}

	public String getChqBeneficiario() {
		return chqBeneficiario;
	}

	public void setChqBeneficiario(String chqBeneficiario) {
		this.chqBeneficiario = chqBeneficiario;
	}

	public String getChqContableNumero() {
		return chqContableNumero;
	}

	public void setChqContableNumero(String chqContableNumero) {
		this.chqContableNumero = chqContableNumero;
	}

	public String getChqContablePeriodo() {
		return chqContablePeriodo;
	}

	public void setChqContablePeriodo(String chqContablePeriodo) {
		this.chqContablePeriodo = chqContablePeriodo;
	}

	public String getChqContableTipo() {
		return chqContableTipo;
	}

	public void setChqContableTipo(String chqContableTipo) {
		this.chqContableTipo = chqContableTipo;
	}

	public String getChqCuentaCodigo() {
		return chqCuentaCodigo;
	}

	public void setChqCuentaCodigo(String chqCuentaCodigo) {
		this.chqCuentaCodigo = chqCuentaCodigo;
	}

	public String getChqCuentaDetalle() {
		return chqCuentaDetalle;
	}

	public void setChqCuentaDetalle(String chqCuentaDetalle) {
		this.chqCuentaDetalle = chqCuentaDetalle;
	}

	public String getChqFechaEmision() {
		return chqFechaEmision;
	}

	public void setChqFechaEmision(String chqFechaEmision) {
		this.chqFechaEmision = chqFechaEmision;
	}

	public String getChqFechaVencimiento() {
		return chqFechaVencimiento;
	}

	public void setChqFechaVencimiento(String chqFechaVencimiento) {
		this.chqFechaVencimiento = chqFechaVencimiento;
	}

	public String getChqNumero() {
		return chqNumero;
	}

	public void setChqNumero(String chqNumero) {
		this.chqNumero = chqNumero;
	}

	public BigInteger getChqOrden() {
		return chqOrden;
	}

	public void setChqOrden(BigInteger chqOrden) {
		this.chqOrden = chqOrden;
	}

	public BigInteger getChqSecuencia() {
		return chqSecuencia;
	}

	public void setChqSecuencia(BigInteger chqSecuencia) {
		this.chqSecuencia = chqSecuencia;
	}

	public BigDecimal getChqValor() {
		return chqValor;
	}

	public void setChqValor(BigDecimal chqValor) {
		this.chqValor = chqValor;
	}

	public String getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}

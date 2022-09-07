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
public class ReporteProvisionesComprobanteContable {
	private String periodo;
	private String tipo;
	private String numero;

	///// DETALLE
	private String provId;
	private String provNombres;
	private BigDecimal provSueldo;
	private BigDecimal provDiasPagados;
	private java.math.BigDecimal provAporteIndividual;
	private java.math.BigDecimal provAportePatronal;
	private java.math.BigDecimal provIece;
	private java.math.BigDecimal provSecap;
	private java.math.BigDecimal provXiii;
	private java.math.BigDecimal provXiv;
	private java.math.BigDecimal provFondoReserva;
	private java.math.BigDecimal provVacaciones;
	private java.math.BigDecimal provDesahucio;
	private String provContableRol;
	private String provContableProvision;

	public ReporteProvisionesComprobanteContable() {
	}

	public ReporteProvisionesComprobanteContable(String periodo, String tipo, String numero, String provId,
			String provNombres, BigDecimal provSueldo, BigDecimal provDiasPagados, BigDecimal provAporteIndividual,
			BigDecimal provAportePatronal, BigDecimal provIece, BigDecimal provSecap, BigDecimal provXiii,
			BigDecimal provXiv, BigDecimal provFondoReserva, BigDecimal provVacaciones, BigDecimal provDesahucio,
			String provContableRol, String provContableProvision) {
		this.periodo = periodo;
		this.tipo = tipo;
		this.numero = numero;
		this.provId = provId;
		this.provNombres = provNombres;
		this.provSueldo = provSueldo;
		this.provDiasPagados = provDiasPagados;
		this.provAporteIndividual = provAporteIndividual;
		this.provAportePatronal = provAportePatronal;
		this.provIece = provIece;
		this.provSecap = provSecap;
		this.provXiii = provXiii;
		this.provXiv = provXiv;
		this.provFondoReserva = provFondoReserva;
		this.provVacaciones = provVacaciones;
		this.provDesahucio = provDesahucio;
		this.provContableRol = provContableRol;
		this.provContableProvision = provContableProvision;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getProvAporteIndividual() {
		return provAporteIndividual;
	}

	public void setProvAporteIndividual(BigDecimal provAporteIndividual) {
		this.provAporteIndividual = provAporteIndividual;
	}

	public BigDecimal getProvAportePatronal() {
		return provAportePatronal;
	}

	public void setProvAportePatronal(BigDecimal provAportePatronal) {
		this.provAportePatronal = provAportePatronal;
	}

	public String getProvContableProvision() {
		return provContableProvision;
	}

	public void setProvContableProvision(String provContableProvision) {
		this.provContableProvision = provContableProvision;
	}

	public String getProvContableRol() {
		return provContableRol;
	}

	public void setProvContableRol(String provContableRol) {
		this.provContableRol = provContableRol;
	}

	public BigDecimal getProvDesahucio() {
		return provDesahucio;
	}

	public void setProvDesahucio(BigDecimal provDesahucio) {
		this.provDesahucio = provDesahucio;
	}

	public BigDecimal getProvDiasPagados() {
		return provDiasPagados;
	}

	public void setProvDiasPagados(BigDecimal provDiasPagados) {
		this.provDiasPagados = provDiasPagados;
	}

	public BigDecimal getProvFondoReserva() {
		return provFondoReserva;
	}

	public void setProvFondoReserva(BigDecimal provFondoReserva) {
		this.provFondoReserva = provFondoReserva;
	}

	public String getProvId() {
		return provId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public BigDecimal getProvIece() {
		return provIece;
	}

	public void setProvIece(BigDecimal provIece) {
		this.provIece = provIece;
	}

	public String getProvNombres() {
		return provNombres;
	}

	public void setProvNombres(String provNombres) {
		this.provNombres = provNombres;
	}

	public BigDecimal getProvSecap() {
		return provSecap;
	}

	public void setProvSecap(BigDecimal provSecap) {
		this.provSecap = provSecap;
	}

	public BigDecimal getProvSueldo() {
		return provSueldo;
	}

	public void setProvSueldo(BigDecimal provSueldo) {
		this.provSueldo = provSueldo;
	}

	public BigDecimal getProvVacaciones() {
		return provVacaciones;
	}

	public void setProvVacaciones(BigDecimal provVacaciones) {
		this.provVacaciones = provVacaciones;
	}

	public BigDecimal getProvXiii() {
		return provXiii;
	}

	public void setProvXiii(BigDecimal provXiii) {
		this.provXiii = provXiii;
	}

	public BigDecimal getProvXiv() {
		return provXiv;
	}

	public void setProvXiv(BigDecimal provXiv) {
		this.provXiv = provXiv;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}

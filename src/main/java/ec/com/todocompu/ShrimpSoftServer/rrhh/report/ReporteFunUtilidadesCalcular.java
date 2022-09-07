/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import java.math.BigDecimal;

/**
 *
 * @author developer2
 */
public class ReporteFunUtilidadesCalcular {
	// CABECERA
	private String sector;
	private String periodo;
	private String fechaDesde;
	private String fechaHasta;
	private String fechaMaxima;
	// DETALLE
	private String xivId;
	private String xivNombres;
	private String xivApellidos;
	private Character xivGenero;
	private String xivFechaIngreso;
	private String xivFechaSalida;
	private String xivCargo;
	private Integer xivCargasFamiliares;
	private Integer xivDiasLaborados;
	private java.math.BigDecimal xivValorPersonal;
	private java.math.BigDecimal xivValorCargas;
	private java.math.BigDecimal xivValorUtilidades;
	private java.math.BigDecimal xivValorSueldos;
	private java.math.BigDecimal xivValorBonos;
	private java.math.BigDecimal xivValorXiii;
	private java.math.BigDecimal xivValorXiv;
	private java.math.BigDecimal xivValorFondoReserva;
	private java.math.BigDecimal xivValorSalarioDigno;
	private java.math.BigDecimal xivValorUtilidadAnterior;
	private java.math.BigDecimal xivValorImpuesto;
	private String xivCategoria;
	private String xivCuenta;
	private String xivSector;
	private boolean estado;

	public ReporteFunUtilidadesCalcular() {

	}

	public ReporteFunUtilidadesCalcular(String sector, String periodo, String fechaDesde, String fechaHasta,
			String fechaMaxima, String xivId, String xivNombres, String xivApellidos, Character xivGenero,
			String xivFechaIngreso, String xivFechaSalida, String xivCargo, Integer xivCargasFamiliares,
			Integer xivDiasLaborados, BigDecimal xivValorPersonal, BigDecimal xivValorCargas,
			BigDecimal xivValorxivlidades, BigDecimal xivValorSueldos, BigDecimal xivValorBonos,
			BigDecimal xivValorXiii, BigDecimal xivValorXiv, BigDecimal xivValorFondoReserva,
			BigDecimal xivValorSalarioDigno, BigDecimal xivValorxivlidadAnterior, BigDecimal xivValorImpuesto,
			String xivCategoria, String xivCuenta, String xivSector, boolean estado) {
		this.sector = sector;
		this.periodo = periodo;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.fechaMaxima = fechaMaxima;
		this.xivId = xivId;
		this.xivNombres = xivNombres;
		this.xivApellidos = xivApellidos;
		this.xivGenero = xivGenero;
		this.xivFechaIngreso = xivFechaIngreso;
		this.xivFechaSalida = xivFechaSalida;
		this.xivCargo = xivCargo;
		this.xivCargasFamiliares = xivCargasFamiliares;
		this.xivDiasLaborados = xivDiasLaborados;
		this.xivValorPersonal = xivValorPersonal;
		this.xivValorCargas = xivValorCargas;
		this.xivValorUtilidades = xivValorxivlidades;
		this.xivValorSueldos = xivValorSueldos;
		this.xivValorBonos = xivValorBonos;
		this.xivValorXiii = xivValorXiii;
		this.xivValorXiv = xivValorXiv;
		this.xivValorFondoReserva = xivValorFondoReserva;
		this.xivValorSalarioDigno = xivValorSalarioDigno;
		this.xivValorUtilidadAnterior = xivValorxivlidadAnterior;
		this.xivValorImpuesto = xivValorImpuesto;
		this.xivCategoria = xivCategoria;
		this.xivCuenta = xivCuenta;
		this.xivSector = xivSector;
		this.estado = estado;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
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

	public String getFechaMaxima() {
		return fechaMaxima;
	}

	public void setFechaMaxima(String fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getXivApellidos() {
		return xivApellidos;
	}

	public void setXivApellidos(String xivApellidos) {
		this.xivApellidos = xivApellidos;
	}

	public Integer getXivCargasFamiliares() {
		return xivCargasFamiliares;
	}

	public void setXivCargasFamiliares(Integer xivCargasFamiliares) {
		this.xivCargasFamiliares = xivCargasFamiliares;
	}

	public String getXivCargo() {
		return xivCargo;
	}

	public void setXivCargo(String xivCargo) {
		this.xivCargo = xivCargo;
	}

	public String getXivCategoria() {
		return xivCategoria;
	}

	public void setXivCategoria(String xivCategoria) {
		this.xivCategoria = xivCategoria;
	}

	public String getXivCuenta() {
		return xivCuenta;
	}

	public void setXivCuenta(String xivCuenta) {
		this.xivCuenta = xivCuenta;
	}

	public Integer getXivDiasLaborados() {
		return xivDiasLaborados;
	}

	public void setXivDiasLaborados(Integer xivDiasLaborados) {
		this.xivDiasLaborados = xivDiasLaborados;
	}

	public String getXivFechaIngreso() {
		return xivFechaIngreso;
	}

	public void setXivFechaIngreso(String xivFechaIngreso) {
		this.xivFechaIngreso = xivFechaIngreso;
	}

	public String getXivFechaSalida() {
		return xivFechaSalida;
	}

	public void setXivFechaSalida(String xivFechaSalida) {
		this.xivFechaSalida = xivFechaSalida;
	}

	public Character getXivGenero() {
		return xivGenero;
	}

	public void setXivGenero(Character xivGenero) {
		this.xivGenero = xivGenero;
	}

	public String getXivId() {
		return xivId;
	}

	public void setXivId(String xivId) {
		this.xivId = xivId;
	}

	public String getXivNombres() {
		return xivNombres;
	}

	public void setXivNombres(String xivNombres) {
		this.xivNombres = xivNombres;
	}

	public String getXivSector() {
		return xivSector;
	}

	public void setXivSector(String xivSector) {
		this.xivSector = xivSector;
	}

	public BigDecimal getXivValorBonos() {
		return xivValorBonos;
	}

	public void setXivValorBonos(BigDecimal xivValorBonos) {
		this.xivValorBonos = xivValorBonos;
	}

	public BigDecimal getXivValorCargas() {
		return xivValorCargas;
	}

	public void setXivValorCargas(BigDecimal xivValorCargas) {
		this.xivValorCargas = xivValorCargas;
	}

	public BigDecimal getXivValorFondoReserva() {
		return xivValorFondoReserva;
	}

	public void setXivValorFondoReserva(BigDecimal xivValorFondoReserva) {
		this.xivValorFondoReserva = xivValorFondoReserva;
	}

	public BigDecimal getXivValorImpuesto() {
		return xivValorImpuesto;
	}

	public void setXivValorImpuesto(BigDecimal xivValorImpuesto) {
		this.xivValorImpuesto = xivValorImpuesto;
	}

	public BigDecimal getXivValorPersonal() {
		return xivValorPersonal;
	}

	public void setXivValorPersonal(BigDecimal xivValorPersonal) {
		this.xivValorPersonal = xivValorPersonal;
	}

	public BigDecimal getXivValorSalarioDigno() {
		return xivValorSalarioDigno;
	}

	public void setXivValorSalarioDigno(BigDecimal xivValorSalarioDigno) {
		this.xivValorSalarioDigno = xivValorSalarioDigno;
	}

	public BigDecimal getXivValorSueldos() {
		return xivValorSueldos;
	}

	public void setXivValorSueldos(BigDecimal xivValorSueldos) {
		this.xivValorSueldos = xivValorSueldos;
	}

	public BigDecimal getXivValorUtilidadAnterior() {
		return xivValorUtilidadAnterior;
	}

	public void setXivValorUtilidadAnterior(BigDecimal xivValorxivlidadAnterior) {
		this.xivValorUtilidadAnterior = xivValorxivlidadAnterior;
	}

	public BigDecimal getXivValorUtilidades() {
		return xivValorUtilidades;
	}

	public void setXivValorUtilidades(BigDecimal xivValorxivlidades) {
		this.xivValorUtilidades = xivValorxivlidades;
	}

	public BigDecimal getXivValorXiii() {
		return xivValorXiii;
	}

	public void setXivValorXiii(BigDecimal xivValorXiii) {
		this.xivValorXiii = xivValorXiii;
	}

	public BigDecimal getXivValorXiv() {
		return xivValorXiv;
	}

	public void setXivValorXiv(BigDecimal xivValorXiv) {
		this.xivValorXiv = xivValorXiv;
	}

}

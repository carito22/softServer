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
public class ReporteListaDetalleVacaionesPagadas {

	// AGREGADA AL SERVIDOR /J.
	private String desde;
	private String hasta;
	private String sector;
	private String empleado;

	private String vacId;
	private String vacApellidosNombres;
	private BigDecimal vacValor;
	private String vacDesde;
	private String vacHasta;
	private String vacGozadasDesde;
	private String vacGozadasHasta;
	private String vacObservaciones;
	private String vacContables;

	public ReporteListaDetalleVacaionesPagadas() {
	}

	public ReporteListaDetalleVacaionesPagadas(String desde, String hasta, String sector, String empleado, String vacId,
			String vacApellidosNombres, BigDecimal vacValor, String vacDesde, String vacHasta, String vacGozadasDesde,
			String vacGozadasHasta, String vacObservaciones, String vacContables) {
		this.desde = desde;
		this.hasta = hasta;
		this.sector = sector;
		this.empleado = empleado;
		this.vacId = vacId;
		this.vacApellidosNombres = vacApellidosNombres;
		this.vacValor = vacValor;
		this.vacDesde = vacDesde;
		this.vacHasta = vacHasta;
		this.vacGozadasDesde = vacGozadasDesde;
		this.vacGozadasHasta = vacGozadasHasta;
		this.vacObservaciones = vacObservaciones;
		this.vacContables = vacContables;
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

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getVacId() {
		return vacId;
	}

	public void setVacId(String vacId) {
		this.vacId = vacId;
	}

	public String getVacApellidosNombres() {
		return vacApellidosNombres;
	}

	public void setVacApellidosNombres(String vacApellidosNombres) {
		this.vacApellidosNombres = vacApellidosNombres;
	}

	public BigDecimal getVacValor() {
		return vacValor;
	}

	public void setVacValor(BigDecimal vacValor) {
		this.vacValor = vacValor;
	}

	public String getVacDesde() {
		return vacDesde;
	}

	public void setVacDesde(String vacDesde) {
		this.vacDesde = vacDesde;
	}

	public String getVacHasta() {
		return vacHasta;
	}

	public void setVacHasta(String vacHasta) {
		this.vacHasta = vacHasta;
	}

	public String getVacGozadasDesde() {
		return vacGozadasDesde;
	}

	public void setVacGozadasDesde(String vacGozadasDesde) {
		this.vacGozadasDesde = vacGozadasDesde;
	}

	public String getVacGozadasHasta() {
		return vacGozadasHasta;
	}

	public void setVacGozadasHasta(String vacGozadasHasta) {
		this.vacGozadasHasta = vacGozadasHasta;
	}

	public String getVacObservaciones() {
		return vacObservaciones;
	}

	public void setVacObservaciones(String vacObservaciones) {
		this.vacObservaciones = vacObservaciones;
	}

	public String getVacContables() {
		return vacContables;
	}

	public void setVacContables(String vacContables) {
		this.vacContables = vacContables;
	}
}

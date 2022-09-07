/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

import java.math.BigDecimal;

/**
 *
 * @author charly
 */
public class ReporteFunContablesVerificacionesCompras {
	private String fechaDesde;
	private String fechaHasta;
	private String contabilidadFecha;
	private String contabilidadSecuencial;
	private String inventarioFecha;
	private String inventarioSecuencial;
	private BigDecimal inventarioMonto;
	private String inventarioObservacion;

	public ReporteFunContablesVerificacionesCompras() {
	}

	public ReporteFunContablesVerificacionesCompras(String fechaDesde, String fechaHasta, String contabilidadFecha,
			String contabilidadSecuencial, String inventarioFecha, String inventarioSecuencial,
			BigDecimal inventarioMonto, String inventarioObservacion) {
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.contabilidadFecha = contabilidadFecha;
		this.contabilidadSecuencial = contabilidadSecuencial;
		this.inventarioFecha = inventarioFecha;
		this.inventarioSecuencial = inventarioSecuencial;
		this.inventarioMonto = inventarioMonto;
		this.inventarioObservacion = inventarioObservacion;
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

	public String getContabilidadFecha() {
		return contabilidadFecha;
	}

	public void setContabilidadFecha(String contabilidadFecha) {
		this.contabilidadFecha = contabilidadFecha;
	}

	public String getContabilidadSecuencial() {
		return contabilidadSecuencial;
	}

	public void setContabilidadSecuencial(String contabilidadSecuencial) {
		this.contabilidadSecuencial = contabilidadSecuencial;
	}

	public String getInventarioFecha() {
		return inventarioFecha;
	}

	public void setInventarioFecha(String inventarioFecha) {
		this.inventarioFecha = inventarioFecha;
	}

	public BigDecimal getInventarioMonto() {
		return inventarioMonto;
	}

	public void setInventarioMonto(BigDecimal inventarioMonto) {
		this.inventarioMonto = inventarioMonto;
	}

	public String getInventarioObservacion() {
		return inventarioObservacion;
	}

	public void setInventarioObservacion(String inventarioObservacion) {
		this.inventarioObservacion = inventarioObservacion;
	}

	public String getInventarioSecuencial() {
		return inventarioSecuencial;
	}

	public void setInventarioSecuencial(String inventarioSecuencial) {
		this.inventarioSecuencial = inventarioSecuencial;
	}
}

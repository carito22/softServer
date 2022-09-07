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
public class ReporteSaldoIndividualAnticiposPrestamos {
	private String desde;
	private String hasta;
	private String id;

	private String siapTipo;
	private String siapFecha;
	private String siapObservaciones;
	private BigDecimal siapDebitos;
	private BigDecimal siapCreditos;
	private BigDecimal siapSaldo;
	private String siapContable;
	private BigDecimal siapSecuencial;

	public ReporteSaldoIndividualAnticiposPrestamos() {
	}

	public ReporteSaldoIndividualAnticiposPrestamos(String desde, String hasta, String id, String siapTipo,
			String siapFecha, String siapObservaciones, BigDecimal siapDebitos, BigDecimal siapCreditos,
			BigDecimal siapSaldo, String siapContable, BigDecimal siapSecuencial) {
		this.desde = desde;
		this.hasta = hasta;
		this.id = id;
		this.siapTipo = siapTipo;
		this.siapFecha = siapFecha;
		this.siapObservaciones = siapObservaciones;
		this.siapDebitos = siapDebitos;
		this.siapCreditos = siapCreditos;
		this.siapSaldo = siapSaldo;
		this.siapContable = siapContable;
		this.siapSecuencial = siapSecuencial;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSiapContable() {
		return siapContable;
	}

	public void setSiapContable(String siapContable) {
		this.siapContable = siapContable;
	}

	public BigDecimal getSiapCreditos() {
		return siapCreditos;
	}

	public void setSiapCreditos(BigDecimal siapCreditos) {
		this.siapCreditos = siapCreditos;
	}

	public BigDecimal getSiapDebitos() {
		return siapDebitos;
	}

	public void setSiapDebitos(BigDecimal siapDebitos) {
		this.siapDebitos = siapDebitos;
	}

	public String getSiapFecha() {
		return siapFecha;
	}

	public void setSiapFecha(String siapFecha) {
		this.siapFecha = siapFecha;
	}

	public String getSiapObservaciones() {
		return siapObservaciones;
	}

	public void setSiapObservaciones(String siapObservaciones) {
		this.siapObservaciones = siapObservaciones;
	}

	public BigDecimal getSiapSaldo() {
		return siapSaldo;
	}

	public void setSiapSaldo(BigDecimal siapSaldo) {
		this.siapSaldo = siapSaldo;
	}

	public BigDecimal getSiapSecuencial() {
		return siapSecuencial;
	}

	public void setSiapSecuencial(BigDecimal siapSecuencial) {
		this.siapSecuencial = siapSecuencial;
	}

	public String getSiapTipo() {
		return siapTipo;
	}

	public void setSiapTipo(String siapTipo) {
		this.siapTipo = siapTipo;
	}
}

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
public class ReporteSaldoConsolidadoBonosViaticos {
	private String fechaHasta;

	///// detalle
	private String scbvCategoria;
	private String scbvId;
	private String scbvNombres;
	private BigDecimal scbvBonos;
	private BigDecimal scbvViaticos;
	private BigDecimal scbvTotal;
	private String scbvOrden;

	public ReporteSaldoConsolidadoBonosViaticos() {
	}

	public ReporteSaldoConsolidadoBonosViaticos(String fechaHasta, String scbvCategoria, String scbvId,
			String scbvNombres, BigDecimal scbvBonos, BigDecimal scbvViaticos, BigDecimal scbvTotal, String scbvOrden) {
		this.fechaHasta = fechaHasta;
		this.scbvCategoria = scbvCategoria;
		this.scbvId = scbvId;
		this.scbvNombres = scbvNombres;
		this.scbvBonos = scbvBonos;
		this.scbvViaticos = scbvViaticos;
		this.scbvTotal = scbvTotal;
		this.scbvOrden = scbvOrden;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public BigDecimal getScbvBonos() {
		return scbvBonos;
	}

	public void setScbvBonos(BigDecimal scbvBonos) {
		this.scbvBonos = scbvBonos;
	}

	public String getScbvCategoria() {
		return scbvCategoria;
	}

	public void setScbvCategoria(String scbvCategoria) {
		this.scbvCategoria = scbvCategoria;
	}

	public String getScbvId() {
		return scbvId;
	}

	public void setScbvId(String scbvId) {
		this.scbvId = scbvId;
	}

	public String getScbvNombres() {
		return scbvNombres;
	}

	public void setScbvNombres(String scbvNombres) {
		this.scbvNombres = scbvNombres;
	}

	public String getScbvOrden() {
		return scbvOrden;
	}

	public void setScbvOrden(String scbvOrden) {
		this.scbvOrden = scbvOrden;
	}

	public BigDecimal getScbvTotal() {
		return scbvTotal;
	}

	public void setScbvTotal(BigDecimal scbvTotal) {
		this.scbvTotal = scbvTotal;
	}

	public BigDecimal getScbvViaticos() {
		return scbvViaticos;
	}

	public void setScbvViaticos(BigDecimal scbvViaticos) {
		this.scbvViaticos = scbvViaticos;
	}
}

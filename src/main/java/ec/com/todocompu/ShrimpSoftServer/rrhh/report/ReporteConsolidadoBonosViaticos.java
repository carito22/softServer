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
public class ReporteConsolidadoBonosViaticos {
	private String desde;
	private String hasta;

	private String cbvCategoria;
	private String cbvId;
	private String cbvNombres;
	private BigDecimal cbvBonos;
	private BigDecimal cbvBonosND;
	private BigDecimal cbvBonoFijo;
	private BigDecimal cbvBonoFijoND;
	private BigDecimal cbvViaticos;
	private BigDecimal cbvTotal;
	private String cbvOrden;

	public ReporteConsolidadoBonosViaticos() {
	}

	public ReporteConsolidadoBonosViaticos(String desde, String hasta, String cbvCategoria, String cbvId,
			String cbvNombres, BigDecimal cbvBonos, BigDecimal cbvBonosND, BigDecimal cbvBonoFijo,
			BigDecimal cbvBonoFijoND, BigDecimal cbvViaticos, BigDecimal cbvTotal, String cbvOrden) {
		this.desde = desde;
		this.hasta = hasta;
		this.cbvCategoria = cbvCategoria;
		this.cbvId = cbvId;
		this.cbvNombres = cbvNombres;
		this.cbvBonos = cbvBonos;
		this.cbvBonosND = cbvBonosND;
		this.cbvBonoFijo = cbvBonoFijo;
		this.cbvBonoFijoND = cbvBonoFijoND;
		this.cbvViaticos = cbvViaticos;
		this.cbvTotal = cbvTotal;
		this.cbvOrden = cbvOrden;
	}

	public BigDecimal getCbvBonoFijo() {
		return cbvBonoFijo;
	}

	public void setCbvBonoFijo(BigDecimal cbvBonoFijo) {
		this.cbvBonoFijo = cbvBonoFijo;
	}

	public BigDecimal getCbvBonoFijoND() {
		return cbvBonoFijoND;
	}

	public void setCbvBonoFijoND(BigDecimal cbvBonoFijoND) {
		this.cbvBonoFijoND = cbvBonoFijoND;
	}

	public BigDecimal getCbvBonos() {
		return cbvBonos;
	}

	public void setCbvBonos(BigDecimal cbvBonos) {
		this.cbvBonos = cbvBonos;
	}

	public BigDecimal getCbvBonosND() {
		return cbvBonosND;
	}

	public void setCbvBonosND(BigDecimal cbvBonosND) {
		this.cbvBonosND = cbvBonosND;
	}

	public String getCbvCategoria() {
		return cbvCategoria;
	}

	public void setCbvCategoria(String cbvCategoria) {
		this.cbvCategoria = cbvCategoria;
	}

	public String getCbvId() {
		return cbvId;
	}

	public void setCbvId(String cbvId) {
		this.cbvId = cbvId;
	}

	public String getCbvNombres() {
		return cbvNombres;
	}

	public void setCbvNombres(String cbvNombres) {
		this.cbvNombres = cbvNombres;
	}

	public String getCbvOrden() {
		return cbvOrden;
	}

	public void setCbvOrden(String cbvOrden) {
		this.cbvOrden = cbvOrden;
	}

	public BigDecimal getCbvTotal() {
		return cbvTotal;
	}

	public void setCbvTotal(BigDecimal cbvTotal) {
		this.cbvTotal = cbvTotal;
	}

	public BigDecimal getCbvViaticos() {
		return cbvViaticos;
	}

	public void setCbvViaticos(BigDecimal cbvViaticos) {
		this.cbvViaticos = cbvViaticos;
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
}

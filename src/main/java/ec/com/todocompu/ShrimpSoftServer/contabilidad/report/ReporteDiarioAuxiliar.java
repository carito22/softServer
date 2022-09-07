/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteDiarioAuxiliar {
	private String codigoTipo;
	private String fechaDesde;
	private String fechaHasta;
	private Integer daOrden;
	private String daCuenta;
	private String daDetalle;
	private String daCP;
	private String daCC;
	private String daDocumento;
	private String daDebe;
	private String daHaber;
	private String daBloque;

	public ReporteDiarioAuxiliar() {
	}

	public ReporteDiarioAuxiliar(String codigoTipo, String fechaDesde, String fechaHasta, Integer daOrden,
			String daCuenta, String daDetalle, String daCP, String daCC, String daDocumento, String daDebe,
			String daHaber, String daBloque) {
		this.codigoTipo = codigoTipo;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.daOrden = daOrden;
		this.daCuenta = daCuenta;
		this.daDetalle = daDetalle;
		this.daCP = daCP;
		this.daCC = daCC;
		this.daDocumento = daDocumento;
		this.daDebe = daDebe;
		this.daHaber = daHaber;
		this.daBloque = daBloque;
	}

	public String getCodigoTipo() {
		return codigoTipo;
	}

	public void setCodigoTipo(String codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public String getDaBloque() {
		return daBloque;
	}

	public void setDaBloque(String daBloque) {
		this.daBloque = daBloque;
	}

	public String getDaCC() {
		return daCC;
	}

	public void setDaCC(String daCC) {
		this.daCC = daCC;
	}

	public String getDaCP() {
		return daCP;
	}

	public void setDaCP(String daCP) {
		this.daCP = daCP;
	}

	public String getDaCuenta() {
		return daCuenta;
	}

	public void setDaCuenta(String daCuenta) {
		this.daCuenta = daCuenta;
	}

	public String getDaDebe() {
		return daDebe;
	}

	public void setDaDebe(String daDebe) {
		this.daDebe = daDebe;
	}

	public String getDaDetalle() {
		return daDetalle;
	}

	public void setDaDetalle(String daDetalle) {
		this.daDetalle = daDetalle;
	}

	public String getDaDocumento() {
		return daDocumento;
	}

	public void setDaDocumento(String daDocumento) {
		this.daDocumento = daDocumento;
	}

	public Integer getDaOrden() {
		return daOrden;
	}

	public void setDaOrden(Integer daOrden) {
		this.daOrden = daOrden;
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

	public String getDaHaber() {
		return daHaber;
	}

	public void setDaHaber(String daHaber) {
		this.daHaber = daHaber;
	}

}

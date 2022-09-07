/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.report;

/**
 *
 * @author paca94
 */
public class ReporteListadoConsumos {

	private String desde;
	private String hasta;
	///////////// DETALLE
	private String conNumeroSistema;
	private String conFecha;
	private String conObservaciones;
	private Boolean conPendiente;
	private Boolean conAnulado;

	public ReporteListadoConsumos() {
	}

	public ReporteListadoConsumos(String desde, String hasta, String conNumeroSistema, String conFecha,
			String conObservaciones, Boolean conPendiente, Boolean conAnulado) {
		this.desde = desde;
		this.hasta = hasta;
		this.conNumeroSistema = conNumeroSistema;
		this.conFecha = conFecha;
		this.conObservaciones = conObservaciones;
		this.conPendiente = conPendiente;
		this.conAnulado = conAnulado;
	}

	public Boolean getConAnulado() {
		return conAnulado;
	}

	public void setConAnulado(Boolean conAnulado) {
		this.conAnulado = conAnulado;
	}

	public String getConFecha() {
		return conFecha;
	}

	public void setConFecha(String conFecha) {
		this.conFecha = conFecha;
	}

	public String getConNumeroSistema() {
		return conNumeroSistema;
	}

	public void setConNumeroSistema(String conNumeroSistema) {
		this.conNumeroSistema = conNumeroSistema;
	}

	public String getConObservaciones() {
		return conObservaciones;
	}

	public void setConObservaciones(String conObservaciones) {
		this.conObservaciones = conObservaciones;
	}

	public Boolean getConPendiente() {
		return conPendiente;
	}

	public void setConPendiente(Boolean conPendiente) {
		this.conPendiente = conPendiente;
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

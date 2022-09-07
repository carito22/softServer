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
public class ReporteDetalleViatico {
	///// categoria
	private String desde;
	private String hasta;
	private String categoriaCabecera;
	private String nombresCabecera;

	//// detalle
	private String categoria;
	private String fecha;
	private String id;
	private String nombres;
	private java.math.BigDecimal valor;
	private Boolean reverso;
	private String contable;
	private Boolean anulado;
	private String observaciones;

	public ReporteDetalleViatico() {
	}

	public ReporteDetalleViatico(String desde, String hasta, String categoriaCabecera, String nombresCabecera,
			String categoria, String fecha, String id, String nombres, BigDecimal valor, Boolean reverso,
			String contable, Boolean anulado, String observaciones) {
		this.desde = desde;
		this.hasta = hasta;
		this.categoriaCabecera = categoriaCabecera;
		this.nombresCabecera = nombresCabecera;
		this.categoria = categoria;
		this.fecha = fecha;
		this.id = id;
		this.nombres = nombres;
		this.valor = valor;
		this.reverso = reverso;
		this.contable = contable;
		this.anulado = anulado;
		this.observaciones = observaciones;
	}

	public Boolean getAnulado() {
		return anulado;
	}

	public void setAnulado(Boolean anulado) {
		this.anulado = anulado;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCategoriaCabecera() {
		return categoriaCabecera;
	}

	public void setCategoriaCabecera(String categoriaCabecera) {
		this.categoriaCabecera = categoriaCabecera;
	}

	public String getContable() {
		return contable;
	}

	public void setContable(String contable) {
		this.contable = contable;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getNombresCabecera() {
		return nombresCabecera;
	}

	public void setNombresCabecera(String nombresCabecera) {
		this.nombresCabecera = nombresCabecera;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Boolean getReverso() {
		return reverso;
	}

	public void setReverso(Boolean reverso) {
		this.reverso = reverso;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}

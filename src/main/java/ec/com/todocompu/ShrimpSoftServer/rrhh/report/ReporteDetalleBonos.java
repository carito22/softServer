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
public class ReporteDetalleBonos {
	///// categoria
	private String desde;
	private String hasta;
	private String categoriaCabecera;
	private String nombresCabecera;

	//// detalle
	private String dbCategoria;
	private String dbFecha;
	private String dbId;
	private String dbNombres;
	private String dbConcepto;
	private String dbSector;
	private String dbPiscina;
	private BigDecimal dbValor;
	private Boolean dbReverso;
	private String dbContable;
	private Boolean dbAnulado;
	private Boolean dbDeducible;
	private String dbObservaciones;

	public ReporteDetalleBonos() {
	}

	public ReporteDetalleBonos(String desde, String hasta, String categoriaCabecera, String nombresCabecera,
			String dbCategoria, String dbFecha, String dbId, String dbNombres, String dbConcepto, String dbSector,
			String dbPiscina, BigDecimal dbValor, Boolean dbReverso, String dbContable, Boolean dbAnulado,
			Boolean dbDeducible, String dbObservaciones) {
		this.desde = desde;
		this.hasta = hasta;
		this.categoriaCabecera = categoriaCabecera;
		this.nombresCabecera = nombresCabecera;
		this.dbCategoria = dbCategoria;
		this.dbFecha = dbFecha;
		this.dbId = dbId;
		this.dbNombres = dbNombres;
		this.dbConcepto = dbConcepto;
		this.dbSector = dbSector;
		this.dbPiscina = dbPiscina;
		this.dbValor = dbValor;
		this.dbReverso = dbReverso;
		this.dbContable = dbContable;
		this.dbAnulado = dbAnulado;
		this.dbDeducible = dbDeducible;
		this.dbObservaciones = dbObservaciones;
	}

	public String getCategoriaCabecera() {
		return categoriaCabecera;
	}

	public void setCategoriaCabecera(String categoriaCabecera) {
		this.categoriaCabecera = categoriaCabecera;
	}

	public Boolean getDbAnulado() {
		return dbAnulado;
	}

	public void setDbAnulado(Boolean dbAnulado) {
		this.dbAnulado = dbAnulado;
	}

	public String getDbCategoria() {
		return dbCategoria;
	}

	public void setDbCategoria(String dbCategoria) {
		this.dbCategoria = dbCategoria;
	}

	public String getDbConcepto() {
		return dbConcepto;
	}

	public void setDbConcepto(String dbConcepto) {
		this.dbConcepto = dbConcepto;
	}

	public String getDbContable() {
		return dbContable;
	}

	public void setDbContable(String dbContable) {
		this.dbContable = dbContable;
	}

	public String getDbFecha() {
		return dbFecha;
	}

	public void setDbFecha(String dbFecha) {
		this.dbFecha = dbFecha;
	}

	public String getDbId() {
		return dbId;
	}

	public void setDbId(String dbId) {
		this.dbId = dbId;
	}

	public String getDbNombres() {
		return dbNombres;
	}

	public void setDbNombres(String dbNombres) {
		this.dbNombres = dbNombres;
	}

	public String getDbObservaciones() {
		return dbObservaciones;
	}

	public void setDbObservaciones(String dbObservaciones) {
		this.dbObservaciones = dbObservaciones;
	}

	public String getDbPiscina() {
		return dbPiscina;
	}

	public void setDbPiscina(String dbPiscina) {
		this.dbPiscina = dbPiscina;
	}

	public Boolean getDbReverso() {
		return dbReverso;
	}

	public void setDbReverso(Boolean dbReverso) {
		this.dbReverso = dbReverso;
	}

	public String getDbSector() {
		return dbSector;
	}

	public void setDbSector(String dbSector) {
		this.dbSector = dbSector;
	}

	public BigDecimal getDbValor() {
		return dbValor;
	}

	public void setDbValor(BigDecimal dbValor) {
		this.dbValor = dbValor;
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

	public String getNombresCabecera() {
		return nombresCabecera;
	}

	public void setNombresCabecera(String nombresCabecera) {
		this.nombresCabecera = nombresCabecera;
	}

	public Boolean getDbDeducible() {
		return dbDeducible;
	}

	public void setDbDeducible(Boolean dbDeducible) {
		this.dbDeducible = dbDeducible;
	}

}

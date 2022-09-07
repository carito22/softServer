/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import java.math.BigDecimal;

/**
 *
 * @author Mario Ramos Duque
 */
public class ReporteDetalleHorasExtras {
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
    private BigDecimal dbValor50;
    private BigDecimal dbValor100;
    private BigDecimal dbValorExtraordinaria100;
    private Boolean dbReverso;
    private String dbContable;
    private Boolean dbAnulado;
    private String dbObservaciones;

    public ReporteDetalleHorasExtras() {
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

    public BigDecimal getDbValor50() {
        return dbValor50;
    }

    public void setDbValor50(BigDecimal dbValor50) {
        this.dbValor50 = dbValor50;
    }

    public BigDecimal getDbValor100() {
        return dbValor100;
    }

    public void setDbValor100(BigDecimal dbValor100) {
        this.dbValor100 = dbValor100;
    }

    public BigDecimal getDbValorExtraordinaria100() {
        return dbValorExtraordinaria100;
    }

    public void setDbValorExtraordinaria100(BigDecimal dbValorExtraordinaria100) {
        this.dbValorExtraordinaria100 = dbValorExtraordinaria100;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import java.math.BigDecimal;

/**
 *
 * @author VALDIVIEZO
 */
public class ResumenCorridaSubcategoriaNombres {

    private String sector;
    private String piscina;
    private String corrida;
    private BigDecimal hectareaje;
    private String fechaInicio;
    private String fechaSiembra;
    private String fechaPesca;
    private String fechaHasta;
    private String codigoSubcategoria;
    private String codigoSubcategoriaTransferido;

    public ResumenCorridaSubcategoriaNombres() {
    }

    public ResumenCorridaSubcategoriaNombres(String sector, String piscina, String corrida, BigDecimal hectareaje, String fechaInicio, String fechaSiembra, String fechaPesca, String fechaHasta, String codigoSubcategoria, String codigoSubcategoriaTransferido) {
        this.sector = sector;
        this.piscina = piscina;
        this.corrida = corrida;
        this.hectareaje = hectareaje;
        this.fechaInicio = fechaInicio;
        this.fechaSiembra = fechaSiembra;
        this.fechaPesca = fechaPesca;
        this.fechaHasta = fechaHasta;
        this.codigoSubcategoria = codigoSubcategoria;
        this.codigoSubcategoriaTransferido = codigoSubcategoriaTransferido;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPiscina() {
        return piscina;
    }

    public void setPiscina(String piscina) {
        this.piscina = piscina;
    }

    public String getCorrida() {
        return corrida;
    }

    public void setCorrida(String corrida) {
        this.corrida = corrida;
    }

    public BigDecimal getHectareaje() {
        return hectareaje;
    }

    public void setHectareaje(BigDecimal hectareaje) {
        this.hectareaje = hectareaje;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaSiembra() {
        return fechaSiembra;
    }

    public void setFechaSiembra(String fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }

    public String getFechaPesca() {
        return fechaPesca;
    }

    public void setFechaPesca(String fechaPesca) {
        this.fechaPesca = fechaPesca;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getCodigoSubcategoria() {
        return codigoSubcategoria;
    }

    public void setCodigoSubcategoria(String codigoSubcategoria) {
        this.codigoSubcategoria = codigoSubcategoria;
    }

    public String getCodigoSubcategoriaTransferido() {
        return codigoSubcategoriaTransferido;
    }

    public void setCodigoSubcategoriaTransferido(String codigoSubcategoriaTransferido) {
        this.codigoSubcategoriaTransferido = codigoSubcategoriaTransferido;
    }

}

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
public class ResumenTrazabilidadAlimentacionNombres {

    private String sector;
    private String piscina;
    private String corrida;
    private BigDecimal hectareaje;
    private String fechaInicio;
    private String fechaSiembra;
    private String codigobalanceado;
    private String codigobalanceadoTransferido;

    public ResumenTrazabilidadAlimentacionNombres() {
    }

    public ResumenTrazabilidadAlimentacionNombres(String sector, String piscina, String corrida, BigDecimal hectareaje, String fechaInicio, String fechaSiembra, String codigobalanceado, String codigobalanceadoTransferido) {
        this.sector = sector;
        this.piscina = piscina;
        this.corrida = corrida;
        this.hectareaje = hectareaje;
        this.fechaInicio = fechaInicio;
        this.fechaSiembra = fechaSiembra;
        this.codigobalanceado = codigobalanceado;
        this.codigobalanceadoTransferido = codigobalanceadoTransferido;
    }

    public String getCodigobalanceadoTransferido() {
        return codigobalanceadoTransferido;
    }

    public void setCodigobalanceadoTransferido(String codigobalanceadoTransferido) {
        this.codigobalanceadoTransferido = codigobalanceadoTransferido;
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

    public String getCodigobalanceado() {
        return codigobalanceado;
    }

    public void setCodigobalanceado(String codigobalanceado) {
        this.codigobalanceado = codigobalanceado;
    }

}

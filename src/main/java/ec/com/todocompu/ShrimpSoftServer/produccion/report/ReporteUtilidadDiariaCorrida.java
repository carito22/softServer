/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteUtilidadDiariaCorrida {

    private String sector;
    private String piscina;
    private String corrida;
    private String uTipo;
    private String uDescripcion;
    private BigDecimal uValorNumerico;
    private String uValorTexto;

    public ReporteUtilidadDiariaCorrida() {
    }

    public ReporteUtilidadDiariaCorrida(String sector, String piscina, String corrida, String uTipo, String uDescripcion, BigDecimal uValorNumerico, String uValorTexto) {
        this.sector = sector;
        this.piscina = piscina;
        this.corrida = corrida;
        this.uTipo = uTipo;
        this.uDescripcion = uDescripcion;
        this.uValorNumerico = uValorNumerico;
        this.uValorTexto = uValorTexto;
    }

    public String getuTipo() {
        return uTipo;
    }

    public void setuTipo(String uTipo) {
        this.uTipo = uTipo;
    }

    public String getuDescripcion() {
        return uDescripcion;
    }

    public void setuDescripcion(String uDescripcion) {
        this.uDescripcion = uDescripcion;
    }

    public BigDecimal getuValorNumerico() {
        return uValorNumerico;
    }

    public void setuValorNumerico(BigDecimal uValorNumerico) {
        this.uValorNumerico = uValorNumerico;
    }

    public String getuValorTexto() {
        return uValorTexto;
    }

    public void setuValorTexto(String uValorTexto) {
        this.uValorTexto = uValorTexto;
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

}

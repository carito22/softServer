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
public class ReporteSaldoConsolidadoHorasExtras {

    private String fechaHasta;

    ///// detalle
    private String scbvCategoria;
    private String scbvId;
    private String scbvNombres;
    private BigDecimal scbvValor50;
    private BigDecimal scbvValor100;
    private BigDecimal scbvValorExtraordinarias100;
    private BigDecimal scbvTotal;
    private String scbvOrden;

    public ReporteSaldoConsolidadoHorasExtras() {
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
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

    public BigDecimal getScbvValor50() {
        return scbvValor50;
    }

    public void setScbvValor50(BigDecimal scbvValor50) {
        this.scbvValor50 = scbvValor50;
    }

    public BigDecimal getScbvValor100() {
        return scbvValor100;
    }

    public void setScbvValor100(BigDecimal scbvValor100) {
        this.scbvValor100 = scbvValor100;
    }

    public BigDecimal getScbvValorExtraordinarias100() {
        return scbvValorExtraordinarias100;
    }

    public void setScbvValorExtraordinarias100(BigDecimal scbvValorExtraordinarias100) {
        this.scbvValorExtraordinarias100 = scbvValorExtraordinarias100;
    }

}

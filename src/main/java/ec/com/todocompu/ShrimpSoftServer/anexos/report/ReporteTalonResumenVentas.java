/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteTalonResumenVentas {

    private String desde;
    private String hasta;
    private String venComprobante;
    private String venEstablecimiento;
    private String venPuntoEmision;
    private java.math.BigDecimal venBaseNoObjetoIva;
    private java.math.BigDecimal venBase0;
    private java.math.BigDecimal venBaseImponible;
    private java.math.BigDecimal venMotoIva;
    private java.math.BigDecimal venMontoIce;
    private java.math.BigDecimal venValorRetenidoIva;
    private java.math.BigDecimal venValorRetenidoRenta;
    private Integer venCantidadComprobantes;

    ;

	public ReporteTalonResumenVentas() {
    }

    public ReporteTalonResumenVentas(String desde, String hasta, String venComprobante, String venEstablecimiento,
            String venPuntoEmision, BigDecimal venBaseNoObjetoIva, BigDecimal venBase0, BigDecimal venBaseImponible, BigDecimal venMontoIce,
            BigDecimal venMotoIva, BigDecimal venValorRetenidoIva, BigDecimal venValorRetenidoRenta) {
        this.desde = desde;
        this.hasta = hasta;
        this.venComprobante = venComprobante;
        this.venEstablecimiento = venEstablecimiento;
        this.venPuntoEmision = venPuntoEmision;
        this.venBaseNoObjetoIva = venBaseNoObjetoIva;
        this.venBase0 = venBase0;
        this.venBaseImponible = venBaseImponible;
        this.venMontoIce = venMontoIce;
        this.venMotoIva = venMotoIva;
        this.venValorRetenidoIva = venValorRetenidoIva;
        this.venValorRetenidoRenta = venValorRetenidoRenta;
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

    public BigDecimal getVenBase0() {
        return venBase0;
    }

    public void setVenBase0(BigDecimal venBase0) {
        this.venBase0 = venBase0;
    }

    public BigDecimal getVenBaseImponible() {
        return venBaseImponible;
    }

    public void setVenBaseImponible(BigDecimal venBaseImponible) {
        this.venBaseImponible = venBaseImponible;
    }

    public BigDecimal getVenBaseNoObjetoIva() {
        return venBaseNoObjetoIva;
    }

    public void setVenBaseNoObjetoIva(BigDecimal venBaseNoObjetoIva) {
        this.venBaseNoObjetoIva = venBaseNoObjetoIva;
    }

    public String getVenComprobante() {
        return venComprobante;
    }

    public void setVenComprobante(String venComprobante) {
        this.venComprobante = venComprobante;
    }

    public String getVenEstablecimiento() {
        return venEstablecimiento;
    }

    public void setVenEstablecimiento(String venEstablecimiento) {
        this.venEstablecimiento = venEstablecimiento;
    }

    public BigDecimal getVenMotoIva() {
        return venMotoIva;
    }

    public void setVenMotoIva(BigDecimal venMotoIva) {
        this.venMotoIva = venMotoIva;
    }

    public String getVenPuntoEmision() {
        return venPuntoEmision;
    }

    public void setVenPuntoEmision(String venPuntoEmision) {
        this.venPuntoEmision = venPuntoEmision;
    }

    public BigDecimal getVenValorRetenidoIva() {
        return venValorRetenidoIva;
    }

    public void setVenValorRetenidoIva(BigDecimal venValorRetenidoIva) {
        this.venValorRetenidoIva = venValorRetenidoIva;
    }

    public BigDecimal getVenValorRetenidoRenta() {
        return venValorRetenidoRenta;
    }

    public void setVenValorRetenidoRenta(BigDecimal venValorRetenidoRenta) {
        this.venValorRetenidoRenta = venValorRetenidoRenta;
    }

    public BigDecimal getVenMontoIce() {
        return venMontoIce;
    }

    public void setVenMontoIce(BigDecimal venMontoIce) {
        this.venMontoIce = venMontoIce;
    }

    public Integer getVenCantidadComprobantes() {
        return venCantidadComprobantes;
    }

    public void setVenCantidadComprobantes(Integer venCantidadComprobantes) {
        this.venCantidadComprobantes = venCantidadComprobantes;
    }

}

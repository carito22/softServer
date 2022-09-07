/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteBalanceResultadosComparativo {
	private String codigoCP;
	private String fechaDesde;
	private String fechaHasta;
	private String fechaDesde2;
	private String fechaHasta2;
	private String brcCuenta;
	private String brcDetalle;
	private java.math.BigDecimal brcSaldo;
	private java.math.BigDecimal brcSaldo2;

    public ReporteBalanceResultadosComparativo() {
    }

    public ReporteBalanceResultadosComparativo(String codigoCP, String fechaDesde, String fechaHasta, String fechaDesde2, String fechaHasta2, String brcCuenta, String brcDetalle, BigDecimal brcSaldo, BigDecimal brcSaldo2) {
        this.codigoCP = codigoCP;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.fechaDesde2 = fechaDesde2;
        this.fechaHasta2 = fechaHasta2;
        this.brcCuenta = brcCuenta;
        this.brcDetalle = brcDetalle;
        this.brcSaldo = brcSaldo;
        this.brcSaldo2 = brcSaldo2;
    }

    public String getCodigoCP() {
        return codigoCP;
    }

    public void setCodigoCP(String codigoCP) {
        this.codigoCP = codigoCP;
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

    public String getFechaDesde2() {
        return fechaDesde2;
    }

    public void setFechaDesde2(String fechaDesde2) {
        this.fechaDesde2 = fechaDesde2;
    }

    public String getFechaHasta2() {
        return fechaHasta2;
    }

    public void setFechaHasta2(String fechaHasta2) {
        this.fechaHasta2 = fechaHasta2;
    }

    public String getBrcCuenta() {
        return brcCuenta;
    }

    public void setBrcCuenta(String brcCuenta) {
        this.brcCuenta = brcCuenta;
    }

    public String getBrcDetalle() {
        return brcDetalle;
    }

    public void setBrcDetalle(String brcDetalle) {
        this.brcDetalle = brcDetalle;
    }

    public BigDecimal getBrcSaldo() {
        return brcSaldo;
    }

    public void setBrcSaldo(BigDecimal brcSaldo) {
        this.brcSaldo = brcSaldo;
    }

    public BigDecimal getBrcSaldo2() {
        return brcSaldo2;
    }

    public void setBrcSaldo2(BigDecimal brcSaldo2) {
        this.brcSaldo2 = brcSaldo2;
    }

    @Override
    public String toString() {
        return "ReporteBalanceResultadosComparativo{" + "codigoCP=" + codigoCP + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + ", fechaDesde2=" + fechaDesde2 + ", fechaHasta2=" + fechaHasta2 + ", brcCuenta=" + brcCuenta + ", brcDetalle=" + brcDetalle + ", brcSaldo=" + brcSaldo + ", brcSaldo2=" + brcSaldo2 + '}';
    }


}

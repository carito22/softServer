/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

import java.math.BigDecimal;

public class ReporteConFunConsumoError {

    private String ippSecuencial;
    private String ippCompra;
    private String ippFecha;
    private String ippProductoCodigo;
    private String ippProductoNombre;
    private java.math.BigDecimal ippValor;
    private String ippSector;
    private String ippPiscina;

    public ReporteConFunConsumoError() {
    }

    public ReporteConFunConsumoError(String ippSecuencial, String ippCompra, String ippFecha, String ippProductoCodigo,
            String ippProductoNombre, BigDecimal ippValor, String ippSector, String ippPiscina) {
        
        this.ippSecuencial = ippSecuencial;
        this.ippCompra = ippCompra;
        this.ippFecha = ippFecha;
        this.ippProductoCodigo = ippProductoCodigo;
        this.ippProductoNombre = ippProductoNombre;
        this.ippValor = ippValor;
        this.ippSector = ippSector;
        this.ippPiscina = ippPiscina;
    }

    public String getIppSecuencial() {
        return ippSecuencial;
    }

    public void setIppSecuencial(String ippSecuencial) {
        this.ippSecuencial = ippSecuencial;
    }

    public String getIppCompra() {
        return ippCompra;
    }

    public void setIppCompra(String ippCompra) {
        this.ippCompra = ippCompra;
    }

    public String getIppFecha() {
        return ippFecha;
    }

    public void setIppFecha(String ippFecha) {
        this.ippFecha = ippFecha;
    }

    public String getIppProductoCodigo() {
        return ippProductoCodigo;
    }

    public void setIppProductoCodigo(String ippProductoCodigo) {
        this.ippProductoCodigo = ippProductoCodigo;
    }

    public String getIppProductoNombre() {
        return ippProductoNombre;
    }

    public void setIppProductoNombre(String ippProductoNombre) {
        this.ippProductoNombre = ippProductoNombre;
    }

    public BigDecimal getIppValor() {
        return ippValor;
    }

    public void setIppValor(BigDecimal ippValor) {
        this.ippValor = ippValor;
    }

    public String getIppSector() {
        return ippSector;
    }

    public void setIppSector(String ippSector) {
        this.ippSector = ippSector;
    }

    public String getIppPiscina() {
        return ippPiscina;
    }

    public void setIppPiscina(String ippPiscina) {
        this.ippPiscina = ippPiscina;
    }

    
}

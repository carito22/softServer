/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.report;

import java.math.BigDecimal;
import javax.persistence.Column;

/**
 *
 * @author Usuario1
 */
public class ReporteLiquidacionComisionista {

    private String liqEmpresa;
    private String liqMotivo;
    private String liqNumero;
    private String liqLote;
    private String liqFecha;
    private String liqCorrida;
    private String liqPiscina;
    private String liqSector;
    private String liqPendiente;
    private String liqAnulado;
    private String liqProveedorCodigo;
    private String liqProveedorNombre;
    private String liqClienteCodigo;
    private String liqClienteNombre;
    private String liqComisionistaCodigo;
    private String liqComisionistaNombre;
    private BigDecimal liqComisionistaValor;
    private BigDecimal liqLibrasEnviadas;
    private BigDecimal liqLibrasEntero;
    private BigDecimal liqLibrasColaProcesadas;
    private String fechaInicio;
    private String fechaFin;
    private String centroCosto;
    private String centroProduccion;
    private String descripcion;
    private String valor;

    public ReporteLiquidacionComisionista() {
    }

    public String getLiqEmpresa() {
        return liqEmpresa;
    }

    public void setLiqEmpresa(String liqEmpresa) {
        this.liqEmpresa = liqEmpresa;
    }

    public String getLiqMotivo() {
        return liqMotivo;
    }

    public void setLiqMotivo(String liqMotivo) {
        this.liqMotivo = liqMotivo;
    }

    public String getLiqNumero() {
        return liqNumero;
    }

    public void setLiqNumero(String liqNumero) {
        this.liqNumero = liqNumero;
    }

    public String getLiqLote() {
        return liqLote;
    }

    public void setLiqLote(String liqLote) {
        this.liqLote = liqLote;
    }

    public String getLiqFecha() {
        return liqFecha;
    }

    public void setLiqFecha(String liqFecha) {
        this.liqFecha = liqFecha;
    }

    public String getLiqCorrida() {
        return liqCorrida;
    }

    public void setLiqCorrida(String liqCorrida) {
        this.liqCorrida = liqCorrida;
    }

    public String getLiqPiscina() {
        return liqPiscina;
    }

    public void setLiqPiscina(String liqPiscina) {
        this.liqPiscina = liqPiscina;
    }

    public String getLiqSector() {
        return liqSector;
    }

    public void setLiqSector(String liqSector) {
        this.liqSector = liqSector;
    }

    public String getLiqPendiente() {
        return liqPendiente;
    }

    public void setLiqPendiente(String liqPendiente) {
        this.liqPendiente = liqPendiente;
    }

    public String getLiqAnulado() {
        return liqAnulado;
    }

    public void setLiqAnulado(String liqAnulado) {
        this.liqAnulado = liqAnulado;
    }

    public String getLiqProveedorCodigo() {
        return liqProveedorCodigo;
    }

    public void setLiqProveedorCodigo(String liqProveedorCodigo) {
        this.liqProveedorCodigo = liqProveedorCodigo;
    }

    public String getLiqProveedorNombre() {
        return liqProveedorNombre;
    }

    public void setLiqProveedorNombre(String liqProveedorNombre) {
        this.liqProveedorNombre = liqProveedorNombre;
    }

    public String getLiqClienteCodigo() {
        return liqClienteCodigo;
    }

    public void setLiqClienteCodigo(String liqClienteCodigo) {
        this.liqClienteCodigo = liqClienteCodigo;
    }

    public String getLiqClienteNombre() {
        return liqClienteNombre;
    }

    public void setLiqClienteNombre(String liqClienteNombre) {
        this.liqClienteNombre = liqClienteNombre;
    }

    public String getLiqComisionistaCodigo() {
        return liqComisionistaCodigo;
    }

    public void setLiqComisionistaCodigo(String liqComisionistaCodigo) {
        this.liqComisionistaCodigo = liqComisionistaCodigo;
    }

    public String getLiqComisionistaNombre() {
        return liqComisionistaNombre;
    }

    public void setLiqComisionistaNombre(String liqComisionistaNombre) {
        this.liqComisionistaNombre = liqComisionistaNombre;
    }

    public BigDecimal getLiqComisionistaValor() {
        return liqComisionistaValor;
    }

    public void setLiqComisionistaValor(BigDecimal liqComisionistaValor) {
        this.liqComisionistaValor = liqComisionistaValor;
    }

    public BigDecimal getLiqLibrasEnviadas() {
        return liqLibrasEnviadas;
    }

    public void setLiqLibrasEnviadas(BigDecimal liqLibrasEnviadas) {
        this.liqLibrasEnviadas = liqLibrasEnviadas;
    }

    public BigDecimal getLiqLibrasEntero() {
        return liqLibrasEntero;
    }

    public void setLiqLibrasEntero(BigDecimal liqLibrasEntero) {
        this.liqLibrasEntero = liqLibrasEntero;
    }

    public BigDecimal getLiqLibrasColaProcesadas() {
        return liqLibrasColaProcesadas;
    }

    public void setLiqLibrasColaProcesadas(BigDecimal liqLibrasColaProcesadas) {
        this.liqLibrasColaProcesadas = liqLibrasColaProcesadas;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public String getCentroProduccion() {
        return centroProduccion;
    }

    public void setCentroProduccion(String centroProduccion) {
        this.centroProduccion = centroProduccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}

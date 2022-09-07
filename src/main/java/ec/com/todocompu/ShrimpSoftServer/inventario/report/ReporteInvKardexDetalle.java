/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import java.math.BigDecimal;

/**
 *
 * @author desarrollador1
 */
public class ReporteInvKardexDetalle {

    private String sisUsuarioEmpresaTO;
    private String kardex;
    private String nomProducto;
    private String fechaDesde;
    private String fechaHasta;

    // detalle
    private String ktransaccion;
    private String kFecha;

    private BigDecimal kEntradasCantidad;
    private BigDecimal kEntradasCosto;
    private BigDecimal kSalidasCantidad;
    private BigDecimal kSalidasCosto;
    private BigDecimal kSaldosCantidad;
    private BigDecimal kSaldosCosto;
    private BigDecimal kCostoActual;

    private BigDecimal kEntradasCaja;
    private BigDecimal kSalidasCaja;
    private BigDecimal kSaldosCaja;

    private String kSectorPiscina;
    private String kNúmeroSistema;
    private String kDocumentoNumero;
    private String kProveedor;
    private String kObservaciones;
    private String kCategoria;
    private BigDecimal kFactorConversion;

    public ReporteInvKardexDetalle() {
    }

    public ReporteInvKardexDetalle(String sisUsuarioEmpresaTO, String kardex, String nomProducto, String fechaDesde,
            String fechaHasta, String ktransaccion, String kFecha, BigDecimal kEntradasCantidad,
            BigDecimal kEntradasCosto, BigDecimal kSalidasCantidad, BigDecimal kSalidasCosto,
            BigDecimal kSaldosCantidad, BigDecimal kSaldosCosto, BigDecimal kCostoActual, String kSectorPiscina,
            String kNúmeroSistema, String kDocumentoNumero, String kProveedor, String kObservaciones) {
        this.sisUsuarioEmpresaTO = sisUsuarioEmpresaTO;
        this.kardex = kardex;
        this.nomProducto = nomProducto;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.ktransaccion = ktransaccion;
        this.kFecha = kFecha;
        this.kEntradasCantidad = kEntradasCantidad;
        this.kEntradasCosto = kEntradasCosto;
        this.kSalidasCantidad = kSalidasCantidad;
        this.kSalidasCosto = kSalidasCosto;
        this.kSaldosCantidad = kSaldosCantidad;
        this.kSaldosCosto = kSaldosCosto;
        this.kCostoActual = kCostoActual;
        this.kSectorPiscina = kSectorPiscina;
        this.kNúmeroSistema = kNúmeroSistema;
        this.kDocumentoNumero = kDocumentoNumero;
        this.kProveedor = kProveedor;
        this.kObservaciones = kObservaciones;
    }

    public String getSisUsuarioEmpresaTO() {
        return sisUsuarioEmpresaTO;
    }

    public void setSisUsuarioEmpresaTO(String sisUsuarioEmpresaTO) {
        this.sisUsuarioEmpresaTO = sisUsuarioEmpresaTO;
    }

    public String getKardex() {
        return kardex;
    }

    public void setKardex(String kardex) {
        this.kardex = kardex;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
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

    public String getKtransaccion() {
        return ktransaccion;
    }

    public void setKtransaccion(String ktransaccion) {
        this.ktransaccion = ktransaccion;
    }

    public String getkFecha() {
        return kFecha;
    }

    public void setkFecha(String kFecha) {
        this.kFecha = kFecha;
    }

    public BigDecimal getkEntradasCantidad() {
        return kEntradasCantidad;
    }

    public void setkEntradasCantidad(BigDecimal kEntradasCantidad) {
        this.kEntradasCantidad = kEntradasCantidad;
    }

    public BigDecimal getkEntradasCosto() {
        return kEntradasCosto;
    }

    public void setkEntradasCosto(BigDecimal kEntradasCosto) {
        this.kEntradasCosto = kEntradasCosto;
    }

    public BigDecimal getkSalidasCantidad() {
        return kSalidasCantidad;
    }

    public void setkSalidasCantidad(BigDecimal kSalidasCantidad) {
        this.kSalidasCantidad = kSalidasCantidad;
    }

    public BigDecimal getkSalidasCosto() {
        return kSalidasCosto;
    }

    public void setkSalidasCosto(BigDecimal kSalidasCosto) {
        this.kSalidasCosto = kSalidasCosto;
    }

    public BigDecimal getkSaldosCantidad() {
        return kSaldosCantidad;
    }

    public void setkSaldosCantidad(BigDecimal kSaldosCantidad) {
        this.kSaldosCantidad = kSaldosCantidad;
    }

    public BigDecimal getkSaldosCosto() {
        return kSaldosCosto;
    }

    public void setkSaldosCosto(BigDecimal kSaldosCosto) {
        this.kSaldosCosto = kSaldosCosto;
    }

    public BigDecimal getkCostoActual() {
        return kCostoActual;
    }

    public void setkCostoActual(BigDecimal kCostoActual) {
        this.kCostoActual = kCostoActual;
    }

    public String getkSectorPiscina() {
        return kSectorPiscina;
    }

    public void setkSectorPiscina(String kSectorPiscina) {
        this.kSectorPiscina = kSectorPiscina;
    }

    public String getkNúmeroSistema() {
        return kNúmeroSistema;
    }

    public void setkNúmeroSistema(String kNúmeroSistema) {
        this.kNúmeroSistema = kNúmeroSistema;
    }

    public String getkDocumentoNumero() {
        return kDocumentoNumero;
    }

    public void setkDocumentoNumero(String kDocumentoNumero) {
        this.kDocumentoNumero = kDocumentoNumero;
    }

    public String getkProveedor() {
        return kProveedor;
    }

    public void setkProveedor(String kProveedor) {
        this.kProveedor = kProveedor;
    }

    public String getkObservaciones() {
        return kObservaciones;
    }

    public void setkObservaciones(String kObservaciones) {
        this.kObservaciones = kObservaciones;
    }

    public String getkCategoria() {
        return kCategoria;
    }

    public void setkCategoria(String kCategoria) {
        this.kCategoria = kCategoria;
    }

    public BigDecimal getkFactorConversion() {
        return kFactorConversion;
    }

    public void setkFactorConversion(BigDecimal kFactorConversion) {
        this.kFactorConversion = kFactorConversion;
    }

    public BigDecimal getkEntradasCaja() {
        return kEntradasCaja;
    }

    public void setkEntradasCaja(BigDecimal kEntradasCaja) {
        this.kEntradasCaja = kEntradasCaja;
    }

    public BigDecimal getkSalidasCaja() {
        return kSalidasCaja;
    }

    public void setkSalidasCaja(BigDecimal kSalidasCaja) {
        this.kSalidasCaja = kSalidasCaja;
    }

    public BigDecimal getkSaldosCaja() {
        return kSaldosCaja;
    }

    public void setkSaldosCaja(BigDecimal kSaldosCaja) {
        this.kSaldosCaja = kSaldosCaja;
    }

}

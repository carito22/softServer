/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.pedidos.report;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Developer4
 */
public class ReporteInvPedidosTO {

    //cabecera
    private String codigoEmpresa;
    private String codigoSector;
    private String codigoMotivo;
    private String descripcionSector;
    private String descripcionMotivo;
    private Date fechaElaboracion;
    //detalle list
    private String pedEstado;
    private String pedNumero;
    private String pedCodigoMotivo;
    private String pedMotivoDescripcion;
    private String pedCodigoSector;
    private String pedSectorDescripcion;
    private Date pedFecha;
    private Date pedFechaEntrega;
    private String pedRegistrador;
    private String pedAprobador;
    private String pedEjecutor;
    private BigDecimal pedMontoTotal;

    ReporteInvPedidosTO() {

    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getCodigoSector() {
        return codigoSector;
    }

    public void setCodigoSector(String codigoSector) {
        this.codigoSector = codigoSector;
    }

    public String getCodigoMotivo() {
        return codigoMotivo;
    }

    public void setCodigoMotivo(String codigoMotivo) {
        this.codigoMotivo = codigoMotivo;
    }

    public String getPedEstado() {
        return pedEstado;
    }

    public void setPedEstado(String pedEstado) {
        this.pedEstado = pedEstado;
    }

    public String getPedNumero() {
        return pedNumero;
    }

    public void setPedNumero(String pedNumero) {
        this.pedNumero = pedNumero;
    }

    public Date getPedFecha() {
        return pedFecha;
    }

    public void setPedFecha(Date pedFecha) {
        this.pedFecha = pedFecha;
    }

    public String getPedRegistrador() {
        return pedRegistrador;
    }

    public void setPedRegistrador(String pedRegistrador) {
        this.pedRegistrador = pedRegistrador;
    }

    public String getPedAprobador() {
        return pedAprobador;
    }

    public void setPedAprobador(String pedAprobador) {
        this.pedAprobador = pedAprobador;
    }

    public String getPedEjecutor() {
        return pedEjecutor;
    }

    public void setPedEjecutor(String pedEjecutor) {
        this.pedEjecutor = pedEjecutor;
    }

    public BigDecimal getPedMontoTotal() {
        return pedMontoTotal;
    }

    public void setPedMontoTotal(BigDecimal pedMontoTotal) {
        this.pedMontoTotal = pedMontoTotal;
    }

    public String getDescripcionSector() {
        return descripcionSector;
    }

    public void setDescripcionSector(String descripcionSector) {
        this.descripcionSector = descripcionSector;
    }

    public String getDescripcionMotivo() {
        return descripcionMotivo;
    }

    public void setDescripcionMotivo(String descripcionMotivo) {
        this.descripcionMotivo = descripcionMotivo;
    }

    public Date getPedFechaEntrega() {
        return pedFechaEntrega;
    }

    public void setPedFechaEntrega(Date pedFechaEntrega) {
        this.pedFechaEntrega = pedFechaEntrega;
    }

    public String getPedCodigoMotivo() {
        return pedCodigoMotivo;
    }

    public void setPedCodigoMotivo(String pedCodigoMotivo) {
        this.pedCodigoMotivo = pedCodigoMotivo;
    }

    public String getPedMotivoDescripcion() {
        return pedMotivoDescripcion;
    }

    public void setPedMotivoDescripcion(String pedMotivoDescripcion) {
        this.pedMotivoDescripcion = pedMotivoDescripcion;
    }

    public String getPedCodigoSector() {
        return pedCodigoSector;
    }

    public void setPedCodigoSector(String pedCodigoSector) {
        this.pedCodigoSector = pedCodigoSector;
    }

    public String getPedSectorDescripcion() {
        return pedSectorDescripcion;
    }

    public void setPedSectorDescripcion(String pedSectorDescripcion) {
        this.pedSectorDescripcion = pedSectorDescripcion;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

}
